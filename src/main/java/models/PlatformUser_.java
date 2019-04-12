package models;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-04-11T16:47:39.209+0000")
@StaticMetamodel(PlatformUser.class)
public class PlatformUser_ extends ModelBase_ {
	public static volatile SingularAttribute<PlatformUser, String> userName;
	public static volatile SingularAttribute<PlatformUser, String> pwHash;
	public static volatile SetAttribute<PlatformUser, PlatformRole> platformRoles;
}
