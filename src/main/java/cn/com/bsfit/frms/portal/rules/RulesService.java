package cn.com.bsfit.frms.portal.rules;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.jboss.resteasy.annotations.cache.NoCache;

import cn.com.bsfit.frms.portal.base.util.ExtJSResponse;

/**
 * 用于调用规则管理平台相关接口
 * 
 * @author 王新根
 * 
 * @since 1.0.0
 * 
 */
public interface RulesService {

	/**
	 * get all bizs
	 * 
	 * @return {@link ExtJSResponse} used in extjs client.<br>
	 *         success:
	 * 
	 *         <pre>
	 * {success:true,data:***}
	 * </pre>
	 * 
	 *         fail:
	 * 
	 *         <pre>
	 * {success:false,error:***}
	 * </pre>
	 */
	@GET
	@NoCache
	@Path("/biz/list")
	public ExtJSResponse listAllBizs();

	/**
	 * get all RiskType
	 * 
	 * @return {@link ExtJSResponse} used in extjs client.<br>
	 *         success:
	 * 
	 *         <pre>
	 * {success:true,data:***}
	 * </pre>
	 * 
	 *         fail:
	 * 
	 *         <pre>
	 * {success:false,error:***}
	 * </pre>
	 */
	@GET
	@NoCache
	@Path("/riskType/list")
	public ExtJSResponse listAllRiskTypes();

	/**
	 * get all RulePolicy
	 * 
	 * @param type
	 *            could be one of following:<br>
	 *            <um> <li>null or "" : 列出所有策略.</li><li>CONTROL: 列出所有控制策略</li>
	 *            <li>NOTIFY: 列出所有通知策略</li><li>VERIFY: 列出所有验证策略</li> </um>
	 * @return {@link ExtJSResponse} used in extjs client.<br>
	 *         success:
	 * 
	 *         <pre>
	 * {success:true,data:***}
	 * </pre>
	 * 
	 *         fail:
	 * 
	 *         <pre>
	 * {success:false,error:***}
	 * </pre>
	 */
	@GET
	@NoCache
	@Path("/rulePolicy/list")
	public ExtJSResponse listAllRulePolicies(final @QueryParam("type") @DefaultValue("") String type);

	@GET
	@NoCache
	@Path("/productPackage/listProdPackage")
	public ExtJSResponse listProdPackage();

	/**
	 * 
	 * @param pkgId
	 * @param tag
	 * @return
	 */
	@GET
	@NoCache
	@Path("/processManagement/listRule")
	public ExtJSResponse listRule(final @QueryParam("pkgId") String pkgId, final @QueryParam("tag") String tag);
	
	/**
	 * 
	 * @param tag BIZ
	 * @param keyword
	 * @return
	 */
	@GET
	@NoCache
	@Path("/package/list")
	public ExtJSResponse list(final @QueryParam("tag") String tag, final @QueryParam("keyword") String keyword);
	
	/**
	 * 
	 * @param pkgId
	 * @return
	 */
	@GET
    @NoCache
    @Path("/rule/list")
    public ExtJSResponse listBizRules(final @QueryParam("pkgId") @DefaultValue("-1") Long pkgId);
}