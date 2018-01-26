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
	
	//修改
	function update(){
		document.myform.action="${base}/datamap/SubDataMap_update.do";
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
		    <li><a href="#">修改配置</a></li>
	    </ul>
	</div>
<form id="myform" name="myform" action="" method="post">
<input type="hidden" id="subDataMap.id" name="subDataMap.id" value="${subDataMap.id?if_exists}">
<input type="hidden" id="parent" name="subDataMap.parent" value="${subDataMap.parent?if_exists}">
	<div class="formbody">
    	<div class="formtitle"><span>字典信息</span></div>
	    <ul class="forminfo">
		    <li><label>名称</label><input name="subDataMap.name" type="text" class="dfinput" value="${subDataMap.name?if_exists}"/></li>
		    <li><label>编码</label><input name="subDataMap.code" type="text" class="dfinput" value="${subDataMap.code?if_exists}"/></li>
		    <li class="add"><input name="add" type="button" id="save" class="btn" onclick="update();" value="修改"/>&nbsp;&nbsp;<input type="button" id="cancel" class="btn" onclick="go_back();" value="取消"/></li>
	    </ul>
    </div>
</form>
</body>
</html>
