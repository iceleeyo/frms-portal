Ext.define('portal.controller.resources.ResourcesController', {
    extend: 'Ext.app.Controller',
    views: ['resources.ResourcesView', 'resources.ResourcesMenuView', 
            'resources.ResourcesAddView', 'resources.ResourcesEditView'],
    stores: ['resources.ResourcesStore'],
    init: function() {
        this.control({
            'resourcesView treepanel#auth': {
                itemcontextmenu: this.showMenu
            },
            'resourcesMenuView [name=addNode]': {
                click: this.addMenu
            },
            'resourcesMenuView [name=editNode]': {
                click: this.editMenu
            },
            'resourcesMenuView [name=deleteNode]': {
                click: this.deleteMenu
            },
            'resourcesAddView button[action=add]': {
                click: this.addResource
            },
            'resourcesEditView button[action=edit]': {
                click: this.editResource
            }
        });
    },

    showMenu: function(menutree, record, items, index, e) {
        e.preventDefault();
        e.stopEvent();
        var nodemenu = Ext.widget('resourcesMenuView');
        nodemenu.record = record;
        nodemenu.showAt(e.getXY());
    },

    addMenu: function(item, e, eOpts) {
        var menu = item.up('resourcesMenuView');
        var window = Ext.widget('resourcesAddView');
        var pid = menu.record.data.id;
        var level = menu.record.data.level;
        level++;
        window.down('hiddenfield[name=level]').setValue(level);
        window.down('hiddenfield[name=parentId]').setValue(pid);
        window.down('hiddenfield[name=expand]').setValue(false);
        window.show();
    },

    editMenu: function(item, e, eOpts) {
        var menu = item.up('resourcesMenuView'),
        window = Ext.widget('resourcesEditView');

        window.down('form').loadRecord(menu.record);
        window.show();
    },

    deleteMenu: function(item, e, eOpts) {
        var tab1 = Ext.ComponentQuery.query('resourcesView')[0];
        var tab = tab1.down('treepanel');
        var store = tab.getStore('resources.ResourcesStore');
        var menu = item.up('resourcesMenuView');
        var id = menu.record.get('id');
        var node = store.getNodeById(menu.record.get('id'));
        var sucCallback = function() {
        	Ext.MessageBox.alert('删除成功', '删除成功');
        };
        var failCallback = function(batch, opts) {
        	Ext.MessageBox.alert('删除失败', store.proxy.reader.jsonData.error);
        };
        Ext.Msg.confirm('确认', '该节点下的子节点也会一并删除，你确认要删除吗？',
        function(res) {
            if (res == 'yes') {
                Ext.Ajax.request({
                    url: 'resources/delete?id=' + id,
                    method: 'POST',
                    success: function(response, options) {
                    	Ext.MessageBox.alert('删除成功', '删除成功');
                        store.load();
                    },
                    failure: function() {
                    	Ext.MessageBox.alert('删除失败', '删除失败');
                        store.load();
                    }
                });
            }
        });
    },

    addResource: function(button) {
        var win = button.up('window');
        var form = win.down('form');
        var values = form.getValues();
        var store = this.getStore('resources.ResourcesStore');
        var node = store.getNodeById(values.parentId);
        
        values.type = Ext.isEmpty(values.type) ? 0 : 1;
        values.openType = Ext.isEmpty(values.openType) ? 0 : 1;
        
        node.appendChild(values);
        var sucCallback = function(controller, fun) {
        	Ext.MessageBox.alert('添加成功', '添加成功');
            var response = controller.operations[0].response.responseText,
            	it = Ext.JSON.decode(response);
            var newNode = node.lastChild;
            newNode.setId(it.data);
            win.close();
        };
        var failCallback = function(batch, opts) {
        	Ext.MessageBox.alert('添加失败', store.proxy.reader.jsonData.error);
        };
        // 关闭窗口
        store.sync({
            success: sucCallback,
            failure: failCallback
        });
    },

    editResource: function(button) {
        var win = button.up('window'),
        me = this,
        form = win.down('form'),
        record = form.getRecord(),
        values = form.getValues();

        values.type = Ext.isEmpty(values.type) ? 0 : 1;
        values.openType = Ext.isEmpty(values.openType) ? 0 : 1;

        record.set(values);

        var store = this.getStore('resources.ResourcesStore');
        var sucCallback = function() {
        	Ext.MessageBox.alert('编辑成功', '编辑成功');
            win.close();
        };
        var failCallback = function(batch, opts) {
        	Ext.MessageBox.alert('编辑失败', store.proxy.reader.jsonData.error);
        };
        store.sync({
            success: sucCallback,
            failure: failCallback
        });
    }
});