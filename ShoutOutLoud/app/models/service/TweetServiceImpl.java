package models.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import models.Constants;
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

}
