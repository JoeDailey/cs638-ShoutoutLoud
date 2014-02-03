package models.service;

/**
 * Service class that exposes profile update related functions.
 * 
 * @author excelsior
 *
 */
public interface ProfileService {

	/**
	 * API hook to allow a 'source' user to start following a 'target' user.
	 * @param sourceHandle
	 * @param targetHandle
	 */
	public void follow(String sourceHandle, String targetHandle);
}
