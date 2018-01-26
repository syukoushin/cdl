
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
										      <th>序号</th><th>名称</th><th>操作</th>
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
			<form id="myform" name="myform" action="" enctype="multipart/form-data" method="post">
				<div class="mask_content_top"><p style="font-family:'微软雅黑';">添加公司</p><span class="mark_close"></span></div>
				<div class="mask_content_con">
					<p><span class="span_left">名称：</span><input name="name" style="font-family:'微软雅黑';" type="text" placeholder="请输入名称" /></p>
				</div>
				<div class="mask_content_bot mt4">
					<input onclick="addPartGroup();" type="button" value="确定" class="confirm"/>
					<input type="button" value="取消" class="cancel"/>
				</div>
			</form>
		</div>
		<div id="editWindow" style="display:none" class="mask_content">
			<form>
				<div class="mask_content_top"><p style="font-family:'微软雅黑';">编辑公司</p><span class="mark_close"></span></div>
				<div class="mask_content_con">
				<input type="hidden" name="id" type="text" />
					<p><span class="span_left">名称：</span><input name="nameE" type="text"/></p>
				</div>
				<div class="mask_content_bot mt4">
					<input onclick="updatePartGroup();" type="button" value="确定" class="confirm"/>
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
                <td>{{= item.name}}</td>
                <td style="font-family:'微软雅黑';">
	                <a href="javascript:void(0)" onclick="editPartGroup('{{= item.id}}')" class="hover_a" style="margin-right:10px;" title="编辑">
	                	<img src="/cdl/images/cms/small_icon_6.png" style="border-radius:0; width:13px; height:13px;">
	                </a>
	                <a href="javascript:void(0)" onclick="deletePartGroup('{{= item.id}}')" class="hover_a" style="margin-right:10px;" title="删除">
	                	<img src="/cdl/images/cms/small_icon_4.png" style="border-radius:0; width:13px; height:13px;">
	                </a>
                </td>
            </tr>
		{{/each}}
	<tbody>
</script>
<script>
	function updatePartGroup(){
		var param={};
		var nameE= $("input[name='nameE']").val();
		if(!nameE){alert("请填写名称"); return};
		param["name"]= nameE;
		param["id"]=$("input[name='id']").val();
		$.ajax({
            url: '${base}/manage/Group_ajaxUpdateEntity.do',
            type: "post",
            data: param,
            dataType: 'json', //返回值类型 一般设置为json
            success: function (json)  //服务器成功响应处理函数
            {
                if(json.optSts =='0'){
                	window.location ='${base}/manage/Group_toList.do'
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
	function setPicturePath(obj) {
        $("#fakePicture").val(obj.value);
    }
	function deletePartGroup(id){
		if(!confirm("确定删除？")){return false};
		var param={};
		param["id"]=id;
		$.ajax({
            url: '${base}/manage/Group_ajaxDeleteEntity.do',
            type: "post",
            data: param,
            dataType: 'json', //返回值类型 一般设置为json
            success: function (json)  //服务器成功响应处理函数
            {
                if(json.optSts =='0'){
                	window.location ='${base}/manage/Group_toList.do'
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
	
	function editPartGroup(id){
		var param={};
		param["id"]=id;
		$.ajax({
            url: '${base}/manage/Group_ajaxFindEntity.do',
            type: "post",
            data: param,
            dataType: 'json', //返回值类型 一般设置为json
            success: function (json)  //服务器成功响应处理函数
            {
                if(json.optSts =='0'){
                	var data = json.data;
                	$("input[name='nameE']").val(data.name);
                	$("input[name='id']").val(data.id);
                	$("#editWindow").show();
                	$(".mask_box").show();
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
	
	function addPartGroup(){
		 var name = $("input[name='name']").val();
		 if(!name){alert("请填写名称"); return};
		 var param ={};
		 param["name"] = name;
		 $.ajax({
            url: '${base}/manage/Group_ajaxAddEntity.do',
            type: "post",
            data: param,
            dataType: 'json', //返回值类型 一般设置为json
            success: function (json)  //服务器成功响应处理函数
            {	
                if(json.optSts =='0'){
                	$("input[name='name']").val('');
                	window.location ='${base}/manage/Group_toList.do'
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
            url: "${base}/manage/Group_ajaxGroupList.do",
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
