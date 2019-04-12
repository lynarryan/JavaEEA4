package models;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-04-11T16:47:39.198+0000")
@StaticMetamodel(Comment.class)
public class Comment_ extends ModelBase_ {
	public static volatile SingularAttribute<Comment, String> commentText;
	public static volatile SingularAttribute<Comment, BlogUser> blogUser;
	public static volatile SingularAttribute<Comment, BlogPost> post;
}
