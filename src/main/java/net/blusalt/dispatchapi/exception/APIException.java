package net.blusalt.dispatchapi.exception;

/**
 * @author Olusegun Adeoye
 */
public abstract class APIException extends RuntimeException {

  protected APIException(String message) {
    super(message);
  }

  protected APIException(Throwable cause) {
    super(cause);
  }
}
