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
	public static int NUM_TRENDS_TO_SHOW = 10;
	
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
	
	// SESSION CONSTANTS
	public static final String USER_HANDLE = "handle";
	public static final String USER_PASSWORD = "password";
	public static final String USER_EMAIL = "email";
	public static final String USER_FULL_NAME = "full_name";
	public static final String USER_LOCATION = "location";
	
	public static final String LOGGED_USER = "logged_user";
	public static final String LOGGED_PROFILE = "logged_profile";
	
	// FORM CONSTANTS
	public static final String TWEET_CONTENT = "content";
    public static final String USER_HANDLE = "handle";
    public static final String USER_EMAIL = "email";
    public static final String USER_FULL_NAME = "full_name";
    public static final String USER_LOCATION = "location";
    public static final String USER_PASSWORD = "password";
}
