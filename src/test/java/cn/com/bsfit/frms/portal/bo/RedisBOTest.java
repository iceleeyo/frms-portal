package cn.com.bsfit.frms.portal.bo;

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
public class RedisBOTest {
	
	@Autowired
	private RedisBO redisBO;
	
	@Test
	public void testSet() {
		System.out.println(redisBO.set("123", "qqqqqqq"));
	}
}
