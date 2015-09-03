/**
 * created by zqq
 * 
 * 该组件扩展了EXTJS组件combo!
 * 使用该组件不但易于扩展新功能而且能够即时对所有继承该组件的类生效,同时
 * 新功能都是可以设置默认开或关,即使继承的组件不想使用默认为开的新功能也
 * 只需设置一行代码就可以关闭!
 */
/**
 * 已扩展功能: <br>
 * 1.allowBlank=false时自动显示红色星号,默认开启,通过{showRedStar : Boolean}配置 <br>
 * 2.为combo增加一个筛选所有数据的选项,默认开启,通过{isHasNoLimit : Boolean}配置 <br>
 * 3.默认为combo指定一个通用的store,基本不用再重新指定store,同时默认指定了显示的值和实际的值对应的字段 <br>
 * 4.添加了指定store里的值作为默认值的功能,默认开启,通过{hasDefaultValue : Boolean}配置, <br>
 * 同时可以通过{indexOfStore : Integer}配置默认值的索引或者{defaultValue : String}直接 <br>
 * 赋值(优先级最高) <br>
 * 5.通过其他组件为combo赋值时,可能当前combo指定的store里的数据还未加载,此时可能导致数据未渲染. <br>
 * 通过配置{autoLoadStore : Boolean}来设置是combo实例化后直接加载store还是在combo实际被点击时 <br>
 */
Ext.define('portal.util.form.field.ExtendComboBox', {
	extend : 'Ext.form.field.ComboBox',
	alias : 'widget.extendcombo',
	require : ['portal.util.data.RenderDataStore'],
	showRedStar : true,// 默认开启必填项添加红色星号
	isHasNoLimit : true,// 默认开启自动为combo添加筛选所有数据的选项
	noLimitCode : '',// 自定义参数,能筛选所有数据的选项对应的code
	noLimitValue : '全部',// 自定义参数,能筛选所有数据的选项对应的value
	store : 'portal.util.data.RenderDataStore',
	queryMap : null,// store的查询参数
	displayField : 'VALUE',
	valueField : 'CODE',
	hasDefaultValue : true,// 默认开启自动将store的指定数据设为combo默认值
	indexOfStore : 0,// 要被设为默认值的数据在store里的索引
	defaultValue : '',// 如果默认值不为空,其优先级高于索引,会覆盖索引指定的值
	autoLoadStore :　false,// 默认按需加载store,如果为true,在combo实例化之后立刻加载数据
	emptyText : '请选择',
	initComponent : function() {
		var me = this;
		// 获取命名空间
		if('__proto__' in {} ) {
			me.$namespace = Ext.app.getNamespace(me.__proto__.$className);
		} else {
			// TODO IE8
			me.$namespace = 'portal';
		}
		// 在label后面加上红色星号代表必填项
		if (me.showRedStar == true && me.allowBlank == false) {
			if (me.fieldLabel) {
				me.fieldLabel += '<b style="color:red">*</b>';
			}
		}
		// 自己主动实例化store,因为api的实例化部分功能不满足要求,比如store如果给的是字符串就只支持实例化一个框架的命名空间
		var store = me.store = me.getUniqueStore();
		me.callParent(arguments);
		// 给指定的store附加操作
		if (store != null) {
			if (me.queryMap != null) {
				store.getProxy().extraParams = me.queryMap;
			}				
			if (me.isHasNoLimit) {
				me.setNoLimit();
			}			
			if (me.hasDefaultValue) {
				me.setDefaultValue();
			}		
			if(me.autoLoadStore){
				if(me.queryMode == 'local'){
					me.queryMode = 'remote';
					// 强制重新查询
					delete me.lastQuery;
					me.loadStore();
					me.queryMode = 'local';
				} else {
					me.loadStore();
				}
			}
		}
	},
	// 获取store的引用,不存在就创建它
	getUniqueStore : function() {
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
	// 为combo添加一个额外的选项
	setNoLimit : function() {
		var me = this;
		var store = me.getStore();
		var callback = function() {
			if (store.find(me.displayField, me.noLimitValue) == -1) {
				var rec = {};
				rec[me.displayField] = me.noLimitValue;
				rec[me.valueField] = me.noLimitCode;
				store.insert(0, rec);
			}
		}
		if (me.queryMode != 'local' && !me.storeIsLoaded()) {
			me.mon(store, 'load', callback);
		} else {	
			callback();
		}
	},
	// 为combo设置默认值,可以通过默认值在store里的索引来设置或直接赋值
	setDefaultValue : function(value, fn) {
		var me = this;
		var store = me.getStore();
		var defaultValue = value;		
		var callback = function() {
			if(Ext.isEmpty(defaultValue)){
				defaultValue = me.defaultValue;
			}
			// 不指定有效的value值就代表使用索引
			if (Ext.isEmpty(defaultValue)) {
				var record = store.getAt(me.indexOfStore);
				if(record){
					defaultValue = record.get(me.valueField);	
				}else{
					defaultValue = '';
				}					
			}
			me.setValue(defaultValue);
			me.resetOriginalValue();		
			// 如果下拉框展开需要关掉
			if (me.isExpanded) {
				me.collapse();
			}
			// 执行请求的回调函数
			if (!Ext.isEmpty(fn) && Ext.isFunction(fn)) {
				fn();
			}
		}
		if (me.queryMode != 'local' && !me.storeIsLoaded()) {
			me.mon(store, 'load', callback);		
			me.loadStore();			
		} else {	
			callback();
		}
	},
	// 加载store,保持跟EXTJS的API的判断条件一样,同时该方法会展开下拉框
	loadStore : function() {
		var me = this;
		if (me.triggerAction === 'all') {
			me.doQuery(me.allQuery, true);
		} else if (me.triggerAction === 'last') {
			me.doQuery(me.lastQuery, true);
		} else {
			me.doQuery(me.getRawValue(), false, true);
		}
	},
	// 判断store是否已经加载,保持跟EXTJS的API的判断条件一样
	storeIsLoaded : function() {
		var me = this;
		var query = me.allQuery || me.lastQuery || me.getRawValue() || '';
		return (me.queryCaching && query === me.lastQuery);
	}
});