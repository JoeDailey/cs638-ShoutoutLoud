import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

import java.util.List;
import java.util.UUID;

import org.junit.Test;

import play.Logger;
import models.Tweet;
import models.service.TweetService;
import models.service.TweetServiceImpl;


public class TweetServiceImplTest {
	private TweetService tweetService = new TweetServiceImpl();
	
	@Test
	public void testInsertTweet() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				String randomStr = UUID.randomUUID().toString();
				boolean isDone = tweetService.insertTweet("test", "tweet from test class " + randomStr);
				Logger.info("Is tweet inserted ? " + isDone);
				
				assertThat(isDone).isEqualTo(true);
			}
		});
	}
	
}
