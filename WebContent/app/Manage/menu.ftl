<#macro menu>
<!-- 头部样式star -->
<ul>
    <li></li>
    <li <#if  'lecense' == Session.CURRENT_MENU> class="select"</#if> ><a href="javascript:void(0)" onclick="goLicense()" class="siyuannormal" style="font-size:16px;"><i class="link_i1"><img src="/cdl/images/cms/iconfont_4.png"></i>行驶证</a></li>
    <li <#if  'invoice' == Session.CURRENT_MENU> class="select"</#if>><a href="javascript:void(0)" onclick="goInvoice()" class="siyuannormal" style="font-size:16px;"><i class="link_i1"><img src="/cdl/images/cms/iconfont_4.png"></i>发票</a></li>
    <#--<li <#if  'idcard' == Session.CURRENT_MENU> class="select"</#if>><a href="javascript:void(0)" onclick="goIdCard()" class="siyuannormal" style="font-size:16px;"><i class="link_i1"><img src="/cdl/images/cms/iconfont_4.png"></i>身份证</a></li>-->
    <#--<li <#if  'business' == Session.CURRENT_MENU> class="select"</#if>><a href="javascript:void(0)" onclick="goBusinessLicense()" class="siyuannormal" style="font-size:16px;"><i class="link_i1"><img src="/cdl/images/cms/iconfont_4.png"></i>营业执照</a></li>-->
    <li <#if  'user' == Session.CURRENT_MENU> class="select"</#if>><a href="javascript:void(0);" onclick="goEnginner()" class="siyuannormal" style="font-size:16px;"><i class="link_i2"><img src="/cdl/images/cms/iconfont_5.png"></i>客户端账号</a></li>
	<#--<#if Session.CURRENT_USER.jobLevel =='1'>
        <li <#if 'group' == Session.CURRENT_MENU> class="select"</#if>><a href="javascript:void(0);" onclick="goGroup()" class="siyuannormal" style="font-size:16px;"><i class="link_i2"><img src="/cdl/images/cms/iconfont_5.png"></i>公司</a></li>
	</#if>-->
</ul>
</#macro>