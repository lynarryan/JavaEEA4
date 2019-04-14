package models;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-04-14T13:53:57.087+0000")
@StaticMetamodel(Blog.class)
public class Blog_ extends ModelBase_ {
	public static volatile SingularAttribute<Blog, BlogUser> blogUser;
	public static volatile SingularAttribute<Blog, String> blogName;
	public static volatile ListAttribute<Blog, BlogPost> blogPosts;
}
