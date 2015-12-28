$(document).ready(function() {
	
	initTable();
	
	$("#userQueryButton").click(function(){
		loadTable();
	});
	
	xyzCombobox({
		combobox : 'position',
		url : '../AdminUserWS/getAllPosition.do',
		valueField: 'numberCode',
		textField : 'nameCn'
	});
});

function initTable(){
	var toolbar = [];
	if(xyzControlButton("buttonCode_h20151214155701")){
		toolbar[toolbar.length]={
				text: '新增用户',
				iconCls: 'icon-add',
				handler: function(){var title=$(this).text();addUser(title);}
		};
		toolbar[toolbar.length]='-';
	}
	if(xyzControlButton("buttonCode_h20151214155702")){
		toolbar[toolbar.length]={
				text: '编辑用户',
				iconCls: 'icon-edit',
				handler: function(){var title=$(this).text();editUser(title);}
		};
		toolbar[toolbar.length]='-';
	}
	if(xyzControlButton("buttonCode_h20151214155703")){
		toolbar[toolbar.length]={
				text: '删除用户',
				iconCls: 'icon-remove',
				handler: function(){deleteUser();}
		};
	}
	if(xyzControlButton("buttonCode_h20151214155704")){
		toolbar[toolbar.length]='-';
		toolbar[toolbar.length]={
				text: '<div style="color:red">重设密码</div>',
				iconCls: 'icon-edit',
				handler: function(){editUserPassword();}
		};
	}
	xyzgrid({
		table : 'userManagerTable',
		title:'用户列表',
		url:'../AdminUserWS/queryUserList.do',
		toolbar : toolbar,
		idField:'username',
		singleSelect : false,
		columns:[[
		    {field:'checkboxTemp',checkbox:true},
		    {field:'nickName',title:'昵称'},
			{field:'username',title:'账号'},
			{field:'enabled',title:'可用',
				formatter: function(value,row,index){
					if (value == 1){
						return '√';
					} else {
						return '×';
					}
				},
				styler : function(value,row,index){
					if(value == 1){
						return "background-color:green";
					}else {
						return "background-color:red";
					}
				}
			},
			{field:'positionNameCn',title:'岗位'},
			{field:'isRepeat',title:'可重登',
				formatter: function(value,row,index){
					if (value == 1){
						return '√';
					} else {
						return '×';
					}
				}
			},
			{field:'tokenNum',title:'动态令牌号'},
			{field:'operTemp',title:'操作',
				formatter: function(value,row,index){
					var buttonTemp1 =  "";
					var buttonTemp2 =  "";
					var buttonTemp3 = "";
					var buttonTemp4 = "";
					var buttonTemp5 = "";
					var buttonTemp6 = "";
					if(xyzControlButton("buttonCode_h20151214155706")){
						buttonTemp1 =  "<a href='javascript:void(0);' onclick='setUserPosition(\""+row.nickName+"\",\""+row.username+"\",\""+row.position+"\")'>岗位</a>";

					}
					if(xyzControlButton("buttonCode_h20151214155707")){
						buttonTemp2 =  "<a href='javascript:void(0);' onclick='setUserOtp(\""+row.nickName+"\",\""+row.username+"\")'>令牌</a>";

					}
					if(xyzControlButton("buttonCode_h20151214155705")){
						if(row.enabled==1){
							buttonTemp3 =  "<a href='javascript:void(0);' onclick='editUserEnabled(\""+row.username+"\")'>关闭</a>";
						}else{
							buttonTemp3 =  "<a href='javascript:void(0);' onclick='editUserEnabled(\""+row.username+"\")'>启用</a>";
						}
					}
					return buttonTemp4+ "&nbsp;"+buttonTemp1 + "&nbsp;" +buttonTemp2+ "&nbsp;" +buttonTemp3+ "&nbsp;" +buttonTemp5+ "&nbsp;" +buttonTemp6;
				}
			},
			{field:'possessor',title:'资源组操作',
				formatter: function(value,row,index){
					var buttonTemp1 = "";
					if(xyzControlButton("buttonCode_h20151214155709")){
						buttonTemp1 =  "<a href='javascript:void(0);' onclick='setUserPossessor(\""+row.username+"\" ,\""+row.nickName+"\" ,\""+row.possessor+"\")'>设置资源组</a>";
					}
					
					return buttonTemp1;
				}
			},
			{field:'possessorNameCn',title:'资源组'},
			{field:'alterDate',title:'修改时间'},
			{field:'addDate',title:'添加时间',hidden:true}
		]]
	});
}

