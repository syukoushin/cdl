/**
 * 导出 Excel 前台解析
 * @param {Object} tableId
 * @return {TypeName} 
 */
	function PrintTableToExcel(tableId) {
		var offsetLeftArray = new Array(); // 每个cell的绝对x坐标数组
		var cell;                          // 单元格
		var col;                           // 单元格列值
		var cellStr;                       // 单元格属性组成的字符串，row行+col列+rowSpan+colSpan+格式+text文本 的格式
		var cellStrArray = [];             // 单元格组成的字符串的数组
		var table0 = document.getElementById(tableId);              // 获得table
		var table = $(table0).find("thead"); // 获得thead
		table = $(table).get(0);
		// 第一次循环，首先得到最多的单元格的 offsetLeft 种类，最后有多少种代表此表有多少列
		for (var i = 0; i < table.rows.length; i++) {
			for (var j = 0; j < table.rows[i].cells.length; j++) {
				var left = table.rows[i].cells[j].offsetLeft;
				if (offsetLeftArray.contains(left) == -1) // 如果为 -1 则不包含
					offsetLeftArray.push(left);
			}
		}
		
		// 由于 offsetLeftArray 中的值越小表明越靠左，代表列值越小，则这里由小到大排序，让 offsetLeftArray 中的值与其列对应
		offsetLeftArray.sort(function(x, y) { return parseInt(x) - parseInt(y) });
		// 第二次循环，组装 cellStr
		for (var i = 0; i < table.rows.length; i++) {
			for (var j = 0; j < table.rows[i].cells.length; j++) {
				cell = table.rows[i].cells[j];
				col = offsetLeftArray.contains(cell.offsetLeft);
				var width = parseInt($(table0).width()*(cell.width.split('%')[0])/100); // cell宽度 像素单位
				cellStr = i + "," + col + "," + cell.rowSpan + "," + cell.colSpan + "," + cell.style.textAlign + "," + cell.innerText + "," + width;
				cellStrArray.push(cellStr);
			}
		}
		
		var finalStr = cellStrArray.join('&');
		$("#tableColumn").val(finalStr);
		/*
		var url = '${pageContext.request.contextPath}/transit/exportExcel_exportExcel.action';
		$.ajax({
			type : 'post',
			url : url,
			data : {
				str : finalStr,
				date : new Date()
			},
			dataType : 'text',
			async : false,
			
		});
		*/
	}
	Array.prototype.contains = function(obj) {
		return containsArray(this, obj);
	}
	// 如果 array 数组中包含 obj，返回非 -1
	function containsArray(array, obj) {
		for (var i = 0; i < array.length; i++) {
			if (array[i] == obj) {
				return i;
			}
		}
		return -1;
	}