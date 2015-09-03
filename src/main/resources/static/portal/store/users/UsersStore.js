Ext.define('portal.store.users.UsersStore', {
	extend : 'Ext.data.Store',
	autoLoad : false,
	fields : [ 'id', 'userName', 'groupName', 'roleNames', 'realName', 'title',
			'mobile', 'email', 'deptId', 'modifer', {
				name : 'permission',
				type : 'number'
			}, {
				name : 'enabled',
				type : 'short'
			}, {
				name : 'workStatus',
				type : 'short'
			}, 'password', {
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
		url : 'users/list',
		api : {
			update : 'users/edit',
			read : 'users/list',
			create : 'users/add',
			destroy : 'users/delete'
		},
		reader : {
			type : 'json',
			root : 'data',
			successProperty : 'success'
		}
	}
});