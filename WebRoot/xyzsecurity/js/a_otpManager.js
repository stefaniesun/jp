//初始化
$(document).ready(function() {
	
	initTable();
	
	$("#otpQueryButton").click(function(){
		loadTable();
	});
	
});

function initTable(){
	
	var toolbar = [];
	
	if(xyzControlButton("buttonCode_q20141227103304")){
		toolbar[toolbar.length] = {
				iconCls: 'icon-add',
				text : '新建令牌',
				handler: function(){
					var title = $(this).text();
					addOtp(title);
				}
			};
		toolbar[toolbar.length] = '-';
	}
	
	if(xyzControlButton("buttonCode_q20141227103305")){
		toolbar[toolbar.length] = {
				iconCls: 'icon-remove',
				text : '删除令牌',
				handler: function(){deleteOtp();}
			};
	}
	
	xyzgrid({
		table : 'otpManagerTable',
		title:'令牌列表',
		url:'../AdminOptWS/queryOtpList.do',
		toolbar : toolbar,
		idField : 'tokenNum',
		singleSelect : false,
		columns:[[
		    {field:'checkboxTemp',checkbox:true},  
			{field:'tokenNum',title:'令牌编号'},
			{field:'authkey',title:'令牌密钥'},  
			{field:'countUser',title:'绑定用户数'},
			{field:'usernames',title:'绑定用户'},
			{field:'alterDate',title:'修改时间'},
			{field:'addDate',title:'添加时间',hidden:true}
		]]
	});
}

function loadTable(){
	var tokenNum = $("#tokenNum").val();
	var flag = $("input[name='flag']:checked").val();
	$("#otpManagerTable").datagrid('load',{
		tokenNum : tokenNum,
		flag : flag
	});
}

function addOtpFormSubmit(){
	
	if(!$("form").form('validate')){
		return false;
	}
	
	var tokenNum = $("#tokenNumForm").val();
	var authkey = $("#authkeyForm").val();
	
	xyzAjax({
		url : "../AdminOptWS/addOtp.do",
		data : {
			tokenNum : tokenNum,
			authkey : authkey
		},
		success : function(data) {
			if(data.status==1){
				$("#otpManagerTable").datagrid("reload");
				top.$.messager.alert("提示","操作成功！","info");
				$("#dialogFormDiv_addOpt").dialog("destroy");
			}else{
				top.$.messager.alert("警告",data.msg,"warning");
			}
		}
	});
}

function addOtp(title){
	xyzdialog({
		dialog : 'dialogFormDiv_addOpt',
		title : title,
	    href : '../xyzsecurity/a_addOtp.html',
	    fit:false,
	    width:500,
	    buttons:[{
			text:'确定',
			handler:function(){
				addOtpFormSubmit();
			}
		},{
			text:'取消',
			handler:function(){
				$("#dialogFormDiv_addOpt").dialog("destroy");
			}
		}]
	});
}

function deleteOtp(){
	var otps = $.map($("#otpManagerTable").datagrid("getChecked"),function(p){return p.iidd;}).join(",");
	if(xyzIsNull(otps)){
		top.$.messager.alert("提示","请先选中需要删除的对象！","info");
		return;
	}
	if(!confirm("彻底删除对象，确定？")){
		return;
	}
	
	xyzAjax({
		url : "../AdminOptWS/deleteOtp.do",
		data : {
			otps : otps
		},
		success : function(data) {
			if(data.status==1){
				$("#otpManagerTable").datagrid("reload");
				top.$.messager.alert("提示","操作成功！","info");
			}else{
				top.$.messager.alert("警告",data.msg,"warning");
			}
		}
	});
}
