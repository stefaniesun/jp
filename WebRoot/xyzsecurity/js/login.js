//初始化  有加密，无动态令牌,正式
$(document).ready(function() {
	$("#usernameForm").validatebox({    
	    required: true,
	    validType: 'length[3,20]'
	});
	
	$("#passwordForm").validatebox({    
	    required: true,
	    validType: 'length[3,20]'
	});
	changeCheckCode();
	$("#checkCodeFont").click(function(){
		changeCheckCode();
	});
	
	$("#alterPasswordButton").click(function(){
		alterPassword();
	});
});

function changeCheckCode(){
	var getRandomColor = function(){ 
	    return '#'+('00000'+(Math.random()*0x1000000<<0).toString(16)).slice(-6);
	};
	var getRandomMath = function(){ 
    	var temp = Math.floor(Math.random()*10);
    	if(temp!=0){
    		return temp;
    	}else{
    		return getRandomMath();
    	}
	};
	$("#checkCodeDiv").css({
		"background-color" : getRandomColor(),
		"color" : getRandomColor()
	});
	$("#checkCodeDiv").text(getRandomMath());
}

function loginFast(){
	xyzAjax({
		url : "LoginWS/decideLogin.do",
		data : {
		},
		success : function(data) {
			if(data.status==1){
				window.location.href = "xyzsecurity/mainFrame.html";
			}else{
				top.$.messager.alert("警告",data.msg,"warning");
			}
		}
	});
}

function login(){
	if(!$("#loginForm").form('validate')){
		return false;
	}
	{
		var checkCodeForm = $("input[name='checkCodeForm']:checked");
		var checkCode = new Number();
		for(var i = 0;i<checkCodeForm.length;i++){
			checkCode += parseInt(checkCodeForm.get(i).value);
		}
		var checkCodeValue = parseInt($("#checkCodeDiv").text());
		if(checkCode!=checkCodeValue){
			top.$.messager.alert("警告","验证码输入错误","warning");
			changeCheckCode();
			$("input[name='checkCodeForm']").removeAttr("checked");
			return;
		}
	}
	var username = $("#usernameForm").val();
	var password = $.md5($("#passwordForm").val()).substr(8,16);
	
	xyzAjax({ 
		url : "LoginWS/login.xyz",
		data : {
			username : username,
			password : password,
			indateHours : 0,
			phoneType : "pc",
			phoneCode : "pc"
		},
		success : function(data) {
			if(data.status==1){
				addLoginCookie(data.content.apikey,7);
				window.location.href = "xyzsecurity/mainFrame.html";
			}else{
				top.$.messager.alert("警告",data.msg,"warning");
			}
		}
	});
}

function alterPassword(){
	var url = "xyzsecurity/alterPassword.html";
	var tt = $(window).height()-50;
	xyzdialog({
		dialog : 'dialogFormDiv_alterPassword',
		title : "修改密码",
	    content : "<iframe id='dialogFormDiv_alterPasswordIframe' frameborder='0' name=''></iframe>",
	    closable : true,
	    fit:false,
	    width:1000,
	    height:tt
	});
	var tempWidth = $("#dialogFormDiv_alterPassword").css("width");
	var tempHeight = $("#dialogFormDiv_alterPassword").css("height");
	var tempWidth2 = parseInt(tempWidth.split("px")[0]);
	var tempHeight2 = parseInt(tempHeight.split("px")[0]);
	$("#dialogFormDiv_alterPasswordIframe").css("width",(tempWidth2-20)+"px");
	$("#dialogFormDiv_alterPasswordIframe").css("height",(tempHeight2-20)+"px");
	$("#dialogFormDiv_alterPasswordIframe").attr("src",url);
}






