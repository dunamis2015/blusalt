package net.blusalt.dispatchapi.repository;

import net.blusalt.dispatchapi.model.entity.Log;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Olusegun Adeoye
 */
@Repository
public interface LogRepository extends
        PagingAndSortingRepository<Log, Long> {
  Log findOneById(Integer id);
//  Log findOneByReferenceAndCreatedAtBetweenAndCreatedAt(String reference, Timestamp createdAt);
}