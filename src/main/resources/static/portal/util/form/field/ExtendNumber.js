/**
 * created by zqq
 * 
 * 该组件扩展了EXTJS组件numberfield!
 * 使用该组件不但易于扩展新功能而且能够即时对所有继承该组件的类生效,同时
 * 新功能都是可以设置默认开或关,即使继承的组件不想使用默认为开的新功能也
 * 只需设置一行代码就可以关闭!
 */
/**
 * 已扩展功能: <br>
 * 1.allowBlank=false时自动显示红色星号,默认开启,通过{showRedStar : Boolean}配置 <br>
 * 2.默认禁用指数输入功能,该功能不是很实用,而且经常给人造成可输入字母的错觉,通过{allowExponential : Boolean}配置 <br>
 */
Ext.define('portal.util.form.field.ExtendNumber', {
	extend : 'Ext.form.field.Number',
	alias : 'widget.extendnumberfield',
	showRedStar : true,
	allowExponential : false,
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