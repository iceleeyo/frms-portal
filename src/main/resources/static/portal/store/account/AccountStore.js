Ext.define('portal.store.account.AccountStore', {
	extend : 'Ext.data.Store',
	autoLoad : false,
	autoDestroy : true,
	fields : [ 'id', 'userName', 'roleNames', 'realName', 'title', 'mobile',
			'email', {
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
	groupField : 'account',
	proxy : {
		type : 'ajax',
		url : 'account/list',
		api : {
			read : 'account/list',
			update : 'account/update'
		},
		reader : {
			type : 'json',
			root : 'data',
			successProperty : 'success'
		}
	}
});