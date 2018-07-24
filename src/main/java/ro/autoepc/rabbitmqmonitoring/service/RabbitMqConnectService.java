package ro.autoepc.rabbitmqmonitoring.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

@Service
@Transactional
public class RabbitMqConnectService {

    private final Logger log = LoggerFactory.getLogger(RabbitMqConnectService.class);

    private String rabbitMqUser;
    private String rabbitMqPass;
    private String rabbitMqHost;
    private String rabbitMqQueue;
    private Integer rabbitMqPort;


    public String getRabbitMqUser() {
        return rabbitMqUser;
    }

    public void setRabbitMqUser(String rabbitMqUser) {
        this.rabbitMqUser = rabbitMqUser;
    }

    public String getRabbitMqPass() {
        return rabbitMqPass;
    }

    public void setRabbitMqPass(String rabbitMqPass) {
        this.rabbitMqPass = rabbitMqPass;
    }

    public String getRabbitMqHost() {
        return rabbitMqHost;
    }

    public void setRabbitMqHost(String rabbitMqHost) {
        this.rabbitMqHost = rabbitMqHost;
    }

    public Integer getRabbitMqPort() {
        return rabbitMqPort;
    }

    public void setRabbitMqPort(Integer rabbitMqPort) {
        this.rabbitMqPort = rabbitMqPort;
    }

    public String getConnectionUri() {
        return "amqp://" + getRabbitMqUser() + ":" + getRabbitMqPass() + "@" + getRabbitMqHost() + ":" + getRabbitMqPort();
    }

    public String getRabbitMqQueue() {
        return rabbitMqQueue;
    }

    public void setRabbitMqQueue(String rabbitMqQueue) {
        this.rabbitMqQueue = rabbitMqQueue;
    }

    public Integer message_count() throws TimeoutException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException {


        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(getConnectionUri());
        Integer counter = null;
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            counter = channel.queueDeclarePassive(getRabbitMqQueue()).getMessageCount();
            channel.close();
            connection.close();
            return counter;
        } catch (ConnectException e) {
            log.error("Failed to connect to host: " + rabbitMqHost);
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error("Failed to find queue: " + rabbitMqQueue + " on: " + rabbitMqHost);
        }
        return counter;
    }
}
