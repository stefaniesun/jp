$(document).ready(function() {
	
	initTable();
	
	$("#possessorQueryButton").click(function(){
		loadTable();
	});
	
});

function initTable(){
	var toolbar = [];
	if(xyzControlButton("buttonCode_s20150708143402")){
		toolbar[toolbar.length]={
				text: '新增资源组',
				iconCls: 'icon-add',
				handler: function(){var title=$(this).text();addPossessor(title);}
		};
		toolbar[toolbar.length]='-';
	}
	if(xyzControlButton("buttonCode_s20150708143403")){
		toolbar[toolbar.length]={
				text: '编辑资源组',
				iconCls: 'icon-edit',
				handler: function(){editPossessor();}
		};
		toolbar[toolbar.length]='-';
	}
	if(xyzControlButton("buttonCode_s20150708143404")){
		toolbar[toolbar.length]={
				text: '删除资源组',
				iconCls: 'icon-remove',
				handler: function(){deletePossessor();}
		};
		toolbar[toolbar.length]='-';
	}
	
	xyzgrid({
		table : 'possessorManagerTable',
		url:'../PossessorWS/queryPossessorList.do',
		toolbar : toolbar,
		title : '资源组列表',
		singleSelect : false,
		columns:[[
		    {field:'checkboxTemp',checkbox:true},
		    {field:'numberCode',title:'编号',hidden:true},
		    {field:'nameCn',title:'名称',width:'80'},
		    {field:'operTemp1',title:'渠道受限',
				formatter: function(value,row,index){
					var buttonTemp1 = "";
					var buttonTemp2 = "";
					if(xyzControlButton("buttonCode_s20150709091401")){
						var rowT = xyzIsNull(row.decideStr)?{}:eval("("+row.decideStr+")");
						if(rowT.channelFlag=="1"){
							buttonTemp1 =  "<a href='javascript:void(0);' onclick='setPossessorResourceManager(\""+row.numberCode+"\" ,\"Channel\")'>√设为不限</a>";
							buttonTemp2 =  "<a href='javascript:void(0);' onclick='possessorResourceManager(\""+row.numberCode+"\" ,\""+row.nameCn+"\" ,\"Channel\" ,\"渠道\")'>设置渠道</a>";
						}else{
							buttonTemp1 =  "<a href='javascript:void(0);' onclick='setPossessorResourceManager(\""+row.numberCode+"\" ,\"Channel\")'>×设为限制</a>";
						}
					}
					var temp3 = "";
					var decideStr = xyzIsNull(row.decideStr)?'{}':row.decideStr;
					var temp4 = JSON.parse(decideStr);
					if(!xyzIsNull(temp4.channels)){
						temp3 = temp4.channels.length;
					}
					return buttonTemp1 + "&nbsp;" +buttonTemp2+temp3;
				}
			},
			{field:'operTemp2',title:'预约商品受限',
				formatter: function(value,row,index){
					var buttonTemp1 = "";
					var buttonTemp2 = "";
					if(xyzControlButton("buttonCode_y20151021145601")){
						var rowT = xyzIsNull(row.decideStr)?{}:eval("("+row.decideStr+")");
						if(rowT.groupTitleFlag=="1"){
							buttonTemp1 =  "<a href='javascript:void(0);' onclick='setPossessorResourceManager(\""+row.numberCode+"\" ,\"GroupTitle\")'>√设为不限</a>";
							buttonTemp2 =  "<a href='javascript:void(0);' onclick='possessorResourceManager(\""+row.numberCode+"\" ,\""+row.nameCn+"\" ,\"GroupTitle\" ,\"预约商品\")'>设置预约商品</a>";
						}else{
							buttonTemp1 =  "<a href='javascript:void(0);' onclick='setPossessorResourceManager(\""+row.numberCode+"\" ,\"GroupTitle\")'>×设为限制</a>";
						}
					}
					var temp3 = "";
					var decideStr = xyzIsNull(row.decideStr)?'{}':row.decideStr;
					var temp4 = JSON.parse(decideStr);
					if(!xyzIsNull(temp4.groupTitles)){
						temp3 = temp4.groupTitles.length;
					}
					return buttonTemp1 + "&nbsp;" +buttonTemp2+temp3;
				}
			},
			{field:'operTemp3',title:'单品受限',
				formatter: function(value,row,index){
					var buttonTemp1 = "";
					var buttonTemp2 = "";
					if(xyzControlButton("buttonCode_y20151116151001")){
						var rowT = xyzIsNull(row.decideStr)?{}:eval("("+row.decideStr+")");
						if(rowT.orderTkviewFlag=="1"){
							buttonTemp1 =  "<a href='javascript:void(0);' onclick='setPossessorResourceManager(\""+row.numberCode+"\" ,\"OrderTkview\")'>√设为不限</a>";
							buttonTemp2 =  "<a href='javascript:void(0);' onclick='possessorResourceManager(\""+row.numberCode+"\" ,\""+row.nameCn+"\" ,\"OrderTkview\" ,\"单品\")'>设置单品</a>";
						}else{
							buttonTemp1 =  "<a href='javascript:void(0);' onclick='setPossessorResourceManager(\""+row.numberCode+"\" ,\"OrderTkview\")'>×设为限制</a>";
						}
					}
					var temp3 = "";
					var decideStr = xyzIsNull(row.decideStr)?'{}':row.decideStr;
					var temp4 = JSON.parse(decideStr);
					if(!xyzIsNull(temp4.orderTkviews)){
						temp3 = temp4.orderTkviews.length;
					}
					return buttonTemp1 + "&nbsp;" +buttonTemp2+temp3;
				}
			},
			{field:'remark',title:'备注'},
			{field:'alterDate',title:'修改时间'},
			{field:'addDate',title:'添加时间',hidden:true}
		]]
	});
}

