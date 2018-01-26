function $A(c){
	if(!c){
		return[];
	}
	if(c.toArray){
		return c.toArray();
	}
}
var Starter = {
	funs : [],
	add : function(e) {
		this.funs || (this.funss = []), this.funs.push(e);
	},
	start : function() {
		if (!this.funs || this.funs.length <= 0)
			return;
		$A(this.funs).each(function(e) {
			e();
		});
	}
};