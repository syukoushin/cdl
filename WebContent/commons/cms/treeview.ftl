<#macro treeview>
<link rel="stylesheet" type="text/css" href="${base}/css/bootstrap.css"/>
<!-- 头部样式star -->
<div id="treeview5" style="width:200px;"></div>
<script type="text/javascript" src="${base}/js/bootstrap/bootstrap-treeview-init.js"></script>
<script>
    var $tree;
    $tree = $('#treeview5');
    $(function(){
        $.ajax({
            type: "post",
            url: "${base}/uum/tree/getBranchDeptTree.do?branchOrgId=${ Session.TASK_USER.deptCode?if_exists}",
            data: {},
            dataType : 'json',
            success: function (json) {
                $('#treeview5').treeview({
                    color: "#428bca",
                    data: json,
                    onNodeExpanded: addNextNode,
                    onNodeSelected:function(event,data) {
                        if(data.attributes.orgcode){
                            deptCode = data.attributes.orgcode;
                            getPageList(data.attributes.orgcode,1);
                        }
                    }
                });
                $('#treeview5 li:first').click();
            },
            error:function(){}
        });
    });
    /**
     * 一个节点被展开 惰性加载
     */
    function addNextNode(event, node) {
        if(node.lazyLoad) {
            $.ajax({
                type: "post",
                url: "${base}/uum/tree/getBranchDeptTree.do?branchOrgId=${ Session.TASK_USER.deptCode?if_exists}",
                data: {id:node.id},
                dataType : 'json',
                success: function (json) {
                    $("#treeview5").treeview("addNode", [node.nodeId, {node: json}]);
                }
            })
        }
    }
</script>
</#macro>