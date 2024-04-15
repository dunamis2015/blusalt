package net.blusalt.dispatchapi.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import net.blusalt.dispatchapi.model.entity.Log;
import net.blusalt.dispatchapi.model.request.CompleteDroneLoadingRequest;
import net.blusalt.dispatchapi.model.request.FireDroneRequest;
import net.blusalt.dispatchapi.model.request.InitiateDroneLoadingRequest;
import net.blusalt.dispatchapi.model.request.RegisterDroneRequest;
import net.blusalt.dispatchapi.model.response.*;
import net.blusalt.dispatchapi.service.DispatchService;
import net.blusalt.dispatchapi.validator.InputValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(path = "/v1/getDroneLoadedItems", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetDroneLoadedItemsResponse> GetDroneLoadedItems(
            @Valid @RequestParam String droneSerialNumber
    ) throws Exception {
        Log logs = dispatchService.saveLog(droneSerialNumber, gson.toJson(droneSerialNumber),
                "getDroneLoadedItemsRequest");
        GetDroneLoadedItemsResponse getDroneLoadedItemsResponse = dispatchService
                .getDroneLoadedItems(droneSerialNumber, logs);
        log.info("GetDroneLoadedItemsResponse: " + gson.toJson(getDroneLoadedItemsResponse));
        dispatchService.updateLog(logs, gson.toJson(getDroneLoadedItemsResponse));
        return new ResponseEntity<>(getDroneLoadedItemsResponse, HttpStatus.OK);
    }

    @GetMapping(path = "/v1/getAvailableDrones", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetAvailableDronesResponse> GetAvailableDrones() throws Exception {
        Log logs = dispatchService.saveLog("", "", "getAvailableDrones");
        GetAvailableDronesResponse getAvailableDronesResponse = dispatchService.getAvailableDrones(logs);
        log.info("GetDroneLoadedItemsResponse: " + gson.toJson(getAvailableDronesResponse));
        dispatchService.updateLog(logs, gson.toJson(getAvailableDronesResponse));
        return new ResponseEntity<>(getAvailableDronesResponse, HttpStatus.OK);
    }

    @GetMapping(path = "/v1/getDroneBatteryLevel", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetDroneBatteryLevelResponse> GetDroneBatteryLevel(
            @Valid @RequestParam String droneSerialNumber
    ) throws Exception {
        Log logs = dispatchService.saveLog(droneSerialNumber, gson.toJson(droneSerialNumber),
                "getDroneBatteryLevel");
        GetDroneBatteryLevelResponse getDroneBatteryLevelResponse = dispatchService
                .getDroneBatteryLevel(droneSerialNumber, logs);
        log.info("GetDroneBatteryLevelResponse: " + gson.toJson(getDroneBatteryLevelResponse));
        dispatchService.updateLog(logs, gson.toJson(getDroneBatteryLevelResponse));
        return new ResponseEntity<>(getDroneBatteryLevelResponse, HttpStatus.OK);
    }
}
