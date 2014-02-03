package controllers;

import static play.data.Form.form;
import models.Constants;
import models.Profile;
import models.service.LoginService;
import models.service.LoginServiceImpl;
import models.service.SearchService;
import models.service.SearchServiceImpl;
import play.Logger;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.landing;

public class Login extends Controller {

    private static LoginService loginService = new LoginServiceImpl();
    private static SearchService searchService = new SearchServiceImpl();

    /**
     * Manages login screen for user.
     *
     * @return
     */
    public static Result index(){
        return ok(landing.render());
    }

    /**
     * Verifies the user handle and password match
     *
     * @return
     */
    public static Result login() {
        DynamicForm dynamicForm = form().bindFromRequest();
        String handle = dynamicForm.get(Constants.USER_HANDLE);
        String password = dynamicForm.get(Constants.USER_PASSWORD);

        boolean authenticationSuccessful = loginService.login(handle, password);
        Logger.info("Auth status : " + authenticationSuccessful + " for handle " + handle + ", password " + password);

        if (authenticationSuccessful){
            Profile profile = searchService.searchProfileByHandle(handle);
            setupSessionVars(profile);

            return redirect("/feed");
        }
        else {
        	return index();
        }
    }

    /**
     * Registers a new user
     *
     * @return
     */
    public static Result register(){
        DynamicForm dynamicForm = form().bindFromRequest();

        String email = dynamicForm.get(Constants.USER_EMAIL);
        String fullName = dynamicForm.get(Constants.USER_FULL_NAME);
        String handle = dynamicForm.get(Constants.USER_HANDLE);
        String location = "Madison";
        String password = dynamicForm.get(Constants.USER_PASSWORD);
        
        Logger.info("Handle : " + handle + ", Password : " + password);

        Profile profile = loginService.register(email, fullName, handle, password, location);
        setupSessionVars(profile);

        return redirect("/feed");
    }

    /**
     * Logs out current user
     *
     * @return
     */
    public static Result logout(){
        session().clear();
        return ok(landing.render());
    }
    
    
    private static void setupSessionVars(Profile profile)
    {
        session(Constants.USER_ID, Long.toString(profile.getId()));
    	session(Constants.USER_EMAIL, profile.getEmail());
        session(Constants.USER_HANDLE, profile.getHandle());
        session(Constants.USER_FULL_NAME, profile.getFullName());
        session(Constants.USER_LOCATION, profile.getLocation());
        
        session(Constants.USER_NUM_TWEETS, Integer.toString(profile.getNumTweets()));
        session(Constants.USER_NUM_FOLLOWERS, Integer.toString(profile.getNumFollowers()));
        session(Constants.USER_NUM_FOLLOWING, Integer.toString(profile.getNumFollowing()));
    }
}