package net.blusalt.dispatchapi.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.blusalt.dispatchapi.validator.annotation.UpperCase;
import net.blusalt.dispatchapi.validator.annotation.ValidValue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author Olusegun Adeoye
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InitiateDroneLoadingRequest {

    @NotNull
    @NotEmpty
    private String loadingReference;
    @NotNull
    @NotEmpty
    @ValidValue(message = "is invalid! Only letters, numbers, hyphen and underscores are allowed")
    private String medicationName;
    @NotNull
    private Double medicationWeight;
    @NotNull
    @NotEmpty
    @UpperCase(message = "is invalid! Only upper case letters, underscore, and numbers are allowed")
    private String medicationCode;
    @NotNull
    @NotEmpty
    private String medicationImage;
    @NotNull
    @NotEmpty
    private String droneSerialNumber;
}
