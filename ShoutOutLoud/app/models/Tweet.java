package models;

import java.util.Date;
public class Tweet{

	private long id;
	private String content;
	private Date timestamp;
	
	// Who created this tweet ? Required to show full name and handle as part of the
	// tweet on the feeds page.
	private Profile tweetCreator;
	
	public Tweet(long id, String content, Date timestamp) {
		super();
		this.id = id;
		this.content = content;
		this.timestamp = timestamp;
	}
	
	public Tweet(long id, String content) {
		super();
		this.id = id;
		this.content = content;
		this.timestamp = new Date();
	}
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Tweet [id=").append(id).append(", content=")
				.append(content).append(", timestamp=").append(timestamp)
				.append("]");
		return builder.toString();
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Profile getTweetCreator() {
		return tweetCreator;
	}

	public void setTweetCreator(Profile tweetCreator) {
		this.tweetCreator = tweetCreator;
	}
}
