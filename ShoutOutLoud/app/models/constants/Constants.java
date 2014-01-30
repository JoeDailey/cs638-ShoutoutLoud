package models.constants;

public final class Constants {

	// How many tweets will be displayed on a page
	public static final int TOPN = 20;

	// Class should not be able to be constructed
	private Constants(){
		throw new AssertionError()
	}
}
