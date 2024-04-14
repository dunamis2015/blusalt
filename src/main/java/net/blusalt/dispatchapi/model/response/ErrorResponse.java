package net.blusalt.dispatchapi.model.response;


import lombok.*;
import net.blusalt.dispatchapi.model.constant.ErrorCode;

/**
 * @author Olusegun Adeoye
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ErrorResponse {

  private boolean status;
  private ErrorCode error;
  private String message;
  private Response response;
}
