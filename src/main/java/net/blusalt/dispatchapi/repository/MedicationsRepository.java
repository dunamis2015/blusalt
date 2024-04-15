package net.blusalt.dispatchapi.repository;

import net.blusalt.dispatchapi.model.entity.Medications;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Olusegun Adeoye
 */
@Repository
public interface MedicationsRepository extends
        PagingAndSortingRepository<Medications, Long> {
    Medications findOneByLoadingReference(String loadingReference);
    List<Medications> findAllByMappedAndDroneSerialNumber(Boolean mapped, String droneSerialNumber);
    List<Medications> findAllByMappedAndDroneSerialNumberAndStatus(Boolean mapped, String droneSerialNumber,
                                                                   String status);
}
