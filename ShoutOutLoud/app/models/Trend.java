package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
public class Trend extends Model {

	@Id
	public long id;

	@Constraints.Required
	public String keyword;

	@Constraints.Required
	public boolean isHashtag;

public static Finder<Long, Trend> find = new Finder(Long.class, Trend.class);
}
