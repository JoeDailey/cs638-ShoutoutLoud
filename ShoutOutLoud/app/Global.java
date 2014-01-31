import models.daemon.TrendGenerator;
import play.Application;
import play.GlobalSettings;
import play.Logger;

/**
 * Bootstraps the application with startup settings.
 * 
 * In our case, generates the trends for the twitter clone application.
 * 
 * @author excelsior
 *
 */
public class Global extends GlobalSettings{
	@Override
	  public void onStart(Application app) {
		Logger.info("Generating trends for twitter application ..");
		//TrendGenerator.generateTrends();
	  }  

}