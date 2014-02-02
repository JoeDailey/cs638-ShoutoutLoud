package controllers;

import models.service.LoginServiceImpl;
import models.service.SearchServiceImpl;
import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;

import views.html.landing;
import views.html.profile;

import models.Constants;
import models.Tweet;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

public class Login extends Controller {

    public static LoginServiceImpl loginService = new LoginServiceImpl();
    public static SearchServiceImpl searchService = new SearchServiceImpl();

    /**
     * Manages login screen for user.
     *
     * @return
     */
    public static Result login(){
        return ok(landing.render());
    }

    /**
     * Verifies the user handle and password match
     *
     * @return
     */
    public static Result authenticate() {
        DynamicForm dynamicForm = form().bindFromRequest();
        String handle = dynamicForm.get(Constants.USER_HANDLE);
        String password = dynamicform.get(USER_PASSWORD);

        boolean authenticationSuccessful = loginService.login(handle, password);

        if (authenticationSuccessful){
            Profile profile = searchService.searchProfileByHandle(handle);

            session(Constants.USER_EMAIL, profile.getEmail());
            session(Constants.USER_HANDLE, profile.getHandle());
            session(Constants.USER_FULL_NAME, profile.getFullName());
            session(Constants.USER_LOCATION, profile.getLocation());
            session(Constants.USER_PASSWORD, profile.getPassword());

            return ok("Authentication Successful!");
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
        String full_name = dynamicForm.get(Constants.USER_FULL_NAME);
        String handle = dynamicForm.get(Constants.USER_HANDLE);
        String location = dynamicForm.get(Constants.USER_LOCATION);
        String password = dynamicForm.get(Constants.USER_PASSWORD);

        loginService.register(email, full_name, handle, location, password);

        session(Constants.USER_EMAIL, profile.getEmail());
        session(Constants.USER_HANDLE, profile.getHandle());
        session(Constants.USER_FULL_NAME, profile.getFullName());
        session(Constants.USER_LOCATION, profile.getLocation());
        session(Constants.USER_PASSWORD, profile.getPassword());

        List<Tweet> tweets = searchService.searchTweetsByHandle(handle, maxId);

        return ok(profile.render(tweets));

    }

    /**
     * Logs out current user
     *
     * @return
     */
    public static Result logout(){
        session().clear();
        flash("success", "You've been logged out");
        return ok(landing.render());
    }
}
