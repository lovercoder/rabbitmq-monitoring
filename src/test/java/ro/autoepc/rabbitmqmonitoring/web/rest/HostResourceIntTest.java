package ro.autoepc.rabbitmqmonitoring.web.rest;

import ro.autoepc.rabbitmqmonitoring.RabbitmqMonitoringApp;

import ro.autoepc.rabbitmqmonitoring.domain.Host;
import ro.autoepc.rabbitmqmonitoring.repository.HostRepository;
import ro.autoepc.rabbitmqmonitoring.service.HostService;
import ro.autoepc.rabbitmqmonitoring.service.dto.HostDTO;
import ro.autoepc.rabbitmqmonitoring.service.mapper.HostMapper;
import ro.autoepc.rabbitmqmonitoring.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static ro.autoepc.rabbitmqmonitoring.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ro.autoepc.rabbitmqmonitoring.domain.enumeration.State;
/**
 * Test class for the HostResource REST controller.
 *
 * @see HostResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RabbitmqMonitoringApp.class)
public class HostResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DNS = "AAAAAAAAAA";
    private static final String UPDATED_DNS = "BBBBBBBBBB";

    private static final Integer DEFAULT_PORT = 1;
    private static final Integer UPDATED_PORT = 2;

    private static final State DEFAULT_STATE = State.ENABLED;
    private static final State UPDATED_STATE = State.DISABLED;

    @Autowired
    private HostRepository hostRepository;


    @Autowired
    private HostMapper hostMapper;
    

    @Autowired
    private HostService hostService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHostMockMvc;

    private Host host;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HostResource hostResource = new HostResource(hostService);
        this.restHostMockMvc = MockMvcBuilders.standaloneSetup(hostResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Host createEntity(EntityManager em) {
        Host host = new Host()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .dns(DEFAULT_DNS)
            .port(DEFAULT_PORT)
            .state(DEFAULT_STATE);
        return host;
    }

    @Before
    public void initTest() {
        host = createEntity(em);
    }

    @Test
    @Transactional
    public void createHost() throws Exception {
        int databaseSizeBeforeCreate = hostRepository.findAll().size();

        // Create the Host
        HostDTO hostDTO = hostMapper.toDto(host);
        restHostMockMvc.perform(post("/api/hosts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hostDTO)))
            .andExpect(status().isCreated());

        // Validate the Host in the database
        List<Host> hostList = hostRepository.findAll();
        assertThat(hostList).hasSize(databaseSizeBeforeCreate + 1);
        Host testHost = hostList.get(hostList.size() - 1);
        assertThat(testHost.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHost.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testHost.getDns()).isEqualTo(DEFAULT_DNS);
        assertThat(testHost.getPort()).isEqualTo(DEFAULT_PORT);
        assertThat(testHost.getState()).isEqualTo(DEFAULT_STATE);
    }

    @Test
    @Transactional
    public void createHostWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hostRepository.findAll().size();

        // Create the Host with an existing ID
        host.setId(1L);
        HostDTO hostDTO = hostMapper.toDto(host);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHostMockMvc.perform(post("/api/hosts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hostDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Host in the database
        List<Host> hostList = hostRepository.findAll();
        assertThat(hostList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = hostRepository.findAll().size();
        // set the field null
        host.setName(null);

        // Create the Host, which fails.
        HostDTO hostDTO = hostMapper.toDto(host);

        restHostMockMvc.perform(post("/api/hosts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hostDTO)))
            .andExpect(status().isBadRequest());

        List<Host> hostList = hostRepository.findAll();
        assertThat(hostList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = hostRepository.findAll().size();
        // set the field null
        host.setDescription(null);

        // Create the Host, which fails.
        HostDTO hostDTO = hostMapper.toDto(host);

        restHostMockMvc.perform(post("/api/hosts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hostDTO)))
            .andExpect(status().isBadRequest());

        List<Host> hostList = hostRepository.findAll();
        assertThat(hostList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDnsIsRequired() throws Exception {
        int databaseSizeBeforeTest = hostRepository.findAll().size();
        // set the field null
        host.setDns(null);

        // Create the Host, which fails.
        HostDTO hostDTO = hostMapper.toDto(host);

        restHostMockMvc.perform(post("/api/hosts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hostDTO)))
            .andExpect(status().isBadRequest());

        List<Host> hostList = hostRepository.findAll();
        assertThat(hostList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPortIsRequired() throws Exception {
        int databaseSizeBeforeTest = hostRepository.findAll().size();
        // set the field null
        host.setPort(null);

        // Create the Host, which fails.
        HostDTO hostDTO = hostMapper.toDto(host);

        restHostMockMvc.perform(post("/api/hosts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hostDTO)))
            .andExpect(status().isBadRequest());

        List<Host> hostList = hostRepository.findAll();
        assertThat(hostList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHosts() throws Exception {
        // Initialize the database
        hostRepository.saveAndFlush(host);

        // Get all the hostList
        restHostMockMvc.perform(get("/api/hosts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(host.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].dns").value(hasItem(DEFAULT_DNS.toString())))
            .andExpect(jsonPath("$.[*].port").value(hasItem(DEFAULT_PORT)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())));
    }
    

    @Test
    @Transactional
    public void getHost() throws Exception {
        // Initialize the database
        hostRepository.saveAndFlush(host);

        // Get the host
        restHostMockMvc.perform(get("/api/hosts/{id}", host.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(host.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.dns").value(DEFAULT_DNS.toString()))
            .andExpect(jsonPath("$.port").value(DEFAULT_PORT))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingHost() throws Exception {
        // Get the host
        restHostMockMvc.perform(get("/api/hosts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHost() throws Exception {
        // Initialize the database
        hostRepository.saveAndFlush(host);

        int databaseSizeBeforeUpdate = hostRepository.findAll().size();

        // Update the host
        Host updatedHost = hostRepository.findById(host.getId()).get();
        // Disconnect from session so that the updates on updatedHost are not directly saved in db
        em.detach(updatedHost);
        updatedHost
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .dns(UPDATED_DNS)
            .port(UPDATED_PORT)
            .state(UPDATED_STATE);
        HostDTO hostDTO = hostMapper.toDto(updatedHost);

        restHostMockMvc.perform(put("/api/hosts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hostDTO)))
            .andExpect(status().isOk());

        // Validate the Host in the database
        List<Host> hostList = hostRepository.findAll();
        assertThat(hostList).hasSize(databaseSizeBeforeUpdate);
        Host testHost = hostList.get(hostList.size() - 1);
        assertThat(testHost.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHost.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testHost.getDns()).isEqualTo(UPDATED_DNS);
        assertThat(testHost.getPort()).isEqualTo(UPDATED_PORT);
        assertThat(testHost.getState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    @Transactional
    public void updateNonExistingHost() throws Exception {
        int databaseSizeBeforeUpdate = hostRepository.findAll().size();

        // Create the Host
        HostDTO hostDTO = hostMapper.toDto(host);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHostMockMvc.perform(put("/api/hosts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hostDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Host in the database
        List<Host> hostList = hostRepository.findAll();
        assertThat(hostList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHost() throws Exception {
        // Initialize the database
        hostRepository.saveAndFlush(host);

        int databaseSizeBeforeDelete = hostRepository.findAll().size();

        // Get the host
        restHostMockMvc.perform(delete("/api/hosts/{id}", host.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Host> hostList = hostRepository.findAll();
        assertThat(hostList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Host.class);
        Host host1 = new Host();
        host1.setId(1L);
        Host host2 = new Host();
        host2.setId(host1.getId());
        assertThat(host1).isEqualTo(host2);
        host2.setId(2L);
        assertThat(host1).isNotEqualTo(host2);
        host1.setId(null);
        assertThat(host1).isNotEqualTo(host2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HostDTO.class);
        HostDTO hostDTO1 = new HostDTO();
        hostDTO1.setId(1L);
        HostDTO hostDTO2 = new HostDTO();
        assertThat(hostDTO1).isNotEqualTo(hostDTO2);
        hostDTO2.setId(hostDTO1.getId());
        assertThat(hostDTO1).isEqualTo(hostDTO2);
        hostDTO2.setId(2L);
        assertThat(hostDTO1).isNotEqualTo(hostDTO2);
        hostDTO1.setId(null);
        assertThat(hostDTO1).isNotEqualTo(hostDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(hostMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(hostMapper.fromId(null)).isNull();
    }
}
