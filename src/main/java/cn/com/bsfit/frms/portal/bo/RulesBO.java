package cn.com.bsfit.frms.portal.bo;

import cn.com.bsfit.frms.portal.base.util.ExtJSResponse;

/**
 * 规则调用
 * 
 * @author hjp
 * 
 * @since 1.0.0
 *
 */
public interface RulesBO {
	
	public ExtJSResponse listAllBizs();

	public ExtJSResponse listAllRiskTypes();

	public ExtJSResponse listAllRulePolicies(final String type);

	public ExtJSResponse listProdPackage();
	
	public ExtJSResponse listRule(final String pkgId, final String tag);
	
	public ExtJSResponse list(final String tag, final String keyword);
	
    public ExtJSResponse listBizRules(final Long pkgId);
}