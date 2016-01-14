$(function(){
	$.prompt.setDefaults({
		opacity:0.4,
		promptspeed: 0
	});
	techanuserlogin.init()
});

var techanuserlogin = window.techanuserlogin || function () { };

techanuserlogin.domain = "http://localhost:8080/Jp";
techanuserlogin.doing = false;
techanuserlogin.$ = function (id) {
    return document.getElementById(id);
};
techanuserlogin.tpl = {
	loginhtml: '<div class="login_box"><div class="BoxL"><div class="errinfo hide" id="login_errinfo">用户名密码错误</div><div class="ztit"><strong>登录聚派商城</strong><br>聚集家乡土特产</div><div class="L1"><input type="text" value="请输入手机号码/用户名" name="username" class="ipt_email gray" id="login_ipt_username"></div><div class="L1"><input type="text" value="请输入密码" name="" class="ipt_pwd_mirror gray" id="ipt_pwd_mirror"><input type="password" value="" name="ps" class="hide ipt_pwd" id="login_ipt_pwd"></div><div class="login_code_box hide L3" id="login_code_box"></div><div class="L2"><div class="c_L"><button onclick="techanuserlogin.dologin();" class="loginBtn">登录</button></div><div class="c_R"><input type="checkbox" class="chkb ckb_isremember" value="315360000" checked="checked" id="ckb_isremember" name="cookietime"><label for="ckb_isremember">下次自动登录</label></div><span class="getpassword"><a href="getpwdway.html" target="_blank">忘记密码？</a></span></div></div><div class="BoxR"><div class="Reg changeBox">还没有注册？<a href="javascript:;" class="go_reg">免费注册&gt;&gt;</a></div></div><div class="clear"></div></div>',
	reghtml: '<div class="login_box"><div class="BoxL"><div class="errinfo hide" id="login_errinfo">用户名密码错误</div><div class="ztit"><strong>免费注册</strong><br>注册就算好礼</div><div class="L1"><input type="text" value="请输入手机号码/用户名" name="username" class="ipt_email gray" id="reg_ipt_username"></div><div class="L1"><input type="text" value="请输入密码" name="" class="ipt_pwd_mirror gray" id="ipt_regpwd_mirror"><input type="password" value="" name="ps" class="hide ipt_pwd" id="reg_ipt_pwd"></div><div class="L1"><input type="text" value="请输入确认密码" name="" class="ipt_pwd_mirror gray" id="ipt_again_pwd_mirror"><input type="password" value="" name="ps" class="hide ipt_pwd" id="reg_ipt_again_pwd"></div><div class="L1"></div><div class="login_code_box hide L3" id="login_code_box"></div><div class="L2"><div class="c_L"><button onclick="techanuserlogin.doreg();" class="regBtn">立即注册</button></div><div class="c_R" style="margin-left:20px;"><input type="checkbox" class="chkb ckb_isremember" value="1" name="agreement" checked="checked" disabled="disabled"><label for="ckb_isremember">同意注册协议</label></div></div></div><div class="BoxR" style="margin-top:50px;"><div class="Reg changeBox">已有帐号？<a href="javascript:;" class="go_login">立即登陆&gt;&gt;</a></div></div><div class="clear"></div></div>',
	loginpart: '<div class="errinfo hide" id="login_errinfo">用户名密码错误</div><div class="ztit"><strong>登录特产</strong><br>聚集家乡土特产</div><div class="L1"><input type="text" value="请输入手机号码/用户名" name="username" class="ipt_email gray" id="login_ipt_username"></div><div class="L1"><input type="text" value="请输入密码" name="" class="ipt_pwd_mirror gray" id="ipt_pwd_mirror"><input type="password" value="" name="ps" class="hide ipt_pwd" id="login_ipt_pwd"></div><div class="login_code_box hide L3" id="login_code_box"></div><div class="L2"><div class="c_L"><button onclick="techanuserlogin.dologin();" class="loginBtn">登录</button></div><div class="c_R"><input type="checkbox" class="chkb ckb_isremember" value="315360000" checked="checked" id="ckb_isremember" name="cookietime"><label for="ckb_isremember">下次自动登录</label></div><span class="getpassword"><a href="javascript:;">忘记密码？</a></span></div>',
	regpart: '<div class="errinfo hide" id="login_errinfo">用户名密码错误</div><div class="ztit"><strong>免费注册</strong><br>聚集家乡土特产</div><div class="L1"><input type="text" value="请输入手机号码/用户名" name="username" class="ipt_email gray" id="reg_ipt_username"></div><div class="L1"><input type="text" value="请输入密码" name="" class="ipt_pwd_mirror gray" id="ipt_regpwd_mirror"><input type="password" value="" name="ps" class="hide ipt_pwd" id="reg_ipt_pwd"></div><div class="L1"><input type="text" value="请输入确认密码" name="" class="ipt_pwd_mirror gray" id="ipt_again_pwd_mirror"><input type="password" value="" name="ps" class="hide ipt_pwd" id="reg_ipt_again_pwd"></div><div class="L1"></div><div class="login_code_box hide L3" id="login_code_box"></div><div class="L2"><div class="c_L"><button onclick="techanuserlogin.doreg();" class="regBtn">立即注册</button></div><div class="c_R" style="margin-left:20px;"><input type="checkbox" class="chkb ckb_isremember" value="1" name="agreement" checked="checked" disabled="disabled"><label for="ckb_isremember">同意注册协议</label></div></div>',
	regtxt: '还没有注册？<a href="javascript:;" class="go_reg">免费注册&gt;&gt;</a>',
	logintxt: '已有帐号？<a href="javascript:;" class="go_login">立即登陆&gt;&gt;</a>'
};

