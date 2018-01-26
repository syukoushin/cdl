//并发ajax方法定义
var ConcurrentAjax = function (){
	
	this.max_session = 10;// 最大并发数量
	
	this.sessions = 0;// 请求数量
	
	this.requestQue = new Array();// 请求队列
	
	this.createRequest = function(method, url, async, objID) {//发起请求
		var request = new Object();// define request Object
		if (window.ActiveXObject){
			request.ajax = new ActiveXObject("Microsoft.XMLHTTP");
		}else if (window.XMLHttpRequest){
			request.ajax = new XMLHttpRequest();
		}
		if (request.ajax) {
			request.url = url;// request rul
			request.method = method;// request method
			request.async = async;// boolean async
			request.objID = objID;// objId
			if (this.sessions < this.max_session) {
				this.sessions++;
				this.sendRequest(request);// send request
				return;
			} else {
				// ajax请求达到最大并发数量，则请求进入队列
				this.requestQue.push(request);
			}
		}
	};
	
	this.sendRequest = function (request) {//执行请求
		request.ajax.open(request.method, request.url, request.async);
		request.ajax.onreadystatechange = processRequest(request.ajax,
				request.objID);
		request.ajax.send(null);
	};
	
	this.checkQue = function () {//根据最大并发数，检查是否执行请求
		if (this.sessions < this.max_session || this.requestQue.length > 0) {
			this.sessions++;
			var request = this.requestQue.shift();// return the removed element, it's first element
			if (request) {// Do something before sending request about object
				this.preworkBeforeRequest(objID);
				this.sendRequest(request);
			}
		}
	};
	
	this.processRequest = function (request, objID) {//状态监控方法
		var ajaxObj = this;
		return function() {
			if (request.readyState != 4 || request.status != 200)
				return false;
			ajaxObj.processEcho(request, objID);
			ajaxObj.sessions--;
			ajaxObj.checkQue();
		};

	};

	this.preworkBeforeRequest = function (objID) {
		alert("Hi,在请求之前我先休息一下！");
	};

	this.processEcho = function (request, objID) {//内容处理
		alert("Hi,请求返回了，可以在页面展现了~");
		echoText = requst.responseText;
		echoXML = request.responseXml;
	};
	
};

/*基于jQuery简单Ajax调用
 * params:参数
 * usrStr:服务地址
 * returnType:返回数据类型
 * asyncOption:同步异步策略
 * handler:回调方法
 * 用例：AjaxCommonTools("","xx地址",'json',true,function(data){alert(data)});
 */
function  AjaxCommonTools(params,urlStr,returnType,asyncOption,handler){
	$.ajax({
		type : "post",
		url  : urlStr,
		data : params,
		dataType : returnType,
		async: asyncOption,
		success  : handler
	});
}


