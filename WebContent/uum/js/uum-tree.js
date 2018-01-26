var MyTreeTools = {
		
		//项目全路径
		serverUrl : document.location.protocol+"//"+document.location.host + window.document.location.pathname.substring(0,window.document.location.pathname.substr(1).indexOf('/')+1),
		
		rootNodeId : 1,
		
		myGet : function (){
			return document.getElementById(arguments[0]);
		},
		
		/**人员树相关**/
		
		/*
		人员树,全部组织
		selectMode 1:单选 2:多选(不级联) 3:多选(级联)
		showOrgLevel : 显示树到组织的第几层级 ,0为显示全部层级
		idValue :input的id, 存放选中节点的 id
		nameValue : input的id,存放选中节点的name
		dbclickClean : 双击存放选中name的input时,单出是否清空选中值对话框  true,false
		openNewTree : 每次打开树时是否生成新的树 true,false
		callback : 窗口关闭的回调函数
		*/
		createTreeAllUser : function(width,height,selectMode,showOrgLevel,idValue,nameValue,dbclickClean,openNewTree,callback){
			
			var url = this.serverUrl+'/uum/tree/getEmpTree.do?showOrgLevel='+showOrgLevel;
			
			this.createUserHtml("全部人员树",idValue,nameValue,selectMode,url,"uum_win_all_user","uum_myTree_all_user",dbclickClean,openNewTree,true,callback,width,height);
			
		},
		
		/*
		人员树,特定组织
		selectMode 1:单选 2:多选(不级联) 3:多选(级联)
		showOrgLevel : 显示树到组织的第几层级 ,0为显示全部层级(当showOrgLevel小于等于分支树根节点的组织级别时,显示全部层级)
		branchOrgId : 分支组织树的根节点组织id
		idValue :input的id, 存放选中节点的 id
		nameValue : input的id,存放选中节点的name
		dbclickClean : 双击存放选中name的input时,单出是否清空选中值对话框  true,false
		openNewTree : 每次打开树时是否生成新的树 true,false
		callback : 窗口关闭的回调函数
		
		*/
		createTreeBranchUser : function(width,height,selectMode,showOrgLevel,branchOrgId,idValue,nameValue,deptCode,deptValue,phoneValue,dbclickClean,openNewTree,callback){
			var url = this.serverUrl+'/uum/tree/getBranchEmpTree.do?branchOrgId='+branchOrgId+'&showOrgLevel='+showOrgLevel;
			this.createUserHtml("全部人员树",idValue,nameValue,deptCode,deptValue,phoneValue,selectMode,url,"uum_win_branch_user","uum_myTree_branch_user",dbclickClean,openNewTree,false,callback,width,height);
			
		},

		
		/*
		创建人员Html
		title : 消息框标题
		single : 单选:true 多选:false
		idValue :input的id, 存放选中节点的 id
		nameValue : input的id,存放选中节点的name
		*/
		createNewUserHtml : function(title,idValue,nameValue,deptCode,deptName,selectMode,url,divId,treeId,dbclickClean,openNewTree,allOrBranch,callback,width,height,uuid){
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
						html.push('<input type="hidden" id="uum_tempId_single_user_id_all_'+uuid+'" />');
						html.push('<input type="hidden" id="uum_tempId_single_user_name_all_'+uuid+'" />');
						html.push('<input type="hidden" id="uum_tempId_single_dept_code_all_'+uuid+'" />');
						html.push('<input type="hidden" id="uum_tempId_single_dept_name_all_'+uuid+'" />');
					}else{
						html.push('<input type="hidden" id="uum_tempId_single_user_id_branch_'+uuid+'" />');
						html.push('<input type="hidden" id="uum_tempId_single_user_name_branch_'+uuid+'" />');
						html.push('<input type="hidden" id="uum_tempId_single_dept_code_branch_'+uuid+'" />');
						html.push('<input type="hidden" id="uum_tempId_single_dept_name_branch_'+uuid+'" />');
					}
				}
				html.push('</div>');
				var newObj = document.createElement("div"); 
				newObj.id = divId; 
				document.body.appendChild(newObj);
			    this.myGet(divId).innerHTML = html.join('');
			    $('#'+divId).dialog({ 
					width : 1*width || 300,  
				    height : 1*height|| 200, 
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
								self.finishNewSingleUserSelect(idValue,nameValue,deptCode,deptName,divId,allOrBranch,callback,uuid);
							}else{
								self.finishNewMultiUserSelect(idValue,nameValue,divId,treeId,callback,uuid);
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
			    this.createNewUserTree(selectMode,url,idValue,nameValue,treeId,allOrBranch,uuid);
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
				 this.createNewUserTree(selectMode,url,idValue,nameValue,treeId,allOrBranch,uuid);
			}
		},
		//创建人员树
		createNewUserTree : function (selectMode,url,idValue,nameValue,treeId,allOrBranch,uuid){
			var checkbox = false;
			var onlyLeafCheck = false;
			var cascadeCheck = false;
			//单选
			if(selectMode == 1){
				checkbox = false;
				//多选 不级联
			}else if(selectMode == 2){
				checkbox = true;
				onlyLeafCheck = true;
				cascadeCheck = false;
				//多选 级联
			}else{
				checkbox = true;
				onlyLeafCheck = false;
				cascadeCheck = true;
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
					//子节点
					var isLeaf = myTree.tree('isLeaf', node.target);
					if(selectMode == 1){
						if(isLeaf){
							if(allOrBranch){
								$("#uum_tempId_single_user_id_all_"+uuid).val(node.id);
								$("#uum_tempId_single_user_name_all_"+uuid).val(node.text);
								$("#uum_tempId_single_dept_code_all_"+uuid).val(node.attributes.parentCode);
								$("#uum_tempId_single_dept_name_all_"+uuid).val(node.attributes.parentName);
							}else{
								$("#uum_tempId_single_user_id_branch_"+uuid).val(node.id);
								$("#uum_tempId_single_user_name_branch_"+uuid).val(node.text);
								$("#uum_tempId_single_dept_code_branch_"+uuid).val(node.attributes.parentCode);
								$("#uum_tempId_single_dept_name_branch_"+uuid).val(node.attributes.parentName);
							}
						}else{
							if(allOrBranch){
								$("#uum_tempId_single_user_id_all_"+uuid).val('');
								$("#uum_tempId_single_user_name_all_"+uuid).val('');
								$("#uum_tempId_single_dept_code_all_"+uuid).val('');
								$("#uum_tempId_single_dept_name_all_"+uuid).val('');
							}else{
								$("#uum_tempId_single_user_id_branch_"+uuid).val('');
								$("#uum_tempId_single_user_name_branch_"+uuid).val('');
								$("#uum_tempId_single_dept_code_branch_"+uuid).val('');
								$("#uum_tempId_single_dept_name_branch_"+uuid).val('');
							}
						}

					}else{
						if(isLeaf){
							if(node.checked){
								myTree.tree('uncheck', node.target);
							}else{
								myTree.tree('check', node.target);
							}
						}
					}
				},
				onDblClick : function (node){
					myTree.tree('toggle', node.target);
				}
			});
		},

		//单选人员确定
		finishNewSingleUserSelect : function(idValue,nameValue,deptCode,deptName,divId,allOrBranch,callback,uuid){
			var id = '';
			var name = '';
			var tempDeptCode = "";
			var tempDeptName = "";
			if(allOrBranch){
				id = $("#uum_tempId_single_user_id_all_"+uuid).val();
				name = $("#uum_tempId_single_user_name_all_"+uuid).val();
				tempDeptCode = $("#uum_tempId_single_dept_code_all_"+uuid).val();
				tempDeptName = $("#uum_tempId_single_dept_name_all_"+uuid).val();
			}else{
				id = $("#uum_tempId_single_user_id_branch_"+uuid).val();
				name = $("#uum_tempId_single_user_name_branch_"+uuid).val();
				tempDeptCode = $("#uum_tempId_single_dept_code_branch_"+uuid).val();
				tempDeptName = $("#uum_tempId_single_dept_name_branch_"+uuid).val();
			}
			if(id == ''){
				alert("请选择人员");
				return false;
			}
			$("#"+idValue).val(id);
			$("#"+nameValue).val(name);
			$("#"+deptCode).val(tempDeptCode);
			$("#"+deptName).val(tempDeptName);
			$('#'+divId).window('close');
			//设置回调方法
			if(callback)callback(uuid);
		},
		
		//多选人员确定
		finishMultiUserSelect : function (idValue,nameValue,divId,treeId,callback){
			var nodes = $('#'+treeId).tree('getChecked');
			var infoName = '';
			if (nodes.length > 0) { 
				var userArr = new Array();
				for (var i = 0; i < nodes.length; i++) { 
					var node = nodes[i];
					//只取type为1的人员
					if(node.attributes.type == 1){
						var userCode = node.id;
						var userName = node.text;
						var idCardNum = node.attributes.idCardNum;
						var parNode = $('#'+treeId).tree('getParent',node.target);
						var deptCode = parNode.id;
						var deptName = parNode.text;
						var type = 1;
						var tempUer = new UserBean(userCode,userName,idCardNum,deptCode,deptName,type);
						userArr.push(tempUer);
					}else{//部门
						if(node.state == 'closed'){
							var deptCode = node.id;
							infoName += "," + deptCode;
						}
					}
				}
				var chooseUser = JSON.stringify(userArr);
				$('#'+idValue).val(chooseUser);
				infoName = infoName.substring(1);
			}else{
				alert("请选择部门或人员!");
				return false;
			}
			$('#'+nameValue).val(infoName);
			$('#'+divId).window('close');
			//设置回调方法
			if(callback)callback();
		},

		/*
		创建人员Html
		title : 消息框标题
		single : 单选:true 多选:false
		idValue :input的id, 存放选中节点的 id
		nameValue : input的id,存放选中节点的name
		*/
		createUserHtml : function(title,idValue,nameValue,deptCode,deptValue,phoneValue,selectMode,url,divId,treeId,dbclickClean,openNewTree,allOrBranch,callback,width,height){
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
						html.push('<input type="hidden" id="uum_tempId_single_user_id_all" />');
						html.push('<input type="hidden" id="uum_tempId_single_user_name_all" />');
						html.push('<input type="hidden" id="uum_tempId_single_user_deptCode_all" />');
						html.push('<input type="hidden" id="uum_tempId_single_user_dept_all" />');
						html.push('<input type="hidden" id="uum_tempId_single_user_phone_all" />');
						html.push('<input type="hidden" id="uum_tempId_single_user_type_all" />');
						html.push('<input type="hidden" id="uum_tempId_single_user_parentcode_all" />');
						html.push('<input type="hidden" id="uum_tempId_single_user_idcardnum_all" />');
					}else{
						html.push('<input type="hidden" id="uum_tempId_single_user_id_branch" />');
						html.push('<input type="hidden" id="uum_tempId_single_user_name_branch" />');
						html.push('<input type="hidden" id="uum_tempId_single_user_deptCode_branch" />');
						html.push('<input type="hidden" id="uum_tempId_single_user_dept_branch" />');
						html.push('<input type="hidden" id="uum_tempId_single_user_phone_branch" />');
						html.push('<input type="hidden" id="uum_tempId_single_user_type_branch" />');
						html.push('<input type="hidden" id="uum_tempId_single_user_parentcode_branch" />');
						html.push('<input type="hidden" id="uum_tempId_single_user_idcardnum_branch" />');
					}
				}
				
				html.push('</div>');
				
				var newObj = document.createElement("div"); 
				
				newObj.id = divId; 
		
				document.body.appendChild(newObj);
		
			    this.myGet(divId).innerHTML = html.join('');
			    
			    $('#'+divId).dialog({ 
					width : 1*width || 300,  
				    height : 1*height|| 200, 
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
								self.finishSingleUserSelect(idValue,nameValue,deptCode,deptValue,phoneValue,divId,allOrBranch,callback);
							}else{
								self.finishMultiUserSelect(idValue,nameValue,deptCode,deptValue,phoneValue,divId,treeId,callback);
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
			    
			    this.createUserTree(selectMode,url,idValue,nameValue,deptCode,deptValue,phoneValue,treeId,allOrBranch);
			    
			    if(dbclickClean){
					$("#"+nameValue).bind("dblclick",function(){
						
						var v = $("#"+nameValue).val();
						if(v){
							if(confirm("确定清空?")){
								$("#"+idValue).val('');
								$("#"+nameValue).val('');
								$("#"+deptValue).val('');
								$("#"+deptCode).val('');
								$("#"+phoneValue).val('');
							}
						}
					});
				}
			    
			}else{
				$('#'+divId).window('open');
			}
			
			if(openNewTree){
				 this.createUserTree(selectMode,url,idValue,nameValue,phoneValue,phoneValue,treeId,allOrBranch);
			}
			
		},

		//创建人员树
		createUserTree : function (selectMode,url,idValue,nameValue,deptValue,phoneValue,treeId,allOrBranch){
			var checkbox = false;
			var onlyLeafCheck = false;
			var cascadeCheck = false;

			//单选
			if(selectMode == 1){
				checkbox = false;
				//多选 不级联
			}else if(selectMode == 2){
				checkbox = true;
				onlyLeafCheck = true;
				cascadeCheck = false;
				//多选 级联
			}else{
				checkbox = true;
				onlyLeafCheck = false;
				cascadeCheck = true;
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
					//子节点
					var isLeaf = myTree.tree('isLeaf', node.target);
					if(selectMode == 1){
						if(isLeaf){
							if(allOrBranch){
								$("#uum_tempId_single_user_id_all").val(node.id);
								$("#uum_tempId_single_user_name_all").val(node.text);
								$("#uum_tempId_single_user_type_all").val(node.attributes.type);
								$("#uum_tempId_single_user_deptCode_all").val(node.attributes.parentCode);
								$("#uum_tempId_single_user_dept_all").val(node.attributes.parentName);
								$("#uum_tempId_single_user_phone_all").val(node.attributes.phone);
								$("#uum_tempId_single_user_parentcode_all").val(node.attributes.parentCode);
								$("#uum_tempId_single_user_idcardnum_all").val(node.attributes.idCardNum);
							}else{
								$("#uum_tempId_single_user_id_branch").val(node.id);
								$("#uum_tempId_single_user_name_branch").val(node.text);
								$("#uum_tempId_single_user_type_branch").val(node.attributes.type);
								$("#uum_tempId_single_user_deptCode_branch").val(node.attributes.parentCode);
								$("#uum_tempId_single_user_dept_branch").val(node.attributes.parentName);
								$("#uum_tempId_single_user_phone_branch").val(node.attributes.phone);
								$("#uum_tempId_single_user_parentcode_branch").val(node.attributes.parentCode);
								$("#uum_tempId_single_user_idcardnum_branch").val(node.attributes.idCardNum);
							}

						}else{
							if(allOrBranch){
								$("#uum_tempId_single_user_id_all").val('');
								$("#uum_tempId_single_user_name_all").val('');
								$("#uum_tempId_single_user_type_all").val('');
								$("#uum_tempId_single_user_dept_all").val('');
								$("#uum_tempId_single_user_deptCode_all").val('');
								$("#uum_tempId_single_user_phone_all").val('');
								$("#uum_tempId_single_user_parentcode_all").val('');
								$("#uum_tempId_single_user_idcardnum_all").val('');
							}else{
								$("#uum_tempId_single_user_id_branch").val('');
								$("#uum_tempId_single_user_name_branch").val('');
								$("#uum_tempId_single_user_type_branch").val('');
								$("#uum_tempId_single_user_dept_branch").val('');
								$("#uum_tempId_single_user_deptCode_branch").val('');
								$("#uum_tempId_single_user_phone_branch").val('');
								$("#uum_tempId_single_user_parentcode_branch").val('');
								$("#uum_tempId_single_user_idcardnum_branch").val('');
							}
						}

					}else{
						if(isLeaf){
							if(node.checked){
								myTree.tree('uncheck', node.target);
							}else{
								myTree.tree('check', node.target);
							}
						}
					}
				},
				onDblClick : function (node){
					myTree.tree('toggle', node.target);
				}
			});
		},

		//单选人员确定
		finishSingleUserSelect : function(idValue,nameValue,deptCode,deptValue,phoneValue,divId,allOrBranch,callback,mailValue){
			var id = '';
			var name = '';
			var mail = '';
			var deptCodeVar = '';
			var dept = '';
			var phone = '';
			
			if(allOrBranch){
				id = $("#uum_tempId_single_user_id_all").val();
				name = $("#uum_tempId_single_user_name_all").val();
				mail = $("#uum_tempId_single_user_mail_all").val();
				deptCodeVar = $("#uum_tempId_single_user_deptCode_all").val();
				dept = $("#uum_tempId_single_user_dept_all").val();
				phone = $("#uum_tempId_single_user_phone_all").val();
			}else{
				id = $("#uum_tempId_single_user_id_branch").val();
				name = $("#uum_tempId_single_user_name_branch").val();
				mail = $("#uum_tempId_single_user_mail_branch").val();
				deptCodeVar = $("#uum_tempId_single_user_deptCode_branch").val();
				dept = $("#uum_tempId_single_user_dept_branch").val();
				phone = $("#uum_tempId_single_user_phone_branch").val();
			}
			
			if(id == ''){
				alert("请选择人员");
				return false;
			}
			$("#"+idValue).val(id);
			$("#"+nameValue).val(name);
			$("#"+deptCode).val(deptCodeVar);
			$("#"+deptValue).val(dept);
			$("#"+phoneValue).val(phone);
			$('#'+divId).window('close');
			if(mailValue != undefined){
				$("#"+mailValue).val(mail);
			}
			//设置回调方法
			if(callback)callback();
		},
		
		//多选人员确定
		finishMultiUserSelect : function (idValue,nameValue,deptCode,deptValue,phoneValue,divId,treeId,callback){
			var nodes = $('#'+treeId).tree('getChecked');
			var infoName = '';
			if (nodes.length > 0) { 
				var userArr = new Array();
				for (var i = 0; i < nodes.length; i++) { 
					var node = nodes[i];
					//只取type为1的人员
					if(node.attributes.type == 1){
						var userCode = node.id;
						var userName = node.text;
						var idCardNum = node.attributes.idCardNum;
						var phone = node.attributes.phone;
						var parNode = $('#'+treeId).tree('getParent',node.target);
						var deptCode = parNode.id;
						var deptName = parNode.text;
						var type = 1;
						var tempUer = new UserBean(userCode,userName,idCardNum,phone,deptCode,deptName,type);
						userArr.push(tempUer);
					}else{//部门
						if(node.state == 'closed'){
							var deptCode = node.id;
							infoName += "," + deptCode;
						}
					}
				}
				var chooseUser = JSON.stringify(userArr);
				$('#'+idValue).val(chooseUser);
				infoName = infoName.substring(1);
			}else{
				alert("请选择部门或人员!");
				return false;
			}
			$('#'+nameValue).val(infoName);
			$('#'+divId).window('close');
			//设置回调方法
			if(callback)callback();
		},



		/**组织树相关**/
		/*
		 * 组织树,全部组织
		 * selectMode 1:单选 2:多选(不级联 只有子节点可选) 3:多选(级联 全部节点可选) 4:多选(不级联 全部节点可选)
		 * showOrgLevel : 显示树到组织的第几层级 ,0为显示全部层级
		 * idValue :input的i通过d, 存放选中节点的 id
		 * nameValue : input的id,存放选中节点的name
		 * dbclickClean : 双击存放选中name的input时,单出是否清空选中值对话框  true,false
		 * openNewTree : 每次打开树时是否生成新的树 true,false
		*/
		createTreeAllOrg : function(selectMode,showOrgLevel,idValue,nameValue,codeValue,dbclickClean,openNewTree){
			
			var url = this.serverUrl+'/uum/tree/getOrgTree.do?showOrgLevel='+showOrgLevel;
			
			this.createOrgHtml("全部组织树",idValue,nameValue,codeValue,null,selectMode,url,"uum_win_all_org","uum_myTree_all_org",dbclickClean,openNewTree,true);
		},
		
		createTreeAllOrgPro : function(selectMode,showOrgLevel,idValue,nameValue,codeValue,dbclickClean,openNewTree){
			var url = this.serverUrl+'/uum/tree/getOrgTreePro.do?showOrgLevel='+showOrgLevel;

			this.createOrgHtml("全部组织树",idValue,nameValue,codeValue,null,selectMode,url,"uum_win_all_org","uum_myTree_all_org",dbclickClean,openNewTree,true);
		},
		createTreeAllOrgPro1 : function(selectMode,showOrgLevel,idValue,nameValue,codeValue,dbclickClean,openNewTree){
			var url = this.serverUrl+'/uum/tree/getOrgTreePro.do?showOrgLevel='+showOrgLevel;

			this.createOrgHtml("全部组织树",idValue,nameValue,codeValue,null,selectMode,url,"uum_win_all_org1","uum_myTree_all_org1",dbclickClean,openNewTree,true);
		},
		/*
		创建组织Html
		title : 消息框标题
		single : 单选:true 多选:false
		idValue :input的id, 存放选中节点的 id
		nameValue : input的id,存放选中节点的name
		*/
		/*createOrgHtml : function(title,idValue,nameValue,codeValue,locationValue,selectMode,url,divId,treeId,dbclickClean,openNewTree,allOrBranch){
			
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
						html.push('<input type="hidden" id="uum_tempId_single_org_id_all" />');
						html.push('<input type="hidden" id="uum_tempId_single_org_name_all" />');
						html.push('<input type="hidden" id="uum_tempId_single_org_code_all" />');
						html.push('<input type="hidden" id="uum_tempId_single_org_location_all" />');
					}else{
						html.push('<input type="hidden" id="uum_tempId_single_org_id_branch" />');
						html.push('<input type="hidden" id="uum_tempId_single_org_name_branch" />');
						html.push('<input type="hidden" id="uum_tempId_single_org_code_branch" />');
						html.push('<input type="hidden" id="uum_tempId_single_org_location_branch" />');
					}
				}	
				
				html.push('</div>');
				
				var newObj = document.createElement("div"); 
				
				newObj.id = divId; 
		
				document.body.appendChild(newObj);
		
			    this.myGet(divId).innerHTML = html.join('');
			    
			    $('#'+divId).dialog({ 
					width : 400,  
				    height : 250, 
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
		},*/
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
						html.push('<input type="hidden" id="uum_tempId_single_org_id_all" />');
						html.push('<input type="hidden" id="uum_tempId_single_org_name_all" />');
						html.push('<input type="hidden" id="uum_tempId_single_org_code_all" />');
						html.push('<input type="hidden" id="uum_tempId_single_org_location_all" />');
					}else{
						html.push('<input type="hidden" id="uum_tempId_single_org_id_branch" />');
						html.push('<input type="hidden" id="uum_tempId_single_org_name_branch" />');
						html.push('<input type="hidden" id="uum_tempId_single_org_code_branch" />');
						html.push('<input type="hidden" id="uum_tempId_single_org_location_branch" />');
					}
				}	
				
				html.push('</div>');
				
				var newObj = document.createElement("div"); 
				
				newObj.id = divId; 
		
				document.body.appendChild(newObj);
		
			    this.myGet(divId).innerHTML = html.join('');
			    
			    $('#'+divId).dialog({ 
					width : 400,  
				    height : 250, 
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
						html.push('<input type="hidden" id="uum_tempId_single_org_id_all" />');
						html.push('<input type="hidden" id="uum_tempId_single_org_name_all" />');
						html.push('<input type="hidden" id="uum_tempId_single_org_code_all" />');
						html.push('<input type="hidden" id="uum_tempId_single_org_location_all" />');
					}else{
						html.push('<input type="hidden" id="uum_tempId_single_org_id_branch" />');
						html.push('<input type="hidden" id="uum_tempId_single_org_name_branch" />');
						html.push('<input type="hidden" id="uum_tempId_single_org_code_branch" />');
						html.push('<input type="hidden" id="uum_tempId_single_org_location_branch" />');
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
				id = $("#uum_tempId_single_org_id_all").val();
				name = $("#uum_tempId_single_org_name_all").val();
				code = $("#uum_tempId_single_org_code_all").val();
				location= $("#uum_tempId_single_org_location_all").val();
			}else{
				id = $("#uum_tempId_single_org_id_branch").val();
				name = $("#uum_tempId_single_org_name_branch").val();
				code = $("#uum_tempId_single_org_code_branch").val();
				location= $("#uum_tempId_single_org_location_branch").val();
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
							$("#uum_tempId_single_org_id_all").val(node.id);
							$("#uum_tempId_single_org_name_all").val(node.text);
							$("#uum_tempId_single_org_code_all").val(node.attributes.orgcode);
							$("#uum_tempId_single_org_location_all").val(node.attributes.location);
						}else{
							$("#uum_tempId_single_org_id_branch").val(node.id);
							$("#uum_tempId_single_org_name_branch").val(node.text);
							$("#uum_tempId_single_org_code_branch").val(node.attributes.orgcode);
							$("#uum_tempId_single_org_location_branch").val(node.attributes.location);
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
							$("#uum_tempId_single_org_id_all").val(node.id);
							$("#uum_tempId_single_org_name_all").val(node.text);
							$("#uum_tempId_single_org_code_all").val(node.attributes.orgcode);
							$("#uum_tempId_single_org_location_all").val(node.attributes.location);
						}else{
							$("#uum_tempId_single_org_id_branch").val(node.id);
							$("#uum_tempId_single_org_name_branch").val(node.text);
							$("#uum_tempId_single_org_code_branch").val(node.attributes.orgcode);
							$("#uum_tempId_single_org_location_branch").val(node.attributes.location);
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
/**树节点选择对象**/
function UserBean(userCode,userName,idcardnum,deptCode,deptName,type){
	this.userCode = userCode;
	this.userName = userName;
	this.idcardnum = idcardnum;
	this.deptCode = deptCode;
	this.deptName = deptName;
	this.type = type;
}