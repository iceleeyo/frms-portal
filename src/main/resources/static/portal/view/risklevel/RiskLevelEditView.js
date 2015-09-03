Ext.apply(Ext.form.VTypes, {
	numberrange : function(val, field) {
		if (field.startNumberField) {
			var sd = Ext.getCmp(field.startNumberField);
			sd.maxValue = val;
		} else if (field.endNumberField) {
			var ed = Ext.getCmp(field.endNumberField);
			ed.minValue = val;
		}
		return true;
	},
	numberrangeText : '最小风险值不能大于最大风险值!'
});
Ext.define('portal.view.risklevel.RiskLevelEditView', {
	extend : 'Ext.window.Window',
	alias : 'widget.riskLevelEditView',
	title : '修改风险等级',
	layout : 'border',
	iconCls : 'navigator-risklevel',
	autoShow : false,
	autoScroll : true,
	autoHeight : true,
	height : 220,
	width : 300,
	draggable : true,
	modal : true,
	initComponent : function() {
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
				fieldLabel : '编号',
				xtype : 'textfield',
				name : 'id',
				hidden : true
			}, {
				fieldLabel : '风险等级',
				xtype : 'numberfield',
				name : 'levelVal',
				labelAlign : "right",
				readOnly : true,
				value : 0,
				minValue : 0
			}, {
				fieldLabel : '最小风险值',
				xtype : 'numberfield',
				name : 'levelMin',
				id : "levelMin",
				labelAlign : "right",
				regex : /^[-+]?[\d]+$/,
				nanText:'',
				regexText : '请输入有效数值!',
				step : 10,
				minValue : 0,
				vtype : 'numberrange',
                endNumberField : 'levelMax',
                listeners : {
					change : function(field, value) {
						if (isNaN(value)) {
							// 不是数字
							var e = new RegExp('e', 'g');
							var val = value.replace(e, '');
							// 置空
							field.setValue();
						}
					}
				}
			}, {
				fieldLabel : '最大风险值',
				xtype : 'numberfield',
				name : 'levelMax',
				id : 'levelMax',
				labelAlign : "right",
				regex : /^[-+]?[\d]+$/,
				nanText:'',
				regexText : '请输入有效数值!',
				step : 10,
				minValue : 0,
				// maxValue : 100,
				vtype : 'numberrange',
                startNumberField : 'levelMin',
				listeners : {
					change : function(field, value) {
						if (isNaN(value)) {
							// 不是数字
							var e = new RegExp('e', 'g');
							var val = value.replace(e, '');
							// 置空
							field.setValue();
						}
					}
				}
			}, {
				fieldLabel : '风险名称',
				xtype : 'textfield',
				labelAlign : "right",
				width : 255,
				name : 'name'
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