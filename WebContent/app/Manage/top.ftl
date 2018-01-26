


<#macro top>
<!-- 头部样式star -->
<div class="header">
    <div class="head">
        <div class="head_l fl"><a href="javascript:" class="fl"><span class="logo"  width="170" height="49" style="margin-left:30px;margin-top:32px;padding-top:0px;border-radius:0px;"/></a><span class="siyuannormal fl">后台管理系统</span></div>
        <div class="head_r fr">
            <span><a href="javascript:" class="siyuannormal">${Session.CURRENT_USER.userName?if_exists}</a></span>
            <span><a id="modifyPwd"  href="javascript:" style="font-family:'微软雅黑';" class="btn_signout">修改密码</a></span>
            <span><a href="${base}/logOut.do" style="font-family:'微软雅黑';" class="btn_signout">退出</a></span></div>
    </div>
</div>
<!--添加修改密码弹窗-->
<div id="modifyPwdMaskBox" class="mask_box" style="display:none"></div>
<div id="modifyPwdDialog" style="display:none" class="mask_content">
    <form>
        <div class="mask_content_top"><p style="font-family:'微软雅黑';">修改密码</p><span class="mark_close"></span></div>
        <div class="mask_content_con">
            <p><span class="span_left">旧密码：</span><input id="oldPw" name="oldPwd" style="font-family:'微软雅黑';" type="password" placeholder="请输入密码" /></p>
            <p><span class="span_left">新密码：</span><input name="newPwd" style="font-family:'微软雅黑';" type="password" placeholder="请输入新密码" /></p>
            <p><span class="span_left">再次输入：</span><input name="surePwd" style="font-family:'微软雅黑';" type="password" placeholder="请再次输入新密码" /></p>
        </div>
        <div class="mask_content_bot mt4">
            <input onclick="modifyPwd('${Session.CURRENT_USER.id?if_exists}');" type="button" value="确定" class="confirm"/>
            <input type="button" value="取消" class="cancel"/>
        </div>
    </form>
</div>
<script>
    function modifyPwd(id){
        var param ={};
        param["id"]=id;
        param["oldPwd"] = $("input[name='oldPwd']").val();
        param["passWord"] = $("input[name='newPwd']").val();
        param["surePwd"] = $("input[name='surePwd']").val();
        if(param["passWord"] !== param["surePwd"]){
            alert("两次密码不一致");
            return false;
        }
        $.ajax({
            url: '${base}/manage/Manage_modifyPwd.do',
            type: "post",
            data: param,
            dataType: 'json', //返回值类型 一般设置为json
            success: function (json)  //服务器成功响应处理函数
            {
                if(json.optSts =='0'){
                    alert("修改成功");
                    $("#modifyPwdMaskBox").hide();
                    $("#modifyPwdDialog").hide();
                    $("input[name='oldPwd']").val("");
                    $("input[name='newPwd']").val("");
                    $("input[name='surePwd']").val("");
                } else {
                    alert(json.optMsg);
                }
            },
            error: function ()//服务器响应失败处理函数
            {
                alert("服务器异常")
            }
        });
    }
</script>
</#macro>