$(document).ready(function() {
	
	currentIframe= $("#dialogFormDiv_possessorResourceManagerIframe",window.parent.document).attr("name");
	
	possessor = currentIframe.split(",")[0];
	resourceType = currentIframe.split(",")[1];
	
	$("#possessorResource_possessor").val(currentIframe.split(",")[0]);
	$("#possessorResource_resourceType").val(currentIframe.split(",")[1]);
	
	initPossessorResourceList();
	
	updatePossessorResource();
	
	initTable();
});

function initTable(){
	
	var resourceTypeCn = "";
	var trueListUrl = "";
	var falseListUrl = "";
	if(resourceType == "Channel"){
		resourceTypeCn = "渠道";
		trueListUrl = "../ChannelWS/queryPossessorChannelTrueList.do";
		falseListUrl = "../ChannelWS/queryPossessorChannelFalseList.do";
	}else if(resourceType == "GroupTitle"){
		resourceTypeCn = "预约商品";
		trueListUrl = "../GroupTitleWS/queryPossessorGroupTitleTrueList.do";
		falseListUrl = "../GroupTitleWS/queryPossessorGroupTitleFalseList.do";
	}else if(resourceType == "OrderTkview"){
		resourceTypeCn = "单品";
		trueListUrl = "../OrderTkviewWS/queryPossessorOrderTkviewTrueList.do";
		falseListUrl = "../OrderTkviewWS/queryPossessorOrderTkviewFalseList.do";
	}else{
		return;
	}
	
	var nameCn = $("#nameCn").val();
	var numberCode = $("#numberCode").val();
	
	var toolbarTrue = [];
	
	toolbarTrue[toolbarTrue.length] = {
			iconCls: 'icon-search',
			text : '查询已选中'+resourceTypeCn,
			handler: function(){
				possessorResourceQueryTrue();
		}
	};
	
	toolbarTrue[toolbarTrue.length] = '-';
	
	toolbarTrue[toolbarTrue.length] = {
			iconCls: 'icon-remove',
			text : '删除资源组的'+resourceTypeCn,
			handler: function(){deletePossessorResource();}
		};
	
	var toolbarFalse = [];
	
	toolbarFalse[toolbarFalse.length] = {
		iconCls: 'icon-search',
		text : '查询未选中'+resourceTypeCn,
		handler: function(){
			possessorResourceQueryFalse();
		}
	};
	
	toolbarFalse[toolbarFalse.length] = '-';
	
	toolbarFalse[toolbarFalse.length] = {
			iconCls: 'icon-add',
			text : '给资源组添加'+resourceTypeCn,
			handler: function(){addPossessorResource();}
		};
	
	var parameter = xyzIsNull(possessorResourceList)?"":possessorResourceList.join(',');
	
	xyzgrid({
		table : 'possessorResourceTrueTable',
		title : '已选中的'+resourceTypeCn,
		url:trueListUrl,
		pageList : [5,10,15,30,50],
		pageSize : 5,
		toolbar : toolbarTrue,
		singleSelect : false,
		idField : 'numberCode',
		height:'auto',
		columns:[[
		    {field:'checkboxTemp',checkbox:true},
		    {field:'numberCode',title:'编号'},
		    {field:'nameCn',title:'名称'}
		]],
		queryParams : {
			inPossessorResourceNumberCodes : parameter,
			nameCn : nameCn,
			numberCode : numberCode
		}
	});
	
	xyzgrid({
		table : 'possessorResourceFalseTable',
		title : '未选中的'+resourceTypeCn,
		url:falseListUrl,
		pageList : [5,10,15,30,50],
		pageSize : 5,
		toolbar : toolbarFalse,
		singleSelect : false,
		idField : 'numberCode',
		height:'auto',
		columns:[[
		    {field:'checkboxTemp',checkbox:true},
		    {field:'numberCode',title:'编号'},
		    {field:'nameCn',title:'名称'}
		]],
		queryParams : {
			inPossessorResourceNumberCodes : parameter,
			nameCn : nameCn,
			numberCode : numberCode
		}
	});
}

function possessorResourceQueryTrue(){
	var numberCode = $("#numberCode").val();
	var nameCn = $("#nameCn").val();
	
	var parameter = xyzIsNull(possessorResourceList)?"":possessorResourceList.join(',');
	
	$("#possessorResourceTrueTable").datagrid('load',{
		inPossessorResourceNumberCodes : parameter,
		nameCn : nameCn,
		numberCode : numberCode
	});
}

