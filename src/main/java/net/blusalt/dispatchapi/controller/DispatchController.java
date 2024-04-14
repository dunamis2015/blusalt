package net.blusalt.dispatchapi.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import net.blusalt.dispatchapi.model.entity.Log;
import net.blusalt.dispatchapi.model.request.InitiateDroneLoadingRequest;
import net.blusalt.dispatchapi.model.request.RegisterDroneRequest;
import net.blusalt.dispatchapi.model.response.GetDroneModelsResponse;
import net.blusalt.dispatchapi.model.response.DispatchResponse;
import net.blusalt.dispatchapi.service.DispatchService;
import net.blusalt.dispatchapi.validator.InputValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Olusegun Adeoye
 */
@Slf4j
@RestController
public class DispatchController {

    private static final String AUTHMESSAGE = "Not Authorized";
    private static final String REQUESTSTRING = ">>>>>>>>" + "Dispatch_Request ";
    private static final String RESPONSESTRING = ">>>>>>>>" + "Dispatch_Response ";
    private final Gson gson;
    private final DispatchService dispatchService;

    @Autowired
    public DispatchController(Gson gson, DispatchService dispatchService) {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.dispatchService = dispatchService;
    }

    @GetMapping(path = "/v1/getDroneModels", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetDroneModelsResponse> GetDroneModels(
//            @RequestHeader("Authorization") String authCredentials,
           ) throws Exception {
//        if (StringUtils.isBlank(authCredentials)) {
//            return new ResponseEntity<>(GetDroneModelsResponse.builder().build(),
//                    HttpStatus.UNAUTHORIZED);
//        }
        Log logs = dispatchService.saveLog("", "", "getDroneModelsRequest");
        GetDroneModelsResponse getDroneModelsResponse = dispatchService.getDroneModels();
        log.info("GetDroneModelsResponse: " + gson.toJson(getDroneModelsResponse));
        dispatchService.updateLog(logs, gson.toJson(getDroneModelsResponse));
        return new ResponseEntity<>(getDroneModelsResponse, HttpStatus.OK);
    }

    @PostMapping(path = "/v1/registerDrone", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DispatchResponse> RegisterDrone(
//            @RequestHeader("Authorization") String authCredentials,
            @Valid @RequestBody RegisterDroneRequest registerDroneRequest,
            BindingResult bindingResult) throws Exception {
//        if (StringUtils.isBlank(authCredentials)) {
//            return new ResponseEntity<>(DispatchResponse.builder().build(),
//                    HttpStatus.UNAUTHORIZED);
//        }
        Log logs = dispatchService.saveLog(registerDroneRequest.getSerialNumber(), gson.toJson(registerDroneRequest),
                "registerDroneRequest");
        log.info("registerDroneRequest: " + gson.toJson(registerDroneRequest));
        InputValidator.validate(bindingResult);
        DispatchResponse dispatchResponse = dispatchService.registerDrone(registerDroneRequest, logs);
        log.info("registerDroneResponse: " + gson.toJson(dispatchResponse));
        dispatchService.updateLog(logs, gson.toJson(dispatchResponse));
        return new ResponseEntity<>(dispatchResponse, HttpStatus.OK);
    }

    @PostMapping(path = "/v1/initiateDroneLoading", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DispatchResponse> InitiateDroneLoading(
//            @RequestHeader("Authorization") String authCredentials,
            @Valid @RequestBody InitiateDroneLoadingRequest initiateDroneLoadingRequest,
            BindingResult bindingResult) throws Exception {
//        if (StringUtils.isBlank(authCredentials)) {
//            return new ResponseEntity<>(DispatchResponse.builder().build(),
//                    HttpStatus.UNAUTHORIZED);
//        }
//        Log logs = dispatchService.saveLog(registerDroneRequest.getSerialNumber(), gson.toJson(registerDroneRequest),
//                "registerDroneRequest");
//        log.info("registerDroneRequest: " + gson.toJson(registerDroneRequest));
        InputValidator.validate(bindingResult);
        DispatchResponse dispatchResponse = dispatchService.initiateDroneLoading(initiateDroneLoadingRequest);
        log.info("registerDroneResponse: " + gson.toJson(dispatchResponse));
//        dispatchService.updateLog(logs, gson.toJson(dispatchResponse));
        return new ResponseEntity<>(dispatchResponse, HttpStatus.OK);
    }
}
