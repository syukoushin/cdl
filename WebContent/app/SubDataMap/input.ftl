<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>添加数据字典</title>
<link href="${base}/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/js/jquery.js"></script>
<script>

	//返回
	function go_back(){
		history.go(-1);
	}
	
	//添加
	function addImg(){
		document.myform.action="${base}/datamap/SubDataMap_save.do";
		document.myform.submit();			
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
		    <li><a href="#">添加配置</a></li>
	    </ul>
	</div>
<form id="myform" name="myform" action="" method="post">
<input type="hidden" id="parent1" name="subDataMap.parent" value="${subDataMap.parent?if_exists}">
    <div class="formbody">
    	<div class="formtitle"><span>字典信息</span></div>
	    <ul class="forminfo">
		    <li><label>名称</label><input placeholder="请输入名称" name="subDataMap.name" type="text" class="dfinput" /></li>
		    <li><label>编码</label><input placeholder="请输入编码" name="subDataMap.code" type="text" class="dfinput" /></li>
		    <li class="add"><input name="add" type="button" id="save" class="btn" onclick="addImg();" value="保存"/>&nbsp;&nbsp;<input type="button" id="cancel" class="btn" onclick="go_back();" value="取消"/></li>
	    </ul>
    </div>
</form>
</body>
</html>
