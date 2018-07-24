package ro.autoepc.rabbitmqmonitoring.repository;

import org.springframework.transaction.annotation.Transactional;
import ro.autoepc.rabbitmqmonitoring.domain.Queue;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Queue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QueueRepository extends JpaRepository<Queue, Long> {

    @Transactional
    List<Queue> findAllByHostId(Long id);
}
