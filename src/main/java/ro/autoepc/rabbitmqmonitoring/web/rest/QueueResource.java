package ro.autoepc.rabbitmqmonitoring.web.rest;

import com.codahale.metrics.annotation.Timed;
import ro.autoepc.rabbitmqmonitoring.service.QueueService;
import ro.autoepc.rabbitmqmonitoring.web.rest.errors.BadRequestAlertException;
import ro.autoepc.rabbitmqmonitoring.web.rest.util.HeaderUtil;
import ro.autoepc.rabbitmqmonitoring.web.rest.util.PaginationUtil;
import ro.autoepc.rabbitmqmonitoring.service.dto.QueueDTO;
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
 * REST controller for managing Queue.
 */
@RestController
@RequestMapping("/api")
public class QueueResource {

    private final Logger log = LoggerFactory.getLogger(QueueResource.class);

    private static final String ENTITY_NAME = "queue";

    private final QueueService queueService;

    public QueueResource(QueueService queueService) {
        this.queueService = queueService;
    }

    /**
     * POST  /queues : Create a new queue.
     *
     * @param queueDTO the queueDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new queueDTO, or with status 400 (Bad Request) if the queue has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/queues")
    @Timed
    public ResponseEntity<QueueDTO> createQueue(@Valid @RequestBody QueueDTO queueDTO) throws URISyntaxException {
        log.debug("REST request to save Queue : {}", queueDTO);
        if (queueDTO.getId() != null) {
            throw new BadRequestAlertException("A new queue cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QueueDTO result = queueService.save(queueDTO);
        return ResponseEntity.created(new URI("/api/queues/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /queues : Updates an existing queue.
     *
     * @param queueDTO the queueDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated queueDTO,
     * or with status 400 (Bad Request) if the queueDTO is not valid,
     * or with status 500 (Internal Server Error) if the queueDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/queues")
    @Timed
    public ResponseEntity<QueueDTO> updateQueue(@Valid @RequestBody QueueDTO queueDTO) throws URISyntaxException {
        log.debug("REST request to update Queue : {}", queueDTO);
        if (queueDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        QueueDTO result = queueService.save(queueDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, queueDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /queues : get all the queues.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of queues in body
     */
    @GetMapping("/queues")
    @Timed
    public ResponseEntity<List<QueueDTO>> getAllQueues(Pageable pageable) {
        log.debug("REST request to get a page of Queues");
        Page<QueueDTO> page = queueService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/queues");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /queues/:id : get the "id" queue.
     *
     * @param id the id of the queueDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the queueDTO, or with status 404 (Not Found)
     */
    @GetMapping("/queues/{id}")
    @Timed
    public ResponseEntity<QueueDTO> getQueue(@PathVariable Long id) {
        log.debug("REST request to get Queue : {}", id);
        Optional<QueueDTO> queueDTO = queueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(queueDTO);
    }

    /**
     * DELETE  /queues/:id : delete the "id" queue.
     *
     * @param id the id of the queueDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/queues/{id}")
    @Timed
    public ResponseEntity<Void> deleteQueue(@PathVariable Long id) {
        log.debug("REST request to delete Queue : {}", id);
        queueService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
