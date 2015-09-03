package cn.com.bsfit.frms.portal.util.store;

import java.util.List;
import java.util.Map;

/**
 * 建议combo的store统一配置方便管理， store的获取方式有以下几种： 
 * 1.直接在rams的数据库中查询：使用实现类DatabaseStore
 * 2.从portal提供的SystemConfigUtil中取：使用实现类SystemConfigStore
 * 3.使用反射直接调用方法来获取：使用实现类ReflectStore
 *
 * Created by 崇建 on 15-3-11.
 */
public interface IStore {
	List<Map<String, Object>> call();
}
