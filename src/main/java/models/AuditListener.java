/**********************************************************************egg*m******a******n********************
 * File: AuditListener.java
 * Course materials (19W) CST 8277
 * @author (student) Ryan Lynar 040-879-248
 * @author (student) Lauren Preston 040-839-284
 * @author (student) Gregory Leverton 040-885-599
 * 
 */
package models;

import java.time.LocalDateTime;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class AuditListener {
    /**
     * Setter for createdDate
     * @param auditable
     */
    @PrePersist
    public void setCreatedDate(Auditable auditable) {
        Audit audit = auditable.getAudit();
        audit.setCreatedDate(LocalDateTime.now());
    }

    /**
     * Setter for updatedDate
     * @param auditable
     */
    @PreUpdate
    public void setUpdatedDate(Auditable auditable) {
        Audit audit = auditable.getAudit();
        audit.setUpdatedDate(LocalDateTime.now());
    }
}