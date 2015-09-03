Ext.define('portal.store.resources.ResourcesButtonStore', {
	extend : 'Ext.data.Store',
	autoLoad : false,
	fields : [ {
		name : 'key'
	}, {
		name : 'value'
	}, {
		name : 'code'
	} ],
	proxy : {
		type : 'ajax',
		url : 'resourcesButton/list',
		api : {
			read : 'resourcesButton/list'
		},
		reader : {
			type : 'json',
			root : 'data',
			successProperty : 'success'
		}
	}
});