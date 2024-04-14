package net.blusalt.dispatchapi.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.blusalt.dispatchapi.model.constant.ResponseCodeAndMessages;

import java.util.List;

/**
 * @author Olusegun Adeoye
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetDroneModelsResponse {

    private ModelData data;
    private Response response;

    public static GetDroneModelsResponse buildResponse(List models) {
        return GetDroneModelsResponse.builder()
                .response(Response.builder()
                        .code(ResponseCodeAndMessages.SUCCESSFUL.code())
                        .message(ResponseCodeAndMessages.SUCCESSFUL.message())
                        .build())
                .data(ModelData.builder()
                        .models(models)
                        .build())
                .build();
    }
}
