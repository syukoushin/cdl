/*
 *对象中的initialize方法为类的初始化方法，
 *如果采用Class创建，那么在构造对象的时候就会自动调用initialize
 */
var Class = {
	create: function () {
		return function () {
			this.initialize.apply(this, arguments);//构造方法，默认执行initialize
		};
	}
};

//转换数组
var $ToArray = function (a) {
	return a ? Array.apply(null, a) : new Array;
};

//ID选择获取对象
var $me = function (id) {
	return document.getElementById(id);
};

//追加方法
Object.extend = function (a, b) {
	for (var i in b) a[i] = b[i];
	return a;
};

//添加函数
Object.extend(Object, {//为Widget添加相应的事件
	addEvent : function (a, b, c, d) {
		if (a.attachEvent) a.attachEvent(b[0], c);
		else a.addEventListener(b[1] || b[0].replace(/^on/, ""), c, d || false);
		return c;
	},
	
	delEvent : function (a, b, c, d) {//删除Widget后，删除相关事件
		if (a.detachEvent) a.detachEvent(b[0], c);
		else a.removeEventListener(b[1] || b[0].replace(/^on/, ""), c, d || false);
		return c;
	},
	
	reEvent : function () {	//获取Event
		return window.event ? window.event : (function (o) {
			do {
				o = o.caller;
			} while (o && !/^\[object[ A-Za-z]*Event\]$/.test(o.arguments[0]));
			return o.arguments[0];
		})(this.reEvent);
	}
});

//为方法定义绑定事件
Function.prototype.bind = function () {
	var self = this, a = $ToArray(arguments), o = a.shift();
	return function () {
		self.apply(o, a.concat($ToArray(arguments)));
	};
};

var Drag = Class.create();

Drag.BrowserVersion = $.browser.version;//获得浏览器的版本

Drag.IE = $.browser.msie && (Drag.BrowserVersion != 9);//得到浏览器类型，其中IE9特殊处理，内核与火狐相近，所以不能作为IE处理

Drag.ReduceObject = {isGlobalClick: false,globalWidgetStatus : 1};//声明页面中所有widget的当前状态对象

Drag.Table = Class.create();

Drag.Table.prototype = {
	//初始化
	initialize : function () {	
		var self = this;
		self.items = []; //创建列组
	},
	//添加列
	add : function () {
		var self = this, id = self.items.length, arg = arguments;
		return self.items[id] = new Drag.Table.Cols(id, self, arg[0]);
	}
};

Drag.Table.Cols = Class.create();

Drag.Table.Cols.prototype = {
	initialize : function (id, parent, element) {//初始化
		var self = this;
		self.items = []; //创建列组
		self.id = id;
		self.parent = parent;
		self.element = element;
	},
	
	add : function () {//添加行
		var self = this, id = self.items.length, arg = arguments;
		return self.items[id] = new Drag.Table.Rows(id, self, arg[0], arg[1]);
	},
	
	ins : function (num, row) {//插入行
		var self = this, items = self.items, i;
		if (row.parent == self && row.id < num) num --; //同列向下移动的时候
		for (i = num ; i < items.length ; i ++) items[i].id ++;
		items.splice(num, 0, row);
		row.id = num, row.parent = self;
		return row;
	},
	
	del : function (num) {//删除行
		var self = this, items = self.items, i;
		if (num >= items.length) return;
		for (i = num + 1; i < items.length ; i++) items[i].id = i - 1;
		return items.splice(num, 1)[0];
	}
};

Drag.Table.Rows = Class.create();

//每一个Widget对象
Drag.Table.Rows.prototype = {
	initialize : function (id, parent, element, status) {//初始化
		var self = this, temp;
		self.id = id;
		self.parent = parent;
		self.root_id = element;
		self.status = status;
		self.element = self.elementInit();
		temp = self.element.childNodes[Drag.IE ? 0 : 1];
		temp = temp.childNodes[0];
		self.widgetHeader = temp;
		self.title = temp.childNodes[0];
		self.close = temp.childNodes[1];
		self.edit = temp.childNodes[2];   
		self.reduce = temp.childNodes[3];
		self.refresh = temp.childNodes[4];
		self.widget = temp.parentNode.parentNode;
		self.content = self.element.childNodes[Drag.IE ? 2 : 5];
		self.editContent = self.element.childNodes[Drag.IE ? 1 : 3];
		self.refreshFunc = self.editFunc = self.mouseover = self.mouseout = self.mousedown = self.reduceFunc = self.closeFunc = null;
		self.load();
	},
	
	elementInit : function () {//初始化元素，并放入到列容器里面
		var self = this, div = $me("template").cloneNode(true);
		self.parent.element.appendChild(div);
		div.style.display = "block";
		return div;
	},
	
	load : function () {//加载信息
		var self = this, info = App.parseModules(self.root_id);
		self.widget.id = info.id;
		self.type = info.type;
		self.title.innerHTML = "<img src='"+basePath+info.path+info.icon+"' class='icon' width='16' height='16' />"+info.title;
		if(info.color != 'default'){
			$(self.widget).attr("className","module " + info.color);
		}
		if(info.type == "common"){
			var htmlStr = '<iframe allowTransparency=\'true\' id=\''+info.id+'_iframe\' style="border-top-width: 0px;'+
	              ' border-right-width: 0px; '+
				  ' border-bottom-width: 0px; '+
				  ' border-left-width: 0px; '+
				  ' border-style: initial; '+
				  ' border-color: initial; '+
				  ' padding-top: 0px; '+
				  ' padding-right: 0px; '+
				  ' padding-bottom: 0px; '+
				  ' padding-left: 0px; '+
				  ' margin-top: 0px; '+
				  ' margin-right: 0px; '+
				  ' margin-bottom: 0px; '+
				  ' margin-left: 0px; '+
				  ' width: 100%; '+
				  ' overflow-x: hidden; '+
				  ' overflow-y: hidden; '+
				  ' height: '+info.height+'px;" '+
				  ' frameborder="0" '+
				  ' scrolling="no" '+
				  ' src="'+basePath+info.path+info.index+'?widgetId='+info.id+'" >'+
				  ' </iframe>';
			self.content.innerHTML = htmlStr;
		}else if(info.type == "smart"){
			self.content.innerHTML = "请稍候，正在读取...";
			loadcss(basePath+info.path+"style.css", "utf-8");
			loadjs(basePath+info.path+"widget.js",function(){
				$(self.content).attr("className",self.content.className+ " "+info.index+"-content" );
				if(typeof info.data == 'string' && info.data != ''){
					try{
						info.data = JSON.parse(info.data);
					}catch(e){
						alert("widget实例data数据为非法json格式，解析出错");
					}
				}
				self.initWidget = new AppConfig.Widgets[info.index](info.data,self.content,self.editContent,basePath+info.path);
			    self.initWidget.save = function(){
			    	var data = arguments[0];
			    	var params = "tabWidgetsId="+self.root_id+"&data="+JSON.stringify(data);
			    	AjaxCommonTools(params,basePath+"/portalScene/updateWidgetData.action","json",true,function(){info.data = data;self.initWidget.endSave();});
		    	};
				self.initWidget.initialize();
			}, "utf-8");
		}else{
			self.content.innerHTML = info.data;
		}
		if (self.status == 0) {
			self.content.style.display = "none";
			self.reduce.innerHTML = "<img title='展开' style='margin-top:3px;' src='"+basePath+"/images/ico_hideMod.gif' />";
		} else {
			self.content.style.display = "block";
			self.reduce.innerHTML = "<img title='收起' style='margin-top:3px;' src='"+basePath+"/images/ico_showMod.gif' />";
		}
		self.content.style.display = (self.status == 0 ? "none" : "block");
	}
};

