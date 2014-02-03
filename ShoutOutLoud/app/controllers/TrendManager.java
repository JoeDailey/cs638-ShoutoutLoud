package controllers;

import models.daemon.TrendGenerator;
import play.mvc.Controller;
import play.mvc.Result;

public class TrendManager extends Controller {

	public static Result trends()
	{
		TrendGenerator.generateTrends();
		return ok("Trend generation complete ..");
	}
}
