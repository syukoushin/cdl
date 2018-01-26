/**
 * 数据过滤器
 * 
 * @param treeId
 * @param parentNode
 * @param childNodes
 * @returns
 */
function filter(treeId, parentNode, childNodes) {
	if (!childNodes)
		return null;
	for ( var i = 0, l = childNodes.length; i < l; i++) {
		childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
	}
	return childNodes;
}


/**
 * 保存关联
 */
function saveRF(){
	var treeObj = $.fn.zTree.getZTreeObj("fun_tree");
	var nodes = treeObj.getCheckedNodes(true);
	var funIds="";
	if(nodes!=null&&nodes.length>0){
		for(var i=0;i<nodes.length;i++){
			var node=nodes[i];
			funIds+=node.id+",";
		}
	}
	$("#funId").val(funIds);
	$("#form1").submit();
}

