Ext.define('portal.controller.currency.CurrencyController', {
	extend : 'Ext.app.Controller',
	views : [ 'currency.CurrencyView'],
	stores : [ 'currency.CurrencyStore' ],
	init : function() {
		this.control({
			'currencyView grid' : {
				afterrender : this.afterrender
			},
			'currencyView button[iconCls=search-icon]' :{
				click : this.searchBtnClicked
			}
		});
	},
	
	afterrender : function(grid) {
		grid.getStore().load();
	},
	
	searchBtnClicked : function(btn, event, opts) {
		var grid = btn.up('currencyView').down('grid');
		var digitalCode = grid.down('textfield[name=digitalCode]').getValue();
	    var letterCode = grid.down('textfield[name=letterCode]').getValue();
	    var name = grid.down('textfield[name=name]').getValue();
	    var queryMap = {};
	    queryMap['digitalCode'] = digitalCode;
	    queryMap['letterCode'] = letterCode;
	    queryMap['name'] = name;
	    
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