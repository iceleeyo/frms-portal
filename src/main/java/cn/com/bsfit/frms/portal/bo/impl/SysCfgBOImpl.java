package cn.com.bsfit.frms.portal.bo.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.com.bsfit.frms.portal.base.pojo.SystemConfig;
import cn.com.bsfit.frms.portal.base.util.SystemConfigUtil;
import cn.com.bsfit.frms.portal.bo.SysCfgBO;

@Service
public class SysCfgBOImpl implements SysCfgBO {

    private SystemConfigUtil systemConfigUtil = SystemConfigUtil.getInstance();

    private SysCfgBOImpl() {
    	
    }
    
    @Override
    public List<SystemConfig> getSystemConfigList(Long type) {
        if (systemConfigUtil.getSystemConfigList() == null || systemConfigUtil.getSystemConfigList().isEmpty()) {
            return new ArrayList<SystemConfig>();
        }
        List<SystemConfig> result = new ArrayList<SystemConfig>();
        for (SystemConfig systemConfig : systemConfigUtil.getSystemConfigList()) {
            if (systemConfig.getType() != null && systemConfig.getType().equals(type)) {
                result.add(systemConfig);
            }
        }
        return result;
    }

    @Override
    public Map<String, Object> getSysConfigMap(Long type) {
        if (systemConfigUtil.getSystemConfigList() == null || systemConfigUtil.getSystemConfigList().isEmpty()) {
            return new HashMap<String, Object>();
        }
        Map<String, Object> result = new HashMap<String, Object>();
        for (SystemConfig systemConfig : systemConfigUtil.getSystemConfigList()) {
            if (systemConfig.getType() != null && systemConfig.getType().equals(type)) {
                result.put(systemConfig.getCode(), systemConfig.getValue());
            }
        }
        return result;
    }
}