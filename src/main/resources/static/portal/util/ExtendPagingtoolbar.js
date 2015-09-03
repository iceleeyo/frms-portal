/**
 * Created by s on 15-7-9.
 */
Ext.define('portal.util.ExtendPagingtoolbar', {
    extend: 'Ext.toolbar.Paging',
    alias: 'widget.extendpagingtoolbar',
    border: true,
    displayInfo: false,
    prependButtons: true,
    width: '100%',
    cls: 'fansen-pagingtoolbar',
    afterPageText: '共{0}页,',
    beforePageText: '至第',
    items: ['->',
        {
            xtype: 'container',
            html: '页'
        },
        {
            xtype: 'button',
            text: '确定',
            listeners:{
                click:function(btn,event){
                    var store = btn.ownerCt.getStore();
                    var toPage = btn.ownerCt.items.items[4].getValue();
                    var pageCount = btn.ownerCt.getPageData().pageCount;
                    toPage = toPage>pageCount?pageCount:toPage;
                    store.loadPage(toPage);
                }
            }
        }
    ],
    initComponent: function () {
        var me = this;
        //增加一个确定按钮
        me.callParent(arguments);
        me.move(1, 7);
        me.move(1, 7);
        me.remove(me.items.items[13]);
        me.remove(me.items.items[12]);
        me.remove(me.items.items[9]);
        me.remove(me.items.items[3]);
    },
    onPagingBlur : function(e){
        //var curPage = this.getPageData().currentPage;
        //this.child('#inputItem').setValue(curPage);
    }
});