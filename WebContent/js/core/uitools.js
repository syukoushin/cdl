var Colorbox = {// 遮罩工具类
	showGlobal : function() {// 遮罩整个页面
		var params = arguments[0];//接受自定义参数
		$("#BgDiv").css({
			top : "0px",
			left : "0px",
			display : "block",
			filter: "alpha(opacity = 70)",
			opacity: 0.7,
			height : $(document).height(),
			width : $(window).width()
		});
		if(params){
			$("#BgDiv").css(params);
		}
	},

	showWithElement : function(elementID) {// 遮罩某页面节点
		$("#" + elementID).css({
			top : $("#" + elementID).position().top,
			left : $("#" + elementID).position().left,
			display : "block",
			height : $("#" + elementID).height(),
			width : $("#" + elementID).width()
		});
	},

	hidden : function() {
		$("#BgDiv").css("display", "none");
	}
}; 

var Scrollbox = {//滚动工具类
	/**
	 * 初始化滚动列表、绑定滚动method
	 * 参数1：应用根路径
	 * 参数2：指定调用控件id
	 * 参数3：点击链接触发函数名称
	*/
	init : function(basepath,nodeId,handle) {
		var _self = this;
		AjaxCommonTools("",basepath+"/affiche/obtainLatestAffiches.action?a="+Math.random(),"json",true,function(data){ 
			var divNode = _self.createNode(data,handle);
			$("#"+nodeId).append(divNode); 
			//绑定滚动method
			global_affiche_str = _self.scrollShow("#newsId", "global_affiche_str", 4000, 0, basepath);
		});
	},
	//创建div
	createNode : function(affiches,handle) {
		if (affiches.length > 0) {
			var div = document.createElement("div");
			div.id = "newsId"; 
			div.className = "news";
			var ul = document.createElement("ul");
			var len = affiches.length;//减少查询次数，提升性能
			for (var i=0; i<len; i++) {
				var tempAffiche = affiches[i];
				var li = document.createElement("li");
				var html = '<a href=javascript:'+handle+'("'+tempAffiche.id+'")>'+tempAffiche.title+'</a>';
				li.innerHTML = li.innerHTML + html;
				ul.appendChild(li);
			}
			div.appendChild(ul);
			var ol = document.createElement("ol");
			ol.className = "activeOL";
			div.appendChild(ol);
			return div;
		}
		return null;
	},
	/**
	 * 参数1：ID   
	 * 参数2：实例化对象的名称（和var后边定义的名称相同）  
	 * 参数3：间隔时间
	 * 参数4：初始化时，默认哪个先显示
	 * 参数5：应用根路径
	 */
	scrollShow : function (container,handle,time,StartIndex,basepath){
		var _self=this;
		this.container=container;
		this.handle=handle;
		this.time = time;
		this.i=StartIndex||0;
		this.Count=$(this.container+" ul li").length;
		$(this.container+" ul li").hide();//全部隐藏
		$(this.container+" ul li").eq(this.i).show();//第i个显示
		$(this.container).bind("mouseenter",function(){
			if(_self.sI){clearInterval(_self.sI);}
		}).bind("mouseleave",function(){
			_self.showIndex(_self.i++,'event');
		});
		/*生成激活OL项目*/
		for(var j=0;j<this.Count;j++)
			$(this.container+" .activeOL").append('<li><a onclick="'+this.handle+'.showIndex('+j+');return false;" href="#"><img src="'+basepath+'/images/affiche/crystal.gif"></a></li>');
		$(this.container+" ol li a").eq(this.i).addClass("active");
		this.sI=setInterval(this.handle+".showIndex(null)",this.time);
		
		this.showIndex = function(index){
			this.i++;//显示下一个
			if(this.sI){clearInterval(this.sI);}
			this.sI=setInterval(this.handle+".showIndex()",this.time);
			if (index!=null)
				this.i=index;
			if(this.i==this.Count)
				this.i=0;
			if(arguments[1] && arguments[1]  == 'event'){
			    $(this.container+" ol li a").removeClass("active");
				$(this.container+" ol li a").eq(this.i).addClass("active");
			    return false;
			}
			$(this.container+" ul li").hide();
			$(this.container+" ul li").eq(this.i).slideDown(1000);
			$(this.container+" ol li a").removeClass("active");
			$(this.container+" ol li a").eq(this.i).addClass("active");
		};
		return _self;
	}
};

/**
 * DOM滑动工具类
 */
var SlideBox = {
	slideUp : function(id){//DOM往上滑动动画效果
		$("#"+id).slideUp("slow");
	}
};

var AjaxLoadBar = {//门户Ajax请求过程提示
	show : function (){
		$(".loadBar").show();
	},
	hide : function (){
		$(".loadBar").hide();
	}
};

var DraggBox = {
	
	drageDom : function(){//拖拽DOM
		var domId = arguments[0];
		$("#"+domId).draggable();
	},
	
	//拖拽DOM在容器中，containment其中有三个参数可以选择1.parent,2.window,3.document,必须是此三个参数中的一个。
	drageDomInSome : function(){
		var domId = arguments[0];
		var inwhere = arguments[1];
		$("#"+domId).draggable({ containment:inwhere});
	},
	
	enableDrage : function(){//激活拖拽事件
		var domId = arguments[0];
		$("#"+domId).draggable("enable");
	},
	
	disableDrag : function(){//去除拖拽事件
		var domId = arguments[0];
		$("#"+domId).draggable("disable");
	}
};

var AjaxLoadBar = {//门户Ajax请求过程提示
	show : function (){
		$(".loadBar").show();
	},
	hide : function (){
		$(".loadBar").hide();
	}
};

/*
 * Cookie操作类
 */
var CookieTools = {
		
	/**
	 * 根据名称获取Cookie
	 */
	getCookie : function (name){
		var arg = name + "=";
		var alen = arg.length;
		var clen = document.cookie.length;
		var i = 0;
		while (i < clen) {
			var j = i + alen;
			if (document.cookie.substring(i, j) == arg)
				return this.getCookieVal(j);
			i = document.cookie.indexOf(" ", i) + 1;
			if (i == 0)
				break;
		}
		return null;
	},
	/**
	 * 获得Cookie值，辅助GetCookie方法
	 */
	getCookieVal : function (offset){
		var endstr = document.cookie.indexOf(";", offset);
		if (endstr == -1)
			endstr = document.cookie.length;
		return document.cookie.substring(offset, endstr);
	},
	
	/**
	 * 设置Cookie
	 */
	setCookie : function(name, value){
		var expdate = new Date();
		var argv = arguments;
		var argc = arguments.length;
		var expires = (argc > 2) ? argv[2] : null;
		var path = (argc > 3) ? argv[3] : null;
		var domain = (argc > 4) ? argv[4] : null;
		var secure = (argc > 5) ? argv[5] : false;
		if (expires != null)
			expdate.setTime(expdate.getTime() + (expires * 1000));
		document.cookie = name
				+ "="
				+ escape(value)
				+ ((expires == null) ? "" : ("; expires=" + expdate
						.toGMTString()))
				+ ((path == null) ? "" : ("; path=" + path))
				+ ((domain == null) ? "" : ("; domain=" + domain))
				+ ((secure == true) ? "; secure" : "");
	},
	
	/**
	 * 删除Cookie
	 * @param name
	 * @returns
	 */
	delCookie : function(name){
		var exp = new Date();
		exp.setTime(exp.getTime() - 1);
		var cval = this.getCookie(name);
		document.cookie = name + "=" + cval + "; expires=" + exp.toGMTString();
	}

};


