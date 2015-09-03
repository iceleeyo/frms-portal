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
import cn.com.bsfit.frms.portal.base.mapper.SystemConfigMapper;
import cn.com.bsfit.frms.portal.base.pojo.SystemConfig;
import cn.com.bsfit.frms.portal.base.pojo.SystemConfigExample;
import cn.com.bsfit.frms.portal.base.pojo.Users;
import cn.com.bsfit.frms.portal.base.util.ExtJSResponse;
import cn.com.bsfit.frms.portal.base.util.Page;
import cn.com.bsfit.frms.portal.base.util.SysContent;

/**
 * 系统配置管理Service
 * 
 * @author hjp
 * 
 * @since 1.0.0
 *
 */
@RestController
@RequestMapping("/systemConfig")
public class SystemConfigServerResource {

	@Autowired
	private SystemConfigMapper systemConfigMapper;
	
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public ExtJSResponse findSystemConfig(final @RequestParam(value = "type", required = false) String type,
			final @RequestParam(value = "typeName", required = false) String typeName,
			final @RequestParam(value = "code", required = false) String code,
			final @RequestParam(value = "value", required = false) String value,
			final @RequestParam(value = "page", required = false, defaultValue = "1") String page,
			final @RequestParam(value = "start", required = false, defaultValue = "0") String start,
			final @RequestParam(value = "limit", required = false, defaultValue = "25") String limit) {
		// 设置查询条件
		SystemConfigExample systemConfigExample = new SystemConfigExample();
		SystemConfigExample.Criteria criteria = systemConfigExample.createCriteria();
		if (type != null && !"".equals(type)) {
			criteria.andTypeEqualTo(Long.valueOf(type));
		}
		if (typeName != null && !"".equals(typeName)) {
			try {
				criteria.andTypeNameLike("%" + URLDecoder.decode(typeName, "UTF-8") + "%");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		if (code != null && !"".equals(code)) {
			try {
				criteria.andCodeLike("%" + URLDecoder.decode(code, "UTF-8") + "%");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		if (value != null && !"".equals(value)) {
			try {
				criteria.andValueLike("%" + URLDecoder.decode(value, "UTF-8") + "%");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		// 查询总数,分页用
		int total = systemConfigMapper.countByExample(systemConfigExample);
		systemConfigExample.setPage(new Page(start, limit));
		systemConfigExample.setOrderByClause("TYPE, ORDER_BY");
		List<SystemConfig> systemConfigList = systemConfigMapper.selectByExample(systemConfigExample);
		return ExtJSResponse.successRes4Find(systemConfigList, total);
	}
	
	/**
	 * 根据ID查找系统配置
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/findSystemConfigById", method = { RequestMethod.GET })
	public ExtJSResponse findSystemConfigById(final @RequestParam(value = "id", required = true) String id) {
		if (id != null && !"".equals(id)) {
			return ExtJSResponse.successResWithData(systemConfigMapper.selectByPrimaryKey(Integer.parseInt(id)));
		} else {
			return ExtJSResponse.errorRes("系统配置ID为空,无法查找用户信息!");
		}
	}

	/**
	 * 增加系统配置
	 * 
	 * @param systemConfig
	 * @return
	 */
	@RequestMapping(value = "/add", method = { RequestMethod.POST })
	public ExtJSResponse addSystemCofig(@RequestBody SystemConfig systemConfig) {
		// 当前登录用户
		final Users currentUsers = SysContent.getUser();
		systemConfig.setCreateTime(new Date());
		systemConfig.setUpdateTime(new Date());
		systemConfig.setModifer(currentUsers.getUserName());

		systemConfigMapper.insertSelective(systemConfig);

		LogDBUtil.info(this.getClass(), "SystemConfig[{}] inserted by Operator[{}].", systemConfig.getValue(), currentUsers.getUserName());
		return ExtJSResponse.success();
	}

	/**
	 * 编辑系统配置
	 * 
	 * @param systemConfig
	 * @return
	 */
	@RequestMapping(value = "/edit", method = { RequestMethod.POST })
	public ExtJSResponse editSystemCofig(@RequestBody SystemConfig systemConfig) {
		// 当前登录用户
		final Users currentUsers = SysContent.getUser();
		systemConfig.setUpdateTime(new Date());
		systemConfig.setModifer(currentUsers.getUserName());

		systemConfigMapper.updateByPrimaryKeySelective(systemConfig);

		LogDBUtil.info(this.getClass(), "SystemConfig[{}] updated by Operator[{}].", systemConfig.getValue(), currentUsers.getUserName());
		return ExtJSResponse.success();
	}

	/**
	 * 删除系统配置
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/delete", method = { RequestMethod.POST })
	public ExtJSResponse deleteSystemCofig(@RequestBody Object param) {
		// 当前登录用户
		final Users currentUsers = SysContent.getUser();
		final List<Map<String, Object>> objs = params2Attributes(param);
		for (Map<String, Object> map : objs) {
			Integer id = (Integer) map.get("id");
			systemConfigMapper.deleteByPrimaryKey(id);
			LogDBUtil.info(this.getClass(), "SystemConfig[{}] deleted by Operator[{}].", id.toString(), currentUsers.getUserName());
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
