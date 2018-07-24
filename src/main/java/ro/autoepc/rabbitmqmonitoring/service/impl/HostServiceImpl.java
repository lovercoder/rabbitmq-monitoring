package ro.autoepc.rabbitmqmonitoring.service.impl;

import ro.autoepc.rabbitmqmonitoring.service.HostService;
import ro.autoepc.rabbitmqmonitoring.domain.Host;
import ro.autoepc.rabbitmqmonitoring.repository.HostRepository;
import ro.autoepc.rabbitmqmonitoring.service.dto.HostDTO;
import ro.autoepc.rabbitmqmonitoring.service.mapper.HostMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

/**
 * Service Implementation for managing Host.
 */
@Service
@Transactional
public class HostServiceImpl implements HostService {

    private final Logger log = LoggerFactory.getLogger(HostServiceImpl.class);

    private final HostRepository hostRepository;

    private final HostMapper hostMapper;

    public HostServiceImpl(HostRepository hostRepository, HostMapper hostMapper) {
        this.hostRepository = hostRepository;
        this.hostMapper = hostMapper;
    }

    /**
     * Save a host.
     *
     * @param hostDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public HostDTO save(HostDTO hostDTO) {
        log.debug("Request to save Host : {}", hostDTO);
        Host host = hostMapper.toEntity(hostDTO);
        host = hostRepository.save(host);
        return hostMapper.toDto(host);
    }

    /**
     * Get all the hosts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<HostDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Hosts");
        return hostRepository.findAll(pageable)
            .map(hostMapper::toDto);
    }


    /**
     * Get one host by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<HostDTO> findOne(Long id) {
        log.debug("Request to get Host : {}", id);
        return hostRepository.findById(id)
            .map(hostMapper::toDto);
    }

    /**
     * Delete the host by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Host : {}", id);
        hostRepository.deleteById(id);
    }

}
