Ext.define('portal.store.roles.RolesStore', {
	extend : 'Ext.data.Store',
	autoLoad : false,
	fields : [ {
		name : 'id',
		type : 'int'
	}, {
		name : 'roleName',
		type : 'String'
	}, {
		name : 'roleDesc',
		type : 'String'
	}, {
		name : 'enabled',
		type : 'int'
	}, {
		name : 'updateTime',
		type : 'date',
		dateFormat : 'time'
	}, {
		name : 'createTime',
		type : 'date',
		dateFormat : 'time'
	}, {
		name : 'modifer',
		type : 'String'
	} ],
	proxy : {
		type : 'ajax',
		url : 'roles/list',
		api : {
			update : 'roles/edit',
			read : 'roles/list',
			create : 'roles/add',
			destroy : 'roles/delete'
		},
		reader : {
			type : 'json',
			root : 'data',
			successProperty : 'success'
		}
	}
});