$(document).ready(function(){
	var username = getCookie("XZTRIP_LOGIN_NAME");
	$("#username").html(username);
	
	$("#logoutLink").click(function(){
		deleteB2bCookie();
		location.href="../../login.html";
	});
	
	$("#logoutHeaderLink").click(function(){
		deleteB2bCookie();
		location.href="../../login.html";
	});
});