<#import "../Manage/top.ftl" as top>
<#import "../Manage/menu.ftl" as menu>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta charset="UTF-8">
		<title>查看发票详情</title>
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<link rel="stylesheet" type="text/css" href="${base}/css/common.css"/>
		<link rel="stylesheet" type="text/css" href="${base}/css/move.css"/>
		<link rel="stylesheet" href="${base}/css/cms/paging.css"/>
    	<link href="${base}/js/easyui/themes/default/easyui.css" rel="stylesheet" type="text/css" />
		<script src="${base}/js/jquery/1.9.1/jquery-1.9.1.min.js"></script>
		<script src="${base}/js/move.js" type="text/javascript" charset="utf-8"></script>
    	<script type="text/javascript" src="${base}/js/core/jquery.tmpl.min.js"></script>
    	<script type="text/javascript" src="${base}/js/core/json2.js" ></script>
    	<script type="text/javascript" src="${base}/js/calendar/My97DatePicker/WdatePicker.js"></script>
    	<script type="text/javascript" src="${base}/uum/js/easyui/jquery.easyui.min.js"></script>
    	<script type="text/javascript" src="${base}/uum/js/uum-tree.js"></script>
    	<script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=97c8589b70203dbf43c13e82f1573e1c"></script> 
		<style type="text/css">
			@font-face {
				font-family: "microsoft yahei";
				src: url("${base}/css/SourceHanSansCN-Normal.otf");
			}
			.siyuannormal,
			.ranking_table tr th,
			.ranking_table tr td
			.ranking_table tr td a
			{
				 font-family:'微软雅黑';
			}
			#container{}  
		</style>
	</head>
	<body style="background: #eeeeee;">
		<div class="box">
			<!--header-->
			<@top.top/>
			<div class="main">
				<div class="container">
					<div class="slide_left fl">
					<@menu.menu/>
					</div>
					<div class="create_right fr">
						<div class="create_table_top">
							<p class="siyuannormal">查看详情</p>
						</div>
						<div class="create_table_content">
						<form id="myForm" action="${base}/admin/WordOrder_ajaxAdd.do" method="post" enctype="multipart/form-data">
							<table border="0" cellspacing="" cellpadding=""><tbody>
								<tr>
									<td class="span_tdl siyuannormal">开票日期：</td>
									<td class="span_tdr siyuannormal">
										${entity.printDate?if_exists}
									</td>
								</tr>
								<tr>
									<td class="span_tdl siyuannormal">发票号码：</td>
									<td class="span_tdr siyuannormal">
										${entity.number?if_exists}
									</td>
								</tr>
								<tr>
									<td class="span_tdl siyuannormal">购买方税号：</td>
									<td class="span_tdr siyuannormal">
										${entity.name?if_exists}
									</td>
								</tr>
								<tr><td class="span_tdl siyuannormal">发票代码：</td>
									<td class="span_tdr siyuannormal">
										${entity.invoiceNo?if_exists}
									</td>
								</tr>
								<tr ><td class="span_tdl siyuannormal">车架号码：</td>
									<td  class="span_tdr siyuannormal">
									${entity.frameNo?if_exists}
									</td>
								</tr>
								<tr ><td class="span_tdl siyuannormal">价税合计：</td>
									<td  class="span_tdr siyuannormal">
									${entity.tax?if_exists}
									</td>
								</tr>
								<tr><td class="span_tdl siyuannormal">上传时间：</td>
									<td  class="span_tdr siyuannormal">
									${entity.createTime?string("yyyy-MM-dd HH:mm:ss")}
								</td>
								<tr><td class="span_tdl siyuannormal">上传账号：</td>
									<td  class="span_tdr siyuannormal">
									${entity.createUser?if_exists}
								</td>
								<#if attachmentList?has_content>
									<tr><td class="span_tdl siyuannormal">发票图片：</td>
										<td class="span_tdr siyuannormal" style="position:relative;">
											<#list attachmentList?if_exists as item>
												<img width=100px height=100px src="${base}/attachment/download.do?id=${item.id}" onclick="showBigImg(this);">
											</#list>
										</td>
									</tr>
								</#if>
									<tr><td class="span_tdl siyuannormal"></td>
										<td class="span_tdr siyuannormal td_btn">
											<input onclick="goList()" style="font-family:'微软雅黑';" type="button" value="确定" />
										</td>
									</tr>
								</tbody>
							</table>
						</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div id="mapper" class="mask_box" style="display:none"></div>
		<div id="addWindow" style="display:none" class="mask_content">
			<div class="mask_content_top"><p>图片</p><span class="mark_close"></span></div>
			<div style="width:600px;height:300px" id="container"></div>
		</div>
		<div id="showBigImg" style="display:none" class="mask_content" style="top:30%">
			<div class="mask_content_top"><p>大图显示</p><span class="mark_close"></span></div>
			<div style="width:600px;height:400px;text-align:center" id="bigImgDiv">
				<img id="bigImg" src="" style="height:100%;"></img>
			</div>
		</div>
	</body>
	<script>
		function init(cood){
			$("#addWindow").show();
	        $(".mask_box").show();
			strs = cood.split(",");
			var x = strs[0];
			var y = strs[1];
	        var map = new AMap.Map('container', {
	            center: [x,y],
	            zoom: 17
	        });
	        map.plugin(["AMap.ToolBar"], function() {
	            map.addControl(new AMap.ToolBar());
	        });
	        marker = new AMap.Marker({
	            icon: "http://webapi.amap.com/theme/v1.3/markers/n/mark_b.png",
	            position: [x,y]
	        });
	        marker.setMap(map);

	    }
		function goList(){
			window.location ="${base}/manage/Invoice_toList.do";
		}
		function bigImg(obj){
			$(obj).next("div").fadeIn(500);
			$(".the_background_layer_mask").show();
		}
		function showBigImg(obj){
			$("#bigImg").attr('src',obj.src);
			$("#showBigImg").show();
	        $(".mask_box").show();
		}
	</script>
</html>
