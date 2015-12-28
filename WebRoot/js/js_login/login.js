$(document).ready(function(){
	if(!isIE9()){
		layer.alert("预约签证请使用IE10及以上浏览器,预约其他商品请使用IE9及以上浏览器,或者使用火狐、谷歌、360浏览器","提示");
		return;
	}
	changeCheckCode();
	$("#checkCodeFont").click(function(){
		changeCheckCode();
	});
	document.onkeydown=function keyEnter(event){
		  if (event.keyCode == 13){
			  check_submit();
		  }	
	};
});

var check_submit = function(){
	var orderNum = $('#orderNum').val().trim();
	var phoneOrTaoBaoId = $('#phoneOrTaoBaoId').val().trim();
	
	if(xyzIsNull(orderNum)){
		layer.alert("订单编号不能为空!","警告");
		return;
	}
	if(xyzIsNull(phoneOrTaoBaoId)){
		layer.alert("手机号码或旺旺ID不能为空!","警告");
		return;
	}
	var checkCodeForm = $("input[name='checkCodeForm']:checked");
	var checkCode = new Number();
	for(var i = 0;i<checkCodeForm.length;i++){
		checkCode += parseInt(checkCodeForm.get(i).value);
	}
	var checkCodeValue = parseInt($("#checkCodeDiv").text());
	if(checkCode!=checkCodeValue){
		layer.alert("验证码输入错误","警告");
		changeCheckCode();
		$("input[name='checkCodeForm']").removeAttr("checked");
		return;
	}
	var loginData = {};
	xyzAjax({
		url : "MemberWS/loginOper.xyz",
		data : {
			orderNum : orderNum,
			phoneOrTaoBaoId : phoneOrTaoBaoId
		},
		success : function(data) {
			if(data.status==1){
				loginData = data.content;
			}else if(data.status==0 && data.msg.indexOf("预约未开放")>-1){
				layer.confirm(data.msg+"<br/>(是否需要先填写实名信息?)",function(index){
					window.location.href="jsp_main/changYongCustomer.html?orderNum="+orderNum+"&phoneOrTaoBaoId="+phoneOrTaoBaoId;
				},"提示");
			}else{
				layer.alert(data.msg,"警告");
			}
		}
	});
	
	addCustomerLoginCookie(loginData.apikey,7);
	initSkip(loginData);
};
function initSkip(loginData){
	//查找后台
	if(loginData.type=="erp"){
		if(!isIE10()){
			layer.alert("本预约系统暂不支持ie10以下浏览器，请使用火狐、谷歌、360、IE10及以上浏览器","提示");
			return;
		}
		xyzAjax({
			url : "TkviewWS/queryTkview.visa",
			success : function(data) {
				var erpType = data.content;
				if(erpType.type[0]=="visa"){
					if(loginData.content.countTkview!=1){
						layer.alert("您正在办理签证业务，但订单数量出现问题,请联系客服！","提示",function(){
							window.location.href = "";
						});
						$(".xubox_setwin").remove();
						return;
					}
					location.href="jsp_visa/visa.html";
				}else{
					layer.alert("您购买的商品未配置服务类型，请联系客服！","提示");
				}
			}
		});
	}else if(loginData.type=="taobao"){
		if(!isIE9()){
			layer.alert("本预约系统暂不支持ie9以下浏览器，请使用火狐、谷歌、360、IE9及以上浏览器","提示");
			return;
		}
		window.location.href="jsp_main/orderManager.html"; 
	}
}
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

