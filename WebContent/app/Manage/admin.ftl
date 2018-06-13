<#import "../Manage/top.ftl" as top>
<#import "form01.ftl" as form01>
<#import "../Manage/common.ftl" as common>
<#import "../Manage/menu.ftl" as menu>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<title>工单管理页面</title>
		<link rel="stylesheet" type="text/css" href="${base}/css/common.css"/>
		<link rel="stylesheet" type="text/css" href="${base}/css/move.css"/>
	    <link rel="stylesheet" href="${base}/css/cms/paging.css"/>
		<link rel="stylesheet" href="${base}/css/cms/paging.css"/>
		<!--[if lt IE 9]>
	    <script src="${base}/js/jquery/1.7.1/jquery-1.7.1.min.js"></script>
	    <script src="${base}/js/cms/respond.min.js"></script>
	    <![endif]-->
	    <script src="${base}/js/jquery/1.7.1/jquery-1.7.1.min.js"></script>
		<script src="${base}/js/move.js" type="text/javascript" charset="utf-8"></script>
    	<script type="text/javascript" src="${base}/js/core/jquery.tmpl.min.js"></script>
    	<script type="text/javascript" src="${base}/js/core/query.js"></script>
    	<script type="text/javascript" src="${base}/js/core/paging.js"></script>
    	<script type="text/javascript" src="${base}/uum/js/uum-tree.js"></script>
    	<script src="${base}/js/ajaxfileupload.js"></script>
		<style type="text/css">
			@font-face {
				font-family:'microsoft yahei'
				src: url("css/SourceHanSansCN-Normal.otf");
			}
			.siyuannormal,
			.ranking_table tr th,
			.ranking_table tr td
			.ranking_table tr td a
			{
				font-family:'微软雅黑';
			}
		</style>
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
					
					<div class="create_right fr">
						<div class="create_table_top">
							<p class="siyuannormal">常用操作</p>
						</div>
						<div class="create_table_content">
						<form id="myForm" action="${base}/manage/Manage_ajaxAdd.do" method="post" enctype="multipart/form-data">
							<table border="0" cellspacing="" cellpadding=""><tbody>
								<tr>
									<td>刷新数据字典</td>
									<td><input onclick="flush()" style="margin-left:90px;" type="button" value="确定" /></td>
								</tr>
								<tr>
									<td>安装包</td>
									<td>
									<input name="attachFile"  id="attachFile"  type="file"  />
									</td>
								</tr>
								<tr>
									<td>版本号</td>
									<td><input id="version" name="version" type="text" class="dfinput" />
									</td>
								</tr>
								<tr>
									<td>是否强制更新</td>
									<td>
										<label> <input type="radio" name="updateFor" id="updateFor01" value="1" checked="true" /><span class="siyuannormal">是</span></label>
										<label> <input type="radio" name="updateFor" id="updateFor02" value="0" /><span class="siyuannormal">否</span></label>
									</td>
								</tr>
								<tr>
									<td></td>
									<td>
									<input onclick="setAttachPath(this)" style="margin-left:90px;" type="button" value="确定" />
									</td>
								</tr>
								</tbody>
							</table>
						</form>
						</div>
					</div>
				</div>
			</div>
			<!--footer-->
			<div class="footer"></div>
		</div>
	</body>
<script>
	function flush() {
		$.ajax({
	        url: "${base}/datamap/DataMap_flash.do",
	        type: "post",
	        data: {},
	        async:false,
	        dataType: "json",
	        success: function(json){
	        	if(json.operStatus =='success'){
		            alert("刷新成功");
	        	}
	        },
	        error: function(){}
		});
	};
    function setAttachPath(obj){
        doUploadFile();
    }
    /**上传附件**/
    function doUploadFile(){
    	var fileAttach = $("#attachFile").val();
    	var version =$("#version").val();
    	var update =  $("input[name='updateFor']:checked").val();
    	if(fileAttach == "" || fileAttach == undefined){
    		return;
    	}
        $.ajaxFileUpload({
            url: '${base}/manage/Manage_ajaxUploadFile.do', //用于文件上传的服务器端请求地址
            secureuri: false, //是否需要安全协议，一般设置为false
            fileElementId: 'attachFile', //文件上传域的ID
            data: {version : version,updateFlag:update}, //此参数非常严谨，写错一个引号都不行
            dataType: 'text', //返回值类型 一般设置为json
            success: function (data, status)  //服务器成功响应处理函数
            {
                console.log(data);
                var dj =  JSON.parse(data);
                if(dj.optSts == '0'){
                    alert("上传失败");
                    return;
				}
            	alert("上传成功");
            },
            error: function (data, status, e)//服务器响应失败处理函数
            {
           	 alert("上传失败");
            }
        });
    }
</script>
</html>
		
