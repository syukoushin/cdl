<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>头文件</title>
<link href="${base}/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/js/jquery/2.1.3/jquery-2.1.3.min.js"></script>
<script type="text/javascript" src="${base}/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${base}/js/core/ajaxtools.js"></script>
<script type="text/javascript" src="${base}/js/core/uitools.js"></script>
<script type="text/javascript">
var basePath = "${base}"; //项目根路径路径
var currentUser = "${Session["CURRENT_USER"].userName}"; //当前登录人
</script>
</head>
<body style="background:url(${base}/images/topbg.gif) repeat-x;">
    <div class="topleft">
    	<a href="javascript:window.location.reload();">
    	<img src="${base}/images/logo.png" title="系统首页" />
    	</a>
    </div>
    <div class="topright">    
	    <div class="user">
		    <span>您好，<font color="yellow"></font>
		    	<#if Session.CURRENT_USER.userCode?if_exists == 'administrator'>
		    		<font color="yellow" style="font-size:14px;">管理员</font>
		    	<#else>
		    		<a target="rightFrame" href="${base}/manager/userManager_gainPrivateMsg.do" title="点击修改个人资料">
		    			<font color="yellow" style="font-size:14px;">${Session.CURRENT_USER.userName?if_exists}</font>
		    		</a>
		    	</#if>
		    </span>
		    <a href="${base}/login.jsp" style="margin-right: 15px;color: white;" target="_parent">退出</a>
	    </div>    
    </div>
</body>
</html>
