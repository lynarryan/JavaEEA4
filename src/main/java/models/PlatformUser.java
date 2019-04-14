/**********************************************************************egg*m******a******n********************
 * File: PlatformUser.java
 * Course materials (19W) CST 8277
 * @author (Original) Mike Norman
 * @author (student) Ryan Lynar 040-879-248
 * @author (student) Lauren Preston 040-839-284
 * @author (student) Gregory Leverton 040-885-599
 * 
 */
package models;

import java.io.Serializable;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="PLATFORM_USER")
@EntityListeners({AuditListener.class})
public class PlatformUser extends ModelBase implements Serializable, Principal{
    /** explicit set serialVersionUID */
    private static final long serialVersionUID = 1L;
    protected String userName;
    protected String pwHash;
    protected Set<PlatformRole> platformRoles = new HashSet<>();
    
    /**
     * Default constructor 
     */
    public PlatformUser() {
        super();
    }
    
    
    /* (non-Javadoc)
     * @see java.security.Principal#getName()
     */
    @Override
    public String getName() {
        return userName;
    }
    /**
     * Getter for userName
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Setter for userName
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    

    /**
     * Getter for PwHash
     * @return the password hash
     */
    public String getPwHash() {
        return pwHash;
    }
    /**
     * Setter for pwHash
     * @param pwHash
     */
    public void setPwHash(String pwHash) {
        this.pwHash = pwHash;
    }

    /**
     * Getter for platformRoles
     * @return the Platform roles
     */
    @ManyToMany(cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE
    })
    @JoinTable(name="PLATFORM_USER_ROLE",
      joinColumns=@JoinColumn(name="USER_ID", referencedColumnName="ID"),
      inverseJoinColumns=@JoinColumn(name="ROLE_ID", referencedColumnName="ID"))
    public Set<PlatformRole> getPlatformRoles() {
        return platformRoles;
    }
    /**
     * Setter for platformRoles
     * @param platformRoles
     */
    public void setPlatformRoles(Set<PlatformRole> platformRoles) {
        this.platformRoles = platformRoles;
    }
    
    /**
     * Ass a single platformRole
     * @param platformrole
     */
    public void addPlatformRole(PlatformRole platformrole) {
        this.platformRoles.add(platformrole);
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
        if (!(obj instanceof PlatformUser)) {
            return false;
        }
        PlatformUser other = (PlatformUser)obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

}
