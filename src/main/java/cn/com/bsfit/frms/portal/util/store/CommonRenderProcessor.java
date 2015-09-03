package cn.com.bsfit.frms.portal.util.store;

import cn.com.bsfit.frms.portal.service.StoreServerResource;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 渲染用的工具类
 * Created by 崇建 on 15-6-23.
 */
public class CommonRenderProcessor {

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private Map<String, Future<Map<Object, Object>>> futures = new HashMap<String, Future<Map<Object, Object>>>();

	/**
	 * 根据各个模块中 ***StoresConfig.java中配置的信息，通过args中声明的选项，多线程去查询相关的渲染信息
	 *
	 * @param storeServerResource StoreServerResource类的实例，从Spring容器中获取后传入
	 * @param args                在各StoreConfig中配置的key。
	 *                            例如，我在PortalStoresConfig中配置了"BIZ_CODE"和"RISK_TYPE"，此处传入"BIZ_CODE","RISK_TYPE",
	 *                            将会使用PortalStoresConfig中配置好的信息，多线程去查询相关的渲染信息，
	 *                            并将其以key,value的形式组装成Map，方便渲染
	 */
	public CommonRenderProcessor(final StoreServerResource storeServerResource, String... args) {
		// 遍历各个模块中 ***StoresConfig.java中配置的信息，将其组成以String为key，多线程的执行任务为value的map
				Map<String, Callable<Map<Object, Object>>> renderTaskMap = new HashMap<String, Callable<Map<Object, Object>>>();
		if (StoreServerResource.stores != null && !StoreServerResource.stores.isEmpty()) {
			for (String key : StoreServerResource.stores.keySet()) {
				renderTaskMap.put(key, new StoreRenderTask(storeServerResource, key));
			}
		}
		// 根据args中声明的选项，从renderTaskMap中找出对应的多线程的执行任务，并新建一个线程池，去执行这些多线程的执行任务，
		// 多线程执行任务的结果保存在futures中
		ExecutorService exer = Executors.newCachedThreadPool();
		for (String arg : args) {
			if (renderTaskMap.containsKey(arg)) {
				futures.put(arg, exer.submit(renderTaskMap.get(arg)));
			}
		}
		exer.shutdown();
	}

	@SuppressWarnings("rawtypes")
	public Map getMap(String type) {
		try {
			return futures.get(type).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return new HashMap();
	}

	public Object render(String type, Object code) {
		return render(type, code, true);
	}

	@SuppressWarnings("rawtypes")
	public Object render(String type, Object code, boolean changeCodeToString) {
		if (code == null) {
			return null;
		}
		Map map = getMap(type);
		if (map == null || !map.containsKey(changeCodeToString ? code.toString() : code)) {
			return "未知状态(" + code.toString() + ")";
		}
		return map.get(changeCodeToString ? code.toString() : code);
	}

	public static String renderDate(Date date) {
		if (date == null) {
			return null;
		}
		return sdf.format(date);
	}

	public static String renderMoney(Long money) {
		if (money == null) {
			return null;
		}
		return String.valueOf(money / 1000) + "元";
	}
}