function loadTable(){
	var username = $("#username").val();
	var nickName = $("#nickName").val();
	var modelStr = $("#modelStr").combobox("getValue");
	var position = $("#position").combobox("getValue");
	$("#userManagerTable").datagrid('load',{
		username : username,
		nickName : nickName,
		modelStr : modelStr,
		position : position
	});
}

function addUser(title){
	xyzdialog({
		dialog : 'dialogFormDiv_addUser',
		title : title,
	    href : '../xyzsecurity/a_addUser.html',
	    fit:false,
	    height:400,
	    width:600,
	    buttons:[{
			text:'确定',
			handler:function(){
				addUserSubmit();
			}
		},{
			text:'取消',
			handler:function(){
				$("#dialogFormDiv_addUser").dialog("destroy");
			}
		}]
	});
}

function editUser(title){
	var users = $("#userManagerTable").datagrid("getChecked");
	if(users.length != 1){
		top.$.messager.alert("提示","请先选中单个对象！","info");
		return;
	}
	
	var row = users[0];
	
	xyzdialog({
		dialog : 'dialogFormDiv_editUser',
		title : title,
	    href : '../xyzsecurity/a_editUser.html',
	    fit:false,
	    height:400,
	    width:600,
	    buttons:[{
			text:'确定',
			handler:function(){
				editUserSubmit();
			}
		},{
			text:'取消',
			handler:function(){
				$("#dialogFormDiv_editUser").dialog("destroy");
			}
		}],
		onLoad : function(){
			$("#usernameForm").val(row.username);
			$("#nickNameForm").val(row.nickName);
		}
	});
}

function deleteUser(){
	var users = $.map($("#userManagerTable").datagrid("getChecked"),function(p){return p.username;}).join(",");
	if(xyzIsNull(users)){
		top.$.messager.alert("提示","请先选中需要删除的对象！","info");
		return;
	}
	if(!confirm("彻底删除对象，确定？")){
		return;
	}
	
	xyzAjax({
		url : "../AdminUserWS/deleteUser.do",
		data : {
			users : users
		},
		success : function(data) {
			if(data.status==1){
				$("#userManagerTable").datagrid("reload");
				top.$.messager.alert("提示","操作成功！","info");
			}else{
				top.$.messager.alert("警告",data.msg,"warning");
			}
		}
	});
}

function addUserSubmit(){
	
	if(!$("form").form('validate')){
		return false;
	}
	
	var username = $("#usernameForm").val();
	var nickName = $("#nickNameForm").val();
	var password = $("#passwordForm").val();
	var password2 = $("#password2Form").val();
	if(password!=password2){
		top.$.messager.alert("提示","两次输入的密码不一致，请重新输入！","info");
		return;
	}
	password3 = $.md5(password).substr(8,16);
	xyzAjax({
		url : "../AdminUserWS/addUser.do",
		data : {
			username : username,
			nickName : nickName,
			password : password3
		},
		success : function(data) {
			if(data.status==1){
				$("#userManagerTable").datagrid("reload");
				top.$.messager.alert("提示","操作成功！","info");
				$("#dialogFormDiv_addUser").dialog("destroy");
			}else{
				top.$.messager.alert("警告",data.msg,"warning");
			}
		}
	});
}

