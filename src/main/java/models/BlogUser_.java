package models;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-04-11T16:47:39.193+0000")
@StaticMetamodel(BlogUser.class)
public class BlogUser_ extends ModelBase_ {
	public static volatile SingularAttribute<BlogUser, String> firstName;
	public static volatile SingularAttribute<BlogUser, String> lastName;
	public static volatile SingularAttribute<BlogUser, String> email;
	public static volatile SingularAttribute<BlogUser, PlatformUser> platformUser;
	public static volatile ListAttribute<BlogUser, Blog> blogs;
	public static volatile ListAttribute<BlogUser, Comment> comments;
}
