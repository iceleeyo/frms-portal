package cn.com.bsfit.frms.portal.util.store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.bsfit.frms.portal.base.pojo.SystemConfig;
import cn.com.bsfit.frms.portal.bo.SysCfgBO;

/**
 * 从portal的系统配置表获取的store
 * 
 * @author ccj
 * 
 * @since 1.1.0
 *
 */
public class SystemConfigStore implements IStore {

	private SysCfgBO sysCfgBO;
	private Long type;
	private boolean isCodeInteger = false;

	/**
	 * 
	 * @param sysCfgBO
	 *            调用portal的方法的接口,Spring注入后传入
	 * @param type
	 *            system_config表中type字段的值
	 */
	public SystemConfigStore(SysCfgBO sysCfgBO, Long type) {
		this.sysCfgBO = sysCfgBO;
		this.type = type;
	}

	/**
	 * 
	 * @param sysCfgBO
	 *            调用portal的方法的接口,Spring注入后传入
	 * @param type
	 *            system_config表中type字段的值
	 * @param isCodeInteger
	 *            code字段是否是整型，如果是，将转换类型为整型
	 */
	public SystemConfigStore(SysCfgBO sysCfgBO, Long type, boolean isCodeInteger) {
		this.sysCfgBO = sysCfgBO;
		this.type = type;
		this.isCodeInteger = isCodeInteger;
	}

	public List<Map<String, Object>> call() {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		List<SystemConfig> systemConfigList = sysCfgBO.getSystemConfigList(type);
		// 去除多余字段，且使得字段的KEY都统一成"CODE"和"VALUE"
		for (SystemConfig systemConfig : systemConfigList) {
			Map<String, Object> obj = new HashMap<String, Object>();
			obj.put("CODE", isCodeInteger ? Integer.valueOf(systemConfig.getCode()) : systemConfig.getCode());
			obj.put("VALUE", systemConfig.getValue());
			resultList.add(obj);
		}
		return resultList;
	}
}
