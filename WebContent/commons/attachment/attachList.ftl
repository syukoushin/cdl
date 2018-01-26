<#macro attachmentList>
    <#if attachments?exists>
    	<#if attachments?size gt 0>
			<table id="attachList" cellpadding="0" cellspacing="0" class="table3">
				<#list attachments as attachment>
						<tr id="attachment${attachment_index+1?if_exists}">
							<td width="50%" align="center">
								<a href="${base}/attachment/download.do?attachment.id=${attachment.id?if_exists}">${attachment.realName?if_exists}</a>
							</td>
							<td width="30%" align="center">
								<#if attachment.createDate?exists>${attachment.createDate?if_exists?string('yyyy-MM-dd HH:mm:ss')}</#if>
							</td>
							<td width="10%" align="center">
								<a href="javascript:deleteFile('${base}','${attachment.id?if_exists}','attachment${attachment_index+1?if_exists}')">删除</a>
							</td>
						</tr>
				</#list>
			</table>
			<script>
				attachmentNum=${attachments?size};
			</script>
		</#if>
	</#if>
</#macro>