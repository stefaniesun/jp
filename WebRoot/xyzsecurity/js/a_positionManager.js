$(document).ready(function() {
	
	initTable();
	
	$("#positionQueryButton").click(function(){
		loadTable();
	});
});

function initTable(){
	
	var toolbar = [];
	
	toolbar[toolbar.length] = {
		iconCls: 'icon-add',
		text : '新建岗位',
		handler: function(){
			var title = $(this).text();
			addPosition(title);
		}
	};
	
	toolbar[toolbar.length] = '-';
	
	toolbar[toolbar.length] = {
			iconCls: 'icon-edit',
			text : '编辑岗位',
			handler: function(){editPosition();}
		};
	toolbar[toolbar.length] = '-';
	
	toolbar[toolbar.length] = {
		iconCls: 'icon-remove',
		text : '删除岗位',
		handler: function(){deletePosition();}
	};
	
	xyzgrid({
		table : 'positionManagerTable',
		title:'岗位列表',
		url:'../AdminPositionWS/queryPositionList.do',
		toolbar : toolbar,
		singleSelect : false,
		columns:[[    
		    {field:'checkboxTemp',checkbox:true},  
		    {field:'numberCode',title:'编号'},
			{field:'nameCn',title:'名称'},
			{field:'priority',title:'岗位级别'},
			{field:'countUser',title:'使用人数'},
			{field:'remark',title:'备注'},
			{field:'operTemp',title:'操作',
				formatter: function(value,row,index){
					var buttonTemp1 =  "<a href='javascript:void(0);' onclick='managerPositionFunction(\""+row.numberCode+"\",\""+row.nameCn+"\")'>管理功能</a>";
					var buttonTemp2 =  "<a href='javascript:void(0);' onclick='managerPositionButton(\""+row.numberCode+"\",\""+row.nameCn+"\")'>管理操作</a>";
					return buttonTemp1+" "+buttonTemp2;
				}
			},
			{field:'alterDate',title:'修改时间'},
			{field:'addDate',title:'添加时间',hidden:true}
		]]
	});
}

function loadTable(){
	var nameCn = $("#nameCn").val();
	$("#positionManagerTable").datagrid('load',{
		nameCn : nameCn
	});
}

function editPositionSubmit(){
	
	if(!$("form").form('validate')){
		return false;
	}
	
	var numberCode = $("#numberCodeForm").val();
	var nameCn = $("#nameCnForm").val();
	var remark = $("#remarkForm").val();
	
	xyzAjax({
		url : "../AdminPositionWS/editPosition.do",
		data : {
			numberCode : numberCode,
			nameCn : nameCn,
			remark : remark
		},
		success : function(data) {
			if(data.status==1){
				$("#positionManagerTable").datagrid("reload");
				top.$.messager.alert("提示","操作成功！","info");
				$("#dialogFormDiv_editPosition").dialog("destroy");
			}else{
				top.$.messager.alert("警告",data.msg,"warning");
			}
		}
	});
}

function addPositionSubmit(){
	
	if(!$("form").form('validate')){
		return false;
	}
	
	var numberCode = $("#numberCodeForm").val();
	var nameCn = $("#nameCnForm").val();
	var remark = $("#remarkForm").val();
	var priority = $("#priorityForm").numberbox("getValue");

	xyzAjax({
		url : "../AdminPositionWS/addPosition.do",
		data : {
			numberCode : numberCode,
			nameCn : nameCn,
			remark : remark,
			priority : priority
		},
		success : function(data) {
			if(data.status==1){
				$("#positionManagerTable").datagrid("reload");
				top.$.messager.alert("提示","操作成功！","info");
				$("#dialogFormDiv_addPosition").dialog("destroy");
			}else{
				top.$.messager.alert("警告",data.msg,"warning");
			}
		}
	});
}

