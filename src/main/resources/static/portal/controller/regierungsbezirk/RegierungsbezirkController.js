Ext.define('portal.controller.regierungsbezirk.RegierungsbezirkController', {
	extend : 'Ext.app.Controller',
	views : [ 'regierungsbezirk.RegierungsbezirkView' ],
	stores : [ 'regierungsbezirk.RegierungsbezirkStore' ],
	init : function() {
		this.control({
			'regierungsbezirkView grid' : {
				afterrender : this.afterrender
			},
			'regierungsbezirkView button[iconCls=search-icon]' : {
				click : this.searchBtnClicked
			}
		});
	},

	afterrender : function(grid) {
		grid.getStore().load();
	},
	
	searchBtnClicked : function(btn, event, opts) {
		var grid = btn.up('regierungsbezirkView').down('grid');
		var code = grid.down('textfield[name=code]').getValue();
	    var name = grid.down('textfield[name=name]').getValue();
	    var queryMap = {};
	    queryMap['code'] = code;
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