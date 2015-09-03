Ext.define('portal.controller.countries.CountriesController', {
	extend : 'Ext.app.Controller',
	views : [ 'countries.CountriesView' ],
	stores : [ 'countries.CountriesStore' ],
	init : function() {
		this.control({
			'countriesView grid' : {
				afterrender : this.afterrender
			},
			'countriesView button[iconCls=search-icon]' : {
				click : this.searchBtnClicked
			}
		});
	},

	afterrender : function(grid) {
		grid.getStore().load();
	},

	searchBtnClicked : function(btn, event, opts) {
		var grid = btn.up('countriesView').down('grid');
		var digitalCode = grid.down('textfield[name=digitalCode]').getValue();
	    var letterCode = grid.down('textfield[name=letterCode]').getValue();
	    var forShort = grid.down('textfield[name=forShort]').getValue();
	    var fullName = grid.down('textfield[name=fullName]').getValue();
	    var queryMap = {};
		queryMap['digitalCode'] = digitalCode;
	    queryMap['letterCode'] = letterCode;
	    queryMap['forShort'] = forShort;
	    queryMap['fullName'] = fullName;
	    grid.getStore().getProxy().extraParams = queryMap;
	    grid.getStore().loadPage(1, {
	    	callback : function(records, operation, success) {
	    		if (!success) {
	    			Ext.Msg.alert('运行错误', operation.request.proxy.reader.rawData.error);
	    		}
	    	}
	    });
	}
});