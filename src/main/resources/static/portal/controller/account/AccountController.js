Ext.define('portal.controller.account.AccountController', {
	extend : 'Ext.app.Controller',
	views : [ 'account.AccountView' ],
	stores : [ 'account.AccountStore' ],
	init : function() {
		this.control({
			'accountView' : {
				render : this.onRender
			},
			'accountView button[iconCls=button-ok]' : {
				click : this.updateRender
			},
			'accountView button[iconCls=button-cancel]' : {
				click : this.resetRender
			},
			'accountMenuView [text=退出]' : {
				click : this.logout
			}
		});
	},
	
	logout : function(){
		Ext.Ajax.request({
			url : 'login/logout',
			method : 'GET',
			success: function (response) {
	            var obj = Ext.JSON.decode(response.responseText);
	            if (obj.success == true) {
	                window.location = 'login.html';
	            }
	        },
	        failure: function () {
	            window.location = 'index.html';
	        }
		})
	},
	
	onRender : function(tab, record) {
		var me = this;
		this.getStore('account.AccountStore').load({
			callback : function(records, operation, success) {
				if (!success) {
					Ext.MessageBox.alert('运行错误', operation.request.proxy.reader.rawData.error);
				} else {
					tab.down('form').loadRecord(records[0]);
				}
			}
		});
	},
	
	updateRender : function(btn, opts) {
		var form = btn.up('form');
		if (!form.isValid()) {
			return;
		}
		var me = this;
		if(form.getValues().newPassword != '' && form.getValues().newPassword != form.getValues().newPasswordAgain) {
			Ext.MessageBox.alert('提示', '两次密码不一致,请确认!');
			return;
		}
		form.submit({
			url : 'account/update',
			success : function(form, action) {
				Ext.MessageBox.alert('修改结果', '修改成功，请确认！');
				me.onRender(btn.up('accountView'));
				me.resetRender(btn);
			},
			failure : function(form, action) {
				var result = Ext.JSON.decode(action.response.responseText);
				if(!result.success) {
					Ext.MessageBox.alert('修改结果', result.error);
				} else {
					Ext.MessageBox.alert('修改结果', '修改失败，请您再次确认！');
				}
			}
		});
	},
	
	resetRender : function(tab, opts) {
		var form = tab.up('form');
		form.getForm().reset();
	}
});