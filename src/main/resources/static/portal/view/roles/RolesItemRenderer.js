Ext.define('portal.view.roles.RolesItemRenderer', {
	extend : 'Ext.tree.Panel',
	alias : 'widget.rolesItemRenderer',
	lines : false,
	border : false,
	useArrows : true,
	rootVisible : true,
	singleExpand : false,
	listeners : {
		checkchange : function(node, checked, eOpts) {
			if (checked) {
				node.bubble(function(parentNode) {
					parentNode.set('checked', true);
				});
				node.cascadeBy(function(node) {
					node.set('checked', true);
				});
			} else {
				node.cascadeBy(function(node) {
					node.set('checked', false);
				});
				node.bubble(function(parentNode) {
					var isChecked = false;
					parentNode.eachChild(function(childNode) {
						if (childNode.get('checked'))
							isChecked = true;
					});
					parentNode.set('checked', isChecked);
				});
			}
		}
	},
	initComponent : function() {
		this.callParent(arguments);
	}
});