function editUserSubmit(){
	
	if(!$("form").form('validate')){
		return false;
	}
	
	var username = $("#usernameForm").val();
	var nickName = $("#nickNameForm").val();
	
	xyzAjax({
		url : "../AdminUserWS/editUser.do",
		data : {
			username : username,
			nickName : nickName
		},
		success : function(data) {
			if(data.status==1){
				$("#userManagerTable").datagrid("reload");
				top.$.messager.alert("提示","操作成功！","info");
				$("#dialogFormDiv_editUser").dialog("destroy");
			}else{
				top.$.messager.alert("警告",data.msg,"warning");
			}
		}
	});
}

function setUserPosition(nickName,username,currentPosition){
	xyzdialog({
		dialog : 'dialogFormDiv_setUserPosition',
		title : "设置岗位["+nickName+"]["+username+"]",
	    content : '<div id="dialogFormDiv_setUserPosition_content"></div>',
	    buttons:[{
			text:'确定',
			handler:function(){
				setUserPositionSubmit(username);
			}
		},{
			text:'取消',
			handler:function(){
				$("#dialogFormDiv_setUserPosition").dialog("destroy");
			}
		}],
		onOpen : function(){
			var positionJson = "";
			xyzAjax({
				url : "../AdminUserWS/getAllPosition.do",
				success : function(data) {
					positionJson = data.content;
				}
			});

			var insertHtml = "<table>";
			for(var i=0;i<positionJson.length;i++){
				insertHtml += "<tr><td><input type='radio' name='positionRadio' value='"+
				positionJson[i].numberCode+"'/>"+positionJson[i].nameCn+"("+positionJson[i].remark+")</td></tr>";
			}
			insertHtml += "</table>";
			$("#dialogFormDiv_setUserPosition_content").append(insertHtml);

			$("#dialogFormDiv_setUserPosition_content input[name='positionRadio'][value='"+currentPosition+"']").attr("checked","checked");
			
		}
	});
}

function setUserPositionSubmit(username){
	var position = $("input[name='positionRadio']:checked").val();
	xyzAjax({
		url : "../AdminUserWS/setUserPosition.do",
		data : {
			username : username,
			position : position
		},
		success : function(data) {
			if(data.status==1){
				$("#userManagerTable").datagrid("reload");
				top.$.messager.alert("提示","操作成功！","info");
			}else{
				top.$.messager.alert("警告",data.msg,"warning");
			}
		}
	});
}

function editUserEnabled(username){
	xyzAjax({
		url : "../AdminUserWS/editUserEnabled.do",
		data : {
			username : username
		},
		success : function(data) {
			if(data.status==1){
				$("#userManagerTable").datagrid("reload");
				top.$.messager.alert("提示","操作成功！","info");
			}else{
				top.$.messager.alert("警告",data.msg,"warning");
			}
		}
	});
}

function setUserOtp(nickName,username){
	xyzdialog({
		dialog : 'dialogFormDiv_setUserOtp',
		title : "设置令牌["+nickName+"]["+username+"]",
	    content : '<div>新的令牌号是？<input type="text" id="userOtpForm"/></div>',
	    fit:false,
	    width:300,
	    height:150,
	    buttons:[{
			text:'确定',
			handler:function(){
				setUserOtpSubmit(username);
			}
		},{
			text:'取消',
			handler:function(){
				$("#dialogFormDiv_setUserOtp").dialog("destroy");
			}
		}]
	});
};

function setUserOtpSubmit(username){
	if(!$("form").form('validate')){
		return false;
	}
	
	var userOtp = $("#userOtpForm").val();
	
	xyzAjax({
		url : "../AdminUserWS/setUserOtp.do",
		data : {
			username : username,
			userOtp : userOtp
		},
		success : function(data) {
			if(data.status==1){
				$("#userManagerTable").datagrid("reload");
				top.$.messager.alert("提示","操作成功！","info");
				$("#dialogFormDiv_setUserOtp").dialog("destroy");
			}else{
				top.$.messager.alert("警告",data.msg,"warning");
			}
		}
	});
}

