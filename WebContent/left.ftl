<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>功能导航</title>
<link href="${base}/css/style.css" rel="stylesheet" type="text/css" />
<script src="${base}/js/jquery/1.4.4/jquery.min.js" type="text/javascript" ></script>
<script src="${base}/js/core/ajaxtools.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){	
	//导航切换
	$(".menuson li").click(function(){
		$(".menuson li.active").removeClass("active")
		$(this).addClass("active");
	});
	
	$('.title').click(function(){
		var $ul = $(this).next('ul');
		$('dd').find('ul').slideUp();
		if($ul.is(':visible')){
			$(this).next('ul').slideUp();
		}else{
			$(this).next('ul').slideDown();
		}
	});
	
});
</script>

</head>

<body style="background:#f0f9fd;width: 187px;text-align: left;margin:0;min-width: 187px;">
	<div class="lefttop"><span></span>功能导航</div>
   	<dl class="leftmenu">
 		<#list functionList as item>
 			<dd>
			<div class="title">
				<span><img src="${base}/images/${item.image?if_exists}" /></span>${item.name?if_exists}
			</div>
			<ul class="menuson">
				<#if item.children?has_content >
					<#list item.children as subItem>
						<li <#if subItem_index==0 && item_index==0>class="active"</#if>>
							<#if subItem_index==0 && item_index==0>
								<input id="defaultSrc" value="${base}/${subItem.url?if_exists}" type="hidden"/>
								<script>
										window.parent.document.getElementById('rightFrame').contentWindow.location.href = $("#defaultSrc").val();
								</script>
							</#if>
						<cite></cite>
						<a href="${base}/${subItem.url?if_exists}" target="rightFrame">${subItem.name?if_exists}</a><i></i></li>
					</#list>
				</#if>
			</ul>    
			</dd>
		</#list>
	</dl>
</body>
</html>
