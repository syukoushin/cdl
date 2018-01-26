var checkAuthTools = new function(){
	var self = this;
	self.authList = {'xumin':['3100','0200','5100','1900','1700','2600','2700','5200','2800','5800'],'yanwusi':['2101'],'fengyiqing':['2108','2102','2107'],'yuhui':['0300','0100','1100','3300','5300','5600','5700','1300','5500'],'lijie':['0600','5000','3200','3000','0900','2100'],'xujun':['0700']};
	self.createAuthList = ['yanwusi','fengyiqing','lijie','yuhui','yanjiang','xumin','xujun','tianjian','yangying','lihaicheng','zhangqiao','moa1'];
	/**权限验证**/
	self.checkAuth = function(userCode,jobLevel,receiverJobLevel,sendDeptCode,receiveDeptCode){
		var flag = false;
		if('yanjiang' == userCode){
			flag = true;
		}else{
			if(self.authList[userCode] === undefined){/**不属于归口人员**/
				/**判断是不是一个部门**/
				if(sendDeptCode == receiveDeptCode){
					if(jobLevel > receiverJobLevel){
						flag = true;
					}else{
						if(receiverJobLevel > 4){
							flag = true;/*普通员工*/
						}
					}
				}else{
					var tempDeptCode = sendDeptCode.substring(0,2);
					if(receiveDeptCode.startWith(tempDeptCode)){
						flag = true;
					}
				}
			}else{
				var authDept = self.authList[userCode];
				for(var i = 0 ; i < authDept.length ;i++){
					var tempDeptCode = authDept[i].substring(0,2);
					if(receiveDeptCode.startWith(tempDeptCode)){/*部门名称以归口部门开头*/
						flag = true;
						break;
					}
				}
			}
		}
		return flag;
	};
};
String.prototype.endWith=function(str){
	if(str==null||str==""||this.length==0||str.length>this.length)
	  return false;
	if(this.substring(this.length-str.length)==str)
	  return true;
	else
	  return false;
	return true;
}
String.prototype.startWith=function(str){
	if(str==null||str==""||this.length==0||str.length>this.length)
	  return false;
	if(this.substr(0,str.length)==str)
	  return true;
	else
	  return false;
	return true;
}