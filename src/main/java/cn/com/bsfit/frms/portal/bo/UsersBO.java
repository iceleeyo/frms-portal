package cn.com.bsfit.frms.portal.bo;

import java.util.List;

import cn.com.bsfit.frms.portal.base.pojo.Users;
import cn.com.bsfit.frms.portal.util.CaseSource;

/**
 * 系统用户接口
 * 
 * @author hjp
 * 
 * @since 1.0.0
 *
 */
public interface UsersBO {

	/**
	 * 根据用户名和密码查找用户
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	public Users getUsersByNameAndPwd(final String userName, final String password);
	
	/**
	 * 根据用户ID查询用户信息
	 * 
	 * @param userId
	 * @return
	 */
	public Users findUsersById(final Integer userId);
	
	/**
	 * 查找所有的用户信息
	 * 
	 * @return
	 */
	public List<Users> findAllUsers();
	
	/**
	 * 根据资源编号查询用户信息
	 * 
	 * @param resourceCode
	 * @return
	 */
	public List<Users> findUsersByResourceCode(final String resourceCode);
	
	/**
	 * 查询用户是否具有案件的权限
	 * 1,柜台业务核查单;2,银行卡核查单;3,公务卡核查单
	 * 
	 * @return
	 */
	public List<CaseSource> findCaseUsers();
	
	/**
	 * 把用户信息从缓存中移除
	 * 
	 * @param key
	 * @return
	 */
	public Long removeUsersCache(final String key);
	
	/**
	 * 根据cookie中的信息在redis中查找当前登陆用户
	 * 
	 * @param key
	 * @return
	 */
	public Object getUsersCache(final String key);

	/**
	 * 缓存当前登陆信息
	 * @param key
	 * @param value
	 * @param seconds
	 * @return
	 */
	public Boolean storeUsersCache(final String key, final Object value, final Long seconds);
	
	/**
	 * 设置用户缓存信息过期时间
	 * 
	 * @param key
	 * @param seconds
	 * @return
	 */
	public Boolean expireUsersCache(final String key, final Long seconds);
}