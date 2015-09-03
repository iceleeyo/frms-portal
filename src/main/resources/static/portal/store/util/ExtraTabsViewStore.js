Ext.define('portal.store.util.ExtraTabsViewStore', {
	extend : 'Ext.data.Store',
	autoLoad : false,
	alias : 'widget.extratabsviewstore',
	fields : [ {
		name : 'xtype',
		type : 'string'
	}, {
		name : 'paramSensitive',
		type : 'boolean'
	}, {
		name : 'navParam',
		type : 'boolean'
	}, {
		name : 'controllerName',
		type : 'string'
	}, {
		name : 'openType',
		type : 'string'
	} ],
	proxy : {
		type : 'ajax',
		url : 'tabView/list',
		reader : {
			type : 'json',
			root : 'data',
			successProperty : 'success'
		}
	}
});