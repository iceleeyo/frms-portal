package cn.com.bsfit.frms.portal.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import cn.com.bsfit.frms.portal.service.RulesServerResource;
import cn.com.bsfit.frms.portal.util.store.IStore;
import cn.com.bsfit.frms.portal.util.store.ReflectStore;
import cn.com.bsfit.frms.portal.util.store.StoreConfigs;

/**
 * 配置规则平台的业务类型和风险类型
 * 
 * @author hjp
 * 
 * @since 1.0.0
 *
 */
@Configuration
public class PortalStoresConfig implements StoreConfigs {
	
	@Autowired
	private RulesServerResource rulesServerResource;
	
    public Map<String, IStore> getStoreConfigs() {
        Map<String, IStore> stores = new HashMap<String, IStore>();
        /********************************规则平台部分开始***********************************************/
		// 业务类型
		stores.put("BIZ_CODE", new ReflectStore(rulesServerResource, "listAllBizs", "code", "name"));
		// 风险类型
		stores.put("RISK_TYPE", new ReflectStore(rulesServerResource, "listAllRiskTypes", "code", "name"));
		/********************************规则平台部分结束***********************************************/
        return stores;
    }
}