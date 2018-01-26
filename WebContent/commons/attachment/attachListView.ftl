<#macro attachmentListView>
    <#if attachments?exists>
    	<#if attachments?size gt 0>
				<table cellpadding="0" cellspacing="0" class="table3" >
					<#list attachments as attachment>
							<tr id="attachment${attachment_index+1?if_exists}">
								<td width="20%" align="center">
									<a href="${base}/attachment/download.do?attachment.id=${attachment.id?if_exists}">${attachment.realName?if_exists}</a>
								</td>
								<td width="10%" align="center">
									${attachment.createUser?if_exists}
								</td>
								<td width="10%" align="center">
									${attachment.createDate?if_exists}
								</td>
							</tr>
					</#list>
				</table>
				<#else>
				无
		</#if>
	</#if>
</#macro>