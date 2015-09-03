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
import cn.com.bsfit.frms.portal.base.mapper.DimensionMapper;
import cn.com.bsfit.frms.portal.base.pojo.Dimension;
import cn.com.bsfit.frms.portal.base.pojo.DimensionExample;
import cn.com.bsfit.frms.portal.base.pojo.Users;
import cn.com.bsfit.frms.portal.base.util.ExtJSResponse;
import cn.com.bsfit.frms.portal.base.util.Page;
import cn.com.bsfit.frms.portal.base.util.SysContent;
import cn.com.bsfit.frms.portal.enums.Status;

/**
 * 维度信息设置Service
 * 
 * @author hjp
 * 
 * @since 1.0.0
 *
 */
@RestController
@RequestMapping("/dimension")
public class DimensionServerResource {

	@Autowired
	private DimensionMapper dimensionMapper;
	
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public ExtJSResponse findDimension(final @RequestParam(value = "name", required = false) String name,
			final @RequestParam(value = "memo", required = false) String memo,
			final @RequestParam(value = "page", required = true, defaultValue = "1") String page,
			final @RequestParam(value = "start", required = true, defaultValue = "0") String start,
			final @RequestParam(value = "limit", required = true, defaultValue = "25") String limit) {
		// 设置查询条件
		DimensionExample dimensionExample = new DimensionExample();
		DimensionExample.Criteria criteria = dimensionExample.createCriteria();
		criteria.andEnabledGreaterThan(Status.DELETED.getIndex());
		if (name != null && !"".equals(name)) {
			try {
				criteria.andNameLike("%" + URLDecoder.decode(name, "UTF-8") + "%");
			} catch (UnsupportedEncodingException e) {
				LogDBUtil.error(this.getClass(), e.getMessage());
				e.printStackTrace();
			}
		}
		// 查询总数
		int total = dimensionMapper.countByExample(dimensionExample);
		dimensionExample.setPage(new Page(start, limit));
		List<Dimension> dimensionList = dimensionMapper.selectByExample(dimensionExample);
		return ExtJSResponse.successRes4Find(dimensionList, total);
	}
	
	/**
	 * 根据ID查询维度信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/findDimensionById", method = { RequestMethod.GET })
	public ExtJSResponse findDimensionById(final @RequestParam(value = "id", required = true) String id) {
		if (id != null && !"".equals(id)) {
			Dimension dimension = dimensionMapper.selectByPrimaryKey(Integer.parseInt(id));
			return ExtJSResponse.successResWithData(dimension);
		} else {
			return ExtJSResponse.errorRes("维度ID为空,无法查找维度信息!");
		}
	}

	/**
	 * 增加维度信息
	 * 
	 * @param dimension
	 * @return
	 */
	@RequestMapping(value = "/add", method = { RequestMethod.POST })
	public ExtJSResponse addDimension(@RequestBody Dimension dimension) {
		if (dimension == null) {
			LogDBUtil.error(this.getClass(), "新增维度信息出错，信息为空！");
			return ExtJSResponse.errorRes("新增维度信息出错，信息为空！");
		}
		// 当前登录用户
		final Users currentUsers = SysContent.getUser();
		dimension.setEnabled(Status.NORMAL.getIndex());
		dimension.setCreateTime(new Date());
		dimension.setUpdateTime(new Date());
		dimension.setModifer(currentUsers.getUserName());
		DimensionExample dimensionExample = new DimensionExample();
		dimensionExample.createCriteria().andNameEqualTo(dimension.getName()).andEnabledEqualTo(Status.NORMAL.getIndex());
		List<Dimension> dimensionList = dimensionMapper.selectByExample(dimensionExample);
		if (dimensionList == null || dimensionList.isEmpty()) {
			dimensionMapper.insert(dimension);
		} else {
			return ExtJSResponse.errorRes("维度信息" + dimension.getName() + "已经存在!");
		}
		LogDBUtil.info(this.getClass(), "Dimension[{}] inserted by Operator[{}].", dimension.getName(), currentUsers.getUserName());
		return ExtJSResponse.success();
	}

	/**
	 * 编辑维度信息
	 * 
	 * @param dimension
	 * @return
	 */
	@RequestMapping(value = "/edit", method = { RequestMethod.POST })
	public ExtJSResponse editDimension(@RequestBody Dimension dimension) {
		if (dimension == null) {
			LogDBUtil.error(this.getClass(), "编辑维度信息出错，信息为空！");
			return ExtJSResponse.errorRes("编辑维度信息出错，信息为空！");
		}
		// 当前登录用户
		final Users currentUsers = SysContent.getUser();
		dimension.setUpdateTime(new Date());
		dimension.setModifer(currentUsers.getUserName());
		DimensionExample dimensionExample = new DimensionExample();
		dimensionExample.createCriteria().andNameEqualTo(dimension.getName()).andIdNotEqualTo(dimension.getId()).andEnabledEqualTo(Status.NORMAL.getIndex());
		List<Dimension> dimensionList = dimensionMapper.selectByExample(dimensionExample);
		if (dimensionList == null || dimensionList.isEmpty()) {
			dimensionMapper.updateByPrimaryKeySelective(dimension);
		} else {
			return ExtJSResponse.errorRes("维度信息" + dimension.getName() + "已经存在!");
		}
		LogDBUtil.info(this.getClass(),"Dimension[{}] updated by Operator[{}].", dimension.getName(), currentUsers.getUserName());
		return ExtJSResponse.success();
	}

	/**
	 * 删除维度信息
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/delete", method = { RequestMethod.POST })
	public ExtJSResponse deleteDimension(@RequestBody Object param) {
		// 当前登录用户
		final Users currentUsers = SysContent.getUser();
		final List<Map<String, Object>> objs = params2Attributes(param);
		for (Map<String, Object> map : objs) {
			Integer id = (Integer) map.get("id");
			String name = map.get("name").toString();
			dimensionMapper.deleteByPrimaryKey(id);
			LogDBUtil.info(this.getClass(), "Dimension[{}] deleted by Operator[{}].", name, currentUsers.getUserName());
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