package com.springboot.camel.jms.producer;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by Aniruddha on 09-06-2018.
 */
public class MessageProducer {

    public void sendMesage() throws JMSException {
        final ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
        final Connection connection = connectionFactory.createConnection();
        connection.start();

        final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        final Destination destination = session.createQueue("MESSAGE.GATEWAY");
        final javax.jms.MessageProducer producer = session.createProducer(destination);
        final TextMessage message = session.createTextMessage();
        message.setText("Sample Message for Active MQ");
        producer.send(message);
    }

    public static void main(String[] args) throws JMSException {
        final MessageProducer messageProducer = new MessageProducer();
        messageProducer.sendMesage();
    }
}
