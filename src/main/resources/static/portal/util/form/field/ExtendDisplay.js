/**
 * created by zqq
 * 
 * 该组件扩展了EXTJS组件displayfield!
 * 使用该组件不但易于扩展新功能而且能够即时对所有继承该组件的类生效,同时
 * 新功能都是可以设置默认开或关,即使继承的组件不想使用默认为开的新功能也
 * 只需设置一行代码就可以关闭!
 */
/**
 * 已扩展功能: <br>
 * 1.为display添加渲染功能,通过{renderType : String}配置! <br>
 * renderType可选值范围: <br>
 * store : 启用加载store的数据来渲染,一般就是code和value
 */
Ext.define('portal.util.form.field.ExtendDisplay', {
	extend : 'Ext.form.field.Display',
	alias : 'widget.extenddisplayfield',
	renderType : null,
	renderText : '未知状态',
	store : 'portal.util.data.RenderDataStore',
	storeCode : 'CODE',// 待渲染数据在store里的key
	storeValue : 'VALUE',// 渲染后的值在store里的key
	queryMap : null,// store的查询参数
	jsonData : false,// 需要使用jsonData时设为true
	multiSelect : false, // 待渲染的数据是否是数组
	initComponent : function() {
		var me = this;
		// 获取命名空间
		me.$namespace = Ext.app.getNamespace(me.__proto__.$className);
		if (me.renderType == 'store') {
			me.initStore();
		}
		me.callParent(arguments);
	},
	// 初始化store并加载数据
	initStore : function() {
		var me = this;
		var queryMap = me.queryMap;
		var store = me.store = me.getStore();
		// 首先判断store是否已经加载
		if (store.getCount() < 1) {
			// 同步加载,保证渲染时数据已加载
			var request = {
				url : store.proxy.url,
				method : store.proxy.actionMethods['read'],
				headers : store.proxy.headers,
				async : false, // 同步加载
				success : function(response) {
					var obj = Ext.decode(response.responseText);
					if (obj.success) {
						store.loadData(obj.data);
					}
					me.setRender(store);
				}
			};
			if (queryMap) {
				if (me.jsonData) {
					Ext.apply(request, {
						jsonData : queryMap
					});
				} else {
					Ext.apply(request, {
						params : queryMap
					});
				}
			}
			Ext.Ajax.request(request);
		} else {
			me.setRender(store);
		}
	},
	// 获取store的引用,不存在就创建它
	getStore : function() {
		var me = this;
		var storeId, store;
		var name = me.store;
		if(typeof name != 'string'){
			return name;
		}
		var queryMap = me.queryMap;
		storeId = (name.indexOf('@') == -1) ? name : name.split('@')[0];
		// 一些带参数的store可以通过附加参数里的type类型来区分
		// 所以如果参数里没有type将不能区分store
		if (queryMap && queryMap.type) {
			storeId += '_' + queryMap.type;
		}
		store = Ext.StoreManager.get(storeId);
		if (!store) {
			name = Ext.app.Controller.getFullName(name, 'store', me.$namespace);
			if (name) {
				try {
					store = Ext.create(name.absoluteName, {
						storeId : storeId
					});
				} catch (e) {
					store = Ext.create(name.shortName, {
						storeId : storeId
					});
				}
			}
		}
		return store;
	},
	// 配置column的渲染函数,在renderer未配置下使用默认配置
	setRender : function(store) {
		var me = this;
		if(!Ext.isEmpty(me.renderer)){
			return;
		}
		me.renderer = function(val) {
			if (!val || val === '') {
				return '';
			}

			if (!me.multiSelect) {
				var record = store.findRecord(me.storeCode, val, 0, false, false, true);
				return record ? record.get(me.storeValue) : me.renderText + '<font color=red>(' + val + ')</font>';
			} else {
				var codes = Ext.JSON.decode(val), names = [];

				for (var i = 0; i < codes.length; i++) {
					record = store.findRecord(me.storeCode, codes[i], 0, false, false, true);
					names.push(record ? record.get(me.storeValue) : me.renderText + '<font color=red>(' + codes[i] + ')</font>');
				}
				return names.join();
			}
		}
	}
});