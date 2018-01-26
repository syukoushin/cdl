<#import "../Manage/top.ftl" as top>
<#import "form01.ftl" as form01>
<#import "../Manage/common.ftl" as common>
<#import "../Manage/menu.ftl" as menu>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<@common.common/>
	</head>
	<body style="background: #EEEEEE;">
		<div class="box">
			<!--header-->
			<@top.top/>
			<!--main-->
			<div class="main">
				<div class="container">
					<div class="slide_left fl">
					<@menu.menu/>
						<#--<ul>-->
							<#--<li></li>-->
							<#--<li ><a href="javascript:void(0)" onclick="goLicense()" class="siyuannormal" style="font-size:16px;"><i class="link_i1"><img src="/cdl/images/cms/iconfont_4.png"></i>行驶证</a></li>-->
							<#--<li class="select"><a href="javascript:void(0)" onclick="goInvoice()" class="siyuannormal" style="font-size:16px;"><i class="link_i1"><img src="/cdl/images/cms/iconfont_1.png"></i>发票</a></li>-->
							<#--<li><a href="javascript:void(0);" onclick="goEnginner()" class="siyuannormal" style="font-size:16px;"><i class="link_i2"><img src="/cdl/images/cms/iconfont_5.png"></i>客户端账号</a></li>-->
							<#--<#if Session.CURRENT_USER.jobLevel =='1'>-->
								<#--<li><a href="javascript:void(0);" onclick="goGroup()" class="siyuannormal" style="font-size:16px;"><i class="link_i2"><img src="/cdl/images/cms/iconfont_5.png"></i>公司</a></li>-->
							<#--</#if>-->
						<#--</ul>-->
					</div>
					<div class="slide_right fr">
						<div class="slide_right_content">
							<@form01.form01/>
						</div>
					</div>
				</div>
			</div>
			<!--footer-->
			<div class="footer"></div>
		</div>
	</body>
<script>
getPageList(1);
</script>
</html>
		
