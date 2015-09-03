/**
 * 弹窗组件<br>
 * 将需要显示的页面必须的view和controller配置在url里,该组件<br>
 * 就能自动加载相应js文件,其他组件需要在自己的controller里控制.
 */

// 调用common.js文件里的代码进行一些预处理操作
fixBugBeforeApp();
extjsConfig();
var portal = {};

Ext.application({
	requires : [ 'Ext.container.Viewport' ],
	name : 'portal',
	appFolder : 'portal',
	launch : function() {
		var me = this;
		var view = Ext.create('Ext.container.Viewport', {
			layout : 'fit',
			style : 'padding:0px',
			hideBorders : true
		});
		var map = me.paramsMap;
		// 必须参数的key
		var controllerTypeName = 'controllerName';
		var viewTypeName = 'viewName';
		// 必须参数的value
		var controllerName = map[controllerTypeName];
		var viewName = map[viewTypeName];
		// 判断参数里的必须项是否存在
		if (!Ext.isEmpty(controllerName) && !Ext.isEmpty(viewName)) {
			// 判断需要的controller是否已经加载
			if (Ext.ClassManager.isCreated(controllerName)) {
				view.add({
					xtype : viewName
				});
			} else {
				// 未加载的情况下就主动加载
				Ext.require(controllerName, function() {
					var controller = Ext.create(controllerName, {
						application : me,
						id : controllerName
					});
					me.controllers.add(controller);
					if (me._initialized) {
						controller.doInit(me);
					}
					view.add({
						xtype : viewName,
						paramsMap : map
					});
				});
			}
		} else if (Ext.isEmpty(controllerName)) {
			Ext.Msg.alert('提示信息', 'url里获取的参数缺失{' + controllerTypeName + '}',
					function() {
						window.close();
					});
		} else {
			Ext.Msg.alert('提示信息', 'url里获取的参数缺失{' + viewTypeName + '}',
					function() {
						window.close();
					});
		}
	},
	init : function() {
		var me = this;
		me.urlToMap();
		// 如果用户指定了窗口标题,需要重新设置
		var windowTitle = me.paramsMap.windowTitle;
		if (!Ext.isEmpty(windowTitle)) {
			document.title = windowTitle;
		}
		portal.loadController = function(name, fn) {
			var controller;
			var bol = false; // false表示该controller不在portal项目里
			var fullName = ''; // controller全限定名
			if (name.indexOf('.controller.') < 0) {
				fullName = me.getModuleClassName(name, 'controller');
				bol = true;
			}
			// 查询controller是否已经存在
			controller = me.controllers.get(name);
			if (!controller && fullName != name) {
				controller = me.controllers.get(fullName)
			}
			if (!controller) {
				if (bol) {
					controller = me.getController(name);
				} else {
					controller = Ext.create(name, {
						application : me,
						id : name
					});
					me.controllers.add(controller);
					if (me._initialized) {
						controller.doInit(me);
					}
				}
				if (fn)
					fn();
			}
			return controller;
		};
		me.callParent(arguments);
	},
	// 解析url,将其中的参数转为map
	urlToMap : function() {
		var me = this;
		var paramArr = window.location.href.split('?');
		var paramStr = '';
		var map = {};
		if (paramArr.length == 2) {
			paramStr = paramArr[1];
		} else {
			alert("url不正确,当前页面即将关闭!");
			window.close();
		}
		map = Ext.Object.fromQueryString(paramStr, true);
		me.paramsMap = map;
	}
});