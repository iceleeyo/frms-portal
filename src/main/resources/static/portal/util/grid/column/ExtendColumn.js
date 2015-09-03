/**
 * created by zqq
 *
 * 该组件扩展了EXTJS组件grid下的column!
 * 使用该组件不但易于扩展新功能而且能够即时对所有继承该组件的类生效,同时
 * 新功能都是可以设置默认开或关,即使继承的组件不想使用默认为开的新功能也
 * 只需设置一行代码就可以关闭!
 */
/**
 * 已扩展功能: <br>
 * 1.为display添加渲染功能,通过{renderType : String}配置! <br>
 * renderType可选值范围: <br>
 * store : 最实用的渲染,一般是将id转为name
 * money_bangsun :　适用于邦盛的金额渲染函数,一般是对原始数据除以1000并显示两位小数
 */
Ext.define('portal.util.grid.column.ExtendColumn', {
	extend : 'Ext.grid.column.Column',
	alias : ['widget.extendcolumn'],
	require : ['portal.util.data.RenderDataStore'],
	renderType : null,
	renderText : '未知状态',
	store : 'portal.util.data.RenderDataStore',// 渲染数据时需要的store,只能使用全限定路径名
	storeCode : 'CODE',// 待渲染数据在store里的key
	storeValue : 'VALUE',// 渲染后的值在store里的key
	queryMap : null,// 如果store需要参数可以指定值
	jsonData : false,// 需要使用jsonData时设为true
	multiSelect : false, // 待渲染的数据是否是数组
	initComponent : function() {
		var me = this;
		// 获取命名空间
		me.$namespace = Ext.app.getNamespace(me.__proto__.$className);
		me.callParent(arguments);
		if (!Ext.isEmpty(me.renderType)) {
			me.setRender();
		}
	},
	// 初始化store并加载数据
	initStore : function(fn) {
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
		}
		if (Ext.isFunction(fn)) {
			fn();
		}
	},
	// 获取store的引用,不存在就创建它
	getStore : function() {
		var me = this;
		var storeId, store;
		var name = me.store;
		if (typeof name != 'string') {
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
	// 配置column的渲染函数
	setRender : function() {
		var me = this;
		if (me.renderType == 'store') {
			me.initStore();
		}
		if (Ext.isEmpty(me.renderer) && !me.renderer) {
			return;
		}
		me.renderer = function(val) {
			if (!val || val === '') {
				return '';
			}
			var finded = null;
			switch (me.renderType) {
				case 'store' :				
					var store = me.getStore();
					if (!me.multiSelect) {
						var record = store.findRecord(me.storeCode, val, 0, false, false, true);
						if(record){
							finded = record.get(me.storeValue);
						}						
					} else {						
						var codes = Ext.JSON.decode(val), names = [];						
						for (var i = 0; i < codes.length; i++) {							
							record = store.findRecord(me.storeCode, codes[i], 0, false, false, true);
							names.push(record ? record.get(me.storeValue) : me.renderText + '<font color=red>(' + codes[i] + ')</font>');
						}
						return names.join();
					}					
					break;
				case 'money_bangsun' :
					var num = Number(val);
					if(num !== null){
						finded = Ext.util.Format.number(num / 1000, '0.00');
					}					
					break;
				default :
					break;
			}
			return finded != null ? finded : me.renderText + '<font color=red>(' + val + ')</font>';
		}
	}
});