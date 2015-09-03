package cn.com.bsfit.frms.portal.bo;

import cn.com.bsfit.frms.portal.base.pojo.SystemConfig;

import java.util.List;
import java.util.Map;

/**
 * 系统配置信息获取
 * 
 * @author hjp
 * 
 * @since 1.0.0
 *
 */
public interface SysCfgBO {

	/**
	 * 根据类型获取系统配置信息List
	 * 
	 * @param type 类型
	 * @return
	 */
    public abstract List<SystemConfig> getSystemConfigList(Long type);

    /**
     * 根据类型获取系统配置信息Map
     * 
     * @param type
     * @return
     */
    public abstract Map<String, Object> getSysConfigMap(Long type);
}