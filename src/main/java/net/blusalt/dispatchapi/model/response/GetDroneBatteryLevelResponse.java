package net.blusalt.dispatchapi.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.blusalt.dispatchapi.model.constant.ResponseCodeAndMessages;
import net.blusalt.dispatchapi.model.entity.Drones;

/**
 * @author Olusegun Adeoye
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetDroneBatteryLevelResponse {
    private Response response;
    private BatteryLevelResponse data;

    public static GetDroneBatteryLevelResponse buildBatteryLevelResponse(Drones drone) {
        return GetDroneBatteryLevelResponse.builder()
                .response(Response.builder()
                        .code(ResponseCodeAndMessages.SUCCESSFUL.code())
                        .message(ResponseCodeAndMessages.SUCCESSFUL.message())
                        .build())
                .data(BatteryLevelResponse.builder()
                        .batteryLevel(drone.getBatteryCapacity())
                        .build())
                .build();
    }
}