Drag.prototype = {
	initialize : function () {//初始化成员
		var self = this;
		self.table = new Drag.Table; //建立表格对象
		self.iFunc = self.eFunc = null;
		self.obj = { on : { a : null, b : "" }, row : null, left : 0, top : 0 };
		self.temp = { row : null, div : document.createElement("div") };
		$(self.temp.div).attr("className","drag_temp_div");
		self.temp.div.innerHTML = "&nbsp;";
	},
	
	reMouse : function (a) {//获取鼠标位置
		var e = Object.reEvent();
		return {
			x : document.body.scrollLeft + document.documentElement.scrollLeft + e.clientX,
			y : document.body.scrollTop + document.documentElement.scrollTop + e.clientY
		};
	},
	
	rePosition : function (o) {//获取元素绝对位置
		var $x = $y = 0;
		do {
			$x += o.offsetLeft;
			$y += o.offsetTop;
		} while ((o = o.offsetParent)); // && o.tagName != "BODY"
		return { x : $x, y : $y };
	},
	
	startMove : function (o , evt) {//当拖动开始时设置参数
		
		var e=(evt)?evt:window.event; //判断浏览器的类型，在基于ie内核的浏览器中的使用cancelBubble
		if (window.event) { 
			e.cancelBubble = true; 
		} else { 
			e.preventDefault(); //在基于firefox内核的浏览器中支持做法stopPropagation
			e.stopPropagation(); 
		} 

		var self = this;
		if (self.iFunc || self.eFinc) return;
		var mouse = self.reMouse(), obj = self.obj, temp = self.temp, div = o.element, position = self.rePosition(div);
		obj.row = o;
		obj.on.b = "me";
		obj.left = mouse.x - position.x;
		obj.top = mouse.y - position.y;
		temp.row = document.body.appendChild(div.cloneNode(true)); //复制预拖拽对象
		if(Drag.IE){
			temp.row.childNodes[temp.row.childNodes.length-1].innerHTML = '移动中...';
		}else{
			temp.row.childNodes[temp.row.childNodes.length-2].innerHTML = '移动中...';
		}
		with (temp.row.style) {//设置复制对象
			position = "absolute";
			left = mouse.x - obj.left + "px";
			top = mouse.y - obj.top + "px";
			zIndex = 100;
			opacity = "0.5";
			width = $(div).width()+"px";
			filter = "alpha(opacity:50)";
		}
		with (temp.div.style) {//设置拖动时占位对象的宽度和高度
			height = $(div).height()+"px";
			width = "100%";
		}
		div.parentNode.replaceChild(temp.div, div);
		self.iFunc = Object.addEvent(document, ["onmousemove"], self.inMove.bind(self));
		self.eFunc = Object.addEvent(document, ["onmouseup"], self.releaseMove.bind(self));
		document.onselectstart = new Function("return false");
	},
	
	inMove : function () {//当鼠标移动时设置参数
		Colorbox.showGlobal({
			filter: "alpha(opacity = 0)",
			cursor: "move",
			opacity: 0
		});//整屏遮罩效果，此处为解决iframe的卡屏效果
		var self = this, mouse = self.reMouse(), cols = self.table.items, obj = self.obj, temp = self.temp,
			row = obj.row, div = temp.row, t_position, t_cols, t_rows, i, j;
		with (div.style) {
			left = mouse.x - obj.left + "px";
			top = mouse.y - obj.top + "px";
		}
		for (i = 0 ; i < cols.length ; i ++) {
			t_cols = cols[i];
			t_position = self.rePosition(t_cols.element);
			if (t_position.x < mouse.x && (t_position.x + t_cols.element.offsetWidth) > mouse.x) {
				if (t_cols.items.length > 0) { //如果此列行数大于0
					if (self.rePosition(t_cols.items[0].element).y + 20 > mouse.y) {
						//如果鼠标位置大于第一行的位置即是最上。。
						obj.on.a = t_cols.items[0];
						obj.on.b = "up";
						obj.on.a.element.parentNode.insertBefore(temp.div, obj.on.a.element);
					} else if (t_cols.items.length > 1 && t_cols.items[0] == row &&
						self.rePosition(t_cols.items[1].element).y + 20 > mouse.y) {
						//如果第一行是拖拽对象而第鼠标大于第二行位置则，没有动。。
						obj.on.b = "me";
						t_cols.items[1].element.parentNode.insertBefore(temp.div, t_cols.items[1].element);
					} else {
						for (j = t_cols.items.length - 1 ; j > -1 ; j --) {
							//重最下行向上查询
							t_rows = t_cols.items[j];
							if (t_rows == obj.row) {
								if (t_cols.items.length == 1) {
									t_cols.element.appendChild(temp.div);
									obj.on.b = "me";
								}
								continue;
							}
							if (self.rePosition(t_rows.element).y < mouse.y) {
								//如果鼠标大于这行则在这行下面
								if (t_rows.id + 1 < t_cols.items.length && t_cols.items[t_rows.id + 1] != obj.row) {
									//如果这行有下一行则重这行下一行的上面插入
									t_cols.items[t_rows.id + 1].element.parentNode.
										insertBefore(temp.div, t_cols.items[t_rows.id + 1].element);
									obj.on.a = t_rows;
									obj.on.b = "down";
								} else if (t_rows.id + 2 < t_cols.items.length) {
									//如果这行下一行是拖拽对象则插入到下两行，即拖拽对象返回原位
									t_cols.items[t_rows.id + 2].element.parentNode.
										insertBefore(temp.div, t_cols.items[t_rows.id + 2].element);
									obj.on.b = "me";
								} else {
									//前面都没有满足则放在最低行
									t_rows.element.parentNode.appendChild(temp.div);
									obj.on.a = t_rows;
									obj.on.b = "down";
								}
								return;
							}
						}
					}
				} else {
					//此列无内容添加新行
					t_cols.element.appendChild(temp.div);
					obj.on.a = t_cols;
					obj.on.b = "new";
				}
			}
		}
	},
	
	releaseMove : function () {//当鼠标释放时设置参数
		Colorbox.hidden();//取消遮罩效果，与拖动时设置遮罩效果对应
		var self = this, obj = self.obj, temp = self.temp, row = obj.row, div = row.element, o_cols, n_cols = null, number;
		if (obj.on.b != "me") {
			number = (obj.on.b == "down" ? obj.on.a.id + 1 : 0);
			n_cols = (obj.on.b != "new" ? obj.on.a.parent : obj.on.a);
			o_cols = obj.row.parent;
			n_cols.ins(number, o_cols.del(obj.row.id));
		}
		
		if (obj.on.b != "me") {
			var currentColumnIndex = n_cols.id;//当前列号
			var rowId = row.id;//行号
			var currentColumn = self.table.items[currentColumnIndex].items;//获取当前列号对应的行数组列表
			var currentColumnSize = currentColumn.length;//当前列的row数量
			var widgetObj = App.parseModules(currentColumn[rowId].widget.id);//获取tabwidget对象的主键
			var _tab = TabTools.getSelectedTab();//获取当前选中的tab对象
            var mods = _tab.mods;
            var moveInSameColumn = widgetObj.col == (currentColumnIndex+1);//判断是否同列移动
            
			var oldWidgetCol = widgetObj.col;
			widgetObj.col = currentColumnIndex+1;
			if(currentColumnSize==1){//数组为一个，那么就是第一个值，取row1000
				widgetObj.row = 1000;
				var newModsItems = mods[currentColumnIndex];//目的数组
				var oldModsItems = mods[oldWidgetCol-1];//原数组
				var position = jQuery.inArray(widgetObj.id, oldModsItems);  //返回这个widget在原数组中的位置
				oldModsItems.splice(position,1);//删除原数组中的值
				newModsItems.push(widgetObj.id);//添加到新数组中
			}else if (rowId == 0){//数组大于一个，并且移动位置为置顶的时候，取row为原第一个row值的一半
				var tempWidget = App.parseModules(currentColumn[1].widget.id);
				widgetObj.row = tempWidget.row/2;
				if(moveInSameColumn){
					var modsItems = mods[currentColumnIndex];//原数组和目的数组为同一数组
					var movePosition = jQuery.inArray(widgetObj.id, modsItems);  //返回这个widget在原数组中的位置
					modsItems.splice(movePosition,1);//删除原数组中的值
					modsItems.unshift(widgetObj.id);
				}else{
					var newModsItems = mods[currentColumnIndex];//目的数组
					var oldModsItems = mods[oldWidgetCol-1];//原数组
					var movePosition = jQuery.inArray(widgetObj.id, oldModsItems);  //返回这个widget在原数组中的位置
					oldModsItems.splice(movePosition,1);//删除原数组中的值
					newModsItems.unshift(widgetObj.id);//添加到新数组中
				}
			}else if (rowId == (currentColumnSize-1)){//当移动位置为置底的时候，取row为原倒数第一个row值加1000
				var tempWidget = App.parseModules(currentColumn[currentColumnSize-2].widget.id);
				widgetObj.row = parseFloat(tempWidget.row) + 1000;
				if(moveInSameColumn){
					var modsItems = mods[currentColumnIndex];//原数组和目的数组为同一数组
					var movePosition = jQuery.inArray(widgetObj.id, modsItems);  //返回这个widget在原数组中的位置
					modsItems.splice(movePosition,1);//删除原数组中的值
					modsItems.push(widgetObj.id);
				}else{
					var newModsItems = mods[currentColumnIndex];//目的数组
					var oldModsItems = mods[oldWidgetCol-1];//原数组
					var movePosition = jQuery.inArray(widgetObj.id, oldModsItems);  //返回这个widget在原数组中的位置
					oldModsItems.splice(movePosition,1);//删除原数组中的值
					newModsItems.push(widgetObj.id);//添加到新数组中
				}
			}else{//当移动处于中间情况，取上一个和下一个的row值的一半
				var tempWidgetTop = App.parseModules(currentColumn[obj.row.id-1].widget.id);
				var tempWidgetBottom = App.parseModules(currentColumn[obj.row.id+1].widget.id);
				widgetObj.row = (tempWidgetTop.row+tempWidgetBottom.row)/2;
				if(moveInSameColumn){
					var modsItems = mods[currentColumnIndex];//原数组和目的数组为同一数组
					var position = jQuery.inArray(tempWidgetBottom.id, modsItems);  //返回这个widget在原数组中的位置
					var movePosition = jQuery.inArray(widgetObj.id, modsItems);  //返回这个widget在原数组中的位置
					modsItems.splice(movePosition,1);//删除原数组中的值
					modsItems.splice(position,0,widgetObj.id);//把移动的widget放入到新位置上
				}else{
					var newModsItems = mods[currentColumnIndex];//目的数组
					var oldModsItems = mods[oldWidgetCol-1];//原数组
					var position = jQuery.inArray(tempWidgetBottom.id, newModsItems);  //返回这个widget在原数组中的位置
					var movePosition = jQuery.inArray(widgetObj.id, oldModsItems);  //返回这个widget在原数组中的位置
					oldModsItems.splice(movePosition,1);//删除原数组中的值
					newModsItems.splice(position,0,widgetObj.id);//添加到新数组中
				}
			}
			var params = "tabWidgetsId="+widgetObj.id+"&col="+widgetObj.col+"&row="+widgetObj.row;
			AjaxCommonTools(params,basePath+"/portalScene/updateTabWidget.action","json",true);
		}
		
		
		temp.div.parentNode.replaceChild(div, temp.div);
		temp.row.parentNode.removeChild(temp.row);
		delete temp.row;
		
		Object.delEvent(document, ["onmousemove"], self.iFunc);
		Object.delEvent(document, ["onmouseup"], self.eFunc);
		document.onselectstart = self.iFunc = self.eFunc = null;
	},
	
	add : function (o,setEventFlag) {
	    //添加对象，并绑定相应事件操作
		var self = this;
		if(setEventFlag){
			o.mousedown = Object.addEvent(o.widgetHeader, ["onmousedown"], self.startMove.bind(self, o));
			o.reduceFunc = Object.addEvent(o.reduce, ["onmousedown"], self.reduce.bind(self, o));
			o.closeFunc = Object.addEvent(o.close, ["onmousedown"], self.close.bind(self, o));
			o.mouseover = Object.addEvent(o.widget, ["onmouseover"], self.over.bind(self, o.widget,setEventFlag));
			o.mouseout = Object.addEvent(o.widget, ["onmouseout"], self.out.bind(self, o.widget));
			o.editFunc = Object.addEvent(o.edit, ["onmousedown"], self.editPref.bind(self, o.editContent));
			o.refreshFunc = Object.addEvent(o.refresh, ["onmousedown"], self.refresh.bind(self, o));
		}else{
			o.mousedown = Object.addEvent(o.widgetHeader, ["onmousedown"], self.startMove.bind(self, o));
			o.reduceFunc = Object.addEvent(o.reduce, ["onmousedown"], self.reduce.bind(self, o));
			o.mouseover = Object.addEvent(o.widget, ["onmouseover"], self.over.bind(self, o.widget,setEventFlag));
			o.mouseout = Object.addEvent(o.widget, ["onmouseout"], self.out.bind(self, o.widget));
			o.editFunc = Object.addEvent(o.edit, ["onmousedown"], self.editPref.bind(self, o.editContent));
			o.refreshFunc = Object.addEvent(o.refresh, ["onmousedown"], self.refresh.bind(self, o));
			o.close.style.display = "none";
		}
	},
	
	close : function (o , evt) {
		
		var e=(evt)?evt:window.event; //判断浏览器的类型，在基于ie内核的浏览器中的使用cancelBubble
		if (window.event) { 
			e.cancelBubble=true; 
		} else { 
			e.preventDefault(); //在基于firefox内核的浏览器中支持做法stopPropagation
			e.stopPropagation(); 
		} 
		
	    //关闭对象，释放所有绑定的事件，并删除在内存中的引用
		Colorbox.showGlobal({cursor: "none"});//整屏遮罩效果
	    if(!confirm("你确定要删除此widget？")){
	    	Colorbox.hidden();
	        return false;
	    }
		
	    ModuleTools.removeModule(o.widget.id,o.parent.id);//删除页面用户数组中的组件
		var parent = o.parent;
		Object.delEvent(o.close, ["onmousedown"], o.closeFunc);
		Object.delEvent(o.reduce, ["onmousedown"], o.reduceFunc);
		Object.delEvent(o.widgetHeader, ["onmousedown"], o.mousedown);
		Object.delEvent(o.widget, ["onmouseover"], o.mouseover);
		Object.delEvent(o.widget, ["onmouseout"], o.mouseout);
		Object.delEvent(o.edit, ["onmousedown"], o.editFunc);
		Object.delEvent(o.refresh, ["onmousedown"], o.refreshFunc);
		o.refreshFunc = o.mouseover = o.mouseout = o.editFunc = o.closeFunc = o.reduceFunc = o.mousedown = null;
		parent.element.removeChild(o.element);
		parent.del(o.id);
		delete o;
	},
	/**
	 * 编辑个性化属性
	 * @param o
	 * @return
	 */
	editPref : function(o , evt){
		
		var e=(evt)?evt:window.event; //判断浏览器的类型，在基于ie内核的浏览器中的使用cancelBubble
		if (window.event) { 
			e.cancelBubble=true; 
		} else { 
			e.preventDefault(); //在基于firefox内核的浏览器中支持做法stopPropagation
			e.stopPropagation(); 
		} 

		if (o.style.display == "block") {
			o.style.display = "none";
		} else {
			var widget = $(o.parentNode);
			var widgetID = widget.attr("id");
			var widgetObject = App.parseModules(widgetID);
			if(widgetObject.type != 'common'){
				o.style.display = "block";
				return;
			}
			$(o).children("form").html('');
			var perTab = $("<table></table>");
			var perDiv = $("<div align=\"left\"></div>");
			$.get(basePath+"/portalScene/getOtherPreference.action?a="+Math.random(), { tabWidgetsId: widgetID },
			    function(){
				   var data = arguments[0].tabWidgetJson;//获取后台tabWidgetJsonwidget个性化数据
				   if (data.length>0) {
				   	var count = 0;//记数器，据此判断属性是否仅有hidden
					   $.each( data, function(i, n){
						   var type = n.elementType;
						   if(type == "string" || type == "password"){
							   $(perTab).append('<tr><td align=\"right\">'+n.label+'：</td><td align=\"left\"><input type=\'text\' name='+n.elementType+' id='+n.id+' value='+n.value+' oldvalue='+n.value+' /></td></tr>');
							   count++;
						   }else if (type == "bool"){
							   if(n.value=="true"){
								   $(perTab).append('<tr><td align=\"right\">'+n.label+'：</td><td align=\"left\"><input type=\'checkbox\' name='+n.elementType+' id='+n.id+' checked  oldvalue='+n.value+'></input></td></tr>');
							   }else {
								   $(perTab).append('<tr><td align=\"right\">'+n.label+'：</td><td align=\"left\"><input type=\'checkbox\' name='+n.elementType+' id='+n.id+' oldvalue='+n.value+'></input></td></tr>');
							   }
							   count++;
						   }else if (type == "enum"){
							   var enumData = eval("(" + n.value + ")");
							   var optionStr = '';
							   $(enumData).each(function(i, b){ //遍历结果数组
								   if (b.value==n.selectValue) {
									   optionStr = optionStr+"<option value=\"" + b.value + "\" selected oldvalue=\""+b.value+"\">" + b.showName + "</option>";
								   } else {
									   optionStr = optionStr+"<option value=\"" + b.value + "\" >" + b.showName + "</option>";
								   }
							   });	
							   $(perTab).append('<tr><td align=\"right\">'+n.label+'：</td><td align=\"left\"><select id='+n.id+' name='+n.elementType+'>'+optionStr+'</select></td></tr>');
							   count++;
						   } 
					   });  
					   if(count>0){//解决只有hidden类型数据的情况，仅有hidden则不显示保存按钮
						   $(perTab).append('<tr><td align=\"right\"><input type=\'button\' id=\'submit_'+widgetID+'\' value=\'保存\'/></td><td align=\"left\"><input id=\'cancel_'+widgetID+'\' type=\'button\'  class=\"pre_cancel\" value=\'取消\' /></td></tr>');
						   $(perDiv).append(perTab);
						   $("#submit_"+widgetID).live("click",function(){//持久化个性化widget属性
							   var widgetInfo = [];//widget更新信息：使用直接量和push拼接字符串效率较用new关键字和+=要高
							   $("input:text", $(o)).each(function(){
								   var inputValue = $.trim(this.value);//获取用户编辑后的值：可能此属性用户并未更改
								   var oldValue = $.trim($(this).attr('oldvalue'));//获取此属性之前的值
								   //为提升性能，如果用户并未修改属性或者修改后属性值为空则后台不做update操作
								   if (inputValue!="" && inputValue!=oldValue) {
									   widgetInfo.push(this.id + "_widget_attr_" + this.name + "_widget_attr_" + inputValue + "_widget_split_");
								   }
							   });
							   $("input:checkbox", $(o)).each(function(){
								   var oldValue = $.trim($(this).attr('oldvalue'));//获取此属性之前的值
								   if (oldValue!=this.checked+"") {
									   widgetInfo.push(this.id + "_widget_attr_" + this.name + "_widget_attr_" + this.checked + "_widget_split_");
								   }
							   });
							   $("select", $(o)).each(function(){
								   var selectObj = $(this).find("option:selected");
								   var oldValue = $.trim($(selectObj).attr('oldvalue'));//获取此属性之前的值
								   if (selectObj.val()!=oldValue) {
									   widgetInfo.push(this.id + "_widget_attr_" + this.name + "_widget_attr_" + selectObj.val() + "_widget_split_");
								   }
							   });
							   var len = widgetInfo.length;
							   if (len>0) {//widget个性属性有修改再发送异步请求，否则不请求后台
								   var params = "widgetInfo="+widgetInfo.join("");
								   AjaxCommonTools(params,basePath+"/portalScene/mergePreference.action","json",true,function(data){
								   		if(data.success==1){//update成功
									   		ModuleTools.refreshIframe(widgetID+"_iframe");//刷新当前iframe
										}else{
											alert("编辑个性化属性出错了！");
										}
								   });
								   
							   }
							   o.style.display = "none";
						   });
						   $("#cancel_"+widgetID).live("click",function(){//取消widget编辑
							   o.style.display = "none";
						   });
						   $(o).children("form").append(perDiv.html());
					  	}
					   
				   }
		        }
			,"json");
			o.style.display = "block";
		}
	},
	
	reduce : function (o , evt) {//widget缩小与放大
		var e=(evt)?evt:window.event; //判断浏览器的类型，在基于ie内核的浏览器中的使用cancelBubble
		if (window.event) { 
			e.cancelBubble=true; 
		} else { 
			e.preventDefault(); //在基于firefox内核的浏览器中支持做法stopPropagation
			e.stopPropagation(); 
		} 
		
		var isGlobalClick = Drag.ReduceObject.isGlobalClick;
		if(isGlobalClick){
			var globalWidgetStatus = Drag.ReduceObject.globalWidgetStatus;
			o.status = globalWidgetStatus;
			if (o.status == 1) {
				o.content.style.display = "block";
				o.reduce.innerHTML = "<img title='收起' style='margin-top:3px;' src='"+basePath+"/images/ico_showMod.gif' />";
			} else {
				o.content.style.display = "none";
				o.reduce.innerHTML = "<img title='展开' style='margin-top:3px;' src='"+basePath+"/images/ico_hideMod.gif' />";
			}
		}else{
			if ((o.status = (o.status == 1 ? 0 : 1))) {
				o.content.style.display = "block";
				o.reduce.innerHTML = "<img title='收起' style='margin-top:3px;' src='"+basePath+"/images/ico_showMod.gif' />";
			} else {
				o.content.style.display = "none";
				o.reduce.innerHTML = "<img title='展开' style='margin-top:3px;' src='"+basePath+"/images/ico_hideMod.gif' />";
			}
		}
	},
	
	refresh : function (o , evt) {
		
		var e=(evt)?evt:window.event; //判断浏览器的类型，在基于ie内核的浏览器中的使用cancelBubble
		if (window.event) { 
			e.cancelBubble=true; 
		} else { 
			e.preventDefault(); //在基于firefox内核的浏览器中支持做法stopPropagation
			e.stopPropagation(); 
		} 
		if(o.type == "smart"){
			o.initWidget.refresh();
		}
		if(o.type == "common"){
			var iframe = document.getElementById(o.root_id+'_iframe');
			iframe.src = iframe.src;
		}
	},
	
	over : function (o) {//鼠标悬浮事件定义
		var temp = o.childNodes[Drag.IE ? 0 : 1].childNodes[0];
		var close = temp.childNodes[1];
		var edit = temp.childNodes[2];
		var reduce = temp.childNodes[3];
		var refresh = temp.childNodes[4];
		$(reduce).css("display","");
		var setEventFlag = arguments[1];
		if(setEventFlag){
			$(close).css("display","");
		}
		$(edit).css("display","");
		$(refresh).css("display","");
	},

	out : function (o) {//鼠标离开事件定义
		var temp = o.childNodes[Drag.IE ? 0 : 1].childNodes[0];
		var close = temp.childNodes[1];
		var edit = temp.childNodes[2];
		var reduce = temp.childNodes[3];
		var refresh = temp.childNodes[4];
		$(reduce).css("display","none");
		$(close).css("display","none");
		$(edit).css("display","none");
		$(refresh).css("display","none");
	},
	
	parse : function (o) {//初始化成员
		try {
			var  setEventFlag = TabTools.getEventFlag();//获得是否开启widget绑定事件状态标识
			var self = this, table = self.table, cols, div, i, j;
			for (i = 0 ; i < o.length ; i ++) {
				div = $me(o[i].cols), cols = table.add(div);
				for (j = 0 ; j < o[i].rows.length ; j++)
					self.add(cols.add(o[i].rows[j].id, 1),setEventFlag);
			}
		} catch (exp) {
			alert("解析出错了");
		}
	}
	
};

