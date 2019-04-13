package models;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-04-10T18:18:48.574+0000")
@StaticMetamodel(BlogPost.class)
public class BlogPost_ extends ModelBase_ {
	public static volatile ListAttribute<BlogPost, Comment> comment;
	public static volatile SingularAttribute<BlogPost, Blog> blog;
	public static volatile SingularAttribute<BlogPost, String> postText;
}
