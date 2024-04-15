package net.blusalt.dispatchapi.model.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Olusegun Adeoye
 */
@Entity
@Table(indexes = {
        @Index(name = "loading_reference_index", columnList = "loading_reference"),
        @Index(name = "drone_serial_number_index", columnList = "drone_serial_number"),
        @Index(name = "mapped_index", columnList = "mapped")
})
public class Medications {

    private Integer id;
    private String loadingReference;
    private String droneSerialNumber;
    private String medicationName;
    private Double medicationWeight;
    private String medicationCode;
    private String medicationImage;
    private String status;
    private Boolean mapped;
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
    @Column(name = "loading_reference", unique = true, nullable = false)
    public String getLoadingReference() {
        return loadingReference;
    }

    public void setLoadingReference(String loadingReference) {
        this.loadingReference = loadingReference;
    }

    @Basic
    @Column(name = "drone_serial_number", nullable = false)
    public String getDroneSerialNumber() {
        return droneSerialNumber;
    }

    public void setDroneSerialNumber(String droneSerialNumber) {
        this.droneSerialNumber = droneSerialNumber;
    }

    @Basic
    @Column(name = "medication_name", nullable = false)
    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    @Basic
    @Column(name = "medication_weight", nullable = false)
    public Double getMedicationWeight() {
        return medicationWeight;
    }

    public void setMedicationWeight(Double medicationWeight) {
        this.medicationWeight = medicationWeight;
    }

    @Basic
    @Column(name = "medication_code", nullable = false)
    public String getMedicationCode() {
        return medicationCode;
    }

    public void setMedicationCode(String medicationCode) {
        this.medicationCode = medicationCode;
    }

    @Basic
    @Lob
    @Column(name = "medication_image", nullable = false)
    public String getMedicationImage() {
        return medicationImage;
    }

    public void setMedicationImage(String medicationImage) {
        this.medicationImage = medicationImage;
    }

    @Basic
    @Column(name = "status", nullable = false)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "mapped", nullable = false)
    public Boolean getMapped() {
        return mapped;
    }

    public void setMapped(Boolean mapped) {
        this.mapped = mapped;
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