//页面相关数据操作
var App = {
	modules : [],//当前用户组件库
	
	tabs : [],//当前用户tab
	
	removeTabs : function (id){
		var self = this, tabs = self.tabs, i = 0;
		for (i in tabs) {
			if (tabs[i].id == id)
				tabs.splice(i,1);
		}
	},

	
	parseTabs : function (id) {//根据id返回指定的tab对象
		var self = this, tabs = self.tabs, i = 0;
		for (i in tabs) {
			if (tabs[i].id == id)
				return tabs[i];
		}
	},
	
	removeModules : function (id){
		var self = this, json = self.modules, i = 0;
		for (i in json) {
			if (json[i].id == id)
				json.splice(i,1);
		}
	},
	
	parseModules : function (id) {//根据id返回指定的module对象
		var self = this, json = self.modules, i = 0;
		for (i in json) {
			if (json[i].id == id)
				return json[i];
		}
	},
	
	loadTabMenu : function (){//加载Tab，并添加切换事件
	     var self = this;
	     var selectTab = TabTools.getDefaultSelectedTab();//返回当前页面中设置为默认选中的tab
         $.each(self.tabs,function(entryIndex,entry){//遍历tab元素取出元素并添加至标签UL容器中
        	var defaultClass = entry.defaultSelect==1?'selected':'';//如果选中类型为1，则将Tab样式置为已选中
         	if(!selectTab  && entryIndex == 0){//当前场景没有设置默认选中的Tab，并且为第一个Tab，则将第一个Tab置为选中样式
         		defaultClass = 'selected';
         		entry.defaultSelect = 1;
         	}
            if(entry.defaultSelect == 1){
	    		var liContent = '<span class="s_innertab">';
	    		liContent = liContent + '<img class="icon" src="'+basePath+'/images/tabicons/'+entry.icon+'">';
				if(entry.templateType==3){
					liContent = liContent + '<span id=\'tab_title_'+entry.id+'\'  title="'+entry.title+'">'+entry.title+'&nbsp;&nbsp;<img onclick="TabTools.remove(\''+entry.id+'\')" class="pageClose" src="'+basePath+'/images/spacer.gif" alt="删除该页面" title="删除该页面" /></span>';
				}else{
		    		liContent = liContent + '<span id=\'tab_title_'+entry.id+'\'  title="'+entry.title+'">'+entry.title+'&nbsp;&nbsp;</span>';
				}
	    		liContent = liContent + '</span>';	
            	var html = '<li style=\'top: 0px; left: 0px\' id='+entry.id+' class='+defaultClass+'>'+liContent+'</li>';  
            	$('#mainNav').append(html);   
            }else{
	    		var liContent = '<span class="innertab">';
	    		liContent = liContent + '<img class="icon" src="'+basePath+'/images/tabicons/'+entry.icon+'">';
	    		liContent = liContent + '<span id=\'tab_title_'+entry.id+'\'  title="'+entry.title+'">'+entry.title+'&nbsp;&nbsp;</span>';
	    		liContent = liContent + '</span>';	
            	var html = '<li style=\'top: 0px; left: 0px\' id='+entry.id+' class='+defaultClass+'>'+liContent+'</li>';  
            	$('#mainNav').append(html);   
            }
            
            if(entry.templateType==3){//如果tab不是系统级别，则为tab绑定双击事件，让用户可修改tab名称
            	TabTools.liBindDBClickEvent(entry.id);
            }
         });        
	     $("#mainNav>li").click(function() {//绑定tab的click切换事件，包括样式切换和容器加载
	         var currentClass = $(this).attr("className");
	         if(currentClass == 'selected'){
	             return false;
	         }
	         SlideBox.slideUp("tabOptions");//页面布局div向上滑动，解决切tab后之前的布局div没有关闭问题
	         $(this).addClass("selected"); 
    		 var currentLIID = $(this).attr("id");
			 var _tab = App.parseTabs(currentLIID);//获取当前tab对象
			 var _type = _tab.templateType;//tab模板级别：1、2、3分表标识首页、我的工作、用户自定义页面
			 if(_type==3){//为type=3的用户自定义tab添加删除按钮
	         	 var closeHTML = '<img onclick="TabTools.remove(\''+currentLIID+'\')" class="pageClose" src="'+basePath+'/images/spacer.gif" alt="删除该页面" title="删除该页面" />';
	         	 $("#tab_title_"+currentLIID).append(closeHTML);
			 }
         	 
         	 $("#mainNav li[id='" + currentLIID + "']").children("span").removeClass("innertab");
         	 $("#mainNav li[id='" + currentLIID + "']").children("span").addClass("s_innertab");
         	 
	         $("#mainNav li[id!='" + currentLIID + "']").removeClass("selected");
	         $("#mainNav li[id!='" + currentLIID + "']").children("span").addClass("innertab");
	         $("#mainNav li[id!='" + currentLIID + "']").children("span").children("span").children('img').remove();
	         
			 //每次切换时，查找最新的tab数据
	        var tabID = $(this).attr("id");
			self.initTabPage(tabID);//调用方法加载tab页面
			ModuleTools.initOpenOrClose();//初始化全局收缩参数
	     });
		 if(self.tabs.length >=6){//当tab的数量达到6个的时候，就不再显示添加按钮
			$("#aNewTab").hide();
		 }
	}, 
	
	initTabPage : function (id){//初始化tab页面
	     var self = this;
	     var selectedTab  = self.parseTabs(id);
	     var cols = selectedTab.colStyle.split(":");
	     $('#columns').html('');
	     var html = '<div class="cell r_nbsp_small">&nbsp;</div>';	
	     for(var d in cols){
	         html += '<div class="cell" id="column_'+(parseInt(d)+1)+'" style="min-height: 117px;width: '+cols[d]+'%;"></div>';
	         if(d != cols.length-1){
	        	 html += '<div class="cell r_nbsp">&nbsp;</div>';
	         }
	     }
	     html += '<div class="cell r_nbsp_small">&nbsp;</div>';
	     $('#columns').append(html);
	     self.loadTabPage(selectedTab);
	},
	drag : null,
    loadTabPage : function (tab){//加载页面的widget
    	 this.drag = null;
    	 this.drag = new Drag();
       	 var selectedTab = tab;
       	 var modJson = selectedTab.mods;
       	 var columnJson = [];
       	 for(var i in modJson){
	       	 var columnObject = {};
	       	 var columnIndex = parseInt(i)+1;
	       	 columnObject.cols = "column_" + columnIndex;
	       	 columnObject.rows = [];
	       	 var ma = modJson[i];
	       	 for(var j in ma){
	       	     var rowObject = {};
	       	     rowObject.id = ma[j];  
		       	 columnObject.rows.push(rowObject);
	       	 }
	       	 columnJson.push(columnObject); 
       	 } 
		 this.drag.parse(columnJson);
    },
    
	start : function(){//进行数据的初始化过程
	     var self = this;
 		 self.loadTabMenu();//加载并初始化tab标签
 		 var  selectTab = TabTools.getDefaultSelectedTab();//返回当前页面中设置为默认选中的tab
 		 if(selectTab){//如果存在，则直接将该Tab的widget取出并加载
 			 self.initTabPage(TabTools.getDefaultSelectedTab().id);//加载页面的widget
 		 }else{//如果不存在，则直接第一个Tab的widget取出并加载
 			self.initTabPage(this.tabs[0].id);//加载页面的widget
 		 }
         TabTools.liveOptions();//数据初始化完毕为class为icon的图片绑定事件
         $("#tabIcons a").live("click",function(){//页面图标切换绑定click事件，提供update操作
        	TabTools.updateTabIcon(this);
         });
		 //为应用商店页面图片绑定tiptool提示事件
		$(".wc_ad").live("mouseover",function(){
			var conf = {
				tip: '#tip', 
				position: ['top', 'left'], 
				offset: [60-(document.body.scrollTop + document.documentElement.scrollTop), -70],
				effect: 'toggle',
				events: {
					def: 'mouseover,mouseout'
				}
			};
			$(".wc_ad img[title]").tooltip($.extend({}, conf)).dynamic();	
		});
		//为应用商店绑定用户输入商品名称后回车键即搜索功能
		$("#widgetName").keydown(function(e){
			switch(e.which){
				case 13 : ModuleTools.showModuleList(1,'null');
					break;
			}
		});
	} 
};
	
