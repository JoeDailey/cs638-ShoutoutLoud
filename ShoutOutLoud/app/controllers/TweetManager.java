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
		
		Profile me = ProfileManager.getCurrentSessionUser();
		session(Constants.USER_NUM_TWEETS, Integer.toString(me.getNumTweets() + 1));
		return redirect("/feed");
	}
	
	/**
	 * Manages the tweet feed of the current logged in user.
	 * 
	 * @return
	 */
	public static Result feed(Long maxId)
	{
		if(session().get(Constants.USER_HANDLE) == null)
            return redirect("/");
        else{
        	Profile user = ProfileManager.getCurrentSessionUser();
        	if(maxId==100000){

				List<Trend> trends = trendService.getTrends();
				String handle = session().get(Constants.USER_HANDLE);
				String following = searchService.searchForDataForFollowings(user.getHandle());
			
				System.out.println(following);
				List<Tweet> tweets = tweetService.getFeed(handle, maxId);
				
				return ok(home.render(tweets, trends, user, following));
			}else{
				String json = "[";
				List<Tweet> tweets = tweetService.getFeed(user.getHandle(), maxId);
				for(int i = 0; i < tweets.size(); i++){
					json += "{\"content\":\""+tweets.get(i).getContent()+"\", \"id\":\""+tweets.get(i).getId()+"\", \"timestamp\":\""+tweets.get(i).getTimestamp()+"\", \"user\":{ \"handle\":\""+tweets.get(i).getTweetCreator().getHandle()+"\", \"fullName\":\""+tweets.get(i).getTweetCreator().getFullName()+"\", \"id\":\""+tweets.get(i).getTweetCreator().getId()+"\" }}";
					if(i!=tweets.size()-1)
				 		json+=",";
				}
				return ok(json+"]");
			}
		}
	}	
}
