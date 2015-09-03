package cn.com.bsfit.frms.portal.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * fastjson和POJO之间转换
 * 
 * @author hjp
 * 
 * @since 1.0.0
 *
 */
public class FastJsonUtil {
	
	private final static Logger logger = LoggerFactory.getLogger(FastJsonUtil.class);
	
	/**
	 * 序列化
	 * 
	 * @param obj
	 * @return
	 */
	public static String toJsonString(Object obj) {
		String text = JSON.toJSONString(obj);
		logger.info("toJsonString()方法:text=" + text);
		return text;
	}
	
	/**
	 * 反序列化为JSON对象
	 * 
	 * @param text
	 * @return
	 */
	public static JSONObject parseJsonObject(String text) {
		JSONObject json = JSON.parseObject(text);
		logger.info("parseJsonObject()方法:json==" + json);
		return json;
	}
	
	/**
	 * 反序列化为POJO
	 * 
	 * @param text
	 * @return
	 */
	public static Object parsePojoObject(String text, Object object) {
		return JSON.parseObject(text, object.getClass());
	}
	
	/**
	 * 将POJO转化为JSON对象
	 * 
	 * @param obj
	 * @return
	 */
	public static JSONObject pojoToJson(Object obj) {
		JSONObject jsonObject = (JSONObject) JSON.toJSON(obj);
		logger.info("pojoToJson()方法:jsonObject==" + jsonObject);
		return jsonObject;
	}
	
	/**
	 * 全序列化 直接把POJO序列化为JSON文本之后，能够按照原来的类型反序列化回来。
	 * 支持全序列化，需要打开SerializerFeature.WriteClassName特性
	 * 
	 * @param obj
	 * @return
	 */
	public static Object parseJSONAndPojoEachother(Object obj) {
		SerializerFeature[] featureArr = { SerializerFeature.WriteClassName };
		String text = JSON.toJSONString(obj, featureArr);
		logger.info("parseJSONAndPojoEachother()方法:text==" + text);
		return JSON.parse(text);
	}
}
