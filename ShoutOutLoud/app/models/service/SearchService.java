package models.service;

import models.Profile;
import models.Tweet;

import java.util.List;

public interface SearchService {

	public List<Tweet> findTweetsByHandle(long tid, String handle);

	public List<Tweet> findTweetsByKeyword(String keyword);

	public List<Profile> searchFollowers(String handle);

	public List<Profile> searchFollowing(String handle);
}
