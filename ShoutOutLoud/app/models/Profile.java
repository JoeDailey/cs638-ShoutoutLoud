package models;



public class Profile {
	private long id;
	private String fullName;
	private String email;
	private String handle;
	private String location;
	
	// Summary stats about the profile. Required for the home page summary section.
	private int numTweets;
	private int numFollowers;
	private int numFollowing;
	
	public Profile(long id, String fullName, String email, String handle, String location) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.email = email;
		this.handle = handle;
		this.location = location;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Profile [id=").append(id).append(", fullName=")
				.append(fullName).append(", handle=").append(handle)
				.append("]");
		return builder.toString();
	}



	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getHandle() {
		return handle;
	}
	public void setHandle(String handle) {
		this.handle = handle;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}

	public int getNumTweets() {
		return numTweets;
	}

	public void setNumTweets(int numTweets) {
		this.numTweets = numTweets;
	}

	public int getNumFollowers() {
		return numFollowers;
	}

	public void setNumFollowers(int numFollowers) {
		this.numFollowers = numFollowers;
	}

	public int getNumFollowing() {
		return numFollowing;
	}

	public void setNumFollowing(int numFollowing) {
		this.numFollowing = numFollowing;
	}
	
}
