package models;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-04-11T16:01:38.504+0000")
@StaticMetamodel(Blog.class)
public class Blog_ extends ModelBase_ {
	public static volatile ListAttribute<Blog, BlogPost> blogs;
	public static volatile SingularAttribute<Blog, BlogUser> blogUser;
}
