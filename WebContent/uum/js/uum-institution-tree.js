var MyInstitutionTreeTools = {
		
		//项目全路径
		serverUrl : document.location.protocol+"//"+document.location.host + window.document.location.pathname.substring(0,window.document.location.pathname.substr(1).indexOf('/')+1),
		
		rootNodeId : 1,
		
		myGet : function (){
			return document.getElementById(arguments[0]);
		},
		/**制度树相关**/
		/*
		 * 制度树,全部制度
		 * selectMode 1:单选 2:多选(不级联 只有子节点可选) 3:多选(级联 全部节点可选) 4:多选(不级联 全部节点可选)
		 * showOrgLevel : 显示树到组织的第几层级 ,0为显示全部层级
		 * idValue :input的id, 存放选中节点的 id
		 * nameValue : input的id,存放选中节点的name
		 * dbclickClean : 双击存放选中name的input时,单出是否清空选中值对话框  true,false
		 * openNewTree : 每次打开树时是否生成新的树 true,false
		*/
		createInstitutionTree : function(selectMode,showOrgLevel,idValue,nameValue,codeValue,dbclickClean,openNewTree){
			var url = this.serverUrl+'/uum/tree/getInstitution.do';
			this.createOrgHtml("全部制度树",idValue,nameValue,codeValue,null,selectMode,url,"uum_win_all_inst","uum_myTree_all_inst",dbclickClean,openNewTree,true);
		},
		/*
		组织树2,特定组织
		selectMode 1:单选 2:多选(不级联 只有子节点可选) 3:多选(级联 全部节点可选) 4:多选(不级联 全部节点可选) 5:多选（不级联  选择父节点，不能选择子节点）
		urlArray :  访问路径，针对兼职使用
		showOrgLevel : 显示树到组织的第几层级 ,0为显示全部层级(当showOrgLevel小于等于分支树根节点的组织级别时,显示全部层级)
		branchOrgId : 分支人员树的根节点组织id
		idValue :input的id, 存放选中节点的 id
		nameValue : input的id,存放选中节点的name
		dbclickClean : 双击存放选中name的input时,单出是否清空选中值对话框  true,false
		openNewTree : 每次打开树时是否生成新的树 true,false
		*/
		createTreeMultiBranchOrg: function(selectMode,showOrgLevel,branchOrgIds,idValue,nameValue,codeValue,locationValue,dbclickClean,openNewTree){
			var orgIDs=new Array(); 
			var urlArray=new Array();
			var treeIdArray=new Array();
			orgIDs=branchOrgIds.split(",");
			
			for (i=0;i<orgIDs.length;i++ ) 
			{ 
				urlArray[i] = this.serverUrl+'/uum/tree/getBranchOrg.do?branchOrgId='+orgIDs[i]+'&showOrgLevel='+showOrgLevel;
				treeIdArray[i]="uum_myTree_branch_org_"+i;
			} 
			this.createMultiOrgHtml("部分组织树",idValue,nameValue,codeValue,locationValue,selectMode,urlArray,"uum_win_branch_org",treeIdArray,dbclickClean,openNewTree,false);
		},
		/*
		创建组织Html
		title : 消息框标题
		single : 单选:true 多选:false
		idValue :input的id, 存放选中节点的 id
		nameValue : input的id,存放选中节点的name
		*/
		createOrgHtml : function(title,idValue,nameValue,codeValue,locationValue,selectMode,url,divId,treeId,dbclickClean,openNewTree,allOrBranch){
			
			var single = false;

			var self = this;
			
			if(!self.myGet(divId)){
				var html = [];
				html.push('<div id='+divId+' >');
				html.push('<div id='+treeId+'>');
				html.push('</div>');
				
				if(selectMode == 1){
					single = true;
					if(allOrBranch){
						html.push('<input type="hidden" id="uum_tempId_single_inst_id_all" />');
						html.push('<input type="hidden" id="uum_tempId_single_inst_name_all" />');
						html.push('<input type="hidden" id="uum_tempId_single_inst_code_all" />');
						html.push('<input type="hidden" id="uum_tempId_single_inst_location_all" />');
					}else{
						html.push('<input type="hidden" id="uum_tempId_single_inst_id_branch" />');
						html.push('<input type="hidden" id="uum_tempId_single_inst_name_branch" />');
						html.push('<input type="hidden" id="uum_tempId_single_inst_code_branch" />');
						html.push('<input type="hidden" id="uum_tempId_single_inst_location_branch" />');
					}
				}	
				
				html.push('</div>');
				
				var newObj = document.createElement("div"); 
				
				newObj.id = divId; 
		
				document.body.appendChild(newObj);
		
			    this.myGet(divId).innerHTML = html.join('');
			    
			    $('#'+divId).dialog({ 
					width : 600,  
				    height : 400, 
					collapsible : false, 
					minimizable : false, 
					maximizable : false,
					modal:true ,
					title : title,
					buttons: [{ 
						text: '确定', 
						iconCls: 'icon-ok', 
						handler: function() { 
							if(single){
								self.finishSingleOrgSelect(idValue,nameValue,codeValue,locationValue,divId,allOrBranch);
							}else{
								self.finishMultiOrgSelect(idValue,nameValue,codeValue,locationValue,divId,treeId);
							}
						} 
					}, { 
						text: '取消', 
						iconCls: 'icon-cancel', 
						handler: function() { 
							$('#'+divId).window('close');
						} 
					}] 
				}); 
			    
			    this.createOrgTree(selectMode,url,idValue,nameValue,codeValue,locationValue,treeId,allOrBranch);
			    
			    if(dbclickClean){
					$("#"+nameValue).bind("dblclick",function(){
						
						var v = $("#"+nameValue).val();
						if(v){
							if(confirm("确定清空?")){
								$("#"+idValue).val('');
								$("#"+nameValue).val('');
							}
						}
						
					});
				}
			    
			}else{
				$('#'+divId).window('open');
			}
			
			if(openNewTree){
				 this.createOrgTree(selectMode,url,idValue,nameValue,codeValue,locationValue,treeId,allOrBranch);
			}
			
			
		},
		/*
		创建组织Html
		title : 消息框标题
		single : 单选:true 多选:false
		idValue :input的id, 存放选中节点的 id
		nameValue : input的id,存放选中节点的name
		*/
		createMultiOrgHtml : function(title,idValue,nameValue,codeValue,locationValue,selectMode,urlArray,divId,treeIdArray,dbclickClean,openNewTree,allOrBranch){
			
			var single = false;

			var self = this;
			
			if(!self.myGet(divId)){
				var html = [];
				html.push('<div id='+divId+' >');
				for(var i=0;i<treeIdArray.length;i++){
					html.push('<div id='+treeIdArray[i]+'>');
					html.push('</div>');
				}
				
				if(selectMode == 1){
					single = true;
					if(allOrBranch){
						html.push('<input type="hidden" id="uum_tempId_single_inst_id_all" />');
						html.push('<input type="hidden" id="uum_tempId_single_inst_name_all" />');
						html.push('<input type="hidden" id="uum_tempId_single_inst_code_all" />');
						html.push('<input type="hidden" id="uum_tempId_single_inst_location_all" />');
					}else{
						html.push('<input type="hidden" id="uum_tempId_single_inst_id_branch" />');
						html.push('<input type="hidden" id="uum_tempId_single_inst_name_branch" />');
						html.push('<input type="hidden" id="uum_tempId_single_inst_code_branch" />');
						html.push('<input type="hidden" id="uum_tempId_single_inst_location_branch" />');
					}
				}	
				
				html.push('</div>');
				
				var newObj = document.createElement("div"); 
				
				newObj.id = divId; 
		
				document.body.appendChild(newObj);
		
			    this.myGet(divId).innerHTML = html.join('');
			    
			    $('#'+divId).dialog({ 
					width : 800,  
				    height : 600, 
					collapsible : false, 
					minimizable : false, 
					maximizable : false,
					modal:true ,
					title : title,
					buttons: [{ 
						text: '确定', 
						iconCls: 'icon-ok', 
						handler: function() { 
							if(single){
								self.finishSingleOrgSelect(idValue,nameValue,codeValue,locationValue,divId,allOrBranch);
							}else{
								self.finishMultiOrgSelect(idValue,nameValue,codeValue,locationValue,divId,treeId);
							}
						} 
					}, { 
						text: '取消', 
						iconCls: 'icon-cancel', 
						handler: function() { 
							$('#'+divId).window('close');
						} 
					}] 
				}); 
			    
			    for(var j=0;j<treeIdArray.length;j++){
			    	this.createMultiOrgTree(selectMode,urlArray[j],idValue,nameValue,codeValue,locationValue,treeIdArray[j],treeIdArray,allOrBranch);
			    }
			    
			    if(dbclickClean){
					$("#"+nameValue).bind("dblclick",function(){
						
						var v = $("#"+nameValue).val();
						if(v){
							if(confirm("确定清空?")){
								$("#"+idValue).val('');
								$("#"+nameValue).val('');
							}
						}
						
					});
				}
			    
			}else{
				$('#'+divId).window('open');
			}
			
			if(openNewTree){
				 for(var j=0;j<treeIdArray.length;j++){
				    this.createMultiOrgTree(selectMode,urlArray[j],idValue,nameValue,codeValue,locationValue,treeIdArray[j],treeIdArray,allOrBranch);
				    }
			}
			
		},
		
		//单选组织确定
		finishSingleOrgSelect : function(idValue,nameValue,codeValue,locationValue,divId,allOrBranch){
			var id = '';
			var name = '';
			var code= '';
			var location='';
			if(allOrBranch){
				id = $("#uum_tempId_single_inst_id_all").val();
				name = $("#uum_tempId_single_inst_name_all").val();
				code = $("#uum_tempId_single_inst_code_all").val();
				location= $("#uum_tempId_single_inst_location_all").val();
			}else{
				id = $("#uum_tempId_single_inst_id_branch").val();
				name = $("#uum_tempId_single_inst_name_branch").val();
				code = $("#uum_tempId_single_inst_code_branch").val();
				location= $("#uum_tempId_single_inst_location_branch").val();
			}
			if(id == ''||id == this.rootNodeId){
				alert("请选择组织");
				return false;
			}
			
			$("#"+idValue).val(id);
			$("#"+nameValue).val(name);
			$("#"+codeValue).val(code);
			
			if(location!=null){
				$("#"+locationValue).val(location);
			}
			$('#'+divId).window('close');
		},
		
		//多选组织确定
		finishMultiOrgSelect : function (idValue,nameValue,codeValue,locationValue,divId,treeId){
			
			var nodes = $('#'+treeId).tree('getChecked');
			var infoId = ''; 
			var infoName = '';
			var infoCode= '';
			var infoLocation='';
			if (nodes.length > 0) { 
				for (var i = 0; i < nodes.length; i++) { 
					
//					if(nodes[i].id != this.rootNodeId){
						if (infoId != '') {
							infoId += ','; 
						} 
						infoId += nodes[i].id;
							
						if (infoName != '') {
							infoName += ','; 
						} 
						infoName += nodes[i].text;
						
						if (infoCode != '') {
							infoCode += ','; 
						}
						infoCode += nodes[i].attributes.orgcode;
						
						if (infoLocation != '') {
							infoLocation += ','; 
						}
						infoLocation += nodes[i].attributes.location;
//					}
				}
				if(infoId == ''){
					alert("请选择组织");
					return false;
				}
			} 
			
			$('#'+idValue).val(infoId);
			$('#'+nameValue).val(infoName);
			$('#'+codeValue).val(infoCode);
			
			if(locationValue!=null){
				$('#'+locationValue).val(infoLocation);
			}
			
			
			$('#'+divId).window('close');
			
		},
		
		//创建组织树
		createOrgTree : function (selectMode,url,idValue,nameValue,codeValue,locationValue,treeId,allOrBranch){
			var checkbox = false;
			var onlyLeafCheck = false;
			var cascadeCheck = false;
			
			//单选
			if(selectMode == 1){
				checkbox = false;
				onlyLeafCheck = false;
				cascadeCheck = false;
			//多选(不级联 只有子节点可选)
			}else if(selectMode == 2){
				checkbox = true;
				onlyLeafCheck = true;
				cascadeCheck = false;
			//多选(级联 全部节点可选)	
			}else if(selectMode == 3){
				checkbox = true;
				onlyLeafCheck = false;
				cascadeCheck = true;
			//多选(不级联 全部节点可选)	
			}else{
				checkbox = true;
				onlyLeafCheck = false;
				cascadeCheck = false;
			}
			
			var myTree = $('#'+treeId);
			
			//初始化树
			myTree.tree({
				checkbox:checkbox,
				onlyLeafCheck:onlyLeafCheck,
				cascadeCheck:cascadeCheck,
				animate:true,
				url: url,
				onClick : function (node){
					if(selectMode == 1){
						if(allOrBranch){
							$("#uum_tempId_single_inst_id_all").val(node.id);
							$("#uum_tempId_single_inst_name_all").val(node.text);
							$("#uum_tempId_single_inst_code_all").val(node.attributes.orgcode);
							$("#uum_tempId_single_inst_location_all").val(node.attributes.location);
						}else{
							$("#uum_tempId_single_inst_id_branch").val(node.id);
							$("#uum_tempId_single_inst_name_branch").val(node.text);
							$("#uum_tempId_single_inst_code_branch").val(node.attributes.orgcode);
							$("#uum_tempId_single_inst_location_branch").val(node.attributes.location);
						}
						
						
					}else{
						if(node.checked){
							myTree.tree('uncheck', node.target); 
						}else{
							myTree.tree('check', node.target); 
						}
					}
				},
				onDblClick : function (node){
//					if(node.state == "open"){
//						myTree.tree('collapse', node.target); 
//					}else{
//						myTree.tree('expand', node.target); 
//					}
					myTree.tree('toggle', node.target);
				},
				onCheck : function (node) {
					
					if (selectMode == 5) {
						
						if (node.checked) {
							
							var nodes = myTree.tree('getChildren',node.target);
							if (nodes != null && nodes.length > 0) {
								
								for (var i = 0; i < nodes.length; i++) {
									if (nodes[i].checked) {
										myTree.tree('uncheck', nodes[i].target); 
									}
								}
								
							}
							
							var parentNode = myTree.tree('getParent',node.target);
							
							while (parentNode != null) {
								
								if (parentNode.checked) {
									myTree.tree('uncheck', parentNode.target); 
								}
								parentNode = myTree.tree('getParent',parentNode.target);
							}
							
							
						}
					}
				}
			});	
		},
		//创建组织树
		createMultiOrgTree : function (selectMode,url,idValue,nameValue,codeValue,locationValue,treeId,treeIdArray,allOrBranch){
			var checkbox = false;
			var onlyLeafCheck = false;
			var cascadeCheck = false;
			
			//单选
			if(selectMode == 1){
				checkbox = false;
				onlyLeafCheck = false;
				cascadeCheck = false;
			//多选(不级联 只有子节点可选)
			}else if(selectMode == 2){
				checkbox = true;
				onlyLeafCheck = true;
				cascadeCheck = false;
			//多选(级联 全部节点可选)	
			}else if(selectMode == 3){
				checkbox = true;
				onlyLeafCheck = false;
				cascadeCheck = true;
			//多选(不级联 全部节点可选)	
			}else{
				checkbox = true;
				onlyLeafCheck = false;
				cascadeCheck = false;
			}
			
			var myTree = $('#'+treeId);
			
			//初始化树
			myTree.tree({
				checkbox:checkbox,
				onlyLeafCheck:onlyLeafCheck,
				cascadeCheck:cascadeCheck,
				animate:true,
				url: url,
				onClick : function (node){
					cancelSelectNode(treeId,treeIdArray);
					if(selectMode == 1){
						
						if(allOrBranch){
							$("#uum_tempId_single_inst_id_all").val(node.id);
							$("#uum_tempId_single_inst_name_all").val(node.text);
							$("#uum_tempId_single_inst_code_all").val(node.attributes.orgcode);
							$("#uum_tempId_single_inst_location_all").val(node.attributes.location);
						}else{
							$("#uum_tempId_single_inst_id_branch").val(node.id);
							$("#uum_tempId_single_inst_name_branch").val(node.text);
							$("#uum_tempId_single_inst_code_branch").val(node.attributes.orgcode);
							$("#uum_tempId_single_inst_location_branch").val(node.attributes.location);
						}
						
						
					}else{
						if(node.checked){
							myTree.tree('uncheck', node.target); 
						}else{
							myTree.tree('check', node.target); 
						}
					}
				},
				onDblClick : function (node){
//					if(node.state == "open"){
//						myTree.tree('collapse', node.target); 
//					}else{
//						myTree.tree('expand', node.target); 
//					}
					myTree.tree('toggle', node.target);
				},
				onCheck : function (node) {
					
					if (selectMode == 5) {
						
						if (node.checked) {
							
							var nodes = myTree.tree('getChildren',node.target);
							if (nodes != null && nodes.length > 0) {
								
								for (var i = 0; i < nodes.length; i++) {
									if (nodes[i].checked) {
										myTree.tree('uncheck', nodes[i].target); 
									}
								}
								
							}
							
							var parentNode = myTree.tree('getParent',node.target);
							
							while (parentNode != null) {
								
								if (parentNode.checked) {
									myTree.tree('uncheck', parentNode.target); 
								}
								parentNode = myTree.tree('getParent',parentNode.target);
							}
							
							
						}
					}
				}
			});	
		}
};

//取消树节点选择效果
function cancelSelectNode(currentID,treeIdArray){
	for(i=0;i<treeIdArray.length;i++)
	{ 
		if(currentID!=treeIdArray[i]){
			$('#'+treeIdArray[i]).find('.tree-node-selected').removeClass('tree-node-selected');
		}
	}
}