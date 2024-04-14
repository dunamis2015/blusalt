package net.blusalt.dispatchapi.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.blusalt.dispatchapi.util.CustomResponse;

/**
 * @author Olusegun Adeoye
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class Response {

  private String code;
  private String message;

  public Response build(String code) {
    return Response.builder().code(code).message(CustomResponse.buildMessage(code)).build();
  }
}
