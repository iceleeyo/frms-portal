Ext.define('portal.store.util.MenuInfoModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id',
		type : 'string',
		defaultValue : null
	}, {
		name : 'text',
		type : 'string',
		defaultValue : null
	}, {
		name : 'parentId',
		type : 'string',
		defaultValue : null
	}, {
		name : 'index',
		type : 'int',
		defaultValue : null
	}, {
		name : 'depth',
		type : 'int',
		defaultValue : 0
	}, {
		name : 'expanded',
		type : 'bool',
		defaultValue : false
	}, {
		name : 'expandable',
		type : 'bool',
		defaultValue : true
	}, {
		name : 'checked',
		type : 'auto',
		mapping : 'checked',
		defaultValue : null
	}, {
		name : 'leaf',
		type : 'bool',
		defaultValue : false
	}, {
		name : 'cls',
		type : 'string',
		defaultValue : null
	}, {
		name : 'iconCls',
		type : 'string',
		defaultValue : null
	}, {
		name : 'icon',
		type : 'string',
		defaultValue : null
	}, {
		name : 'root',
		type : 'boolean',
		defaultValue : false
	}, {
		name : 'isLast',
		type : 'boolean',
		defaultValue : false
	}, {
		name : 'isFirst',
		type : 'boolean',
		defaultValue : false
	}, {
		name : 'allowDrop',
		type : 'boolean',
		defaultValue : true
	}, {
		name : 'allowDrag',
		type : 'boolean',
		defaultValue : true
	}, {
		name : 'loaded',
		type : 'boolean',
		defaultValue : false
	}, {
		name : 'loading',
		type : 'boolean',
		defaultValue : false
	}, {
		name : 'href',
		type : 'string',
		defaultValue : null
	}, {
		name : 'hrefTarget',
		type : 'string',
		defaultValue : null
	}, {
		name : 'qtip',
		type : 'string',
		defaultValue : null
	}, {
		name : 'qtitle',
		type : 'string',
		defaultValue : null
	}, {
		name : 'formid',
		type : 'string',
		defaultValue : null
	}, {
		name : 'formcode',
		type : 'string',
		defaultValue : null
	}, {
		name : 'formname',
		type : 'string',
		defaultValue : null
	}, {
		name : 'webpath',
		type : 'string',
		defaultValue : null
	}, {
		name : 'exefile',
		type : 'string',
		defaultValue : null
	}, {
		name : 'tcode',
		type : 'string',
		defaultValue : null
	}, {
		name : 'controllerName',
		type : 'string',
		defaultValue : null
	} ]
});