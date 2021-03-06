package br.com.projetochernobyl.jms.queue;

import java.util.Properties;
import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class TestConsumerQueue {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws JMSException, NamingException {
		
		Properties properties = new Properties();
		properties.setProperty("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
		properties.setProperty("java.naming.provider.url", "tcp://localhost:61616");
		properties.setProperty("queue.financeiro", "fila.financeiro");
		
		InitialContext context = new InitialContext(properties);
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection conn = factory.createConnection();
		conn.start();
		
		
		// get consumer from session, it requires a Destination (Queue)
		Session session = conn.createSession(true, Session.SESSION_TRANSACTED);
		Destination queue = (Destination) context.lookup("financeiro");
		
		// from consumer we are able to get a message
		MessageConsumer consumer = session.createConsumer(queue);
		consumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {
				TextMessage text = (TextMessage) message;
				try {
					System.out.println("Message: " + text.getText());
					session.commit();
				} catch (JMSException e) {
					try {
						session.rollback();
					} catch (JMSException e1) {
						e1.printStackTrace();
					}
					e.printStackTrace();
				}
			}
			
		});

		new Scanner(System.in).nextLine();
		
		session.close();
		conn.close();
		context.close();
		System.out.println("Terminated!");

	}

}
