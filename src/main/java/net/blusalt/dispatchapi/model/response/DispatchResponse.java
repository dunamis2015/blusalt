package net.blusalt.dispatchapi.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.blusalt.dispatchapi.model.constant.ResponseCodeAndMessages;

/**
 * @author Olusegun Adeoye
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DispatchResponse {

    private Response response;
    public static DispatchResponse buildResponse() {
        return DispatchResponse.builder()
                .response(Response.builder()
                        .code(ResponseCodeAndMessages.SUCCESSFUL.code())
                        .message(ResponseCodeAndMessages.SUCCESSFUL.message())
                        .build())
                .build();
    }
}
