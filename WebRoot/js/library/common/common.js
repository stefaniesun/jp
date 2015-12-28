$(document).ready(function() {
	$.ajaxSettings.async = false;
	//获取url参数
	 $.getUrlParam = function (name) {
         var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
         var r = window.location.search.substr(1).match(reg);
         if (r != null) return unescape(r[2]); return null;
     }
});
function pxTool(value){
	var screenWidth = window.screen.width;	
	return value*screenWidth/1500;
}
function chinaDate(date){
	var year = date.split("-")[0];
	var month = parseInt(date.split("-")[1]);
	var day = parseInt(date.split("-")[2]);
	var result = year+"年"+month+"月"+day+"日";
	return result;
}
function AjaxError(XMLHttpRequest, textStatus, errorThrown) {
	var ErrorInfo = new Array();
	ErrorInfo["parsererror"] = "解析出错！1.长时间未操作可重新登录；2.核查提交数据的正确性。";
	ErrorInfo["timeout"] = "请求超时！1.核查网络状况；2.核查提交数据的正确性。";
	ErrorInfo["error"] = "请求出错！1.核查您的权限；2.核查提交数据的正确性。";
	ErrorInfo["notmodified"] = "网络异常！1.核查浏览器配置；2.关闭浏览器重试。";
	top.$.messager.alert("错误",ErrorInfo[textStatus]+"（提示：这是一个良性错误！）","error");
}
/**
 * 系统异常信息弹框
 */
function layerAjaxError(textStatus) {
	var ErrorInfo = new Array();
	ErrorInfo["parsererror"] = "长时间未操作可重新登录/核查提交数据的正确性";
	ErrorInfo["parsererror_title"]="解析出错";
	ErrorInfo["timeout"] = "核查网络状况/核查提交数据的正确性";
	ErrorInfo["timeout_title"]="请求超时";
	ErrorInfo["error"] = "核查您的权限/核查提交数据的正确性";
	ErrorInfo["error_title"]="请求出错";
	ErrorInfo["notmodified"] = "核查浏览器配置/关闭浏览器重试";
	ErrorInfo["notmodified_title"]="网络异常";
	layer.alert(ErrorInfo[textStatus],5,ErrorInfo[textStatus+"_title"]);
}
/**
 * 判断变量为空或空字符串
 */
function xyzIsNull(obj){
	if(obj==undefined || obj==null || (obj+"".trim())==="" ||  (obj+"".trim())==='' || (obj+"".trim())==='null'){
		return true;
	}else{
		return false;
	}
}
String.prototype.trim=function(){
    return this.replace(/(^\s*)|(\s*$)/g, "");
 };
Date.prototype.Format = function(fmt) { // author: meizz
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()
	// 毫秒
	};
	if (/(y+)/.test(fmt)){
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	}
	for (var k in o){
		if (new RegExp("(" + k + ")").test(fmt)){
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
		}
	}
	return fmt;
};

/**
 * 按钮控制方法
 */
function xyzControlButton(key){
	var authArr = top.currentUserButtons;
	for(var i=0;i<authArr.length;i++){
		if(authArr[i]==key){
			return true;
		}
	}
	return false;
}
function xyzJsonToObject(str){
	if(xyzIsNull(str)){
		return "";
	}else{
		str = str.replace(/\n/g, " ");
		str = str.replace(/\r/g, " ");
		str = str.replace(/\t/g, " ");
		return JSON.parse(str);
	}
}
/**
 * 验证是否为正数
 */
function xyzValidatePositive(num)
{
  return "/^\d+(?=\.{0,1}\d+$|$)/".test(num);
}
/**
*验证零和非零开头的数字
*/
function xyzValidateIsZero(num){
	return "^(0|[1-9][0-9]*)$".test(num);
}
/**
 * 转为指定日期  格式：2015-09-25
 */
function formatDate (date) {  
    var y = date.getFullYear();  
    var m = date.getMonth() + 1;  
    m = m < 10 ? '0' + m : m;  
    var d = date.getDate();  
    d = d < 10 ? ('0' + d) : d;  
    return y + '-' + m + '-' + d;  
};

function xyzLog(fmt){
	if(navigator.userAgent.indexOf("MSIE")>0) {  
		alert(JSON.stringify(fmt));
	}else{
		console.info(fmt);
	}
}

function xyzAjax(p){
//	var port = window.location.port==""?"":":"+window.location.port;
//	var targetServer = window.location.protocol+"//"+window.location.hostname+port+"/ebmb2b/";
	$.ajax({
		url : p.url.indexOf('../')==0?p.url:xyzGetFullUrl(p.url),
		type : ('type' in p)?p.type:"POST",
		data : ('data' in p)?p.data:{},
		async : ('async' in p)?p.async:false,
		dataType : ('dataType' in p)?p.dataType:"json",
		success : p.success,
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			top.window.AjaxError(XMLHttpRequest, textStatus, errorThrown, ('errorTip' in p)?p.errorTip:"layer");
		}
	});
};

function xyzGetFullUrl(url){
	var port = window.location.port==""?"":":"+window.location.port;
	var targetServer = window.location.protocol+"//"+window.location.hostname+port+"/xztrip/";
	return targetServer+url;
};


