Ext.define('portal.store.resources.ResourcesStore', {
	extend : 'Ext.data.TreeStore',
	autoLoad : false,
	alias : 'widget.resourcesStore',
	idProperty : "id",
	fields : [ {
		name : 'id',
		type : 'int',
		mapping : 'id'
	}, {
		name : 'text',
		type : 'string',
		mapping : 'resourceName'
	}, {
		name : 'level',
		type : 'int',
		mapping : 'resourceLevel'
	}, {
		name : 'parentId',
		type : 'int',
		mapping : 'parentId'
	}, {
		name : 'iconCls',
		type : 'string',
		mapping : 'resourceIcon'
	}, {
		name : 'qtitle',
		type : 'string',
		mapping : 'resourceUrl'
	}, {
		name : 'code',
		type : 'string',
		mapping : 'resourceCode'
	}, {
		name : 'orderField',
		type : 'int',
		mapping : 'orderField'
	}, {
		name : 'type',
		type : 'int',
		mapping : 'resourceType'
	}, {
		name : 'openType',
		type : 'int',
		mapping : 'resourceDesc'
	} ],
	proxy : {
		type : 'ajax',
		url : 'resources/list',
		api : {
			update : 'resources/edit',
			read : 'resources/list',
			create : 'resources/add',
			remove : 'resources/delete'
		},
		reader : {
			type : 'json',
			root : 'data',
			successProperty : 'success'
		}
	}
});