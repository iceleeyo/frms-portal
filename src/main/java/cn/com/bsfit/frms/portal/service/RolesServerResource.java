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
import cn.com.bsfit.frms.portal.base.mapper.RolesMapper;
import cn.com.bsfit.frms.portal.base.mapper.RolesResourcesMapper;
import cn.com.bsfit.frms.portal.base.mapper.UsersMapper;
import cn.com.bsfit.frms.portal.base.mapper.UsersRolesMapper;
import cn.com.bsfit.frms.portal.base.pojo.Roles;
import cn.com.bsfit.frms.portal.base.pojo.RolesExample;
import cn.com.bsfit.frms.portal.base.pojo.RolesResources;
import cn.com.bsfit.frms.portal.base.pojo.RolesResourcesExample;
import cn.com.bsfit.frms.portal.base.pojo.Users;
import cn.com.bsfit.frms.portal.base.pojo.UsersExample;
import cn.com.bsfit.frms.portal.base.pojo.UsersRoles;
import cn.com.bsfit.frms.portal.base.pojo.UsersRolesExample;
import cn.com.bsfit.frms.portal.base.util.ExtJSResponse;
import cn.com.bsfit.frms.portal.base.util.Page;
import cn.com.bsfit.frms.portal.base.util.SysContent;
import cn.com.bsfit.frms.portal.base.util.TreeNode;
import cn.com.bsfit.frms.portal.bo.ResourcesBO;
import cn.com.bsfit.frms.portal.enums.Status;
import cn.com.bsfit.frms.portal.util.StringUtils;

/**
 * 系统角色管理Service
 * 
 * @author hjp
 * 
 * @since 1.0.0
 *
 */
@RestController
@RequestMapping("/roles")
public class RolesServerResource {

