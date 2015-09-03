Ext.define('portal.store.util.TreeMenuStore', {
	extend: 'Ext.data.TreeStore',
    autoLoad: false,
    alias: 'widget.treeMenuStore',
    idProperty: "id",
    fields: [
        { name: 'id', type: 'int', mapping: 'id'},
        { name: 'text', type: 'string', mapping: 'text'},
        { name: 'iconCls', type: 'string', mapping: 'iconCls'},
        { name: 'qtitle', type: 'string', mapping: 'qtitle'},
        { name: 'children'}
    ],
    proxy: {
        type: 'ajax',
        url: 'login/findMenu',
        reader: {
            type: 'json',
            root: 'data',
            successProperty: 'success'
        }
    }
});