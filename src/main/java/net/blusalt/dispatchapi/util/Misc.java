package net.blusalt.dispatchapi.util;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Olusegun Adeoye
 */
public class Misc {

  private Misc() {
    throw new IllegalStateException("Utility class");
  }

  public static Timestamp now() {
    return new Timestamp(System.currentTimeMillis());
  }

  public static String generateNuban(int length) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("ddHHssSSS");
    String randomSuffix = String.format("%01d", 1 + new SecureRandom().nextInt(length));
    return dateFormat.format(new Date()) + randomSuffix;
  }

  public static String getTransactionIdentifier(int length) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHssSSS");
    String randomSuffix = String.format("%03d", new SecureRandom().nextInt(length));
    return dateFormat.format(new Date()) + randomSuffix;
  }

  public static Timestamp addSecondsToDate (Date date, String seconds) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.SECOND, Integer.valueOf(seconds));
    Date expiryDate = calendar.getTime();
    Timestamp timestamp = new Timestamp(expiryDate.getTime());
    System.out.println(">>>>>>>CreatedAt: "+date);
    System.out.println(">>>>>>>ConfiguredExpiryTimeInSeconds: "+seconds);
    System.out.println(">>>>>>ExpiredAt: "+timestamp);
    return timestamp;
  }

}