package models;

/**
 * All the constants used for this application.
 * 
 * @author excelsior
 *
 */
public class Constants {

	// Number of entries to show/fetch by default.
	public static int NUM_ENTRIES_PER_PAGE = 20;
	
	// Database constants
	public static String DB_USER = "db_user";
	public static String DB_PWD = "db_user";
	public static String DB_NAME = "twitter_clone";
	
	// Database tables
	public static String USER_TBL = "profile";
	public static String TWEET_TBL = "tweet";
	public static String TREND_TBL = "trend";
	public static String FOLLOWERS_TBL = "follower";
	public static String FOLLOWING_TBL = "following";
	public static String USER_TO_TWEET_TBL = "user_to_tweet";
	public static String TREND_TO_TWEET_TBL = "trend_to_tweet";
}