//Tab相关工具
var TabTools = {
	  getDefaultSelectedTab : function () {//返回当前页面中设置为默认选中的tab
		   var tabs = App.tabs, i = 0;
		   for (i in tabs) {
				if (tabs[i].defaultSelect == '1')
					return tabs[i];
		   }
	  },
	  
	  getEventFlag : function (){//根据当前选中的Tab，判断是否为首页，如果是首页则返回false，否则返回true
		   var currentTab = this.getSelectedTab();
		   if(currentTab.title == '首页' && currentTab.templateType == 1){
			   return false;
		   }else{
			   return true;
		   }
	  },
	  
	  add : function (){
		$("#aNewTab").bind("click", function(){//给添加tab链接绑定事件，解决网速问题导致用户添加tab超过最大限制问题
			return false;
		});
		SlideBox.slideUp("tabOptions");//页面布局div向上滑动，解决切tab后之前的布局div没有关闭问题
		var currentScene = $("#currentScene").val();//得到当前场景ID
	    var params = "sceneId="+currentScene;
	    AjaxCommonTools(params,basePath+"/portalScene/addTab.action","json",true,function(tabObj){
	    	var liContent = '<span class="s_innertab">';
	    	liContent = liContent + '<img class="icon" src="'+basePath+'/images/tabicons/vcard.gif">';
	    	liContent = liContent + '<span id=\'tab_title_'+tabObj.id+'\'  title="新页面">新页面&nbsp;&nbsp;<img onclick="TabTools.remove(\''+tabObj.id+'\')"  class="pageClose" src="'+basePath+'/images/spacer.gif" alt="删除该页面" title="删除该页面" /></span>';
	    	liContent = liContent + '</span>';	
	    	
	    	var html = '<li style=\'top: 0px; left: 0px\' id="'+tabObj.id+'" class="selected">'+liContent+'</li>';//生成一个新的元素tab元素
	    	$('#mainNav').append(html);//加入到标签UL容器中
	    	
	    	$("#mainNav li[id='" + tabObj.id + "']").children("span").removeClass("innertab");
	    	$("#mainNav li[id='" + tabObj.id + "']").children("span").addClass("s_innertab");
	    	
	    	$("#mainNav li[id!='" + tabObj.id + "']").removeClass("selected");
	    	$("#mainNav li[id!='" + tabObj.id + "']").children("span").addClass("innertab");
	    	$("#mainNav li[id!='" + tabObj.id + "']").children("span").children("span").children('img').remove();
	    	
	    	TabTools.liveOptions(1);//添加新tab页面后为class为icon的图片绑定事件
	    	$("#"+tabObj.id).click(function() {//绑定tab的click切换事件，包括样式切换和容器加载
	    		var currentClass = $(this).attr("className");
	    		if(currentClass == 'selected'){
	    			return false;
	    		}
				SlideBox.slideUp("tabOptions");//页面布局div向上滑动，解决直接点击click后布局div没有关闭问题
	    		$(this).addClass("selected");
	    		var currentLIID = $(this).attr("id");
	    		
	    		var closeHTML = '<img onclick="TabTools.remove(\''+currentLIID+'\')" class="pageClose" src="'+basePath+'/images/spacer.gif" alt="删除该页面" title="删除该页面" />';
	    		
	    		$("#tab_title_"+currentLIID).append(closeHTML);
	    		
	    		$("#mainNav li[id='" + currentLIID + "']").children("span").removeClass("innertab");
	    		$("#mainNav li[id='" + currentLIID + "']").children("span").addClass("s_innertab");
	    		
	    		$("#mainNav li[id!='" + currentLIID + "']").removeClass("selected");
	    		$("#mainNav li[id!='" + currentLIID + "']").children("span").addClass("innertab");
	    		$("#mainNav li[id!='" + currentLIID + "']").children("span").children("span").children('img').remove();
	    		App.initTabPage(tabObj.id);//调用方法加载tab页面
	    		ModuleTools.initOpenOrClose();//初始化全局收缩参数
	    	});
	    	$("#"+tabObj.id).dblclick(function() {//绑定tab的dblclick事件，可以修改tab的名称
	    		var tab = $(this);
	    		var tab_title = $('#tab_title_'+tab.attr("id"));
	    		if(tab_title.html().indexOf('<INPUT')>=0 || tab_title.html().indexOf('<input')>=0 ){
	    			return false;
	    		}
	    		tab_title.children("img").remove();
	    		var oldName = tab_title.html().replace(/&nbsp;&nbsp;/, "");//替换调&nbsp;&nbsp;
	    		var tabID = tab.attr('id');
	    		tab_title.html("<input style='height:10px;font-size: 10px' id='li_input_tabName' type='text' onblur='TabTools.updateTab(\""+tabID+"\",\""+oldName+"\",this.value)' style='width:44px;size: 15px;' value='"+oldName+"' />");
				setTimeout(function(){$('#li_input_tabName').focus();},10);
	    	});
	    	App.tabs.push(tabObj);
	    	App.initTabPage(tabObj.id);
	    	if(App.tabs.length >=6){//当tab的数量达到6个的时候，就不再显示添加按钮
	    		$("#aNewTab").hide();
	    	}
	    	$("#aNewTab").unbind("click");//用户添加tab ajax返回解除绑定事件
	    });
	    ModuleTools.initOpenOrClose();//初始化全局收缩参数
	},
	
	updateTab : function(tabID,oldTitle,newTitle){
		var tabName = $.trim(newTitle);
		var _tab = App.parseTabs(tabID);//获取当前tab对象
		var _type = _tab.templateType;//tab模板级别：1、2、3分表标识首页、我的工作、用户自定义tab
		var closeHTML = '&nbsp;&nbsp;';
		if(_type == 3){
			closeHTML = '&nbsp;&nbsp;<img onclick="TabTools.remove(\''+tabID+'\')" class="pageClose" src="'+basePath+'/images/spacer.gif" alt="删除该页面" title="删除该页面" />';
		}
		if($.trim(oldTitle) == tabName){
			$("#tab_title_"+tabID).html(tabName+closeHTML);
			return false;
		}
		if(tabName == ''){
			$("#tab_title_"+tabID).html(oldTitle+closeHTML);
			return false;
		}
		if(tabName.length  > 6){
			if(confirm("页面标题字数太多了，请减少到6个字！")){
				setTimeout(function(){$("#li_input_tabName").focus();},10);
			}else{
				$("#tab_title_"+tabID).html(oldTitle+closeHTML);
			}
			return false;
		}
		var params = "portalTabsId="+tabID+"&tabtext="+encodeURI(encodeURI(tabName));
		AjaxCommonTools(params,basePath+"/portalScene/updateTabTitle.action","json",true);
		$("#tab_title_"+tabID).html(tabName+closeHTML);
	},
	
	remove : function (id){//删除后，当tab的数量小于8个的时候，继续显示添加按钮
		if(App.tabs.length <= 1){
			alert("您不能删除唯一的标签页面。");
			return false;
		}
		if(!confirm("确认删除该标签页？")){
			return false;
		}
		var params = "portalTabsId="+id;
    	AjaxCommonTools(params,basePath+"/portalScene/deleteTab.action","json",true);
    	App.removeTabs(id);
		if(App.tabs.length <8){
			$("#aNewTab").show();
		}
		//清除被删除的tab，并重新定位一个新的tab
		
		var prevID = $("#"+id).prev().attr("id");
		var nextID = $("#"+id).next().attr("id");
		
		$("#"+id).remove();
		if(prevID){
			$("#"+prevID).click();
		}else{
			$("#"+nextID).click();
		}
	},
	
	closeOptions : function(id){//关闭页面布局选项DIV
		$("#"+id).slideToggle("slow");
	},
	
	liveOptions : function(){//为class为icon的图片绑定click事件
		if(arguments[0]==1 ){//点击添加tab按钮移除App.start()为icon绑定的事件,1--只是表示在添加页面时传的一个标识哦~~
			$(".icon").unbind("click");
		}
		$(".icon").bind("click",function(){
			if($(this).parent().hasClass("s_innertab") && $(this).parent().parent().hasClass("selected")){
				$("#tabOptions").slideToggle("slow");
			}
		});
	},
	
	updateTabIcon : function(){//更新tab图标ajax操作
		var _img = arguments[0];
		var _li = $(".selected");//当前被选中tab对象
     	var tabId = $(_li).attr("id");//当前选中tab的id
     	var iconName = $.trim($(_img).attr("iconName"));//获取tab新图标名称
     	var imgSrc = $.trim($(_li).children("span").children("img").attr("src"));
     	if(imgSrc.indexOf(iconName)==-1){//发送异步更新图标
     		var params = "tabId="+tabId+"&iconName="+iconName;
     		AjaxCommonTools(params,basePath+"/portalScene/updateTabIcon.action","json",true,function(msg){//异步更新icon图标
     			var suc_flag = msg["success"];//获取后台返回更新成功标识
     			if(suc_flag=="1"){//如果标识为1则更新前台图标
     				$(_li).children("span").children("img").attr("src",basePath+"/images/tabicons/"+iconName);
     			}
     		});
     	}
	},
	
	changePageLayout : function(){//改变页面布局
		var _layout = arguments[0];//用户点击的布局方式
		var _tab = TabTools.getSelectedTab();//获取当前tab对象
		var _tabId = _tab.id;//获取tab的Id
		var _col = _tab.colStyle;//tab之前的布局方式
		if(_layout!=_col){//表示用户要切换布局
			//解析tab布局方式：判断用户是增加列数还是减少列数
			var newClos = (_layout.split(":")).length;
			var cols = (_col.split(":")).length;
			var _mods = _tab.mods;
			var _modsLength = _mods.length;//当前tab的widget数组长度
			var columnCount = Math.abs(cols-newClos);//判断列数差
			var params;//异步更新参数
			//表示增加列数：前台增加相应个数的空数组，后台数据库只需修改colStyle即可
			if(newClos>cols){
				for(var i=1; i<columnCount+1; i++){
					var tempArr = [];
					_mods[_modsLength+i-1] = tempArr;
				}
				params = "tabId="+_tabId+"&layout="+_layout;//传递参数
			}
			else if(newClos==cols){//列数相等，只是更改列的宽度，则只更新布局即可
				params = "tabId="+_tabId+"&layout="+_layout;//传递参数
			}
			else{//减少列数：
				var addRemoveColumn =  _mods[_modsLength-columnCount-1];//添加widget的起始位置	
				var moduleID = addRemoveColumn[addRemoveColumn.length-1];//添加列最后的widget对象
				var startRowVal=0;//起始widget的row
				var startColVal = _modsLength-columnCount;//起始widget的col
				var passRow=0;//传递row值
				var changeEleArr = [];//改变位置的widget元素集合
				if(moduleID==undefined){//表示操作位置无widget，则赋予其基row值1000
					startRowVal = 1000;
				}else{
					var module = App.parseModules(moduleID);
					startRowVal = module.row;
				}
				passRow = startRowVal;
				var handleArray = [];//中转要删除的widget数组
				for(var i = columnCount;i >0 ; i--){
					handleArray = handleArray.concat(_mods[_modsLength-i]);
				}
				_mods.splice(_modsLength-columnCount,columnCount);//删除不需要的widget数组列
				for(var m in handleArray){
					var tempModule = App.parseModules(handleArray[m]);
					startRowVal += 1000;
					tempModule.row = startRowVal;
					tempModule.col = startColVal;
					changeEleArr.push(tempModule.id);
				}
				_mods[_modsLength-columnCount-1] = addRemoveColumn.concat(handleArray);//连接删除的widget数组至最近的有效列
				params = "tabId="+_tabId+"&layout="+_layout+"&col="+startColVal+"&row="+passRow+"&ids="+changeEleArr;//传递参数
			}
			_tab.colStyle = _layout;//更改布局方式
			App.initTabPage(_tabId);//前台更新
			//后台Ajax 同步tab的信息到数据库
			AjaxCommonTools(params,basePath+"/portalScene/updateLayout.action","json",true);
		}
	},
	
	getSelectedTab : function(){//返回当前选中的tab对象
		var _li = $(".selected");//当前被选中tab对象
		var _tabId = $(_li).attr("id");//当前选中tab的id
		var _tab = App.parseTabs(_tabId);
		return _tab;
	},
	
	/*
	 * 作用：恢复tab默认设置
	 * 
	 * ---后台处理：
	 * 1、针对系统级别的三个tab：首页、我的工作、我的KM，后台删除用户个性化数据并将当前tab恢复到系统模板初始化数据，
	 * 	    返回前台当前tab的widgetJson和tabsJson新数据
	 * 
	 * 2、针对用户自定义tab：后台直接删除返回前台操作成功标识
	 * 
	 * --前台处理：执行回调函数,当后台ajax操作返回操作成功标识
	 * 1、前台针对系统级别的tab去更新它在App.tabs和App.modules里的数据，并把新数据展示给用户;
	 * 2、针对用户自定义级别的tab在App.tabs和App.modules里找到其相应的数据，删掉即可
	 * 
	 * --前后台数据同步完毕tab重新加载....
	 */
	resumeDefaultSetup : function(){
		var _self = this;
		var _tab = _self.getSelectedTab();//获取当前选中的tab对象
		var _mods = _tab.mods;
		var _modsLen = _mods.length;
		var _type = _tab.templateType;//tab模板级别：1、2、3分表标识首页、我的工作、用户自定义tab
		var _promptInfo;//提示用户信息
		
		if(_type==3){//用户试图恢复自定义tab操作
			_promptInfo = "恢复默认设置将删除您的个性化页面所有widget数据，您确认此操作吗？";
			var widgetCounts = 0;//tab页面widget数量总和，用于判断个性化页面用户回复默认操作，如果总和为0则不需要再请求后台数据库,对于系统级别的tab则必须让用户能恢复
			for(var i=0;i<_modsLen;i++){
				var _widgetArr = _mods[i];
				widgetCounts += _widgetArr.length;
			}
			if(widgetCounts==0){//如果为0提示用户
				alert("当前个性化页面已处于系统默认状态，您可以开启您的个性化之旅了！");
				return;
			}
		}else{
			_promptInfo = "恢复默认设置将更改此tab页面信息至系统初始化状态，您的个性化设置将失效，您确认恢复吗？";
		}
			
		Colorbox.showGlobal();
		if (confirm(_promptInfo)) {
			var _tabId = _tab.id;//tabId
			//ajax恢复默认设置
	    	var params = "tabId="+_tabId+"&templateType="+_type;
	    	AjaxCommonTools(params,basePath+"/portalScene/updaterecoverTab.action","json",true,function(data){
				var _operateFlag = data.success;//操作标识
				if(_operateFlag==1){//如果为1表示后台恢复操作成功
					var _tabsJson = data.tabsJson;//后台返回新的tab数据
					
					//根据tabId得到当前tab在App.tabs数组的位置，并替换tab信息为最新放回数据
					var _tabs = App.tabs;//获取tabs数组
					var _modules = App.modules;//获取modules数组
					
					//删除tab：mods里对应记录在App.modules里的数据 
					for(var i=0; i<_modsLen; i++){
						var _tempArr = _mods[i];
						var _tempLen = _tempArr.length;
						for(var j=0; j<_tempLen;j++){
							var _tempWidtId = _tempArr[j];
							App.removeModules(_tempWidtId);
						}
					}
					
					//遍历返回的widget数组，加入到App.modules
					$.each(data.widgetJson, function(i,obj){
						_modules.push(obj);
					});
					
					var tabPosition = 0;//当前tab在App.tabs数组中的位置
					for (var i in _tabs) {//获取tab在_tabs里的位置
						if (_tabs[i].id == _tabId) {
							tabPosition = i;
						}
					}
					_tabs[tabPosition] = _tabsJson;//替换tab数组原位置的tab
					//重新加载tab页面
					App.initTabPage(_tabId);//调用方法加载tab页面
				}else{
					alert("恢复默认设置出现异常！");
				}
			});
		}
		Colorbox.hidden();
	},
	
	liBindDBClickEvent : function(){//为tab绑定双击修改名称事件
		var tabID = arguments[0];//tabId
		$("#"+tabID).dblclick(function() {//绑定tab的dblclick事件，可以修改tab的名称
			 var tab_title = $('#tab_title_'+tabID);
			 if(tab_title.html().indexOf('<INPUT')>=0 || tab_title.html().indexOf('<input')>=0 ){
				 return false;
			 }
			 tab_title.children("img").remove();
			 var oldName = tab_title.html().replace(/&nbsp;&nbsp;/, "");//替换调&nbsp;&nbsp;
			 tab_title.html("<input style='height:10px;font-size: 10px' id='li_input_tabName' type='text' onblur='TabTools.updateTab(\""+tabID+"\",\""+oldName+"\",this.value)' style='width:44px;size: 15px;' value='"+oldName+"' />");
			 setTimeout(function(){$('#li_input_tabName').focus();},10);
		 });
	},
	
	defaultSelectedTab : function(){//默认选中tab设置
		var _self = this;
		var _tab = _self.getSelectedTab();//获取当前选中的tab对象;
		var defaultSelectTab = _self.getDefaultSelectedTab();
		if(_tab.id == defaultSelectTab.id){
			return false;
		}else{
			var params = "ids="+_tab.id+","+defaultSelectTab.id;
			AjaxCommonTools(params,basePath+"/portalScene/updateTabDefaultSelect.action","json",true,function(data){
				if(data.success == '1'){
					_tab.defaultSelect = '1';
					defaultSelectTab.defaultSelect = '0';
					alert('恭喜，设置成功!');
				}else{
					alert('抱歉，设置失败，请联系系统管理员!');
				}
			});
		}
	}
};

