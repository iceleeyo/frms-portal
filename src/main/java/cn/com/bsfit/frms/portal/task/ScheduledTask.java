package cn.com.bsfit.frms.portal.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.com.bsfit.frms.portal.base.mapper.RiskLevelMapper;
import cn.com.bsfit.frms.portal.base.mapper.SystemConfigMapper;
import cn.com.bsfit.frms.portal.base.mapper.UsersMapper;
import cn.com.bsfit.frms.portal.base.pojo.RiskLevel;
import cn.com.bsfit.frms.portal.base.pojo.RiskLevelExample;
import cn.com.bsfit.frms.portal.base.pojo.SystemConfig;
import cn.com.bsfit.frms.portal.base.pojo.SystemConfigExample;
import cn.com.bsfit.frms.portal.base.pojo.Users;
import cn.com.bsfit.frms.portal.base.pojo.UsersExample;
import cn.com.bsfit.frms.portal.base.util.OperatorUtil;
import cn.com.bsfit.frms.portal.base.util.RiskLevelUtil;
import cn.com.bsfit.frms.portal.base.util.SystemConfigUtil;
import cn.com.bsfit.frms.portal.base.util.UsersUtil;
import cn.com.bsfit.frms.portal.enums.Status;

/**
 * Portal定时任务
 * 更新系统配置，风险等级，受理人员，操作人员
 * 
 * @author hjp
 * 
 * @since 1.0.0
 *
 */
@Component
@EnableScheduling
public class ScheduledTask {

	@Autowired
	private UsersMapper usersMapper;
	@Autowired
	private RiskLevelMapper riskLevelMapper;
	@Autowired
	private SystemConfigMapper systemConfigMapper;
	private Logger logger = LoggerFactory.getLogger(ScheduledTask.class);
	
	/**
	 * 心跳更新。启动时执行一次，之后每隔60秒执行一次
	 */
	@Scheduled(fixedRate = 1000 * 60)
	public void reportCurrentTime() {
		updateSystemConfigUtil();
		updateRiskLevel();
		updateUsers();
		updateOperator();
	}

	@Scheduled(fixedDelay = 1000 * 60)
	public void reportCurrentDelay() {
		
	}
	
	/**
	 * cron表达式: * * * * * *（共6位，使用空格隔开，具体如下）
	 * cron表达式: *(秒0-59) * (分钟0-59) * (小时0-23) * (日期1-31) * (月份1-12或是JAN-DEC) * (星期1-7或是SUN-SAT)
	 * 定时计算。每1分钟执行一次
	 */
	@Scheduled(cron = "0 */1 *  * * * ")
	public void reportCurrentByCron() {
		
	}

	/**
	 * 更新系统配置
	 */
	private void updateSystemConfigUtil() {
		SystemConfigExample example = new SystemConfigExample();
		example.createCriteria().andEnabledGreaterThanOrEqualTo(Status.NORMAL.getIndex());
		example.setOrderByClause("TYPE, ORDER_BY");
		List<SystemConfig> systemConfigList = systemConfigMapper.selectByExample(example);
		SystemConfigUtil.getInstance().setSystemConfigList(systemConfigList);
		SystemConfigUtil.getInstance().setSysConfigMap(listToMap(systemConfigList));
	}
	
	/**
	 * 更新风险等级
	 */
	private void updateRiskLevel() {
		RiskLevelExample example = new RiskLevelExample();
		example.createCriteria().andEnabledGreaterThanOrEqualTo(Status.NORMAL.getIndex());
		example.setOrderByClause("LEVEL_VAL");
		List<RiskLevel> riskLevelList = riskLevelMapper.selectByExample(example);
		RiskLevelUtil.getInstance().setRiskLevelList(riskLevelList);
		RiskLevelUtil.getInstance().setRiskLvelMap(listToMap(riskLevelList));
	}
	
	/**
	 * 更新受理人员
	 */
	private void updateUsers() {
		UsersExample example = new UsersExample();
		example.createCriteria().andEnabledGreaterThanOrEqualTo(Status.NORMAL.getIndex());
		List<Users> usersList = usersMapper.selectByExample(example);
		UsersUtil.getInstance().setUsersList(usersList);
		UsersUtil.getInstance().setUsersMap(listToMap(usersList));
	}
	
	/**
	 * 更新操作人员
	 */
	private void updateOperator() {
		UsersExample example = new UsersExample();
		example.createCriteria().andEnabledGreaterThanOrEqualTo(Status.NORMAL.getIndex());
		List<Users> usersList = usersMapper.selectByExample(example);
		OperatorUtil.getInstance().setOperatorList(usersList);
		OperatorUtil.getInstance().setOperatorMap(listToMap(usersList));
	}
	
	/**
	 * List转化为Map
	 * 
	 * @param objectList
	 * @return
	 */
	private Map<String, Object> listToMap(List<?> objectList) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(objectList != null && objectList.size() != 0) {
			for (Object obj : objectList) {
				if(obj instanceof SystemConfig) {
					SystemConfig systemConfig = (SystemConfig) obj;
					map.put(systemConfig.getCode(), systemConfig.getValue());
				} else if(obj instanceof RiskLevel) {
					RiskLevel riskLevel = (RiskLevel) obj;
					map.put(riskLevel.getLevelVal() == null ? null : riskLevel.getLevelVal().toString() , riskLevel.getName());
				} else if(obj instanceof Users) {
					Users users = (Users) obj;
					map.put(users.getId() == null ? null : users.getId().toString(), users.getUserName());
				} else {
					logger.info("this system does not know the object:" + obj);
				}
			}
		}
		return map;
	}
}
