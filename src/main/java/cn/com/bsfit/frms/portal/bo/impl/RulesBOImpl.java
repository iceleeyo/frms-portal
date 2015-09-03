package cn.com.bsfit.frms.portal.bo.impl;

import java.net.URI;
import java.net.URISyntaxException;

import javax.annotation.PostConstruct;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.com.bsfit.frms.portal.base.util.ExtJSResponse;
import cn.com.bsfit.frms.portal.bo.RedisBO;
import cn.com.bsfit.frms.portal.bo.RulesBO;
import cn.com.bsfit.frms.portal.rules.RulesService;
import cn.com.bsfit.frms.portal.util.Constants;

@Service
public class RulesBOImpl implements RulesBO {

	@Value("${rules.url}")
	private String url;
	@Value("${rules.username}")
	private String username;
	@Value("${rules.password}")
	private String password;
	@Autowired
	private RedisBO redisBO;
	private RulesService rulesService;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PostConstruct
	public void init() throws URISyntaxException {
		URI uri = new URI(url);
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(new AuthScope(uri.getHost(), uri.getPort()),
				new UsernamePasswordCredentials(username, password));
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider)
				.setConnectionManager(new PoolingHttpClientConnectionManager()).build();
		ApacheHttpClient4Engine engine = new ApacheHttpClient4Engine(httpClient);
		ResteasyClient client = new ResteasyClientBuilder().httpEngine(engine).build();
		ResteasyWebTarget target = client.target(uri);
		rulesService = target.proxy(RulesService.class);
	}
	
	@Override
	public ExtJSResponse listAllBizs() {
		Object object = redisBO.get("listAllBizs");
		if (object != null && object instanceof ExtJSResponse) {
			logger.info("get all bizs from redis:[{}]", object);
			return (ExtJSResponse) object;
		} else {
			ExtJSResponse extJSResponse = rulesService.listAllBizs();
			logger.info("get all bizs from frms-rules:[{}]", extJSResponse);
			redisBO.set("listAllBizs", extJSResponse, Constants.RULES_LIVE_TIME);
			return extJSResponse;
		}
	}

	@Override
	public ExtJSResponse listAllRiskTypes() {
		Object object = redisBO.get("listAllRiskTypes");
		if (object != null && object instanceof ExtJSResponse) {
			logger.info("get all RiskType from redis:[{}]", object);
			return (ExtJSResponse) object;
		} else {
			ExtJSResponse extJSResponse = rulesService.listAllRiskTypes();
			logger.info("get all RiskType from frms-rules:[{}]", extJSResponse);
			redisBO.set("listAllRiskTypes", extJSResponse, Constants.RULES_LIVE_TIME);
			return extJSResponse;
		}
	}

	@Override
	public ExtJSResponse listAllRulePolicies(final String type) {
		Object object = redisBO.get("listAllRulePolicies");
		if (object != null && object instanceof ExtJSResponse) {
			logger.info("get all RulePolicy by type[{}] from redis:[{}]", type, object);
			return (ExtJSResponse) object;
		} else {
			ExtJSResponse extJSResponse = rulesService.listAllRulePolicies(type);
			logger.info("get all RulePolicy by type[{}] from frms-rules:[{}]", type, extJSResponse);
			redisBO.set("listAllRulePolicies", extJSResponse , Constants.RULES_LIVE_TIME);
			return extJSResponse;
		}
	}

	@Override
	public ExtJSResponse listProdPackage() {
		Object object = redisBO.get("listProdPackage");
		if (object != null && object instanceof ExtJSResponse) {
			logger.info("get all ProdPackage from redis:[{}]", object);
			return (ExtJSResponse) object;
		} else {
			ExtJSResponse extJSResponse = rulesService.listProdPackage();
			logger.info("get all ProdPackage from frms-rules:[{}]", extJSResponse);
			redisBO.set("listProdPackage", extJSResponse, Constants.RULES_LIVE_TIME);
			return extJSResponse;
		}
	}

	@Override
	public ExtJSResponse listRule(final String pkgId, final String tag) {
		Object object = redisBO.get("listRule");
		if (object != null && object instanceof ExtJSResponse) {
			logger.info("get all Rule by pkgId[{}] and tag[{}] from redis:[{}]", pkgId, tag, object);
			return (ExtJSResponse) object;
		} else {
			ExtJSResponse extJSResponse = rulesService.listRule(pkgId, tag);
			logger.info("get all Rule by pkgId[{}] and tag[{}] from frms-rules:[{}]", pkgId, tag, extJSResponse);
			redisBO.set("listRule", extJSResponse, Constants.RULES_LIVE_TIME);
			return extJSResponse;
		}
	}

	@Override
	public ExtJSResponse list(final String tag, final String keyword) {
		Object object = redisBO.get("list");
		if (object != null && object instanceof ExtJSResponse) {
			logger.info("list by tag[{}] and keyword[{}] from redis:[{}]", tag, keyword, object);
			return (ExtJSResponse) object;
		} else {
			ExtJSResponse extJSResponse = rulesService.list(tag, keyword);
			logger.info("list by tag[{}] and keyword[{}] from frms-rules:[{}]", tag, keyword, extJSResponse);
			redisBO.set("list", extJSResponse, Constants.RULES_LIVE_TIME);
			return extJSResponse;
		}
	}

	@Override
	public ExtJSResponse listBizRules(final Long pkgId) {
		Object object = redisBO.get("listBizRules" + pkgId);
		if(object != null && object instanceof ExtJSResponse) {
			logger.info("listBizRules by pkgId[{}] from redis:[{}]", pkgId, object);
			return (ExtJSResponse) object;
		} else {
			ExtJSResponse extJSResponse = rulesService.listBizRules(pkgId);
			redisBO.set("listBizRules" + pkgId, extJSResponse, Constants.RULES_LIVE_TIME);
			logger.info("listBizRules by pkgId[{}] from frms-rules:[{}]", pkgId, extJSResponse);
			return extJSResponse;
		}
	}
}