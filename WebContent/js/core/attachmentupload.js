function $me(){//根据ID获得dom对象
    return document.getElementById(arguments[0]);
}

//多个文件列表上传
function MultipleFile(){
    return  {
    //全局变量，记录文件数；
    fileNum : 1,
    //待上传文件个数
    fileCount : 0,
    //记录文件名的数组;
    fileName : [],
	//初始化对象的名称，便于在input事件中调用;
	fileEventName : "fileupload",
	commonType : [".zip",".txt",".doc",".docx",".xls",".xlsx",".swf","bmp",".gif",".jpg",".png",".rar",".ppt",".pptx",".flv",".mp4",".wmv",".pdf",".apk"],
	//最多上传文件数
	maxFileAmount : 0,
    init : function(){
            this.fileNum = 1;
            this.fileCount = 0;
            this.fileName = [];
    },
    //mouseover时，把input file移到按扭上，保证点击的是file
    floatFile : function(){    
            var event = arguments[0]? arguments[0]: window.event;
            if(!window.event){
                 $me("file"+this.fileNum).style.top=event.target.offsetTop;
			     $me("file"+this.fileNum).style.left=event.pageX-$me("file"+this.fileNum).offsetWidth/2;
            }else{
                 $me("file"+this.fileNum).style.top=event.srcElement.offsetTop;
			     $me("file"+this.fileNum).style.left=event.x-$me("file"+this.fileNum).offsetWidth/2;
            }

	 },
	 //选择完一个文件之后，自动创建一个新的div 和 file表单，用于下回使用，hidden刚用过的file
	 showText : function (){
	 	   var obj = arguments[0];
		   if(arguments[1]){
			   $me(obj.id+"text").align = "center";
		 	   var floatedFile = arguments[1];
			   var domId = arguments[1].id;
			   var width = floatedFile.offsetWidth;
			   var height = floatedFile.offsetHeight;
	           if(!this.fileTypeCheck(obj.value.toLowerCase())){
	                  $me("div"+this.fileNum).innerHTML ='<div id="file'+this.fileNum+'text" ></div><input id="file'+this.fileNum+'" name="upload" type="file"  onchange="'+this.fileEventName+'.showText(this,'+domId+')"  onclick="return '+this.fileEventName+'.checkFileCount();"  style="position:absolute;filter:alpha(opacity=0);width:30px;height:'+height+'px;left:'+floatedFile.offsetLeft+'px;top:'+floatedFile.offsetTop+'px;cursor:pointer;"hidefocus>';
	                  alert("不支持该类型文件上传！");
	                  return;
	           }
	           if(this.checkFileName(this.getFileName(obj.value))){
	                  $me("div"+this.fileNum).innerHTML ='<div id="file'+this.fileNum+'text" ></div><input id="file'+this.fileNum+'" name="upload" type="file"  onchange="'+this.fileEventName+'.showText(this,'+domId+')"  onclick="return '+this.fileEventName+'.checkFileCount();"  style="position:absolute;opacity: 0;filter:alpha(opacity=0);width:30px;height:'+height+'px;left:'+floatedFile.offsetLeft+'px;top:'+floatedFile.offsetTop+'px;cursor:pointer;"hidefocus>';
	                  alert("已经存在同名的文件："+this.getFileName(obj.value));
	                  return;
	           }
		   }else {
		   	   if(!this.fileTypeCheck(obj.value.toLowerCase())){
	                  $me("div"+this.fileNum).innerHTML ='<div id="file'+this.fileNum+'text" ></div><input id="file'+this.fileNum+'" name="upload" type="file"  onchange="'+this.fileEventName+'.showText(this)"  onclick="return '+this.fileEventName+'.checkFileCount();"  style="position:absolute;filter:alpha(opacity=0);width:30px;cursor:pointer;"hidefocus>';
	                  alert("不支持该类型文件上传！");
	                  return;
	           }
	           if(this.checkFileName(this.getFileName(obj.value))){
	                  $me("div"+this.fileNum).innerHTML ='<div id="file'+this.fileNum+'text" ></div><input id="file'+this.fileNum+'" name="upload" type="file"  onchange="'+this.fileEventName+'.showText(this)"  onclick="return '+this.fileEventName+'.checkFileCount();"  style="position:absolute;opacity: 0;filter:alpha(opacity=0);width:30px;cursor:pointer;"hidefocus>';
	                  alert("已经存在同名的文件："+this.getFileName(obj.value));
	                  return;
	           }
		   }
           $me(obj.id+"text").innerHTML="<table style='table-layout:fixed;width:500px;'><tr><td title="+this.getFileName(obj.value)+" style='overflow:hidden;text-overflow:ellipsis;white-space:nowrap;'>"+this.getFileName(obj.value)+"</td><td style='width: 60px;'>&nbsp;<a href='###' onclick='javascript:"+this.fileEventName+".del("+this.fileNum+")'><font color=red>删除</font></a></td></tr></table><br>";
           $me("file"+this.fileNum).style.display='none';
           this.fileName.push(this.getFileName(obj.value));
           this.fileNum=this.fileNum+1;
           this.fileCount=this.fileCount+1;
           //直接追加innerHTML(innerHTML+=)会清空原来file中的内容
		   if(floatedFile){
			   this.insertAdjacentHTML('AfterEnd',$me("div"+(this.fileNum-1)),'<div id="div'+this.fileNum+'"><div id="file'+this.fileNum+'text" ></div><input id="file'+this.fileNum+'" name="upload" type="file" size="1" onchange="'+this.fileEventName+'.showText(this,'+domId+')"  onclick="return '+this.fileEventName+'.checkFileCount();"  style="position:absolute;opacity: 0;filter:alpha(opacity=0);width:30px;height:'+height+'px;left:'+floatedFile.offsetLeft+'px;top:'+floatedFile.offsetTop+'px;cursor:pointer;" hidefocus></div>');
		   }else {
		  	 this.insertAdjacentHTML('AfterEnd',$me("div"+(this.fileNum-1)),'<div id="div'+this.fileNum+'"><div id="file'+this.fileNum+'text" ></div><input id="file'+this.fileNum+'" name="upload" type="file" size="1" onchange="'+this.fileEventName+'.showText(this)"  onclick="return '+this.fileEventName+'.checkFileCount();"  style="position:absolute;opacity: 0;filter:alpha(opacity=0);width:30px;cursor:pointer;" hidefocus></div>');
		   }
    },
    //删除文件使用
    del : function (id){
       this.fileName[id-1] = "";
       $me("div"+id).innerHTML="";
       $me("div"+id).style.display="none";
       this.fileCount--;
    },
    //上传文件事件
    fileUpload : function (){
           fileReset();
    },
    //截取有效文件名
    getFileName : function (){
           var  path = arguments[0];
           var  position = path.lastIndexOf("\\");
           return  path.substring(position+1,path.length);
    },
    //提交文件后，置空文件域及参数
    fileReset : function (){
           for(var i = 1;i<40;i++){
                  if($me("div"+i)!=null){
                         if(i!=this.fileNum){
                              del(i);
                         }
                  }
           }
          this.fileCount = 0;
    },
    //点击时候，判断文件个数不能超过四个
    checkFileCount : function  (){
	    if(this.maxFileAmount != undefined){
			if(this.fileCount>=this.maxFileAmount){
				 alert("最多只能上传"+this.maxFileAmount+"个文件！");
				 return false;
			}
		}
    },
    //文件格式判断
    fileTypeCheck : function  (){
        var filepath = arguments[0];
        var flag = false;
        for(var  i = 0;i<this.commonType.length;i++){
             if(filepath.indexOf(this.commonType[i])!=-1){
                  flag = true;
                  break;
             }
        }
        return flag;
    },
    //查看是否已经存在同名的文件
	checkFileName : function (){
		var name = arguments[0];
		var flag = false ;
		for(var i = 0;i<this.fileName.length;i++){
			 if(name == this.fileName[i]){
				  flag = true;
				  break;
			 }
		}
		return flag;
	},
	insertAdjacentHTML : function(where, el, html){
        where = where.toLowerCase();   
        if(el.insertAdjacentHTML){   
            switch(where){   
                case "beforebegin":   
                    el.insertAdjacentHTML('BeforeBegin', html);   
                    return el.previousSibling;   
                case "afterbegin":   
                    el.insertAdjacentHTML('AfterBegin', html);   
                    return el.firstChild;   
                case "beforeend":   
                    el.insertAdjacentHTML('BeforeEnd', html);   
                    return el.lastChild;   
                case "afterend":   
                    el.insertAdjacentHTML('AfterEnd', html);   
                    return el.nextSibling;   
            }   
            throw 'Illegal insertion point -> "' + where + '"';   
        } else {   
            var range = el.ownerDocument.createRange();   
            var frag;   
            switch(where){   
                 case "beforebegin":   
                    range.setStartBefore(el);   
                    frag = range.createContextualFragment(html);   
                    el.parentNode.insertBefore(frag, el);   
                    return el.previousSibling;   
                 case "afterbegin":   
                    if(el.firstChild){   
                        range.setStartBefore(el.firstChild);   
                        frag = range.createContextualFragment(html);   
                        el.insertBefore(frag, el.firstChild);   
                        return el.firstChild;   
                    }else{   
                        el.innerHTML = html;   
                        return el.firstChild;   
                    }   
                case "beforeend":   
                    if(el.lastChild){   
                        range.setStartAfter(el.lastChild);   
                        frag = range.createContextualFragment(html);   
                        el.appendChild(frag);   
                        return el.lastChild;   
                    }else{   
                        el.innerHTML = html;   
                        return el.lastChild;   
                    }   
                case "afterend":   
                    range.setStartAfter(el);   
                    frag = range.createContextualFragment(html);   
                    el.parentNode.insertBefore(frag, el.nextSibling);   
                    return el.nextSibling;   
                }   
                throw 'Illegal insertion point -> "' + where + '"';   
        }   
	},
	//参数1：file容器 2：定义事件名称，同变量名称一致 3:最大上传附件数
	render : function(containerID,eventName,amount){
		var initContent = [];
		this.fileEventName = eventName;
		this.maxFileAmount = amount;
		var domId = arguments[3];
		var floatedFile = $me(domId);
		initContent.push('<div id="div1">');
		initContent.push('<div id="file1text"></div>');
		initContent.push('<input id="file1" hidefocus  name="upload" size="1" type="file"');
		initContent.push('onchange="'+this.fileEventName+'.showText(this,'+domId+')" ');
		initContent.push('onclick="return '+this.fileEventName+'.checkFileCount();" style="position:absolute;opacity:0;');
		if(domId){
			var width = floatedFile.offsetWidth;
			var height = floatedFile.offsetHeight;
			initContent.push('filter:alpha(opacity=0);width:30px;height:'+height+'px;left:'+floatedFile.offsetLeft+'px;top:'+floatedFile.offsetTop+'px;cursor:pointer;" />');
		}else {
			initContent.push('filter:alpha(opacity=0);width:30px;cursor:pointer;" />');
		}
		initContent.push('</div>');
		$me(containerID).innerHTML = $me(containerID).innerHTML+initContent.join(' ');
	}
  }

}


//删除附件信息
function deleteFile(base,id,deltr){
	if (confirm("附件删除后无法恢复，您确认删除吗？")) {
		$.ajax({
			   type: "POST",
			   url: base+"/attachment/deleteFile.do",
			   data: "attachment.id="+id+"",
			   dataType:"json",
			   success: function(json){
				    var status = json.result;
				   	if(status=="1"){
				   	 	alert("删除成功！");
				   	 	$("#"+deltr+"").empty();
				   	}if(status=="0"){
				   		alert("删除失败！");
				   		return false;
				   	}
			   }
		});	
	}
}
