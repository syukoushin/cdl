/**
 * js表单验证工具
 */
/***************************************************************************************/
/**
 * 去除整个fomr表单的左右空格 调用说明：在提交之前加上去空格函数 author muxw
 */
function trimAll(form) {
	for (var i = 0; i < form.elements.length; i++)
		if (form.elements[i].type == "text"
				|| form.elements[i].type == "textarea") {
			var flag = false;
			var val = form.elements[i].value;
			var val1 = "";
			if (form.elements[i].value != null && form.elements[i].value != "") {
				flag = true;
			}
			while (flag) {
				for (j = 0; j < form.elements[i].value.length; j++) {
					var letter = form.elements[i].value.charAt(j)
					if (" ".indexOf(letter) != -1 || "　".indexOf(letter) != -1) {
						val = form.elements[i].value.substring(j + 1,
								form.elements[i].value.length);
						if (j == (form.elements[i].value.length - 1)) {
							flag = false;
							break;
						}
					} else {
						flag = false;
						break;
					}
				}
			}
			val1 = val;
			if (val.length > 0)
				flag = true;
			while (flag) {
				for (k = val.length - 1; k < val.length; k--) {
					var letter = val.charAt(k)
					if (" ".indexOf(letter) != -1 || "　".indexOf(letter) != -1) {
						val1 = val.substring(0, k);
					} else {
						flag = false;
						break;
					}
				}
			}
			form.elements[i].value = jsTrim(val1);
		}

}


/**
 * 清空表单名称在ClearNameArray的字段
 * @param form
 */
function clearField(form,ClearNameArray){
	for ( var i = 0; i < form.elements.length; i++) {
		var field = form.elements[i];
		if(ClearNameArray!=null&&ClearNameArray.length>0){
			for(var w=0;w<ClearNameArray.length;w++){
				if(field.name==ClearNameArray[w]){
					var fieldType = field.type.toLowerCase();
					if (fieldType != "submit" && fieldType != "reset"
							&& fieldType != "button") {
						if (fieldType == "radio" || fieldType == "checkbox") {
							field.checked = false;
						} else if (fieldType == "select") {
							field.selected = false;
						} else {
							field.value = "";
						}
					}
				}
			}
		}
	}
}

/**
 * 去掉输入域的空格，包括文字中间的空格 return false or true.
 */
function jsTrim(str) {
	var re;
	re = /^[ \t]*|[ \t]*$/g;
	str = str.replace(re, '');
	// modify by fupeng on 2007-12-11.remove \r\n
	// var p=/[ \t\v\f]/g;
	var p = /\s/g;
	str = str.replace(p, '');
	return (str);
}

/**
 * 去除单行输入框的左右空格 调用说明：在提交之前加上去空格函数
 */
function trimText(obj) {
	var flag = false;
	var val = obj.value;
	var val1 = "";
	if (obj.value != null && obj.value != "")
		flag = true;
	while (flag) {
		for (j = 0; j < obj.value.length; j++) {
			var letter = obj.value.charAt(j)
			if (" ".indexOf(letter) != -1 || "　".indexOf(letter) != -1) {
				val = obj.value.substring(j + 1, obj.value.length);
				if (j == (obj.value.length - 1)) {
					flag = false;
					break;
				}
			} else {
				flag = false;
				break;
			}
		}
	}
	val1 = val;
	if (val.length > 0)
		flag = true;
	while (flag) {
		for (k = val.length - 1; k < val.length; k--) {
			var letter = val.charAt(k)
			if (" ".indexOf(letter) != -1 || "　".indexOf(letter) != -1) {
				val1 = val.substring(0, k);
			} else {
				flag = false;
				break;
			}
		}
	}
	obj.value = val1;
}

/**
 * 去除左空格
 */
function lTrim(str) {
	return str.replace(/(^[\s　]*)/g, "");
}

/**
 * 去除右空格
 */
function rTrim(str) {
	return str.replace(/([\s　]*$)/g, "");
}

/**
 * 检查某个文本框的输入值的长度 value--控件value；length--控件允许的最大长度 返回true or false
 * 若返回false，需要在EXT的if中加上obj.focus();和提示信息 eg:XXX 只能输入 XX 个字符(一个汉字相当于两个字符)！
 */
function checkLength(value, length) {
	var len = getBLength(value);
	if (len > length) {
		return false;
	}
	return true;
}
/**
 * 取得文本框的输入值的长度
 */
function getBLength(checkField) {
	var len = 0;
	for (var i = 0; i < checkField.length; i++) { // 对于特殊字符 × § ÷ 也需要按两个字符长度处理
		if (checkField.charAt(i).charCodeAt() > 255
				|| checkField.charAt(i) == '×' || checkField.charAt(i) == '§'
				|| checkField.charAt(i) == '÷') {
			len += 2;
		} else {
			len += 1;
		}
	}
	return len;
}
/**
 * money验证
 */
function isMoney(s) {
	var regu = "^[0-9]+[/.][0-9]{0,3}$";
	var re = new RegExp(regu);
	if (re.test(s)) {
		return true;
	} else {
		return false;
	}
}

/**
 * 数字验证
 */
function isNum(s) {
	if(s=="") return true;
	var regu =/^\d+(\.\d+)?$/;
	
	if (regu.test(s)) {
		return true;
	} else {
		
		return false;
	}
}
/**
 * 验证金钱格式
 * @param obj
 * @returns {Boolean}
 */
function validateMoney(obj){
	 var re =new RegExp(/^(([1-9]\d*)(\.\d{1,2})?)$|(0\.0?([1-9]\d?))$/);
    if (re.test(obj.value)) {
    	var money=obj.value;
    	if(money.indexOf('.')<0){
	     	money+=""+".00";
	     	obj.value=money;
    	}
       return true;  
    } else {  
       alert("金额格式有误！"); 
       obj.value = ""; 
       return false;  
    }
}

/**
 * 邮件格式验证
 */
function isMail(str) {
	if(str==""){
		return true;
	}
	var Regex = /^(?:\w+\.?)*\w+@(?:\w+\.)*\w+$/;            
	if (Regex.test(str)){                
		return true;     
	}
	return false;
}
