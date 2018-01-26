//全选/发选
function checkAll(field) {
	$("input[name='ids']").attr("checked", field.checked);
}

//获取被选中的对象的值
function getSelectchx(){
	
	var arr = new Array();
	
	var i = 0;
	
	$("input[name='ids']").each(function(){
		
		if ($(this).attr("checked")) {
			
			arr[i++]=$(this).val();
		}
	});

	return arr;
}
//选中下拉列表
function setSelect(id,value) {
	$("#"+id).val(value);
}
