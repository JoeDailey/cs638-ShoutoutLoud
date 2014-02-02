package models.service;

import java.util.List;

import models.Tweet;


/**
 * Service class that exposes all methods related to managing the tweets.
 * @author excelsior
 *
 */
public interface TweetService {

	/**
	 * Inserts a tweet from a user into the database.
	 * @param handle	- twitter handle id
	 * @param tweet		- contents of the tweet
	 * @return	Boolean - whether insertion succeeded ?
	 */
	public boolean insertTweet(String handle, String tweet);
	
	/**
	 * Returns feed for the current logged in user.
	 * 
	 * @param maxTid
	 * @return
	 */
	public List<Tweet> getFeed(String handle, Long maxTid);

}
