package controllers;

import java.util.List;

import views.html.profile;
import views.html.search;
import models.Constants;
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
		Profile me = new Profile(	0,
									session().get(Constants.USER_FULL_NAME),
									session().get(Constants.USER_EMAIL),
									session().get(Constants.USER_HANDLE),
									session().get(Constants.USER_LOCATION));
		return ok(profile.render(user, me));
	}
	
	/**
	* Controller route for getting data by user profile
	*
	* @return
	*/
	public static Result searchForDataByProfile(String handle)
	{
		Profile user = searchService.searchProfileByHandle(handle);
		String json = "{\"fullName\":\""+user.getFullName()+"\",\"handle\":\""+user.getHandle()+"\",\"numTweets\":"+user.getNumTweets()+",\"numFollowers\":"+user.getNumFollowers()+",\"numFollowing\":"+user.getNumFollowing()+"}";
		return ok(json);
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
