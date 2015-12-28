function getLoginCookie(){
	var cookieValue = $.cookie('XZTRIP_LOGIN_KEY');
	if(cookieValue==null){
		cookieValue = "";
	}
	return cookieValue;
}

function getCookie(cookieName){
	var cookieValue = $.cookie(cookieName);
	if(cookieValue==null){
		cookieValue = "";
	}
	return cookieValue;
}

function addCookie(cookieKey,cookieName,days){
	if(days==0){
		$.cookie(cookieKey,cookieName, 
			{
				path : '/'
			}
		);
	}else{
		$.cookie(cookieKey,cookieName, 
			{
				expires : days,
				path : '/'
			}
		);
	}
}

function deleteLoginCookie(){
	$.cookie('XZTRIP_LOGIN_KEY',null,
		{
			path : '/'
		}
	);
}

function setLoginCookie(XZTRIP_LOGIN_KEY){
	$.cookie('XZTRIP_LOGIN_KEY',XZTRIP_LOGIN_KEY);
}

function addLoginCookie(XZTRIP_LOGIN_KEY,days){
	if(days==0){
		$.cookie('XZTRIP_LOGIN_KEY',XZTRIP_LOGIN_KEY, 
			{
				path : '/'
			}
		);
	}else{
		$.cookie('XZTRIP_LOGIN_KEY',XZTRIP_LOGIN_KEY, 
			{
				expires : days,
				path : '/'
			}
		);
	}
}

