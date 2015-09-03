/**
 * created by zqq
 * 
 * 该组件扩展了EXTJS组件textfield!
 * 使用该组件不但易于扩展新功能而且能够即时对所有继承该组件的类生效,同时
 * 新功能都是可以设置默认开或关,即使继承的组件不想使用默认为开的新功能也
 * 只需设置一行代码就可以关闭!
 */
/**
 * 已扩展功能: <br>
 * 1.自带重置按钮,默认开启,通过{hideTrigger : Boolean}配置 <br>
 * 2.allowBlank=false时自动显示红色星号,默认开启,通过{showRedStar : Boolean}配置 <br>
 */
Ext.define('portal.util.form.field.ExtendText', {
	extend : 'Ext.form.field.Trigger',
	alias : 'widget.extendtextfield',
	triggerCls : 'x-form-clear-trigger',
	onTriggerClick : function() {
		this.reset();
	},
	showRedStar :true,
	initComponent : function() {
		var me = this;
		if (me.showRedStar == true && me.allowBlank == false) {
			if (me.fieldLabel) {
				me.fieldLabel += '<b style="color:red">*</b>';
			}
		}
		me.callParent(arguments);
	}
});