Ext.define('portal.controller.risklevel.RiskLevelController', {
	extend : 'Ext.app.Controller',
	views : [ 'risklevel.RiskLevelView', 'risklevel.RiskLevelAddView', 
	          'risklevel.RiskLevelEditView' ],
	stores : [ 'risklevel.RiskLevelStore' ],
	init : function() {
		this.control({
			'riskLevelView grid' : {
				beforerender : this.beforeRender,
				afterrender : this.afterrender,
				beforeselect : this.beforeGridSelect
			},
			'riskLevelView button[action=search]' : {
				click : this.searchBtnClicked
			},
			'riskLevelView grid button[text=新建]' : {
				click : this.addBtnClicked
			},
			'riskLevelAddView button[text=确定]' : {
				click : this.addRiskLevel
			},
			'riskLevelView grid button[text=修改]' : {
				click : this.editBtnClicked
			},
			'riskLevelEditView button[text=确定]' : {
				click : this.editRiskLevel
			},
			'riskLevelView grid button[text=删除]' : {
				click : this.deleteBtnClicked
			},
			'riskLevelView grid actioncolumn' : {
				click : this.onRecordEdited
			}
		});
	},

	beforeRender : function(grid) {
		
	},

	beforeGridSelect : function(grid, record, index, eOpts) {
		if (record.get('readonly') == 1) {
			Ext.MessageBox.alert('出错提示', '风险等级' + record.get('name') + '是只读状态,不能修改!');
			return false;
		}
	},
	
	afterrender : function(grid) {
		grid.getStore().load();
	},
	
	searchBtnClicked : function(btn) {
		var grid = btn.up('riskLevelView').down('grid'),
			store = grid.getStore();
		var queryMap = {};
		var level = grid.down('numberfield[name=level]').getValue();
		var name = grid.down('textfield[name=name]').getValue();
		if(level && isNaN(level)) {
			return;
		}
		queryMap['level'] = level;
		queryMap['name'] = encodeURI(name);
		store.getProxy().extraParams = queryMap;
		store.loadPage(1);
	},
	
	addBtnClicked : function(btn) {
		var grid = btn.up('grid');
		var window = this.getView('risklevel.RiskLevelAddView').create();
		window.mode = 'insert';
		window.on('close', function(window) {
			grid.getStore().load();
		});
		window.show();
	},
	
	addRiskLevel : function(btn) {
		var me = this, 
			win = btn.up('window'), 
			form = win.down('form');
		var store = this.getStore('risklevel.RiskLevelStore');
		if (!form.isValid()) {
			return;
		}
		var values = form.getValues();
		Ext.Ajax.request({
			url : 'risklevel/add',
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
		var window = Ext.widget('riskLevelEditView');
		window.setTitle('风险等级修改 - "' + model[0].get('levelVal') + '"');
		window.down('form').loadRecord(model[0]);
		window.store = grid.getStore();
		window.grid = grid;
		window.on('close', function(window) {
			grid.getStore().load();
		});
		window.show();
	},
	
	editRiskLevel : function(btn) {
		var me = this, 
			win = btn.up('window'), 
			form = win.down('form');
		var store = this.getStore('risklevel.RiskLevelStore');
		if (!form.isValid()) {
			return;
		}
		var values = form.getValues();
		if (parseInt(values.levelMin) > parseInt(values.levelMax)) {
			Ext.MessageBox.alert('出错提示', '最小风险值不能大于最大风险值!');
			return;
		}
		Ext.Ajax.request({
			url : 'risklevel/edit',
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
			level = rec.get('levelVal'),
			window = Ext.widget('riskLevelEditView');
		window.mode = 'update';
		window.setTitle('风险等级修改 - "' + level + '"');
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
			msg +=  '风险等级=' + models[i].get('levelVal') + '<br>';
		}
		var callback = function(btn) {
			if (btn == 'yes') {
				var ids = [];// 选中项
				Ext.each(models, function(item) {
					ids.push(item.data);
				});
				Ext.Ajax.request({
					url : 'risklevel/delete',
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
		Ext.Msg.confirm('确认窗口', '你确认删除以下风险等级吗？<br>' + msg, callback);
	}
});