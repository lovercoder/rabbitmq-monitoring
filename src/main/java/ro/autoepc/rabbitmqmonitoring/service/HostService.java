package ro.autoepc.rabbitmqmonitoring.service;

import ro.autoepc.rabbitmqmonitoring.service.dto.HostDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Host.
 */
public interface HostService {

    /**
     * Save a host.
     *
     * @param hostDTO the entity to save
     * @return the persisted entity
     */
    HostDTO save(HostDTO hostDTO);

    /**
     * Get all the hosts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<HostDTO> findAll(Pageable pageable);


    /**
     * Get the "id" host.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<HostDTO> findOne(Long id);

    /**
     * Delete the "id" host.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
