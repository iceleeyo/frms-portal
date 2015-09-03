Ext.define('portal.store.users.UsersRolesStore', {
	extend : 'Ext.data.Store',
	alias : 'widget.usersRolesStore',
	autoLoad : false,
	fields : [ 'rolesId', 'rolesName', 'checked' ],
	proxy : {
		type : 'ajax',
		url : 'users/loadUsersRoles',
		reader : {
			type : 'json',
			root : 'data',
			successProperty : 'success'
		}
	}
});