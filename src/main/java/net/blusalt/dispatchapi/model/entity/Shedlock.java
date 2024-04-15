package net.blusalt.dispatchapi.model.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
public class Shedlock {
  private String name;
  private Timestamp lockUntil;
  private Timestamp lockedAt;
  private String lockedBy;

  @Id
  @Basic
  @Column(name = "name", nullable = false)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Basic
  @Column(name = "lock_until", nullable = false)
  public Timestamp getLockUntil() {
    return lockUntil;
  }

  public void setLockUntil(Timestamp lockUntil) {
    this.lockUntil = lockUntil;
  }

  @Basic
  @Column(name = "locked_at", nullable = false)
  public Timestamp getLockedAt() {
    return lockedAt;
  }

  public void setLockedAt(Timestamp lockedAt) {
    this.lockedAt = lockedAt;
  }

  @Basic
  @Column(name = "locked_by", nullable = false)
  public String getLockedBy() {
    return lockedBy;
  }

  public void setLockedBy(String lockedBy) {
    this.lockedBy = lockedBy;
  }


  @PrePersist
  public void beforeSave() {
    this.lockedAt = new Timestamp(new Date().getTime());
  }

}
