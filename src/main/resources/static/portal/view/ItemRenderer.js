Ext.define('portal.view.ItemRenderer', {
	extend : 'Ext.tree.Panel',
	alias : 'widget.itemrenderer',
	useArrows : true,
	cls : 'common-itemrenderer',
	singleExpand : false,
	rootVisible : false,
	initComponent : function() {
		this.callParent(arguments);
	}
});