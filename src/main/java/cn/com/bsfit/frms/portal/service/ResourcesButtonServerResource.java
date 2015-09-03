package cn.com.bsfit.frms.portal.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.com.bsfit.frms.portal.base.mapper.ResourcesMapper;
import cn.com.bsfit.frms.portal.base.mapper.RolesMapper;
import cn.com.bsfit.frms.portal.base.mapper.RolesResourcesMapper;
import cn.com.bsfit.frms.portal.base.mapper.UsersRolesMapper;
import cn.com.bsfit.frms.portal.base.pojo.Resources;
import cn.com.bsfit.frms.portal.base.pojo.ResourcesButton;
import cn.com.bsfit.frms.portal.base.pojo.ResourcesExample;
import cn.com.bsfit.frms.portal.base.pojo.Roles;
import cn.com.bsfit.frms.portal.base.pojo.RolesExample;
import cn.com.bsfit.frms.portal.base.pojo.RolesResources;
import cn.com.bsfit.frms.portal.base.pojo.RolesResourcesExample;
import cn.com.bsfit.frms.portal.base.pojo.Users;
import cn.com.bsfit.frms.portal.base.pojo.UsersRoles;
import cn.com.bsfit.frms.portal.base.pojo.UsersRolesExample;
import cn.com.bsfit.frms.portal.base.util.ExtJSResponse;
import cn.com.bsfit.frms.portal.base.util.SysContent;
import cn.com.bsfit.frms.portal.enums.Status;

/**
 * 资源按钮Service
 * 
 * @author hjp
 * 
 * @since 1.0.0
 * 
 */
@RestController
@RequestMapping("/resourcesButton")
public class ResourcesButtonServerResource {
	
	@Autowired
	private RolesMapper rolesMapper;
	@Autowired
	private ResourcesMapper resourcesMapper;
	@Autowired
	private UsersRolesMapper usersRolesMapper;
	@Autowired
	private RolesResourcesMapper rolesResourcesMapper;
	
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public ExtJSResponse findResourcesButton() {
		// 当前登录用户
		Users currentUsers = SysContent.getUser();
		List<ResourcesButton> resourcesButtonList = new ArrayList<ResourcesButton>();
		UsersRolesExample usersRolesExample = new UsersRolesExample();
		usersRolesExample.createCriteria().andUsersIdEqualTo(currentUsers.getId()).andRolesIdIn(getRoleIds());
		// 从用户-角色关联表中取出角色ID
		List<UsersRoles> usersRolesList = usersRolesMapper.selectByExample(usersRolesExample);
		if(usersRolesList == null || usersRolesList.isEmpty()) {
			return ExtJSResponse.success();
		}
        Set<Integer> oldResourceIds = new HashSet<Integer>();
		for (UsersRoles usersRoles : usersRolesList) {
			RolesResourcesExample rolesResourcesExample = new RolesResourcesExample();
			rolesResourcesExample.createCriteria().andRolesIdEqualTo(usersRoles.getRolesId());
			// 从角色-资源关联表中取出资源ID
			List<RolesResources> rolesResourcesList = rolesResourcesMapper.selectByExample(rolesResourcesExample);
            if(rolesResourcesList == null || rolesResourcesList.isEmpty()) {
				return ExtJSResponse.success();
			}
			for (RolesResources rolesResources : rolesResourcesList) {
				oldResourceIds.add(rolesResources.getResourcesId());
			}
		}
		List<Integer> resourceIdList = new ArrayList<Integer>(oldResourceIds);
		ResourcesExample resourcesExample = new ResourcesExample();
        resourcesExample.createCriteria().andIdIn(resourceIdList);
		List<Resources> resourcesList = resourcesMapper.selectByExample(resourcesExample);
		if(resourcesList == null || resourcesList.isEmpty()) {
			return ExtJSResponse.success();
		}
		for (Resources resources : resourcesList) {
			if(resources.getResourceType().equals(0)) {
				ResourcesButton resourcesButton = new ResourcesButton(resources.getResourceUrl(), 1, resources.getResourceCode());
				resourcesButtonList.add(resourcesButton);
			}
		}
		return ExtJSResponse.successRes4Find(resourcesButtonList, resourcesButtonList.size());
	}
	
	@RequestMapping(value = "/findByResourceCode", method= { RequestMethod.GET })
	public ExtJSResponse findResourcesButtonByResourceCode(final @RequestParam(value = "resourceCode", required = false) String resourceCode) {
		// 当前登录用户
		Users currentUsers = SysContent.getUser();
		List<ResourcesButton> resourcesButtonList = new ArrayList<ResourcesButton>();
		UsersRolesExample usersRolesExample = new UsersRolesExample();
		usersRolesExample.createCriteria().andUsersIdEqualTo(currentUsers.getId()).andRolesIdIn(getRoleIds());
		// 从用户-角色关联表中取出角色ID
		List<UsersRoles> usersRolesList = usersRolesMapper.selectByExample(usersRolesExample);
		if(usersRolesList == null || usersRolesList.isEmpty()) {
			return ExtJSResponse.success(); 
		}
        Set<Integer> oldResourceIds = new HashSet<Integer>();
		for (UsersRoles usersRoles : usersRolesList) {
			RolesResourcesExample rolesResourcesExample = new RolesResourcesExample();
			rolesResourcesExample.createCriteria().andRolesIdEqualTo(usersRoles.getRolesId());
			// 从角色-资源关联表中取出资源ID
			List<RolesResources> rolesResourcesList = rolesResourcesMapper.selectByExample(rolesResourcesExample);
            if(rolesResourcesList == null || rolesResourcesList.isEmpty()) {
				return ExtJSResponse.success();
			}
			for (RolesResources rolesResources : rolesResourcesList) {
				oldResourceIds.add(rolesResources.getResourcesId());
			}
		}
		List<Integer> resourceIdList = new ArrayList<Integer>(oldResourceIds);
		ResourcesExample resourcesExample = new ResourcesExample();
		resourcesExample.createCriteria().andIdIn(resourceIdList);
		List<Resources> resourcesList = resourcesMapper.selectByExample(resourcesExample);
		if(resourcesList == null || resourcesList.isEmpty()) {
			return ExtJSResponse.success();
		}
		if(resourceCode == null || "".equals(resourceCode)) {
			return ExtJSResponse.errorRes("资源编号resourceCode不能为空!");
		}
		for (Resources resources : resourcesList) {
			if(resourceCode.equals(resources.getResourceCode()) && resources.getResourceType().equals(0)) {
				ResourcesButton resourcesButton = new ResourcesButton(resources.getResourceUrl(), 1, resources.getResourceCode());
				resourcesButtonList.add(resourcesButton);
			}
		}
		return ExtJSResponse.successRes4Find(resourcesButtonList, resourcesButtonList.size());
	}
	
	/**
	 * 取出所有启用状态的角色ID
	 * 
	 * @return
	 */
	private List<Integer> getRoleIds() {
		RolesExample rolesExample = new RolesExample();
		rolesExample.createCriteria().andEnabledEqualTo(Status.NORMAL.getIndex());
		List<Roles> rolesList = rolesMapper.selectByExample(rolesExample);
		List<Integer> roleIdList = new ArrayList<Integer>(rolesList.size());
		if(rolesList == null || rolesList.isEmpty()) {
			return null;
		}
		for (Roles roles : rolesList) {
			roleIdList.add(roles.getId());
		}
		return roleIdList;
	}
}