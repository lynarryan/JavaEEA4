package jpa;

public interface Auditable {
    
    public Audit getAudit();
    public void setAudit(Audit audit);

}
