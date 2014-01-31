package models.service;

import java.util.List;

import models.Trend;

/**
 * Service class that exposes methods to generate the trends from the tweet data.
 * @author excelsior
 *
 */
public interface TrendService {

	/**
	 * Generates trends from the tweet data.
	 * @return
	 */
	public List<String> getTrends();
	
	/**
	 * Inserts the top trends in database after a batch job run.
	 */
	public void insertTrend(Trend trend);
	
	/**
	 * Deletes the old trends table before the bacth job invocation, so that it can be
	 * populated with new trend data.
	 */
	public void deleteStaleTrends();
}
