package models.service;

import models.Profile;
import models.Tweet;

import java.util.List;

public interface DBService {

	public boolean insertTweet(String handle, Tweet tweet);

	public List<Tweet> fetchTweets(long id, String handle);

	public Profile createProfile(String email, String handle, String password); 
}
