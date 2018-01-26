//工单管理页面选项卡    	
$(function(){
	var $slide=$(".slide_right_top ul li:not(:last-child)");
	$slide.click(function(){
		$(this).addClass("selected").siblings().removeClass("selected");
		var index = $slide.index(this);
		$(".slide_right_content > div").eq(index).show().siblings().hide();
	});
	//奇偶行样式
        $(".slide_table .ranking_table tbody tr:odd").addClass("odd");//odd为偶数行
        $(".slide_table .ranking_table tbody tr:even").addClass("even")//even为偶数行
})

//创建工单得关闭按钮
$(function(){
	$(".create_table_content table tr td .btn_close").on("click",function(){
		$(this).parent().remove();
	});
})

function goLicense(){
	window.location="../manage/Manage_toList.do";
};
function goGroup(){
	window.location="../manage/Group_toList.do";
};
function goInvoice(){
	window.location="../manage/Invoice_toList.do";
};
function goIdCard(){
	window.location="../manage/IdCard_toList.do";
};
function goEnginner(){
	window.location="../manage/User_toList.do";
};
function goDetail(id){
	window.location ="../manage/Manage_toLicenseDetail.do?id="+id;
};
function goEdit(id){
	window.location ="../manage/Manage_toLicenseEdit.do?id="+id;
};

function goAdmin(){
	window.location ="../manage/Manage_toAdmin.do?";
}
function goMember(){
	window.location ="../manage/Manage_toList.do?";
}
function deleteManage(id){
	if(!confirm("确定删除？")){return false};
	var param={};
	param["id"]=id;
	$.ajax({
        url: '../manage/Manage_ajaxDeleteEntity.do',
        type: "post",
        data: param,
        dataType: 'json', //返回值类型 一般设置为json
        success: function (json)  //服务器成功响应处理函数
        {
            if(json.optSts =='0'){
            	window.location ='../manage/Manage_toList.do'
            } else {
            	alert("删除失败");
            }
        },
        error: function ()//服务器响应失败处理函数
        {
            alert("服务器异常")
        }
    });
};
$(function(){
$(".mask_box").hide();
$("#editWindow").hide();
$("#addWindow").hide();
	$(".choice").click( function(){
		$(".mask_box").show();
        $("#editWindow").show();
	});
	$("#addEnginner").click(function(){
		$(".mask_box").show();
		$("#addWindow").show();
	});
	$(".mark_close").click( function(){
		$(".mask_box").hide();
        $("#editWindow").hide();
        $("#addWindow").hide();
        if($("#showBigImg")){
        	$("#showBigImg").hide();
        }
        if($("#modifyPwdMaskBox")){
        	$("#modifyPwdMaskBox").hide();
        }
        if($("#modifyPwdDialog")){
        	$("#modifyPwdDialog").hide();
        }
	});
	$("#modifyPwd").click(function(){
		$("#modifyPwdMaskBox").show();
		$("#modifyPwdDialog").show();
	});
	
$(".cancel").click( function(){
		$(".mask_box").hide();
        $("#editWindow").hide();
        $("#addWindow").hide();
        $("#showBigImg").hide();
        if($("#modifyPwdMaskBox")){
        	$("#modifyPwdMaskBox").hide();
        }
        if($("#modifyPwdDialog")){
        	$("#modifyPwdDialog").hide();
        }
	});
})


