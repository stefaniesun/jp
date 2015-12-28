$(document).ready(function() {
	
	
	$("#orderContentButton").click(function(){
		loadTable();
	});
	
	initTable();
	
});

function initTable(){
	var toolbar = [];
	if(xyzControlButton("buttonCode_w20151221155601")){
		toolbar[toolbar.length]={
				text: '出票',
				iconCls: 'icon-edit',
				handler: function(){updateOrderContentForFlagClient();}
		};
		toolbar[toolbar.length]='-';
	}
	if(xyzControlButton("buttonCode_w20151221155602")){
		toolbar[toolbar.length]={
				text: '退票',
				iconCls: 'icon-edit',
				handler: function(){updateOrderContentForFlagRefund();}
		};
	}
	
	xyzgrid({
		table : 'orderContentManagerTable',
		title : '订单列表',
		url:'../OrderContentWS/queryOrderContentList.do',
		pageList : [5,10,15,30,50],
		pageSize : 5,
		toolbar : toolbar,
		singleSelect : false,
		idField : 'iidd',
		height:'auto',
		frozenColumns : [[
		    {field:'checkboxTemp',checkbox:true},
			{field:'orderNum',title:'订单编号'},
			{field:'clientCode',title:'客户码',width:100,hidden:true},
			{field:'provider',title:'供应商编号',width:100,hidden:true},
			{field:'product',title:'产品编号',width:100,hidden:true},
			{field:'flagPay',title:'支付',width:30,
				formatter : function(value,row,index){
					if(value == 1){
						return "√";
					}else {
						return "×";
					}
				},
				styler : function(value,row,index){
					if(value == 1){
						return "background-color:green";
					}else {
						return "background-color:red";
					}
				}
			},
			{field:'flagClient',title:'出票',width:30,
				formatter : function(value,row,index){
					if(value == 1){
						return "√";
					}else {
						return "×";
					}
				},
				styler : function(value,row,index){
					if(value == 1){
						return "background-color:green";
					}else {
						return "background-color:red";
					}
				}
			},
			{field:'flagUse',title:'使用',width:30,
				formatter : function(value,row,index){
					if(value == 1){
						return "√";
					}else {
						return "×";
					}
				},
				styler : function(value,row,index){
					if(value == 1){
						return "background-color:green";
					}else {
						return "background-color:red";
					}
				}
			},
			{field:'flagApply',title:'申请退票',width:60,
				formatter : function(value,row,index){
					if(value == 1){
						return "√";
					}else {
						return "×";
					}
				},
				styler : function(value,row,index){
					if(value == 1){
						return "background-color:red";
					}else {
						return "background-color:green";
					}
				}
			},
			{field:'flagRefund',title:'退票',width:30,
				formatter : function(value,row,index){
					if(value == 1){
						return "√";
					}else {
						return "×";
					}
				},
				styler : function(value,row,index){
					if(value == 1){
						return "background-color:red";
					}else {
						return "background-color:green";
					}
				}
			},
			{field:'flagOver',title:'冻结',width:30,
				formatter : function(value,row,index){
					if(value == 1){
						return "√";
					}else {
						return "×";
					}
				},
				styler : function(value,row,index){
					if(value == 1){
						return "background-color:green";
					}else {
						return "background-color:red";
					}
				}
			}]],
			columns:[[
			{field:'productNameCn',title:'产品名称',width:100},
			{field:'providerNameCn',title:'供应商名称',width:100},
			{field:'buyer',title:'买家',width:100},
			{field:'price',title:'单价',width:100},
			{field:'count',title:'数量',width:100},
			{field:'money',title:'总价',width:100},
			{field:'addDate',title:'录单日期',width:100},
			{field:'dateInfo',title:'出行日期',width:100},
			{field:'payDate',title:'支付时间',width:100},
			{field:'clientDate',title:'出票时间',width:100},
			{field:'useDate',title:'使用时间',width:100},
			{field:'applyDate',title:'申请退票时间',width:100},
			{field:'refundDate',title:'退票时间',width:100},
			{field:'alterDate',title:'最后修改时间',width:100},
			{field:'overDate',title:'冻结时间',width:100},
			{field:'cleanDate',title:'清洗时间',width:100},
		]],
		queryParams : {
			flagStr : $("#flagStr").combobox("getValue"),
		}
	});
	
}

function loadTable(){
	var orderNum = $("#orderNum").val();
	var flagStr=$("#flagStr").combobox("getValues").join(",");
	var buyer=$("#buyer").val();
	var dateStr=$("#dateStr").combobox("getValue");
	var dateStart=$("#dateStart").datebox("getValue");
	var dateEnd=$("#dateEnd").datebox("getValue");
	var remarkStr=$("#remarkStr").combobox("getValue");
	var remark=$("#remark").val();
	var productNameCn=$("#productNameCn").val();
	var providerNameCn=$("#providerNameCn").val();
	$("#orderContentManagerTable").datagrid("load",{
		orderNum : orderNum,
		flagStr:flagStr,
		buyer:buyer,
		dateStr:dateStr,
		dateStart:dateStart,
		dateEnd:dateEnd,
		remarkStr:remarkStr,
		remark:remark,
		productNameCn:productNameCn,
		providerNameCn:providerNameCn,
	});
}

function updateOrderContentForFlagClient(){
	var clientCode = $.map($("#orderContentManagerTable").datagrid("getChecked"),function(p){return p.clientCode;}).join(",");
	xyzAjax({
		url:"../OrderContentWS/updateOrderContentForFlagClient.do",
		data:{
			orderContents:clientCode
		},
		success:function(data){
			if(data.status==1){
				top.$.messager.alert("提示","操作成功","info");
				$("#orderContentManagerTable").datagrid("reload");
			}else{
				top.$.messager.alert("警告",data.msg,"warning");
			}
		}
	});
}

function updateOrderContentForFlagRefund (){
	var clientCode = $.map($("#orderContentManagerTable").datagrid("getChecked"),function(p){return p.clientCode;}).join(",");
	xyzAjax({
		url:"../OrderContentWS/updateOrderContentForFlagRefund.do",
		data:{
			orderContents:clientCode
		},
		success:function(data){
			if(data.status==1){
				top.$.messager.alert("提示","操作成功","info");
				$("#orderContentManagerTable").datagrid("reload");
			}else{
				top.$.messager.alert("警告",data.msg,"warning");
			}
		}
	});
}