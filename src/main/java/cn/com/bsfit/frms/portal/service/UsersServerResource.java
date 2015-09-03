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
import cn.com.bsfit.frms.portal.base.mapper.UsersMapper;
import cn.com.bsfit.frms.portal.base.mapper.UsersRolesMapper;
import cn.com.bsfit.frms.portal.base.pojo.Roles;
import cn.com.bsfit.frms.portal.base.pojo.RolesExample;
import cn.com.bsfit.frms.portal.base.pojo.Users;
import cn.com.bsfit.frms.portal.base.pojo.UsersDetail;
import cn.com.bsfit.frms.portal.base.pojo.UsersExample;
import cn.com.bsfit.frms.portal.base.pojo.UsersRoles;
import cn.com.bsfit.frms.portal.base.pojo.UsersRolesExample;
import cn.com.bsfit.frms.portal.base.util.ExtJSResponse;
import cn.com.bsfit.frms.portal.base.util.MD5;
import cn.com.bsfit.frms.portal.base.util.Page;
import cn.com.bsfit.frms.portal.base.util.SysContent;
import cn.com.bsfit.frms.portal.enums.Status;
import cn.com.bsfit.frms.portal.pojo.UserRoles;
import cn.com.bsfit.frms.portal.util.StringUtils;

/**
 * 系统用户管理service
 * 
 * @author hjp
 * 
 * @since 1.0.0
 *
 */
@RestController
@RequestMapping("/users")
public class UsersServerResource {

	@Autowired
	private UsersMapper usersMapper;
	@Autowired
	private RolesMapper rolesMapper;
	@Autowired
	private UsersRolesMapper usersRolesMapper;
	
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public ExtJSResponse findUsers(final @RequestParam(value = "userName", required = false) String userName,
			final @RequestParam(value = "userRoleIds", required = false) String userRoleIds,
			final @RequestParam(value = "page", required = false, defaultValue = "1") String page,
			final @RequestParam(value = "start", required = false, defaultValue = "0") String start,
			final @RequestParam(value = "limit", required = false, defaultValue = "25") String limit) {
		// 设置查询条件
		UsersExample example = new UsersExample();
		UsersExample.Criteria criteria = example.createCriteria();
		criteria.andEnabledGreaterThan(Status.DELETED.getIndex());
		if (!StringUtils.isEmpty(userName)) {
			try {
				criteria.andUserNameLike("%" + URLDecoder.decode(userName, "UTF-8") + "%");
			} catch (UnsupportedEncodingException e) {
				LogDBUtil.error(this.getClass(), e.getMessage());
				e.printStackTrace();
			}
		}
		// 用户ID List
		List<Integer> userIdList = findUserIdByRoleId(userRoleIds);

		if (userIdList != null && userIdList.size() != 0) {
			criteria.andIdIn(userIdList);
		}
		// 查询总数,分页用
		int total = usersMapper.countByExample(example);
		example.setPage(new Page(start, limit));
		example.setOrderByClause("ID DESC");
		List<UsersDetail> usersDetailList = usersMapper.selectUsersDetailByExample(example);
		return ExtJSResponse.successRes4Find(buildUserDetailList(usersDetailList), total);
	}

	/**
	 * 组装角色名
	 * 
	 * @param usersDetailList
	 */
	private List<UsersDetail> buildUserDetailList(List<UsersDetail> usersDetailList) {
		List<UsersDetail> usersDetails = new ArrayList<UsersDetail>();
		if (usersDetailList == null || usersDetailList.isEmpty()) {
			return null;
		}
		for (UsersDetail usersDetail : usersDetailList) {
			String roleNames = usersRolesMapper.selectRoleNamesByUserId(usersDetail.getId());
			usersDetail.setRoleNames(roleNames);
			usersDetail.setPassword(null);

			usersDetails.add(usersDetail);
		}
		return usersDetails;
	}
	