function managerPositionFunction(numberCode,nameCn){
	xyzdialog({
		dialog : 'dialogFormDiv_managerPositionFunction',
		title : "管理功能["+nameCn+"]",
	    content : "<iframe id='dialogFormDiv_managerPositionFunctionIframe' frameborder='0' name='"+numberCode+"'></iframe>",
	    buttons:[{
			text:'返回',
			handler:function(){
				$("#dialogFormDiv_managerPositionFunction").dialog("destroy");
			}
		}]
	});
	var tempWidth = $("#dialogFormDiv_managerPositionFunction").css("width");
	var tempHeight = $("#dialogFormDiv_managerPositionFunction").css("height");
	var tempWidth2 = parseInt(tempWidth.split("px")[0]);
	var tempHeight2 = parseInt(tempHeight.split("px")[0]);
	$("#dialogFormDiv_managerPositionFunctionIframe").css("width",(tempWidth2-20)+"px");
	$("#dialogFormDiv_managerPositionFunctionIframe").css("height",(tempHeight2-50)+"px");
	$("#dialogFormDiv_managerPositionFunctionIframe").attr("src","../xyzsecurity/a_positionFunctionManager.html");
}

function managerPositionButton(numberCode,nameCn){
	xyzdialog({
		dialog : 'dialogFormDiv_managerPositionButton',
		title : "管理功能["+nameCn+"]",
	    content : "<iframe id='dialogFormDiv_managerPositionButtonIframe' frameborder='0' name='"+numberCode+"'></iframe>",
	    buttons:[{
			text:'返回',
			handler:function(){
				$("#dialogFormDiv_managerPositionButton").dialog("destroy");
			}
		}]
	});
	var tempWidth = $("#dialogFormDiv_managerPositionButton").css("width");
	var tempHeight = $("#dialogFormDiv_managerPositionButton").css("height");
	var tempWidth2 = parseInt(tempWidth.split("px")[0]);
	var tempHeight2 = parseInt(tempHeight.split("px")[0]);
	$("#dialogFormDiv_managerPositionButtonIframe").css("width",(tempWidth2-20)+"px");
	$("#dialogFormDiv_managerPositionButtonIframe").css("height",(tempHeight2-50)+"px");
	$("#dialogFormDiv_managerPositionButtonIframe").attr("src","../xyzsecurity/a_positionButtonManager.html");
}

function addPosition(title){
	xyzdialog({
		dialog : 'dialogFormDiv_addPosition',
		title : title,
	    href : '../xyzsecurity/a_addPosition.html',
	    fit:false,
	    height:400,
	    width:600,
	    buttons:[{
			text:'确定',
			handler:function(){
				addPositionSubmit();
			}
		},{
			text:'取消',
			handler:function(){
				$("#dialogFormDiv_addPosition").dialog("destroy");
			}
		}]
	});
}

function editPosition(){
	var positions = $("#positionManagerTable").datagrid("getChecked");
	if(positions.length != 1){
		top.$.messager.alert("提示","请先选中单个对象！","info");
		return;
	}
	
	var row = positions[0];
	
	var title = $(this).text();
	xyzdialog({
		dialog : 'dialogFormDiv_editPosition',
		title : title,
	    href : '../xyzsecurity/a_editPosition.html',
	    fit:false,
	    height:400,
	    width:600,
	    buttons:[{
			text:'确定',
			handler:function(){
				editPositionSubmit();
			}
		},{
			text:'取消',
			handler:function(){
				$("#dialogFormDiv_editPosition").dialog("destroy");
			}
		}],
		onLoad : function(){
			$("#numberCodeForm").val(row.numberCode);
			$("#nameCnForm").val(row.nameCn);
			$("#remarkForm").val(row.remark);
		}
	});
}

function deletePosition(){
	var positions = $.map($("#positionManagerTable").datagrid("getChecked"),function(p){return p.numberCode;}).join(",");
	if(xyzIsNull(positions)){
		top.$.messager.alert("提示","请先选中需要删除的对象！","info");
		return;
	}
	if(!confirm("彻底删除对象，确定？")){
		return;
	}
	
	xyzAjax({
		url : "../AdminPositionWS/deletePosition.do",
		data : {
			positions : positions
		},
		success : function(data) {
			if(data.status==1){
				$("#positionManagerTable").datagrid("reload");
				top.$.messager.alert("提示","操作成功！","info");
			}else{
				top.$.messager.alert("警告",data.msg,"warning");
			}
		}
	});
}
