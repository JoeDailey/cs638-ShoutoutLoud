package controllers;

import static play.data.Form.form;

import java.util.List;

import models.Constants;
import models.Tweet;
import models.Trend;
import models.Profile;
import models.service.SearchService;
import models.service.SearchServiceImpl;
import models.service.TweetService;
import models.service.TweetServiceImpl;
import models.service.TrendService;
import models.service.TrendServiceImpl;
import play.Logger;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.home;

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
	private static TrendService trendService = new TrendServiceImpl();
	
	/**
	 * Manages the creation of a single tweet.
	 * 
	 * @return
	 */
	public static Result compose()
	{
		DynamicForm dynamicForm = form().bindFromRequest();
		String tweet = dynamicForm.get(Constants.TWEET_CONTENT);
		String handle = session().get(Constants.USER_HANDLE);
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
		Profile user = new Profile(	Long.parseLong(session().get(Constants.USER_ID)),
									session().get(Constants.USER_FULL_NAME),
									session().get(Constants.USER_EMAIL),
									session().get(Constants.USER_HANDLE),
									session().get(Constants.USER_LOCATION));

		List<Trend> trends = trendService.getTrends();
		
		String handle = session().get(Constants.USER_HANDLE);
		List<Tweet> tweets = tweetService.getFeed(handle, maxId);
		
		return ok(home.render(tweets, trends, user));
	}	
}
