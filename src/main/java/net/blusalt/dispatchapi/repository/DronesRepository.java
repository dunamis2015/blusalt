package net.blusalt.dispatchapi.repository;

import net.blusalt.dispatchapi.model.entity.Drones;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Olusegun Adeoye
 */
@Repository
public interface DronesRepository extends
        PagingAndSortingRepository<Drones, Long> {
    Drones findOneBySerialNumber(String serialNumber);
    List<Drones> findAllByState(String state);
    List<Drones> findAll();
}
