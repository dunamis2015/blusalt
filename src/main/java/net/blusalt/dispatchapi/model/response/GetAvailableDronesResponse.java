package net.blusalt.dispatchapi.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.blusalt.dispatchapi.model.constant.ResponseCodeAndMessages;
import net.blusalt.dispatchapi.model.entity.Drones;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Olusegun Adeoye
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAvailableDronesResponse {

    private Response response;
    private List<Drones> data;
    public static GetAvailableDronesResponse buildDroneListResponse(List<Drones> drones) {
        List<Drones> dronez = new ArrayList<>();
        for(Drones drone : drones) {
            dronez.add(drone);
        }
        return GetAvailableDronesResponse.builder()
                .response(Response.builder()
                        .code(ResponseCodeAndMessages.SUCCESSFUL.code())
                        .message(ResponseCodeAndMessages.SUCCESSFUL.message())
                        .build())
                .data(dronez)
                .build();
    }
}
