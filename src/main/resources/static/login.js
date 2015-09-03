// 获取登录样式
function getLoginCss() {
	// 登录页面的样式
	Ext.Ajax.request({
		url : 'loginCss/getLoginCSS',
		async : true,
		method : 'GET',
		success : function(resp) {
			var obj = Ext.JSON.decode(resp.responseText);
			if (obj.success) {
				var cssStr = obj.data;
				if (Ext.isEmpty(cssStr)) {
					// 默认值
					cssStr = 'resources/css/login_normal.css';
				}
				var head = document.getElementsByTagName('head')[0];
				var cssArr = cssStr.split(',');
				Ext.Array.each(cssArr, function(item) {
					var link = document.createElement('link');
					link.rel = 'stylesheet';
					link.type = 'text/css';
					link.href = item;
					head.appendChild(link);
			    });
			}
		},
		failure : function(resp) {
			var result = Ext.JSON.decode(resp.responseText);
			if (!result.success) {
				Ext.Msg.alert("提示", result.error);
			} else {
				Ext.MessageBox.alert('运行错误', resp.responseText);
			}
		}
	});
	// 登录页面title请求
	Ext.Ajax.request({
		url : 'loginCss/getLoginTitle',
		async : true,
		method : 'GET',
		success : function(resp) {
			var obj = Ext.JSON.decode(resp.responseText);
			if (obj.success) {
				var title = obj.data;
				if (Ext.isEmpty(title)) {
					// 默认值
					title = '风控后台管理系统';
				}
				document.title = title;
			} else {
				var title = '风控后台管理系统';
				document.title = title;
				portal.title = title;
			}
		},
		failure : function(resp) {
			var title = '风控后台管理系统';
			document.title = title;
			portal.title = title;
			var result = Ext.JSON.decode(resp.responseText);
			if (!result.success) {
				Ext.Msg.alert("提示", result.error);
			} else {
				Ext.MessageBox.alert('运行错误', resp.responseText);
			}
		}
	});
}

function doLogin() {
	var form = Ext.get("loginForm");
	var password = Ext.get("password").getValue();
	var loginName = Ext.get("loginName").getValue();
	if (Ext.isEmpty(password) || Ext.isEmpty(loginName)) {
		Ext.get("loginError").dom.style.display = "none";
		Ext.get("loginEmpty").dom.style.display = "";
		return;
	}
	var md5password = Ext.util.MD5(password);
	Ext.Ajax.request({
		url : 'login/login',
		method : 'POST',
		params : {
			loginName : loginName,
			password : md5password
		},
		success : function(response) {
			var obj = Ext.JSON.decode(response.responseText);
			if (obj.success == false) {
				Ext.get("loginEmpty").dom.style.display = "none";
				Ext.get("loginError").dom.style.display = "";
			} else {
				Ext.get("loginEmpty").dom.style.display = "none";
				Ext.get("loginError").dom.style.display = "none";
				Ext.MessageBox.alert('登录成功', '现在进行重定向，请稍后...');
				window.location = "index.html";
			}
		},
		failure : function() {
			window.location = "login.html";
		}
	});
}

// 键盘事件捕捉
document.onkeydown = function(event_e) {
	if (window.event)
		event_e = window.event;
	var int_keycode = event_e.charCode || event_e.keyCode;
	// 回车键
	if (int_keycode == 13) {
		document.getElementById("loginBtn").focus();
		var task = new Ext.util.DelayedTask(function() {
			doLogin();
		});
		task.delay(100);
	}
}