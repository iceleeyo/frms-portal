package cn.com.bsfit.frms.portal.bo.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.bsfit.frms.portal.base.mapper.ResourcesMapper;
import cn.com.bsfit.frms.portal.base.mapper.UsersMapper;
import cn.com.bsfit.frms.portal.base.mapper.UsersRolesMapper;
import cn.com.bsfit.frms.portal.base.pojo.Resources;
import cn.com.bsfit.frms.portal.base.pojo.Users;
import cn.com.bsfit.frms.portal.base.pojo.UsersExample;
import cn.com.bsfit.frms.portal.base.pojo.UsersRoles;
import cn.com.bsfit.frms.portal.base.pojo.UsersRolesExample;
import cn.com.bsfit.frms.portal.base.util.SysContent;
import cn.com.bsfit.frms.portal.bo.RedisBO;
import cn.com.bsfit.frms.portal.bo.UsersBO;
import cn.com.bsfit.frms.portal.enums.MenuType;
import cn.com.bsfit.frms.portal.enums.Status;
import cn.com.bsfit.frms.portal.util.CaseSource;
import cn.com.bsfit.frms.portal.util.Constants;
import cn.com.bsfit.frms.portal.util.StringUtils;

@Service
public class UsersBOImpl implements UsersBO {

	@Autowired
	private RedisBO redisBO;
	@Autowired
	private UsersMapper usersMapper;
	@Autowired
	private ResourcesMapper resourcesMapper;
	@Autowired
	private UsersRolesMapper usersRolesMapper;

	private UsersBOImpl() {
		
	}
	
	@Override
	public Users getUsersByNameAndPwd(final String userName, final String password) {
		if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
			return null;
		}
		UsersExample example = new UsersExample();
		example.createCriteria().andUserNameEqualTo(userName).andPasswordEqualTo(password).andEnabledEqualTo(Status.NORMAL.getIndex());
		List<Users> usersList = usersMapper.selectByExample(example);
		if (usersList == null || usersList.isEmpty()) {
			return null;
		}
		Users users = usersList.get(0);
		
		List<Integer> roleIdList = new ArrayList<Integer>();
		UsersRolesExample usersRolesExample = new UsersRolesExample();
		usersRolesExample.createCriteria().andUsersIdEqualTo(users.getId());
		List<UsersRoles> usersRolesList = usersRolesMapper.selectByExample(usersRolesExample);
		// 当前用户角色List
		if(usersRolesList != null && usersRolesList.size() != 0) {
			for (UsersRoles usersRoles : usersRolesList) {
				roleIdList.add(usersRoles.getRolesId());
			}
		}
		// 设置角色ID列表
		users.setRoleIdList(roleIdList);
		// 设置资源编号
		users.setResourceCodeList(usersMapper.selectResourceCodeByUserId(users.getId()));
		return users;
	}

	@Override
	public Users findUsersById(final Integer userId) {
		Users users = usersMapper.selectByPrimaryKey(userId);
		users.setResourceCodeList(usersMapper.selectResourceCodeByUserId(userId));
		return users;
	}

	@Override
	public List<Users> findAllUsers() {
		List<Users> list = new ArrayList<Users>();
		UsersExample example = new UsersExample();
		example.createCriteria().andEnabledGreaterThanOrEqualTo(Status.NORMAL.getIndex());
		List<Users> usersList = usersMapper.selectByExample(example);
		if(usersList != null && usersList.size() != 0) {
			for (Users users : usersList) {
				Integer usersId = users.getId();
				users.setResourceCodeList(usersMapper.selectResourceCodeByUserId(usersId));
				
				list.add(users);
			}
		}
		return list;
	}
	
	@Override
	public List<Users> findUsersByResourceCode(final String resourceCode) {
		List<Users> usersList = new ArrayList<Users>();
		List<Users> list = usersMapper.selectUsersByResourceCode(resourceCode);
		if(list != null && list.size() != 0) {
			for (Users users : list) {
				Integer usersId = users.getId();
				users.setResourceCodeList(usersMapper.selectResourceCodeByUserId(usersId));
				
				usersList.add(users);
			}
		}
		return usersList;
	}
	
	@Override
	public List<CaseSource> findCaseUsers() {
		List<CaseSource> caseSourceList = new ArrayList<CaseSource>();
		Map<Integer, Object> map = new HashMap<Integer, Object>();
		// 案件的父级ID
		Integer parentId = Constants.CASE_PARENT_ID;
		List<Resources> resources = resourcesMapper.selectByParentId(parentId);
		List<Resources> resourcesList = resourcesMapper.selectByUsersId(SysContent.getUser().getId(), MenuType.ISMENU.getIndex());
		for(int i = 0; i < resources.size(); i++) {
			Resources re = resources.get(i);
			if(resourcesList.contains(re)) {
				map.put((i + 1), re.getResourceName().replace("管理", ""));
			}
		}
		Iterator<Entry<Integer, Object>> iterator = map.entrySet().iterator();
		while(iterator.hasNext()) {
			Entry<Integer, Object> entry = iterator.next();
			CaseSource caseSource = new CaseSource();
			caseSource.setCode(entry.getKey());
			caseSource.setName(entry.getValue().toString());
			caseSourceList.add(caseSource);
		}
		return caseSourceList;
	}
	
	@Override
	public Long removeUsersCache(final String key) {
		return redisBO.del(key);
	}

	@Override
	public Object getUsersCache(final String key) {
		return redisBO.get(key);
	}

	@Override
	public Boolean storeUsersCache(final String key, final Object value, final Long seconds) {
		return redisBO.set(key, value, seconds);
	}

	@Override
	public Boolean expireUsersCache(String key, Long seconds) {
		return redisBO.expire(key, seconds);
	}
}
