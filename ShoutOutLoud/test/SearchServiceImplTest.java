import java.util.List;

import org.junit.Test;

import play.Logger;
import models.Profile;
import models.Tweet;
import models.service.SearchService;
import models.service.SearchServiceImpl;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;
import static org.fest.assertions.Assertions.*;

/**
 * Test methods for SearchService API class.
 * 
 * @author excelsior
 *
 */
public class SearchServiceImplTest {
	private SearchService searchService = new SearchServiceImpl();
	
	@Test
	public void testSearchTweetsByHandle() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				String handle = "test";
				List<Tweet> tweets = searchService.searchTweetsByHandle(handle, null);
				Logger.info("Found " + tweets.size() + " tweets for user " + handle);
				for(Tweet t : tweets) {
					Logger.info(t.toString());
				}
			}
		});
	}
	
	@Test
	public void testSearchTweetsByKeyword() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				String keyword = "#test";
				List<Tweet> tweets = searchService.searchTweetsByKeyword(keyword, null);
				Logger.info("Found " + tweets.size() + " tweets for keyword " + keyword);
				for(Tweet t : tweets) {
					Logger.info(t.toString());
				}
			}
		});
	}
	
	@Test
	public void testSearchFollowers() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				String handle = "test";
				List<Profile> followers = searchService.searchFollowers("test");
				Logger.info("Found " + followers.size() + " followers for user : " + handle);
				for(Profile p : followers) {
					Logger.info(p.toString());
				}
			}
		});
	}
	
	@Test
	public void testSearchFollowing() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				String handle = "test";
				List<Profile> following = searchService.searchFollowers(handle);
				Logger.info("Found " + following.size() + " following for handle : " + handle);
				for(Profile p : following) {
					Logger.info(p.toString());
				}
			}
		});
	}
	
	@Test
	public void testSearchProfileByUid() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				Profile profile = searchService.searchProfileByHandle("test");
				Logger.info("Profile found : " + profile.getFullName());
			}
		});
	}
	
	@Test
	public void testSearchProfileByHandle() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				Profile profile = searchService.searchProfileByUid(1);
				Logger.info("Profile found : " + profile.getFullName());
			}
		});
	}
	
	@Test
	public void testGetUidForTwitterHandle() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				long uid = searchService.searchUidForTwitterHandle("test");
				Logger.info("Uid found " + uid);
				
				assertThat(uid).isEqualTo(1);
			}
		});
	}
	
	@Test
	public void testMaxUid() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				long uid = searchService.searchMaxUserId();
				Logger.info("Max uid found " + uid);
			}
		});
	}
	
	@Test
	public void testMaxTweetId() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				long tid = searchService.searchMaxTweetId();
				Logger.info("Max tid found " + tid);
			}
		});
	}
}
