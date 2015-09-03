package cn.com.bsfit.frms.portal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.com.bsfit.frms.portal.base.util.ExtJSResponse;
import cn.com.bsfit.frms.portal.bo.RulesBO;
import cn.com.bsfit.frms.portal.rules.RulesService;

/**
 * 调用规则平台接口的方法
 * 
 * @author hjp
 * 
 * @since 1.0.0
 * 
 * @see RulesService
 *
 */
@RestController
@RequestMapping("/rules")
public class RulesServerResource {

	@Autowired
	private RulesBO rulesBO;

	@RequestMapping(value = "/listAllBizs", method = { RequestMethod.GET })
	public ExtJSResponse listAllBizs() {
		return rulesBO.listAllBizs();
	}

	@RequestMapping(value = "/listAllRiskTypes", method = { RequestMethod.GET })
	public ExtJSResponse listAllRiskTypes() {
		return rulesBO.listAllRiskTypes();
	}

	@RequestMapping(value = "/listAllRulePolicies", method = { RequestMethod.GET })
	public ExtJSResponse listAllRulePolicies(final @RequestParam(value = "type", required = false) String type) {
		return rulesBO.listAllRulePolicies(type);
	}

	@RequestMapping(value = "/listProdPackage", method = { RequestMethod.GET })
	public ExtJSResponse listProdPackage() {
		return rulesBO.listProdPackage();
	}
	
	@RequestMapping(value = "/listRule", method = { RequestMethod.GET })
	public ExtJSResponse listRule(final @RequestParam(value = "pkgId", required = false) String pkgId,
			final @RequestParam(value = "tag", required = false) String tag) {
		return rulesBO.listRule(pkgId, tag);	
	}
	
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public ExtJSResponse list(final @RequestParam(value = "tag", required = false) String tag, 
			final @RequestParam(value = "keyword", required = false) String keyword) {
		return rulesBO.list(tag, keyword);
	}
	
	@RequestMapping(value = "/listBizRules", method = { RequestMethod.GET })
	public ExtJSResponse listBizRules(final @RequestParam(value = "pkgId", required = false) Long pkgId) {
		return rulesBO.listBizRules(pkgId);
	}
}