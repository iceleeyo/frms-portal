Ext.define('portal.store.currency.CurrencyStore', {
	extend : 'Ext.data.Store',
	autoLoad : false,
	fields : [ 'digitalCode', 'letterCode', 'name' ],
	proxy : {
		type : 'ajax',
		url : 'amlBaseCode/currencyList',
		reader : {
			type : 'json',
			root : 'data',
			successProperty : 'success'
		}
	}
});