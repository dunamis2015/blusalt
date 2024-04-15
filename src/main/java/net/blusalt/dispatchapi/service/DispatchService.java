package net.blusalt.dispatchapi.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import net.blusalt.dispatchapi.exception.ConflictException;
import net.blusalt.dispatchapi.exception.NotFoundException;
import net.blusalt.dispatchapi.exception.ProcessingException;
import net.blusalt.dispatchapi.model.constant.DroneModels;
import net.blusalt.dispatchapi.model.constant.DroneStates;
import net.blusalt.dispatchapi.model.entity.Drones;
import net.blusalt.dispatchapi.model.entity.Log;
import net.blusalt.dispatchapi.model.entity.Medications;
import net.blusalt.dispatchapi.model.request.CompleteDroneLoadingRequest;
import net.blusalt.dispatchapi.model.request.FireDroneRequest;
import net.blusalt.dispatchapi.model.request.InitiateDroneLoadingRequest;
import net.blusalt.dispatchapi.model.request.RegisterDroneRequest;
import net.blusalt.dispatchapi.model.response.*;
import net.blusalt.dispatchapi.repository.DronesRepository;
import net.blusalt.dispatchapi.repository.LogRepository;
import net.blusalt.dispatchapi.repository.MedicationsRepository;
import net.blusalt.dispatchapi.util.Misc;
import net.javacrumbs.shedlock.core.LockAssert;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static net.blusalt.dispatchapi.util.Misc.addSecondsToDate;
import static net.blusalt.dispatchapi.util.Misc.convertEnumToList;

/**
 * @author Olusegun Adeoye
 */
@Slf4j
@Service
public class DispatchService {

    private final LogRepository logRepository;
    private final DronesRepository dronesRepository;
    private final MedicationsRepository medicationsRepository;
    private Gson gson;
    private final String DUPLICATE_DRONE_MSG = "Drone already exist: serialNumber- ";
    private final String DUPLICATE_LOADING_MSG = "Medication loading already initiated: loadingReference- ";
    private final String INVALID_DRONE_MSG = "Invalid Drone Model!";
    private final String DRONE_NOT_FOUND = "Drone with given serial number does not exist: serialNumber-";
    private final String MEDICATION_NOT_FOUND = "Loading request with the given reference does not exist: loadingReference-";
    private final String NOT_LOADING_STATE = "Medication/Drone is currently not in loading state: loadingReference-";
    private final String NOT_LOADED_STATE = "Drone is currently not loaded: droneSerialNumber-";
    private final Double BATTERY_MIN_LIMIT = 25.0;
    private final String LOW_BATTERY_MSG = "Drone Battery too low for loading!";
    private final String OVERLOAD_MSG = "Sorry, the medication weight will overload the drone";
    private final String DRONE_BUSY_MSG = "All drones are currently busy!";

