// 调用common.js文件里的代码进行一些预处理操作
fixBugBeforeApp();
extjsConfig();
// portal主页动态样式
portalConfig();
var portal = {};
Ext.application({
	requires : [ 'Ext.container.Viewport' ],
	name : 'portal',
	appFolder : 'portal',
	launch : function() {
		Ext.create('Ext.container.Viewport', {
			layout : 'fit',
			style : 'padding:0px',
			hideBorders : true,
			items : [ {
				xtype : 'container',
				layout : 'border',
				cls : 'common-container',
				items : [ {
					xtype : 'topbar',
					height : 50,
					region : 'north'
				}, {
					xtype : 'navigator',
					region : 'west',
					collapsible : true,
					split : true,
					width : 240
				}, {
					xtype : 'tabcontroller',
					region : 'center'
				} ]
			} ]
		});
	},
	init : function() {
		this.callParent(arguments);
		var me = this;
		portal.loadController = function(name, fn) {
			var controller;
			var bol = false;
			var fullName = '';
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
				if (fn) {
					fn();
				}
			}
			return controller;
		};
		portal.setCookie = function(key, value) {
			var cTime = Date.now();
			Ext.util.Cookies.set(key, value, new Date(cTime + 365 * 24 * 60 * 60 * 1000));
		};
		portal.delay = function(fn, latency) {
			var task = new Ext.util.DelayedTask(fn);
			task.delay(latency);
		};
		portal.findExactRecord = function(store, key, value) {
			var index = store.findExact(key, value);
			if (index == -1)
				return null;
			else
				return store.getAt(index);
		};
		portal.getTextContent = function(view) {
			return Ext.isIE ? view.innerText : view.textContent;
		}
	},
	controllers : [ 'TopBar', 'Navigator', 'TabController' ]
});