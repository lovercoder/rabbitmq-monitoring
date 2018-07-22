package ro.autoepc.rabbitmqmonitoring.service;

import ro.autoepc.rabbitmqmonitoring.service.dto.CredentialDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Credential.
 */
public interface CredentialService {

    /**
     * Save a credential.
     *
     * @param credentialDTO the entity to save
     * @return the persisted entity
     */
    CredentialDTO save(CredentialDTO credentialDTO);

    /**
     * Get all the credentials.
     *
     * @return the list of entities
     */
    List<CredentialDTO> findAll();


    /**
     * Get the "id" credential.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CredentialDTO> findOne(Long id);

    /**
     * Delete the "id" credential.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
