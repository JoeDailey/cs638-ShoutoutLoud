package controllers;

import models.service.LoginService;
import models.service.LoginServiceImpl;
import models.service.SearchService;
import models.service.SearchServiceImpl;
import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import views.html.landing;
import views.html.profile;
import models.Constants;
import models.Profile;
import models.Tweet;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;
import java.util.Map;

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

        if (authenticationSuccessful){
            Profile profile = searchService.searchProfileByHandle(handle);

            session(Constants.USER_EMAIL, profile.getEmail());
            session(Constants.USER_HANDLE, profile.getHandle());
            session(Constants.USER_FULL_NAME, profile.getFullName());
            session(Constants.USER_LOCATION, profile.getLocation());

            return redirect("/feed");
        }
        else {
        	flash("Please enter correct username/password !! ");
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
        
        for(Map.Entry<String, String> entry : dynamicForm.data().entrySet()) {
        	Logger.info("Key : " + entry.getKey() + ", Value : " + entry.getValue());
        }

        String email = dynamicForm.get(Constants.USER_EMAIL);
        String fullName = dynamicForm.get(Constants.USER_FULL_NAME);
        String handle = dynamicForm.get(Constants.USER_HANDLE);
        String location = "Madison";
        String password = dynamicForm.get(Constants.USER_PASSWORD);
        
        Logger.info("Handle : " + handle + ", Password : " + password);

        loginService.register(email, fullName, handle, location, password);

        session(Constants.USER_EMAIL, email);
        session(Constants.USER_HANDLE, handle);
        session(Constants.USER_FULL_NAME, fullName);
        session(Constants.USER_LOCATION, location);

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
}