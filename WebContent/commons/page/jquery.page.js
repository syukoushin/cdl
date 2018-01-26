var path = "/cwproject";

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
	$("#form1").submit();
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
