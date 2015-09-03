Ext.define('portal.controller.Main', {
	extend : 'Ext.app.Controller',
	views : [ 'MainView' ],
	init : function() {
		this.control({
			'mainview' :　{
				afterrender : function(){
				}
			}
		});
	}
});