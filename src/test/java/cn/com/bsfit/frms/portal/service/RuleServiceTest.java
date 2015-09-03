package cn.com.bsfit.frms.portal.service;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.engines.ApacheHttpClient4Engine;

import cn.com.bsfit.frms.portal.rules.RulesService;

/**
 * 调用规则平台接口测试
 * @author hjp
 *
 */
public class RuleServiceTest {
	
	private final static String URL = "http://10.100.1.85:9090/rs";
	private final static String USERNAME = "admin";
	private final static String PASSWORD = "bangsun";
	
	public static void main(String[] args) throws URISyntaxException {
		URI uri = new URI(URL);
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(new AuthScope(uri.getHost(), uri.getPort()),
				new UsernamePasswordCredentials(USERNAME, PASSWORD));
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider)
				.setConnectionManager(new PoolingHttpClientConnectionManager()).build();
		ApacheHttpClient4Engine engine = new ApacheHttpClient4Engine(httpClient);
		ResteasyClient client = new ResteasyClientBuilder().httpEngine(engine).build();
		ResteasyWebTarget target = client.target(uri);
		RulesService rulesService = target.proxy(RulesService.class);
		System.out.println("listAllBizs:" + rulesService.listAllBizs());
		System.out.println("listAllRiskTypes:" + rulesService.listAllRiskTypes());
		System.out.println("listAllRulePolicies:" + rulesService.listAllRulePolicies(null));
		System.out.println("listProdPackage:" + rulesService.listProdPackage());
		System.out.println("listRule:" + rulesService.listRule("", ""));
	}
}
