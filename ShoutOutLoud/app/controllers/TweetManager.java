package controllers;

import java.util.List;

import views.html.home;
import static play.data.Form.*;
import models.Constants;
import models.Tweet;
import models.service.SearchService;
import models.service.SearchServiceImpl;
import models.service.TweetService;
import models.service.TweetServiceImpl;
import play.Logger;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Controller class that manages the workflow related to creation or display of tweet
 * feed for the logged in user.
 * 
 * @author excelsior
 *
 */
public class TweetManager extends Controller {

	private static TweetService tweetService = new TweetServiceImpl();
	private static SearchService searchService = new SearchServiceImpl();
	
	/**
	 * Manages the creation of a single tweet.
	 * 
	 * @return
	 */
	public static Result compose()
	{
		DynamicForm dynamicForm = form().bindFromRequest();
		String tweet = dynamicForm.get(Constants.TWEET_CONTENT);
		String handle = session().get(Constants.LOGGED_USER);
		boolean isTweetAdded = tweetService.insertTweet(handle, tweet);
		if(isTweetAdded) {
			Logger.info("Tweet successfully added !!");
		}
		
		return redirect("/feed");
	}
	
	/**
	 * Manages the tweet feed of the current logged in user.
	 * 
	 * @return
	 */
	public static Result feed(Long maxId)
	{
		String handle = session().get(Constants.LOGGED_USER);
		List<Tweet> tweets = searchService.searchTweetsByHandle(handle, maxId);
		
		return ok(home.render(tweets));		
	}	
}