    @Autowired
    public DispatchService(LogRepository logRepository, DronesRepository dronesRepository,
                           MedicationsRepository medicationsRepository) {
        this.logRepository = logRepository;
        this.dronesRepository = dronesRepository;
        this.medicationsRepository = medicationsRepository;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public GetDroneModelsResponse getDroneModels() {
        return GetDroneModelsResponse.buildResponse(convertEnumToList(DroneModels.values()));
    }

    public DispatchResponse registerDrone(RegisterDroneRequest registerDroneRequest, Log logs) {
        verifyUniqueSerialNumber(registerDroneRequest.getSerialNumber(), logs);
        validateModel(registerDroneRequest.getModel(), logs);
        SaveDrone(registerDroneRequest);
        return DispatchResponse.buildResponse();
    }

    public DispatchResponse initiateDroneLoading(InitiateDroneLoadingRequest initiateDroneLoadingRequest, Log logs) {
        validateUniqueLoadingReference(initiateDroneLoadingRequest.getLoadingReference(), logs);
        Drones drones = validateDrone(initiateDroneLoadingRequest, logs);
        saveMedications(initiateDroneLoadingRequest, drones);
        return DispatchResponse.buildResponse();
    }

    public DispatchResponse completeDroneLoading(CompleteDroneLoadingRequest completeDroneLoadingRequest, Log logs) {
        Medications medications = fetchMedication(completeDroneLoadingRequest.getLoadingReference());
        if (medications == null) {
            log.info(">>>>>>DispatchService: ".concat(MEDICATION_NOT_FOUND) + completeDroneLoadingRequest
                    .getLoadingReference());
            updateLog(logs, MEDICATION_NOT_FOUND + completeDroneLoadingRequest.getLoadingReference());
            throw new NotFoundException("Loading request with the given reference does not exist!");
        }
        if (!medications.getStatus().equals(String.valueOf(DroneStates.LOADING))) {
            log.info(">>>>>>DispatchService: ".concat(NOT_LOADING_STATE) + completeDroneLoadingRequest
                    .getLoadingReference());
            updateLog(logs, NOT_LOADING_STATE + completeDroneLoadingRequest.getLoadingReference());
            throw new ProcessingException("Medication/Drone is currently not in loading state");
        }
        updateStatus(medications);
        return DispatchResponse.buildResponse();
    }

    public DispatchResponse fireDrone(FireDroneRequest fireDroneRequest, Log logs) {
        Drones drones = fetchDrones(fireDroneRequest.getDroneSerialNumber());
        if (drones == null) {
            log.info(">>>>>>DispatchService: ".concat(DRONE_NOT_FOUND) + fireDroneRequest.getDroneSerialNumber());
            updateLog(logs, DRONE_NOT_FOUND + fireDroneRequest.getDroneSerialNumber());
            throw new NotFoundException("Drone with given serial number does not exist!");
        }
        if (!drones.getState().equals(String.valueOf(DroneStates.LOADED))) {
            log.info(">>>>>>DispatchService: ".concat(NOT_LOADED_STATE) + fireDroneRequest.getDroneSerialNumber());
            updateLog(logs, NOT_LOADED_STATE + fireDroneRequest.getDroneSerialNumber());
            throw new ProcessingException("Drone is currently not loaded!");
        }
        updateDeliveryInfo(drones, fireDroneRequest);
        return DispatchResponse.buildResponse();
    }

    public GetDroneLoadedItemsResponse getDroneLoadedItems(String droneSerialNumber, Log logs) {
        Drones drone = fetchDrones(droneSerialNumber);
        if (drone == null) {
            log.info(">>>>>>DispatchService: ".concat(DRONE_NOT_FOUND) + droneSerialNumber);
            updateLog(logs, DRONE_NOT_FOUND + droneSerialNumber);
            throw new NotFoundException("Drone with given serial number does not exist!");
        }
        if (!drone.getState().equals(String.valueOf(DroneStates.LOADED))) {
            log.info(">>>>>>DispatchService: ".concat(NOT_LOADED_STATE) + droneSerialNumber);
            updateLog(logs, NOT_LOADED_STATE + droneSerialNumber);
            throw new ProcessingException("Drone is currently not loaded!");
        }
        List<Medications> medications = medicationsRepository.findAllByMappedAndDroneSerialNumber(true,
                droneSerialNumber);
        return GetDroneLoadedItemsResponse.buildLoadedDroneResponse(medications);
    }

    public GetAvailableDronesResponse getAvailableDrones(Log logs) {
        List<Drones> drones = dronesRepository.findAllByState(String.valueOf(DroneStates.IDLE));
        if (drones == null) {
            log.info(">>>>>>DispatchService: ".concat(DRONE_BUSY_MSG) );
            updateLog(logs, DRONE_BUSY_MSG);
            throw new NotFoundException(DRONE_BUSY_MSG);
        }
        return GetAvailableDronesResponse.buildDroneListResponse(drones);
    }

    public GetDroneBatteryLevelResponse getDroneBatteryLevel(String droneSerialNumber, Log logs) {
        Drones drone = fetchDrones(droneSerialNumber);
        if (drone == null) {
            log.info(">>>>>>DispatchService: ".concat(DRONE_NOT_FOUND) + droneSerialNumber);
            updateLog(logs, DRONE_NOT_FOUND + droneSerialNumber);
            throw new NotFoundException("Drone with given serial number does not exist!");
        }
        return GetDroneBatteryLevelResponse.buildBatteryLevelResponse(drone);
    }

    @Scheduled(cron = "${monitorDrone.cron}",
            zone = "${monitorDrone.cronTimezone}")
    @SchedulerLock(name = "dispatchScheduler")
    public void monitorDrones() {
        LockAssert.assertLocked();
        log.info(">>>>>>>DispatchService- initiated cron job");
        List<Drones> drones = dronesRepository.findAllByState(String.valueOf(DroneStates.DELIVERING));
        try {
            if (drones != null) {
                for (Drones drone : drones) {
                    if ((addSecondsToDate(drone.getDateTimeFired(), drone.getTimeToDestinationInSeconds()))
                            .before(Misc.now())) {
                        drone.setState(String.valueOf(DroneStates.RETURNING));
                        dronesRepository.save(drone);
                    }
                }
            }
        List<Drones> dronez = dronesRepository.findAllByState(String.valueOf(DroneStates.RETURNING));
        if (dronez != null) {
            for (Drones drone : dronez) {
                if (drone.getReturnDateTime().before(Misc.now())) {
                    drone.setState(String.valueOf(DroneStates.IDLE));
                    drone.setBatteryCapacity(drone.getBatteryCapacity() - drone.getJourneyBatteryConsumption());
                    dronesRepository.save(drone);
                    List<Medications> medications = medicationsRepository
                            .findAllByMappedAndDroneSerialNumberAndStatus(true, drone.getSerialNumber(),
                                    String.valueOf(DroneStates.DELIVERING));
                    if (medications != null) {
                        for (Medications medication : medications) {
                            medication.setMapped(false);
                            medication.setStatus(String.valueOf(DroneStates.DELIVERED));
                            medicationsRepository.save(medication);
                        }
                    }
                }
            }
        }

        List<Drones> allDrones = dronesRepository.findAll();
            List<BatteryCheckResponse> batteryCheckResponses = new ArrayList<>();
        if (allDrones != null) {
            for (Drones drones1 : allDrones) {
                BatteryCheckResponse batteryCheckResponse = new BatteryCheckResponse();
                batteryCheckResponse.setBatteryLevel(drones1.getBatteryCapacity());
                batteryCheckResponse.setDroneSerialNumber(drones1.getSerialNumber());
                batteryCheckResponses.add(batteryCheckResponse);
            }
        }
        Log logs = new Log();
        logs.setActionType("batteryCheckAuditLog");
        logs.setResponse(gson.toJson(batteryCheckResponses));
        logRepository.save(logs);
        } catch (Exception e) {
//            throw new RuntimeException(e);
        }
    }

    private void updateDeliveryInfo(Drones drones, FireDroneRequest fireDroneRequest) {
        drones.setDeliveryLocation(fireDroneRequest.getDeliveryLocation());
        drones.setStartLocation(fireDroneRequest.getStartLocation());
        drones.setReturnDateTime(Timestamp.valueOf(fireDroneRequest.getReturnDateTime()));
        drones.setJourneyBatteryConsumption(fireDroneRequest.getJourneyBatteryConsumption());
        drones.setTimeToDestinationInSeconds(fireDroneRequest.getTimeToDestinationInSeconds());
        drones.setState(String.valueOf(DroneStates.DELIVERING));
        drones.setDateTimeFired(Misc.now());
        dronesRepository.save(drones);
        List<Medications> medications = medicationsRepository.findAllByMappedAndDroneSerialNumberAndStatus(true,
                fireDroneRequest.getDroneSerialNumber(), String.valueOf(DroneStates.LOADED));
        for(Medications medication : medications) {
            medication.setStatus(String.valueOf(DroneStates.DELIVERING));
            medicationsRepository.save(medication);
        }
    }

    private void updateStatus(Medications medications) {
        medications.setStatus(String.valueOf(DroneStates.LOADED));
        medicationsRepository.save(medications);
        Drones drones = fetchDrones(medications.getDroneSerialNumber());
        drones.setState(String.valueOf(DroneStates.LOADED));
        dronesRepository.save(drones);
    }

    private void saveMedications(InitiateDroneLoadingRequest initiateDroneLoadingRequest, Drones drones) {
        Medications medications =  new Medications();
        medications.setLoadingReference(initiateDroneLoadingRequest.getLoadingReference());
        medications.setMedicationCode(initiateDroneLoadingRequest.getMedicationCode());
        medications.setMedicationImage(initiateDroneLoadingRequest.getMedicationImage());
        medications.setMedicationName(initiateDroneLoadingRequest.getMedicationName());
        medications.setMedicationWeight(initiateDroneLoadingRequest.getMedicationWeight());
        medications.setMapped(true);
        medications.setStatus(String.valueOf(DroneStates.LOADING));
        medications.setDroneSerialNumber(initiateDroneLoadingRequest.getDroneSerialNumber());
        medicationsRepository.save(medications);
        drones.setState(String.valueOf(DroneStates.LOADING));
        dronesRepository.save(drones);
    }

    private void SaveDrone(RegisterDroneRequest registerDroneRequest) {
        Drones drones = new Drones();
        drones.setBatteryCapacity(registerDroneRequest.getBatteryCapacity());
        drones.setModel(registerDroneRequest.getModel());
        drones.setWeight(registerDroneRequest.getWeight());
        drones.setSerialNumber(registerDroneRequest.getSerialNumber());
        drones.setState(String.valueOf(DroneStates.IDLE));
        dronesRepository.save(drones);
    }

    private boolean isModelValid(String model) {
        try {
       Enum.valueOf(DroneModels.class, model);
       return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void validateModel(String model, Log logs) {
        if (!isModelValid(model)) {
            log.info(">>>>>>DispatchService: ".concat(INVALID_DRONE_MSG));
            updateLog(logs, INVALID_DRONE_MSG );
            throw new ProcessingException(INVALID_DRONE_MSG);
        }
    }

    private void verifyUniqueSerialNumber(String serialNumber, Log logs) {
        Drones drones = fetchDrones(serialNumber);
        if (drones != null) {
            log.info(">>>>>>DispatchService: ".concat(DUPLICATE_DRONE_MSG) + serialNumber);
            updateLog(logs, DUPLICATE_DRONE_MSG + serialNumber);
            throw new ConflictException("Drone already exist");
        }
    }

    private void validateUniqueLoadingReference(String loadingReference, Log logs) {
        Medications medications = fetchMedication(loadingReference);
        if (medications != null) {
            log.info(">>>>>>DispatchService: ".concat(DUPLICATE_LOADING_MSG) + loadingReference);
            updateLog(logs, DUPLICATE_LOADING_MSG + loadingReference);
            throw new ConflictException("Medication loading initiated already!");
        }
    }

    private Medications fetchMedication(String loadingReference) {
        Medications medications = medicationsRepository.findOneByLoadingReference(loadingReference);
        return medications;
    }

    private Drones fetchDrones(String serialNumber) {
        Drones drones = dronesRepository.findOneBySerialNumber(serialNumber);
        return drones;
    }

    private Drones validateDrone(InitiateDroneLoadingRequest initiateDroneLoadingRequest, Log logs) {
        Drones drones = fetchDrones(initiateDroneLoadingRequest.getDroneSerialNumber());
        if (drones == null) {
            log.info(">>>>>>DispatchService: ".concat(DRONE_NOT_FOUND) + initiateDroneLoadingRequest
                    .getDroneSerialNumber());
            updateLog(logs, DRONE_NOT_FOUND + initiateDroneLoadingRequest.getDroneSerialNumber());
            throw new NotFoundException("Drone with given serial number does not exist!");
        }
        if (drones.getBatteryCapacity() < BATTERY_MIN_LIMIT) {
            log.info(">>>>>>DispatchService: ".concat(LOW_BATTERY_MSG) + initiateDroneLoadingRequest
                    .getDroneSerialNumber());
            updateLog(logs, LOW_BATTERY_MSG + initiateDroneLoadingRequest.getDroneSerialNumber());
            throw new ProcessingException(LOW_BATTERY_MSG);
        }
        if (drones.getState().equals(String.valueOf(DroneStates.IDLE)) || drones.getState().equals(String
                .valueOf(DroneStates.LOADED))) {
            List<Medications> medications = medicationsRepository.findAllByMappedAndDroneSerialNumber(true,
                    initiateDroneLoadingRequest.getDroneSerialNumber());
            Double medicationWeight = 0.0;
            for(Medications medication : medications) {
                medicationWeight = medicationWeight+medication.getMedicationWeight();
            }
            if ((medicationWeight+initiateDroneLoadingRequest.getMedicationWeight()) > drones.getWeight()) {
                log.info(">>>>>>DispatchService: ".concat(OVERLOAD_MSG));
                updateLog(logs, OVERLOAD_MSG);
                throw new ProcessingException(OVERLOAD_MSG);
            }
        }
        return drones;
    }

    public Log saveLog(String reference, String request, String actionType) {
        Log log = new Log();
        log.setReference(reference);
        log.setRequest(request);
        log.setActionType(actionType);
        Log logs = logRepository.save(log);
        return logs;
    }

    public void updateLog(Log log, String response) {
        log.setResponse(response);
        logRepository.save(log);
    }
}
