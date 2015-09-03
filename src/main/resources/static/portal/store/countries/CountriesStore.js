Ext.define('portal.store.countries.CountriesStore', {
	extend : 'Ext.data.Store',
	autoLoad : false,
	fields : [ 'digitalCode', 'letterCode', 'forShort', 'fullName' ],
	proxy : {
		type : 'ajax',
		url : 'amlBaseCode/countriesList',
		reader : {
			type : 'json',
			root : 'data',
			successProperty : 'success'
		}
	}
});