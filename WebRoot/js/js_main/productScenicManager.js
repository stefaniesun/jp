$(document).ready(function() {
	
	$("#productButton").click(function(){
		loadTable();
	});
	
	xyzCombobox({
		combobox : 'providerLoadForm',
		url : '../ListWS/getProviderList.do',
		onBeforeLoad: function(param){
			param.providerType = "SC";
		}
	});
	
	initTable();
	
});

function initTable(){
	
	var toolbar = [];
	if(xyzControlButton('buttonCode_h20151214181001')){
		toolbar[toolbar.length]={
				text: '新增产品',
				border:'1px solid #bbb',
				iconCls: 'icon-add',
				handler: function(){var title=$(this).text();addProductScenicButton(title);}
		};
		toolbar[toolbar.length]='-';
	}
	if(xyzControlButton('buttonCode_h20151214181002')){
		toolbar[toolbar.length]={
				text: '编辑产品',
				border:'1px solid #bbb',
				iconCls: 'icon-edit',
				handler: function(){var title=$(this).text();editProductScenicButton(title);}
		};
		toolbar[toolbar.length]='-';
	}
	if(xyzControlButton('buttonCode_h20151214181003')){
		toolbar[toolbar.length]={
				text: '删除产品',
				border:'1px solid #bbb',
				iconCls: 'icon-remove',
				handler: function(){deleteProductScenicButton();}
		};
		toolbar[toolbar.length]='-';
	}	
	
	xyzgrid({
		table : 'productScenicManagerTable',
		title : '产品列表',
		url:'../ProductScenicWS/queryProductScenicList.do',
		pageList : [5,10,15,30,50],
		pageSize : 5,
		toolbar:toolbar,
		singleSelect : false,
		idField : 'numberCode',
		height:'auto',
		columns : [[
		    {field:'checkboxTemp',checkbox:true},
			{field:'numberCode',title:'产品编号',hidden:true},
			{field:'nameCn',title:'产品名称',width:100}
		]]
	});
	
}

function loadTable(){
	var nameCn = $("#nameCn").val();
	var provider= $("#providerLoadForm").combobox("getValue");
	$("#productScenicManagerTable").datagrid("load",{
		nameCn : nameCn,
		provider:provider
	});
}

function addProductScenicButton(title){

	xyzdialog({
		dialog : 'dialogFormDiv_addProductScenic',
		title : title,
	    href : '../jsp_main/addProductScenic.html',
	    fit:false,
	    height:300,
	    width:600,
	    buttons:[{
			text:'确定',
			handler:function(){
				addProductScenicSubmit();
			}
		},{
			text:'取消',
			handler:function(){
				$("#dialogFormDiv_addProductScenic").dialog("destroy");
			}
		}],onLoad:function(){
			xyzCombobox({
				combobox : 'providerForm',
				url : '../ListWS/getProviderList.do',
				required:true,
				onBeforeLoad: function(param){
					param.providerType = "SC";
				}
			});
		}
	});
	
}

function editProductScenicButton(title){
	
	var product = $("#productScenicManagerTable").datagrid("getChecked");
	if(product.length != 1){
		top.$.messager.alert("提示","请先选中单个对象！","info");
		return;
	}
	var row = product[0];
	
	xyzdialog({
		dialog : 'dialogFormDiv_editProductScenic',
		title : title,
	    href : '../jsp_main/editProductScenic.html',
	    fit:false,
	    height:300,
	    width:600,
	    buttons:[{
			text:'确定',
			handler:function(){
				editProductScenicSubmit(row.numberCode);
			}
		},{
			text:'取消',
			handler:function(){
				$("#dialogFormDiv_editProductScenic").dialog("destroy");
			}
		}],
		onLoad:function(){
			xyzCombobox({
				combobox : 'providerForm',
				url : '../ListWS/getProviderList.do',
				required:true,
				lazy:false,
				onBeforeLoad: function(param){
					param.providerType = "SC";
				}
			});
			$("#nameCnForm").val(row.nameCn);
			$("#providerForm").combobox("setValue",row.provider);
		}
	});
}

function addProductScenicSubmit(){
	var nameCn=$("#nameCnForm").val();
	var provider=$("#providerForm").combobox("getValue");
	if(!$("form").form('validate')){
		return;
	}
	xyzAjax({
		url:"../ProductScenicWS/addProductScenic.do",
		data:{
			nameCn:nameCn,
			provider:provider
		},
		success:function(data){
			if(data.status==1){
				top.$.messager.alert("提示","操作成功","info");
				$("#dialogFormDiv_addProductScenic").dialog("destroy");
				$("#productScenicManagerTable").datagrid("reload");
			}else{
				top.$.messager.alert("警告",data.msg,"warning");
			}
		}
	});
}

function editProductScenicSubmit(numberCode){
	
	if(!$("form").form('validate')){
		return;
	}
	var nameCn=$("#nameCnForm").val();
	var provider=$("#providerForm").combobox("getValue");

	xyzAjax({
		url:"../ProductScenicWS/editProductScenic.do",
		data:{
			numberCode:numberCode,
			nameCn:nameCn,
			provider:provider
		},
		success:function(data){
			if(data.status==1){
				top.$.messager.alert("提示","操作成功","info");
				$("#dialogFormDiv_editProductScenic").dialog("destroy");
				$("#productScenicManagerTable").datagrid("reload");
			}else{
				top.$.messager.alert("警告",data.msg,"warning");
			}
		}
	});
	
}

function deleteProductScenicButton(){
	var products = $.map($("#productScenicManagerTable").datagrid("getChecked"),function(p){return p.numberCode;}).join(",");
	if(products == null || products == ''){
		top.$.messager.alert("提示","请先选中需要删除的对象！","info");
		return;
	}
	if(!confirm("彻底删除对象，确定？")){
		return;
	}
	
	xyzAjax({
		url : '../ProductScenicWS/deleteProductScenic.do',
		data : {
			numberCodes : products
		},
		success : function(data) {
			if(data.status==1){
				top.$.messager.alert("提示","操作成功","info");
				$("#productScenicManagerTable").datagrid("reload");
			}else{
				top.$.messager.alert("警告",data.msg,"warning");
			}
		}
	});
}