	/**
	 * 根据用户ID查找用户信息
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/findUsersByUserId", method = { RequestMethod.GET })
	public ExtJSResponse findUsersByUserId(final @RequestParam(value = "userId", required = true) String userId) {
		if (userId != null && !"".equals(userId)) {
			Users users = usersMapper.selectByPrimaryKey(Integer.parseInt(userId));
			users.setPassword(null);
			return ExtJSResponse.successResWithData(users);
		} else {
			return ExtJSResponse.errorRes("用户ID为空,无法查找用户信息!");
		}
	}

	/**
	 * 添加用户
	 * 
	 * @param users
	 * @return
	 */
	@RequestMapping(value = "/add", method = { RequestMethod.POST })
	public ExtJSResponse addUsers(@RequestBody Users users) {
		// 当前登录用户
		Users currentUsers = SysContent.getUser();
		if (users == null) {
			LogDBUtil.error(this.getClass(), "新增用户出错，用户信息为空！");
			return ExtJSResponse.errorRes("新增用户出错，用户信息为空！");
		}
		// 用户名不能为空
		if(users.getUserName() == null || "".equals(users.getUserName())) {
			return ExtJSResponse.errorRes("新增用户出错，用户名不能为空！");
		}
		// 用户角色
		List<Integer> roleIdList = users.getRoleIdList();
		if (roleIdList == null || roleIdList.size() == 0) {
			return ExtJSResponse.errorRes("新增用户出错，角色信息为空！");
		}
		// 根据用户名判断用户是否存在
		UsersExample usersExample = new UsersExample();
		usersExample.createCriteria().andUserNameEqualTo(users.getUserName())
				.andEnabledGreaterThanOrEqualTo(Status.NORMAL.getIndex());
		List<Users> usersList = usersMapper.selectByExample(usersExample);
		if (usersList == null || usersList.size() == 0) {
			// 插入用户信息
			users.setCreateTime(new Date());
			users.setUpdateTime(new Date());
			users.setModifer(currentUsers.getUserName());
			usersMapper.insertSelective(users);
		} else {
			return ExtJSResponse.errorRes("新增用户出错，用户" + users.getUserName() + "已经存在");
		}
		List<UsersRoles> usersRolesList = new ArrayList<UsersRoles>();
		for (Integer roleId : roleIdList) {
			UsersRoles usersRoles = new UsersRoles();
			usersRoles.setUsersId(users.getId());
			usersRoles.setRolesId(roleId);

			usersRolesList.add(usersRoles);
		}
		// 插入用户和角色的关联关系
		usersRolesMapper.insertBatch(usersRolesList);
		LogDBUtil.info(this.getClass(), "Users[{}] inserted by Operator[{}].", users.getUserName(), currentUsers.getUserName());
		return ExtJSResponse.success();
	}

