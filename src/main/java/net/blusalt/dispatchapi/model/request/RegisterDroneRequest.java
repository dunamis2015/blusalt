package net.blusalt.dispatchapi.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Olusegun Adeoye
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDroneRequest {

    @NotNull
    @NotEmpty
    @Size(max = 100, message = "Invalid serialNumber! The maximum number of characters allowed is 100")
    private String serialNumber;
    @NotNull
    @NotEmpty
    private String model;
    @NotNull
    @DecimalMax(value = "500.0", inclusive = true, message = "is invalid! The maximum weight limit is 500gr")
    private Double weight;
    @NotNull
    @DecimalMax(value = "100.0", inclusive = true, message = "is invalid! The maximum battery capacity is 100%")
    private Double batteryCapacity;

}
