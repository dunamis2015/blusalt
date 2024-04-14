package net.blusalt.dispatchapi.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

/**
 * @author Olusegun Adeoye
 */
@Configuration
@Slf4j
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

  @Override
  public void handleError(ClientHttpResponse response) throws IOException {
    log.error("Response error: {} {}", response.getStatusCode(), response.getStatusText());
  }

  @Override
  public boolean hasError(ClientHttpResponse response) throws IOException {
    return RestUtil.isError((HttpStatus) response.getStatusCode());
  }
}