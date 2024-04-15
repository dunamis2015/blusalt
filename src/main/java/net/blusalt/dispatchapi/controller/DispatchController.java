package net.blusalt.dispatchapi.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import net.blusalt.dispatchapi.model.entity.Log;
import net.blusalt.dispatchapi.model.request.CompleteDroneLoadingRequest;
import net.blusalt.dispatchapi.model.request.FireDroneRequest;
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

    private final Gson gson;
    private final DispatchService dispatchService;

    @Autowired
    public DispatchController(Gson gson, DispatchService dispatchService) {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.dispatchService = dispatchService;
    }

    @GetMapping(path = "/v1/getDroneModels", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetDroneModelsResponse> GetDroneModels() throws Exception {
        Log logs = dispatchService.saveLog("", "", "getDroneModelsRequest");
        GetDroneModelsResponse getDroneModelsResponse = dispatchService.getDroneModels();
        log.info("GetDroneModelsResponse: " + gson.toJson(getDroneModelsResponse));
        dispatchService.updateLog(logs, gson.toJson(getDroneModelsResponse));
        return new ResponseEntity<>(getDroneModelsResponse, HttpStatus.OK);
    }

    @PostMapping(path = "/v1/registerDrone", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DispatchResponse> RegisterDrone(
            @Valid @RequestBody RegisterDroneRequest registerDroneRequest,
            BindingResult bindingResult) throws Exception {
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
            @Valid @RequestBody InitiateDroneLoadingRequest initiateDroneLoadingRequest,
            BindingResult bindingResult) throws Exception {
        Log logs = dispatchService.saveLog(initiateDroneLoadingRequest.getLoadingReference(),
                gson.toJson(initiateDroneLoadingRequest), "initiateDroneLoadingRequest");
        log.info("initiateDroneLoadingRequest: " + gson.toJson(initiateDroneLoadingRequest));
        InputValidator.validate(bindingResult);
        DispatchResponse dispatchResponse = dispatchService.initiateDroneLoading(initiateDroneLoadingRequest, logs);
        log.info("initiateDroneLoadingResponse: " + gson.toJson(dispatchResponse));
        dispatchService.updateLog(logs, gson.toJson(dispatchResponse));
        return new ResponseEntity<>(dispatchResponse, HttpStatus.OK);
    }

    @PostMapping(path = "/v1/completeDroneLoading", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DispatchResponse> CompleteDroneLoading(
            @Valid @RequestBody CompleteDroneLoadingRequest completeDroneLoadingRequest,
            BindingResult bindingResult) throws Exception {
        Log logs = dispatchService.saveLog(completeDroneLoadingRequest.getLoadingReference(),
                gson.toJson(completeDroneLoadingRequest), "completeDroneLoadingRequest");
        log.info("completeDroneLoadingRequest: " + gson.toJson(completeDroneLoadingRequest));
        InputValidator.validate(bindingResult);
        DispatchResponse dispatchResponse = dispatchService.completeDroneLoading(completeDroneLoadingRequest, logs);
        log.info("completeDroneLoadingResponse: " + gson.toJson(dispatchResponse));
        dispatchService.updateLog(logs, gson.toJson(dispatchResponse));
        return new ResponseEntity<>(dispatchResponse, HttpStatus.OK);
    }

    @PostMapping(path = "/v1/fireDrone", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DispatchResponse> FireDrone(
            @Valid @RequestBody FireDroneRequest fireDroneRequest,
            BindingResult bindingResult) throws Exception {
        Log logs = dispatchService.saveLog(fireDroneRequest.getDroneSerialNumber(), gson.toJson(fireDroneRequest),
                "fireDroneRequest");
        log.info("fireDroneRequest: " + gson.toJson(fireDroneRequest));
        InputValidator.validate(bindingResult);
        DispatchResponse dispatchResponse = dispatchService.fireDrone(fireDroneRequest, logs);
        log.info("fireDroneResponse: " + gson.toJson(dispatchResponse));
        dispatchService.updateLog(logs, gson.toJson(dispatchResponse));
        return new ResponseEntity<>(dispatchResponse, HttpStatus.OK);
    }
}
