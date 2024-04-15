package net.blusalt.dispatchapi.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.blusalt.dispatchapi.model.constant.ResponseCodeAndMessages;
import net.blusalt.dispatchapi.model.entity.Medications;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Olusegun Adeoye
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetDroneLoadedItemsResponse {

    private Response response;
    private List<Medications> data;
    public static GetDroneLoadedItemsResponse buildLoadedDroneResponse(List<Medications> medications) {
        List<Medications> medication = new ArrayList<>();
        for(Medications medication1 : medications) {
            medication.add(medication1);
        }
        return GetDroneLoadedItemsResponse.builder()
                .response(Response.builder()
                        .code(ResponseCodeAndMessages.SUCCESSFUL.code())
                        .message(ResponseCodeAndMessages.SUCCESSFUL.message())
                        .build())
                .data(medication)
                .build();
    }
}
