package models;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-04-11T16:47:39.184+0000")
@StaticMetamodel(BlogPost.class)
public class BlogPost_ extends ModelBase_ {
	public static volatile SingularAttribute<BlogPost, String> postText;
	public static volatile ListAttribute<BlogPost, Comment> comment;
	public static volatile SingularAttribute<BlogPost, Blog> blog;
}
