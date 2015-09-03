Ext.define('portal.store.dimension.DimensionStore', {
	extend : 'Ext.data.Store',
	autoLoad : false,
	fields : [ 'id', 'name', 'memo', 'modifer', {
		name : 'readonly',
		type : 'number'
	}, {
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
	}, {
		name : 'type',
		convert : function(value, record) {
			return (value == null || value == '') ? '否' : value;
		}
	} ],
	sorters : [ 'id' ],
	proxy : {
		type : 'ajax',
		url : 'dimension/list',
		api : {
			update : 'dimension/edit',
			read : 'dimension/list',
			create : 'dimension/add',
			destroy : 'dimension/delete'
		},
		reader : {
			type : 'json',
			root : 'data',
			successProperty : 'success'
		}
	}
});