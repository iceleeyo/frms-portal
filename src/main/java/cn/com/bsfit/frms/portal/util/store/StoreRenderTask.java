package cn.com.bsfit.frms.portal.util.store;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import cn.com.bsfit.frms.portal.base.util.ExtJSResponse;
import cn.com.bsfit.frms.portal.service.StoreServerResource;

/**
 * 通过调用StoreServerResource的getRenderByType方法，
 * 多线程获取渲染用的映射
 * Created by 崇建 on 15-6-23.
 *
 */
public class StoreRenderTask implements Callable<Map<Object, Object>>{

	private String type;
	private StoreServerResource storeServerResource;

	public StoreRenderTask(StoreServerResource storeServerResource, String type) {
		this.storeServerResource = storeServerResource;
		this.type = type;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<Object, Object> call() throws Exception {
		Map<Object, Object> result = new HashMap<Object, Object>();
		ExtJSResponse extJSResponse = storeServerResource.getRenderByType(type);
		if (extJSResponse.isSuccess()) {
			List<Map<String, Object>> objs = (List<Map<String, Object>>) extJSResponse.getData();
			for (Map<String, Object> obj : objs) {
				result.put(obj.get("CODE"), obj.get("VALUE"));
			}
		}
		return result;
	}
}
