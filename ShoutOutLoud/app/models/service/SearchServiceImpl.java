package models.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;

import play.Logger;
import models.Constants;
import models.Profile;
import models.Tweet;
import models.utils.DBUtils;

public class SearchServiceImpl implements SearchService {

	@Override
	public List<Tweet> searchTweetsByHandle(String handle, Long maxTid) {
		if(maxTid == null) {
			maxTid = Long.MAX_VALUE;
		}
		
		Connection dbConn = DBUtils.getDBConnection();
		List<Tweet> tweets = Lists.newArrayList();
		long uid = searchUidForTwitterHandle(handle);
		
		String tweetFetchSQL = "SELECT t.id AS tid, t.content, t.create_time FROM " + 
				Constants.USER_TO_TWEET_TBL + " u JOIN " + Constants.TWEET_TBL + 
				" t ON(u.tid = t.id) WHERE u.uid = ? AND t.id < ? LIMIT ?";
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
				
				Tweet tweet = new Tweet(tweetId, content, timestamp);
				Profile profile = searchProfileByHandle(handle);
				tweet.setTweetCreator(profile);
				tweets.add(tweet);
			}
		} catch (SQLException e) {
			Logger.error("Failed to load tweet for handle : " + handle);
			e.printStackTrace();
		}		
		
		DBUtils.cleanDBResources(dbConn, preparedSql);
		return tweets;	
	}

	@Override
	public List<Tweet> searchTweetsByKeyword(String keyword, Long maxTid) {
		if(maxTid == null) {
			maxTid = Long.MAX_VALUE;
		}
		
		Connection dbConn = DBUtils.getDBConnection();
		List<Tweet> tweets = Lists.newArrayList();
		
		String tweetFetchSQL = "SELECT p.id AS uid, t.id AS tid, t.content, t.create_time FROM tweet t"
				+ " JOIN user_to_tweet u ON (t.id = u.tid) JOIN profile p ON (p.id = u.uid) "
				+ " WHERE t.content LIKE '%" + keyword +"%' AND t.id < ? LIMIT ?";
		PreparedStatement preparedSql = null;
		try {
			preparedSql = dbConn.prepareStatement(tweetFetchSQL);
			preparedSql.setLong(1, maxTid);
			preparedSql.setInt(2, Constants.NUM_ENTRIES_PER_PAGE);
			ResultSet results = preparedSql.executeQuery();
			
			while(results.next()) {
				long tweetId = results.getLong("tid");
				String content = results.getString("content");
				Date timestamp = results.getDate("create_time");
				Tweet tweet = new Tweet(tweetId, content, timestamp);
				
				long uid = results.getLong("uid");
				Profile profile = searchProfileByUid(uid);
				tweet.setTweetCreator(profile);
				tweets.add(tweet);
			}
		} catch (SQLException e) {
			Logger.error("Failed to load tweet for keyword : " + keyword);
			e.printStackTrace();
		}		
		
		DBUtils.cleanDBResources(dbConn, preparedSql);
		return tweets;	
	}

	@Override
	public List<Profile> searchFollowers(String handle) {
		Connection dbConn = DBUtils.getDBConnection();

		long uid = searchUidForTwitterHandle(handle);
		String sql = "SELECT tgt_uid FROM " + Constants.FOLLOWERS_TBL + " WHERE src_uid = ?";
		PreparedStatement preparedSql = null;
		List<Long> followerIds = Lists.newArrayList();
		try {
			preparedSql = dbConn.prepareStatement(sql);
			preparedSql.setLong(1, uid);
			ResultSet results = preparedSql.executeQuery();
			
			while(results.next()) {
				followerIds.add(results.getLong("src_uid"));
			}
		} catch (SQLException e) {
			Logger.error("Failed to find followers for handle : " + handle);
			e.printStackTrace();
		}
		
		List<Profile> followers = Lists.newArrayList();
		for(long tgtUid : followerIds) {
			Profile profile = searchProfileByUid(tgtUid);
			followers.add(profile);
		}
		
		DBUtils.cleanDBResources(dbConn, preparedSql);
		return followers;
	}
	public String searchForDataForFollowings(String handle){
		List<Profile> followings = searchFollowing(handle);
		String json = "[";

		// for(int i = 0; i < followings.size(); i++){
		// 	json+="{\"id\":\""+followings.get(i).getId()+"\", \"fullName\":\""+followings.get(i).getFullName()+"\", \"handle\":\""+followings.get(i).getHandle()+"\"}";
		// 	if(i!=followings.size()-1)
		// 		json+=",";
		// }

		for(int i = 0; i < followings.size(); i++){
			json += "\""+followings.get(i).getHandle()+"\"";
			if(i!=followings.size()-1)
		 		json+=",";
		}

		json+="]";
		System.out.println(json);
		return json;
	}

	@Override
	public List<Profile> searchFollowing(String handle) {
		Connection dbConn = DBUtils.getDBConnection();

		long uid = searchUidForTwitterHandle(handle);
		String sql = "SELECT tgt_uid FROM " + Constants.FOLLOWING_TBL + " WHERE src_uid = ?";
		PreparedStatement preparedSql = null;
		List<Long> followingIds = Lists.newArrayList();
		try {
			preparedSql = dbConn.prepareStatement(sql);
			preparedSql.setLong(1, uid);
			ResultSet results = preparedSql.executeQuery();
			
			while(results.next()) {
				followingIds.add(results.getLong("tgt_uid"));
			}
		} catch (SQLException e) {
			Logger.error("Failed to find following for handle : " + handle);
			e.printStackTrace();
		}

		List<Profile> following = Lists.newArrayList();
		for(long tgtUid : followingIds) {
			Profile profile = searchProfileByUid(tgtUid);
			following.add(profile);
		}
		
		DBUtils.cleanDBResources(dbConn, preparedSql);
		return following;
	}

	@Override
	public long searchUidForTwitterHandle(String handle) {
		long uid = -1;
		Connection dbConn = DBUtils.getDBConnection();

		String searchSQL = "SELECT id FROM " + Constants.USER_TBL + " WHERE handle = ?";
		PreparedStatement preparedSql = null;
		try {
			preparedSql = dbConn.prepareStatement(searchSQL);
			preparedSql.setString(1, handle);
			ResultSet results = preparedSql.executeQuery();
			
			while(results.next()) {
				uid = results.getLong("id");
			}
		} catch (SQLException e) {
			Logger.error("Failed to finduser id for handle : " + handle);
			e.printStackTrace();
		}
		
		DBUtils.cleanDBResources(dbConn, preparedSql);
		return uid;
	}

	@Override
	public long searchMaxTweetId() {
		long maxTweetId = -1;
		Connection dbConn = DBUtils.getDBConnection();

		String searchSQL = "SELECT MAX(id) AS id FROM " + Constants.TWEET_TBL;
		PreparedStatement preparedSql = null;
		try {
			preparedSql = dbConn.prepareStatement(searchSQL);
			ResultSet results = preparedSql.executeQuery();
			
			while(results.next()) {
				maxTweetId = results.getLong("id");
			}
		} catch (SQLException e) {
			Logger.error("Failed to find max tweet id ..");
			e.printStackTrace();
		}
		
		DBUtils.cleanDBResources(dbConn, preparedSql);
		return maxTweetId;
	}

	@Override
	public long searchMaxUserId() {
		long maxUid = -1;
		Connection dbConn = DBUtils.getDBConnection();

		String searchSQL = "SELECT MAX(id) AS id FROM " + Constants.USER_TBL;
		PreparedStatement preparedSql = null;
		try {
			preparedSql = dbConn.prepareStatement(searchSQL);
			ResultSet results = preparedSql.executeQuery();
			
			while(results.next()) {
				maxUid = results.getLong("id");
			}
		} catch (SQLException e) {
			Logger.error("Failed to find max user id ..");
			e.printStackTrace();
		}
		
		DBUtils.cleanDBResources(dbConn, preparedSql);
		return maxUid;
	}

	@Override
	public Profile searchProfileByHandle(String handle) {
		long uid = searchUidForTwitterHandle(handle);
		return searchProfileByUid(uid);

	}

	@Override
	public Profile searchProfileByUid(long uid) {
		Profile profile = null;
		Connection dbConn = DBUtils.getDBConnection();

		String profileSQL = "SELECT p.id AS uid, p.handle, p.email, p.full_name, p.location, COUNT(t.tid) AS tweet_cnt, "
				+ " COUNT(DISTINCT(f1.tgt_uid)) AS follower_cnt, COUNT(DISTINCT(f2.tgt_uid)) AS following_cnt"
				+ " FROM profile p LEFT OUTER JOIN user_to_tweet t ON (p.id = t.uid) LEFT OUTER JOIN follower f1 ON (p.id = f1.src_uid)"
				+ " LEFT OUTER JOIN following f2 ON (f2.src_uid = p.id) WHERE p.id = ? GROUP by p.id, p.handle, p.email,"
				+ " p.full_name, p.location;";
		PreparedStatement preparedSql = null;
		try {
			preparedSql = dbConn.prepareStatement(profileSQL);
			preparedSql.setLong(1, uid);
			ResultSet results = preparedSql.executeQuery();
			
			while(results.next()) {
				long userId = results.getLong("uid");
				String fullName = results.getString("full_name");
				String location = results.getString("location");
				String handle = results.getString("handle");
				String email = results.getString("email");
				
				int tweetCnt = results.getInt("tweet_cnt");
				int followers = results.getInt("follower_cnt");
				int following = results.getInt("following_cnt");
				
				profile = new Profile(userId, fullName, email, handle, location);
				profile.setNumTweets(tweetCnt);
				profile.setNumFollowers(followers);
				profile.setNumFollowing(following);
			}
		} catch (SQLException e) {
			Logger.error("Failed to search profile by user id : " + uid);
			e.printStackTrace();
		}
		
		DBUtils.cleanDBResources(dbConn, preparedSql);		
		return profile;
	}

}
