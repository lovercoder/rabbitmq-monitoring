package ro.autoepc.rabbitmqmonitoring.service;

import org.springframework.transaction.annotation.Transactional;
import ro.autoepc.rabbitmqmonitoring.service.dto.QueueDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Queue.
 */
public interface QueueService {

    /**
     * Save a queue.
     *
     * @param queueDTO the entity to save
     * @return the persisted entity
     */
    QueueDTO save(QueueDTO queueDTO);

    /**
     * Get all the queues.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<QueueDTO> findAll(Pageable pageable);


    /**
     * Get the "id" queue.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<QueueDTO> findOne(Long id);

    /**
     * Delete the "id" queue.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    @Transactional(readOnly = true)
    Page<QueueDTO> findAllByHostId(Pageable pageable, Long id);
}
