package cn.com.bsfit.frms.portal.util.store;

import cn.com.bsfit.frms.portal.base.util.ExtJSResponse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 使用反射直接调用方法来获取的store Created by 崇建 on 15-3-12.
 */
public class ReflectStore implements IStore {

	private Object object;
	private String methodName;
	private Class<?>[] parameterTypes;
	private Object[] parameters;
	private String codeField;
	private String valueField;

	/**
	 * 通过反射调用object对象的methodName方法，得到一个原始pojo的集合。
	 * 再根据pojo的code字段名和value字段名，组装成一个合适的list
	 *
	 * @param object
	 *            需要调用方法的对象
	 * @param methodName
	 *            方法名
	 * @param codeField
	 *            code字段名
	 * @param valueField
	 *            value字段名
	 */
	public ReflectStore(Object object, String methodName, String codeField,
						String valueField) {
		this.object = object;
		this.methodName = methodName;
		this.codeField = codeField;
		this.valueField = valueField;
	}

	/**
	 * 通过反射调用object对象的methodName方法，得到一个原始pojo的集合。
	 * 再根据pojo的code字段名和value字段名，组装成一个合适的list
	 *
	 * @param object
	 *            需要调用方法的对象
	 * @param methodName
	 *            方法名
	 * @param parameterTypes
	 *            方法的参数类型
	 * @param parameters
	 *            方法的参数名
	 * @param codeField
	 *            code字段名
	 * @param valueField
	 *            value字段名
	 */
	public ReflectStore(Object object, String methodName,
						Class<?>[] parameterTypes, Object[] parameters, String codeField,
						String valueField) {
		this.object = object;
		this.methodName = methodName;
		this.parameterTypes = parameterTypes;
		this.parameters = parameters;
		this.codeField = codeField;
		this.valueField = valueField;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map<String, Object>> call() {
		try {
			// 通过反射调用object对象的methodName方法，得到一个原始对象的集合
			Object originalObject = object.getClass().getMethod(methodName, parameterTypes).invoke(object, parameters);
			// 如果方法被调用后返回的是ExtJSResponse类型，则调用getData方法获取里面的list
			List<Object> originalList = (List<Object>) ((originalObject instanceof ExtJSResponse) ? ((ExtJSResponse) originalObject).getData() : originalObject);
			if (originalList == null || originalList.isEmpty()) {
				return new ArrayList<Map<String, Object>>();
			}

			// ======以下将原始POJO转置成统一格式，方便前端的同一个store获取======
			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
			if (originalList.get(0) instanceof Map) { // 如果原始对象是Map类型的
				// 去除多余字段，且使得字段的KEY都统一成"CODE"和"VALUE"
				for (Object obj : originalList) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("CODE", ((Map) obj).get(codeField));
					map.put("VALUE", ((Map) obj).get(valueField));
					resultList.add(map);
				}
			} else { // 如果原始对象是POJO
				Method codeGetterMethod = getterMethod(originalList.get(0), codeField);// pojo的code字段的getter方法
				Method valueGetterMethod = getterMethod(originalList.get(0), valueField);// POJO的value字段的getter方法
				// 去除多余字段，且使得字段的KEY都统一成"CODE"和"VALUE"
				for (Object obj : originalList) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("CODE", codeGetterMethod.invoke(obj));
					map.put("VALUE", valueGetterMethod.invoke(obj));
					resultList.add(map);
				}
			}
			return resultList;
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return new ArrayList<Map<String, Object>>();
	}

	private Method getterMethod(Object obj, String property) throws NoSuchMethodException {
		char[] chars = property.toCharArray();
		chars[0] -= 32;
		String methodName = "get" + String.valueOf(chars);
		return obj.getClass().getMethod(methodName);
	}
}
