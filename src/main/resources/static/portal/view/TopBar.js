Ext.define('portal.view.TopBar', {
	extend : 'Ext.container.Container',
    alias : 'widget.topbar',
    requires : [ 'portal.loader.LoginLoader' ],
    layout : 'border',
    style : 'background-image: url(resources/images/topbar_bg.png);background-repeat:repeat-x;',
    initComponent : function() {
    	this.items = [ {
    		xtype : 'container',
            region : 'center',
            html : '<div><table height="100%" align="left" valign="center" cellspacing="0" cellpadding="0"><tr style="margin:0;">'
            	+ '<td><img src="resources/images/logo.png" height="50" width="240"></td><td valign="center"><span style="font-weight:bold;font-size:24px;"><font color=white>'
                + '&nbsp;&nbsp;' + portal.title + '</font></span></td></tr></table></div>'
        	}, {
        		xtype : 'container',
                region : 'east',
                width : 480,
                layout : {
                	type : 'vbox',
                    pack : 'bottom'
                },
                items : [ {
                	xtype : 'container',
                    height : 16
                }, {
                	xtype : 'box',
                    id : 'welcome_box',
                    width : '100%',
                    tpl : '<p><font color="white">欢迎回来, <b>{userId}</b>, [登录号:<b id="userId">{userId}</b>, <b>{title}</b>]</font>'
                    	+ '<font color=white>&nbsp;|&nbsp;</font><a href="javascript:void(0);">'
                    	+ '<font color="white">我的账号</font></a>'
                        + '<font color=white>&nbsp;|&nbsp;</font><a href="javascript:void(0);">'
                        + '<font color="white">关于</font></a>'
                        + '<font color=white>&nbsp;|&nbsp;</font><a href="login/logout">'
                        + '<font color="white">退出</font></a></p>',
                    loader : Ext.create('portal.loader.LoginLoader'),
                    style : 'padding:3px;position: absolute; bottom: 0; right: 0; text-align:right;'
                } ]
        	} ];
    	this.callParent(arguments);
    }
});