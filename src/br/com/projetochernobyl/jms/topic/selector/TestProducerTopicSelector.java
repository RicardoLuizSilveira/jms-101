package br.com.projetochernobyl.jms.topic.selector;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class TestProducerTopicSelector {

	public static void main(String[] args) throws JMSException, NamingException {
		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection conn = factory.createConnection();
		conn.start();
		
		
		// get consumer from session, it requires a Destination (Queue)
		Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination topic = (Destination) context.lookup("loja");
		
		// from producer we are able to send a message
		MessageProducer producer = session.createProducer(topic);
		
		Message message1 = session.createTextMessage("<id>111</id><name>joão</name>");
		producer.send(message1);
		
		Message message2 = session.createTextMessage("<id>111</id><e-book>false</e-book><name>joão</name>");
		message2.setBooleanProperty("ebook", false);
		producer.send(message2);
		
		session.close();
		conn.close();
		context.close();
		System.out.println("Terminated!");

	}

}
