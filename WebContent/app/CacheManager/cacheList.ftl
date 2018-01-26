<#setting number_format="0.#">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
function clearCache(cacheName){
	$.ajax({
   			type: "POST",
			url: "${base}/admin/CacheManager_clearCache.do",
			data: "cacheName="+cacheName,
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
	    <li><a href="#">缓存管理</a></li>
    </ul>
</div>
<div class="rightinfo">
    <table class="tablelist">
      <thead>
        <th width="20%">CacheName</th>
		<th>总数量</th>
		<th>内存数量</th>
		<th>硬盘数量</th>
		<th>总命中次数</th>
		<th>内存命中次数</th>
		<th>硬盘命中次数</th>
		<th>总大小</th>
		<th>内存大小</th>
		<th>硬盘大小</th>
		<th>最大数量</th>
		<th>内存最大数量</th>
		<th>硬盘最大数量</th>
		<th>操作</th>
      </thead>
<#list cacheVOList as cacheVO>
      <tr>
        <td  width="20%">${cacheVO.cacheName}</td>
        <td>${cacheVO.totalCacheCount}</td>
     	<td>${cacheVO.inMemoryCacheCount}</td>
     	<td>${cacheVO.onDiskCacheCount}</td>
        <td>${cacheVO.totalHitCount}</td>
     	<td>${cacheVO.inMemoryHitCount}</td>
     	<td>${cacheVO.onDiskHitCount}</td>
     	<td>
     		<#if cacheVO.totalCacheSize gt 1024*1024>
     			${cacheVO.totalCacheSize/(1024*1024)}&nbsp;M
     		<#elseif cacheVO.totalCacheSize gt 1024>
     			${cacheVO.totalCacheSize/1024}&nbsp;K
     		<#else>
     			${cacheVO.totalCacheSize}&nbsp;B
     		</#if>
     	</td>
     	<td>
     		<#if cacheVO.inMemoryCacheSize gt 1024*1024>
     			${cacheVO.inMemoryCacheSize/(1024*1024)}&nbsp;M
     		<#elseif cacheVO.inMemoryCacheSize gt 1024>
     			${cacheVO.inMemoryCacheSize/1024}&nbsp;K
     		<#else>
     			${cacheVO.inMemoryCacheSize}&nbsp;B
     		</#if>
     	</td>
     	<td>
     		<#if cacheVO.onDiskCacheSize gt 1024*1024>
     			${cacheVO.onDiskCacheSize/(1024*1024)}&nbsp;M
     		<#elseif cacheVO.onDiskCacheSize gt 1024>
     			${cacheVO.onDiskCacheSize/1024}&nbsp;K
     		<#else>
     			${cacheVO.onDiskCacheSize}&nbsp;B
     		</#if>
     	</td>
     	<td>${cacheVO.configTotalMaxCount}</td>
     	<td>${cacheVO.configMaxElementsInMemory}</td>
     	<td>${cacheVO.configMaxElementsOnDisk}</td>
     	<td width="10%" style="text-align:center"><a href="${base}/admin/CacheManager_findElementsByCacheName.do?cacheName=${cacheVO.cacheName}">详细</a>&nbsp;&nbsp;
     	<a href="javascript:void(0)" onclick="clearCache('${cacheVO.cacheName}');">清空</a></td>
	</#list> 
    </table>
    <script type="text/javascript">
		$('.tablelist tbody tr:odd').addClass('odd');
	</script>
</div>
</body>
</html>