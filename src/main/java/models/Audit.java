/**********************************************************************egg*m******a******n********************
 * File: Audit.java
 * Course materials (19W) CST 8277
 * @author (original) Mike Norman
 * @author (student) Ryan Lynar 040-879-248
 * @author (student) Lauren Preston 040-839-284
 * @author (student) Gregory Leverton 040-885-599
 * 
 */
package models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Audit {
    protected LocalDateTime createdDate;
    protected LocalDateTime updatedDate;


    /**
     * 
     */
    public Audit() {
    }

    /**
     * Getter for createdDate
     * @return Date created
     */
    @Column(name = "created_date")
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    /**
     * Setter for createdDate
     * @param createdDate
     */
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Getter for updated_date
     * @return date updated
     */
    @Column(name = "updatedDate")
    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    /**
     * Setter for updatedDate
     * @param updatedDate
     */
    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }
}