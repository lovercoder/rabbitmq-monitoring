package ro.autoepc.rabbitmqmonitoring.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import ro.autoepc.rabbitmqmonitoring.domain.Credential;
import ro.autoepc.rabbitmqmonitoring.domain.Host;
import ro.autoepc.rabbitmqmonitoring.domain.Queue;
import ro.autoepc.rabbitmqmonitoring.repository.CredentialRepository;
import ro.autoepc.rabbitmqmonitoring.repository.HostRepository;
import ro.autoepc.rabbitmqmonitoring.repository.QueueRepository;


import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.TimeoutException;

@Controller
public class RabbitMqUpdaterService {

    private final Logger log = LoggerFactory.getLogger(RabbitMqUpdaterService.class);

    // Which is auto-generated by Spring, we will use it to handle the data
    @Autowired // This means to get the bean called hostRepository
    private HostRepository hostRepository;
    @Autowired // This means to get the bean called queuesRepository
    private QueueRepository queueRepository;
    @Autowired // This means to get the bean called credentialsRepository
    private CredentialRepository credentialRepository;


    @Scheduled(fixedRate = 50000)
    @Transactional
    public void pullHostQueueCountAll() {
        //Looping through all the Hosts
        List<Host> allHosts = hostRepository.findAll();
        for (Host h : allHosts) {
            if (h.getState().toString().equals("ENABLED")) { //Checking and pulling counters only for enabled hosts
                String hostName = h.getName();
                String hostDns = h.getDns();
                Long hostId = h.getId();
                //Looping through all the Queues on a specific host
                Iterable<Queue> allQueues = queueRepository.findAllByHostId(hostId);
                for (Queue q : allQueues) {
                    String queueName = q.getName();
                    Long queueId = q.getId();
                    if (hostName != null && queueId != null && queueName != null) {
                        log.info("Pulling Queue Counters: " + queueName + " from Host: " + hostName);
                        Queue foundQueue = queueRepository.getOne(queueId);
                        log.info("Queue: " + queueName + " for Host: " + hostName + " found, pulling counters");
                        Credential foundCreds = credentialRepository.findByHostId(hostId); // Creating a new object to fetch credentials for a specified host and queue
                        if (foundCreds != null) {
                            String username = foundCreds.getUsername();
                            String password = foundCreds.getPassword();
                            Integer port = h.getPort();
                            //Accessing the rabbitMQ host and querying the queue counter
                            RabbitMqConnectService rabbitQueue = new RabbitMqConnectService();
                            rabbitQueue.setRabbitMqHost(hostDns);
                            rabbitQueue.setRabbitMqUser(username);
                            rabbitQueue.setRabbitMqPass(password);
                            rabbitQueue.setRabbitMqPort(port);
                            rabbitQueue.setRabbitMqQueue(queueName);
                            try {
                                Integer queueCounter;
                                queueCounter = rabbitQueue.message_count();
                                if (queueCounter != null) {
                                    foundQueue.setCount(queueCounter);
                                    queueRepository.save(foundQueue);
                                    log.info("Counters for Queue: " + queueName + " on Host: " + hostName + " updated");
                                }
                            } catch (TimeoutException e) {
                                e.printStackTrace();
                                log.error(String.valueOf(e));
                                log.error("Timeout: Failed to connect to RabbitMQ Host: " + hostName + " Queue: " + queueName);
                            } catch (NoSuchAlgorithmException e) {
                                e.printStackTrace();
                            } catch (KeyManagementException e) {
                                e.printStackTrace();
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }
                        } else {
                            log.error("Credentials for Host: " + hostName + " not found, select a different HostName");
                        }
                    } else {
                        log.error("Queue: " + queueName + " for Host: " + hostName + " not found, select a different HostName-Queue Pair");
                    }
                }
            }
        }
    }
}

