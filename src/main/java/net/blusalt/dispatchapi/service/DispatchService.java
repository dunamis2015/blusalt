package net.blusalt.dispatchapi.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import net.blusalt.dispatchapi.exception.ConflictException;
import net.blusalt.dispatchapi.exception.ProcessingException;
import net.blusalt.dispatchapi.model.constant.DroneModels;
import net.blusalt.dispatchapi.model.constant.DroneStates;
import net.blusalt.dispatchapi.model.entity.Drones;
import net.blusalt.dispatchapi.model.entity.Log;
import net.blusalt.dispatchapi.model.request.InitiateDroneLoadingRequest;
import net.blusalt.dispatchapi.model.request.RegisterDroneRequest;
import net.blusalt.dispatchapi.model.response.GetDroneModelsResponse;
import net.blusalt.dispatchapi.model.response.DispatchResponse;
import net.blusalt.dispatchapi.repository.DronesRepository;
import net.blusalt.dispatchapi.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static net.blusalt.dispatchapi.util.Misc.convertEnumToList;

/**
 * @author Olusegun Adeoye
 */
@Slf4j
@Service
public class DispatchService {

    private final LogRepository logRepository;
    private final DronesRepository dronesRepository;
    private Gson gson;
    private final String DUPLICATE_DRONE_MSG = "Drone already exist: serialNumber- ";
    private final String INVALID_DRONE_MSG = "Invalid Drone Model!";

    @Autowired
    public DispatchService(LogRepository logRepository, DronesRepository dronesRepository) {
        this.logRepository = logRepository;
        this.dronesRepository = dronesRepository;
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

    private Drones fetchDrones(String serialNumber) {
        Drones drones = dronesRepository.findOneBySerialNumber(serialNumber);
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

    public DispatchResponse initiateDroneLoading(InitiateDroneLoadingRequest initiateDroneLoadingRequest) {
        return null;
    }
}
