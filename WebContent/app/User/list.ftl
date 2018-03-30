
<#import "/app/Manage/top.ftl" as top>
<#import "../Manage/common.ftl" as common>
<#import "../Manage/menu.ftl" as menu>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<@common.common/>
	</head>
	<body style="background: #EEEEEE;">
		<div class="box">
			<!--header-->
			<@top.top/>
			<!--main-->
			<div class="main">
				<div class="container">
					<div class="slide_left fl">
					<@menu.menu/>
					</div>
					<div class="slide_right fr">
						<div class="slide_right_top">
							<ul  style="background: #fff;">
								<li id="addEnginner" class="btn_order fr" style="margin: 17px;">
									<a href="javascript:void(0)">添加</a>
								</li>
							</ul>
						</div>
						<div class="slide_right_content">
							<div class="slide_right_tab" >
								<div class="slide_table" >
									<table id="disTable" border="0" class="ranking_table">
										  <thead>
										    <tr>
										      <th>序号</th><th>用户名</th><th>姓名</th><th>账号类别</th><th>操作</th>
										    </tr>
										  </thead>
									</table>
									<!--分页-->
									<div id="pageToolbar"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
			<!--footer-->
			<div class="footer"></div>
		</div>
        <!--添加PC断账号弹窗-->
		<div class="mask_box" style="display:none"></div>
		<div id="addWindow" style="display:none" class="mask_content">
			<form>
				<div class="mask_content_top"><p style="font-family:'微软雅黑';">添加手机端账号</p><span class="mark_close"></span></div>
				<div class="mask_content_con">
					<p><span class="span_left">姓名：</span><input name="name" style="font-family:'微软雅黑';" type="text" placeholder="请输入姓名" /></p>
					<p><span class="span_left">用户名：</span><input name="userCode" style="font-family:'微软雅黑';" type="text" placeholder="请输入用户名" /></p>
					<#--<#if Session.CURRENT_USER.jobLevel =='1'>-->
						<#--<p><span class="span_left">所在公司：</span>-->
						<#--<select class="select3" style="font-family:'微软雅黑';" type="text" placeholder="请输入用户名" name="groupId" onchange="showGroup()" id ="groupId"></select>-->
						<#--</p>-->
					<#--</#if>-->
				</div>
				<div class="mask_content_bot mt4">
					<input onclick="addMember();" type="button" value="确定" class="confirm"/>
					<input type="button" value="取消" class="cancel"/>
				</div>
			</form>
		</div>
		<div id="editWindow" style="display:none" class="mask_content">
			<form>
				<div class="mask_content_top"><p style="font-family:'微软雅黑';">编辑PC端账号</p><span class="mark_close"></span></div>
				<div class="mask_content_con">
				<input type="hidden" name="id" type="text" />
					<p><span class="span_left">姓名：</span><input name="nameE" type="text"/></p>
					<p><span class="span_left">用户名：</span><input name="userCodeE" type="text"/></p>
					<#--<#if Session.CURRENT_USER.jobLevel =='1'>-->
						<#--<p><span class="span_left">所在公司：</span>-->
						<#--<select class="select3" style="font-family:'微软雅黑';" type="text" placeholder="请输入用户名" name="groupIdE" onchange="showGroupEdit()" id ="groupIdEdit"></select>-->
						<#--</p>-->
					<#--</#if>-->
				</div>
				<div class="mask_content_bot mt4">
					<input onclick="updateMember();" type="button" value="确定" class="confirm"/>
					<input type="button" value="取消" class="cancel"/>
				</div>
			</form>
		</div>
	</body>
