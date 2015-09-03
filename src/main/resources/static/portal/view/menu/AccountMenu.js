Ext.define('portal.view.menu.AccountMenu', {
	extend : 'Ext.menu.Menu',
	alias : 'widget.accountMenu',
	floating : true,
	width : 140,
	items : [ {
		text : '我的账号',
		handler : function(btn) {
			Ext.History.add('accountView');
		}
	}, {
		text : '帮助',
		handler : function(btn) {
			Ext.History.add('#');
		}
	}, {
		text : '关于',
		handler : function(btn) {
			Ext.MessageBox.show({
				title : '关于本系统',
				msg : '<table align=center><tr><td><img src="resources/images/bangsun.png"></td>' + '<td>版本号：'
				+ '2.3.0' + '<br>修订版：' + '' + '</td></tr></table>'
				+ '<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;邦盛电子支付业务风险实时监控系统规则管理平台（以下简称“本平台”）'
				+ '由杭州邦盛金融信息技术有限公司（以下简称“杭州邦盛”）开发并独立拥有完全的知识产权，' + '任何个人及组织，未经杭州邦盛书面同意，不得对本平台进行出让、复制、编译、开发、破解，'
				+ '以及其他侵犯杭州邦盛知识产权的行为。</p>'
				+ '<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;杭州邦盛金融信息技术有限公司成立于2010年，致力于为国内金融机构提供高质量的金融信息服务。'
				+ '公司依靠掌握了世界一流的金融信息系统研发技术及金融业务知识的核心团队，' + '在借鉴国内外先进的金融信息技术基础上，已经形成了一整套以金融风险监控类产品为主线的高性能金融领域解决方案，'
				+ '服务领域囊括银行、外汇、证券、基金、固定收益、保险以及金融支付等行业。</p>' 
				+ '<p align=center>Copyright &copy; 2010~'+ new Date().getFullYear() + '<br>Bangsun Technology Ltd. All Rights Reserved.'
				+ '<br>杭州邦盛金融信息技术有限公司 版权所有</p>',
				buttons : Ext.MessageBox.OK,
				width : 450,
				height : 500
			});
		}
	}, {
		text : '退出',
		handler : function(btn) {
			window.location = 'login/logout';
		}
	} ],
	initComponent : function() {
		this.callParent();
	}
});