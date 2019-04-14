/**********************************************************************egg*m******a******n********************
 * File: PlatformRole.java
 * Course materials (19W) CST 8277
 * @author (original) Mike Norman
 * @author (student) Ryan Lynar 040-879-248
 * @author (student) Lauren Preston 040-839-284
 * @author (student) Gregory Leverton 040-885-599
 * 
 */
package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToMany;
import javax.persistence.Table;



@Entity
@Table(name="PLATFORM_ROLE")
@EntityListeners({AuditListener.class})
public class PlatformRole extends ModelBase implements Serializable {
    /** explicit set serialVersionUID */
    private static final long serialVersionUID = 1L;
    
    /**
     * Default constructor
     */
    public PlatformRole() {
        super();
    }

    protected String roleName;
    protected List<PlatformUser> platformUsers = new ArrayList<>();

    /**
     * Getter for roleName
     * @return The name of the role
     */
    public String getRoleName() {
        return roleName;
    }
    /**
     * Setter for roleName
     * @param roleName
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * Getter for platformUsers
     * @return The Users with the role
     */
    @ManyToMany(mappedBy="platformRoles")
    public List<PlatformUser> getPlatformUsers() {
        return platformUsers;
    }
    /**
     * Setter for platformUsers
     * @param platformUsers
     */
    public void setPlatformUsers(List<PlatformUser> platformUsers) {
        this.platformUsers = platformUsers;
    }
    
    /**
     * Add a platformUser
     * @param platformUser
     */
    public void addPlatformUser(PlatformUser platformUser) {
        this.platformUsers.add(platformUser);
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof PlatformRole)) {
            return false;
        }
        PlatformRole other = (PlatformRole)obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

    
}
