var PortalMessage =  {
	
	appServerPath : "http://10.4.125.211:9080/UniteMsgCenter",
	
	messageCount : 0,
		
	init : function (){
		//为消息中心DIV绑定拖拽事件
		DraggBox.drageDomInSome("message","document");
		$("#messageCenterTitle").bind("mouseover", function(){//鼠标在信息DIV title上时可拖拽
			DraggBox.enableDrage("messageCenter");
		});
		$("#messageCenterListShow").bind("mouseover", function(){//鼠标在信息内容上时不可拖拽
			DraggBox.disableDrag("messageCenter");
		});
	},
	
	showMessage : function (){
		var messageType = arguments[0];
		messageType = messageType == null ? '2' : messageType;
		Colorbox.showGlobal();//整屏遮罩效果
		$("#messageCenter").css("left",(($(document).width())/2-(parseInt($("#messageCenter").width())/2))+"px");
		$("#messageCenter").css("top",($(document).scrollTop()+70)+"px");
		this.showMessageByType(messageType);
		$("#messageCenter").show(600);  
	},
	
	hideMessageList: function(){
		Colorbox.hidden();
		$("#messageCenter").hide();
		$("#messageCenterLists").html("");
		//分类widget的tab重新绑定事件
	    $("#left_tab_ul").children("li").removeClass("hover");
	    $("#left_tab_ul li:first-child").addClass("hover");
	},
	
	//消息中心
	showMessageByType : function(){
	    var type = arguments[0];
	    
		$('#left_tab_ul > li > a[class="hover"]').removeClass("hover");
		$('#sNav0'+type+'Href').addClass("hover");
		
	    if(type == 5){  
	    	$("#messageCenterIframe").attr("src", this.appServerPath+"/message/index.action?messageVO.userCode="+currentUser);
	    }else if (type == 2){
	    	$("#messageCenterIframe").attr("src", basePath+"/affiche/viewAffiches.do");
	    }else if (type == 3){
	    	$("#messageCenterIframe").attr("src", this.appServerPath+"/userSystem/index.do?userCode="+currentUser);
	    }else if (type == 4){
	    	$("#messageCenterIframe").attr("src", this.appServerPath+"/setup/index.do");
	    }else if (type == 1){
	    	$("#messageCenterIframe").attr("src", "http://bpm.mm.bmcc.com.cn:8081/bpmp-remind//mainwork.jsp");
	    }
	},
	
	//获得消息统计
	showMessageTips : function(){
		var appCountPath = this.appServerPath+"/message/getMessageCount.do?method=PortalMessage.getMessageCount&userCode="+currentUser;
		var systemCountPath = basePath+"/affiche/findAfficheCount.do?affiche.msgType=0&userCode="+currentUser;
		var parentFun = this;
		//请求通知总数
		AjaxCommonTools("",systemCountPath,"json",true,function(data){
			parentFun.messageCount = 0;
        	if(!data) {
        		return;  
        	}else{
        		if(data.totalCount>0){
        			$("#msg_count").html(+data.totalCount);
        			$("#msg_system_li").show();
        			$("#messageTips").show(500);
        			parentFun.messageCount = data.totalCount;
        		}
        	}
        	$(".message_number").html(parentFun.messageCount);
        	//请求当前登录人的消息总数
        	//makeJSONPRequest(appCountPath);
	    });
		
	},
	
	//获取消息总数回调方法
	getMessageCount : function(){
		var result = arguments[0];
		if(!result) {
    		return;  
    	}else{
    		if(result.totalCount>0){
    			$("#msg_app_count").html("（"+result.totalCount+"）");
    			$("#msg_app_li").show();
    			$("#messageTips").show(500);
    			this.messageCount = this.messageCount + result.totalCount;
    		}
    	}
		$(".message_number").html(this.messageCount);
	}
	
};











