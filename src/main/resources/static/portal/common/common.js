// IE8以下不支持trim，需要自定义
if (!String.prototype.trim) {
	String.prototype.trim = function() {
		return this.replace(/(^\s*)(\s*$)/g, "");
	};
}
// IE8以下不支持getElementsByClassName,需要自定义
if (!Element.prototype.getElementsByClassName) {
	Element.prototype.getElementsByClassName = function(cls) {
		var els = this.getElementsByTagName('*');
		var ell = els.length;
		var elements = [];
		for (var n = 0; n < ell; n++) {
			var oCls = els[n].className || '';
			if (oCls.indexOf(cls) < 0)
				continue;
			oCls = oCls.split(/\s+/);
			var oCll = oCls.length;
			for (var j = 0; j < oCll; j++) {
				if (cls == oCls[j]) {
					elements.push(els[n]);
					break;
				}
			}
		}
		return elements;
	}
}
function renderWithResource(view) {
    var items = view.query("[resourcesCode]");
	var store = Ext.getStore('portal.store.resources.ResourcesButtonStore');
	if (store.getCount() < 1) {
		store.load({
			callback : function(r, options, success) {
                Ext.Array.forEach(items, function(item) {
					if (store.findRecord('code', item.resourcesCode, 0, false, false, true) == null) {
						item.setVisible(false);
					}
				});
			}
		});
	} else {
		Ext.Array.forEach(items, function(item) {
			if (store.findRecord('code', item.resourcesCode, 0, false, false, true) == null) {
				item.setVisible(false);
			}
		});
	}
}
// 日期开始时间小于结束时间验证
Ext.apply(Ext.form.field.VTypes, {
	daterange : function(val, field) {
		var date = field.parseDate(val);
		if (!date) {
			return;
		}
		var start;
		var end;
		if (field.startDateField) {
			start = field.ownerCt.getComponent(field.startDateField);
			end = field;
		} else if (field.endDateField) {
			start = field;
			end = field.ownerCt.getComponent(field.endDateField);
		}
		if (field.startDateField && date.getTime() != start.maxValue.getTime()) {
			start.setMaxValue(date);
			start.validate();
		} else if (field.endDateField && date.getTime() != end.minValue.getTime()) {
			end.setMinValue(date);
			end.validate();
		}
		return true;
	}
});

/**
 * 主要用来修复Extjs源码的bug<br>
 * 重要:该函数是在Ext.Application前被调用,所以只能修复<br>
 * 文件加载在此之前的Extjs源码的bug!但是,一些ux下的组件<br>
 * 都是在Ext.Application之后才被加载的,这些组件的bug<br>
 * 无法在此函数被修复,请调用fixBugAfterApp来修复这些<br>
 * bug
 */
function fixBugBeforeApp() {
	// 修复tooltip在chrome下的显示bug
	delete Ext.tip.Tip.prototype.minWidth;
}

/**
 * 主要用来修复Extjs源码的bug<br>
 * 重要:适用于那些在Ext.Application被调用之后才被加载的Extjs<br>
 * 的源码bug.同时确保该函数是在待修复的源码被加载之后执行!
 */
function fixBugAfterApp() {
}

/**
 * 主要用来配置Extjs的某些功能的默认状态
 */
function extjsConfig() {
	// 默认启用文本选择功能
	Ext.override(Ext.view.Table, {
		enableTextSelection : true
	});
	// 打开Ext的动态加载机制
	Ext.Loader.setConfig({
		enabled : true
	});
	var me = this;
	// Ext.Loader.setPath('rams', 'rams');
	Ext.Ajax.on('requestcomplete', function(conn, response, options, eOpts) {
		if (response.responseText.substring(0, 8) == '<!DOCTYP') {
			Ext.Msg.show({
				title : '出错提示',
				msg : '系统出错，可能您的会话已过期，请重新登录！<br>' + '<font color="red"><b>注：</b></font>点击“确定”，系统将会自动刷新，您未保存的工作将会丢失<br>' + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;点击“取消”，取消当前操作。',
				buttons : Ext.MessageBox.OKCANCEL,
				icon : Ext.MessageBox.ERROR,
				fn : function(btn) {
					if (btn == 'ok') {
						window.location.reload(true);
					}
				}
			});
		}
	});
	// 实现样式风格可配置功能
	Ext.Ajax.request({
		url : 'cssConfig/css',
		async : false,
		method : 'GET',
		success : function(response) {
			var obj = Ext.JSON.decode(response.responseText);
			if (obj.success) {
				var cssStr = obj.data;
				if (Ext.isEmpty(cssStr)) {
					// 默认值
					cssStr = 'resources/css/portal-classic-all.css';
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
}

function portalConfig() {
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
				portal.title = title;
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

/**
 * 打开弹出窗的公用方法
 * 
 * @param cName
 *            必须的,controller的全限定路径名
 * @param vName
 *            必须的,view的别名
 * @param params
 *            额外附加的参数(参数间通过'&'隔开或者JavaScript对象)
 * @param name
 * @param features
 * @param replace
 *            以上三个参数实际就是window.open的对应参数,<br>
 *      
 */
function openWindow(cName, vName, params, name, features, replace) {
	var str = '';
	var url = 'window.html?controllerName=' + cName + '&viewName=' + vName;
	if (!Ext.isEmpty(params)) {
		if (Ext.isObject(params)) {
			str = Ext.Object.toQueryString(params, true);
		} else {
			str = params;
		}
	}
	url = Ext.String.urlAppend(url, str);
	window.open(url, name, features, replace);
}

// 关闭浏览器的当前页面
function closeWindow() {
	var browserName = navigator.appName;
	if (browserName == "Netscape") {
		self.opener = null;
		self.open('', '_self');
		self.close();
	}
	if (browserName == "Microsoft Internet Explorer") {
		window.opener = "whocares";
		window.close();
	}
}

/**
 * session过期时的处理函数
 * 
 * @param fn
 * @returns
 */
function sessionTimeoutHandler(fn) {
	try {
		return fn();
	} catch (err) {
		console.error(err.stack);
		if (err.sourceClass == 'Ext.JSON') {
			Ext.Msg.show({
				title : '出错提示',
				msg : '系统出错，可能您的会话已过期，请重新登录！<br>' + '<font color="red"><b>注：</b></font>点击“确定”，系统将会自动刷新，您未保存的工作将会丢失<br>' + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;点击“取消”，取消当前操作。',
				buttons : Ext.MessageBox.OKCANCEL,
				icon : Ext.MessageBox.ERROR,
				fn : function(btn) {
					if (btn == 'ok') {
						window.location.reload(true);
					}
				}
			});
		}
	}
}

Ext.override(Ext.data.proxy.Ajax, {
	doRequest : function(operation, callback, scope) {
		var writer = this.getWriter(), request = this.buildRequest(operation, callback, scope);
		if (operation.allowWrite()) {
			request = writer.write(request);
		}

		Ext.apply(request, {
			headers : this.headers,
			timeout : this.timeout,
			scope : this,
			callback : this.createRequestCallback(request, operation, callback, scope),
			method : this.getMethod(request),
			disableCaching : false
		});
		
		if (this.jsonData && operation.action == 'read') {
			request.jsonData = Ext.encode(request.params);
			delete request.params;
		}
		
		Ext.Ajax.request(request);
		return request;
	}
});