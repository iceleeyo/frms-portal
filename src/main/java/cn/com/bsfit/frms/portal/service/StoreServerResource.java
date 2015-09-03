package cn.com.bsfit.frms.portal.service;

import cn.com.bsfit.frms.portal.base.util.ExtJSResponse;
import cn.com.bsfit.frms.portal.util.store.IStore;
import cn.com.bsfit.frms.portal.util.store.StoreConfigs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * combo调用store的Service
 *
 * @author hjp
 *
 * @since 1.0.0
 *
 */
@RestController
@RequestMapping("/store")
public class StoreServerResource {

	@Autowired
	private List<StoreConfigs> storeConfigs;
	public static Map<String, IStore> stores = new HashMap<String, IStore>();

	private StoreServerResource() {

	}

	@PostConstruct
	public void init() {
		if (storeConfigs == null || storeConfigs.isEmpty())
			return;
		for (StoreConfigs storeConfig : storeConfigs) {
			stores.putAll(storeConfig.getStoreConfigs());
		}
	}

	/**
	 * 根据类型查找combo的store
	 *
	 * @param type 类型
	 * @return 数据
	 */
	@RequestMapping(value = "/getRenderByType", method = { RequestMethod.GET, RequestMethod.POST })
	public ExtJSResponse getRenderByType(String type) {
		IStore store = stores.get(type);
		if (store != null) {
			List<Map<String, Object>> result = store.call();
			return ExtJSResponse.successRes4Find(result, result.size());
		}
		return ExtJSResponse.errorRes("类型不存在!");
	}
}