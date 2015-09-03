/*package cn.com.bsfit.frms.portal.jms;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import cn.com.bsfit.PortalApp;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PortalApp.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class JmsTest {
	
	@Autowired
	private MessageProducerClient producerClient;
	@Autowired
	private MessageConsumerClient consumerClient;
	
	@Test
	public void testMessageProducerClient() {
		producerClient.run();
	}
	
	@Test
	public void testMessageConsumerClient() {
		consumerClient.run();
	}
}
*/