techanuserlogin.init = function(){
	$(".uInfo .login, .noLoginAddFa").unbind("click").click(function(n){
		techanuserlogin.showlogin('loginhtml'),n.stopPropagation();
	});
	$(".uInfo .reg").unbind("click").click(function(n){
		techanuserlogin.showlogin('reghtml'),n.stopPropagation();
	});
};
techanuserlogin.showlogin = function(htmls){
	var htmlObj = {'loginhtml':techanuserlogin.tpl.loginhtml,'reghtml':techanuserlogin.tpl.reghtml}
	var slogin = {
		slogin_c: {
			//title: 'Name',
			html:htmlObj[htmls],
			//focus: "input[name='fname']",
			show: 'show',
			buttons: false
		}
	};
	$.prompt(slogin),techanuserlogin.eventInit(),techanuserlogin.changeDoInit();
}

techanuserlogin.dologin = function(e){
	var u = techanuserlogin.$('login_ipt_username').value;
	var p = techanuserlogin.$('login_ipt_pwd').value;
	var er = techanuserlogin.$('login_errinfo');
	if (!u || u=='请输入手机号码/用户名') {
		er.style.display = "block";
        er.innerHTML = '请输入手机号码/用户名';
        return;
    }
    if (!p || p=='请输入密码') {
		er.style.display = "block";
        er.innerHTML = '请输入密码';
        return;
    }
	techanuserlogin.doing = true;
	if(techanuserlogin.doing){
		er.style.display = "block";
        er.innerHTML = '信息处理中...';
	}
	var url = 'http://localhost:8080/Jp/UserWS/loginOper.web';

    xyzAjax({
		url : "UserWS/loginOper.web",
		data : {
			username:u,
			password:p
		},
		success : function(data) {
			if(data.status==1){
				addCookie("JP_LOGIN_KEY",data.content.apikey,7);
				addCookie("JP_LOGIN_NAME",data.content.username,7);
				$.prompt.close(),setTimeout(function(){window.location.reload()},300);
			}else{
				 er.innerHTML = data.msg;
			      return;
			}
		}
	});
}
techanuserlogin.cblogin = function (data) {
    if (data.error == 0) {
		/*$("body").append(data.synlogininfo),*/$.prompt.close(),setTimeout(function(){window.location.reload()},300);
    } else {
        if (data.error == 1) {
			techanuserlogin.$('login_errinfo').innerHTML = data.content;
			techanuserlogin.$('login_errinfo').style.display = "block";
			techanuserlogin.doing = false;
		}
    }
};
techanuserlogin.login_ok = function (datastr) {
    techanuserlogin.doing = false;
	techanuserlogin.$('login_errinfo').style.display = "";
    techanuserlogin.$('login_errinfo').innerHTML = '<span style="color:green;">登录成功！</span>';
	document.body.removeChild(docEle('techan_speDiv'));
	document.body.removeChild(docEle('techan_mask'));
	if(datastr == 'loginActionResponse'){
		//创建一个script节点
		var scriptBlock=document.createElement("script");
		var scriptBlock2=document.createElement("script");
		//将外部文件引入
		scriptBlock.src='http://' + techanuserlogin.domain + "/html/js/common/basic-page.js";
		scriptBlock2.text="userActionCollectioned();";
		// 将该文件加入的html文件的头部。
		document.getElementsByTagName("head")[0].appendChild(scriptBlock);
		document.getElementsByTagName("head")[0].appendChild(scriptBlock2);
	}
};
techanuserlogin.doreg = function(e){
	var u = techanuserlogin.$('reg_ipt_username').value;
	var p = techanuserlogin.$('reg_ipt_pwd').value;
	var ap = techanuserlogin.$('reg_ipt_again_pwd').value;
	var er = techanuserlogin.$('login_errinfo');
	var back_act = '';
	if (!u || u=='请输入手机号码/用户名') {
		er.style.display = "block";
        er.innerHTML = '请输入手机号码/用户名';
        return;
    }
    if (!p || p=='请输入密码') {
		er.style.display = "block";
        er.innerHTML = '请输入密码';
        return;
    }
	if (!ap || ap=='请输入确认密码') {
		er.style.display = "block";
        er.innerHTML = '请输入确认密码';
        return;
    }
	if (p!=ap) {
		er.style.display = "block";
        er.innerHTML = '两次输入的密码不一致';
        return;
    }
	techanuserlogin.doing = true;
	if(techanuserlogin.doing){
		er.style.display = "block";
        er.innerHTML = '信息处理中...';
	}
	
	var url = 'http://localhost:8080/Jp/UserWS/registerOper.web';

    xyzAjax({
		url : "UserWS/registerOper.web",
		data : {
			username:u,
			password:p
		},
		success : function(data) {
			if(data.status==1){
				addCookie("JP_LOGIN_KEY",data.content.apikey,7);
				addCookie("JP_LOGIN_NAME",data.content.username,7);
				$.prompt.close(),setTimeout(function(){window.location.reload()},300);
			}else{
				 er.innerHTML = data.msg;
			      return;
			}
		}
	});
	
};
techanuserlogin.post = function (url, callback) {
    var sc = document.createElement("script");
    sc.type = 'text/javascript';
    sc.async = true;
    sc.src = url;
    if (callback) {
        if (sc.onload) sc.onload = callback;
        else sc.onreadystatechange = callback;
    }
    //document.body.appendChild(sc);
};
techanuserlogin.changeDoInit = function () {
    var _minibox = $("div.login_box");
	$($(".BoxR .changeBox a"), _minibox).unbind("click").bind("click", function(){
		var _curThis = $(this),_curCls = _curThis.prop("class");
		(_curCls=='go_reg') && (_curThis.parent().html(techanuserlogin.tpl.logintxt),techanuserlogin.regpage()) || ((_curCls=='go_login') && (_curThis.parent().html(techanuserlogin.tpl.regtxt),techanuserlogin.loginpage())),techanuserlogin.eventInit();
	});
};
techanuserlogin.regpage = function () {
    var _minibox = $("div.login_box");
	$($(".BoxL"), _minibox).html(techanuserlogin.tpl.regpart),techanuserlogin.changeDoInit();
};
techanuserlogin.loginpage = function () {
    var _minibox = $("div.login_box");
	$($(".BoxL"), _minibox).html(techanuserlogin.tpl.loginpart),techanuserlogin.changeDoInit();
};
techanuserlogin.eventInit = function(a){
	$(".login_box #login_ipt_username").unbind("focus").focus(function() {
		if ("请输入手机号码/用户名" == $(this).val()) {
			$(this).val("");
			try {
				//$(this)[0].select()
			} catch(a) {}
		}
		$("#login_errinfo").hide();
	}).blur(function() { /^\s*$/.test($(this).val()) && $(this).val("请输入手机号码/用户名")
	});
	$(".login_box #ipt_pwd_mirror").unbind("focus").focus(function() {
		$(".login_box #ipt_pwd_mirror").hide();
		$(".login_box #login_ipt_pwd").show().focus();
		if ("请输入密码" == $(this).val()) {
			$(this).val("");
			try {
				//$(this)[0].select()
			} catch(a) {}
		}
		$("#login_errinfo").hide();
	});
	$(".login_box #login_ipt_pwd").unbind("focus").focus(function() {
		if ("请输入密码" == $(this).val()) {
			$(this).val("");
			try {
				//$(this)[0].select()
			} catch(a) {}
		}
		$("#login_errinfo").hide();
	}).blur(function() { 
	 if($(this).val() == ''){
		$(".login_box #ipt_pwd_mirror").show(),$(".login_box #login_ipt_pwd").hide();
		/^\s*$/.test($(".login_box #ipt_pwd_mirror").val()) && $(".login_box #ipt_pwd_mirror").val("请输入密码")
	 }
	});
	
	$(".login_box #reg_ipt_username").unbind("focus").focus(function() {
		if ("请输入手机号码/用户名" == $(this).val()) {
			$(this).val("");
			try {
				//$(this)[0].select()
			} catch(a) {}
		}
		$("#login_errinfo").hide();
	}).blur(function() { /^\s*$/.test($(this).val()) && $(this).val("请输入手机号码/用户名")
	});
	$(".login_box #ipt_regpwd_mirror").unbind("focus").focus(function() {
		$(".login_box #ipt_regpwd_mirror").hide();
		$(".login_box #reg_ipt_pwd").show().focus();
		if ("请输入密码" == $(this).val()) {
			$(this).val("");
			try {
				//$(this)[0].select()
			} catch(a) {}
		}
		$("#login_errinfo").hide();
	});
	$(".login_box #reg_ipt_pwd").unbind("focus").focus(function() {
		if ("请输入密码" == $(this).val()) {
			$(this).val("");
			try {
				//$(this)[0].select()
			} catch(a) {}
		}
		$("#login_errinfo").hide();
	}).blur(function() { 
	 if($(this).val() == ''){
		$(".login_box #ipt_regpwd_mirror").show(),$(".login_box #reg_ipt_pwd").hide();
		/^\s*$/.test($(".login_box #ipt_regpwd_mirror").val()) && $(".login_box #ipt_regpwd_mirror").val("请输入密码")
	 }
	});
	$(".login_box #ipt_again_pwd_mirror").unbind("focus").focus(function() {
		$(".login_box #ipt_again_pwd_mirror").hide();
		$(".login_box #reg_ipt_again_pwd").show().focus();
		if ("请输入密码" == $(this).val()) {
			$(this).val("");
			try {
				//$(this)[0].select()
			} catch(a) {}
		}
		$("#login_errinfo").hide();
	});
	$(".login_box #reg_ipt_again_pwd").unbind("focus").focus(function() {
		if ("请输入密码" == $(this).val()) {
			$(this).val("");
			try {
				//$(this)[0].select()
			} catch(a) {}
		}
		$("#login_errinfo").hide();
	}).blur(function() { 
	 if($(this).val() == ''){
		$(".login_box #ipt_again_pwd_mirror").show(),$(".login_box #reg_ipt_again_pwd").hide();
		/^\s*$/.test($(".login_box #ipt_again_pwd_mirror").val()) && $(".login_box #ipt_again_pwd_mirror").val("请输入密码")
	 }
	});
	
	$(".login_box #ipt_spamcode_mirror").unbind("focus").focus(function() {
		$(".login_box #ipt_spamcode_mirror").hide();
		$(".login_box #reg_ipt_spamcode").show().focus();
		if ("请输入验证码" == $(this).val()) {
			$(this).val("");
			try {
				//$(this)[0].select()
			} catch(a) {}
		}
		$("#login_errinfo").hide();
	});
	$(".login_box #reg_ipt_spamcode").unbind("focus").focus(function() {
		if ("请输入验证码" == $(this).val()) {
			$(this).val("");
			try {
				//$(this)[0].select()
			} catch(a) {}
		}
		$("#login_errinfo").hide();
	}).blur(function() { 
	 if($(this).val() == ''){
		$(".login_box #ipt_spamcode_mirror").show(),$(".login_box #reg_ipt_spamcode").hide();
		/^\s*$/.test($(".login_box #ipt_spamcode_mirror").val()) && $(".login_box #ipt_spamcode_mirror").val("请输入验证码")
	 }
	});
};