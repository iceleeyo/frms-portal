package cn.com.bsfit.frms.portal.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.com.bsfit.frms.portal.base.mapper.ResourcesMapper;
import cn.com.bsfit.frms.portal.base.pojo.Resources;
import cn.com.bsfit.frms.portal.base.pojo.TabView;
import cn.com.bsfit.frms.portal.base.util.DefaultExtraTabsStore;
import cn.com.bsfit.frms.portal.base.util.ExtJSResponse;
import cn.com.bsfit.frms.portal.base.util.SysContent;

/**
 * Ext Controller和View配置
 * 
 * @author hjp
 * 
 * @since 1.0.0
 *
 */
@RestController
@RequestMapping("/tabView")
public class TabViewServerResource {

	@Autowired
	private ResourcesMapper resourcesMapper;
	@Autowired
	private List<DefaultExtraTabsStore> extraTabs;
	private Logger logger = LoggerFactory.getLogger(TabViewServerResource.class);
	
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public ExtJSResponse findTabViews() {
		List<TabView> views = new ArrayList<TabView>();
		List<Resources> resourcesList = resourcesMapper.selectByUsersId(SysContent.getUser().getId(), null);
		Map<String, Object> resourcesMap = listToMap(resourcesList);
		
		if (extraTabs != null && extraTabs.size() > 0) {
			for (DefaultExtraTabsStore store : extraTabs) {
				List<TabView> tabViewList = store.getTabViews();
				for (TabView tabView : tabViewList) {
					// 我的账号不进行限权
					if(tabView.getXtype().equals("accountView")) {
						views.add(tabView);
					}
					if (resourcesMap.get(tabView.getXtype()) != null) {
						Object object = resourcesMap.get(tabView.getXtype());
						if(object instanceof Integer) {
							tabView.put("openType", getOpenType((Integer) object));
						}
						views.add(tabView);
					} else {
						logger.info(tabView.getXtype() + ":" + tabView.getControllerName());
					}
				}
			}
		}
		logger.info("function findTabViews() views.size():" + views.size());
		ExtJSResponse res = new ExtJSResponse(true);
		res.setData(views);
		return res;
	}

	/**
	 * List转为Map
	 * 
	 * @param objectList
	 * @return
	 */
	private Map<String, Object> listToMap(List<?> objectList) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (objectList != null && objectList.size() != 0) {
			for (Object object : objectList) {
				if (object instanceof Resources) {
					Resources resources = (Resources) object;
					map.put(subResourceUrl(resources.getResourceUrl()), resources.getId());
				} else if (object instanceof TabView) {
					TabView tabView = (TabView) object;
					tabView.put(tabView.getXtype(), tabView.getControllerName());
				} else {
					logger.info("this system does not know the object:" + object);
				}
			}
		}
		return map;
	}

	/**
	 * 截取字符串
	 * 
	 * @param resourceUrl
	 * @return
	 */
	private String subResourceUrl(String resourceUrl) {
		if (resourceUrl != null && resourceUrl.contains("?")) {
			return resourceUrl.substring(0, resourceUrl.indexOf("?"));
		}
		return resourceUrl;
	}
	
	/**
	 * 根据资源ID获取菜单打开方式
	 * 
	 * @param resourcesId
	 * @return
	 */
	private String getOpenType(final Integer resourcesId) {
		String result = "";
		Resources resources = resourcesMapper.selectByPrimaryKey(resourcesId);
		if(resources != null) {
			result = resources.getResourceDesc();
		}
		return result;
	}
}