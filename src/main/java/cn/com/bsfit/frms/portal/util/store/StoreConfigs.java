package cn.com.bsfit.frms.portal.util.store;

import java.util.Map;

/**
 * combo的store配置接口
 * 
 * @author hjp
 * 
 * @since 1.0.0
 *
 */
public interface StoreConfigs {
	
    public abstract Map<String, IStore> getStoreConfigs();
}