Ext.require('Ext.window.MessageBox');
var g_tabs;
var extraTabsStore;
Ext.define('portal.controller.TabController', {
	extend : 'Ext.app.Controller',
	views : [ 'TabController' ],
	refs : [ {
		ref : 'tabs',
		selector : 'tabcontroller'
	} ],
	stores : [ 'util.ExtraTabsViewStore' ],
	init : function() {
		this.control({
			'tabcontroller' : {
				tabchange : this.onTabChanged,
				afterrender : this.onTabControllerRendered
			}
		});
	},

	onTabChanged : function(tabPanel, newCard, oldCard, eOpts) {
		var tabs = [], oldToken, newToken;
		newToken = newCard.xtype;
		if (newCard.kvs) {
			newToken += '?';
		    Ext.Array.each(newCard.kvs, function(key) {
		    	newToken += key + '&';
		    });
		    newToken = newToken.substring(0, newToken.length - 1);
		}
		oldToken = Ext.History.getToken();
		if (newToken != oldToken) {
			Ext.History.add(newToken);
		}
	},

	onTabControllerRendered : function(tabs) {
		Ext.History.init();
		fnCreateTab = this.createTab;
		g_tabs = tabs;
		extraTabsStore = this.getStore('util.ExtraTabsViewStore');
        Ext.History.on('change', this.onTokenChanged);
		tabs.on('remove', function(tab) {
			if (tab.items.items.length == 0) {
				Ext.History.add('#');
			}
		});
		var curToken = Ext.History.getToken();
        // 如果没有指定要打开的页面,默认就打开首页
		var callback = function() {
			var home = 'mainview';
			var rec = extraTabsStore.findRecord('xtype', home);
            if(rec){
                Ext.History.add(home);
            }
		};
		if (Ext.isEmpty(curToken)) {
            if (extraTabsStore.getCount() < 1) {
                extraTabsStore.load({
                    callback : callback
                });
			} else {
				callback();
			}
		} else {
            this.onTokenChanged(curToken);
        }
	},

	createTab : function(id, text, iconCls, tab) {
		return {
			xtype : tab,
			id : 'tab_' + id,
			title : text,
			itemId : text,
			closable : true,
			iconCls : iconCls
		};
	},

	onTokenChanged : function(token) {
		var parts, length, tabType, activeTab, kvs;
		if (token) {
			parts = token.split('?');
			length = parts.length;
			if (length > 1) {
				kvs = parts[1].split('&');
			}
		}
		tabType = parts ? parts[0] : 'newrealtimetab';
        activeTabs = Ext.ComponentQuery.query(tabType);
        if (extraTabsStore.getCount() < 1) {
			Ext.Ajax.request({
				url : 'tabView/list',
				method : 'GET',
				async : false,
				success : function(resp) {
					var record = Ext.JSON.decode(resp.responseText);
					if (!record.success) {
						Ext.MessageBox.alert('运行错误', record.error);
					} else {
						extraTabsStore.loadData(record.data);
					}
				},
				failure : function(resp) {
					var result = Ext.JSON.decode(resp.responseText);
					if (!result.success) {
						Ext.Msg.alert("提示", result.error);
					} else {
						Ext.MessageBox.alert('运行错误', resp.responseText);
					}
				}
			});
		}
		var rec = extraTabsStore.findRecord('xtype', tabType);
        if (rec) {
			portal.loadController(rec.data.controllerName);
			// 对于参数敏感的tab，因为存在多个同时打开的tab页，需要根据相关参数进行详细查询。
			var paramSensitive = rec.get('paramSensitive');
			if (paramSensitive === true) {
				for (var i = 0; i < activeTabs.length; ++i) {
					if (kvs.toString() == activeTabs[i].kvs.toString()) {
						activeTab = activeTabs[i];
						break;
					}
				}
			} else {
				activeTab = activeTabs[0];
			}
		} else {
			activeTab = activeTabs[0];
		}
		if (!activeTab) {
			var navs = Ext.ComponentQuery.query('itemrenderer');
			var nav, i;
			for (i = 0; i < navs.length; ++i) {
				nav = navs[i].getRootNode().findChild('qtitle', tabType, true);
				if (nav) {
					break;
				}
			}
			if (nav) {
				var text = nav.data.text;
				var id = nav.data.id;
				activeTab = g_tabs.add(fnCreateTab(id, text, nav.data.iconCls, nav.data.qtitle));
			} else {
				var rec = extraTabsStore.findRecord('xtype', tabType);
				if (rec) {
					var xtype = rec.get('xtype');
					activeTab = g_tabs.add({
						xtype : xtype,
						closable : true
					});
				}
			}
		}
		if (activeTab && typeof activeTab.setKvs === 'function') {
			if (activeTab.rendered) {
				activeTab.setKvs(kvs);
			} else {
				activeTab.on('afterrender', function(tab) {
					activeTab.setKvs(kvs);
				});
			}
		}
		if (activeTab) {
			g_tabs.setActiveTab(activeTab);
		}
	}
});