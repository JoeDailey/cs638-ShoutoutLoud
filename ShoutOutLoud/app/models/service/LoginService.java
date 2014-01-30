package models.service;

import models.Profile;

/**
 * Service class that exposes all methods related to the login functionality of the
 * application.
 * @author excelsior
 *
 */
public interface LoginService {
	
	public void login(String handle, String password);

	public Profile register(String email, String handle, String password);

	public boolean authenticate(String handle, String password);

	public void logout(Profile profile);
}
