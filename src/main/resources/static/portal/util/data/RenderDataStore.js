Ext.define('portal.util.data.RenderDataStore', {
	extend : 'Ext.data.Store',
	autoLoad : false,
	fields : ['CODE', 'VALUE'],
	proxy : {
		type : 'ajax',
		url : 'store/getRenderByType',
		actionMethods : {
			read : 'POST'
		},
		reader : {
			type : 'json',
			successProperty : 'success',
			root : 'data'
		}
	}
});