<script id="pageList" type="text/x-jquery-tmpl">
	<tbody>
		{{each(i,item) result}}
			<tr style="font-family:'微软雅黑';">
				<td>{{= i + 1}}</td>
				<td>{{= item.userCode}}</td>
                <td>{{= item.userName}}</td>
                <td>{{if item.type=="1"}}客户端{{else}}管理员{{/if}}</td>
                <td style="font-family:'微软雅黑';">
                	<a href="javascript:void(0)" onclick="rePassword('{{= item.id}}')" class="hover_a" style="margin-right:10px;" title="重置密码">
                		<img src="/cdl/images/cms/small_icon_77.png" style="border-radius:0; width:13px; height:13px;">
                	</a>
	                <a href="javascript:void(0)" onclick="editMember('{{= item.id}}')" class="hover_a" style="margin-right:10px;" title="编辑">
	                	<img src="/cdl/images/cms/small_icon_6.png" style="border-radius:0; width:13px; height:13px;">
	                </a>
	                <a href="javascript:void(0)" onclick="deleteMember('{{= item.id}}')" class="hover_a" style="margin-right:10px;" title="删除">
	                	<img src="/cdl/images/cms/small_icon_4.png" style="border-radius:0; width:13px; height:13px;">
	                </a>
                </td>
            </tr>
		{{/each}}
	<tbody>
