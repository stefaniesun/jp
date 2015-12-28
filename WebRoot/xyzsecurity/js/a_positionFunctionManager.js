$(document).ready(function() {
	
	position = $("#dialogFormDiv_managerPositionFunctionIframe",window.parent.document).attr("name");
	$("#currentPosition").val(position);
	
	initTable();
});

function initTable(){
	
	var toolbarTrue = [];
	
	toolbarTrue[toolbarTrue.length] = {
		iconCls: 'icon-search',
		text : '查询已选中功能',
		handler: function(){
			functionQueryTrue();
		}
	};
	
	toolbarTrue[toolbarTrue.length] = '-';
	
	toolbarTrue[toolbarTrue.length] = {
			iconCls: 'icon-remove',
			text : '删除岗位内功能',
			handler: function(){
				deletePositionFunction();
			}
		};
	
	var toolbarFalse = [];
	
	toolbarFalse[toolbarFalse.length] = {
		iconCls: 'icon-search',
		text : '查询未选中功能',
		handler: function(){
			functionQueryFalse();
		}
	};
	
	toolbarFalse[toolbarFalse.length] = '-';
	
	toolbarFalse[toolbarFalse.length] = {
			iconCls: 'icon-add',
			text : '添加功能进岗位',
			handler: function(){
				addPositionFunction();
			}
		};
	
	xyzgrid({
		table : 'positionFunctionTrueTable',
		title : '已选中的功能',
		url:'../AdminPositionWS/queryPositionFunctionTrueList.do',
		height:'auto',
		pagination : false,
		toolbar : toolbarTrue,
		singleSelect : false,
		pageSize : 5,
		height:'auto',
		columns:[[
			{field:'checkboxTemp',checkbox:true,width:40},
			{field:'groupCn',title:'组名称',width:200},
			{field:'numberCode',title:'功能编号',width:200},
			{field:'nameCn',title:'功能名称',width:600}
		]],
		queryParams : {
			position : position
		}
	});
	
	xyzgrid({
		table : 'positionFunctionFalseTable',
		title : '未选中的功能',
		url:'../AdminPositionWS/queryPositionFunctionFalseList.do',
		height:'auto',
		pagination : false,
		toolbar : toolbarFalse,
		singleSelect : false,
		pageSize : 5,
		height:'auto',
		columns:[[
			{field:'checkboxTemp',checkbox:true,width:40},
			{field:'groupCn',title:'组名称',width:200},
			{field:'numberCode',title:'功能编号',width:200},
			{field:'nameCn',title:'功能名称',width:600}
		]],
		queryParams : {
			position : position
		}
	});
}

function functionQueryTrue(){
	$("#positionFunctionTrueTable").datagrid('load',{
		position : position
	});
}

function functionQueryFalse(){
	$("#positionFunctionFalseTable").datagrid('load',{
		position : position
	});
}

function addPositionFunction(){
	var functions = $.map($("#positionFunctionFalseTable").datagrid("getChecked"),function(p){return p.numberCode;}).join(",");
	if(xyzIsNull(functions)){
		top.$.messager.alert("提示","请先选中需要操作的对象！","info");
		return;
	}
	
	xyzAjax({
		url : "../AdminPositionWS/addPositionFunction.do",
		data : {
			position : position,
			functions : functions
		},
		success : function(data) {
			if(data.status==1){
				$("#positionFunctionTrueTable").datagrid("reload");
				$("#positionFunctionFalseTable").datagrid("reload");
				top.$.messager.alert("提示","操作成功！","info");
			}else{
				top.$.messager.alert("警告",data.msg,"warning");
			}
		}
	});
}

function deletePositionFunction(){
	var functions = $.map($("#positionFunctionTrueTable").datagrid("getChecked"),function(p){return p.numberCode;}).join(",");
	if(xyzIsNull(functions)){
		top.$.messager.alert("提示","请先选中需要操作的对象！","info");
		return;
	}
	if(!confirm("彻底删除对象，确定？")){
		return;
	}
	
	xyzAjax({
		url : "../AdminPositionWS/deletePositionFunction.do",
		data : {
			position : position,
			functions : functions
		},
		success : function(data) {
			if(data.status==1){
				$("#positionFunctionTrueTable").datagrid("reload");
				$("#positionFunctionFalseTable").datagrid("reload");
				top.$.messager.alert("提示","操作成功！","info");
			}else{
				top.$.messager.alert("警告",data.msg,"warning");
			}
		}
	});
}