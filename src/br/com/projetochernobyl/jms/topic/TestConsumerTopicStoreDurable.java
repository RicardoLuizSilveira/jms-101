package br.com.projetochernobyl.jms.topic;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import br.com.projetochernobyl.modelo.Pedido;


public class TestConsumerTopicStoreDurable {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws JMSException, NamingException {
		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection conn = factory.createConnection();
		conn.setClientID("Store");
		conn.start();
		
		// get consumer from session, it requires a Destination (Topic)
		Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic = (Topic) context.lookup("loja");
		
		// from consumer we are able to get a message
		MessageConsumer consumer = session.createDurableSubscriber(topic, "subscription");
		consumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {
				ObjectMessage objectMessage = (ObjectMessage) message;
				try {
					Pedido pedido = (Pedido) objectMessage.getObject();
					System.out.println("Message: " + pedido.getCodigo());
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
