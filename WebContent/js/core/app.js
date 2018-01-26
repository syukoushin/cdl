~function(window, undefined) {
	Actions = {
		imgPath : "http://" + js_domain1 + "/ppp/blog/styles_ppp/images/",
		themePath : "undefined" != typeof ThP ? ThP : "http://" + js_domain2
				+ "/ppp/blog/themes_ppp/",
		jsPath : "undefined" != typeof SP ? SP : "http://" + js_domain3
				+ "/ppp/blog/js_ppp/",
		flashPath : "http://js.pp.sohu.com/ppp/blog/flash/",
		widgetLibPath : "http://js.pp.sohu.com"
				+ ("undefined" != typeof WP ? WP : "/ppp/blog/widgets/"),
		widgetLibPath1 : "http://" + js_domain1
				+ ("undefined" != typeof WP ? WP : "/ppp/blog/widgets/"),
		widgetLibPath2 : "http://" + js_domain2
				+ ("undefined" != typeof WP ? WP : "/ppp/blog/widgets/"),
		widgetLibPath3 : "http://" + js_domain3
				+ ("undefined" != typeof WP ? WP : "/ppp/blog/widgets/"),
		widgetLibPath4 : "http://" + js_domain4
				+ ("undefined" != typeof WP ? WP : "/ppp/blog/widgets/"),
		widgetLibPath5 : "http://" + js_domain5
				+ ("undefined" != typeof WP ? WP : "/ppp/blog/widgets/"),
		widgetLibPathCR : "http://" + js_domain5
				+ ("undefined" != typeof WP ? WP : "/ppp/blog/widgets/"),
		customTheme : "/manage/style.do",
		userData : "/action/",
		newMod : "/manage/module.do",
		delMod : "/manage/module.do",
		editMod : "/manage/module.do",
		newPage : "/manage/page.do",
		delPage : "/manage/page.do",
		editPage : "/manage/page.do",
		editUserStyle : "/manage/module.do",
		theme : "/manage/theme.do",
		linkMng : "/manage/link.do",
		proxyURL : "/hp"    
	};
	AppConfig = {
		version : "0.2",
		theme : "undefined" != typeof _theme ? _theme : "default",
		defaultColStyle : "33:33:33"
	};
	AppConfig.Widgets={
		registered:[],
		register:function(e){
			this.hasRegistered(e.type)||this.registered.push(e);
		},
		hasRegistered:function(e){
			return this.registered.find(function(t){return t.type==e;})?!0:!1;
		},
		unregister:function(e){
			this.registered=this.registered.reject(function(t){return t.type==e;});
		},
		getWidget:function(e){
			return this.registered.find(function(t){return t.type==e;});
		}
	};
	AppConfig.Lang={
		save:"保存",
		close:"关闭",
		del:"删除",
		add:"添加",
		apply:"应用",
		cancel:"取消",
		setBtn:"设置",
		closeSetBtn:"关闭设置",
		editBtn:"编辑",
		delModuleBtnTitle:"删除该模块",
		hideModuleBtnTitle:"隐藏该模块",
		confirmHideModule:"是否确认隐藏该模块？\n\n提示：该模块为系统模块，以后您可以通过“个性化”-“添加模块”来再次添加该模块。其中的数据不会丢失。",
		confirmDelModule:"是否确认删除该模块？\n\n警告：您正在删除一个自定义模块，他里面的数据将同时删除！\n\n是否确认此操作？",
		showHideModuleBtnTitle:"展开/关闭该模块",
		refreshModuleBtnTitle:"刷新",
		loading:"读取中...",
		saved:"已保存",
		loadUserModule:"正在读取用户信息...",
		createAllModules:"正在创建模块...",
		loadWidget:"请稍候，正在读取...",
		widgetError:"该模块已过期，请点击模块右上角删除模块。",
		loadTimeout:"连接超时。",
		reloadPage:"请尝试重新刷新页面。",
		loadWidgetTimeout:"读取widget时，长时间没有响应。widget：",
		createModule:"正在创建模块...",
		createAllPages:"正在创建页面...",
		loadModuleData:"请稍候，正在读取数据...",
		newModule:"添加模块",
		newWidget:"新模块",
		hasNewWidget:"有新模块",
		moreMod:'<a href="http://ow.blog.sohu.com/category/0" target="_blank">更多模块&gt;&gt;</a>',
		moreTheme:'<a href="http://ow.blog.sohu.com/zone/0" target="_blank">更多主题&gt;&gt;</a>',
		setColStyle:"设置版式",
		setTheme:"设置主题",
		expandAll:"展开全部",
		collapseAll:"折叠全部",
		addToPage:"添加到我的页面中",
		alreadyInUse:" 已添加 ",
		alreadyInUseSign:"√",
		onlyInPrivatePage:" 该模块只能在私有页面中才可添加使用 ",
		sysMod:"系统模块",
		noWidget:"暂时没有模块",
		other:"其他",
		individuate:"个性化",
		pageWidth:"页面宽度",
		pageWidth1:"窄(适合800×600分辨率)",
		pageWidth2:"宽(适合1024×768分辨率)",
		pageLayout:"版式",
		sysThemes:"默认主题",
		newTheme:"新主题",
		hasNewTheme:"有新主题",
		greenWayThemes:"主题绿色通道",
		customHeader:"头图",
		selectImage:"选择图片",
		useThemeDefaultHeaderImg:"使用主题默认的头图",
		hideHeaderImg:"隐藏头图",
		useMyHeaderImg:"使用自己上传的头图",
		upload:"上传",
		imageOption:"图片设置",
		align:"对齐",
		def:"默认",
		horizontal:"水平",
		vertical:"垂直",
		left:"左",
		center:"中",
		right:"右",
		top:"上",
		middle:"中",
		bottom:"下",
		tiled:"平铺",  
		noRepeat:"不平铺" 
	};
	function registerWidget(w) {
		if(!AppConfig.Widgets[w])
			AppConfig.Widgets[w] = eval(w);
	}
	var js_domain1="js1.pp.sohu.com.cn";
	var js_domain2="js2.pp.sohu.com.cn";
	var js_domain3="js3.pp.sohu.com.cn"; 
	var js_domain4="js4.pp.sohu.com.cn";
	var js_domain5="js5.pp.sohu.com.cn";
	window.AppConfig = AppConfig;
	window.registerWidget = registerWidget;
}(this);
