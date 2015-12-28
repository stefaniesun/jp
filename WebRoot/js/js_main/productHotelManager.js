$(document).ready(function() {
	$("#productButton").click(function(){
		loadTable();
	});
	
	xyzCombobox({
		combobox : 'providerLoadForm',
		url : '../ListWS/getProviderList.do',
		onBeforeLoad: function(param){
			param.providerType = "HO";
		}
	});
	
	initTable();
});

function initTable(){
	
	var toolbar = [];
	if(xyzControlButton('buttonCode_h20151215093701')){
		toolbar[toolbar.length]={
				text: '新增产品',
				border:'1px solid #bbb',
				iconCls: 'icon-add',
				handler: function(){var title=$(this).text();addProductHotelButton(title);}
		};
		toolbar[toolbar.length]='-';
	}
	if(xyzControlButton('buttonCode_h20151215093702')){
		toolbar[toolbar.length]={
				text: '编辑产品',
				border:'1px solid #bbb',
				iconCls: 'icon-edit',
				handler: function(){var title=$(this).text();editProductHotelButton(title);}
		};
		toolbar[toolbar.length]='-';
	}
	if(xyzControlButton('buttonCode_h20151215093703')){
		toolbar[toolbar.length]={
				text: '删除产品',
				border:'1px solid #bbb',
				iconCls: 'icon-remove',
				handler: function(){deleteProductHotelButton();}
		};
		toolbar[toolbar.length]='-';
	}	
	
	xyzgrid({
		table : 'productHotelManagerTable',
		title : '产品列表',
		url:'../ProductHotelWS/queryProductHotelList.do',
		pageList : [5,10,15,30,50],
		pageSize : 5,
		toolbar:toolbar,
		singleSelect : false,
		idField : 'numberCode',
		height:'auto',
		columns : [[
		    {field:'checkboxTemp',checkbox:true},
			{field:'numberCode',title:'产品编号',hidden:true},
			{field:'nameCn',title:'产品名称',width:100},
		]]
	});
	
}

function loadTable(){
	var nameCn = $("#nameCn").val();
	var provider= $("#providerLoadForm").combobox("getValue");
	$("#productHotelManagerTable").datagrid("load",{
		nameCn : nameCn,
		provider:provider
	});
}

function addProductHotelButton(title){
	xyzdialog({
		dialog : 'dialogFormDiv_addProductHotel',
		title : title,
	    href : '../jsp_main/addProductHotel.html',
	    fit:false,
	    height:300,
	    width:600,
	    buttons:[{
			text:'确定',
			handler:function(){
				addProductHotelSubmit();
			}
		},{
			text:'取消',
			handler:function(){
				$("#dialogFormDiv_addProductHotel").dialog("destroy");
			}
		}],onLoad:function(){
			xyzCombobox({
				combobox : 'providerForm',
				url : '../ListWS/getProviderList.do',
				required:true,
				onBeforeLoad: function(param){
					param.providerType = "HO";
				}
			});
		}
	});
	
}

function editProductHotelButton(title){
	
	var product = $("#productHotelManagerTable").datagrid("getChecked");
	if(product.length != 1){
		top.$.messager.alert("提示","请先选中单个对象！","info");
		return;
	}
	var row = product[0];
	
	xyzdialog({
		dialog : 'dialogFormDiv_editProductHotel',
		title : title,
	    href : '../jsp_main/editProductHotel.html',
	    fit:false,
	    height:300,
	    width:600,
	    buttons:[{
			text:'确定',
			handler:function(){
				editProductHotelSubmit(row.numberCode);
			}
		},{
			text:'取消',
			handler:function(){
				$("#dialogFormDiv_editProductHotel").dialog("destroy");
			}
		}],
		onLoad:function(){
			xyzCombobox({
				combobox : 'providerForm',
				url : '../ListWS/getProviderList.do',
				required:true,
				lazy:false,
				onBeforeLoad: function(param){
					param.providerType = "HO";
				}
			});
			$("#nameCnForm").val(row.nameCn);
			$("#providerForm").combobox("setValue",row.provider);
		}
	});
}

function addProductHotelSubmit(){
	var nameCn=$("#nameCnForm").val();
	var provider=$("#providerForm").combobox("getValue");
	if(!$("form").form('validate')){
		return;
	}
	xyzAjax({
		url:"../ProductHotelWS/addProductHotel.do",
		data:{
			nameCn:nameCn,
			provider:provider
		},
		success:function(data){
			if(data.status==1){
				top.$.messager.alert("提示","操作成功","info");
				$("#dialogFormDiv_addProductHotel").dialog("destroy");
				$("#productHotelManagerTable").datagrid("reload");
			}else{
				top.$.messager.alert("警告",data.msg,"warning");
			}
		}
	});
}

function editProductHotelSubmit(numberCode){
	var nameCn=$("#nameCnForm").val();
	var provider=$("#providerForm").combobox("getValue");
	if(!$("form").form('validate')){
		return;
	}
	xyzAjax({
		url:"../ProductHotelWS/editProductHotel.do",
		data:{
			numberCode:numberCode,
			nameCn:nameCn,
			provider:provider
		},
		success:function(data){
			if(data.status==1){
				top.$.messager.alert("提示","操作成功","info");
				$("#dialogFormDiv_editProductHotel").dialog("destroy");
				$("#productHotelManagerTable").datagrid("reload");
			}else{
				top.$.messager.alert("警告",data.msg,"warning");
			}
		}
	});
	
}

function deleteProductHotelButton(){
	var products = $.map($("#productHotelManagerTable").datagrid("getChecked"),function(p){return p.numberCode;}).join(",");
	if(products == null || products == ''){
		top.$.messager.alert("提示","请先选中需要删除的对象！","info");
		return;
	}
	if(!confirm("彻底删除对象，确定？")){
		return;
	}
	
	xyzAjax({
		url : '../ProductHotelWS/deleteProductHotel.do',
		data : {
			numberCodes : products
		},
		success : function(data) {
			if(data.status==1){
				top.$.messager.alert("提示","操作成功","info");
				$("#productHotelManagerTable").datagrid("reload");
			}else{
				top.$.messager.alert("警告",data.msg,"warning");
			}
		}
	});
}