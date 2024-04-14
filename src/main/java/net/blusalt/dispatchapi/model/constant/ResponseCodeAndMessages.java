package net.blusalt.dispatchapi.model.constant;


/**
 * @author Olusegun Adeoye
 */
public enum ResponseCodeAndMessages {

  SUCCESSFUL("00", "Approved Or Completed Successfully"),
  PENDING("02", "Address verification is pending"),
  GENERIC_OR_UNKNOWN_ERROR("RR", "Generic/Unknown Error"),
  TIMEOUT("R0", "Timeout, we could not connect to your bank"),
  INVALID_SENDER("03", "Invalid Sender"),
  DO_NOT_HONOUR("05", "Do Not Honour"),
  DORMANT_ACCOUNT("06", "Dormant Account"),
  ENTER_VALID_ACCOUNT("07", "Please enter a valid account number"),
  ACCOUNT_NAME_MISMATCH("08", "Account Name Mismatch"),
  REQUEST_PROCESSING_IN_PROGRESS("09", "Request Processing In Progress"),
  INVALID_TRANSACTION("12", "Invalid Transaction"),
  INVALID_AMOUNT("13", "Invalid Amount"),
  INVALID_BATCH_NUMBER("14", "Invalid Batch Number"),
  INVALID_SESSION_OR_RECORD_ID("15", "Invalid Session Or Record ID"),
  UNKNOWN_BANK_CODE("16", "Unknown Bank Code"),
  INVALID_CHANNEL("17", "Invalid Channel"),
  WRONG_METHOD_CALL("18", "Wrong Method Call"),
  NO_ACTION_TAKEN("21", "No Action Taken"),
  UNABLE_TO_LOCATE_RECORD("25", "Unable To Locate Record"),
  DUPLICATE_RECORD("26", "Duplicate Record"),
  FORMAT_ERROR("30", "Format Error"),
  SUSPECTED_FRAUD("34", "Suspected Fraud"),
  CONTACT_SENDING_BANK("35", "Contact Sending Bank"),
  NO_SUFFICIENT_FUNDS("51", "No Sufficient Funds"),
  TRANSACTION_NOT_PERMITTED_TO_SENDER("57", "Transaction Not Permitted To Sender"),
  TRANSACTION_NOT_PERMITTED_ON_CHANNEL("58", "Transaction Not Permitted On Channel"),
  TRANSFER_LIMIT_EXCEEDED("61", "Transfer Limit Exceeded"),
  SECURITY_VIOLATION("63", "Security Violation"),
  EXCEEDS_WITHDRAWAL_LIMITS("65", "Exceeds Withdrawal Limits"),
  RESPONSE_TOO_LATE("68", "Response Received Too Late"),
  BENEFICIARY_BANK_NOT_AVAILABLE("91", "Beneficiary Bank Not Available"),
  ROUTING_ERROR("92", "Routing Error"),
  DUPLICATE_TRANSACTION("94", "Duplicate Transaction"),
  SYSTEM_MALFUNCTION("96", "System Malfunction"),
  TIMEOUT_WAITING_RESPONSE("97", "Timeout Waiting For Response From Destination");

  private final String code;
  private final String message;

  ResponseCodeAndMessages(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public String code() {
    return this.code;
  }

  public String message() {
    return this.message;
  }
}
