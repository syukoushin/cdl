
//CheckboxTools
var CheckBoxTools = {
	
	deleteAll : function(){//删除所有记录
		var checkboxName = arguments[0];// the name attribute of checkbox component
		var formRequestUrl = arguments[1];//表单请求action
		var transmitHiddenId = arguments[2];//隐藏域ID
		var arr = document.getElementsByName(checkboxName);
		var _checkboxLen = 0;
		if(arr){
			_checkboxLen = arr.length;
		}
		var obj = this.dealCheckedBoxs(checkboxName);
		if (obj) {
			var ids = obj.content;
			var count = obj.count;
			if (count < 1 && _checkboxLen > 0) {
				alert("请您选择要删除的记录！");
				return;
			}
			$("#"+transmitHiddenId).val(ids);
		} else {
			var msg = _checkboxLen > 0 ? "请您选择要删除的记录！" : "暂无可删除记录！";
			alert(msg);
			return;
		}
		if (confirm("删除后无法恢复，您确认删除吗？")) {
	  		$("#form1").attr("action", formRequestUrl);
	  		$("#form1").submit();
	  		$(document).find("input[type='button']").attr("disabled", "disabled");
	  	}
	},
	
	//处理checkbox
	dealCheckedBoxs : function(){
		var _arr = [];//temp array
		var obj;//temp Object
		var arr = document.getElementsByName(arguments[0]);
		if(arr){
		  	 var len = arr.length, i, count=0;
		  	 for(i=0;i<len;i++){
		  		var _tempObj = arr[i];
		  	 	if(_tempObj.checked){
		  	 		count++;
		  	 		_arr.push(_tempObj.value);
		  	 	}
		  	 }
		  	 if(_arr.length>0){
		  	 	 obj = {};
			  	 obj.content = _arr.join(",");
			  	 obj.count = count;
		  	 }
		}
		return obj;
	},
	
	//checked(unchecked) all 
	checkOrUncheckAll : function(){
		var checkboxName = arguments[0];// the name attribute of checkbox component
		var checkAllId = arguments[1];//全选/全不选checkboxId
		try{
			$("#"+checkAllId).change(function(){
			   var _arr = [];//temp array
			   var flag = $(this).attr("checked") == true ? true : false;
			   var arr = document.getElementsByName(checkboxName);
			   if(arr){
			  	 var len = arr.length, i;
			  	 for(i=0;i<len;i++){
			  		var obj = arr[i];
			  	 	obj.checked = flag;
			  	 	if(flag){
			  	 		_arr.push("'"+obj.value+"'");
			  	 	}
			  	 }
			   }
			});
		}catch(e){}
	}
};