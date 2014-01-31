package models.daemon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

import models.Constants;
import models.Trend;
import models.service.TrendService;
import models.service.TrendServiceImpl;
import models.utils.DBUtils;
import play.Logger;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * A batch job that runs hourly and generates top N trending keywords. These keywords are
 * then staged into a db table for easy access.
 * 
 * @author excelsior
 *
 */
public class TrendGenerator {

	public static void generateTrends()
	{
		Connection dbConn = DBUtils.getDBConnection();
		PriorityQueue<Trend> topNTrends = new PriorityQueue<Trend>(Constants.NUM_TRENDS_TO_SHOW);
		
		// Load all tweets in the past 1-2 days
		String sql = "SELECT content FROM " + Constants.TWEET_TBL + 
				" WHERE (DATE_PART('day', now() - create_time )) <= 1";
		List<String> tweets = Lists.newArrayList();
		PreparedStatement preparedSql = null;
		try {
			preparedSql = dbConn.prepareStatement(sql);
			ResultSet results = preparedSql.executeQuery();
			while(results.next()) {
				tweets.add(results.getString("content"));
			}
		} catch (SQLException e) {
			Logger.error("Failed to fetch top N trends ..");
			e.printStackTrace();
		}
		Logger.info("Collected all tweets in the time window ..");
		
		// Use a priority queue to maintain a heap of size N at any point in time.
		Map<String, Integer> bagOfWords = getBagOfWordsInTweets(tweets);
		for(Map.Entry<String, Integer> entry : bagOfWords.entrySet()) {
			String keyword = entry.getKey();
			int count = entry.getValue();
			
			if(topNTrends.size() < Constants.NUM_TRENDS_TO_SHOW) {
				topNTrends.add(new Trend(keyword, count));
			}
			else {
				Trend head = topNTrends.peek();
				if(head.getCount() < count) {
					topNTrends.remove(head);
					topNTrends.add(new Trend(keyword, count));
				}
			}
		}
		Logger.info("Determined the top N trending words in tweets ..");
		
		// Load the trends in a table
		TrendService trendService = new TrendServiceImpl();
		trendService.deleteStaleTrends();
		for(Trend trend : topNTrends) {
			trendService.insertTrend(trend);
		}
		Logger.info("Staged the top N trends into trend table ..");
		
		DBUtils.cleanDBResources(dbConn, preparedSql);
	}
	
	private static Map<String, Integer> getBagOfWordsInTweets(List<String> tweets)
	{
		Map<String, Integer> trendsMap = Maps.newHashMap();
		for(String tweet : tweets) {
			StringTokenizer tokenizer = new StringTokenizer(tweet);
			while(tokenizer.hasMoreElements()) {
				String token = (String) tokenizer.nextElement();
				if(token.length() <= 3) {
					continue;
				}
				
				int count = 1;
				if(trendsMap.containsKey(token)) {
					count = count + trendsMap.get(token);
				}
				
				trendsMap.put(token, count);
			}			
		}

		
		return trendsMap;
				
	}
}