	/**
	 * 编辑用户
	 * 
	 * @param users
	 * @return
	 */
	@RequestMapping(value = "/edit", method = { RequestMethod.POST })
	public ExtJSResponse editUsers(@RequestBody Users users) throws Exception {
		if (users == null) {
			LogDBUtil.error(this.getClass(), "编辑用户出错，用户信息为空！");
			return ExtJSResponse.errorRes("编辑用户出错，用户信息为空！");
		}
		// 用户名不能为空
		if(users.getUserName() == null || "".equals(users.getUserName())) {
			return ExtJSResponse.errorRes("新增用户出错，用户名不能为空！");
		}
		// 用户角色
		List<Integer> roleIdList = users.getRoleIdList();
		if (roleIdList == null || roleIdList.size() == 0) {
			return ExtJSResponse.errorRes("编辑用户出错，角色信息为空！");
		}
		// 当前登录用户
		Users currentUsers = SysContent.getUser();
		users.setModifer(currentUsers.getUserName());
		users.setUpdateTime(new Date());
		// 非正常状态
		if (!users.getEnabled().equals(Status.NORMAL.getIndex())) {
			if (users.getUserName().equals("admin")) {
				throw new Exception("用户admin为默认管理员用户,无法禁用");
			}
			if (users.getUserName().equals(currentUsers.getUserName())) {
				throw new Exception("用户" + users.getUserName() + "为当前用户,无法禁用");
			}
		}
		// 根据用户名判断用户是否存在
		UsersExample usersExample = new UsersExample();
		usersExample.createCriteria().andUserNameEqualTo(users.getUserName()).andIdNotEqualTo(users.getId())
				.andEnabledGreaterThanOrEqualTo(Status.NORMAL.getIndex());
		List<Users> usersList = usersMapper.selectByExample(usersExample);
		if (usersList == null || usersList.size() == 0) {
			// 插入用户信息
			users.setUpdateTime(new Date());
			users.setModifer(currentUsers.getUserName());
			String password = users.getPassword();
			if (password != null && !"".equals(password)) {
				users.setPassword(MD5.md5(password));
			} else {
				users.setPassword(null);
			}
			usersMapper.updateByPrimaryKeySelective(users);
		} else {
			return ExtJSResponse.errorRes("编辑用户出错，用户" + users.getUserName() + "已经存在");
		}

		if (roleIdList.size() == 0) {
			UsersRolesExample usersRolesExample = new UsersRolesExample();
			usersRolesExample.createCriteria().andUsersIdEqualTo(users.getId());
			// 删除用户-角色的关联关系
			usersRolesMapper.deleteByExample(usersRolesExample);
			return ExtJSResponse.success();
		}
		UsersRolesExample usersRolesExample = new UsersRolesExample();
		usersRolesExample.createCriteria().andUsersIdEqualTo(users.getId());
		// 1,删除用户-角色的关联关系
		usersRolesMapper.deleteByExample(usersRolesExample);

		List<UsersRoles> usersRolesList = new ArrayList<UsersRoles>();
		for (Integer roleId : roleIdList) {
			UsersRoles usersRoles = new UsersRoles();
			usersRoles.setUsersId(users.getId());
			usersRoles.setRolesId(roleId);

			usersRolesList.add(usersRoles);
		}
		// 2,插入用户-角色关联关系
		usersRolesMapper.insertBatch(usersRolesList);

		LogDBUtil.info(this.getClass(), "Users[{}] updated by Operator[{}].", users.getUserName(), currentUsers.getUserName());
		return ExtJSResponse.success();
	}

