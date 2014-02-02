package models.service;

import models.Profile;
import models.Tweet;

import java.util.List;

/**
 * Service class that exposes all the search APIs for fetching tweet , profile data
 * from the database.
 * 
 * @author excelsior
 *
 */
public interface SearchService {

	/**
	 * Retrieve tweets given the handle of the user
	 * @param maxTid
	 * @param handle
	 * @return
	 */
	public List<Tweet> searchTweetsByHandle(String handle, Long maxTid);

	/**
	 * Retrieve tweets given the search keyword
	 * @param keyword
	 * @return
	 */
	public List<Tweet> searchTweetsByKeyword(String keyword, Long maxTid);

	/**
	 * Get the list of followers for a user
	 * @param handle
	 * @return
	 */
	public List<Profile> searchFollowers(String handle);

	/**
	 * Get the list of users a user is following.
	 * @param handle
	 * @return
	 */
	public List<Profile> searchFollowing(String handle);
	
	/**
	 * Get a fully decorated profile object for a user given his/her twitter handle.
	 * @param handle
	 * @return
	 */
	public Profile searchProfileByHandle(String handle);
	
	/**
	 * Get a fully decorated profile object for a user given his/her user id in database.
	 * @param uid
	 * @return
	 */
	public Profile searchProfileByUid(long uid);
	
	/**
	 * Return the database user id for a given twitter handle.
	 * @param handle
	 * @return
	 */
	public long searchUidForTwitterHandle(String handle);
	
	/**
	 * Return the max tweet id in the database as of now. This can be used to assign
	 * the tweet id for the next tweet.
	 * @return
	 */
	public long searchMaxTweetId();
	
	/**
	 * Return the max user id in the database as of now. This can be used to assign
	 * the user id for the next user.
	 * @return
	 */
	public long searchMaxUserId();
}
