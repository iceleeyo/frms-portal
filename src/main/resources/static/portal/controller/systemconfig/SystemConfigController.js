Ext.define('portal.controller.systemconfig.SystemConfigController', {
	extend : 'Ext.app.Controller',
	views : [ 'systemconfig.SystemConfigView', 'systemconfig.SystemConfigAddView',
	          'systemconfig.SystemConfigEditView' ],
	stores : [ 'systemconfig.SystemConfigStore' ],
	init : function() {
		this.control({
			'systemConfigView grid' : {
				afterrender : this.afterrender
			},
			'systemConfigView button[action=search]' : {
				click : this.searchBtnClicked
			},
			'systemConfigView grid button[text=新建]' : {
				click : this.addBtnClicked
			},
			'systemConfigAddView button[text=确定]' : {
				click : this.addSystemConfig
			},
			'systemConfigView grid button[text=修改]' : {
				click : this.editBtnClicked
			},
			'systemConfigEditView button[text=确定]' : {
				click : this.editSystemConfig
			},
			'systemConfigView grid button[text=删除]' : {
				click : this.deleteBtnClicked
			},
			'systemConfigView grid actioncolumn' : {
				click : this.onRecordEdited
			}
		});
	},
	
	afterrender : function(grid) {
		grid.getStore().load();
	},
	
	searchBtnClicked : function(btn) {
		var tab = btn.up('systemConfigView').down('grid'),
			store = tab.getStore();
		var queryMap = {};
		var type = tab.down('numberfield[name=type]').getValue();
		var typeName = tab.down('textfield[name=typeName]').getValue();
		var code = tab.down('textfield[name=code]').getValue();
		if(type && isNaN(type)) {
			return;
		}
		queryMap['type'] = type;
		queryMap['typeName'] = encodeURI(typeName);
		queryMap['code'] = code;
		store.getProxy().extraParams = queryMap;
		store.loadPage(1);
	},
	
	addBtnClicked : function(btn) {
		var window = Ext.widget('systemConfigAddView');
		window.show();
	},
	
	addSystemConfig : function(btn) {
		var me = this, 
			win = btn.up('window'), 
			form = win.down('form');
		var store = this.getStore('systemconfig.SystemConfigStore');
		if (!form.isValid()) {
			return;
		}
		var values = form.getValues();
		if (values.enabled) {
			values.enabled = 1;
		} else {
			values.enabled = 2;
		}
		Ext.Ajax.request({
			url : 'systemConfig/add',
			method : 'POST',
			jsonData : values,
			success : function(resp) {
				var record = Ext.JSON.decode(resp.responseText);
				if (!record.success) {
					Ext.MessageBox.alert('运行错误', record.error);
				} else {
					win.close();
					Ext.Msg.alert("新建成功", "新建成功");
					store.reload();
				}
			},
			failure : function(resp) {
				var result = Ext.JSON.decode(resp.responseText);
				if (!result.success) {
					Ext.Msg.alert("提示", result.error);
				} else {
					Ext.MessageBox.alert('运行错误', resp.responseText);
				}
			}
		});
	},
	
	editBtnClicked : function(btn) {
		var grid = btn.up('grid'),
			model = grid.getSelectionModel().getSelection(),
			me = this;
		if (!model[0]) {
			Ext.Msg.alert('提示', '请选择一条记录');
			return false;
		}
		var window = Ext.widget('systemConfigEditView');
		window.setTitle('系统配置修改 - "' + model[0].get('id') + '"');
		Ext.Ajax.request({
			url : 'systemConfig/findSystemConfigById?id=' + model[0].get('id'),
			method : 'GET',
			async : false,
			success : function(resp) {
				var record = Ext.JSON.decode(resp.responseText);
				if (!record.success) {
					Ext.MessageBox.alert('运行错误', record.error);
				} else {
					model[0].data = record.data;
				}
			},
			failure : function(resp) {
				var result = Ext.JSON.decode(resp.responseText);
				if (!result.success) {
					Ext.Msg.alert("提示", result.error);
				} else {
					Ext.MessageBox.alert('运行错误', resp.responseText);
				}
			}
		});
		window.down('form').loadRecord(model[0]);
		window.show();
	},
	
	editSystemConfig : function(btn) {
		var me = this, 
			win = btn.up('window'), 
			form = win.down('form');
		var store = this.getStore('systemconfig.SystemConfigStore');
		if (!form.isValid()) {
			return;
		}
		var values = form.getValues();
		if (values.enabled) {
			values.enabled = 1;
		} else {
			values.enabled = 2;
		}
		Ext.Ajax.request({
			url : 'systemConfig/edit',
			method : 'POST',
			jsonData : values,
			success : function(resp) {
				var record = Ext.JSON.decode(resp.responseText);
				if (!record.success) {
					Ext.MessageBox.alert('运行错误', record.error);
				} else {
					win.close();
					Ext.Msg.alert("修改成功", "修改成功");
					store.reload();
				}
			},
			failure : function(resp) {
				var result = Ext.JSON.decode(resp.responseText);
				if (!result.success) {
					Ext.Msg.alert("提示", result.error);
				} else {
					Ext.MessageBox.alert('运行错误', resp.responseText);
				}
			}
		});
	},
	
	onRecordEdited : function(grid, cell, row) {
		var rec = grid.getStore().getAt(row),
			id = rec.get('id'),
			me = this,
			window = Ext.widget('systemConfigEditView');
		
		window.mode = 'update';
		window.setTitle('系统配置修改 - "' + id + '"');
		var form = window.down('form');
		form.loadRecord(rec);
		window.on('close', function(window) {
			grid.getStore().load();
		});
		window.show();
	},
	
	deleteBtnClicked : function(btn) {
		var grid = btn.up('grid'),
			store = grid.getStore(),
			me = this,
			models = grid.getSelectionModel().getSelection();
		if (models == null || models.length < 1) {
			Ext.Msg.alert('出错提示', '请选择记录后再点击"删除"按钮');
			return;
		}
		var msg = '';
		for ( var i = 0; i < models.length; ++i) {
			msg +=  '系统配置ID=' + models[i].get('id') + '<br>';
		}
		var callback = function(btn) {
			if (btn == 'yes') {
				var ids = [];// 选中项
				Ext.each(models, function(item) {
					ids.push(item.data);
				});
				Ext.Ajax.request({
					url : 'systemConfig/delete',
					method : 'POST',
					headers : 'application/json',
					jsonData : ids,
					success : function(response, options) {
						var result = Ext.JSON.decode(response.responseText);
						if(!result.success) {
							Ext.Msg.alert("提示", result.error);
						} else {
							Ext.MessageBox.alert('提示', '删除成功');
							grid.getStore().load();
						}
					},
					failure : function(response, options) {
						Ext.MessageBox.alert('运行错误', '删除失败!');
					}
				});
			}
		};
		Ext.Msg.confirm('确认窗口', '你确认删除以下系统配置吗？<br>' + msg, callback);
	}
});