package br.com.projetochernobyl.jms.selector;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class TestConsumerTopicStoreSelector {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws JMSException, NamingException {
		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection conn = factory.createConnection();
		conn.setClientID("Store");
		conn.start();
		
		Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic = (Topic) context.lookup("loja");
		
		MessageConsumer consumer = session.createDurableSubscriber(topic, "sibcription-selector", "ebook is null or ebook=false", false);
		consumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {
				TextMessage text = (TextMessage) message;
				try {
					System.out.println("Message: " + text.getText());
				} catch (JMSException e) {
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