//Module相关工具
var ModuleTools = {
	/**
	 * 显示可添加组件列表
	 * pageNo：当前页数
	 * appTypeValue：组件类型
	 */
    showModuleList: function(){
		$("#searchImg").attr("disabled", true);//应用商店图片按钮查询功能失效
		$("#fl_tab_ul").children("li").attr("disabled", true);//应用商店商品类型列表不可用
    	var self_ = this;
    	var pageSize = 10;//默认查询10条
    	var pageNo = arguments[0];//当前页
		var appType = arguments[1];//查询类型 	'null'-表示根据名称模糊查询、非null为系统定义的widget商店类型
		var widtName = $("#widgetName").val();//查询widget名称，支持模糊查询
		if(appType!='null'){//如果不按照类型查询，则情况模糊查询条件
			$("#widgetName").val("");
		}
		if(appType=='null'){
			setTimeout(function(){$('#widgetName').focus();},10);
		}
    	Colorbox.showGlobal({cursor: "default"});//整屏遮罩效果
    	$("#widgetLists").html("<font color='red'>加载中...</font>");
    	$("#widgetPage").html("");
    	//ajax完成请求，并返回已审核通过的widget
    	var params = "page.pageNo="+pageNo+"&page.pageSize="+pageSize+"&portalWidget.appType="+appType+"&portalWidget.name="+encodeURI(encodeURI(widtName));
    	AjaxCommonTools(params,basePath+"/widget/showWidgetAppStore.action","json",true,function(data){
			var _dataLen = data.result.length;
			if(_dataLen <= 0){
				$("#widgetLists").html("<font color='red'>暂无此类型模块！</font>");
			}else{
				$("#widgetLists").html("");
				//展示widget列表
				$.each(data.result, function(i,obj){//遍历widget列表
					var _tempName = '';//应用名称
					
					if( obj.name.length > 13 ){
						_tempName = obj.name.substring(0,13)+'...';
					}else{
						_tempName = obj.name;
					}
					
					//名称高亮显示
					if (widtName && _tempName.indexOf(widtName) != -1) {
						_tempName = _tempName.replace(new RegExp(widtName, 'g'), "<em style='color:#CC0000;font-style: normal;'>"+widtName+"</em>");
					}
					
					var _description = '';//系统描述
					if (obj.description.length > 17) {
						_description = obj.description.substring(0,17)+'...';
					} else {
						_description = obj.description;
					}
					
					//描述高亮显示
					if (widtName && _description.indexOf(widtName) != -1) {
						_description = _description.replace(new RegExp(widtName, 'g'), "<em style='color:#CC0000;font-style: normal;'>"+widtName+"</em>");
					}
					$("#widgetLists").append(
						"	<li class=\"app_com_item clearfix\"  style=\"float:left;width:304px;_width:300px;vertical-align:bottom;\">"+
						" 		<div class=\"app_com_icon\">"+
						"			<div><img src='"+basePath+obj.path+obj.breviaryChart+"' alt='"+obj.name+"' onclick='javascript:ModuleTools.addModule(\""+obj.id+"\");' width=\"60\" height=\"60\" title='添加该应用' style=\"cursor:pointer;\"/></div>"+
						"			<a href=\"#_\" class=\"btn_add\" onclick='javascript:ModuleTools.addModule(\""+obj.id+"\");' title='添加该应用'></a>"+
						" 		</div>"+
						" 		<div class=\"app_com_cnt\">"+
						"			<div class=\"app_com_title\">"+_tempName+"</div>"+
						"			<span class=\"app_com_desc\" title=\""+obj.description+"\">"+_description+"</span>"+
						"			<div class=\"app_com_desc\" style=\"color: #CCC\">使用数量："+obj.sum+"</div>"+
						" 		</div>"+
						"	</li>"
					);
					
				});
				$("#widgetLists").append("<div id=\"tip\" class=\"tip\"></div>");
				//首次进入页面拼装分页组件
				self_.patchPageComponent("widgetPage","page_div_sel","page_go_to","pageGoto",data,appType);
				//为分页组件绑定事件
				self_.pageCompBindEvent("page_div_sel","page_go_to","pageGoto",data);
			}
			if(!$("#productNum").html()){
				$("#productNum").html(data.totalCount);//展示总商品数
			}
			$("#widgetListContianer").css("left",(($(document).width())/2-(parseInt($("#widgetListContianer").width())/2))+"px");
			$("#widgetListContianer").css("top",($(document).scrollTop()+100)+"px");
			$("#widgetListContianer").show(600);
			$("#fl_tab_ul").children("li").attr("disabled", false);//应用商店商品类型列表可用
			$("#searchImg").attr("disabled", false);//应用商店图片按钮查询功能可用
    	});
    },
    /**
     * 拼装分页信息DIV
     * 参数1：页面自定义div ID
     * 参数2：分页组件div ID
     * 参数3：跳转页数输入框 ID
     * 参数4：跳转按钮链接 ID
     * 参数5：后台返回json数据
     */
    patchPageComponent : function(pageDivId,domId,gotoInputId,gotoHrefId,data,appType){
		var pageStr = [];//分页信息
		pageStr.push("<div class=\"page\" id=\""+domId+"\" appType=\""+appType+"\">");
		if(data.pageNo==1){
			pageStr.push("<img src=\""+basePath+"/images/styleimg/frist.png\"/>");
			pageStr.push("<img src=\""+basePath+"/images/styleimg/last.png\"/>");
		}else{
			pageStr.push("<a href=\"#\"  id='"+data.firstNo+"'><img src=\""+basePath+"/images/styleimg/frist.png\"/></a>");
			pageStr.push("<a href=\"#\"  id='"+data.prevNo+"'><img src=\""+basePath+"/images/styleimg/last.png\"/></a>");   
		}
		pageStr.push("<span id=\"pageNo\">"+data.pageNo+"</span>/<span id=\"pageCount\">"+data.pageCount+"</span>");
		if(data.pageCount!=1 && data.pageCount!=data.pageNo){
			pageStr.push("<a href=\"#\"  id='"+data.nextNo+"'><img src=\""+basePath+"/images/styleimg/next.png\"/></a>");  
			pageStr.push("<a href=\"#\"  id='"+data.lastNo+"'><img src=\""+basePath+"/images/styleimg/end.png\"/></a>");
		}else{
			pageStr.push("<img src=\""+basePath+"/images/styleimg/next.png\"/>");
			pageStr.push("<img src=\""+basePath+"/images/styleimg/end.png\"/>");
		}
		pageStr.push("跳至<input id=\""+gotoInputId+"\" class=\"pageinput\" type=\"text\"/><a href=\"#\" id=\""+gotoHrefId+"\"><img src=\""+basePath+"/images/styleimg/go.png\"/></a>");
		pageStr.push("</div>");
		$("#"+pageDivId).append(pageStr.join(""));
    },
    /**
     * 分页组件绑定事件
     * 参数1：分页组件div ID
     * 参数2：跳转页数输入框 ID
     * 参数3：跳转按钮链接 ID
     * 参数4：后台返回json数据
     */
    pageCompBindEvent : function(domId,gotoInputId,gotoHrefId,data){
    	var self_ = this;
    	$("#"+domId).children("a").bind("click",function(){
			var pageNo;//当前页
			var idValue = $(this).attr("id");
			var goToPageNo = $("#"+gotoInputId).val();
			if(idValue==gotoHrefId){//表示点击跳至按钮查询，验证跳转页数是否正确
				if(!goToPageNo){
					alert("跳转页数不能为空，请您输入跳转页数！");
					$("#"+gotoInputId).focus();
					return false;
				}else if(!goToPageNo.match(/^\d+$/)) {
					alert("请您输入正确的页码数！");
					$("#"+gotoInputId).focus();
					return false;
				}
				if(goToPageNo-0>data.pageCount-0){//跳转页数大于总记录数，设置总记录数为当前查询页
					pageNo = data.pageCount;
				}else if(goToPageNo==0){//跳转页数为0，设置当前查询页为第一页
					pageNo = 1;
				} else{
					pageNo = goToPageNo;//根据用户正常查询页数进行查询
				}
			}else{
				pageNo = idValue;//用户点击分页按钮进行查询
			}
			var typeVal = $("#page_div_sel").attr("appType");//获取模块类型
    		self_.showModuleList(pageNo,typeVal);
    	});
    },
    /**
     * 显示系统公告详细信息
     * id：公告主键
     */
    showAfficheInfo: function(id){
    	$("#afficheInfo").html("");
    	// ajax完成请求，并返回公告及附件信息
    	var params = "afficheId="+id;
    	AjaxCommonTools(params,basePath+"/affiche/obtainAfficheInfo.action?a="+Math.random(),"json",true,function(data){
    		var len = data.attachmentList.length;
			var attachStr = [];// 附件信息
			if (len>0) {
				attachStr.push("<table width=\"100%\">"+
					"<tr>"+
						"<th>附件名称</th>"+
						"<th>操作</th>"+
					"</tr>");
				$.each(data.attachmentList, function(i,obj){
					attachStr.push("<tr>"+
							"<td>"+
								obj.realName+
							"</td>"+
							"<td>"+
								"<a href="+basePath+"/attachment/download.action?attachment.id="+obj.id+">下载</a>"+
							"</td>"+
						"</tr>");
				});
				attachStr.push("</table>");
			}
			$("#afficheInfo").append("<div class='afficheInfo'>"+
					"<h1>"+data["title"]+"</h1>"+
					"<p>"+data["content"]+"</p>"+
						attachStr.join("")+
					"</div>");
			$("#afficheListShow").css("left",(($(document).width())/2-(parseInt($("#afficheListShow").width())/2))+"px");
			$("#afficheListShow").css("top",($(document).scrollTop()+100)+"px");
			$("#afficheListShow").show(600);
    	});
    },
    hideAfficheList: function(){//隐藏公告信息
    	$("#afficheListShow").hide();
    	$("#afficheInfo").html("");
    },
    hideModuleList: function(){
    	Colorbox.hidden();
    	$("#widgetListContianer").hide();
    	$("#widgetLists").html("");
    	//分类widget的tab重新绑定事件
	    $("#fl_tab_ul").children("li").removeClass("hover");
	    $("#fl_tab_ul li:first-child").addClass("hover");
    },
    removeModule : function(tabWidgetID,tabColumn){
    	var params = "tabWidgetsId="+tabWidgetID;
    	AjaxCommonTools(params,basePath+"/portalScene/delete.action","json",true,function(){
    		App.removeModules(tabWidgetID);//删除页面用户数组中的组建
    		var _tab = TabTools.getSelectedTab();//获取当前选中tab对象
			var _mods = _tab.mods[tabColumn];//获取当前删除列的widget数组
			var delPosition = jQuery.inArray(tabWidgetID, _mods);  //返回这个widget在原数组中的位置
			_mods.splice(delPosition,1);//删除原数组中的值
    		alert("删除成功");
    		Colorbox.hidden();
    	});
    },
    addModule: function(){
    	//请求添加Widget处理
    	var selectedTab = $("#mainNav li[class='selected']").attr("id")||$("#mainNav li[className='selected']").attr("id");
    	var selectedModuleID = arguments[0];//widget定义主键
    	var params = "portalTabsId="+selectedTab+'&wdigetId='+selectedModuleID;
    	
    	//ajax完成请求，并返回新对象
    	AjaxCommonTools(params,basePath+"/portalScene/addModel.action","json",true,function(module){
    		App.modules.push(module);//将新对象放入到页面模块数组中
    		
    		var columnJson = [];
    		var columnObject = {};
    		columnObject.cols = "column_1";
    		columnObject.rows = [];
    		var rowObject = {};
    		rowObject.id = module.id;  
    		columnObject.rows.push(module);
    		columnJson.push(columnObject); 
    		
    		try {
    			var  setEventFlag = TabTools.getEventFlag();//获得是否开启widget绑定事件方法状态
    			var cols, j;
    			cols = App.drag.table.items[0];
    			for (j = 0 ; j < columnJson[0].rows.length ; j++){
    				App.drag.add(cols.add(columnJson[0].rows[j].id, 1),setEventFlag);
    			}
				var _tab = TabTools.getSelectedTab();//获取当前选中tab对象
				var _mods = _tab.mods[0];//获取当前选中tab的第一列
				_mods.push(module.id);//将新添加的widget放入tab第一列的最后一行
    			alert("添加成功");
    		} catch (exp) {
    			alert("解析出错了");
    		}
    	});
    },
    /**
     * 持久化widget背景色
     * obj：样式li对象
     */
    changeWidgetBackGround: function(obj){
        var liObj = obj;
        var styleValue = liObj.getAttribute('value');
        var widget = liObj.parentNode.parentNode.parentNode;
        if(styleValue == 'default'){
        	$(widget).attr("className","module");
        }else{
        	$(widget).attr("className","module " + styleValue);
        }
        var widgetId = $(widget).attr("id");//获取当前widget的ID
        var params = "tabWidgetsId="+widgetId+"&color="+styleValue;
        var widgetObject = App.parseModules(widgetId);//获取当前widget在数组中的对象，并修改它的最新样式
        widgetObject.color = styleValue;
        AjaxCommonTools(params,basePath+"/portalScene/updateWidgetStyle.action","json",true);//异步更新wdiget背景
    },
	
	/*
	 * 刷新iframe
	 * args:iframe's id
	 */
	refreshIframe : function(){
		var id = arguments[0];//iframe ID
		var _src = $("#"+id).attr("src");
		$("#"+id).attr("src",_src);
	},
	
	/*
	 * 当前tab的页面全部折叠或者展开
	 */
	openOrClose : function(){
		var span = arguments[0];
		span.innerHTML = span.innerHTML == '全部折叠'	? '全部展开':'全部折叠';
		var _li = $(".title_d");//当前被选中tab对象
		Drag.ReduceObject.globalWidgetStatus = Drag.ReduceObject.globalWidgetStatus == 1 ? 0:1;
		Drag.ReduceObject.isGlobalClick = true;
		if(Drag.IE){
			for(var i = 0;i < _li.length;i++ ){
				var jqo = $(_li[i]);
				var domo = jqo[0];
				domo.fireEvent("onmousedown");//基于IE内核的方法，强行执行DOM的事件
			}
		}else{
			if($.browser.msie && (Drag.BrowserVersion == 9)){//如果IE9特殊处理
				for(var i = 0;i < _li.length;i++ ){
					var jqo = $(_li[i]);
					var domo = jqo[0];
					domo.fireEvent("onmousedown");//基于IE内核的方法，强行执行DOM的事件
				}
			}else{
				for(var i = 0;i < _li.length;i++ ){
					var event = document.createEvent("MouseEvents");  
					event .initEvent("mousedown",true,true);  
					_li[i].dispatchEvent(event); 
				}
			}
		}
		Drag.ReduceObject.isGlobalClick = false;
	},
	
	/*
	 * 当切换tab时，初始化全局收缩参数以及span中的内容提示
	 */
	initOpenOrClose : function(){
		var span = $(".collapseExpand");
		span.html('全部折叠');
		Drag.ReduceObject.globalWidgetStatus = 1;
		Drag.ReduceObject.isGlobalClick = false;
	}

	
};

