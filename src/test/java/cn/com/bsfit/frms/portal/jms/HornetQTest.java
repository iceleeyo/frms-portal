package cn.com.bsfit.frms.portal.jms;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.api.jms.HornetQJMSClient;
import org.hornetq.api.jms.JMSFactoryType;
import org.hornetq.core.client.impl.DelegatingSession;
import org.hornetq.core.remoting.impl.netty.NettyConnectorFactory;
import org.hornetq.core.remoting.impl.netty.TransportConstants;
import org.hornetq.jms.client.HornetQConnection;

public class HornetQTest {

	private static final String HORNETQ_PROVIDER_URL = "jnp://10.100.1.14:1099";
	private static final String INITIAL_CONTEXT_FACTORY = "org.jnp.interfaces.NamingContextFactory";
	private static final String FACTORY_URL_PKGS = "org.jboss.naming:org.jnp.interfaces";
	private static final String HOST_PROP_NAME = "10.100.1.13";
	private static final String PORT_PROP_NAME = "5445";

	public static void main(String[] args) throws NamingException, JMSException {
		Connection initConnection = createConnectionWithJNDI();
		Queue queue = createQueueWithJNDI();
		System.out.println(getServer(initConnection));
		Session session = initConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		MessageProducer producer = session.createProducer(queue);
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		producer.send(session.createTextMessage("Hello"));
		producer.send(session.createTextMessage("Hello"));
		releaseConnection(initConnection);
	}

	/**
	 * 关闭连接
	 * @param connection
	 * @throws JMSException
	 */
	private static void releaseConnection(Connection connection) throws JMSException {
		if (connection != null) {
			connection.close();
		}
	}

	public static Connection createConnection() throws JMSException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(TransportConstants.HOST_PROP_NAME, HOST_PROP_NAME);
		map.put(TransportConstants.PORT_PROP_NAME, PORT_PROP_NAME);
		TransportConfiguration server = new TransportConfiguration(NettyConnectorFactory.class.getName(), map);

		ConnectionFactory connectionFactory = (ConnectionFactory) HornetQJMSClient.createConnectionFactoryWithoutHA(JMSFactoryType.CF, server);
		Connection connection = connectionFactory.createConnection();
		return connection;
	}

	public static Connection createConnectionWithJNDI() throws NamingException, JMSException {
		Properties properties = new Properties();
		properties.put("java.naming.provider.url", HORNETQ_PROVIDER_URL);
		properties.put("java.naming.factory.initial", INITIAL_CONTEXT_FACTORY);
		properties.put("java.naming.factory.url.pkgs", FACTORY_URL_PKGS);
		InitialContext initialContext = new InitialContext(properties);
		ConnectionFactory connectionFactory = (ConnectionFactory) initialContext.lookup("/ConnectionFactory");
		Connection connection = connectionFactory.createConnection();
		return connection;
	}

	public static Queue createQueueWithJNDI() throws NamingException {
		Properties properties = new Properties();
		properties.put("java.naming.provider.url", HORNETQ_PROVIDER_URL);
		properties.put("java.naming.factory.initial", INITIAL_CONTEXT_FACTORY);
		properties.put("java.naming.factory.url.pkgs", FACTORY_URL_PKGS);
		InitialContext initialContext = new InitialContext(properties);
		Queue queue = (Queue) initialContext.lookup("/queue/ExpiryQueue");
		return queue;
	}

	private static int getServer(Connection connection) {
		DelegatingSession session = (DelegatingSession) ((HornetQConnection) connection).getInitialSession();

		TransportConfiguration transportConfiguration = session.getSessionFactory().getConnectorConfiguration();
		String port = transportConfiguration.getParams().get("port").toString();
		return Integer.valueOf(port);
	}
}
