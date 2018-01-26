var AssetTreeTools = {
		
		//项目全路径
		serverUrl : document.location.protocol+"//"+document.location.host + window.document.location.pathname.substring(0,window.document.location.pathname.substr(1).indexOf('/')+1),
		
		rootNodeId : 99999999,
		
		myGet : function (){
			return document.getElementById(arguments[0]);
		},
		
		/**资产类型树树相关**/
		/*
		 * 组织树,全部组织
		 * selectMode 1:单选 2:多选(不级联 只有子节点可选) 3:多选(级联 全部节点可选) 4:多选(不级联 全部节点可选)
		 * showLevel : 显示树到资产类型的第几层级 ,0为显示全部层级
		 * idValue :input的id, 存放选中节点的 id
		 * nameValue : input的id,存放选中节点的name
		 * dbclickClean : 双击存放选中name的input时,单出是否清空选中值对话框  true,false
		 * openNewTree : 每次打开树时是否生成新的树 true,false
		 * selectLeafOnly 0.表示不是 ，1表示是
		*/
		createTreeAll : function(selectMode,showLevel,idValue,nameValue,codeValue,dbclickClean,openNewTree,selectLeafOnly){
			var url = this.serverUrl+'/assettype/getTree.do';
			this.createHtml("资产类型树",idValue,nameValue,codeValue,selectMode,url,"assettype_win_all","assettype_myTree_all",dbclickClean,openNewTree,true,selectLeafOnly);
		},
		
		/*
		创建Html
		title : 消息框标题
		single : 单选:true 多选:false
		idValue :input的id, 存放选中节点的 id
		nameValue : input的id,存放选中节点的name
		*/
		createHtml : function(title,idValue,nameValue,codeValue,selectMode,url,divId,treeId,dbclickClean,openNewTree,allOrBranch,selectLeafOnly){
			
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
						html.push('<input type="hidden" id="uum_tempId_single_type_id_all" />');
						html.push('<input type="hidden" id="uum_tempId_single_type_code_all" />');
						html.push('<input type="hidden" id="uum_tempId_single_type_name_all" />');
					}else{
						html.push('<input type="hidden" id="uum_tempId_single_type_id_branch" />');
						html.push('<input type="hidden" id="uum_tempId_single_type_code_branch" />');
						html.push('<input type="hidden" id="uum_tempId_single_type_name_branch" />');
					}
				}	
				
				html.push('</div>');
				
				var newObj = document.createElement("div"); 
				
				newObj.id = divId; 
		
				document.body.appendChild(newObj);
		
			    this.myGet(divId).innerHTML = html.join('');
			    
			    $('#'+divId).dialog({ 
					width : 500,  
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
								self.finishSingleSelect(idValue,nameValue,codeValue,divId,allOrBranch,treeId,selectLeafOnly);
							}else{
								self.finishMultiSelect(idValue,nameValue,codeValue,divId,treeId);
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
			    
			    this.createTree(selectMode,url,idValue,nameValue,codeValue,treeId,allOrBranch);
			    
			    if(dbclickClean){
					$("#"+nameValue).bind("dblclick",function(){
						
						var v = $("#"+nameValue).val();
						if(v){
							if(confirm("确定清空?")){
								$("#"+idValue).val('');
								$("#"+nameValue).val('');
								$("#"+codeValue).val('');
							}
						}
						
					});
				}
			    
			}else{
				$('#'+divId).window('open');
			}
			
			if(openNewTree){
				 this.createTree(selectMode,url,idValue,nameValue,codeValue,treeId,allOrBranch);
			}
			
			
		},
		
		//单选组织确定
		finishSingleSelect : function(idValue,nameValue,codeValue,divId,allOrBranch,treeId,selectLeafOnly){
			
			var id = '';
			var name = '';
			var code = '';
			
			if(allOrBranch){
				id = $("#uum_tempId_single_type_id_all").val();
				code = $("#uum_tempId_single_type_code_all").val();
				name = $("#uum_tempId_single_type_name_all").val();
			}else{
				id = $("#uum_tempId_single_type_id_branch").val();
				code = $("#uum_tempId_single_type_code_branch").val();
				name = $("#uum_tempId_single_type_name_branch").val();
			}
			
			var selectNode = $('#'+treeId).tree('find',id);
			
			if (selectLeafOnly == 1) {
				if (!$('#'+treeId).tree('isLeaf',selectNode.target)) {
					
					alert("只能选择类型的子节点");
					return false;
				}
			}
			
			if(id == ''||id == this.rootNodeId){
				alert("请选择资产类型");
				return false;
			}
			$("#"+idValue).val(id);
			$("#"+codeValue).val(code);
			$("#"+nameValue).val(name);
			
			$('#'+divId).window('close');
		},
		
		//多选组织确定
		finishMultiSelect : function (idValue,nameValue,codeValue,divId,treeId){
			
			var nodes = $('#'+treeId).tree('getChecked');
			var infoId = ''; 
			var infoName = '';
			var infoCode = '';
			if (nodes.length > 0) { 
				for (var i = 0; i < nodes.length; i++) { 
					
					if(nodes[i].id != this.rootNodeId){
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
						infoCode += nodes[i].attributes.code;	
					}
				}
				if(infoId == ''){
					alert("请选择资产类型");
					return false;
				}
			} 
			
			$('#'+idValue).val(infoId);
			$('#'+codeValue).val(infoCode);
			$('#'+nameValue).val(infoName);
			
			$('#'+divId).window('close');
			
		},
		
		//创建组织树
		createTree : function (selectMode,url,idValue,nameValue,codeValue,treeId,allOrBranch){
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
							$("#uum_tempId_single_type_id_all").val(node.id);
							$("#uum_tempId_single_type_code_all").val(node.attributes.code);
							$("#uum_tempId_single_type_name_all").val(node.text);
						}else{
							$("#uum_tempId_single_type_id_branch").val(node.id);
							$("#uum_tempId_single_type_code_branch").val(node.attributes.code);
							$("#uum_tempId_single_type_name_branch").val(node.text);
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