function loadTable(){
	var numberCode = $("#numberCode").val();
	var nameCn = $("#nameCn").val();
	$("#possessorManagerTable").datagrid('load',{
		numberCode : numberCode,
		nameCn : nameCn
	});
}

function addPossessor(title){
	xyzdialog({
		dialog : 'dialogFormDiv_addPossessor',
		title : title,
	    href : '../jsp_base/addPossessor.html',
	    buttons:[{
			text:'确定',
			handler:function(){
				addPossessorSubmit();
			}
		},{
			text:'取消',
			handler:function(){
				$("#dialogFormDiv_addPossessor").dialog("destroy");
			}
		}]
	});
}

function editPossessor(){
	var possessors = $("#possessorManagerTable").datagrid("getChecked");
	if(possessors.length != 1){
		top.$.messager.alert("提示","请先选中单个对象！","info");
		return;
	}
	var row = possessors[0];
	var title = $(this).text();
	xyzdialog({
		dialog : 'dialogFormDiv_editPossessor',
		title : title,
	    href : '../jsp_base/editPossessor.html',
	    buttons:[{
			text:'确定',
			handler:function(){
				editPossessorSubmit();
			}
		},{
			text:'取消',
			handler:function(){
				$("#dialogFormDiv_editPossessor").dialog("destroy");
			}
		}],
		onLoad : function(){
			$("#numberCodeForm").val(row.numberCode);
			$("#nameCnForm").val(row.nameCn);
			$("#remarkForm").val(row.remark);
		}
	});
}

