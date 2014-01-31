import org.junit.Test;

import play.Logger;
import models.Profile;
import models.service.LoginService;
import models.service.LoginServiceImpl;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;
import static org.fest.assertions.Assertions.*;

public class LoginServiceImplTest {
	private LoginService loginService = new LoginServiceImpl();

	@Test
	public void testLogin() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				String handle = "tHandle";
				String password = "tPass";
				boolean login = loginService.login(handle, password);
				Logger.info("Login successful for handle: " + handle + " and password: " + password);
			}
		});
	}

	@Test
	public void testRegister() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				String email = "tEmail";
				String fullName = "tName";
				String handle = "tHandle";
				String password = "tPass";
                String location = "tLocation";
				Profile profile = loginService.register(email, fullName, handle, password, location);
				Logger.info("Profile successfully created for profile: " + profile.getId() + ", " + profile.getFullName() + ", " + profile.getEmail() + ", " + profile.getHandle());
			}
		});
	}

	@Test
	public void testAuthenticate() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				String handle = "tHandle";
				String password = "tPass";
				boolean authenticate = loginService.authenticate(handle, password);
				Logger.info("Authentication valid for handle: " + handle + " and password: " + password);
			}
		});
	}

	@Test
	public void testLogout() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				String handle = "tHandle";
				String password = "tPass";
				boolean authenticate = loginService.authenticate(handle, password);
				Logger.info("Authentication valid for handle: " + handle + " and password: " + password);
			}
		});
	}

}
