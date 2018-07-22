package ro.autoepc.rabbitmqmonitoring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.autoepc.rabbitmqmonitoring.domain.Queue;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Queue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QueueRepository extends JpaRepository<Queue, Long> {

    Page<Queue> findAllByHostId(Pageable pageable, Long id);
}
