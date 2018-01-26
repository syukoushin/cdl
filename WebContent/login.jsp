<%@ page language="java" contentType="text/html;charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	String context = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=EDGE">
<title>后台管理系统</title>
<link rel="stylesheet" href="css/cms/common1.css">
<link rel="stylesheet" href="css/cms/login_two.css">
<script src="js/jquery.min.js" type="text/javascript"></script>
<script src="/cdl/js/des.js" type="text/javascript"></script>
<script src="/cdl/js/core/ajaxtools.js" type="text/javascript"></script>
<script language="javascript">
	$(function(){
	    $('.loginbox').css({'position':'absolute','left':($(window).width()-692)/2});
		$(window).resize(function(){  
	    	$('.loginbox').css({'position':'absolute','left':($(window).width()-692)/2});
	   });
		$('#checkCode').focus(function(){
			if($(this).val() == "请输入验证码..."){
				$(this).val("");
			}
		});
		$('#checkCode').blur(function(){
			if($.trim($(this).val()) == ""){
				$(this).val("请输入验证码...");
			}
		});
	});  
	function checkLogin(){
		var uname = $("#username").val();
		var upass = $("#password").val();
		if($.trim(uname) == '' || $.trim(upass) == ''){
			alert("用户名或密码为空");
			return false;
		}
		if (!check()) {
			return false;
		};
		var encpassowrd = strEnc(upass,"1","2","3");
		$("#password").val(encpassowrd);
		document.getElementById("form1").submit();
	}
	function keydown() {
		if (event.keyCode==13) {
			checkLogin();
		}
	}
	
	if(window.top.location != self.location){
		window.top.location = self.location;
	}

	function getCheckCode(){
		document.getElementById("vcodesrc").src = "<%=request.getContextPath() %>/captcha/checkcode?"+Math.random();
	}

	function checkCodeResult(){
		var checkCode = document.getElementById("checkCode").value;
		var status = false;
		AjaxCommonTools("checkCode="+checkCode,"<%=request.getContextPath() %>/captcha/checkcode","json",false,function(data){
			if(data.status == 1){
				status = true;
			}else{
				status = false;
			}
		});
		return status;
	}

	function check() {
		var checkCode = document.getElementById("checkCode");
		if(checkCode) {
			if(checkCode.value.length <= 0 || checkCode.value == "请输入验证码...") {
				alert("请输入验证码!");
				checkCode.focus();
				return false;
			}
		} else {
			return false;
		}
		if(!checkCodeResult()){
			alert("验证码输入错误！");
			getCheckCode();
			return false;
		}
		return true;
	}
</script> 
</head>

<body style="overflow:hidden;">
<form id="form1" action="/cdl/login.do" method="post">
    <!-- <div class="bg_img">
    </div> -->
<div>   
    <div class="sign_in">
		<img src="images/cms/mr_qp.png" class="bt_text">
		
		<div class="login">
			<div class="collection">
				<dl>
					<dt>用户名：</dt>
					<dd>
						<input id="username" type="text" class="text_inputs" name="userCode" maxlength="30"  value="${errMsg}">
					</dd>
				</dl>
				<dl>
		      		<dt>密&nbsp;&nbsp;码：</dt>
		      		<dd style="float:left;">
		        		<input id="password" type="password" class="text_inputs" name="passWord" maxlength="30"  value="">
		      		</dd>
	  			</dl>
	  			<dl>
		        		<dd style="height: 36px; ling-height: 36px;margin-left: 60px;"><input type="text" id="checkCode" name="checkCode" class="checkcode" value="请输入验证码..." maxlength="20" onkeydown="keydown();" style='width:90px;height:30px;line-height:30px;'/><a href="javaScript:getCheckCode()" title="点击，换一张！"><img src="<%=request.getContextPath() %>/captcha/checkcode" id="vcodesrc" name="vcodesrc" border="0" width="80" height="36" style="float:right;" alt="点击，换一张！" /></a></dd>
	  			</dl>
				<dl>
			    	<dt>&nbsp;</dt>
			    	<dd>
				        <div class="dl_button">
				        	<ul>
					            <li><input  type="button"  onclick="checkLogin();" href="" class="logins" style="margin-left:-20px; cursor:pointer;" value="登录"></li>
					            <li><a href=""  class="logins">重置</a></li>
				        	</ul>
				        </div>
					</dd>
				</dl>
			</div>
		</div>
    </div>
</div>
</form>
</body>
<script>
</script>
</html>