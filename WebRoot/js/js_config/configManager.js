$(document).ready(function() {
	
	$("#productButton").click(function(){
		loadTable();
	});
	
	initTable();
	
});

function initTable(){
	
	var toolbar = [];
	if(xyzControlButton('buttonCode_h20160111173000')){
		toolbar[toolbar.length]={
				text: '新增配置参数',
				border:'1px solid #bbb',
				iconCls: 'icon-add',
				handler: function(){var title=$(this).text();addConfigButton(title);}
		};
		toolbar[toolbar.length]='-';
	}
	if(xyzControlButton('buttonCode_h20160111173001')){
		toolbar[toolbar.length]={
				text: '编辑参数',
				border:'1px solid #bbb',
				iconCls: 'icon-edit',
				handler: function(){var title=$(this).text();editConfigButton(title);}
		};
		toolbar[toolbar.length]='-';
	}
	if(xyzControlButton('buttonCode_h20160111173002')){
		toolbar[toolbar.length]={
				text: '删除参数',
				border:'1px solid #bbb',
				iconCls: 'icon-remove',
				handler: function(){deleteConfigButton();}
		};
		toolbar[toolbar.length]='-';
	}	
	
	xyzgrid({
		table : 'configManagerTable',
		title : '系统参数列表',
		url:'../ConfigWS/queryConfigList.do',
		pageList : [5,10,15,30,50],
		pageSize : 10,
		toolbar:toolbar,
		singleSelect : false,
		idField : 'numberCode',
		height:'auto',
		columns : [[
            {field:'checkboxTemp',checkbox:true},
			{field:'numberCode',title:'产品编号',hidden:true},
			{field:'key',title:'参数key值',width:200},
			{field:'name',title:'参数名称',width:200},
			{field:'value',title:'参数值',width:100}
		]]
	});
	
}

function loadTable(){

	$("#configManagerTable").datagrid("load",{
		nameCn : nameCn,
		provider:provider
	});
}

function addConfigButton(title){

	xyzdialog({
		dialog : 'dialogFormDiv_addConfig',
		title : title,
	    href : '../jsp_config/addConfig.html',
	    fit:false,
	    height:700,
	    width:1000,
	    buttons:[{
			text:'确定',
			handler:function(){
				addConfigSubmit();
			}
		},{
			text:'取消',
			handler:function(){
				$("#dialogFormDiv_addConfig").dialog("destroy");
			}
		}],onLoad:function(){
			
		}
	});
	
}

function editConfigButton(title){
	
	var numberCode = $("#configManagerTable").datagrid("getChecked");
	if(numberCode.length != 1){
		top.$.messager.alert("提示","请先选中单个对象！","info");
		return;
	}
	var row = numberCode[0];
	xyzdialog({
		dialog : 'dialogFormDiv_editConfig',
		title : title,
	    href : '../jsp_config/editConfig.html',
	    fit:false,
	    height:700,
	    width:1000,
	    buttons:[{
			text:'确定',
			handler:function(){
				editConfigSubmit(row.numberCode);
			}
		},{
			text:'取消',
			handler:function(){
				$("#dialogFormDiv_editConfig").dialog("destroy");
			}
		}],
		onOpen:function(){
			xyzAjax({
				url:"../ConfigWS/getConfig.do",
				data:{
					numberCode:row.numberCode
				},
				success:function(data){
					if(data.status==1){
						$("#keyForm").val(data.content.key);
						$("#nameForm").val(data.content.name);
						$("#valueForm").val(data.content.value);
					}else{
						top.$.messager.alert("警告",data.msg,"warning");
					}
				}
			});
			
		
		}
	});
}

function addConfigSubmit(){
	var key=$("#keyForm").val();
	var name=$("#nameForm").val();
	var value=$("#valueForm").val();

	if(!$("form").form('validate')){
		return;
	}
	xyzAjax({
		url:"../ConfigWS/addConfig.do",
		data:{
			key:key,
			name:name,
			value:value
		},
		success:function(data){
			if(data.status==1){
				top.$.messager.alert("提示","操作成功","info");
				$("#dialogFormDiv_addConfig").dialog("destroy");
				$("#configManagerTable").datagrid("reload");
			}else{
				top.$.messager.alert("警告",data.msg,"warning");
			}
		}
	});
}

function editConfigSubmit(numberCode){
	
	if(!$("form").form('validate')){
		return;
	}
	var key=$("#keyForm").val();
	var name=$("#nameForm").val();
	var value=$("#valueForm").val();


	xyzAjax({
		url:"../ConfigWS/editConfig.do",
		data:{
			numberCode:numberCode,
			key:key,
			name:name,
			value:value
		},
		success:function(data){
			if(data.status==1){
				top.$.messager.alert("提示","操作成功","info");
				$("#dialogFormDiv_editConfig").dialog("destroy");
				$("#configManagerTable").datagrid("reload");
			}else{
				top.$.messager.alert("警告",data.msg,"warning");
			}
		}
	});
	
}

function deleteConfigButton(){
	var numberCode = $.map($("#configManagerTable").datagrid("getChecked"),function(p){return p.numberCode;}).join(",");
	if(numberCode == null || numberCode == ''){
		top.$.messager.alert("提示","请先选中需要删除的对象！","info");
		return;
	}
	if(!confirm("彻底删除对象，确定？")){
		return;
	}
	
	xyzAjax({
		url : '../ConfigWS/deleteConfig.do',
		data : {
			numberCode : numberCode
		},
		success : function(data) {
			if(data.status==1){
				top.$.messager.alert("提示","操作成功","info");
				$("#configManagerTable").datagrid("reload");
			}else{
				top.$.messager.alert("警告",data.msg,"warning");
			}
		}
	});
}