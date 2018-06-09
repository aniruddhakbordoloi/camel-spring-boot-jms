package com.springboot.camel.jms.consumer;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.component.jms.JmsConfiguration;
import org.apache.camel.spring.CamelBeanPostProcessor;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import javax.jms.ConnectionFactory;

@SpringBootApplication
@ComponentScan("com.springboot.camel")
public class SpringbootCamelApplication {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private MyCamelRoutes myCamelRoutes;

	@Bean(destroyMethod = "stop")
	public CamelContext camelContext(ActiveMQComponent activeMQComponent) throws Exception {
		SpringCamelContext camelContext = new SpringCamelContext(applicationContext);
		camelContext.disableJMX();
		camelContext.addRoutes(myCamelRoutes);
		camelContext.addComponent("activemq", activeMQComponent);
		camelContext.start();
		return camelContext;
	}

	/**
	 * For using Camel annotations in spring beans.
	 *
	 * @param camelContext
	 * @param applicationContext
	 * @return
	 */
	@Bean
	public CamelBeanPostProcessor camelBeanPostProcessor(CamelContext camelContext, ApplicationContext applicationContext) {
		CamelBeanPostProcessor processor = new CamelBeanPostProcessor();
		processor.setCamelContext(camelContext);
		processor.setApplicationContext(applicationContext);
		return processor;
	}

	@Bean
	public ConsumerTemplate consumerTemplate(CamelContext camelContext) {
		return camelContext.createConsumerTemplate();
	}

	@Bean
	public ActiveMQConnectionFactory coreConnectionFactory() {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		//connectionFactory.setBrokerURL("vm://localhost?broker.persistent=false");
		connectionFactory.setBrokerURL(ActiveMQConnection.DEFAULT_BROKER_URL);
		return connectionFactory;
	}

	@Bean
	public JmsConfiguration jmsConfiguration(ActiveMQConnectionFactory coreConnectionFactory) {
		JmsConfiguration jmsConfiguration = new JmsConfiguration();
		ConnectionFactory connectionFactory = new PooledConnectionFactory(coreConnectionFactory);
		jmsConfiguration.setConnectionFactory(connectionFactory);
		return jmsConfiguration;
	}

	@Bean
	public ActiveMQComponent activeMQComponent(JmsConfiguration jmsConfiguration) {
		ActiveMQComponent component = new ActiveMQComponent();
		component.setConfiguration(jmsConfiguration);
		return component;
	}

	public static void main(String[] args) {

		SpringApplication.run(SpringbootCamelApplication.class, args);
	}


}
