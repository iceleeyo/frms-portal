Ext.define('portal.controller.roles.RolesController', {
	extend : 'Ext.app.Controller',
	views : [ 'roles.RolesView', 'roles.RolesAddView', 'roles.RolesEditView' ],
	stores : [ 'roles.RolesStore' ],
	init : function() {
		this.control({
			'rolesView grid' : {
				beforerender : this.beforeRender,
				afterrender : this.afterrender
			},
			'rolesView button[action=search]' : {
				click : this.searchBtnClicked
			},
			'rolesView grid button[text=新建]' : {
				click : this.addBtnClicked
			},
			'rolesAddView button[text=确定]' : {
				click : this.addRoles
			},
			'rolesView grid button[text=修改]' : {
				click : this.editBtnClicked
			},
			'rolesEditView button[text=确定]' : {
				click : this.editRoles
			},
			'rolesView grid button[text=删除]' : {
				click : this.deleteBtnClicked
			},
			'rolesView grid actioncolumn' : {
				click : this.onRecordEdited
			}
		});
	},

	beforeRender : function(grid) {
		
	},
	
	afterrender : function(grid) {
		grid.getStore().load();
	},
	
	searchBtnClicked : function(btn) {
		var tab = btn.up('rolesView').down('grid'),
			store = tab.getStore();
		var queryMap = {};
		var roleName = tab.down('textfield[name=roleName]').getValue();
		queryMap['roleName'] = encodeURI(roleName);
		store.getProxy().extraParams = queryMap;
		store.loadPage(1);
	},
	
	addBtnClicked : function(btn) {
		var window = Ext.widget('rolesAddView');
		window.on('close', function(window) {
			btn.up('grid').getStore().load();
		});
		window.show();
	},
	
	addRoles : function(btn) {
		var me = this, 
			win = btn.up('window'), 
			form = win.down('form');
		var store = this.getStore('roles.RolesStore');
		if (!form.isValid()) {
			return;
		}
		var values = form.getValues();
		values.roleName = Ext.String.trim(values.roleName);
		if(Ext.isEmpty(values.roleName)) {
			Ext.MessageBox.alert('出错提示', '角色名不能为空!');
			return;
		}
		var nodes = win.down('treepanel').getChecked();
		var resourceIds = [];
		if (nodes && nodes.length) {
			for (var i = 0; i < nodes.length; i++) {
				resourceIds.push(nodes[i].data.id);
			}
		}
		values.resourceIdList = resourceIds;
		values.enabled = Ext.isEmpty(values.enabled) ? 2 : 1;
		Ext.Ajax.request({
			url : 'roles/add',
			method : 'POST',
			jsonData : values,
			success : function(resp, options) {
				var record = Ext.JSON.decode(resp.responseText);
				if (!record.success) {
					Ext.MessageBox.alert('出错提示', record.error);
				} else {
					win.close();
					Ext.MessageBox.alert('新建', '新建成功');
					store.reload();
				}
			},
			failure : function(resp, options) {
				var result = Ext.JSON.decode(resp.responseText);
				if (!result.success) {
					Ext.MessageBox.alert('出错提示', result.error);
				} else {
					Ext.MessageBox.alert('运行错误', resp.responseText);
				}
			}
		});
	},
	
	editBtnClicked : function(btn) {
		var grid = btn.up('grid'),
    		model = grid.getSelectionModel().getSelection();
		if (!model[0]) {
			Ext.MessageBox.alert('出错提示', '请选择一条记录');
			return false;
		}
		var window = Ext.widget('rolesEditView');
		window.setTitle('角色修改 - "' + model[0].get('roleName') + '"');
		window.down('form').loadRecord(model[0]);
		window.store = grid.getStore();
		window.grid = grid;
		window.on('close', function(window) {
			grid.getStore().load();
		});
		window.show();
	},
	
	editRoles : function(btn) {
		var me = this, 
			win = btn.up('window'), 
			form = win.down('form');
		var store = this.getStore('roles.RolesStore');
		if (!form.isValid()) {
			return;
		}
		var values = form.getValues();
		values.roleName = Ext.String.trim(values.roleName);
		if(Ext.isEmpty(values.roleName)) {
			Ext.MessageBox.alert('出错提示', '角色名不能为空!');
			return;
		}
		var nodes = win.down('treepanel').getChecked();
		var resourceIds = [];
		if (nodes && nodes.length) {
			for (var i = 0; i < nodes.length; i++) {
				resourceIds.push(nodes[i].data.id);
			}
		}
		values.resourceIdList = resourceIds;
		values.enabled = Ext.isEmpty(values.enabled) ? 2 : 1;
		Ext.Ajax.request({
			url : 'roles/edit',
			method : 'POST',
			jsonData : values,
			success : function(resp, options) {
				var record = Ext.JSON.decode(resp.responseText);
				if (!record.success) {
					Ext.MessageBox.alert('出错提示', record.error);
				} else {
					win.close();
					Ext.MessageBox.alert('修改', '修改成功');
					store.reload({
						scope: me,
						callback: function(records, operation, success) {
							var index = win.grid.getSelectionModel().getSelection()[0];
							win.grid.getSelectionModel().deselectAll();
							win.grid.getSelectionModel().selectRange(index, index);
						}
					});
				}
			},
			failure : function(resp) {
				var result = Ext.JSON.decode(resp.responseText);
				if (!result.success) {
					Ext.MessageBox.alert('出错提示', result.error);
				} else {
					Ext.MessageBox.alert('运行错误', resp.responseText);
				}
			}
		});
	},
	
	onRecordEdited : function(grid, cell, row) {
		var rec = grid.getStore().getAt(row),
			roleName = rec.get('roleName'),
			window = Ext.widget('rolesEditView');
		window.mode = 'update';
		window.setTitle('角色修改 - "' + roleName + '"');
		var form = window.down('form');
		form.loadRecord(rec);
		window.store = grid.getStore();
		window.grid = grid;
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
			Ext.MessageBox.alert('出错提示', '请选择记录后再点击"删除"按钮');
			return;
		}
		var msg = '';
		for ( var i = 0; i < models.length; ++i) {
			msg +=  '角色名称=' + models[i].get('roleName') + '<br>';
		}
		var callback = function(btn) {
			if (btn == 'yes') {
				var ids = [];// 选中项
				Ext.each(models, function(item) {
					ids.push(item.data);
				});
				Ext.Ajax.request({
					url : 'roles/delete',
					method : 'POST',
					headers : 'application/json',
					jsonData : ids,
					success : function(resp, options) {
						var result = Ext.JSON.decode(resp.responseText);
						if(!result.success) {
							Ext.MessageBox.alert('出错提示', result.error);
						} else {
							Ext.MessageBox.alert('删除', '删除成功');
							grid.getStore().load();
						}
					},
					failure : function(response, options) {
						if(!result.success) {
							Ext.MessageBox.alert('出错提示', result.error);
						} else {
							Ext.MessageBox.alert('运行错误', resp.responseText);
						}
					}
				});
			}
		};
		Ext.Msg.confirm('确认窗口', '你确认删除以下角色吗？<br>' + msg, callback);
	}
});