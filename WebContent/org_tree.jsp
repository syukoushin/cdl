<%@ page language="java" contentType="text/html;charset=UTF-8"	pageEncoding="UTF-8"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<%
	String context = request.getContextPath();
%>
<link rel="stylesheet" type="text/css" href="<%=context %>/uum/js/easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="<%=context %>/uum/js/easyui/themes/icon.css" />
<script type="text/javascript" src="<%=context %>/uum/js/easyui/jquery-1.6.min.js"></script>
<script type="text/javascript" src="<%=context %>/uum/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=context %>/uum/js/uum-tree.js"></script>

<script type="text/javascript">
	
	
	function expandAll(){
		$('#myTree').tree('expandAll');
	}
	
	function collapseAll(){
		$('#myTree').tree('collapseAll');
	}
	
	
</script>

</head>
<body>
	
	<br/><br/><br/>
	id:<input id="ids_user_all" type="text"/>
	name:<input id="names_user_all"  type="text"/>
	<input type="button" value="人员全部树" onclick="MyTreeTools.createTreeAllUser(3,3,'ids_user_all','names_user_all',true,true)"/>
	<br/><br/><br/>
	id:<input id="ids_user_branch" type="text"/>
	name:<input id="names_user_branch" type="text"/>
	<input type="button" value="人员分支树" onclick="MyTreeTools.createTreeBranchUser(3,0,1,'ids_user_branch','names_user_branch',true,false)"/>
	
	<br/><br/><br/>
	
	id:<input id="ids_org_all" type="text"/>
	name:<input id="names_org_all" type="text"/>
	code:<input id="codes_org_all" type="text"/>
	<input type="button" value="组织全部树" onclick="MyTreeTools.createTreeAllOrg(1,1,'ids_org_all','names_org_all','codes_org_all',true,false)"/>
	<br/><br/><br/>
	id:<input id="ids_org_branch" type="text"/>
	name:<input id="names_org_branch" type="text"/>
	code:<input id="codes_org_branch" type="text"/>
	location:<input id="locations_org_branch" type="text"/>
	<input type="button" value="组织分支树" onclick="MyTreeTools.createTreeBranchOrg2(3,0,6,'ids_org_branch','names_org_branch','codes_org_branch','locations_org_branch',false,false)"/>
	
</body>