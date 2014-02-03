import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

import java.util.List;

import models.Trend;
import models.service.TrendService;
import models.service.TrendServiceImpl;

import org.junit.Test;

import play.Logger;


public class TrendServiceImplTest {
	private TrendService trendService = new TrendServiceImpl();
	
	@Test
	public void testGetTrends() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				List<Trend> trends = trendService.getTrends();
				Logger.info("Found " + trends.size() + " trends .." );
			}
		});
	}
}
