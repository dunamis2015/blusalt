package net.blusalt.dispatchapi.util;

import org.springframework.http.HttpStatus;
/**
 * @author Olusegun Adeoye
 */
public class RestUtil {


  private RestUtil() {
    throw new IllegalStateException("Utility class");
  }

  public static boolean isError(HttpStatus status) {
    HttpStatus.Series series = status.series();
    return (HttpStatus.Series.CLIENT_ERROR.equals(series)
        || HttpStatus.Series.SERVER_ERROR.equals(series));
  }

}
