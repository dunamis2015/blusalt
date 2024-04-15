package net.blusalt.dispatchapi.model.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Olusegun Adeoye
 */
@Entity
@Table(indexes = {
        @Index(name = "serial_number_index", columnList = "serial_number")
})
public class Drones {

    private Integer id;
    private String serialNumber;
    private String model;
    private Double weight;
    private Double batteryCapacity;
    private String state;
    private Double journeyBatteryConsumption;
    private String timeToDestinationInSeconds;
    private Timestamp returnDateTime;
    private String startLocation;
    private String deliveryLocation;
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
    @Column(name = "serial_number", unique = true, nullable = false)
    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Basic
    @Column(name = "model", nullable = false)
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Basic
    @Column(name = "weight", nullable = false)
    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @Basic
    @Column(name = "battery_capacity", nullable = false)
    public Double getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(Double batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    @Basic
    @Column(name = "state", nullable = true)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Basic
    @Column(name = "journey_battery_consumption", nullable = true)
    public Double getJourneyBatteryConsumption() {
        return journeyBatteryConsumption;
    }

    public void setJourneyBatteryConsumption(Double journeyBatteryConsumption) {
        this.journeyBatteryConsumption = journeyBatteryConsumption;
    }

    @Basic
    @Column(name = "time_to_destination_in_seconds", nullable = true)
    public String getTimeToDestinationInSeconds() {
        return timeToDestinationInSeconds;
    }

    public void setTimeToDestinationInSeconds(String timeToDestinationInSeconds) {
        this.timeToDestinationInSeconds = timeToDestinationInSeconds;
    }

    @Basic
    @Column(name = "return_date_time", nullable = true)
    public Timestamp getReturnDateTime() {
        return returnDateTime;
    }

    public void setReturnDateTime(Timestamp returnDateTime) {
        this.returnDateTime = returnDateTime;
    }

    @Basic
    @Column(name = "start_location", nullable = true)
    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    @Basic
    @Column(name = "delivery_location", nullable = true)
    public String getDeliveryLocation() {
        return deliveryLocation;
    }

    public void setDeliveryLocation(String deliveryLocation) {
        this.deliveryLocation = deliveryLocation;
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
