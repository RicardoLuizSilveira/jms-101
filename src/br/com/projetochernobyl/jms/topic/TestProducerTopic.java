package br.com.projetochernobyl.jms.topic;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import br.com.projetochernobyl.modelo.Pedido;
import br.com.projetochernobyl.modelo.PedidoFactory;


public class TestProducerTopic {

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
		
		Pedido pedido = new PedidoFactory().geraPedidoComValores();
		
		Message message = session.createObjectMessage(pedido);
		producer.send(message);
		
		session.close();
		conn.close();
		context.close();
		System.out.println("Terminated!");

	}

}
