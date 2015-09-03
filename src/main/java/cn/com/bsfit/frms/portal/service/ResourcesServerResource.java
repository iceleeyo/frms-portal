package cn.com.bsfit.frms.portal.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.com.bsfit.frms.portal.base.log.LogDBUtil;
import cn.com.bsfit.frms.portal.base.mapper.ResourcesMapper;
import cn.com.bsfit.frms.portal.base.pojo.Resources;
import cn.com.bsfit.frms.portal.base.pojo.ResourcesExample;
import cn.com.bsfit.frms.portal.base.pojo.Users;
import cn.com.bsfit.frms.portal.base.util.ExtJSResponse;
import cn.com.bsfit.frms.portal.base.util.SysContent;
import cn.com.bsfit.frms.portal.base.util.TreeMenuNode;
import cn.com.bsfit.frms.portal.bo.ResourcesBO;
import cn.com.bsfit.frms.portal.enums.Status;

/**
 * 系统资源管理Service
 * 
 * @author hjp
 * 
 * @since 1.0.0
 *
 */
@RestController
@RequestMapping("/resources")
public class ResourcesServerResource {

	@Autowired
	private ResourcesBO resourcesBO;
	@Autowired
	private ResourcesMapper resourcesMapper;
	
	/**
	 * 资源树形菜单
	 * 
	 * @param node
	 * @return
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public ExtJSResponse findResources(final @RequestParam(value = "node", required = true) String node) {
		Integer parentId = 0;
		if (node != null && !"NaN".equals(node)) {
			parentId = Integer.parseInt(node);
		}
		return ExtJSResponse.successResWithData(resourcesBO.getTree(parentId));
	}

	/**
	 * 添加资源
	 * 
	 * @param treeMenuNode
	 * @return
	 */
	@RequestMapping(value = "/add", method = { RequestMethod.POST })
	public ExtJSResponse addResources(@RequestBody TreeMenuNode treeMenuNode) {
		if (treeMenuNode == null) {
			LogDBUtil.error(this.getClass(), "新增资源出错，用户信息为空！");
			return ExtJSResponse.errorRes("新增资源出错，用户信息为空！");
		}
		// 当前登录用户
		Users currentUsers = SysContent.getUser();
		Resources resources = resourcesBO.buildResource(treeMenuNode);
		resources.setCreateTime(new Date());
		resources.setUpdateTime(new Date());
		resources.setModifer(currentUsers.getUserName());
		resources.setEnabled(Status.NORMAL.getIndex());
		resourcesMapper.insertSelective(resources);
		LogDBUtil.info(this.getClass(), "Resources[{}] inserted by Operator[{}].", treeMenuNode.getText(), currentUsers.getUserName());
		return ExtJSResponse.successResWithData(resources.getId());
	}

	/**
	 * 编辑资源
	 * 
	 * @param treeMenuNode
	 * @return
	 */
	@RequestMapping(value = "/edit", method = { RequestMethod.POST })
	public ExtJSResponse editResources(@RequestBody TreeMenuNode treeMenuNode) {
		if (treeMenuNode == null) {
			LogDBUtil.error(this.getClass(), "编辑资源出错，用户信息为空！");
			return ExtJSResponse.errorRes("编辑资源出错，用户信息为空！");
		}
		// 当前登录用户
		Users currentUsers = SysContent.getUser();
		Resources resources = resourcesBO.buildResource(treeMenuNode);
		resources.setUpdateTime(new Date());
		resources.setModifer(currentUsers.getUserName());

		resourcesMapper.updateByPrimaryKeySelective(resources);
		LogDBUtil.info(this.getClass(), "Resources[{}] updated by Operator[{}].", treeMenuNode.getText(), currentUsers.getUserName());
		return ExtJSResponse.success();
	}

	/**
	 * 删除资源
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete", method = { RequestMethod.POST })
	public ExtJSResponse deleteResources(final @RequestParam(value = "id", required = true) Integer id) {
		// 当前登录用户
		Users currentUsers = SysContent.getUser();
		Resources resources = new Resources();
		resources.setId(id);
		// 逻辑删除,将状态置为0
		resources.setEnabled(Status.DELETED.getIndex());
		resources.setUpdateTime(new Date());
		resources.setModifer(currentUsers.getUserName());
		resourcesMapper.updateByPrimaryKeySelective(resources);
		LogDBUtil.info(this.getClass(), "Operator[{}] deleted Resources[{}]", currentUsers.getUserName(), id);
		// 递归删除
		deleteTree(id);
		return ExtJSResponse.success();
	}

	/**
	 * 递归删除资源
	 * 
	 * @param parentId
	 */
	private void deleteTree(Integer parentId) {
		// 当前登录用户
		Users currentUsers = SysContent.getUser();
		ResourcesExample resourcesExample = new ResourcesExample();
		resourcesExample.createCriteria().andEnabledEqualTo(Status.NORMAL.getIndex()).andParentIdEqualTo(parentId);
		List<Resources> resourcesList = resourcesMapper.selectByExample(resourcesExample);
		if (resourcesList == null || resourcesList.isEmpty()) {
			return;
		}
		for (Resources resources : resourcesList) {
			// 逻辑删除,将状态置为0
			resources.setEnabled(Status.DELETED.getIndex());
			resources.setUpdateTime(new Date());
			resources.setModifer(currentUsers.getUserName());
			resourcesMapper.updateByPrimaryKeySelective(resources);
			deleteTree(resources.getId());
		}
	}
}