Ext.define('portal.store.risklevel.RiskLevelStore', {
	extend : 'Ext.data.Store',
	autoLoad : false,
	fields : [ 'id', {
		name : 'levelVal',
		type : 'number'
	}, {
		name : 'levelMin',
		type : 'number'
	}, {
		name : 'levelMax',
		type : 'number'
	}, 'name', 'modifer', {
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
	} ],
	sorters : [ 'level' ],
	proxy : {
		type : 'ajax',
		url : 'risklevel/list',
		api : {
			update : 'risklevel/edit',
			read : 'risklevel/list',
			create : 'risklevel/add',
			destroy : 'risklevel/delete'
		},
		reader : {
			type : 'json',
			root : 'data',
			successProperty : 'success'
		}
	}
});