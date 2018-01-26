<#import "/commons/page/page.ftl" as page>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>字典列表</title>
<link href="${base}/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/js/jquery.js"></script>
<script src="${base}/js/common.js" type="text/javascript"></script>
<script src="${base}/js/checkbox/CheckboxTools.js" type="text/javascript"></script>
<script src="${base}/js/page/jquery.page.js" type="text/javascript"></script>
<script>
	
	$(document).ready(function(){
		CheckBoxTools.checkOrUncheckAll('ids','chkBtn');
	});
	
	 //添加
	 function input(){
	 	document.location.href="${base}/datamap/DataMap_input.do";
	 }
	 
	 function flash(){
		$.ajax({
			        url: "${base}/datamap/DataMap_flash.do",
			        type: "post",
			        dataType:"json", 
			        success: function(data) {
			        	if("success" == data.operStatus){
			        		alert("刷新完成");
			        	}else{
			        		alert("刷新失败");
			        	}
			        	//var test=eval("("+data+")");
			        },
			        cache: false,
			        error: function() {
			            alert("超时");
			        }
			    });
	 }
	 
	//查看子字典
	 function show(id){
	 	document.location.href="${base}/admin/subdatamap_list.do?subDataMap.parent="+id;
	 }
	//修改平台
	function edit(){
		var ids= getSelectchx();
		if(ids.length==0){
			alert("请选择一条记录！");
			return;
		}
		if(ids.length>1){
			alert("只能选择一条记录！");
			return;
		}
		document.form1.action="${base}/datamap/DataMap_edit.do?dataMap.id="+ids[0];
		document.form1.submit();			
	}
	
	//删除
	function del(){
		var ids= getSelectchx();
		if(ids.length==0){
			alert("请选择记录！");
			return;
		}
		if(window.confirm("确认删除？")){
		document.location.href="${base}/datamap/DataMap_delete.do?ids="+ids.join(",");
		}
	}	
	
</script>
</head>
<body> 
<form id="form1" name="form1" action="${base}/datamap/DataMap_list.do" method="post">
	<div class="place">
    	<span>位置：</span>
	    <ul class="placeul">
		    <li><a href="#">系统管理</a></li>
		    <li><a href="#">数据字典</a></li>
	    </ul>
    </div>
    <div class="rightinfo">
		    <div class="tools">
		    	<ul class="toolbar">
			        <li onclick="javascript:input();"><span><img src="${base}/images/t01.png" /></span>添加</li>
			        <li onclick="javascript:edit();"><span><img src="${base}/images/t02.png" /></span>修改</li>
			        <li onclick="javascript:del();"><span><img src="${base}/images/t03.png" /></span>删除</li>
			        <li onclick="javascript:flash();"><span><img src="${base}/images/tt01.png" /></span>刷新</li>
		        </ul>
		    </div>
	    	<table class="tablelist">
		    	<thead>
			    	<tr>
				        <th><input id="chkBtn" type="checkbox" onclick="CheckBoxTools.checkOrUncheckAll('dataMapCheckbox','chkBtn');" /></th>
				        <th>名称</th>
				        <th>编码</th>
				        <th>备注</th>
				        <th>创建时间</th>
				        <th>创建人</th>
				        <th>操作</th>
			        </tr>
		        </thead>
		        <tbody>
		        <#list res.result as dataMap>
			        <tr>
				        <td><input name="ids" type="checkbox" value="${dataMap.id?if_exists}" /></td>
				        <td>${dataMap.name?if_exists}</td>
				        <td>${dataMap.code?if_exists}</td>
				        <td>${dataMap.remark?if_exists}</td>
				        <td><#if dataMap.createDate?has_content>${dataMap.createDate?string("yyyy-MM-dd HH:mm")}</#if></td>
				        <td>${dataMap.createPeople?if_exists}</td>
				        <td><a href="${base}/datamap/SubDataMap_list.do?subDataMap.parent=${dataMap.id?if_exists}" class="tablelink">查看</a>&nbsp;&nbsp;<a href="${base}/datamap/DataMap_edit.do?dataMap.id=${dataMap.id?if_exists}" class="tablelink">修改</a></td>
			        </tr> 
		        </#list>
		        </tbody>
	    	</table>
	    	<table width="100%"><tr><td><@page.pageTR formId="form1" page=res/></td></tr></table>
		    <div class="tip">
				<div class="tiptop"><span>提示信息</span><a></a></div>
				<div class="tipinfo">
		      		<span><img src="${base}/images/ticon.png" /></span>
			        <div class="tipright">
				        <p>是否确认对信息的删除 ？</p>
				        <cite>如果是请点击确定按钮 ，否则请点取消。</cite>
			        </div>
		        </div>
		        <div class="tipbtn">
			        <input id="sureButton" type="button"  class="sure" value="确定" />&nbsp;
			        <input id="cancelButton" type="button"  class="cancel" value="取消" />
		        </div>
		    </div>
    	</div>
    <script type="text/javascript">
		$('.tablelist tbody tr:odd').addClass('odd');
	</script>
</form>
</body>
</html>
