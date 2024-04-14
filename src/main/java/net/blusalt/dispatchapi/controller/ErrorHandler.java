package net.blusalt.dispatchapi.controller;

import lombok.extern.slf4j.Slf4j;
import net.blusalt.dispatchapi.exception.*;
import net.blusalt.dispatchapi.model.constant.ErrorCode;
import net.blusalt.dispatchapi.model.constant.ResponseCodeAndMessages;
import net.blusalt.dispatchapi.model.response.ErrorResponse;
import net.blusalt.dispatchapi.model.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeoutException;

/**
 * @author Olusegun Adeoye
 */
@Slf4j
@ControllerAdvice
public class ErrorHandler {

  public ErrorHandler(
//          LoggingUtil loggingUtil
  ) {
//    this.loggingUtil = loggingUtil;
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ErrorResponse> handleBadRequest(
      HttpServletRequest request, Exception e) {
    ErrorResponse response = ErrorResponse.builder()
        .status(false)
        .error(ErrorCode.INPUT)
        .message(e.getMessage())
        .response(Response.builder().code(ResponseCodeAndMessages.FORMAT_ERROR.code())
            .message(e.getMessage()).build())
        .build();
    logError(request, e);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ErrorResponse> handleUnauthorized(
      HttpServletRequest request, AuthenticationException e) {
    ErrorResponse response = ErrorResponse.builder()
        .status(false)
        .error(ErrorCode.PERMISSION)
        .message(e.getMessage())
        .response(Response.builder()
            .code(ResponseCodeAndMessages.TRANSACTION_NOT_PERMITTED_TO_SENDER.code())
            .message(e.getMessage()).build())
        .build();
    logError(request, e);
    return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
  }

  private void logError(HttpServletRequest request, Exception e) {
    StringBuilder requestDescription = new StringBuilder("Error sending ")
        .append(request.getMethod())
        .append(" request to ")
        .append(request.getRequestURI());
    if (!StringUtils.isEmpty(request.getQueryString())) {
      requestDescription.append(" with query: ").append(request.getQueryString());
    }
    requestDescription.append(". Error: ");
    log.error(requestDescription.toString() + e.getMessage(), e);
//    loggingUtil.error(requestDescription.toString() + e.getMessage(), e);
  }

  @ExceptionHandler(ConflictException.class)
  public ResponseEntity<ErrorResponse> handleConflict
      (HttpServletRequest request, ConflictException e) {
    ErrorResponse response = ErrorResponse.builder()
        .status(false)
        .error(ErrorCode.INPUT)
        .message(e.getMessage())
        .response(
            Response.builder().code(ResponseCodeAndMessages.DUPLICATE_RECORD.code())
                .message(e.getMessage()).build())
        .build();
    logError(request, e);
    return new ResponseEntity<>(response, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(ProcessingException.class)
  public ResponseEntity<ErrorResponse> handleProcessing
      (HttpServletRequest request, ProcessingException e) {
    ErrorResponse response = ErrorResponse.builder()
        .status(false)
        .error(ErrorCode.PROCESSING)
        .message(e.getMessage())
        .response(Response.builder()
            .code(ResponseCodeAndMessages.GENERIC_OR_UNKNOWN_ERROR.code()).message(e.getMessage())
            .build())
        .build();
    logError(request, e);
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorResponse> handleNotFound
      (HttpServletRequest request, NotFoundException e) {
    ErrorResponse response = ErrorResponse.builder()
        .status(false)
        .error(ErrorCode.NOT_FOUND)
        .message(e.getMessage())
        .response(Response.builder()
            .code(ResponseCodeAndMessages.UNABLE_TO_LOCATE_RECORD.code()).message(e.getMessage())
            .build())
        .build();
    logError(request, e);
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleInternalError
      (HttpServletRequest request, Exception e) {
    ErrorResponse response = ErrorResponse.builder()
        .status(false)
        .error(ErrorCode.PROCESSING)
        .message(e.getMessage())
        .response(Response.builder()
            .code(ResponseCodeAndMessages.GENERIC_OR_UNKNOWN_ERROR.code()).message(e.getMessage())
            .build())
        .build();
    logError(request, e);
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(TimeoutException.class)
  public ResponseEntity<ErrorResponse> handleProcessing
      (HttpServletRequest request, TimeoutException e) {
    ErrorResponse response = ErrorResponse.builder()
        .status(false)
        .error(ErrorCode.TIMEOUT)
        .message(e.getMessage())
        .response(Response.builder()
            .code(ResponseCodeAndMessages.TIMEOUT.code()).message(e.getMessage())
            .build())
        .build();
    logError(request, e);
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
