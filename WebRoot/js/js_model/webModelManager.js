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
				text: '新增模块',
				border:'1px solid #bbb',
				iconCls: 'icon-add',
				handler: function(){var title=$(this).text();addModelButton(title);}
		};
		toolbar[toolbar.length]='-';
	}
	if(xyzControlButton('buttonCode_h20160111173001')){
		toolbar[toolbar.length]={
				text: '编辑模块',
				border:'1px solid #bbb',
				iconCls: 'icon-edit',
				handler: function(){var title=$(this).text();editProductButton(title);}
		};
		toolbar[toolbar.length]='-';
	}
	if(xyzControlButton('buttonCode_h20160111173002')){
		toolbar[toolbar.length]={
				text: '删除模块',
				border:'1px solid #bbb',
				iconCls: 'icon-remove',
				handler: function(){deleteProductButton();}
		};
		toolbar[toolbar.length]='-';
	}	
	
	xyzgrid({
		table : 'webModelManagerTable',
		title : 'Web模块列表',
		url:'../WebModelWS/queryModelList.do',
		pageList : [5,10,15,30,50],
		pageSize : 5,
		toolbar:toolbar,
		singleSelect : false,
		idField : 'numberCode',
		height:'auto',
		columns : [[
			{field:'numberCode',title:'模块编号',hidden:true},
			{field:'modelName',title:'模块名称',width:500},
			{field:'modelType',title:'模块类别',width:100,
				formatter:function(val,rec){
					if(val=="1"){
						return "肉类海鲜";
					}else if(val=="2"){
						return "南北干货";
					}else if(val=="3"){
						return "酒水饮料";
					}
				}
			},
			{field:'url',title:'资源地址',width:100}
		]]
	});
	
}

function loadTable(){
	var nameCn = $("#nameCn").val();
	var provider= $("#providerLoadForm").combobox("getValue");
	$("#productManagerTable").datagrid("load",{
		nameCn : nameCn,
		provider:provider
	});
}

function addModelButton(title){

	xyzdialog({
		dialog : 'dialogFormDiv_addModel',
		title : title,
	    href : '../jsp_model/addWebModel.html',
	    fit:false,
	    height:700,
	    width:800,
	    buttons:[{
			text:'确定',
			handler:function(){
				addModelSubmit();
			}
		},{
			text:'取消',
			handler:function(){
				$("#dialogFormDiv_addModel").dialog("destroy");
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

function editProductButton(title){
	
	var product = $("#productManagerTable").datagrid("getChecked");
	if(product.length != 1){
		top.$.messager.alert("提示","请先选中单个对象！","info");
		return;
	}
	var row = product[0];
	
	xyzdialog({
		dialog : 'dialogFormDiv_editProduct',
		title : title,
	    href : '../jsp_main/editProduct.html',
	    fit:false,
	    height:300,
	    width:600,
	    buttons:[{
			text:'确定',
			handler:function(){
				editProductSubmit(row.numberCode);
			}
		},{
			text:'取消',
			handler:function(){
				$("#dialogFormDiv_editProduct").dialog("destroy");
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

function addProductSubmit(){
	var name=$("#nameForm").val();
	var type=$("#typeForm").val();
	var price=$("#priceForm").val();
	var stock=$("#stockForm").val();
	if(!$("form").form('validate')){
		return;
	}
	xyzAjax({
		url:"../ProductWS/addProduct.do",
		data:{
			name:name,
			type:type,
			price:price,
			stock:stock
		},
		success:function(data){
			if(data.status==1){
				top.$.messager.alert("提示","操作成功","info");
				$("#dialogFormDiv_addProduct").dialog("destroy");
				$("#productManagerTable").datagrid("reload");
			}else{
				top.$.messager.alert("警告",data.msg,"warning");
			}
		}
	});
}

function editProductSubmit(numberCode){
	
	if(!$("form").form('validate')){
		return;
	}
	var nameCn=$("#nameCnForm").val();
	var provider=$("#providerForm").combobox("getValue");

	xyzAjax({
		url:"../ProductWS/editProduct.do",
		data:{
			numberCode:numberCode,
			nameCn:nameCn,
			provider:provider
		},
		success:function(data){
			if(data.status==1){
				top.$.messager.alert("提示","操作成功","info");
				$("#dialogFormDiv_editProduct").dialog("destroy");
				$("#productManagerTable").datagrid("reload");
			}else{
				top.$.messager.alert("警告",data.msg,"warning");
			}
		}
	});
	
}

function deleteProductButton(){
	var products = $.map($("#productManagerTable").datagrid("getChecked"),function(p){return p.numberCode;}).join(",");
	if(products == null || products == ''){
		top.$.messager.alert("提示","请先选中需要删除的对象！","info");
		return;
	}
	if(!confirm("彻底删除对象，确定？")){
		return;
	}
	
	xyzAjax({
		url : '../ProductWS/deleteProduct.do',
		data : {
			numberCodes : products
		},
		success : function(data) {
			if(data.status==1){
				top.$.messager.alert("提示","操作成功","info");
				$("#productManagerTable").datagrid("reload");
			}else{
				top.$.messager.alert("警告",data.msg,"warning");
			}
		}
	});
}