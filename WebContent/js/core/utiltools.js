// JavaScript Document
var UtilTools = new function(){
	var self = this;
	self.getFormObj = function(form){
		var o = {};
		$.each(form.serializeArray(),function(index){
			if(o[this['name']]){
				o[this['name']] = o[this['name']] +","+this['value'];
			}else{
				o[this['name']] = this['value'];
			}
		});
		return o;
	}
};