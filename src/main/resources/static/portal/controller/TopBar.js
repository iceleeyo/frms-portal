Ext.define('portal.controller.TopBar', {
	extend : 'Ext.app.Controller',
	views : [ 'TopBar', 'account.AccountView' ],
	init : function() {
		this.control({
			'box #welcome_box' : {
				afterrender : function(view, opts) {
					var el = view.el;
					el.on("click", this.onAnchorClicked, null, {
						delegate : 'a'
					});
				}
			}
		});
	},
	
	onAnchorClicked : function(e, view, opt) {
		if (view.innerHTML.indexOf('我的账号') >= 0) {
			Ext.History.add('accountView');
		} else if (view.innerHTML.indexOf('帮助') >= 0) {
			Ext.History.add('#');
		} else if (view.innerHTML.indexOf('关于') >= 0) {
			Ext.Ajax.request({
				url : 'about/version',
				method : 'GET',
				headers : 'application/json',
				success : function(resp) {
					var text = resp.responseText;
					sessionTimeoutHandler(function() {
						var record = Ext.JSON.decode(text);
						if (!record.success) {
							Ext.MessageBox.alert('运行错误', record.error);
						} else {
							if(Ext.isEmpty(record.version)) {
								record.version = '3.0.0-SNAPSHOT';
							}
							if(Ext.isEmpty(record.build)) {
								record.build = Ext.Date.format(new Date(),'Y-m-d');
							}
							Ext.MessageBox.show({
								title : '关于本系统',
								msg : '<table align=center><tr><td><img src="resources/images/bangsun.png"></td>' + '<td>版本号：'
			                    + record.version + '<br>修订版：' + record.build + '</td></tr></table>'
			                    + '<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;邦盛电子支付业务风控后台管理系统（以下简称“本平台”）'
			                    + '由杭州邦盛金融信息技术有限公司（以下简称“杭州邦盛”）开发并独立拥有完全的知识产权，' + '任何个人及组织，未经杭州邦盛书面同意，不得对本平台进行出让、复制、编译、开发、破解，'
			                    + '以及其他侵犯杭州邦盛知识产权的行为。</p>'
			                    + '<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;杭州邦盛金融信息技术有限公司成立于2010年，致力于为国内金融机构提供高质量的金融信息服务。'
			                    + '公司依靠掌握了世界一流的金融信息系统研发技术及金融业务知识的核心团队，' + '在借鉴国内外先进的金融信息技术基础上，已经形成了一整套以金融风险监控类产品为主线的高性能金融领域解决方案，'
			                    + '服务领域囊括银行、外汇、证券、基金、固定收益、保险以及金融支付等行业。</p>' 
			                    + '<table align=center><tr align="center"><td>Copyright &copy; 2010~' 
			                    + new Date().getFullYear() + '</td></tr>'
			                    + '<tr align="center"><td>Bangsun Technology Ltd. All Rights Reserved.</td></tr>'
			                    + '<tr align="center"><td>杭州邦盛金融信息技术有限公司 版权所有</td></tr>'
			                    + '</table>'
			                    + '<p align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>',
								buttons : Ext.MessageBox.OK,
								minHeight : 520,
								width : 400
							});
						}
					});
				},
				failure : function(resp) {
					Ext.MessageBox.alert('运行错误', resp.responseText);
				}
			});
		}
	}
});