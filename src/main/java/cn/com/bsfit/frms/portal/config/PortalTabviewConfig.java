package cn.com.bsfit.frms.portal.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;

import cn.com.bsfit.frms.portal.base.pojo.TabView;
import cn.com.bsfit.frms.portal.base.util.DefaultExtraTabsStore;

/**
 * 配置portal基础Controller
 * 
 * @author hjp
 * 
 * @since 1.0.0
 *
 */
@Configuration
public class PortalTabviewConfig implements DefaultExtraTabsStore {

	public List<TabView> getTabViews() {
		List<TabView> tabViewList = new ArrayList<TabView>();
		// 首页(风险大盘)
		tabViewList.add(new TabView("Main", "mainview", true, null));
		// 我的账号
		tabViewList.add(new TabView("portal.controller.account.AccountController", "accountView", true, null));
		// 用户
		tabViewList.add(new TabView("portal.controller.users.UsersController", "usersView", true, null));
		// 角色
		tabViewList.add(new TabView("portal.controller.roles.RolesController", "rolesView", true, null));
		// 资源
		tabViewList.add(new TabView("portal.controller.resources.ResourcesController", "resourcesView", true, null));
		// 风险等级
		tabViewList.add(new TabView("portal.controller.risklevel.RiskLevelController", "riskLevelView", true, false));
		// 维度信息
		tabViewList.add(new TabView("portal.controller.dimension.DimensionController", "dimensionView", true, null));
		// 系统配置
		tabViewList.add(new TabView("portal.controller.systemconfig.SystemConfigController", "systemConfigView", true, null));
		// 国际地区代码
		tabViewList.add(new TabView("portal.controller.countries.CountriesController", "countriesView", true, null));
		// 货币代码
		tabViewList.add(new TabView("portal.controller.currency.CurrencyController", "currencyView", true, null));
		// 行政区划分代码
		tabViewList.add(new TabView("portal.controller.regierungsbezirk.RegierungsbezirkController", "regierungsbezirkView", true, null));
		return tabViewList;
	}
}