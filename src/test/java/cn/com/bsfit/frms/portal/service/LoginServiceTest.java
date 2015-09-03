package cn.com.bsfit.frms.portal.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import cn.com.bsfit.PortalApp;
import cn.com.bsfit.frms.portal.bo.UsersBO;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PortalApp.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class LoginServiceTest {
	
	@Autowired
	private UsersBO usersBO;
	
	@Test
	public void testexpireCache() {
		usersBO.storeUsersCache("123", "sdhgdfjhhfhjsdg", 0L);
		usersBO.expireUsersCache("123", 10L);
		Object object = usersBO.getUsersCache("123");
		System.out.println(object);
	}
}
