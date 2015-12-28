$(document).ready(function() {
	
	position = $("#dialogFormDiv_managerPositionButtonIframe",window.parent.document).attr("name");
	$("#currentPosition").val(position);
	
	initTable();
});

function initTable(){
	
	var toolbarTrue = [];
	
	toolbarTrue[toolbarTrue.length] = {
		iconCls: 'icon-search',
		text : '查询已选中操作',
		handler: function(){
			apiQueryTrue();
		}
	};
	
	toolbarTrue[toolbarTrue.length] = '-';
	
	toolbarTrue[toolbarTrue.length] = {
			iconCls: 'icon-remove',
			text : '删除岗位内操作',
			handler: function(){deletePositionButton();}
		};
	
	var toolbarFalse = [];
	
	toolbarFalse[toolbarFalse.length] = {
			iconCls: 'icon-search',
			text : '查询未选中操作',
			handler: function(){apiQueryFalse();}
		};
	
	toolbarFalse[toolbarFalse.length] = '-';
	
	toolbarFalse[toolbarFalse.length] = {
		iconCls: 'icon-add',
		text : '添加操作进岗位',
		handler: function(){
			addPositionButton();
		}
	};
	
	
	xyzgrid({
		table : 'positionButtonTrueTable',
		title : '已选中的操作',
		url:'../AdminPositionWS/queryPositionButtonTrueList.do',
		height:'auto',
		pagination : false,
		pageList : [5,10,15,30,50],
		toolbar : toolbarTrue,
		singleSelect : false,
		pageSize : 5,
		height:'auto',
		idField : 'buttonCode',
		columns:[[
			{field:'functionNameCn',title:'所属功能',width:200},
			{field:'checkboxTemp',checkbox:true,width:40},
			{field:'buttonCode',title:'操作编号',width:200},
			{field:'nameCn',title:'操作名称',width:600}
		]],
		queryParams : {
			position : position
		}
	});
	
	xyzgrid({
		table : 'positionButtonFalseTable',
		title : '未选中的操作',
		url:'../AdminPositionWS/queryPositionButtonFalseList.do',
		height:'auto',
		pagination : false,
		pageList : [5,10,15,30,50],
		toolbar : toolbarFalse,
		singleSelect : false,
		pageSize : 5,
		height:'auto',
		idField : 'buttonCode',
		columns:[[
		    {field:'functionNameCn',title:'所属功能',width:200},
			{field:'checkboxTemp',checkbox:true,width:40},
			{field:'buttonCode',title:'操作编号',width:200},
			{field:'nameCn',title:'操作名称',width:600}
			
		]],
		queryParams : {
			position : position
		}
	});
}

function addPositionButton(){
	var buttons = $.map($("#positionButtonFalseTable").datagrid("getChecked"),function(p){return p.buttonCode;}).join(",");
	if(xyzIsNull(buttons)){
		top.$.messager.alert("提示","请先选中需要操作的对象！","info");
		return;
	}
	
	xyzAjax({
		url : "../AdminPositionWS/addPositionButton.do",
		data : {
			position : position,
			buttons : buttons
		},
		success : function(data) {
			if(data.status==1){
				$("#positionButtonTrueTable").datagrid("reload");
				$("#positionButtonFalseTable").datagrid("reload");
				top.$.messager.alert("提示","操作成功！","info");
			}else{
				top.$.messager.alert("警告",data.msg,"warning");
			}
		}
	});
}

function deletePositionButton(){
	var buttons = $.map($("#positionButtonTrueTable").datagrid("getChecked"),function(p){return p.buttonCode;}).join(",");
	if(xyzIsNull(buttons)){
		top.$.messager.alert("提示","请先选中需要操作的对象！","info");
		return;
	}
	if(!confirm("彻底删除对象，确定？")){
		return;
	}
	
	xyzAjax({
		url : "../AdminPositionWS/deletePositionButton.do",
		data : {
			position : position,
			buttons : buttons
		},
		success : function(data) {
			if(data.status==1){
				$("#positionButtonTrueTable").datagrid("reload");
				$("#positionButtonFalseTable").datagrid("reload");
				top.$.messager.alert("提示","操作成功！","info");
			}else{
				top.$.messager.alert("警告",data.msg,"warning");
			}
		}
	});
}

function apiQueryTrue(){
	$("#positionButtonTrueTable").datagrid('load',{
		position : position
	});
}

function apiQueryFalse(){
	$("#positionButtonFalseTable").datagrid('load',{
		position : position
	});
}