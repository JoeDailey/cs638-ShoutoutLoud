package controllers;

import models.Constants;
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
}
