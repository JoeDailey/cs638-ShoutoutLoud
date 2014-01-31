import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;
import models.daemon.TrendGenerator;

import org.junit.Test;

import play.Logger;


public class TrendGeneratorTest {
	@Test
	public void testGenerateTrends() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				Logger.info("Generating trends ..");
				TrendGenerator.generateTrends();
				Logger.info("Successfully generated trends ..");
			}
		});
	}
}
