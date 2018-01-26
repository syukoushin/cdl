<#setting number_format="0.#">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="ie=7" />
<title></title>
<link href="${base}/css/style.css" rel="stylesheet" type="text/css" />
<link href="${base}/css/asset/style.css" rel="stylesheet" type="text/css" />
<link href="${base}/js/easyui/themes/icon.css" rel="stylesheet" type="text/css" />
<link href="${base}/js/easyui/themes/default/easyui.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/js/jquery/1.8.2/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${base}/js/jquery.js"></script>
<script src="${base}/js/common.js" type="text/javascript"></script>
<script src="${base}/js/checkbox/CheckboxTools.js" type="text/javascript"></script>
<script type="text/JavaScript">
function changeCheck() {
	var keyNames = document.getElementsByName("keyNames");
	if($("#checkAll").attr("checked")) {
		for(var i=0;i<keyNames.length;i++) {
			keyNames[i].checked = true;
		}	
	} else {
		for(var i=0;i<keyNames.length;i++) {
			keyNames[i].checked = false;
		}
	}
}
function checkAllChange(keyName) {
	if(!keyName.checked) {
		document.getElementById("checkAll").checked = false;
	}
}
function clearElement(cacheName){
	var vkeyNames = document.getElementsByName("keyNames");
	var keyNames = "";
	var vkeyLength = vkeyNames.length;
	for(var i=0;i<vkeyLength;i++) {
		if(vkeyNames[i].checked) {
			keyNames =  keyNames + vkeyNames[i].value +"@";
		}
	}
	if(keyNames == ""){
		alert("请选择要清空的缓存！");
		return;
	}
	
	var checkAll = "0";
	if(document.getElementById("checkAll").checked) {
		checkAll = "1";
	}
	$.ajax({
		type: "POST",
		url: "${base}/admin/CahceManager_clearElements.do",
		data: "checkAll="+checkAll+"&cacheName="+cacheName+"&keyNames="+keyNames,
		success: function(msg){
	    	if("true"==msg){
	   			alert("清空缓存成功!");
	   			location.reload();
    		}	else {
				alert("清空缓存失败!");
    		}
    	}
	});
}
</script>
</head>
<body>
<div class="place">
	<span>位置：</span>
    <ul class="placeul">
	    <li><a href="#">系统管理</a></li>
	    <li><a href="#">缓存信息管理</a></li>
    </ul>
</div>
<div class="rightinfo">
    <table class="tablelist">
	<thead>
  		<th></th>
		<th>KeyName</th>
		<th>创建时间</th>
		<th>缓存大小</th>
		<th>是否在内存中</th>
		<th>是否在硬盘中</th>
		<th>命中次数</th>
  	</thead>
  	<tbody>
    <#assign count=0>
    <#assign countSize=0>
    <#assign hitCount=0>
    <#list cacheElementList as cacheElementVO>
    	  <#assign count=count+1>
    	  <#assign hitCount=hitCount+cacheElementVO.hitCount>
    	  <#assign countSize=countSize+cacheElementVO.cacheSize>
          <tr>
          	<td style="text-align:center"><input type="checkbox" id="${cacheElementVO.keyName}" value="${cacheElementVO.keyName}" name="keyNames" onclick="checkAllChange(this)"></td>
            <td style="text-align:center">${cacheElementVO.keyName}</td>
            <td style="text-align:center">${cacheElementVO.createTime}</td>
         	<td style="text-align:center">
         		<#if cacheElementVO.cacheSize gt 1024*1024>
         			${cacheElementVO.cacheSize/(1024*1024)}&nbsp;M
         		<#elseif cacheElementVO.cacheSize gt 1024>
     				${cacheElementVO.cacheSize/1024}&nbsp;K
     			<#else>
         			${cacheElementVO.cacheSize}&nbsp;B
         		</#if>	
         	</td>
         	<td style="text-align:center">
         		<#if cacheElementVO.inMemory>
         			是
         		<#else>
         			否
         		</#if>
         	</td>
         	<td style="text-align:center">
         		<#if cacheElementVO.onDisk>
         			是
         		<#else>
         			否
         		</#if>
         	</td>
         	<td style="text-align:center">${cacheElementVO.hitCount}</td>
         </tr>	
    </#list> 
    	 <tr>
    	 	<td style="text-align:center">合计</td>
    	 	<td style="text-align:center">${count}个</td>
    	 	<td style="text-align:center">----------------</td>
    	 	<td style="text-align:center">
    	 		<#if countSize gt 1024*1024>
         			${countSize/(1024*1024)}&nbsp;M
         		<#elseif countSize gt 1024>
     				${countSize/1024}&nbsp;K
     			<#else>
         			${countSize}&nbsp;B
         		</#if>
    	 		
    	 	</td>
    	 	<td style="text-align:center">
    	 	---------
    	 	</td>
    	 	<td style="text-align:center">
    	 	---------
    	 	</td>
    	 	<td style="text-align:center">
    	 		${hitCount}
    	 	</td>
    	 </tr>	
    	 <tr style="border-bottom:1px solid #cbcbcb;">
    	 	<td style="text-align:center"><input type="checkbox" name="checkAll" id="checkAll" onclick="changeCheck();" value="1">全选</td>
    	 	<td colspan="6" style="text-align:center"><a href="javascript:void(0)" onclick="clearElement('${cacheName}')">删除</a></td>
    	 <tr>
    	 </tbody>
        </table>
        <script type="text/javascript">
			$('.tablelist tbody tr:odd').addClass('odd');
		</script>
 </div>
 <div style="text-align:center"><input type="button" class="btn" value="返回" style="align:center" onclick="window.open('${base}/admin/CacheManager_findCaches.do','rightFrame');"></div>
</body>
</html>