package cn.com.bsfit.frms.portal.util;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import cn.com.bsfit.frms.ds.pojo.DSOperation;
import cn.com.bsfit.frms.obj.AuditObject;
import cn.com.bsfit.frms.portal.base.util.DateUtil;

public class BSUtil {
	
	public static LinkedHashMap<String, String> formatMaps = new LinkedHashMap<String, String>();
	
	static {
		formatMaps.put("png", "image/x-png");
		formatMaps.put("bmp", "image/x-ms-bmp");
		formatMaps.put("jpg", "image/jpeg");
		formatMaps.put("tif", "image/tiff");
		formatMaps.put("svg", "image/svg+xml");
		formatMaps.put("pdf", "application/pdf");
		formatMaps.put("xdot", "text/plain");
	}

	public static String trimString(final Object string) {
		if (string == null)
			return "";
		else
			return string.toString().trim();
	}

	public static Map<String, String> getQueryMap(String query) {
		if (BSUtil.allNull(query))
			return null;
		String[] params = query.split("&");
		Map<String, String> map = new HashMap<String, String>();
		for (String param : params) {
			String name = param.split("=")[0];
			String value = param.split("=")[1];
			map.put(name, value);
		}
		return map;
	}

	/**
	 * @param strings
	 * @return true if one of strings is not null
	 */
	public static boolean oneNotNull(String... strings) {
		for (String string : strings) {
			if (string != null && !string.equals(""))
				return true;
		}
		return false;
	}

	/**
	 * @param strings
	 * @return true if one of the strings is null;
	 */
	public static boolean allNull(String... strings) {
		for (String string : strings) {
			if (string == null || string.equals(""))
				return true;
		}
		return false;
	}

	/**
	 * @param cols
	 * @return true if one of the cols is null;
	 */
	public static boolean allNull(Collection<?>... cols) {
		for (Collection<?> col : cols) {
			if (col == null || col.size() == 0)
				return true;
		}
		return false;
	}

	public static String object2String(Object obj) {
		if (obj == null)
			return "";
		else
			return obj.toString();
	}

	public static String li2Yuan(String li) {
		return new BigDecimal(li).divide(new BigDecimal(1000)).toString();
	}

	public static BigDecimal li2Yuan(Long li) {
		if (li == null)
			return new BigDecimal(0);
		return new BigDecimal(li).divide(new BigDecimal(1000));
	}

	public static long yuan2Li(String yuan) {
		return new BigDecimal(yuan).multiply(new BigDecimal(1000)).longValue();
	}

	public static Short booleanToShort(Boolean string) {
		if (string)
			return 1;
		else
			return 0;
	}

	public static Boolean shortToBoolean(Short string) {
		if (string == 1)
			return true;
		else
			return false;
	}

	public static boolean checkUrl(String url) {
		return url.matches("(([a-zA-z0-9]|-){1,}\\.){1,}[a-zA-z0-9]{1,}-*");
	}

	/**
	 * 操作表转换为AuditObject
	 * 
	 * @param oper
	 *            操作表对象
	 * @return 探头对象
	 */
	public static AuditObject trans2AuditObject(DSOperation dsOperation) {
		AuditObject auditObj = new AuditObject();
		if (dsOperation != null) {
			auditObj.setPrimaryKey(dsOperation.getUserId());// 用户号
			auditObj.setUserId(dsOperation.getUserId());// 用户号
			auditObj.setBizCode(dsOperation.getOperType());// 业务号/操作类型
			auditObj.put("bizGroup", dsOperation.getOperType());
			auditObj.setTransId(dsOperation.getPayId());// 交易号/支付标识
			auditObj.setTransVol(dsOperation.getPayAmt());// 支付金额
			auditObj.setUuid(dsOperation.getUuid());// 操作唯一标识
			auditObj.setBizCategory(dsOperation.getBizCate());// 业务组
			auditObj.setIpAddr(dsOperation.getIpAddr());// ip地址
			auditObj.setMacAddr(dsOperation.getMac());// mac
			auditObj.setUserAgent(dsOperation.getBrowserVer());// 浏览器版本号
			auditObj.setImei(dsOperation.getImei());// 手机串号
			auditObj.setOperStatus(dsOperation.getOperStatus());// 操作状态
			// auditObj.setEmail("");//绑定邮箱
			auditObj.setBankCardNo(dsOperation.getCardNo());// 银行卡号
			auditObj.setBankCardType(dsOperation.getCardType());// 银行卡类型
			auditObj.setPhoneNo(dsOperation.getPhoneNo());// 绑定手机号
			auditObj.setIdNo(dsOperation.getIdNo()); // 身份证
			auditObj.setPcid(dsOperation.getMachineDactylogram());// 机器指纹
			auditObj.setSimId(dsOperation.getSim());// 手机SIM卡号
			auditObj.setBankPhone(dsOperation.getBankPhone());// 银行预留手机
			auditObj.setChargePhone(dsOperation.getRechargeMob());// 被充值手机
			auditObj.setMachineId(dsOperation.getMachineId());// 终端编号
			auditObj.setUserLogin(dsOperation.getUserLogin());// 登陆号
			auditObj.setBankIdno(dsOperation.getBankIdNo());// 银行签约身份证
			auditObj.setIdType(dsOperation.getIdType());// 证件类型
			// auditObj.setName(name);//用户姓名
			auditObj.setOS(dsOperation.getOs());// 操作系统
			auditObj.setBrowser(dsOperation.getBrowserType());
			auditObj.put("frms_create_time", dsOperation.getOperTime());
			// OID_PARTNER
			auditObj.setMerchantId(dsOperation.getColCustId());// 商户号
			auditObj.setMerchantName(dsOperation.getColCustName());// 商户名称
			Date dtRequest = dsOperation.getOperTime();
			if (dtRequest != null) {
				auditObj.setDtRequest(DateUtil.getDateOfSDF_YMDHMS((Date) dtRequest));// 操作时间
			}
		}
		return auditObj;
	}
}