$(document).ready(function() {
	
	$("#orderButton").click(function(){
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
		table : 'orderManagerTable',
		title : '产品列表',
		url:'../BaseOrderWS/queryOrderList.do',
		pageList : [5,10,15,30,50],
		pageSize : 5,
		toolbar:toolbar,
		singleSelect : false,
		idField : 'numberCode',
		height:'auto',
		columns : [[
            {field:'checkboxTemp',checkbox:true},
			{field:'orderNum',title:'产品编号'},
			{field:'productName',title:'产品名称',width:200},
			{field:'price',title:'单价',width:100},
			{field:'count',title:'数量',width:100},
			{field:'username',title:'买家',width:100},
			{field:'addDate',title:'下单时间',width:100},
			{field:'status',title:'订单状态',width:100,
				formatter:function(val,rec){
					if(val=="0"){
						return "已取消";
					}else if(val=="1"){
						return "未付款";
					}else if(val=="2"){
						return "已付款";
					}else if(val=="3"){
						return "已发货";
					}else if(val=="4"){
						return "已确认";
					}
				}
			}
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
	    height:700,
	    width:1000,
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
	    href : '../jsp_product/editProduct.html',
	    fit:false,
	    height:700,
	    width:1000,
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
		onOpen:function(){
			xyzAjax({
				url:"../ProductWS/getProduct.do",
				data:{
					numberCode:row.numberCode
				},
				success:function(data){
					if(data.status==1){
						$("#nameForm").val(data.content.name);
						$("#specialForm").val(data.content.special);
						$("#typeForm").val(data.content.type);
						$("#priceForm").val(data.content.price);
						$("#basePriceForm").val(data.content.basePrice);
						$("#stockForm").val(data.content.stock);
						UM.getEditor('editor').setContent(data.content.content, true);
					
						var images=eval("{"+data.content.images+"}"); 
						var html="";
						for(var i=0;i<images.length;i++){
							html+='<span id="'+images[i].numberCode+'"><img width="100px" height="100px"  src="../upload/productImage/'+images[i].url+'"></img><a style="cursor:pointer">删除</a></span>';
						}
						$("#imageList").html(html);
						$("#imageList a").click(function(){
							$(this).parent("span").css("display","none");
						});
						
					}else{
						top.$.messager.alert("警告",data.msg,"warning");
					}
				}
			});
			
		
		}
	});
}

function addProductSubmit(){
	var name=$("#nameForm").val();
	var special=$("#specialForm").val();
	var type=$("#typeForm").val();
	var price=$("#priceForm").val();
	var basePrice=$("#basePriceForm").val();
	var stock=$("#stockForm").val();
	var content=UM.getEditor('editor').getContent();
	var images="";
	$(".filelist li[class='state-complete']").each(function(){
		images+=$(this).attr("code")+",";
	});
	
	if(!$("form").form('validate')){
		return;
	}
	xyzAjax({
		url:"../ProductWS/addProduct.do",
		data:{
			name:name,
			special:special,
			type:type,
			price:price,
			basePrice:basePrice,
			stock:stock,
			content:content,
			images:images
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
	var name=$("#nameForm").val();
	var special=$("#specialForm").val();
	var type=$("#typeForm").val();
	var price=$("#priceForm").val();
	var basePrice=$("#basePriceForm").val();
	var stock=$("#stockForm").val();
	var image=$("#imageForm").val();
	var content=UM.getEditor('editor').getContent();
	var images="";
	$(".filelist li[class='state-complete']").each(function(){
		images+=$(this).attr("code")+",";
	});
	var deleteImages="";
	$("#imageList span").each(function(){
		if($(this).css("display")=="none"){
			deleteImages+=$(this).attr("id")+",";
		}
	});

	xyzAjax({
		url:"../ProductWS/editProduct.do",
		data:{
			numberCode:numberCode,
			name:name,
			special:special,
			type:type,
			price:price,
			basePrice:basePrice,
			stock:stock,
			image:image,
			content:content,
			images:images,
			deleteImages:deleteImages
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
	var product = $.map($("#productManagerTable").datagrid("getChecked"),function(p){return p.numberCode;}).join(",");
	if(product == null || product == ''){
		top.$.messager.alert("提示","请先选中需要删除的对象！","info");
		return;
	}
	if(!confirm("彻底删除对象，确定？")){
		return;
	}
	
	xyzAjax({
		url : '../ProductWS/deleteProduct.do',
		data : {
			numberCode : product
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