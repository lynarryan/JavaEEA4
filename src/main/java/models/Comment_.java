package models;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-04-12T14:17:31.435+0000")
@StaticMetamodel(Comment.class)
public class Comment_ extends ModelBase_ {
	public static volatile SingularAttribute<Comment, BlogUser> blogUser;
	public static volatile SingularAttribute<Comment, BlogPost> post;
	public static volatile SingularAttribute<Comment, String> commentText;
}
