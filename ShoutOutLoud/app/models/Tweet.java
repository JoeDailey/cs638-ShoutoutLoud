package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;
import java.sql.Timestamp

@Entity
public class Tweet extends Model {

	@Id
	public long id;

	@Constraints.Required
	public String content;

	@Constraints.Required
	public Timestamp timestamp;

public static Finder<Long, Tweet> find = new Finder(Long.class, Tweet.class);
}