</script>
<script>
	$(document).ready(function(){
		/*showGroup();*/
	})
	function rePassword(id){
		var param ={};
		param["id"]=id;
		$.ajax({
            url: '${base}/manage/User_modifyPwd.do',
            type: "post",
            data: param,
            dataType: 'json', //返回值类型 一般设置为json
            success: function (json)  //服务器成功响应处理函数
            {
                if(json.optSts =='0'){
                	alert("重置成功");
                } else {
                	alert(json.optMsg);
                }
            },
            error: function ()//服务器响应失败处理函数
            {
                alert("服务器异常")
            }
        });
	}
	
	function showGroup() {
		$.ajax({
            url: '${base}/manage/Group_ajaxFindList.do',
            type: "post",
            dataType: 'json', //返回值类型 一般设置为json
            success: function (json)  //服务器成功响应处理函数
            {
                if(json.optSts =='0'){
                	var pgList = json.data;
                	for(var i=0,len=pgList.length; i < len; i ++){
	                	$("#groupId").append("<option value="+pgList[i].id+">"+pgList[i].name+"</option>");
                	}
                } else {
                	alert("删除失败");
                }
            },
            error: function ()//服务器响应失败处理函数
            {
                alert("服务器异常")
            }
        });
	}
	
	function showGroupEdit() {
		$.ajax({
            url: '${base}/manage/Group_ajaxFindList.do',
            type: "post",
            dataType: 'json', //返回值类型 一般设置为json
            success: function (json)  //服务器成功响应处理函数
            {
                if(json.optSts =='0'){
                	var pgList = json.data;
                	for(var i=0,len=pgList.length; i < len; i ++){
	                	$("#groupIdEdit").append("<option value="+pgList[i].id+">"+pgList[i].name+"</option>");
                	}
                } else {
                	alert("删除失败");
                }
            },
            error: function ()//服务器响应失败处理函数
            {
                alert("服务器异常")
            }
        });
	}
	
	function updateMember(){
		var param={};
		var nameE= $("input[name='nameE']").val();
/*//		var groupIdE= $("select[name='groupIdE']").val();*/
		if(!nameE){alert("请填写姓名"); return};
		var userCodeE =$("input[name='userCodeE']").val();
		if(!userCodeE){alert("请填写用户名"); return};
		param["userName"]= nameE;
		param["userCode"]= userCodeE;
///*		param["groupId"]= groupIdE;*/
		param["id"]=$("input[name='id']").val();
		$.ajax({
            url: '${base}/manage/User_ajaxUpdateEntity.do',
            type: "post",
            data: param,
            dataType: 'json', //返回值类型 一般设置为json
            success: function (json)  //服务器成功响应处理函数
            {
                if(json.optSts =='0'){
                	window.location ='${base}/manage/User_toList.do'
                } else {
                	alert(json.optMsg);
                }
            },
            error: function ()//服务器响应失败处理函数
            {
                alert("服务器异常")
            }
        });
	}
	function deleteMember(id){
		if(!confirm("确定删除？")){return false};
		var param={};
		param["id"]=id;
		$.ajax({
            url: '${base}/manage/User_ajaxDeleteEntity.do',
            type: "post",
            data: param,
            dataType: 'json', //返回值类型 一般设置为json
            success: function (json)  //服务器成功响应处理函数
            {
                if(json.optSts =='0'){
                	window.location ='${base}/manage/User_toList.do'
                } else {
                	alert("删除失败");
                }
            },
            error: function ()//服务器响应失败处理函数
            {
                alert("服务器异常")
            }
        });
	};
	
	function editMember(id){
		var param={};
		param["id"]=id;
		$.ajax({
            url: '${base}/manage/User_ajaxFindEntity.do',
            type: "post",
            data: param,
            dataType: 'json', //返回值类型 一般设置为json
            success: function (json)  //服务器成功响应处理函数
            {
                if(json.optSts =='0'){
                	var data = json.data;
                	$("input[name='nameE']").val(data.userName);
                	$("input[name='userCodeE']").val(data.userCode);
                	$("input[name='id']").val(data.id);
/*//                	$("#groupId").val(data.group);*/
                	$("#editWindow").show();
                	$(".mask_box").show();
                	/*showGroupEdit();*/
                } else {
                	alert("编辑失败");
                }
            },
            error: function ()//服务器响应失败处理函数
            {
                alert("服务器异常")
            }
        });
	};
	
	function addMember(){
		 var param ={};
		 var name = $("input[name='name']").val();
		 if(!name){alert("请填写姓名"); return};
		 var userCode = $("input[name='userCode']").val();
		 if(!userCode){alert("请填写用户名"); return};
		 param["userName"] = name;
		 param["userCode"] = userCode;
		 $.ajax({
            url: '${base}/manage/User_ajaxAddEntity.do',
            type: "post",
            data: param,
            dataType: 'json', //返回值类型 一般设置为json
            success: function (json)  //服务器成功响应处理函数
            {	
                if(json.optSts =='0'){
                	$("input[name='name']").val('');
                	$("input[name='userCode']").val('');
                	window.location ='${base}/manage/User_toList.do'
                }else{
                	alert(json.optMsg);
                }
            },
            error: function ()//服务器响应失败处理函数
            {
                alert("服务器异常")
            }
        });
	};
	function getPageList(pageNo){
		var param ={};
		param["pageNo"]=pageNo;
		$("#pageToolbar").remove();
    	$("#disTable").find("tr:gt(0)").remove();
		getMemberData(param);
	};
	function getMemberData(param){
        $.ajax({
            type: "post",
            url: "${base}/manage/User_ajaxUserList.do",
            data: param,
            dataType : 'json',
            success: function (json) {
                if(json.optSts == '0'){
                    var data = json.data;
                    var len = data.result.length;
                    $('<div id="pageToolbar" style="clear:both;"></div>').insertAfter("#disTable");
                    if(len == 0){
                        $("#pageToolbar").html('<tr><td colspan="9"><div style="width:500px;margin:0 auto"><span><font color="red">对不起</font>，没有符合条件的结果，请您检查查询条件是否合理或者更改查询条件进行查询。</span></div></td></tr>');
                        return;
                    }
                    $("#pageList").tmpl(data).appendTo("#disTable");
                    $('#pageToolbar').Paging({pagesize:10,current:data.pageNo,count:data.totalCount,callback:function(page,size,count){
                        getPageList(page);
                    }});
                }else{
                    $("#pageToolbar").append('<tr><td colspan="9"><div style="width:500px;margin:0 auto"><span><font color="red">对不起</font>，没有符合条件的结果，请您检查查询条件是否合理或者更改查询条件进行查询。</span></div></td></tr>');
                    return;
                }
            },
            error:function(){}
        });
	};
	getPageList(1);
</script>
</html>
