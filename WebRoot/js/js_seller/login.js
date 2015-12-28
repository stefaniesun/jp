$(document).ready(function(){
	var username = getCookie("XZTRIP_LOGIN_NAEM");
	if(!xyzIsNull(username)){
		$('#username').val(username); 
	}
	
	$("#loginButton").click(function(){
		var username = $('#username').val().trim(); 
		var password = $('#password').val().trim(); 
		if(xyzIsNull(username))
		{
			layer.tips('请输入用户名', '#username');
			$("#username").focus();
			return false;
		} 
		if(xyzIsNull(password))
		{ 
			layer.tips('请输入密码', '#password');
			$("#password").focus();
			return false;
		}
		var password = $.md5(password).substr(8,16);
		$("#loginButton").html("登陆中...");
		setTimeout(function(){
			$.ajax({
				url : "LoginWS/login.xyz",
				type : "POST",
				data : {
					username : username,
					password : password,
					indateHours : 0,
					phoneType : "pc",
					phoneCode : "pc"
				},
				async : false,
				dataType : "json",
				success : function(data) {
					if(data.status==1){
						addCookie("XZTRIP_LOGIN_KEY",data.content.apikey,7);
						addCookie("XZTRIP_LOGIN_NAEM",data.content.username,7);
						location.href="jsp_seller/index/index.html";
					}else{
						layer.tips(data.msg, '#password');
						$("#password").focus();
						$("#loginButton").html("登陆");
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					new layerAjaxError(textStatus);
				}
			});
		},100);
	});
	document.onkeydown=function keyEnter(){
		  if (event.keyCode == 13){  
			  $("#loginButton").click();
		  }
	};
});