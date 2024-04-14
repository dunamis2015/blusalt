package net.blusalt.dispatchapi.util;

/**
 * @author Olusegun Adeoye
 */
public class CustomResponse {

  public static final String Approved_Or_Completed_Successfully = "00";
  public static final String Transaction_is_pending = "02";
  public static final String Invalid_Token = "04";
  public static final String Generic_Unknown_Error = "RR";
  public static final String Timeout_we_could_not_connect_to_your_bank = "R0";
  public static final String Invalid_Sender = "03";
  public static final String Do_Not_Honour = "05";
  public static final String Dormant_Account = "06";
  public static final String Please_enter_a_valid_account_number = "07";
  public static final String Account_Name_Mismatch = "08";
  public static final String Request_Processing_In_Progress = "09";
  public static final String Invalid_Transaction = "12";
  public static final String Invalid_Amount = "13";
  public static final String Invalid_Batch_Number = "14";
  public static final String Invalid_Session_Or_Record_ID = "15";
  public static final String Unknown_Bank_Code = "16";
  public static final String Invalid_Channel = "17";
  public static final String Wrong_Method_Call = "18";
  public static final String No_Action_Taken = "21";
  public static final String Declined = "23";
  public static final String Unable_To_Locate_Record = "25";
  public static final String Duplicate_Record = "26";
  public static final String Format_Error = "30";
  public static final String Suspected_Fraud = "34";
  public static final String Contact_Sending_Bank = "35";
  public static final String No_Sufficient_Funds = "51";
  public static final String Transaction_Not_Permitted_To_Sender = "57";
  public static final String Transaction_Not_Permitted_On_Channel = "58";
  public static final String Transfer_Limit_Exceeded = "61";
  public static final String Security_Violation = "63";
  public static final String Exceeds_Withdrawal_Limits = "65";
  public static final String Response_Received_Too_Late = "68";
  public static final String Beneficiary_Bank_Not_Available = "91";
  public static final String Routing_Error = "92";
  public static final String Duplicate_Transaction = "94";
  public static final String System_Malfunction = "96";
  public static final String Timeout_Waiting_For_Response_From_Destination = "97";

  private CustomResponse() {
    throw new IllegalStateException("Utility class");
  }

  public static String buildMessage(String code) {
    switch (code) {
      case "00":
        return "Approved Or Completed Successfully";
      case "02":
        return "Pending - Transaction yet to be paid";
      case "04":
        return "Invalid Token";
      case "RR":
        return "Generic/Unknown Error";
      case "R0":
        return "Timeout, we could not connect to your bank";
      case "03":
        return "Invalid Sender";
      case "05":
        return "Do Not Honour";
      case "06":
        return "Dormant Account";
      case "07":
        return "Please enter a valid account number";
      case "08":
        return "Account Name Mismatch";
      case "09":
        return "Request Processing In Progress";
      case "12":
        return "Invalid Transaction";
      case "13":
        return "Invalid Amount";
      case "14":
        return "Invalid Batch Number";
      case "15":
        return "Invalid Session Or Record ID";
      case "16":
        return "Unknown Bank Code";
      case "17":
        return "Invalid Channel";
      case "18":
        return "Wrong Method Call";
      case "21":
        return "No Action Taken";
      case "23":
        return "Invalid beneficiary bank. Confirm request or contact support.";
      case "25":
        return "Unable To Locate Record";
      case "26":
        return "Duplicate Record";
      case "30":
        return "Format Error";
      case "34":
        return "Suspected Fraud";
      case "35":
        return "Contact Sending Bank";
      case "51":
        return "No Sufficient Funds";
      case "57":
        return "Transaction Not Permitted To Sender";
      case "58":
        return "Transaction Not Permitted On Channel";
      case "61":
        return "Transfer Limit Exceeded";
      case "63":
        return "Security Violation";
      case "65":
        return "Exceeds Withdrawal Limits";
      case "68":
        return "Response Received Too Late";
      case "91":
        return "Beneficiary Bank Not Available";
      case "92":
        return "Transaction not found";
      case "94":
        return "Duplicate Transaction";
      case "96":
        return "System Malfunction";
      case "97":
        return "Timeout Waiting For Response From Destination";
      default:
        break;
    }
    return null;
  }


}
