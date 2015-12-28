function xyzgrid(d){
	$("#"+d.table).datagrid({
		pageNumber : d.pageNumber==undefined?1:d.pageNumber,
		pageSize : d.pageSize==undefined?10:d.pageSize,
		title : d.title==undefined?'数据表':d.title,
		collapsible : d.collapsible==undefined?true:d.collapsible,//默认有折叠按钮
		collapsed : d.collapsed==undefined?false:d.collapsed,//定义初始化的时候是否折叠
		url : d.url==undefined?undefined:d.url,
		data : d.data==undefined?undefined:d.data,
		toolbar : d.toolbar==undefined?'':$.isArray(d.toolbar)?d.toolbar:"#"+d.toolbar,
		loadFilter : d.loadFilter==undefined?function(data){
				if(data.status==1){
					return data.content;
				}else{
					top.$.messager.alert("警告",data.msg,"warning");
					return {total : 0 , rows : []};
				}
			}:d.loadFilter,
		nowrap : d.nowrap==undefined?true:d.nowrap,//是否不换行，true不换行
		border : d.border==undefined?true:d.border,//边框
		height : d.height==undefined?xyzGetHeight($("#"+d.table).position().top+42):d.height,//高度
		singleSelect : d.singleSelect==undefined?true:d.singleSelect,//单选
		fitColumns : d.fitColumns==undefined?false:d.fitColumns,//自适应
		striped : d.striped==undefined?false:d.striped,//斑马线
		pagination : d.pagination==undefined?true:d.pagination,//分页
		pagePosition : d.pagePosition==undefined?"top":d.pagePosition,//分页条文职
		rownumbers : d.rownumbers==undefined?true:d.rownumbers,//行号
		checkOnSelect : d.checkOnSelect==undefined?true:d.checkOnSelect,//点行选框
		selectOnCheck : d.selectOnCheck==undefined?true:d.selectOnCheck,//点框选行
		pageList : d.pageList==undefined?[5,10,15,30,50]:d.pageList,//分页
		idField : d.idField==undefined?'numberCode':d.idField,
		columns : d.columns,
		frozenColumns : d.frozenColumns==undefined?undefined:d.frozenColumns,//冻结列
		queryParams : d.queryParams==undefined?undefined:d.queryParams,//查询条件
		onCheck : d.onCheck==undefined?undefined:d.onCheck,
		tools:[{
			iconCls:'icon-add',
			handler:function(e){
				e.preventDefault();
				var tableId = d.table;
				if($('#columnMenuAdd_'+tableId).attr("id")){
					$('#columnMenuAdd_'+tableId).menu("destroy");
				}
				$('body').append('<div id="columnMenuAdd_'+tableId+'"/>');
				var cmenu = $('#columnMenuAdd_'+tableId);
				cmenu.menu({
					onClick: function(item){
						$('#'+tableId).datagrid('showColumn', item.name);
						cmenu.menu("destroy");
					}
				});
				var fields = $('#'+tableId).datagrid('getColumnFields');
				for(var i=1; i<fields.length; i++){
					var field = fields[i];
					var col = $('#'+tableId).datagrid('getColumnOption', field);
					if(col.hidden==true){
						cmenu.menu('appendItem', {
							text: col.title,
							name: field,
							iconCls: 'icon-empty'
						});
					}
				}
				$('#columnMenuAdd_'+tableId).menu('show', {    
					left:e.pageX,
					top:e.pageY
				});
			}
		},{
			iconCls:'icon-remove',
			handler:function(e){
				e.preventDefault();
				var tableId = d.table;
				if($('#columnMenuSub_'+tableId).attr("id")){
					$('#columnMenuSub_'+tableId).menu("destroy");
				}
				$('body').append('<div id="columnMenuSub_'+tableId+'"/>');
				var cmenu = $('#columnMenuSub_'+tableId);
				cmenu.menu({
					onClick: function(item){
						$('#'+tableId).datagrid('hideColumn', item.name);
						cmenu.menu("destroy");
					}
				});
				var fields = $('#'+tableId).datagrid('getColumnFields');
				for(var i=1; i<fields.length; i++){
					var field = fields[i];
					var col = $('#'+tableId).datagrid('getColumnOption', field);
					if(!(col.hidden==true)){
						cmenu.menu('appendItem', {
							text: col.title,
							name: field,
							iconCls:'icon-ok'
						});
					}
				}
				$('#columnMenuSub_'+tableId).menu('show', {    
					left:e.pageX,
					top:e.pageY 
				});
			}
		}],
		onLoadSuccess : d.onLoadSuccess==undefined?undefined:d.onLoadSuccess,
		onBeforeLoad : function(param){
			$("#"+d.table).datagrid("clearChecked");
			$("#"+d.table).datagrid("clearSelections");
		},
		onSelect : d.onSelect==undefined?function(rowIndex,rowData){
			if(rowData.remark!=undefined){
				top.$("#remarkTop").text(rowData.remark);
			}
			if(!$("#remarkTopDialogDivOrder",top.document).is(":hidden")){
				top.$('#remarkTopDialogDivOrder').window("close");
			}
		}:d.onSelect,
		onHeaderContextMenu : function(e, field){
			e.preventDefault();
			var tableId = d.table;
			if($('#columnMenu_'+tableId).attr("id")){
				$('#columnMenu_'+tableId).menu("destroy");
			}
			$('body').append('<div id="columnMenu_'+tableId+'"/>');
			var cmenu = $('#columnMenu_'+tableId);
			cmenu.menu({
				onClick: function(item){
					if(item.iconCls == 'icon-ok'){
						$('#'+tableId).datagrid('hideColumn', item.name);
						cmenu.menu('setIcon', {
							target: item.target,
							iconCls: 'icon-empty'
						});
					}else {
						$('#'+tableId).datagrid('showColumn', item.name);
						cmenu.menu('setIcon', {
							target: item.target,
							iconCls: 'icon-ok'
						});
					}
					cmenu.menu("destroy");
				}
			});
			var fields = $('#'+tableId).datagrid('getColumnFields');
			for(var i=1; i<fields.length; i++){
				var field = fields[i];
				//console.log("field="+field);
				var col = $('#'+tableId).datagrid('getColumnOption', field);
				if(col.hidden==true){
					cmenu.menu('appendItem', {
						text: col.title,
						name: field,
						iconCls: 'icon-empty'
					});
				}else{
					cmenu.menu('appendItem', {
						text: col.title,
						name: field,
						iconCls: 'icon-ok'
					});
				}
			}
			$('#columnMenu_'+tableId).menu('show',{
				left:e.pageX,
				top:e.pageY
			});
		},
		onClickCell:d.onClickCell==undefined?undefined:d.onClickCell
	});//datagrid() end
};//cgrid end