	@Autowired
	private ResourcesBO resourcesBO;
	@Autowired
	private UsersMapper usersMapper;
	@Autowired
	private RolesMapper rolesMapper;
	@Autowired
	private UsersRolesMapper usersRolesMapper;
	@Autowired
	private RolesResourcesMapper rolesResourcesMapper;
	
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public ExtJSResponse findRoles(final @RequestParam(value = "roleName", required = false) String roleName,
			final @RequestParam(value = "page", required = false, defaultValue = "1") String page,
			final @RequestParam(value = "start", required = false, defaultValue = "0") String start,
			final @RequestParam(value = "limit", required = false, defaultValue = "25") String limit) {
		// 设置查询条件
		RolesExample rolesExample = new RolesExample();
		RolesExample.Criteria criteria = rolesExample.createCriteria();
		criteria.andEnabledGreaterThan(Status.DELETED.getIndex());
		if (!StringUtils.isEmpty(roleName)) {
			try {
				criteria.andRoleNameLike("%" + URLDecoder.decode(roleName, "UTF-8") + "%");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		// 查询总数
		int total = rolesMapper.countByExample(rolesExample);
		rolesExample.setPage(new Page(start, limit));
		rolesExample.setOrderByClause("ID DESC");
		List<Roles> rolesList = rolesMapper.selectByExample(rolesExample);
		return ExtJSResponse.successRes4Find(rolesList, total);
	}

	/**
	 * 根据角色ID查找角色信息
	 * 
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value = "/findRoleByRoleId", method = { RequestMethod.GET })
	public ExtJSResponse findRoleByRoleId(final @RequestParam(value = "roleId", required = true) String roleId) {
		if (roleId != null && !"".equals(roleId)) {
			Roles roles = rolesMapper.selectByPrimaryKey(Integer.parseInt(roleId));
			return ExtJSResponse.successResWithData(roles);
		} else {
			return ExtJSResponse.errorRes("角色ID为空,无法查找角色信息!");
		}
	}

	/**
	 * 添加角色
	 * 
	 * @param roles
	 * @return
	 */
	@RequestMapping(value = "/add", method = { RequestMethod.POST })
	public ExtJSResponse addRoles(@RequestBody Roles roles) {
		if (roles == null) {
			LogDBUtil.error(this.getClass(), "新增角色出错，信息为空！");
			return ExtJSResponse.errorRes("新增角色出错，信息为空！");
		}
		// 角色名不能为空
		if(roles.getRoleName() == null || "".equals(roles.getRoleName())) {
			return ExtJSResponse.errorRes("新增角色出错，角色名不能为空！");
		}
		// 资源ID列表
		List<Integer> resourceIdList = roles.getResourceIdList();
		if (resourceIdList == null || resourceIdList.isEmpty() || (resourceIdList.size() == 1 && resourceIdList.contains(null))) {
			return ExtJSResponse.errorRes("新增角色出错，权限信息为空！");
		}
		// 当前登录用户
		Users currentUsers = SysContent.getUser();
		// 插入角色信息，并且根据角色名判断角色是否存在
		RolesExample rolesExample = new RolesExample();
		rolesExample.createCriteria().andRoleNameEqualTo(roles.getRoleName()).andEnabledGreaterThanOrEqualTo(Status.NORMAL.getIndex());
		List<Roles> rolesList = rolesMapper.selectByExample(rolesExample);
		if (rolesList == null || rolesList.size() == 0) {
			// 插入角色信息
			roles.setCreateTime(new Date());
			roles.setUpdateTime(new Date());
			roles.setModifer(currentUsers.getUserName());
			rolesMapper.insert(roles);
		} else {
			return ExtJSResponse.errorRes("新增角色出错，角色" + roles.getRoleName() + "已经存在!");
		}
		// 插入角色-资源关联关系
		List<RolesResources> rolesResourcesList = new ArrayList<RolesResources>(resourceIdList.size());
		for (Integer resourceId : resourceIdList) {
			RolesResources rolesResources = new RolesResources();
			rolesResources.setRolesId(roles.getId());
			rolesResources.setResourcesId(resourceId);
			if (resourceId != null) {
				rolesResourcesList.add(rolesResources);
			}
		}
		rolesResourcesMapper.insertBatch(rolesResourcesList);
		LogDBUtil.info(this.getClass(), "Roles[{}] inserted by Operator[{}].", roles.getRoleName(), currentUsers.getUserName());
		return ExtJSResponse.success();
	}

	/**
	 * 编辑角色
	 * 
	 * @param roles
	 * @return
	 */
	@RequestMapping(value = "/edit", method = { RequestMethod.POST })
	public ExtJSResponse editRoles(@RequestBody Roles roles) {
		if (roles == null) {
			LogDBUtil.error(this.getClass(), "编辑角色出错，信息为空！");
			return ExtJSResponse.errorRes("编辑色出错，信息为空！");
		}
		// 角色名不能为空
		if(roles.getRoleName() == null || "".equals(roles.getRoleName())) {
			return ExtJSResponse.errorRes("新增角色出错，角色名不能为空！");
		}
		// 当前登录用户
		Users currentUsers = SysContent.getUser();
		roles.setUpdateTime(new Date());
		roles.setModifer(currentUsers.getUserName());
		// 禁用状态
		if (roles.getEnabled().equals(Status.DISABLE.getIndex())) {
			// 得到不是删除状态的用户
			List<Integer> usersIdList = getUsersIdNotDeleted();
			// 判断该角色是否正在使用
			UsersRolesExample usersRolesExample = new UsersRolesExample();
			UsersRolesExample.Criteria criteria = usersRolesExample.createCriteria();
			criteria.andRolesIdEqualTo(roles.getId());
			if (usersIdList != null && usersIdList.size() != 0) {
				criteria.andUsersIdIn(usersIdList);
			}
			List<UsersRoles> usersRolesList = usersRolesMapper.selectByExample(usersRolesExample);
			if (usersRolesList != null && !usersRolesList.isEmpty()) {
				return ExtJSResponse.errorRes(roles.getRoleName() + "角色正在使用,无法禁用!请解除关联关系");
			}
		}
		// 资源ID列表
		List<Integer> resourceIdList = roles.getResourceIdList();
		if (resourceIdList == null || resourceIdList.isEmpty()) {
			return ExtJSResponse.errorRes("编辑角色出错，权限信息为空！");
		}
		// 根据角色名称判断角色是否存在
		RolesExample rolesExample = new RolesExample();
		rolesExample.createCriteria().andRoleNameEqualTo(roles.getRoleName()).andIdNotEqualTo(roles.getId())
				.andEnabledGreaterThanOrEqualTo(Status.NORMAL.getIndex());
		List<Roles> rolesList = rolesMapper.selectByExample(rolesExample);
		if (rolesList == null || rolesList.size() == 0) {
			// 更新角色信息
			rolesMapper.updateByPrimaryKeySelective(roles);
		} else {
			return ExtJSResponse.errorRes("编辑角色出错，角色:" + roles.getRoleName() + "已经存在,请重新添加!");
		}

		RolesResourcesExample rolesResourcesExample = new RolesResourcesExample();
		rolesResourcesExample.createCriteria().andRolesIdEqualTo(roles.getId());
		// 1,删除角色-资源的关联关系
		rolesResourcesMapper.deleteByExample(rolesResourcesExample);
		List<RolesResources> rolesResourcesList = new ArrayList<RolesResources>(resourceIdList.size());
		for (Integer resourceId : resourceIdList) {
			RolesResources rolesResources = new RolesResources();
			rolesResources.setRolesId(roles.getId());
			rolesResources.setResourcesId(resourceId);
			if (resourceId != null) {
				rolesResourcesList.add(rolesResources);
			}
		}
		// 2,插入角色资源关联关系
		rolesResourcesMapper.insertBatch(rolesResourcesList);
		LogDBUtil.info(this.getClass(), "Roles[{}] updated by Operator[{}].", roles.getRoleName(), currentUsers.getUserName());
		return ExtJSResponse.success();
	}

	/**
	 * 加载角色新建或编辑时的资源
	 * 
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value = "/loadRoles", method = { RequestMethod.GET })
	public ExtJSResponse loadRoles(final @RequestParam(value = "roleId", required = false) String roleId) {
		// 默认根节点
		Integer parentId = 0;
		if(roleId == null || "".equals(roleId)) {
			// 加载新建角色时的资源
			return ExtJSResponse.successResWithData(resourcesBO.getCheckedTree(parentId));
		} else {
			// 加载编辑角色时的资源
			RolesResourcesExample rolesResourcesExample = new RolesResourcesExample();
			rolesResourcesExample.createCriteria().andRolesIdEqualTo(Integer.parseInt(roleId));
			List<RolesResources> rolesResourcesList = rolesResourcesMapper.selectByExample(rolesResourcesExample);
			List<TreeNode> treeNodeList = resourcesBO.getCheckedTree(parentId);
			if(rolesResourcesList != null && rolesResourcesList.size() != 0) {
				roleResourcesChecked(rolesResourcesList, treeNodeList);
			}
			return ExtJSResponse.successResWithData(treeNodeList);
		}
	}
	
	/**
	 * 删除角色
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete", method = { RequestMethod.POST })
	public ExtJSResponse deleteRoles(@RequestBody Object param) throws Exception {
		final List<Map<String, Object>> objs = params2Attributes(param);
		// 当前登录用户
		Users currentUsers = SysContent.getUser();
		for (Map<String, Object> map : objs) {
			Integer roleId = (Integer) map.get("id");
			String roleName = map.get("roleName").toString();
			// 判断该角色是否正在使用
			UsersRolesExample usersRolesExample = new UsersRolesExample();
			usersRolesExample.createCriteria().andRolesIdEqualTo(roleId).andUsersIdIn(getUsersIdNotDeleted());
			List<UsersRoles> usersRolesList = usersRolesMapper.selectByExample(usersRolesExample);
			if (usersRolesList == null || usersRolesList.isEmpty()) {
				rolesMapper.deleteByPrimaryKey(roleId);
				// 删除角色-资源关联关系
				RolesResourcesExample rolesResourcesExample = new RolesResourcesExample();
				rolesResourcesExample.createCriteria().andRolesIdEqualTo(roleId);
				rolesResourcesMapper.deleteByExample(rolesResourcesExample);
				LogDBUtil.info(this.getClass(), "Roles[{}] deleted by Operator[{}].", roleName, currentUsers.getUserName());
			} else {
				return ExtJSResponse.errorRes(roleName + "角色正在使用,无法删除!请解除关联关系");
			}
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

	/**
	 * 得到不是删除状态的用户ID
	 * 
	 * @return
	 */
	private List<Integer> getUsersIdNotDeleted() {
		UsersExample example = new UsersExample();
		example.createCriteria().andEnabledGreaterThanOrEqualTo(Status.NORMAL.getIndex());
		List<Users> usersList = usersMapper.selectByExample(example);
		if (usersList == null || usersList.size() == 0) {
			return null;
		}
		List<Integer> usersIdList = new ArrayList<Integer>(usersList.size());
		for (Users users : usersList) {
			usersIdList.add(users.getId());
		}
		return usersIdList;
	}
	
	/**
	 * 递归构造角色树选中
	 * 
	 * @param rolesResourcesList
	 * @param treeNodeList
	 */
	private void roleResourcesChecked(List<RolesResources> rolesResourcesList, List<TreeNode> treeNodeList) {
		if (treeNodeList == null || treeNodeList.size() == 0) {
			return;
		}
		for (TreeNode treeNode : treeNodeList) {
			for (RolesResources rolesResources : rolesResourcesList) {
				if (treeNode.getId().equals(rolesResources.getResourcesId())) {
					treeNode.setChecked(true);
				}
			}
			roleResourcesChecked(rolesResourcesList, treeNode.getChildren());
		}
	}
}