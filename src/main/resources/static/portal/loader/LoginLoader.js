Ext.define('portal.loader.LoginLoader', {
	extend : 'Ext.ComponentLoader',
	url : 'users/whoAmI',
	autoLoad : true,
	renderer : 'data',
	callback : function(loader, succ) {
		if (succ == false || loader.target.data.success == false) {
			Ext.MessageBox.alert('运行错误', loader.target.data.error, function() {
				window.location = 'login.html';
			});
		}
	}
});