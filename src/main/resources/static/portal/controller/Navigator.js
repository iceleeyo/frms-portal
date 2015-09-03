Ext.require('Ext.window.MessageBox');
var fnCreateTab;
Ext.require('portal.store.util.MenuInfoModel');
Ext.define('portal.controller.Navigator', {
	extend : 'Ext.app.Controller',
	views : [ 'Navigator' ],
	refs : [ {
		ref : 'tabs',
		selector : 'tabcontroller'
	} ],
	stores : [ 'util.TreeMenuStore', 'util.ExtraTabsViewStore' ],
	init : function() {
		this.control({
			'itemrenderer' : {
				itemclick : this.onItemClicked
			},
			'navigator' : {
				beforerender : this.beforerender,
				afterrender : this.afterrender,
				collapse : function(nav) {
					nav.placeholder.getEl().removeAllListeners();
					nav.placeholder.getEl().on('mouseenter', function() {
						nav.floatCollapsedPanel();
					});
					nav.placeholder.getEl().on('dblclick', function() {
						nav.expand();
					});
				}
			}
		});
	},

	beforerender : function(tab, opts) {
		var nodes = [];
		Ext.Ajax.request({
			url : 'login/findMenu',
			method : 'GET',
			async : false,
			success : function(response) {
				var obj = Ext.JSON.decode(response.responseText);
				if (obj.success) {
					nodes = obj.data;
				}
			}
		});
		Ext.Array.each(nodes, function(node) {
			var treeStore = Ext.create('Ext.data.TreeStore', {
				model : 'portal.store.util.MenuInfoModel',
				proxy : {
					type : 'memory'
				},
				root : {
					expanded : true,
					children : node.children
				}
			});
			tab.add({
				xtype : 'itemrenderer',
				title : node.text,
				useArrows : false,
				lines : false,
				border : false,
				iconCls : node.iconCls,
				store : treeStore
			});
	    });
	},

	afterrender : function(nav) {
//		var task = new Ext.util.DelayedTask(function() {
//			// nav.collapse();
//		});
//		task.delay(500);
	},

	onItemClicked : function(view, rec, item, index, e, eOpts) {
        var me = this;
		var extraTabsViewStore = this.getStore('util.ExtraTabsViewStore');
		var text = rec.data.text;
		var id = rec.data.id;
		var qtitle = rec.data.qtitle;
		var splitQtitle = qtitle.split('?')[0];
		if(Ext.isEmpty(splitQtitle)) {
			return;
		}
        var callback = function() {
            var tempRecord = extraTabsViewStore.findRecord('xtype', splitQtitle);
            // TODO:菜单打开方式
            if(tempRecord && tempRecord.get('openType') == '1'){
                var obj = {};
                obj.windowTitle = rec.data.text;
                openWindow(tempRecord.get('controllerName'), tempRecord.get('xtype'), obj, 'query' + rec.data.qtitle);
                return ;
            }
            if (tempRecord && tempRecord.get('navParam')) {
                Ext.History.add(rec.data.qtitle);
            } else {
                var controllerName = extraTabsViewStore.findRecord('xtype', splitQtitle).data.controllerName;
                g_tabs = me.getTabs();
                portal.loadController(controllerName);
                var tab = g_tabs.getComponent(text);
                if (!tab) {
                    tab = g_tabs.add(fnCreateTab(id, text, rec.data.iconCls, rec.data.qtitle));
                }
                g_tabs.setActiveTab(tab);
            }
        }
        if(extraTabsViewStore.getCount() < 1){
            extraTabsViewStore.load({
                callback : callback
            });
        } else {
            callback();
        }
	}
});