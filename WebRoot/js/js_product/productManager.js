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
				text: '新增产品',
				border:'1px solid #bbb',
				iconCls: 'icon-add',
				handler: function(){var title=$(this).text();addProductButton(title);}
		};
		toolbar[toolbar.length]='-';
	}
	if(xyzControlButton('buttonCode_h20160111173001')){
		toolbar[toolbar.length]={
				text: '编辑产品',
				border:'1px solid #bbb',
				iconCls: 'icon-edit',
				handler: function(){var title=$(this).text();editProductButton(title);}
		};
		toolbar[toolbar.length]='-';
	}
	if(xyzControlButton('buttonCode_h20160111173002')){
		toolbar[toolbar.length]={
				text: '删除产品',
				border:'1px solid #bbb',
				iconCls: 'icon-remove',
				handler: function(){deleteProductButton();}
		};
		toolbar[toolbar.length]='-';
	}	
	
	xyzgrid({
		table : 'productManagerTable',
		title : '产品列表',
		url:'../ProductWS/queryProductList.do',
		pageList : [5,10,15,30,50],
		pageSize : 5,
		toolbar:toolbar,
		singleSelect : false,
		idField : 'numberCode',
		height:'auto',
		columns : [[
			{field:'numberCode',title:'产品编号',hidden:true},
			{field:'name',title:'产品名称',width:500},
			{field:'type',title:'产品类别',width:100,
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
			{field:'price',title:'单价',width:100},
			{field:'stock',title:'剩余库存',width:100}
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

function addProductButton(title){

	xyzdialog({
		dialog : 'dialogFormDiv_addProduct',
		title : title,
	    href : '../jsp_product/addProduct.html',
	    fit:false,
	    height:300,
	    width:600,
	    buttons:[{
			text:'确定',
			handler:function(){
				addProductSubmit();
			}
		},{
			text:'取消',
			handler:function(){
				$("#dialogFormDiv_addProduct").dialog("destroy");
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