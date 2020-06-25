package br.com.projetochernobyl.log;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class TestProducerQueue {

	public static void main(String[] args) throws JMSException, NamingException {
		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection conn = factory.createConnection();
		conn.start();
		
		Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination queue = (Destination) context.lookup("LOG");
		
		MessageProducer producer = session.createProducer(queue);
		
		info(session, producer);
		warn(session, producer);
		error(session, producer);
		debug(session, producer);
		
		session.close();
		conn.close();
		context.close();
		System.out.println("Terminated!");

	}

	private static void debug(Session session, MessageProducer producer) throws JMSException {
		Message message = session.createTextMessage("DEBUG | Recovery replayed 64 operations from the journal in 0.136 seconds.");
		producer.send(message, DeliveryMode.NON_PERSISTENT, 0, 20000);
	}
	
	private static void info(Session session, MessageProducer producer) throws JMSException {
		Message message = session.createTextMessage("INFO | Recovery replayed 64 operations from the journal in 0.136 seconds.");
		producer.send(message, DeliveryMode.NON_PERSISTENT, 3, 20000);
	}

	private static void warn(Session session, MessageProducer producer) throws JMSException {
		Message message = session.createTextMessage("WARN | Recovery replayed 64 operations from the journal in 0.136 seconds.");
		producer.send(message, DeliveryMode.NON_PERSISTENT, 7, 20000);
	}

	private static void error(Session session, MessageProducer producer) throws JMSException {
		Message message = session.createTextMessage("ERROR | Recovery replayed 64 operations from the journal in 0.136 seconds.");
		producer.send(message, DeliveryMode.NON_PERSISTENT, 9, 20000);
	}


}
