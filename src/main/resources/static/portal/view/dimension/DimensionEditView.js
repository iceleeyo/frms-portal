Ext.define('portal.view.dimension.DimensionEditView', {
	extend : 'Ext.window.Window',
	alias : 'widget.dimensionEditView',
	title : '新建维度信息',
	layout : 'border',
	iconCls : 'dimension-icon',
	autoShow : false,
	autoScroll : true,
	autoHeight : true,
	height : 160,
	width : 300,
	draggable : true,
	modal : true,
	initComponent : function() {
		var yinziData = [ [ '否', 0 ], [ '是', 1 ] ];
		var yinziStore = new Ext.data.SimpleStore({
			fields : [ 'name', 'value' ],
			data : yinziData
		});
		if(yinziStore.getCount() < 1) {
			yinziStore.load();
		}
		this.items = [ {
			xtype : 'form',
			bodyStyle : {
				padding : '10px'
			},
			border : false,
			region : 'center',
			width : '100%',
			height : '100%',
			items : [ {
				fieldLabel : '维度ID',
				xtype : 'textfield',
				name : 'id',
				hidden : true
			}, {
				fieldLabel : '名称',
				xtype : 'textfield',
				name : 'name',
				labelAlign : "right",
				allowBlank : false
			}, {
				fieldLabel : '备注',
				xtype : 'textfield',
				labelAlign : "right",
				name : 'memo'
			}, {
				fieldLabel : '递归因子',
				xtype : 'combobox',
				name : 'type',
				editable : false,
				labelAlign : "right",
				mode : 'local',
				valueField : 'value',
				displayField : 'name',
				store : yinziStore
			} ]
		} ];
		this.buttons = [ {
			text : '确定',
			action : 'add'
		}, {
			text : '取消',
			scope : this,
			handler : function() {
				this.close();
			}
		} ];
		this.callParent(arguments);
	}
});