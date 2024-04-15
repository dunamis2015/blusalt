package net.blusalt.dispatchapi.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author Olusegun Adeoye
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FireDroneRequest {
    @NotNull
    @NotEmpty
    private String droneSerialNumber;
    @NotNull
    private Double journeyBatteryConsumption;
    @NotNull
    @NotEmpty
    private String timeToDestinationInSeconds;
    @NotNull
    @NotEmpty
    private String returnDateTime;
    private String startLocation;
    private String deliveryLocation;
}
