package models.service;

import models.Profile;

/**
 * Service class that exposes all methods related to the login functionality of the
 * application.
 * @author excelsior
 *
 */
public interface LoginService {
	
	public boolean login(String handle, String password);

	public Profile register(String email, String full_name, String handle, String password, String location);

	public boolean authenticate(String handle, String password);
}
