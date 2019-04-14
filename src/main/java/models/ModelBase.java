/**********************************************************************egg*m******a******n********************
 * File: ModelBase.java
 * Course materials (19W) CST 8277
 * @author (student) Ryan Lynar 040-879-248
 * @author (student) Lauren Preston 040-839-284
 * @author (student) Gregory Leverton 040-885-599
 * 
 */
package models;

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
public abstract class ModelBase implements Auditable {

    protected int id;
    protected int version;
    protected Audit audit = new Audit();
    
    /**
     * Default constructor
     */
    public ModelBase() {
        super();
        this.audit.setCreatedDate(LocalDateTime.now());
        // created_date and updated_date have the same value
        // at the very beginning of object's lifecycle
        this.audit.setUpdatedDate(this.audit.getCreatedDate());
        }

    /**
     * Getter for id
     * @return The object id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    /**
     * Setter for id
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for version
     * @return the object version
     */
    @Version
    public int getVersion() {
        return version;
    }
    /**
     * Setter for version
     * @param version
     */
    public void setVersion(int version) {
        this.version = version;
    }
    /* (non-Javadoc)
     * @see models.Auditable#getAudit()
     */
    public Audit getAudit() {
        return audit;
        }
    
    /* (non-Javadoc)
     * @see models.Auditable#setAudit(models.Audit)
     */
    public void setAudit(Audit audit) {
        this.audit = audit;
    }
}