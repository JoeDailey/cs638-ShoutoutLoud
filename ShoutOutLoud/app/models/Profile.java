package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
public class Task extends Model {

	@Id
	public long id;

	@Constraints.Required
	public String full_name;

	@Constraints.Required
	public String email;

	@Constraints.Required
	public String handle;

	@Constraints.Required
	public String password;

	@Constraints.Required
	public String location;

public static Finder<Long, Profile> find = new Finder(Long.class, Profile.class);
}
