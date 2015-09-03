Ext.define('portal.store.regierungsbezirk.RegierungsbezirkStore', {
	extend : 'Ext.data.Store',
	autoLoad : false,
	fields : [ 'code', 'name' ],
	proxy : {
		type : 'ajax',
		url : 'amlBaseCode/regierungsbezirkList',
		reader : {
			type : 'json',
			root : 'data',
			successProperty : 'success'
		}
	}
});