function possessorResourceQueryFalse(){
	var numberCode = $("#numberCode").val();
	var nameCn = $("#nameCn").val();
	
	var parameter = xyzIsNull(possessorResourceList)?"":possessorResourceList.join(',');
	
	$("#possessorResourceFalseTable").datagrid('load',{
		inPossessorResourceNumberCodes : parameter,
		nameCn : nameCn,
		numberCode : numberCode
	});
}

function deletePossessorResource(){
	var possessorResources = $.map($("#possessorResourceTrueTable").datagrid("getChecked"),function(p){return p.numberCode;}).join(",");
	if(possessorResources == null || possessorResources == ''){
		top.$.messager.alert("提示","请先选中需要操作的对象！","info");
		return;
	}
	var tempChannelNameCns = $.map($("#possessorResourceTrueTable").datagrid("getChecked"),function(p){return p.nameCn;}).join(",");
	
	var numberCodeList = possessorResources.split(',');
	var nameCnList = tempChannelNameCns.split(',');
	
	var pppList = [];
	for(var k=0;k<possessorResourceList.length;k++){
		var flag = false;
		for(var i=0;i<numberCodeList.length;i++){
			if(possessorResourceList[k]==numberCodeList[i]){
				flag = true;
				break;
			}
		}
		if(flag==false){
			pppList[pppList.length] = possessorResourceList[k];
		}
	}
	possessorResourceList = pppList;
	
	var tttList = [];
	for(var k=0;k<channelNameCns.length;k++){
		var flag = false;
		for(var i=0;i<nameCnList.length;i++){
			if(channelNameCns[k]==nameCnList[i]){
				flag = true;
				break;
			}
		}
		if(flag==false){
			tttList[tttList.length] = channelNameCns[k];
		}
	}
	channelNameCns = tttList;
	
	var deletePossessorResourceUrl = "";
	if(resourceType == "Channel"){
		deletePossessorResourceUrl = "../PossessorWS/deletePossessorChannel.do";
	}else if(resourceType == "GroupTitle"){
		deletePossessorResourceUrl = "../PossessorWS/deletePossessorGroupTitle.do";
	}else if(resourceType == "OrderTkview"){
		deletePossessorResourceUrl = "../PossessorWS/deletePossessorOrderTkview.do";
	}else{
		return;
	}
	
	var parameter = xyzIsNull(possessorResourceList)?"":possessorResourceList.join(',');
	var nameCnParameter = xyzIsNull(channelNameCns)?"":channelNameCns.join(',');
	
	$.ajax({
		url : deletePossessorResourceUrl,
		type : "POST",
		data : {
			possessor : possessor,
			possessorResource : parameter,
			channelNameCns :nameCnParameter
		},
		async : false,
		dataType : "json",
		success : function(data) {
			if(data.status==1){
				initPossessorResourceList();
				possessorResourceQueryTrue();
				possessorResourceQueryFalse();
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

function addPossessorResource(){
	var possessorResources = $.map($("#possessorResourceFalseTable").datagrid("getChecked"),function(p){return p.numberCode;}).join(",");
	if(possessorResources == null || possessorResources == ''){
		top.$.messager.alert("提示","请先选中需要操作的对象！","info");
		return;
	}
	
	var tempChannelNameCns = $.map($("#possessorResourceFalseTable").datagrid("getChecked"),function(p){return p.nameCn;}).join(",");
	
	var numberCodeList = possessorResources.split(',');
	var nameCnList = tempChannelNameCns.split(',');
	
	var pppList = possessorResourceList;
	for(var i=0;i<numberCodeList.length;i++){
		var flag = false;
		for(var k=0;k<possessorResourceList.length;k++){
			if(possessorResourceList[k]==numberCodeList[i]){
				flag = true;
				break;
			}
		}
		if(flag==false){
			pppList[pppList.length] = numberCodeList[i];
		}
	}
	possessorResourceList = pppList;
	
	var tttList = channelNameCns;
	for(var i=0;i<nameCnList.length;i++){
		var flag = false;
		for(var k=0;k<channelNameCns.length;k++){
			if(channelNameCns[k]==nameCnList[i]){
				flag = true;
				break;
			}
		}
		if(flag==false){
			tttList[tttList.length] = nameCnList[i];
		}
	}
	channelNameCns = tttList;
	
	var addPossessorResourceUrl = "";
	if(resourceType == "Channel"){
		addPossessorResourceUrl = "../PossessorWS/addPossessorChannel.do";
	}else if(resourceType == "GroupTitle"){
		addPossessorResourceUrl = "../PossessorWS/addPossessorGroupTitle.do";
	}else if(resourceType == "OrderTkview"){
		addPossessorResourceUrl = "../PossessorWS/addPossessorOrderTkview.do";
	}else{
		return;
	}
	
	var parameter = xyzIsNull(possessorResourceList)?"":possessorResourceList.join(',');
	var nameCnParameter = xyzIsNull(channelNameCns)?"":channelNameCns.join(',');
	$.ajax({
		url : addPossessorResourceUrl,
		type : "POST",
		data : {
			possessor : possessor,
			possessorResource : parameter,
			channelNameCns :nameCnParameter
		},
		async : false,
		dataType : "json",
		success : function(data) {
			if(data.status==1){
				initPossessorResourceList();
				possessorResourceQueryTrue();
				possessorResourceQueryFalse();
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

function initPossessorResourceList(){
	possessorResourceList = [];
	channelNameCns = [];
	$.ajax({
		url : "../PossessorWS/getPossessor.do",
		type : "POST",
		data : {
			possessor : possessor,
		},
		async : false,
		dataType : "json",
		success : function(data) {
			if(data.status==1){
				
				var possessorObj = data.content;
				if(!xyzIsNull(possessorObj)){
					var decideStrTemp = xyzJsonToObject(possessorObj.decideStr);
					if("Channel" == resourceType){
						possessorResourceList = !xyzIsNull(decideStrTemp.channels)?decideStrTemp.channels:[];
						channelNameCns = !xyzIsNull(decideStrTemp.channelNameCns)?decideStrTemp.channelNameCns:[];
					}else if("GroupTitle" == resourceType){
						possessorResourceList = !xyzIsNull(decideStrTemp.groupTitles)?decideStrTemp.groupTitles:[];
					}else if("OrderTkview" == resourceType){
						possessorResourceList = !xyzIsNull(decideStrTemp.orderTkviews)?decideStrTemp.orderTkviews:[];
					}
				}
			}else{
				top.$.messager.alert("警告",data.msg,"warning");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			top.window.AjaxError(XMLHttpRequest, textStatus, errorThrown);
		}
	});
}

function updatePossessorResource(){
	//如果本身为空, 则不操作
	if(xyzIsNull(possessorResourceList)){
		possessorResourceList = [];
		return;
	}
	
	var baseResourcesUrl = "";
	if(resourceType == "Channel"){
		baseResourcesUrl = "../ChannelWS/queryPossessorChannelTrueList.do";
	}else if(resourceType == "GroupTitle"){
		baseResourcesUrl = "../GroupTitleWS/queryPossessorGroupTitleTrueList.do";
	}else if(resourceType == "OrderTkview"){
		baseResourcesUrl = "../OrderTkviewWS/queryPossessorOrderTkviewTrueList.do";
	}else{
		return;
	}
	
	var baseResources = [];
	
	$.ajax({
		url : baseResourcesUrl,
		type : "POST",
		data : {
			page : 1,
			rows : 9999999,
			inPossessorResourceNumberCodes : xyzIsNull(possessorResourceList)?"":possessorResourceList.join(',')
		},
		async : false,
		dataType : "json",
		success : function(data) {
			if(data.status==1){
				baseResources = data.content.rows;
			}
		}
	});
	
	if(possessorResourceList.length==baseResources.length){
		return;
	}
	
	possessorResourceList = [];
	channelNameCns = [];
	
	for(var i=0;i<baseResources.length;i++){
		possessorResourceList[possessorResourceList.length] = baseResources[i].numberCode;
		if(resourceType == "Channel"){
			channelNameCns[channelNameCns.length] = baseResources[i].nameCn;
		}
	}
	
	var setPossessorResourceUrl = "";
	if(resourceType == "Channel"){
		setPossessorResourceUrl = "../PossessorWS/addPossessorChannel.do";
	}else if(resourceType == "GroupTitle"){
		setPossessorResourceUrl = "../PossessorWS/addPossessorGroupTitle.do";
	}else if(resourceType == "OrderTkview"){
		setPossessorResourceUrl = "../PossessorWS/addPossessorOrderTkview.do";
	}else{
		return;
	}
	
	$.ajax({
		url : setPossessorResourceUrl,
		type : "POST",
		data : {
			possessor : possessor,
			possessorResource : possessorResourceList.join(','),
			channelNameCns : channelNameCns.join(',')
		},
		async : false,
		dataType : "json",
		success : function(data) {
			;
		}
	});
}
