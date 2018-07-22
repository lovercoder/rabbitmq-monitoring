package ro.autoepc.rabbitmqmonitoring.web.rest;

import com.codahale.metrics.annotation.Timed;
import ro.autoepc.rabbitmqmonitoring.service.HostService;
import ro.autoepc.rabbitmqmonitoring.web.rest.errors.BadRequestAlertException;
import ro.autoepc.rabbitmqmonitoring.web.rest.util.HeaderUtil;
import ro.autoepc.rabbitmqmonitoring.web.rest.util.PaginationUtil;
import ro.autoepc.rabbitmqmonitoring.service.dto.HostDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Host.
 */
@RestController
@RequestMapping("/api")
public class HostResource {

    private final Logger log = LoggerFactory.getLogger(HostResource.class);

    private static final String ENTITY_NAME = "host";

    private final HostService hostService;

    public HostResource(HostService hostService) {
        this.hostService = hostService;
    }

    /**
     * POST  /hosts : Create a new host.
     *
     * @param hostDTO the hostDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new hostDTO, or with status 400 (Bad Request) if the host has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/hosts")
    @Timed
    public ResponseEntity<HostDTO> createHost(@Valid @RequestBody HostDTO hostDTO) throws URISyntaxException {
        log.debug("REST request to save Host : {}", hostDTO);
        if (hostDTO.getId() != null) {
            throw new BadRequestAlertException("A new host cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HostDTO result = hostService.save(hostDTO);
        return ResponseEntity.created(new URI("/api/hosts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hosts : Updates an existing host.
     *
     * @param hostDTO the hostDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated hostDTO,
     * or with status 400 (Bad Request) if the hostDTO is not valid,
     * or with status 500 (Internal Server Error) if the hostDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/hosts")
    @Timed
    public ResponseEntity<HostDTO> updateHost(@Valid @RequestBody HostDTO hostDTO) throws URISyntaxException {
        log.debug("REST request to update Host : {}", hostDTO);
        if (hostDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HostDTO result = hostService.save(hostDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, hostDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hosts : get all the hosts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of hosts in body
     */
    @GetMapping("/hosts")
    @Timed
    public ResponseEntity<List<HostDTO>> getAllHosts(Pageable pageable) {
        log.debug("REST request to get a page of Hosts");
        Page<HostDTO> page = hostService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hosts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hosts/:id : get the "id" host.
     *
     * @param id the id of the hostDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the hostDTO, or with status 404 (Not Found)
     */
    @GetMapping("/hosts/{id}")
    @Timed
    public ResponseEntity<HostDTO> getHost(@PathVariable Long id) {
        log.debug("REST request to get Host : {}", id);
        Optional<HostDTO> hostDTO = hostService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hostDTO);
    }

    /**
     * DELETE  /hosts/:id : delete the "id" host.
     *
     * @param id the id of the hostDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/hosts/{id}")
    @Timed
    public ResponseEntity<Void> deleteHost(@PathVariable Long id) {
        log.debug("REST request to delete Host : {}", id);
        hostService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