	/**
	 * 删除用户
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/delete", method = { RequestMethod.POST })
	public ExtJSResponse deleteUsers(@RequestBody Object param) {
		// 当前登录用户
		Users currentUsers = SysContent.getUser();
		final List<Map<String, Object>> objs = params2Attributes(param);
		for (Map<String, Object> map : objs) {
			Integer userId = (Integer) map.get("id");
			String userName = map.get("userName").toString();
			if (userName.equals("admin")) {
				return ExtJSResponse.errorRes("用户admin为默认管理员用户,无法删除!");
			} else if (userName.equals(currentUsers.getUserName())) {
				return ExtJSResponse.errorRes("用户" + userName + "为当前用户,无法删除!");
			} else {
				usersMapper.deleteByPrimaryKey(userId);
				// 删除用户-角色关联关系
				UsersRolesExample usersRolesExample = new UsersRolesExample();
				usersRolesExample.createCriteria().andUsersIdEqualTo(userId);
				usersRolesMapper.deleteByExample(usersRolesExample);
				LogDBUtil.info(this.getClass(), "Users[{}] deleted by Operator[{}].", userName, currentUsers.getUserName());
			}
		}
		return ExtJSResponse.success();
	}

	/**
	 * 加载用户的角色
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/loadUsersRoles", method = { RequestMethod.GET })
	public ExtJSResponse loadUsersRoles(final @RequestParam(value = "userId", required = false) String userId) {
		// 先获取所有的权限信息
		RolesExample rolesExample = new RolesExample();
		rolesExample.createCriteria().andEnabledEqualTo(Status.NORMAL.getIndex());
		List<Roles> rolesList = rolesMapper.selectByExample(rolesExample);
		List<UserRoles> userRolesList = new ArrayList<UserRoles>(rolesList.size());
		for(Roles roles : rolesList) {
			UserRoles userRoles = new UserRoles();
			userRoles.setRolesId(roles.getId());
			userRoles.setRolesName(roles.getRoleName());
			userRoles.setChecked(false);
						
			userRolesList.add(userRoles);
		}
		// 如果userId不为空,示此时的操作是edit 将用户原有权限的checked置为true
		if (userId != null && !"".equals(userId)) {
			UsersRolesExample usersRolesExample = new UsersRolesExample();
			usersRolesExample.createCriteria().andUsersIdEqualTo(Integer.parseInt(userId));
			List<UsersRoles> usersRolesList = usersRolesMapper.selectByExample(usersRolesExample);
			for (UsersRoles usersRoles : usersRolesList) {
				for (UserRoles userRoles : userRolesList) {
					if (usersRoles.getRolesId().compareTo(userRoles.getRolesId()) == 0) {
						userRoles.setChecked(true);
					}
				}
			}
			return ExtJSResponse.successResWithData(userRolesList);
		} 
		return ExtJSResponse.successResWithData(userRolesList);
	}

	/**
	 * 得到当前用户信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/whoAmI", method = { RequestMethod.GET })
	public ExtJSResponse whoAmI() {
		ExtJSResponse response = new ExtJSResponse(true);
		// 当前登录用户
		Users currentUsers = SysContent.getUser();
		if (currentUsers != null) {
			response.put("userName", currentUsers.getRealName());
			response.put("userId", currentUsers.getUserName());
			response.put("title", currentUsers.getTitle());
		} else {
			response.setSuccess(false);
			response.setErrorMsg("会话已过期，请刷新当前页并重新登录");
		}
		return response;
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
	 * 根据角色ID查询用户和角色关联信息
	 * 
	 * @param userRoleIds
	 *            角色ID
	 * @return 用户ID List
	 */
	private List<Integer> findUserIdByRoleId(final String userRoleIds) {
		if (userRoleIds != null && !"".equals(userRoleIds)) {
			List<Integer> userIdList = new ArrayList<Integer>();
			if (userRoleIds.contains(",")) {
				// 如果是多选框
				String[] roleIds = userRoleIds.split(",");
				List<Integer> roleIdList = new ArrayList<Integer>(roleIds.length);
				for (int i = 0; i < roleIds.length; i++) {
					roleIdList.add(Integer.parseInt(roleIds[i]));
				}
				UsersRolesExample usersRolesExample = new UsersRolesExample();
				usersRolesExample.createCriteria().andRolesIdIn(roleIdList);
				List<UsersRoles> usersRolesList = usersRolesMapper.selectByExample(usersRolesExample);
				for (UsersRoles usersRoles : usersRolesList) {
					userIdList.add(usersRoles.getUsersId());
				}
			} else {
				// 单选框
				if (userRoleIds.equals("-1")) {
					// 全部
					return null;
				}
				UsersRolesExample usersRolesExample = new UsersRolesExample();
				usersRolesExample.createCriteria().andRolesIdEqualTo(Integer.parseInt(userRoleIds));
				List<UsersRoles> usersRolesList = usersRolesMapper.selectByExample(usersRolesExample);
				for (UsersRoles usersRoles : usersRolesList) {
					userIdList.add(usersRoles.getUsersId());
				}
			}
			if (userIdList == null || userIdList.isEmpty()) {
				userIdList.add(-1);
			}
			return userIdList;
		} else {
			return null;
		}
	}
}