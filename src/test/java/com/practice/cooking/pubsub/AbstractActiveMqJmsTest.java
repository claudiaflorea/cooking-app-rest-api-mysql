package com.practice.cooking.pubsub;

import java.net.URI;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.apache.activemq.broker.region.policy.PolicyEntry;
import org.apache.activemq.broker.region.policy.PolicyMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public abstract class AbstractActiveMqJmsTest {

    private final static String BROKER_URI = ActiveMQConnectionFactory.DEFAULT_BROKER_URL;
    private final static String ADMIN      = "admin";

    protected Connection        connection;
    protected ConnectionFactory activeMQConnectionFactory;
    protected Session           session;
    protected BrokerService     broker;

    @BeforeEach
    void setUp() throws Exception {
        broker = createBroker();
        broker.start();
        activeMQConnectionFactory = new ActiveMQConnectionFactory(BROKER_URI);
        connection = activeMQConnectionFactory.createConnection(ADMIN, ADMIN);
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        connection.start();
    }

    
    @AfterEach
    void clean() throws Exception {
        connection.close();
        broker.stop();
    } 
    
    protected BrokerService createBroker() throws Exception {
        BrokerService service = new BrokerService();
        service.setPersistent(false);
        service.setUseJmx(true);
        TransportConnector connector = new TransportConnector();
        connector.setUri(new URI("tcp://localhost:61616"));
        service.addConnector(connector);

        PolicyMap policyMap = new PolicyMap();
        PolicyEntry policy = new PolicyEntry();
        policy.setConsumersBeforeDispatchStarts(2);
        policy.setTimeBeforeDispatchStarts(1000);
        policyMap.setDefaultEntry(policy);
        service.setDestinationPolicy(policyMap);

        service.start();
        return service;
    }
}
