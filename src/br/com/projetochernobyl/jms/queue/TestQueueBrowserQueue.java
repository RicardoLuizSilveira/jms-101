package br.com.projetochernobyl.jms.queue;

import java.util.Enumeration;
import java.util.Properties;
import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class TestQueueBrowserQueue {

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
		Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination queue = (Destination) context.lookup("financeiro");
		
		QueueBrowser browser = session.createBrowser((Queue) queue);
		@SuppressWarnings("unchecked")
		Enumeration<TextMessage> msg = browser.getEnumeration();
		while (msg.hasMoreElements()) {
			TextMessage text = msg.nextElement();
			System.out.println("Message: " + text.getText());
		}
		
		new Scanner(System.in).nextLine();
		
		session.close();
		conn.close();
		context.close();
		System.out.println("Terminated!");

	}

}
