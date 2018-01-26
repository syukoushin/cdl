<#import "/commons/page/page.ftl" as page>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>字典列表</title>
<link href="${base}/css/style.css" rel="stylesheet" type="text/css" />
<link href="${base}/css/asset/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/js/jquery.js"></script>
<script src="${base}/js/common.js" type="text/javascript"></script>
<script src="${base}/js/checkbox/CheckboxTools.js" type="text/javascript"></script>
<script src="${base}/js/page/jquery.page.js" type="text/javascript"></script>
<script>

	$(document).ready(function(){
		CheckBoxTools.checkOrUncheckAll('ids','chkBtn');
	});
	 //添加
	 function to_input(){
	 	var parent1 =document.getElementById("parent1").value;
	 	document.location.href="${base}/datamap/SubDataMap_input.do?subDataMap.parent="+parent1;
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
		document.myform.action="${base}/datamap/SubDataMap_edit.do?subDataMap.id="+ids[0];
		document.myform.submit();			
	}
	
	//删除
	function del(){
		var ids= getSelectchx();
		if(ids.length==0){
			alert("请选择记录！");
			return;
		}
		var parent1 =document.getElementById('parent1').value;
		if(window.confirm("确认删除？")){
		document.location.href="${base}/datamap/SubDataMap_delete.do?ids="+ids.join(",")+"&subDataMap.parent="+parent1;
		}
	}	
	
	//查询
	function query(){
		$("#myform").submit();			
	}
	
</script>
</head>
<body>
	<div class="place">
    	<span>位置：</span>
	    <ul class="placeul">
		    <li><a href="#">系统管理</a></li>
		    <li><a href="#">数据字典</a></li>
		    <li><a href="#">字典详情</a></li>
	    </ul>
	</div>
<div class="rightinfo">
	<div id="tab1" class="tabson">
		<form id="myform" name="myform" action="${base}/admin/subdatamap_list.do" method="post">
			<input type="hidden" id="parent1" name="subDataMap.parent" value="${subDataMap.parent?if_exists}">
    		<ul id="conditionForm" class="forminfo" style="height: 30px;">
	             <li>
	             	<label>编码：</label>
	             	<input name="subDataMap.code"  placeholder="请输入编码" type="text" class="dfinput" value="" style="width: 166px;" />
	             </li>
	             <li>
	             	<label>&nbsp;</label>
	             	<input id="findAsset" name="findAsset" type="button" class="btn" value="查询" onclick="query();"/>
	             </li>
			</ul>
		    <div class="tools">
		    	<ul class="toolbar">
			        <li onclick="javascript:to_input();"><span><img src="${base}/images/t01.png" /></span>添加</li>
			        <li onclick="javascript:edit();"><span><img src="${base}/images/t02.png" /></span>修改</li>
			        <li onclick="javascript:del();"><span><img src="${base}/images/t03.png" /></span>删除</li>
		        </ul>
		    </div>
		    <table class="tablelist">
		    	<thead>
			    	<tr>
				        <th><input id="chkBtn" type="checkbox" onclick="CheckBoxTools.checkOrUncheckAll('dataMapCheckbox','chkBtn');" /></th>
				        <th>名称</th>
				        <th>编码</th>
			        </tr>
		        </thead>
		        <tbody>
		        <#list res.result as item>
			        <tr>
				        <td><input name="ids" type="checkbox" value="${item.id?if_exists}" /></td>
				        <td>${item.name?if_exists}</td>
				        <td>${item.code?if_exists}</td>
			        </tr> 
		        </#list>
		        </tbody>
		    </table>
	    	<table width="100%"><tr><td><@page.pageTR formId="myform" page=res/></td></tr></table>
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
</div>
</body>
</html>
