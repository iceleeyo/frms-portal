Ext.define('portal.store.systemconfig.SystemConfigStore', {
	extend : 'Ext.data.Store',
	autoLoad : false,
	fields : [ 'id', 'type', 'typeName', 'code', 'value', 'remark', 'modifer', {
				name : 'enabled',
				type : 'short'
			}, {
				name : 'orderBy',
				type : 'int'
			}, {
				name : 'createTime',
				type : 'date',
				convert : function(value, record) {
					if (!value || value == null || value == 0)
						return null;
					return new Date(value);
				}
			}, {
				name : 'updateTime',
				type : 'date',
				convert : function(value, record) {
					if (!value || value == null || value == 0)
						return null;
					return new Date(value);
				}
			} ],
	proxy : {
		type : 'ajax',
		url : 'systemConfig/list',
		api : {
			update : 'systemConfig/add',
			read : 'systemConfig/list',
			create : 'systemConfig/edit',
			destroy : 'systemConfig/delete'
		},
		reader : {
			type : 'json',
			root : 'data',
			successProperty : 'success'
		}
	}
});