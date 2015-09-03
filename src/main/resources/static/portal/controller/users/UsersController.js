Ext.define('portal.controller.users.UsersController', {
	extend : 'Ext.app.Controller',
	stores : [ 'users.UsersStore', 'users.UsersRolesStore' ],
	views : [ 'users.UsersView', 'users.UsersAddView', 'users.UsersEditView' ],
	init : function() {
		this.control({
			'usersView grid' : {
				beforerender : this.beforeRender,
				afterrender : this.afterrender
			},
			'usersView button[action=search]' : {
				click : this.searchBtnClicked
			},
			'usersView grid button[text=新建]' : {
				click : this.addBtnClicked
			},
			'usersAddView button[text=确定]' : {
				click : this.addUsers
			},
			'usersView grid button[text=修改]' : {
				click : this.editBtnClicked
			},
			'usersEditView button[text=确定]' : {
				click : this.editUsers
			},
			'usersView grid button[text=删除]' : {
				click : this.deleteBtnClicked
			},
			'usersView grid actioncolumn' : {
				click : this.onRecordEdited
			}
		})
	},
	
	beforeRender : function(grid) {

	},
	
	afterrender : function(grid) {
		grid.getStore().load();
	},
	
	searchBtnClicked : function(btn) {
		var tab = btn.up('usersView').down('grid'),
			store = tab.getStore();
		var queryMap = {};
		var loginName = tab.down('textfield[name=loginName]').getValue();
		var userRoles = tab.down('combo[name=userRoles]').getValue();
		queryMap['userName'] = encodeURI(loginName);
		queryMap['userRoleIds'] = userRoles;
		store.getProxy().extraParams = queryMap;
		store.loadPage(1);
	},
	
	addBtnClicked : function(btn) {
		var grid = btn.up('grid');
		var window = this.getView('users.UsersAddView').create();
		
		window.on('close', function(window) {
			grid.getStore().load();
		});
		var usersRolesGrid = window.down('grid');
		var store = usersRolesGrid.getStore();
		var task = new Ext.util.DelayedTask(function() {
			store.load();
	    });
	    task.delay(1);
		window.show();
	},
	
	addUsers : function(btn) {
		var me = this, 
			win = btn.up('window'), 
			form = win.down('form');
		var store = this.getStore('users.UsersStore');
		if (!form.isValid()) {
			return;
		}
		var values = form.getValues();
		values.userName = Ext.String.trim(values.userName);
		if(Ext.isEmpty(values.userName)) {
			Ext.MessageBox.alert('出错提示', '用户名不能为空!');
			return;
		}
		values.enabled = Ext.isEmpty(values.enabled) ? 2 : 1;
		var md5password = Ext.util.MD5(values.password);
		values.password = md5password;
		var nodes = win.down('grid').getSelectionModel().getSelection();
		var usersRoleIds = [];
		if (nodes && nodes.length) {
			for (var i = 0; i < nodes.length; i++) {
				usersRoleIds.push(nodes[i].data.rolesId);
			}
		}
		values.roleIdList = usersRoleIds;
		Ext.Ajax.request({
			url : 'users/add',
			method : 'POST',
			jsonData : values,
			success : function(resp) {
				var record = Ext.JSON.decode(resp.responseText);
				if (!record.success) {
					Ext.MessageBox.alert('出错提示', record.error);
				} else {
					win.close();
					Ext.MessageBox.alert('新建', '新建成功');
					store.load();
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
	
	editBtnClicked : function(btn) {
		var grid = btn.up('grid'),
        	model = grid.getSelectionModel().getSelection(),
        	me = this,
        	data = {};
		if (!model[0]) {
			Ext.MessageBox.alert('出错提示', '请选择一条记录');
			return false;
		}
		data['userId'] = model[0].get('id');
		var userName = model[0].get('userName');
		var window = me.getView('users.UsersEditView').create();
		var usersRolesGrid = window.down('grid');
		var store = usersRolesGrid.getStore();
		store.on('beforeload', function (store, options) {
			Ext.apply(store.proxy.extraParams, data);
	    });
		var task = new Ext.util.DelayedTask(function() {
			store.load({
				callback : function(records, operation, success) {
					if(success) {
						for (var i = 0; i < records.length; i++) {
							var isExist = records[i].get('checked');
							if(isExist) {
								usersRolesGrid.getSelectionModel().select(records[i].index, true, false);
							}
						}
					}
				}
			});
			window.mode = 'update';
			window.setTitle('用户修改 - "' + userName + '"');
			window.down('form').loadRecord(model[0]);
			window.store = grid.getStore();
			window.grid = grid;
			window.on('close', function(window) {
				grid.getStore().load();
			});
	    });
	    task.delay(10);
		window.show();

	},
	
	editUsers : function(btn) {
		var me = this, win = btn.up('window'), form = win.down('form');
		var store = this.getStore('users.UsersStore');
		if (!form.isValid()) {
			return;
		}
		var values = form.getValues();
		values.userName = Ext.String.trim(values.userName);
		if(Ext.isEmpty(values.userName)) {
			Ext.MessageBox.alert('出错提示', '用户名不能为空!');
			return;
		}
		values.enabled = Ext.isEmpty(values.enabled) ? 2 : 1;
		var nodes = win.down('grid').getSelectionModel().getSelection();
		var usersRoleIds = [];
		if (nodes && nodes.length) {
			for (var i = 0; i < nodes.length; i++) {
				usersRoleIds.push(nodes[i].data.rolesId);
			}
		}
		values.roleIdList = usersRoleIds;
		Ext.Ajax.request({
			url : 'users/edit',
			method : 'POST',
			jsonData : values,
			success : function(resp) {
				var record = Ext.JSON.decode(resp.responseText);
				if (!record.success) {
					Ext.MessageBox.alert('出错提示', record.error);
				} else {
					win.close();
					Ext.MessageBox.alert('修改', '修改成功');
					store.load({
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
	
	deleteBtnClicked : function(btn) {
		var grid = btn.up('grid'),
			store = grid.getStore();
		var me = this,
	        models = grid.getSelectionModel().getSelection();
		if (models == null || models.length < 1) {
			Ext.MessageBox.alert('出错提示', '请选择记录后再点击"删除"按钮');
			return;
		}
		var msg = '';
		for ( var i = 0; i < models.length; ++i) {
			msg +=  '用户名称=' + models[i].get('userName') + '<br>';
		}
		var callback = function(btn) {
			if (btn == 'yes') {
				var ids = [];// 选中项
				Ext.each(models, function(item) {
					ids.push(item.data);
	            });
	            Ext.Ajax.request({
	            	url : 'users/delete',
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
	            	failure : function(resp, options) {
	            		var result = Ext.JSON.decode(resp.responseText);
						if (!result.success) {
							Ext.MessageBox.alert('出错提示', result.error);
						} else {
							Ext.MessageBox.alert('运行错误', resp.responseText);
						}
	            	}
	            });
			}
		};
		Ext.Msg.confirm('确认窗口', '你确认删除以下用户吗？<br>' + msg, callback);
	},
	
	onRecordEdited : function(grid, cell, row, col, e) {
		var rec = grid.getStore().getAt(row),
			userName = rec.get('userName'),
			me = this,
			userId = rec.get('id');
		var data = {};
		data['userId'] = userId;
		var window = me.getView('users.UsersEditView').create();
		window.mode = 'update';
		window.setTitle('用户修改 - "' + userName + '"');
		
		var usersRolesGrid = window.down('grid');
		var store = usersRolesGrid.getStore();
		store.on('beforeload', function (store, options) {
			Ext.apply(store.proxy.extraParams, data);
	    });
		var task = new Ext.util.DelayedTask(function() { 
			store.load({
				callback : function(records, operation, success) {
					if(success) {
						for (var i = 0; i < records.length; i++) {
							var isExist = records[i].get('checked');
							if(isExist) {
								usersRolesGrid.getSelectionModel().select(records[i].index, true, false);
							}
						}
					}
				}
			});
		});
		window.down('form').loadRecord(rec);
		window.store = grid.getStore();
		window.grid = grid;
		window.on('close', function(window) {
			grid.getStore().load();
		});
		task.delay(10);
		window.show();
	}
});