<#macro pageTR formId page>
<link href="${base}/css/page.css" rel="stylesheet" type="text/css" />
<script>
var path = "${base}";
$(document).ready(
		function() {
			/* 1 首先删除所有排序字段的排序展示状态 */
			// $(".sortColumn > span").remove();
			$(".sortColumn > img").remove();
			/* 2 根据排序类型判断当前排序字段的展示状态 */
			var appendStr = "&#160;";
			var sortType = $("#sortType").val();
			if ("asc" == sortType) {
				appendStr += "<img src=\"" + path
						+ "/images/sortAsc.gif\" border=\"0\"  />";
				// appendStr += "<span class=\"webdingsStyle\">5</span>";
			} else {
				appendStr += "<img src=\"" + path
						+ "/images/sortDesc.gif\" border=\"0\"  />";
				// appendStr += "<span class=\"webdingsStyle\">6</span>";
			}

			/* 3 附加当前排序字段展示状态 */
			// $(appendStr).appendTo("#" + sortColumn + "TH");
			// $("#" + sortColumn + "TH").append(appendStr);
			// alert($("#" + sortColumn + "TH").html());
			/* 4 当点击字段排序时，触发click事件所执行的操作 */
			$(".sortColumn").click(function() {
				var endIndex = this.id.lastIndexOf("TH");
				if (endIndex > 0) {
					/* 获取要排序的字段 */
					var curColumn = this.id.substring(0, endIndex);
					/* 指定排序的类型 */
					if (curColumn == sortColumn && "asc" == sortType) {
						$("#sortType").val("desc");
					} else {
						$("#sortType").val("asc");
					}
					/* 指定排序的字段 */
					$("#sortColumn").val(curColumn);
					/* 从第一页开始重新查询 */
					$("#pageNo").val("1");
					/* 提交表单 */
					$("#form1").submit();
				}
			});
		});

/* 跳转到指定页码页面 */
function doPage(pageNo) {
	$("#pageNo").val(pageNo);
	$("#${formId?if_exists}").submit();
}

/* 检查输入的页码是否有效 */
function checkPage(pageCount) {
	var page = $("#pageNo").val();
	if (!page) {
		alert("页码数不能为空");
		return false;
	} else if (!page.match(/^\d+$/)) {
		alert("请输入正确的页码数");
		return false;
	}
	if (page > pageCount) {
		page = pageCount;
	}
	doPage(page);
}

function jumpPage(pageCount){
	var page = $("#jumppage").val();
	if (!page) {
		alert("页码数不能为空");
		return false;
	} else if (!page.match(/^\d+$/)) {
		alert("请输入正确的页码数");
		return false;
	}
	if (page > pageCount) {
		page = pageCount;
	}
	doPage(page);
	
}
</script>
<div class="pagin">
	<div class="message">共<i class="blue">${page.totalCount?string('###')}</i>条记录，当前显示第&nbsp;<i class="blue">${page.pageNo?string('###')}&nbsp;</i>页&nbsp;
		&nbsp;&nbsp;<a href="javascript:doPage(${page.prevNo?string('###')});"><i class="blue">上一页</i></a>&nbsp;&nbsp;<a href="javascript:doPage(${page.nextNo?string('###')});"><i class="blue">下一页</i></a>
		&nbsp;<input type="text" id="jumppage" value="" style="border:1px solid #DDD;width:40px;height:20px;"> <input type="button" value="GO" style="border:1px solid #DDD;width:40px;height:20px;" onclick="jumpPage(${page.pageCount?string('###')})">
	</div>
    <ul class="paginList">
	    <#if page.pageNo!=1>
	    <li class="paginItem"><a href="javascript:doPage(${page.firstNo});"><span class="pagepre"></span></a></li>
		</#if>
		<!--当总页数小于8 -->
		<#if page.totalCount<1>
			<li class="paginItem current"><a href="javascript:;">1</a></li>
	    <#elseif page.pageCount<8>
		    <#list page.startNo..page.endNo as x>
				<#if page.pageNo == x>
		<li class="paginItem current"><a href="javascript:;">${x?string('###')}</a></li>
				<#else>
		<li class="paginItem"><a href="javascript:doPage(${x?string('###')});">${x?string('###')}</a></li>
				</#if>
			</#list>
		<#elseif (page.pageCount-page.pageNo<=6)>	
		    <#list page.pageCount-6..page.pageCount as x>
				<#if page.pageNo == x>
		<li class="paginItem current"><a href="javascript:;">${x?string('###')}</a></li>
				<#else>
		<li class="paginItem"><a href="javascript:doPage(${x?string('###')});">${x?string('###')}</a></li>
				</#if>
			</#list>
		<#elseif (page.pageCount-page.pageNo>6)>
			<#if (page.pageNo<=2)>
			<#list page.pageNo..page.pageNo+4 as x>
				<#if page.pageNo == x>
		<li class="paginItem current"><a href="javascript:;">${x?string('###')}</a></li>
				<#else>
		<li class="paginItem"><a href="javascript:doPage(${x?string('###')});">${x?string('###')}</a></li>
				</#if>
			</#list>
			<#else>
			<#list page.pageNo-2..page.pageNo+2 as x>
				<#if page.pageNo == x>
		<li class="paginItem current"><a href="javascript:;">${x?string('###')}</a></li>
				<#else>
		<li class="paginItem"><a href="javascript:doPage(${x?string('###')});">${x?string('###')}</a></li>
				</#if>
			</#list>
			</#if>
		<li class="paginItem"><a href="javascript:;">...</a></li>
		<li class="paginItem"><a href="javascript:doPage(${page.pageCount?string('###')});">${page.pageCount?string('###')}</a></li>
		</#if>
        <#if page.pageNo!=page.pageCount&&page.pageCount!=0>
	    <li class="paginItem"><a href="javascript:doPage(${page.lastNo?string('###')});"><span class="pagenxt"></span></a></li>
		</#if>
    </ul>
	<input type="hidden" id="sortColumn" name="res.sortColumn"  value="${page.sortColumn}" />
	<input type="hidden" id="sortType" name="res.sortType"  value="${page.sortType}" />
	<input type="hidden" id="pageNo"  name="res.pageNo" value="${page.pageNo?string('###')}"/>
</div>
</#macro>