function deletePossessor(){
	var possessors = $.map($("#possessorManagerTable").datagrid("getChecked"),function(p){return p.numberCode;}).join(",");
	if(xyzIsNull(possessors)){
		top.$.messager.alert("提示","请先选中需要删除的对象！","info");
		return;
	}
	if(!confirm("删除对象，确定？")){
		return;
	}
	$.ajax({
		url : "../PossessorWS/deletePossessor.do",
		type : "POST",
		data : {
			numberCodes : possessors
		},
		async : false,
		dataType : "json",
		success : function(data) {
			if(data.status==1){
				$("#possessorManagerTable").datagrid("reload");
				top.$.messager.alert("提示","操作成功！","info");
			}else{
				top.$.messager.alert("警告",data.msg,"warning");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			top.window.AjaxError(XMLHttpRequest, textStatus, errorThrown);
		}
	});
}

function editPossessorSubmit(){
	if(!$("form").form('validate')){
		return false;
	}
	
	var numberCode = $("#numberCodeForm").val();
	var nameCn = $("#nameCnForm").val();
	var remark = $("#remarkForm").val();
	$.ajax({
		url : "../PossessorWS/editPossessor.do",
		type : "POST",
		data : {
			numberCode : numberCode,
			nameCn : nameCn,
			remark : remark
		},
		async : false,
		dataType : "json",
		success : function(data) {
			if(data.status==1){
				$("#possessorManagerTable").datagrid("reload");
				top.$.messager.alert("提示","操作成功！","info");
				$("#dialogFormDiv_editPossessor").dialog("destroy");
			}else{
				top.$.messager.alert("警告",data.msg,"warning");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			top.window.AjaxError(XMLHttpRequest, textStatus, errorThrown);
		}
	});
}

function addPossessorSubmit(){
	
	if(!$("form").form('validate')){
		return false;
	}
	
	var nameCn = $("#nameCnForm").val();
	var remark = $("#remarkForm").val();
	
	$.ajax({
		url : "../PossessorWS/addPossessor.do",
		type : "POST",
		data : {
			nameCn : nameCn,
			remark : remark
		},
		async : false,
		dataType : "json",
		success : function(data) {
			if(data.status==1){
				$("#possessorManagerTable").datagrid("reload");
				top.$.messager.alert("提示","操作成功！","info");
				$("#dialogFormDiv_addPossessor").dialog("destroy");
			}else{
				top.$.messager.alert("警告",data.msg,"warning");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			top.window.AjaxError(XMLHttpRequest, textStatus, errorThrown);
		}
	});
}

function possessorResourceManager(numberCode ,nameCn ,resourceType ,resourceTypeCn){
	xyzdialog({
		dialog : 'dialogFormDiv_possessorResourceManager',
		title : "管理"+resourceTypeCn+"["+nameCn+"]["+numberCode+"]",
	    content : "<iframe id='dialogFormDiv_possessorResourceManagerIframe' frameborder='0' name='"+(numberCode+","+resourceType)+"'></iframe>",
	    buttons:[{
			text:'返回',
			handler:function(){
				$("#dialogFormDiv_possessorResourceManager").dialog("destroy");
			}
		}]
	});
	var tempWidth = $("#dialogFormDiv_possessorResourceManager").css("width");
	var tempHeight = $("#dialogFormDiv_possessorResourceManager").css("height");
	var tempWidth2 = parseInt(tempWidth.split("px")[0]);
	var tempHeight2 = parseInt(tempHeight.split("px")[0]);
	$("#dialogFormDiv_possessorResourceManagerIframe").css("width",(tempWidth2-20)+"px");
	$("#dialogFormDiv_possessorResourceManagerIframe").css("height",(tempHeight2-50)+"px");
	$("#dialogFormDiv_possessorResourceManagerIframe").attr("src","../jsp_base/possessorResourceManager.html");
}

function setPossessorResourceManager(numberCode ,resourceType){
	$.ajax({
		url : "../PossessorWS/setPossessorResourceFlag.do",
		type : "POST",
		data : {
			numberCode : numberCode,
			resourceType : resourceType
		},
		async : false,
		dataType : "json",
		success : function(data) {
			if(data.status==1){
				$("#possessorManagerTable").datagrid("reload");
				top.$.messager.alert("提示","操作成功！","info");
			}else{
				top.$.messager.alert("警告",data.msg,"warning");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			top.window.AjaxError(XMLHttpRequest, textStatus, errorThrown);
		}
	});
	
}
