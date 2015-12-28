$(document).ready(function() {
	
	$("#username").validatebox({    
	    required: true,
	    validType: 'length[3,20]'
	});

	$("#oldPassword").validatebox({    
	    required: true,
	    validType: 'length[3,20]' 
	});
	
	$("#newPassword1").validatebox({    
	    required: true,
	    validType: 'length[3,20]' 
	});
	
	$("#newPassword2").validatebox({
	    required: true,
	    validType: 'length[3,20]'
	});
	
	
	$("#submitButton").click(function (){
		submit();
	});
});

function submit(){
	if(!$("form").form('validate')){
		return false;
	}
	
	var username = $("#username").val();
	var oldPassword = $.md5($("#oldPassword").val()).substr(8,16);
	
	var passwordTemp = $("#newPassword1").val();
	var score = (/[0-9]+/.test(passwordTemp)?1:0)+(/[A-Z]+/.test(passwordTemp)?1:0)+(/[a-z]+/.test(passwordTemp)?1:0)+(/[^0-9A-Za-z]+/.test(passwordTemp)?1:0);
	if(score<=1){
		top.$.messager.alert("提示","密码强度不够：请使用混合字符！","info");
		return;
	}
	
	var newPassword1 = $.md5($("#newPassword1").val()).substr(8,16);
	var newPassword2 = $.md5($("#newPassword2").val()).substr(8,16);
	
	if(newPassword1 != newPassword2){
		top.$.messager.alert("提示","新密码不一致，请重新输入！","info");
		return;
	}
	
	$.ajax({
		url : "../LoginWS/alterPassword.xyz",
		type : "POST",
		data : {
			username : username,
			oldPassword : oldPassword,
			newPassword : newPassword1
		},
		async : false,
		dataType : "json",
		success : function(data) {
			if(data.status==1){
				top.$.messager.alert("提示","操作成功！","info");
				top.$('#dialogFormDiv_alterPassword').panel('destroy');
			}else{
				top.$.messager.alert("警告",data.msg,"warning");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			top.window.AjaxError(XMLHttpRequest, textStatus, errorThrown);
		}
	});
}
