package controllers;

import java.util.List;

import views.html.profile;
import views.html.search;
import models.Profile;
import models.Tweet;
import models.service.SearchService;
import models.service.SearchServiceImpl;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Controller class to provide the search functionality.
 * 
 * @author excelsior
 *
 */
public class Search extends Controller {

	private static SearchService searchService = new SearchServiceImpl();
	
	/**
	 * Controller route for searching a user profile.
	 * 
	 * 
	 * @return
	 */
	public static Result searchByProfile(String handle)
	{
		Profile user = searchService.searchProfileByHandle(handle);
		
		return ok(profile.render(user));
	}
	
	/**
	 * Controller route for searching a keyword in tweets.
	 * 
	 * This can be used for searching a simple string, hashtag or a trend.
	 * @return
	 */
	public static Result searchByKeyword(String keyword, Long lastId)
	{
		List<Tweet> tweets = searchService.searchTweetsByKeyword(keyword, lastId);
		
		return ok(search.render(tweets));
	}	
}
