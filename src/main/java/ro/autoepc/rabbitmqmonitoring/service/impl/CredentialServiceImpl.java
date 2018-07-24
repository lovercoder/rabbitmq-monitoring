package ro.autoepc.rabbitmqmonitoring.service.impl;

import ro.autoepc.rabbitmqmonitoring.service.CredentialService;
import ro.autoepc.rabbitmqmonitoring.domain.Credential;
import ro.autoepc.rabbitmqmonitoring.repository.CredentialRepository;
import ro.autoepc.rabbitmqmonitoring.service.dto.CredentialDTO;
import ro.autoepc.rabbitmqmonitoring.service.mapper.CredentialMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Credential.
 */
@Service
@Transactional
public class CredentialServiceImpl implements CredentialService {

    private final Logger log = LoggerFactory.getLogger(CredentialServiceImpl.class);

    private final CredentialRepository credentialRepository;

    private final CredentialMapper credentialMapper;

    public CredentialServiceImpl(CredentialRepository credentialRepository, CredentialMapper credentialMapper) {
        this.credentialRepository = credentialRepository;
        this.credentialMapper = credentialMapper;
    }

    /**
     * Save a credential.
     *
     * @param credentialDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CredentialDTO save(CredentialDTO credentialDTO) {
        log.debug("Request to save Credential : {}", credentialDTO);
        Credential credential = credentialMapper.toEntity(credentialDTO);
        credential = credentialRepository.save(credential);
        return credentialMapper.toDto(credential);
    }

    /**
     * Get all the credentials.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CredentialDTO> findAll() {
        log.debug("Request to get all Credentials");
        return credentialRepository.findAll().stream()
            .map(credentialMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one credential by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CredentialDTO> findOne(Long id) {
        log.debug("Request to get Credential : {}", id);
        return credentialRepository.findById(id)
            .map(credentialMapper::toDto);
    }

    /**
     * Delete the credential by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Credential : {}", id);
        credentialRepository.deleteById(id);
    }
}
