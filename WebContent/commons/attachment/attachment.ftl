<#import "/commons/attachment/attachList.ftl" as list>
<#macro attachment>
	<tr>
		  <td id="attachment_base">
				 <div id="myFileDivOne" style="position:relative;display:inline-block;">
        			<img src="${base}/images/addfile.gif" onmouseover="fileupload.floatFile(event);"  style="cursor:pointer;">
        			<span class="twarning" style="padding-left:0px;">提示：支持的文件包括：wps、zip、doc、docx、xls、xlsx、ppt、pptx、gif、jpg、jpeg、bmp、png、pdf、txt、flv、mp4、wmv等.</span></br>
      			</div>
      			<@list.attachmentList/>
		  </td>
	 </tr>
</#macro>