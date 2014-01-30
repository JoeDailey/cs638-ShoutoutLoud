package models.service;

import models.Profile;

public interface LoginService {
	
	public void login(String handle, String password);

	public Profile register(String email, String handle, String password);

	public boolean authenticate(String handle, String password);

	public void logout(Profile profile);
}
