Ext.define('portal.controller.dimension.DimensionController', {
	extend : 'Ext.app.Controller',
	views : [ 'dimension.DimensionView', 'dimension.DimensionAddView',
	          'dimension.DimensionEditView' ],
	stores : [ 'dimension.DimensionStore' ],
	init : function() {
		this.control({
			'dimensionView grid' : {
				beforerender : this.beforeRender,
				afterrender : this.afterrender
			},
			'dimensionView button[action=search]' : {
				click : this.searchBtnClicked
			},
			'dimensionView grid button[text=新建]' : {
				click : this.addBtnClicked
			},
			'dimensionAddView button[text=确定]' : {
				click : this.addDimension
			},
			'dimensionView grid button[text=修改]' : {
				click : this.editBtnClicked
			},
			'dimensionEditView button[text=确定]' : {
				click : this.editDimension
			},
			'dimensionView grid button[text=删除]' : {
				click : this.deleteBtnClicked
			},
			'dimensionView grid actioncolumn' : {
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
		var grid = btn.up('dimensionView').down('grid'),
			store = grid.getStore();
		var queryMap = {};
		var name = grid.down('textfield[name=name]').getValue();
		queryMap['name'] = encodeURI(name);
		store.getProxy().extraParams = queryMap;
		store.loadPage(1);
	},
	
	addBtnClicked : function(btn) {
		var grid = btn.up('grid');
		var window = this.getView('dimension.DimensionAddView').create();
		window.mode = 'insert';
		window.on('close', function(window) {
			grid.getStore().load();
		});
		window.show();
	},
	
	addDimension : function(btn) {
		var me = this, 
			win = btn.up('window'), 
		form = win.down('form');
		var store = this.getStore('dimension.DimensionStore');
		if (!form.isValid()) {
			return;
		}
		var values = form.getValues();
		Ext.Ajax.request({
			url : 'dimension/add',
			method : 'POST',
			jsonData : values,
			success : function(resp, options) {
				var result = Ext.JSON.decode(resp.responseText);
				if (!result.success) {
					Ext.MessageBox.alert('出错提示', result.error);
				} else {
					win.close();
					Ext.MessageBox.alert('新建', '新建成功');
					store.load();
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
			model = grid.getSelectionModel().getSelection(),
			me = this;
		if (!model[0]) {
			Ext.MessageBox.alert('出错提示', '请选择一条记录');
			return false;
		}
		var id = model[0].get('id');
		var window = Ext.widget('dimensionEditView');
		window.setTitle('维度信息修改 - "' + model[0].get('name') + '"');
		window.down('form').loadRecord(model[0]);
		window.store = grid.getStore();
		window.grid = grid;
		window.on('close', function(window) {
			grid.getStore().load();
		});
		window.show();
	},
	
	editDimension : function(btn) {
		var me = this, 
			win = btn.up('window'), 
			form = win.down('form');
		var store = this.getStore('dimension.DimensionStore');
		if (!form.isValid()) {
			return;
		}
		var values = form.getValues();
		
		if(values.type == '否') {
			values.type = 0;
		} else if(values.type == '是') {
			values.type = 1;
		}
		Ext.Ajax.request({
			url : 'dimension/edit',
			method : 'POST',
			jsonData : values,
			success : function(resp, options) {
				var result = Ext.JSON.decode(resp.responseText);
				if (!result.success) {
					Ext.MessageBox.alert('出错提示', result.error);
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
	
	onRecordEdited : function(grid, cell, row, col, e) {
		var rec = grid.getStore().getAt(row),
			level = rec.get('name'),
			window = Ext.widget('dimensionEditView');
		window.mode = 'update';
		window.setTitle('维度信息修改 - "' + level + '"');
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
			msg +=  '维度信息=' + models[i].get('name') + '<br>';
		}
		var callback = function(btn) {
			if (btn == 'yes') {
				var ids = [];
				Ext.each(models, function(item) {
					ids.push(item.data);
				});
				Ext.Ajax.request({
					url : 'dimension/delete',
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
		Ext.Msg.confirm('确认窗口', '你确认删除以下维度信息吗？<br>' + msg, callback);
	}
});