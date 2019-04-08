/********************************************************************egg***m******a**************n************
 * File: ModelBase.java
 * Course materials (19W) CST 8277
 * @author Mike Norman
 *
 * @date 2019 03
 */
package jpa;

import java.time.LocalDateTime;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * Abstract class that is base of (class) hierarchy for all com.algonquincollege.cst8277.models @Entity classes
 */
@MappedSuperclass
public abstract class ModelBase {

    protected int id;
    protected int version;
    protected Audit audit = new Audit();
    
    public ModelBase() {
        super();
    this.audit.setCreatedDate(LocalDateTime.now());
    // created_date and updated_date have the same value 
    // at the very beginning of object's lifecycle 
    this.audit.setUpdatedDate(this.audit.getCreatedDate());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Version
    public int getVersion() {
        return version;
    }
    public void setVersion(int version) {
        this.version = version;
    }
    public Audit getAudit() { return audit;
    }
    public void setAudit(Audit audit) { 
        this.audit = audit;
    }
}