//Scene相关工具
var SceneTools = {
	
	/*
	 * 操作主题ID
	 */
	selectedThemeID : '',
	
	/*
	 * 操作主题Code
	 */
	selectedThemeCode : '',
	
	/*
	 * 持久化改变的主题
	 */
    changeTheme : function(portalScenethemeId){
    	var sceneId = $("#currentScene").val();
    	var params = "sceneId="+sceneId+"&portalSceneThemeId="+portalScenethemeId;
    	AjaxCommonTools(params,basePath+"/portalScene/changeSceneThemeRelation.action","json",true);
    	currentUserTheme = this.selectedThemeCode;
    	this.hideThemeList();
    },
    
    saveOperateHandle : function(){
    	this.changeTheme(this.selectedThemeID);
    },
    
    switchStylestyle : function (basePath,curThemObj) {//切换样式，当link带有title的是主题样式，切换它以达到效果
    	var currThemClass = $(curThemObj).attr("className");
		if(currThemClass=="theme sted"){//如果当前主题处于选中状态，则当用户再次点击的时候直接return
			return false;
		}
		var themeCode = $(curThemObj).attr("rel");//主题名称
		$('link[rel*=style][title=portal]').each(function(i) {
			this.href = basePath+"/themes/"+themeCode;
			var preThemeObj = $("li[class=theme sted]");
			$(preThemeObj).removeClass("theme sted");
			$(preThemeObj).addClass("theme");
			$(curThemObj).addClass("theme sted");
		});
    },
    
    /*
	 * 当前tab的页面全部折叠或者展开
	 */
	themeList : function(){
		var pageSize = 12;//默认查询12条
    	var pageNo = arguments[0];//当前页
    	if(!pageNo){
    		pageNo = 1;
    	}
    	var _self = this;
		var params = "page.pageNo="+pageNo+"&page.pageSize="+pageSize;
    	AjaxCommonTools(params,basePath+"/themes/themes_themeAllJsonByPage.action","json",true,function(data){
			var _dataLen = data.result.length;
			if(_dataLen <= 0){
				$(".theme_page").html("<font color='red'>暂无皮肤！</font>");
			}else{
				$(".theme_page").html("");
				$(".theme_page").append('<div class="gallery-operate" style="clear: left;display: none;"><div class="themeOperate"><a rel="prev" href="javascript:SceneTools.saveOperateHandle()">保存</a><a rel="prev" href="javascript:SceneTools.hideThemeList()">取消</a></div></div>');
				var tpath = basePath + "/themes";
				var currentTheme = $('link[rel*=style][title=portal]').attr('href');
				//展示主题列表
				$.each(data.result, function(i,obj){
					var path = tpath + "/" +obj.code;
					var lihtml;
					if(currentTheme == path)
						lihtml = "<li class=\"theme sted\" tid="+obj.id+" rel="+obj.code+"><img src="+tpath+obj.thumbnail+" width=\"107\" height=\"84\" title="+obj.name+"><span title="+obj.name+">"+obj.name+"</span></li>";
					else
						lihtml = "<li class=\"theme\" tid="+obj.id+" rel="+obj.code+"><img src="+tpath+obj.thumbnail+" width=\"107\" height=\"84\" title="+obj.name+"><span title="+obj.name+">"+obj.name+"</span></li>";
					$(".theme_page").append(lihtml);
				});
				var pageInit = _self.themePageInit(data);
				$(".theme_page").append(pageInit);
			}
			if(!$("#themeNum").html()){
				$("#themeNum").html(data.totalCount);//展示总商品数
			}
			$('.theme').click(function(){
				_self.selectedThemeID = $(this).attr("tid");//主题id
				_self.selectedThemeCode = $(this).attr("rel");//主题名称
				if(_self.selectedThemeCode != currentUserTheme){
					$(".gallery-operate").css("display","");
				}else{
					$(".gallery-operate").css("display","none");
				}
				_self.switchStylestyle(basePath,this);//调用函数循环匹配
				return false;
			});
		});
		Colorbox.showGlobal({//整屏遮罩效果
			filter: "alpha(opacity = 50)",
			cursor: "move",
			cursor: "default",
			opacity: 0.5
		});
		$("#themeListContianer").css("left",(($(document).width())/2-(parseInt($("#themeListContianer").width())/2))+"px");
		$("#themeListContianer").css("top",($(document).scrollTop()+100)+"px");
		$("#themeListContianer").show(600); 
	},
	
    themePageInit : function(){
    	var pageInfo = arguments[0];
		var pageStr = [];//分页信息
		pageStr.push('<div class="gallery-pager" style="clear:left">');
		pageStr.push('<div class="pagination">');
		if(pageInfo.prevNo != pageInfo.pageNo){
			pageStr.push('<a rel="prev" href="javascript:SceneTools.themeList('+pageInfo.prevNo+')">前一页</a>');
		}
		for(var i = 0; i<pageInfo.pageCount; i++){
			if((i+1) == pageInfo.pageNo){
				pageStr.push('<a rel="1" href="javascript:void(0)" class="sted">'+(i+1)+'</a>');
			}else{
				pageStr.push('<a rel="1" href="javascript:SceneTools.themeList('+(i+1)+')">'+(i+1)+'</a>');
			}
		}
		if(pageInfo.nextNo != pageInfo.pageNo){
			pageStr.push('<a rel="prev" href="javascript:SceneTools.themeList('+pageInfo.nextNo+')">后一页</a>');
		}
		pageStr.push('</div>');
		pageStr.push('</div>');
		return pageStr.join('');
    },
    
    hideThemeList : function(){
    	if(this.selectedThemeCode != currentUserTheme ){
    		$('link[rel*=style][title=portal]').each(function(i) {
    			this.href = basePath+"/themes/"+currentUserTheme;
    		});
    	}
    	Colorbox.hidden();
    	$("#themeListContianer").hide();
    }
    
};

