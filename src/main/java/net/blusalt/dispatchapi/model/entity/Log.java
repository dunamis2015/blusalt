package net.blusalt.dispatchapi.model.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Olusegun Adeoye
 */
@Entity
@Table(indexes = {
        @Index(name = "ref_index", columnList = "reference")
})
public class Log {
  private Integer id;
  private String reference;
  private String request;
  private String response;
  private String actionType;
  private Timestamp createdAt;
  private Timestamp updatedAt;

  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Basic
  @Column(name = "reference", nullable = true)
  public String getReference() {
    return reference;
  }

  public void setReference(String reference) {
    this.reference = reference;
  }

  @Basic
  @Lob
  @Column(name = "request", nullable = true)
  public String getRequest() {
    return request;
  }

  public void setRequest(String request) {
    this.request = request;
  }

  @Basic
  @Lob
  @Column(name = "response", nullable = true)
  public String getResponse() {
    return response;
  }

  public void setResponse(String response) {
    this.response = response;
  }

  @Basic
  @Column(name = "action_type", nullable = false)
  public String getActionType() {
    return actionType;
  }

  public void setActionType(String actionType) {
    this.actionType = actionType;
  }

  @Basic
  @Column(name = "created_at", nullable = false)
  public Timestamp getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Timestamp createdAt) {
    this.createdAt = createdAt;
  }

  @Basic
  @Column(name = "updated_at", nullable = true)
  public Timestamp getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Timestamp updatedAt) {
    this.updatedAt = updatedAt;
  }

  @PrePersist
  public void beforeSave() {
    this.createdAt = new Timestamp(new Date().getTime());
  }

  @PreUpdate
  private void beforeUpdate() {
    this.updatedAt = new Timestamp(new Date().getTime());
  }
}
