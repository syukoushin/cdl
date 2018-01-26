<#macro form01>
<div class="slide_right_tab">
	<div class="slide_screen">
		<form>
			<p>
				<span class="siyuannormal">购买方税号:</span>
				<span>
					<input id="invoiceName" type="text" placeholder="请输入" class="text_input siyuannormal" style='background:url('');'/>
				</span>
				<span class="siyuannormal">车架号码:</span>
				<span>
					<input id="frameNo" type="text" placeholder="请输入" class="text_input siyuannormal" style='background:url('');'/>
					<input type="button" onclick="getPageList(1)" value="查询" class="btn_input" />
				</span>
				<span>
					<a href="javascript:void(0)" style="margin-left:5px; float:left;" id="search_pic" onclick="javascript:down('${base}/manage/Invoice_exportExcel.do');">
	                    <span class='outchu' style='color: #FFFFFF;'>导出</span>
               	 	</a>
				</span>
			</p>
		</form>
	</div>
	<div class="slide_table" style=''>
		<table id="disTable" border="0" class="ranking_table">
			  <thead >
			    <tr>
			      <th>发票号码</th><th>开票日期</th><th>购买方税号</th><th>价税合计</th><th>操作</th>
			    </tr>
			  </thead>
		</table>
		<!--分页-->
		<div id="pageToolbar"></div>
	</div>
</div>
<link href="${base}/css/cms/common.css" rel="stylesheet"/>
<link href="${base}/css/cms/Create_task.css" rel="stylesheet"/>
<link rel="stylesheet" href="${base}/css/cms/dialog.css" />
<link href="${base}/js/easyui/themes/default/easyui.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/uum/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${base}/uum/js/uum-tree.js"></script>
<script type="text/javascript" src="${base}/js/calendar/My97DatePicker/WdatePicker.js"></script>
<script id="pageList" type="text/x-jquery-tmpl">
	<tbody style="font-family:'微软雅黑';">
		{{each(i,item) result}}
			<tr style="font-family:'微软雅黑';">
                <td class='first'>{{= item.number}}</td>
                <td>{{= item.printDate}}</td>
                <td>{{= item.name}}</td>
                <td>{{= item.tax}}</td>
                <td>
				<a title="详情" href="javascript:void(0)" onclick="goInvoiceDetail('{{= item.id}}')" class="btn_detail choice hover_a" ><img src="/cdl/images/cms/small_icon_10.png" style="border-radius:0; width:13px; height:13px;"></a>
                </td>
            </tr>
		{{/each}}
	<tbody>
</script>
<script>
function goInvoiceDetail(id){
	window.location ="../manage/Invoice_toInvoiceDetail.do?id="+id;
};
function getPageList(pageNo){
	var param ={};
	param = getParam();
	param["pageNo"] = pageNo;
	$("#pageToolbar").empty();
    $("#disTable").find("tr:gt(0)").remove();
	getOrderData(param);
}
function getParam(){
	var param ={};
	param["entity.name"] = $("#invoiceName").val();
	param["entity.frameNo"] = $("#frameNo").val();
	return param;
}
function getOrderData(param){
        $.ajax({
            type: "post",
            url: "${base}/manage/Invoice_ajaxInvoiceList.do",
            data: param,
            dataType : 'json',
            success: function (json) {
                if(json.optSts == '0'){
                    var data = json.data;
                    var len = data.result.length;
                    if(len == 0){
                        $("#pageToolbar").html('<tr><td colspan="9"><div style="width:500px;margin:0 auto"><span style="font-family:"微软雅黑";"><font color="red">对不起</font>，没有符合条件的结果，请您检查查询条件是否合理或者更改查询条件进行查询。</span></div></td></tr>');
                        return;
                    }
                    $("#pageList").tmpl(data).appendTo("#disTable");
                    $('#pageToolbar').Paging({pagesize:10,current:data.pageNo,count:data.totalCount,callback:function(page,size,count){
                        getPageList(page);
                    }});
                }else{
                    $("#pageToolbar").append('<tr><td colspan="9"><div style="width:500px;margin:0 auto"><span style="font-family:"微软雅黑";"><font color="red">对不起</font>，没有符合条件的结果，请您检查查询条件是否合理或者更改查询条件进行查询。</span></div></td></tr>');
                    return;
                }
            },
            error:function(){}
        });
}
function down (url){
    var param = getParam();
    var html = '<form action="'+url+'" method="post">';
	    if(param != undefined){
	        for(val in param){
	          html +='<input type="text" name="'+val+'" value="'+param[val]+'"/>';
	        }
        } 
		html+='</form>';
    jQuery(html).appendTo('body').submit().remove();
    
};
</script>
</#macro>
