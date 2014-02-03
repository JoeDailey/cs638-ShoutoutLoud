package models.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;

import models.Constants;
import models.Profile;
import models.Tweet;
import models.utils.DBUtils;
import play.Logger;

public class TweetServiceImpl implements TweetService {

	private SearchService searchService = new SearchServiceImpl();
	
	@Override
	public boolean insertTweet(String handle, String tweet) {
		Connection dbConn = DBUtils.getDBConnection();
		boolean isDBOpSuccess = true;
		
		// Step1 - Insert the tweet into the tweet table.
		long tweetId = searchService.searchMaxTweetId() + 1;
		String tweetInsertSQL = "INSERT INTO " + Constants.TWEET_TBL + 
				"(id, content, create_time) VALUES (?, ?, now())";
		PreparedStatement preparedSql = null;
		try {
			preparedSql = dbConn.prepareStatement(tweetInsertSQL);
			preparedSql.setLong(1, tweetId);
			preparedSql.setString(2, tweet);
			preparedSql.executeUpdate();
		} catch (SQLException e) {
			isDBOpSuccess = false;
			Logger.error("Failed to insert tweet for handle : " + handle);
			e.printStackTrace();
		}
		
		// Step2 - Update the map for list of tweets for a user.
		long uid = searchService.searchUidForTwitterHandle(handle);
		String userToTweetUpdSQL = "INSERT INTO " + Constants.USER_TO_TWEET_TBL + 
				"(uid, tid) VALUES (?, ?)";
		preparedSql = null;
		try {
			preparedSql = dbConn.prepareStatement(userToTweetUpdSQL);
			preparedSql.setLong(1, uid);
			preparedSql.setLong(2, tweetId);
			preparedSql.executeUpdate();
		} catch (SQLException e) {
			isDBOpSuccess = false;
			Logger.error("Failed to insert tweet for handle : " + handle);
			e.printStackTrace();
		}
		
		DBUtils.cleanDBResources(dbConn, preparedSql);
		return isDBOpSuccess;
	}

	@Override
	public List<Tweet> getFeed(String handle, Long maxTid) {
		if(maxTid == null) {
			maxTid = Long.MAX_VALUE;
		}
		
		/**
		 * The set of tweets to show in the feed consist of the tweets made by the user and
		 * all the users to which this user is subscribed/following.
		 */
		List<Tweet> tweets = Lists.newArrayList();
		List<Tweet> selfTweets = searchService.searchTweetsByHandle(handle, maxTid);
		tweets.addAll(selfTweets);
		
		Connection dbConn = DBUtils.getDBConnection();

		long uid = searchService.searchUidForTwitterHandle(handle);
		
		List<Tweet> subscriberTweets = Lists.newArrayList();
		String tweetFetchSQL = " SELECT f.src_uid AS uid, t.id AS tid, t.content, t.create_time " +
			" FROM following f JOIN user_to_tweet u ON (f.src_uid = u.uid) " +
			" JOIN tweet t ON (u.tid = t.id) WHERE f.tgt_uid = ? AND t.id < ? LIMIT ? ";
		PreparedStatement preparedSql = null;
		try {
			preparedSql = dbConn.prepareStatement(tweetFetchSQL);
			preparedSql.setLong(1, uid);
			preparedSql.setLong(2, maxTid);
			preparedSql.setInt(3, Constants.NUM_ENTRIES_PER_PAGE);
			ResultSet results = preparedSql.executeQuery();
			
			while(results.next()) {
				long tweetId = results.getLong("tid");
				String content = results.getString("content");
				Date timestamp = results.getDate("create_time");
				long subUid = results.getLong("uid");
				
				Tweet tweet = new Tweet(tweetId, content, timestamp);
				Profile profile = searchService.searchProfileByUid(subUid);
				tweet.setTweetCreator(profile);
				subscriberTweets.add(tweet);
			}
		} catch (SQLException e) {
			Logger.error("Failed to load tweet for handle : " + handle);
			e.printStackTrace();
		}
		
		tweets.addAll(subscriberTweets);
		
		Collections.sort(tweets);
		Collections.reverse(tweets);
		
		DBUtils.cleanDBResources(dbConn, preparedSql);
		if(tweets.size() <= Constants.NUM_ENTRIES_PER_PAGE) {
			return tweets;
		}
		else{
			return tweets.subList(0, Constants.NUM_ENTRIES_PER_PAGE);			
		}

	}

}
