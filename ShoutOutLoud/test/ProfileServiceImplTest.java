import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;
import models.service.ProfileService;
import models.service.ProfileServiceImpl;

import org.junit.Test;

import play.Logger;


public class ProfileServiceImplTest {
	private ProfileService profileService = new ProfileServiceImpl();
	
	@Test
	public void testFollow() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				String srcHandle = "tHandle";
				String tgtHandle = "shout";
				profileService.follow(srcHandle, tgtHandle);
				Logger.info("Follow relationship established between " + srcHandle + ", " + tgtHandle);
			}
		});
	}
}
