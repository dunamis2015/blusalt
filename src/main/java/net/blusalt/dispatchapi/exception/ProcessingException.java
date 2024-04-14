package net.blusalt.dispatchapi.exception;

/**
 * @author Olusegun Adeoye
 */
public class ProcessingException extends APIException {

  public ProcessingException(String message) {
    super(message);
  }

  public ProcessingException(Throwable cause) {
    super(cause);
  }
}