function setUserPossessor(username ,nickName ,possessor){
	xyzdialog({
		dialog : 'dialogFormDiv_setUserPossessor',
		title : "给"+nickName+"设置资源组",
	    fit:false,
	    width:300,
	    height:200,
	    content : '<div><input type="text" id="possessorForm" /></div>',
	    buttons:[{
			text:'确定',
			handler:function(){
				setUserPossessorSubmit(username);
			}
		},{
			text:'取消',
			handler:function(){
				$("#dialogFormDiv_setUserPossessor").dialog("destroy");
			}
		}],
		onOpen:function(){
			xyzCombobox({
				combobox : 'possessorForm',
				url : '../PossessorWS/getPossessorList.do',
				lazy : false,
				mode : 'remote'
			});
			$("#possessorForm").combobox("setValue" ,possessor);
		}
	});
}

function setUserPossessorSubmit(username){
	var possessor = $("#possessorForm").combobox("getValue");
	$.ajax({
		url : "../AdminUserWS/setUserPossessor.do",
		type : "POST",
		data : {
			username : username,
			possessor : possessor
		},
		async : false,
		dataType : "json",
		success : function(data) {
			if(data.status==1){
				top.$.messager.alert("提示","操作成功！","info");
				$("#userManagerTable").datagrid("reload");
				$("#dialogFormDiv_setUserPossessor").dialog("destroy");
			}else{
				top.$.messager.alert("警告",data.msg,"warning");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			top.window.AjaxError(XMLHttpRequest, textStatus, errorThrown);
		}
	});
}

function editUserPassword(){
	var users = $("#userManagerTable").datagrid("getChecked");
	if(users.length != 1){
		top.$.messager.alert("提示","请先选中单个对象！","info");
		return;
	}
	
	var row = users[0];
	
	xyzdialog({
		dialog : 'editUserPassword',
		title : '重设['+row.username+']的登录密码',
		content:'<form><table><tr><td style="width:200px; text-align:right;">用户名:</td><td>'+row.username+'</td></tr><tr><td style="width:200px; text-align:right;">昵称:</td><td>'+row.nickName+'</td></tr><tr><td style="width:200px; text-align:right;">新密码:</td><td><input type="password" id="passwordForm"  class="easyui-validatebox" data-options="required:true,validType:\'length[3,20]\'"/></td></tr><tr><td style="width:200px; text-align:right;">确认密码:</td><td><input type="password" id="password2Form"  class="easyui-validatebox" data-options="required:true,validType:\'length[3,20]\'"/></td></tr></table></form>',
	    fit:false,
	    height:300,
	    width:500,
	    buttons:[{
			text:'确定',
			handler:function(){
				editUserPasswordSubmit(row.username);
			}
		},{
			text:'取消',
			handler:function(){
				$("#editUserPassword").dialog("destroy");
			}
		}]
	});
}


function editUserPasswordSubmit(username){
	
	if(!$("form").form('validate')){
		return false;
	}
	
	var password = $("#passwordForm").val();
	var password2 = $("#password2Form").val();
	if(password!=password2){
		top.$.messager.alert("提示","两次输入的密码不一致，请重新输入！","info");
		return;
	}
	password3 = $.md5(password).substr(8,16);
	
	$.ajax({
		url : "../AdminUserWS/editUserPassword.do",
		type : "POST",
		data : {
			username : username,
			password : password3
		},
		async : false,
		dataType : "json",
		success : function(data) {
			if(data.status==1){
				$("#editUserPassword").dialog("destroy");
				top.$.messager.alert("提示","操作成功！","info");
			}else{
				top.$.messager.alert("警告",data.msg,"warning");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			top.window.AjaxError(XMLHttpRequest, textStatus, errorThrown);
		}
	});
}
