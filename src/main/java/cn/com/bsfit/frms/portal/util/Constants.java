package cn.com.bsfit.frms.portal.util;

/**
 * 系统中的常量定义在这里
 * 
 * @author hjp
 * 
 * @since 1.2.0
 *
 */
public class Constants {

	// COOKIE名称
	public final static String CSF_COOKIE_NAME = "FRMS-PORTAL";

	// redis中用户信息存活时间单位秒
	public final static Long USER_LIVE_TIME = 3600L;
	
	// redis中规则信息存活时间单位秒
	public final static Long RULES_LIVE_TIME = 60L;
	
	// 案件父级菜单ID
	public final static Integer CASE_PARENT_ID = 14;

	// 根据用户名查询用户信息SQL
	public final static String USER_BY_NAME_QUERY = "SELECT USER_NAME, PASSWORD, ENABLED FROM USERS WHERE USER_NAME = ?";

	// 根据用户名查询用户权限
	public final static String AUTHORITIES_BY_NAME_QUERY = "SELECT DISTINCT t.USERNAME, t.RESOURCE_CODE FROM "
			+ "(SELECT uurr.ID,uurr.USERNAME,uurr.ROLES_ID,uurr.RESOURCES_ID,r.RESOURCE_CODE FROM "
			+ "(SELECT uur.ID,uur.USERNAME,uur.ROLES_ID,rr.RESOURCES_ID FROM "
			+ "(SELECT u.ID, u.USER_NAME USERNAME, ur.ROLES_ID FROM "
			+ "(SELECT ID, USER_NAME, PASSWORD FROM USERS WHERE USER_NAME = ?) u"
			+ "INNER JOIN USERS_ROLES ur ON ur.USERS_ID = u.ID) uur "
			+ "INNER JOIN ROLES_RESOURCES rr ON rr.ROLES_ID = uur.ROLES_ID) uurr "
			+ "INNER JOIN RESOURCES r ON uurr.RESOURCES_ID = r.ID)t " + "WHERE t.RESOURCE_CODE IS NOT NULL";
}
