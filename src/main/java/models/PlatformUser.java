package models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="PLATFORM_USER")
@EntityListeners({AuditListener.class})
public class PlatformUser extends ModelBase implements Serializable{
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
    
    
   
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    

    public String getPwHash() {
        return pwHash;
    }
    public void setPwHash(String pwHash) {
        this.pwHash = pwHash;
    }

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
    public void setPlatformRoles(Set<PlatformRole> platformRoles) {
        this.platformRoles = platformRoles;
    }
    
    public void addPlatformRole(PlatformRole platformrole) {
        this.platformRoles.add(platformrole);
    }
    

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
