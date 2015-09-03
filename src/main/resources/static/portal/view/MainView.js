Ext.define('portal.view.MainView', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.mainview',
	title : '风险大盘',
	width : '100%',
	height : '100%',
	initComponent : function() {
		this.callParent(arguments);
	},
//	loader : {
//		autoLoad : true,
//		url : '/main.html',
//		scripts : true
//	},
	html:'<iframe id="main-frame" name="main-frame" height="100%" width="100%" src="/main.html">'
});