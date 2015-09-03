package cn.com.bsfit.frms.portal.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.com.bsfit.frms.portal.base.log.LogDBUtil;
import cn.com.bsfit.frms.portal.base.mapper.RiskLevelMapper;
import cn.com.bsfit.frms.portal.base.pojo.RiskLevel;
import cn.com.bsfit.frms.portal.base.pojo.RiskLevelExample;
import cn.com.bsfit.frms.portal.base.pojo.Users;
import cn.com.bsfit.frms.portal.base.util.ExtJSResponse;
import cn.com.bsfit.frms.portal.base.util.Page;
import cn.com.bsfit.frms.portal.base.util.SysContent;
import cn.com.bsfit.frms.portal.enums.Status;

/**
 * 风险等级设置Service
 * 
 * @author hjp
 * 
 * @since 1.0.0
 *
 */
@RestController
@RequestMapping("/risklevel")
public class RiskLevelServerResource {

	@Autowired
	private RiskLevelMapper riskLevelMapper;
	
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public ExtJSResponse findRiskLvel(final @RequestParam(value = "level", required = false, defaultValue = "") String level,
			final @RequestParam(value = "name", required = false, defaultValue = "") String name,
			final @RequestParam(value = "page", required = true, defaultValue = "1") String page,
			final @RequestParam(value = "start", required = true, defaultValue = "0") String start,
			final @RequestParam(value = "limit", required = true, defaultValue = "25") String limit) {
		// 设置查询条件
		RiskLevelExample example = new RiskLevelExample();
		RiskLevelExample.Criteria criteria = example.createCriteria();
		criteria.andEnabledGreaterThan(Status.DELETED.getIndex());
		if (level != null && !"".equals(level)) {
			criteria.andLevelValEqualTo(Long.valueOf(level));
		}
		if (name != null && !"".equals(name)) {
			try {
				criteria.andNameLike("%" + URLDecoder.decode(name, "UTF-8") + "%");
			} catch (UnsupportedEncodingException e) {
				LogDBUtil.error(this.getClass(), e.getMessage());
				e.printStackTrace();
			}
		}
		// 查询总数
		int total = riskLevelMapper.countByExample(example);
		example.setPage(new Page(start, limit));
		example.setOrderByClause("LEVEL_VAL");
		List<RiskLevel> riskLvelList = riskLevelMapper.selectByExample(example);
		return ExtJSResponse.successRes4Find(riskLvelList, total);
	}

	/**
	 * 根据ID查找风险等级
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/findRiskLevelById", method = { RequestMethod.GET })
	public ExtJSResponse findRiskLevelById(final @RequestParam(value = "id", required = true) String id) {
		if (id != null && !"".equals(id)) {
			RiskLevel riskLevel = riskLevelMapper.selectByPrimaryKey(Integer.parseInt(id));
			return ExtJSResponse.successResWithData(riskLevel);
		} else {
			return ExtJSResponse.errorRes("风险等级ID为空,无法查找风险等级信息!");
		}
	}

	/**
	 * 增加风险等级
	 * 
	 * @param riskLvel
	 * @return
	 */
	@RequestMapping(value = "/add", method = { RequestMethod.POST })
	public ExtJSResponse addRiskLevel(@RequestBody RiskLevel riskLevel) {
		// 当前登录用户
		final Users currentUsers = SysContent.getUser();
		if (riskLevel == null) {
			LogDBUtil.error(this.getClass(), "新增风险等级出错，信息为空！");
			return ExtJSResponse.errorRes("新增风险等级出错，信息为空！");
		}
		riskLevel.setEnabled(Status.NORMAL.getIndex());
		riskLevel.setCreateTime(new Date());
		riskLevel.setUpdateTime(new Date());
		riskLevel.setModifer(currentUsers.getUserName());
		RiskLevelExample example = new RiskLevelExample();
		example.createCriteria().andLevelValEqualTo(riskLevel.getLevelVal()).andEnabledEqualTo(Status.NORMAL.getIndex());
		List<RiskLevel> riskLvelList = riskLevelMapper.selectByExample(example);
		
		RiskLevelExample riskLevelExample = new RiskLevelExample();
		riskLevelExample.createCriteria().andNameEqualTo(riskLevel.getName()).andEnabledEqualTo(Status.NORMAL.getIndex());
		List<RiskLevel> riskLevels = riskLevelMapper.selectByExample(riskLevelExample);
		// 风险等级级别不能重复
		if (riskLvelList == null || riskLvelList.size() == 0) {
			if(riskLevel == null || riskLevels.size() == 0) {
				riskLevelMapper.insert(riskLevel);
			} else {
				return ExtJSResponse.errorRes("风险等级" + riskLevel.getName() + "已经存在!");
			}
		} else {
			return ExtJSResponse.errorRes("风险等级" + riskLevel.getLevelVal() + "已经存在!");
		}
		LogDBUtil.info(this.getClass(), "RiskLevel[{}] inserted by Operator[{}].", riskLevel.getLevelVal().toString(), currentUsers.getUserName());
		return ExtJSResponse.success();
	}

