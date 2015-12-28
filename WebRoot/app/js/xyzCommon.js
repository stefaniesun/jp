
//日期格式化
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

(function(thiz){
	var xyz = {};
	xyz.config = {
		server : 'http://192.168.1.10:8080/xztrip/',
		version : new Date().getTime()
	};
	xyz.getFullurl = function(shortUrl){
		return xyz.config.server+'app/'+shortUrl+'?v='+xyz.config.version;
	};
	xyz.isNull = function(obj){
		if(obj==undefined || obj==null || (obj+"".trim())==="" ||  (obj+"".trim())===''){
			return true;
		}else{
			return false;
		}
	};
	xyz.id = function(eleId){
		return document.getElementById(eleId);
	};
	xyz.setUrlparam = function(url, param){
		var pString = '';
		if(typeof param === 'object'){
			for(p in param){
				pString += encodeURIComponent(p)+'='+encodeURIComponent(param[p])+'&';
			}
		}else if(typeof param === 'string'){
			var arr = param.split('&');
			if(arr){
				for(var i=0;i<arr.length;i++){
					var tt = arr[i].split('=');
					if(tt && tt.length==2){
						pString += encodeURIComponent(tt[0])+'='+encodeURIComponent(tt[1])+"&";
					}
				}
			}
		}else{
			return url;
		}
		return url+(url.indexOf('?')>-1?'&':'?')+pString.substring(0,pString.length-1);
	};
	xyz.getUrlparam  = function(){
		var result ={};
		var q = location.search.substr(1);   
		var qs = q.split("&");
		if (qs) {
			for(var i=0;i<qs.length;i++){
				var key = qs[i].substring(0,qs[i].indexOf("="));
				result[decodeURIComponent(key)] = decodeURIComponent(qs[i].substring(qs[i].indexOf("=")+1));
			}
		}
		return result;
	};
	xyz.ajax = function(p){
		var progress = ('progress' in p)?p.progress:true;
		if(progress){
			if(window.plus && window.plus.nativeUI){
				progress = plus.nativeUI.showWaiting(p.progressText?p.progressText:"处理中...");
			}else{
				progress = false;
			}
		}
		mui.ajax(p.url?(p.url.indexOf('http')==1?p.url:xyz.config.server+p.url):'',{
			data:p.data?p.data:{},
			dataType:'json',
			type:'post',
			timeout:5000,//超时
			success:function(data){
				if(progress){
					progress.close();
				}
				if(typeof p.success==='function'){
					p['success'](data);
				}
			},
			error:function(xhr,type,errorThrown){
				if(progress){
					progress.close();
				}
				var ErrorInfo = new Array();
				ErrorInfo["parsererror"] = "解析出错！";
				ErrorInfo["timeout"] = "请求超时！";
				ErrorInfo["error"] = "请求出错！";
				ErrorInfo["abort"] = "请求被阻止或服务器无响应！";
				ErrorInfo["notmodified"] = "网络异常！";
				ErrorInfo["null"] = "网络异常！";
				alert("AJAX错误："+ErrorInfo[type]);
			}
		});
	};
	/**
	 * 把毫秒转成中文的 xx天xx时xx分
	 * @param time
	 * @returns {String}
	 */
	xyz.getZhTime = function(time){
		if(time==0){
			return '0分钟';
		}
		if(time<0){
			time = 0 - time;
		}
		var day = parseInt(time/86400000);
		var hour = parseInt( (time - (86400000 * day)) / 3600000 );
		var min = parseInt( (time - ((86400000 * day) + (3600000 * hour)) ) / 60000 );
		var result = day<=0?'':day+'天';
		if(day<=0 && hour<=0){
			result += '';
		}else{
			result += hour+'时';
		}
		return result += min+'分';
	};
	/**
	 * 把毫秒转成中文的剩余xx天或xx小时或xx分的奇葩效果
	 */
	xyz.getZhTimeQp = function(time){
		if(time==0){
			return '0分钟';
		}
		if(time<0){
			time = 0 - time;
		}
		if(time>=(2*86400000)){
			return parseInt(time/86400000)+"天";
		}
		if(time>=3600000){
			return parseInt(time/3600000)+"小时";
		}
		return parseInt(time/60000)+"分钟";
	};
	thiz.xyz = xyz;
})(this);