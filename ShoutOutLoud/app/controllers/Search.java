package controllers;

import java.util.List;

import views.html.profile;
import views.html.search;
import models.Constants;
import models.Profile;
import models.Tweet;
import models.Trend;
import models.service.SearchService;
import models.service.SearchServiceImpl;
import models.service.TrendService;
import models.service.TrendServiceImpl;
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
	private static TrendService trendService = new TrendServiceImpl();
	
	/**
	 * Controller route for searching a user profile.
	 * 
	 * 
	 * @return
	 */
	public static Result searchByProfile(String handle, long lastId)
	{
		if(session().get(Constants.USER_HANDLE) == null)
            return redirect("/");
        else{
			Profile me = new Profile(	Long.parseLong(session().get(Constants.USER_ID)),
										session().get(Constants.USER_FULL_NAME),
										session().get(Constants.USER_EMAIL),
										session().get(Constants.USER_HANDLE),
										session().get(Constants.USER_LOCATION));
			
        	if(lastId==100000){
        		Profile user = searchService.searchProfileByHandle(handle);
				List<Tweet> tweets = searchService.searchTweetsByHandle(handle, 1000000000L);
				String following = searchService.searchForDataForFollowings(me.getHandle());
				
				return ok(profile.render(user, me, tweets, following));
			}else{
				String json = "[";
				List<Tweet> tweets = searchService.searchTweetsByHandle(handle, lastId);
				for(int i = 0; i < tweets.size(); i++){
					json += "{\"content\":\""+tweets.get(i).getContent()+"\", \"id\":\""+tweets.get(i).getId()+"\", \"timestamp\":\""+tweets.get(i).getTimestamp()+"\", \"user\":{ \"handle\":\""+tweets.get(i).getTweetCreator().getHandle()+"\", \"fullName\":\""+tweets.get(i).getTweetCreator().getFullName()+"\", \"id\":\""+tweets.get(i).getTweetCreator().getId()+"\" }}";
					if(i!=tweets.size()-1)
				 		json+=",";
				}
				return ok(json+"]");	
			}
		}
	}
	
	/**
	* Controller route for getting data by user profile
	*
	* @return
	*/
	public static Result searchForDataByProfile(String handle)
	{
		Profile user = searchService.searchProfileByHandle(handle);
		List<Tweet> tweets = searchService.searchTweetsByHandle(handle, 10000000L);
		//holy shit javasting to json is messy
		String json = "{\"fullName\":\""+user.getFullName()+"\",\"handle\":\""+user.getHandle()+"\",\"numTweets\":"+user.getNumTweets()+",\"numFollowers\":"+user.getNumFollowers()+",\"numFollowing\":"+user.getNumFollowing()+",\"tweets\":[";
		for(int i = 0; i < tweets.size(); i++){
			Tweet t = tweets.get(i);
			json+="{\"content\":\""+t.getContent()+"\",\"timestamp\":\""+t.getTimestamp()+"\",\"id\":\""+t.getId()+"\"}";
			if(i!=tweets.size()-1){
				json+=",";
			}
		}
		json += "]}";
		//enough of that shit
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
		if(session().get(Constants.USER_HANDLE) == null)
            return redirect("/");
        else{
			Profile me = new Profile(	Long.parseLong(session().get(Constants.USER_ID)),
										session().get(Constants.USER_FULL_NAME),
										session().get(Constants.USER_EMAIL),
										session().get(Constants.USER_HANDLE),
										session().get(Constants.USER_LOCATION));
        	if(lastId==100000){
				System.out.println(lastId);
				List<Tweet> tweets = searchService.searchTweetsByKeyword(keyword, lastId);
				String following = searchService.searchForDataForFollowings(me.getHandle());
				List<Trend> trends = trendService.getTrends();

				return ok(search.render(tweets, trends, me, following));
			}else{
				String json = "[";
				List<Tweet> tweets = searchService.searchTweetsByKeyword(keyword, lastId);
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