	/**
	 * 编辑风险等级
	 * 
	 * @param riskLvel
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit", method = { RequestMethod.POST })
	public ExtJSResponse editRiskLevel(@RequestBody RiskLevel riskLevel) throws Exception {
		// 当前登录用户
		final Users currentUsers = SysContent.getUser();
		if (riskLevel == null) {
			LogDBUtil.error(this.getClass(), "编辑风险等级出错，信息为空！");
			return ExtJSResponse.errorRes("编辑风险等级出错，信息为空！");
		}
		if (riskLevel.getLevelVal() == null) {
			throw new Exception("RiskLevel not existed with id = " + riskLevel.getId());
		} else if (riskLevel.getReadonly() != null && riskLevel.getReadonly() == 1) {
			throw new Exception("You are not authorized to update RiskType with id = " + riskLevel.getId());
		}
		riskLevel.setUpdateTime(new Date());
		riskLevel.setModifer(currentUsers.getUserName());
		RiskLevelExample example = new RiskLevelExample();
		example.createCriteria().andLevelValEqualTo(riskLevel.getLevelVal()).andIdNotEqualTo(riskLevel.getId()).andEnabledEqualTo(Status.NORMAL.getIndex());
		List<RiskLevel> riskLvelList = riskLevelMapper.selectByExample(example);
		
		RiskLevelExample riskLevelExample = new RiskLevelExample();
		riskLevelExample.createCriteria().andNameEqualTo(riskLevel.getName()).andIdNotEqualTo(riskLevel.getId()).andEnabledEqualTo(Status.NORMAL.getIndex());
		List<RiskLevel> riskLevels = riskLevelMapper.selectByExample(riskLevelExample);
		// 风险等级级别不能重复
		if (riskLvelList == null || riskLvelList.isEmpty()) {
			if(riskLevels == null || riskLevels.size() == 0) {
				riskLevelMapper.updateByPrimaryKeySelective(riskLevel);
			} else {
				return ExtJSResponse.errorRes("风险等级" + riskLevel.getName() + "已经存在!");
			}
		} else {
			return ExtJSResponse.errorRes("风险等级" + riskLevel.getLevelVal() + "已经存在!");
		}
		LogDBUtil.info(this.getClass(), "RiskLevel[{}] updated by Operator[{}].", riskLevel.getLevelVal().toString(), currentUsers.getUserName());
		return ExtJSResponse.success();
	}

	/**
	 * 删除风险等级
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/delete", method = { RequestMethod.POST })
	public ExtJSResponse deleteRiskLevel(@RequestBody Object param) {
		// 当前登录用户
		final Users currentUsers = SysContent.getUser();
		final List<Map<String, Object>> objs = params2Attributes(param);
		for (Map<String, Object> map : objs) {
			Integer id = (Integer) map.get("id");
			Long levelVal = Long.valueOf(map.get("levelVal").toString());
			riskLevelMapper.deleteByPrimaryKey(id);
			LogDBUtil.info(this.getClass(), "RiskLevel[{}] deleted by Operator[{}].", levelVal.toString(), currentUsers.getUserName());
		}
		return ExtJSResponse.success();
	}
	
	/**
	 * 把对象转化为List
	 * 
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> params2Attributes(Object params) {
		Map<String, Object> map = null;
		if (params instanceof Map) {
			map = (Map<String, Object>) params;
		}
		List<Map<String, Object>> objs = new ArrayList<Map<String, Object>>(1);
		if (params instanceof List) {
			objs = (List<Map<String, Object>>) params;
		}
		if (map != null)
			objs.add(map);
		return objs;
	}
}