$(document).ready(function() {
	
	
	$("#productButton").click(function(){
		loadTable();
	});
	
	initTable();
	
});

function initTable(){
	
	var toolbar = [];
	if(xyzControlButton('buttonCode_w20151215174401')){
		toolbar[toolbar.length]={
				text: '新增供应商',
				border:'1px solid #bbb',
				iconCls: 'icon-add',
				handler: function(){var title=$(this).text();addProviderButton(title);}
		};
		toolbar[toolbar.length]='-';
	}
	if(xyzControlButton('buttonCode_w20151215174402')){
		toolbar[toolbar.length]={
				text: '编辑供应商',
				border:'1px solid #bbb',
				iconCls: 'icon-edit',
				handler: function(){var title=$(this).text();editProviderButton(title);}
		};
		toolbar[toolbar.length]='-';
	}
	if(xyzControlButton('buttonCode_w20151215174403')){
		toolbar[toolbar.length]={
				text: '删除供应商',
				border:'1px solid #bbb',
				iconCls: 'icon-remove',
				handler: function(){deleteProviderButton();}
		};
		toolbar[toolbar.length]='-';
	}	
	
	xyzgrid({
		table : 'providerManagerTable',
		title : '产品列表',
		url:'../ProviderWS/queryProviderList.do',
		pageList : [5,10,15,30,50],
		pageSize : 5,
		toolbar:toolbar,
		singleSelect : false,
		idField : 'numberCode',
		height:'auto',
		columns : [[
		    {field:'checkboxTemp',checkbox:true},
			{field:'numberCode',title:'供应商编号',hidden:true},
			{field:'nameCn',title:'供应商名称',width:100},
			{field:'type',title:'供应商类型',width:100,
				formatter : function(value,row,index){
					if(value == 'HO'){
						return "酒店";
					}else if(value=='SC'){
						return "景点";
					}
				}
			},
			{field:'phone',title:'电话',width:100},
			{field:'qq',title:'QQ帐号',width:100},
			{field:'email',title:'邮箱',width:100},
			{field:'linkman',title:'联系人',width:100},
			{field:'address',title:'地址',width:100},
		]]
	});
	
}

function loadTable(){
	var nameCn = $("#nameCn").val();
	var providerType= $("#providerTypeForm").combobox("getValue");
	$("#providerManagerTable").datagrid("load",{
		nameCn : nameCn,
		providerType:providerType
	});
}

function addProviderButton(title){
	xyzdialog({
		dialog : 'dialogFormDiv_addProvider',
		title : title,
	    href : '../jsp_main/addProvider.html',
	    fit:false,
	    height:500,
	    width:600,
	    buttons:[{
			text:'确定',
			handler:function(){
				addProviderSubmit();
			}
		},{
			text:'取消',
			handler:function(){
				$("#dialogFormDiv_addProvider").dialog("destroy");
			}
		}]
	});
	
}

function editProviderButton(title){
	
	var provider = $("#providerManagerTable").datagrid("getChecked");
	if(provider.length != 1){
		top.$.messager.alert("提示","请先选中单个对象！","info");
		return;
	}
	var row = provider[0];
	
	xyzdialog({
		dialog : 'dialogFormDiv_editProvider',
		title : title,
	    href : '../jsp_main/editProvider.html',
	    fit:false,
	    height:500,
	    width:600,
	    buttons:[{
			text:'确定',
			handler:function(){
				editProviderSubmit(row.numberCode);
			}
		},{
			text:'取消',
			handler:function(){
				$("#dialogFormDiv_editProvider").dialog("destroy");
			}
		}],
		onLoad:function(){
			$("#nameCnForm").val(row.nameCn);
			$("#levelForm").val(row.level);
			$("#levelSystemForm").val(row.levelSystem);
			$("#phoneForm").val(row.phone);
			$("#qqForm").val(row.qq);
			$("#emailForm").val(row.email);
			$("#linkmanForm").val(row.linkman);
			$("#regionFirstForm").val(row.regionFirst);
			$("#regionSecondForm").val(row.regionSecond);
			$("#regionThirdForm").val(row.regionThird);
			$("#addressForm").val(row.address);
			
		}
	});
}

function addProviderSubmit(){
	var nameCn=$("#nameCnForm").val();
	var type=$("#typeForm").combobox("getValue");
	var level=$("#levelForm").val();
	var levelSystem=$("#levelSystemForm").val();
	var phone=$("#phoneForm").val();
	var qq=$("#qqForm").val();
	var email=$("#emailForm").val();
	var linkman=$("#linkmanForm").val();
	var regionFirst=$("#regionFirstForm").val();
	var regionSecond=$("#regionSecondForm").val();
	var regionThird=$("#regionThirdForm").val();
	var address=$("#addressForm").val();
	
	if(!$("form").form('validate')){
		return;
	}
	
	xyzAjax({
		url:"../ProviderWS/addProvider.do",
		data:{
			nameCn:nameCn,
			type:type,
			level:level,
			levelSystem:levelSystem,
			phone:phone,
			qq:qq,
			email:email,
			linkman:linkman,
			regionFirst:regionFirst,
			regionSecond:regionSecond,
			regionThird:regionThird,
			address:address
		},
		success:function(data){
			if(data.status==1){
				top.$.messager.alert("提示","操作成功","info");
				$("#dialogFormDiv_addProvider").dialog("destroy");
				$("#providerManagerTable").datagrid("reload");
			}else{
				top.$.messager.alert("警告",data.msg,"warning");
			}
		}
	});
}

function editProviderSubmit(numberCode){
	var nameCn=$("#nameCnForm").val();
	var level=$("#levelForm").val();
	var levelSystem=$("#levelSystemForm").val();
	var phone=$("#phoneForm").val();
	var qq=$("#qqForm").val();
	var email=$("#emailForm").val();
	var linkman=$("#linkmanForm").val();
	var regionFirst=$("#regionFirstForm").val();
	var regionSecond=$("#regionSecondForm").val();
	var regionThird=$("#regionThirdForm").val();
	var address=$("#addressForm").val();
	if(!$("form").form('validate')){
		return;
	}
	
	xyzAjax({
		url:"../ProviderWS/editProvider.do",
		data:{
			numberCode:numberCode,
			nameCn:nameCn,
			level:level,
			levelSystem:levelSystem,
			phone:phone,
			qq:qq,
			email:email,
			linkman:linkman,
			regionFirst:regionFirst,
			regionSecond:regionSecond,
			regionThird:regionThird,
			address:address
		},
		success:function(data){
			if(data.status==1){
				top.$.messager.alert("提示","操作成功","info");
				$("#dialogFormDiv_editProvider").dialog("destroy");
				$("#providerManagerTable").datagrid("reload");
			}else{
				top.$.messager.alert("警告",data.msg,"warning");
			}
		}
	});
	
}

function deleteProviderButton(){
	var products = $.map($("#providerManagerTable").datagrid("getChecked"),function(p){return p.numberCode;}).join(",");
	if(products == null || products == ''){
		top.$.messager.alert("提示","请先选中需要删除的对象！","info");
		return;
	}
	if(!confirm("彻底删除对象，确定？")){
		return;
	}
	
	xyzAjax({
		url : '../ProviderWS/deleteProvider.do',
		data : {
			numberCodes : products
		},
		success : function(data) {
			if(data.status==1){
				top.$.messager.alert("提示","操作成功","info");
				$("#providerManagerTable").datagrid("reload");
			}else{
				top.$.messager.alert("警告",data.msg,"warning");
			}
		}
	});
}