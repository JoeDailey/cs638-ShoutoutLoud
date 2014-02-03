package controllers;

import models.Constants;
import models.Profile;
import models.service.ProfileService;
import models.service.ProfileServiceImpl;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Controller class that is responsible for profile management
 * 
 * @author excelsior
 *
 */
public class ProfileManager extends Controller {

	private static ProfileService profileService = new ProfileServiceImpl();
	
	public static Result follow(String targetHandle)
	{
		System.out.println("target->"+targetHandle);
		String sourceHandle = session().get(Constants.USER_HANDLE);
		profileService.follow(sourceHandle, targetHandle);
		return ok("Follow relationship established ..");
	}
	
	public static Profile getCurrentSessionUser()
	{
		Profile me = new Profile(	Long.parseLong(session().get(Constants.USER_ID)),
				session().get(Constants.USER_FULL_NAME),
				session().get(Constants.USER_EMAIL),
				session().get(Constants.USER_HANDLE),
				session().get(Constants.USER_LOCATION));
		
		me.setNumTweets(Integer.parseInt(session().get(Constants.USER_NUM_TWEETS)));
		me.setNumFollowers(Integer.parseInt(session().get(Constants.USER_NUM_FOLLOWERS)));
		me.setNumFollowing(Integer.parseInt(session().get(Constants.USER_NUM_FOLLOWING)));
		return me;
		
	}
}
