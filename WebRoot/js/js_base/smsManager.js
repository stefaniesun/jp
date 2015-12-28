$(document).ready(function() {
	
	xyzCombobox({
		combobox:'username',
		url : '../AdminUserWS/getSecurityUserList.do'
	});
	
	initTable();
	
	$("#smsQueryButton").click(function(){
		loadTable();
	});
	$("#smsCountQueryButton").click(function(){
		querySmsCount();
	});
});

function initTable(){
	var toolbar = [];
	if(xyzControlButton("buttonCode_y20150105115301")){
		toolbar[toolbar.length]={
				text: '发短信',
				iconCls: 'icon-add',
				handler: function(){var title=$(this).text();addSms(title);}
		};
	}
	xyzgrid({
		table : 'smsTable',
		title : "已生成短信列表",
		url:'../SmsWS/querySmsList.do',
		toolbar:toolbar,
		columns:[[
		    {field:'checkboxTemp',checkbox:true,hidden:true},
		    {field:'dataKey',title:'关键字'},
		    {field:'phone',title:'手机号'},
			{field:'content',title:'内容',width:500},
			{field:'username',title:'操作人'},
			{field:'status',title:'状态'},
			{field:'operTemp1',title:'操作',width:100,
				formatter: function(value,row,index){
					var buttonTemp1 = "<a href='javascript:void(0);' onclick='updateSmsStatus(\""+row.numberCode+"\")'>更新状态</a>";
					return buttonTemp1;	    	
		    	}	
			},
			{field:'addDate',title:'添加时间'},
			{field:'alterDate',title:'修改时间',hidden:true}
		]]
	});
}

function loadTable(){
	var username = $("#username").combobox("getValue");
	var phone = $("#phone").val();
	var clientCode = $("#clientCode").val();
	var content = $("#content").val();
	var status = $("#status").val();
	var dateStart = $("#dateStart").datebox("getValue");
	var dateEnd = $("#dateEnd").datebox("getValue");
	$("#smsTable").datagrid('load',{
		clientCode : clientCode,
		phone : phone,
		content : content,
		username : username,
		status : status,
		dateStart : dateStart,
		dateEnd : dateEnd
	});
}

function querySmsCount(){
	$.ajax({
		url : "../SmsWS/querySmsCount.do",
		type : "POST",
		data : {
		},
		async : false,
		dataType : "json",
		success : function(data) {
			if(data.status==1){
				top.$.messager.alert("提示","操作成功！剩余条数为："+data.content,"info");
			}else{
				top.$.messager.alert("警告",data.msg,"warning");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			top.window.AjaxError(XMLHttpRequest, textStatus, errorThrown);
		}
	});
}

function addSms(title){
	xyzdialog({
		dialog : 'dialogFormDiv_addSms',
		title : title,
		fit : false,
		width : 400,
		height : 300,
	    href : '../jsp_core/addSms.html',
	    buttons:[{
			text:'确定',
			handler:function(){
				addSmsSubmit();
			}
		},{
			text:'取消',
			handler:function(){
				$("#dialogFormDiv_addSms").dialog("destroy");
			}
		}]
	});
}

function addSmsSubmit(){
	if(!$("form").form('validate')){
		return false;
	}
	var phone = $("#phoneForm").val();
	var content = $("#contentForm").val();
	$.ajax({
		url : "../SmsWS/addSms.do",
		type : "POST",
		data : {
			phone : phone,
			content : content
		},
		async : false,
		dataType : "json",
		success : function(data) {
			if(data.status==1){
				top.$.messager.alert("提示","操作成功！","info");
				$("#dialogFormDiv_addSms").dialog("destroy");
				$("#smsTable").datagrid("reload");
			}else{
				top.$.messager.alert("警告",data.msg,"warning");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			top.window.AjaxError(XMLHttpRequest, textStatus, errorThrown);
		}
	});
}

function updateSmsStatus(numberCode){
	$.ajax({
		url : "../SmsWS/updateSmsStatus.do",
		type : "POST",
		data : {
			numberCode : numberCode
		},
		async : false,
		dataType : "json",
		success : function(data) {
			if(data.status==1){
				top.$.messager.alert("提示","操作成功","info");
				loadTable();
			}else{
				top.$.messager.alert("警告",data.msg,"warning");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			top.window.AjaxError(XMLHttpRequest, textStatus, errorThrown);
		}
	});
}