function xyzdialog(d){
	$("body").append("<div id='"+d.dialog+"'></div>");
	$("#"+d.dialog).dialog({
		title : d.title==undefined?'对话框':d.title,
		height : d.height==undefined?200:d.height,
		width : d.width==undefined?300:d.width,
		modal : d.modal==undefined?true:d.modal,//锁住当前页面
		closable : d.closable==undefined?false:d.closable,//关闭
	    cache : d.cache==undefined?false:d.cache,//缓存
	    fit : d.fit==undefined?true:d.fit,//全屏
	    href : d.href==undefined?undefined:d.href,
	    content : d.content==undefined?undefined:d.content,
	    buttons : d.buttons==undefined?undefined:d.buttons,
		onLoad : d.onLoad==undefined?undefined:d.onLoad,
		onOpen : d.onOpen==undefined?undefined:d.onOpen,
		resizable : d.resizable==undefined?false:d.resizable,
		draggable : d.draggable==undefined?false:d.draggable
	});
	//set center
	if(d.center != undefined || d.center == true){
		$("#"+d.dialog).dialog("center");
	}
};//cdialog end

/*
 * combobox必须
 * url必须 
 * lazy可选:延迟加载,默认延迟
 * valueField可选
 * textField可选
 */
function xyzCombobox(c){
	var xyzComboboxData = {};
	xyzComboboxData.valueField = c.valueField==undefined?'value':c.valueField;
	xyzComboboxData.textField = c.textField==undefined?'text':c.textField;
	xyzComboboxData.loadFilter = function(data){
		if(data instanceof Array){
			return data;
		}else{
			if(data.status==1){
				return data.content;
			}else{
				return [];
			}
		}
	};
	var xyzComboboxLazy = c.lazy==undefined?true:c.lazy;
	if(xyzComboboxLazy){
		xyzComboboxData.onShowPanel = function(){
			if($(this).combobox("getData").length==0){
				$(this).combobox("reload",c.url);
			}
		};
	}else{
		xyzComboboxData.url = c.url;
	}
	xyzComboboxData.onBeforeLoad = c.onBeforeLoad==undefined?undefined:c.onBeforeLoad;
	xyzComboboxData.onLoadSuccess = c.onLoadSuccess==undefined?undefined:c.onLoadSuccess;
	xyzComboboxData.onSelect = c.onSelect==undefined?undefined:c.onSelect;
	xyzComboboxData.onChange = c.onChange==undefined?undefined:c.onChange;
	xyzComboboxData.onHidePanel = c.onHidePanel==undefined?undefined:c.onHidePanel;
	xyzComboboxData.required = c.required==undefined?false:c.required;
	xyzComboboxData.editable = c.editable==undefined?true:c.editable;
	xyzComboboxData.multiple = c.multiple==undefined?false:c.multiple;
	xyzComboboxData.mode = c.mode==undefined?'local':c.mode;
	$('#'+c.combobox).combobox(xyzComboboxData);
}

$.extend($.fn.validatebox.defaults.rules,{
    trim:{    
        validator: function(value,param){
        	if(param[0]==true){
        		if(/(^\s+)|(\s+$)/.test(value)){
        			return false;
        		}
        	}
        	return true;
        },    
        message:'请删除前后空白'
    }    
});


function xyzGetHeight(height){
	var heightT = parseInt(top.$("#centerContentTabs").css("height").split("px")[0]);
	return heightT-height;
}

function xyzGetCurrentRow(table,field,value){
	var rows = $("#"+table).datagrid("getRows");
	var rowT = [];
	for(var i=0;i<rows.length;i++){
		var row = rows[i];
		if(row[field]==value){
			rowT [rowT.length] = row;
		}
	}
	if(rowT.length!=1){
		return null;
	}else{
		return rowT[0];
	}
}

//针对Onchange事件 传div和value取中文 
function xyzOnChangeGetText(div,value){
	var result = "";
	var dataT = $("#"+div).combobox("getData");
	for(var ppp in dataT){
		if(dataT[ppp].value==value){
			result = dataT[ppp].text;
		}
	}
	return result;
}