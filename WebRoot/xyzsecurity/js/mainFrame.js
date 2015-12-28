//初始化
$(document).ready(function() {
	var windowWidth = $(window).width();
	var windowHeight = $(window).height();
	var width;
	var height; //= $(window).height()-20;
	if(windowWidth>1500){
		width = windowWidth*9/10;
	}else{
		width = windowWidth-20;
	}
	height = windowHeight-20;
	//width = width>1320?width:1320;
	width = 1320;
	
	$("#mainDiv").css({"width":width,"height":height});
	
	$.parser.parse();
	
	loginDecide();
	
	$("#safeQuitButton").click(function(){
		safeQuit();
	});
	
	
	$("#inMailFlagButton").click(function(){
		getInMailFlag();
	});
	
	$("#alterPasswordButton").click(function(){
		alterPassword();
	});
	
	//初始化选项卡的工具栏
	{
		$("#centerContentTabs").tabs({
			toolPosition : 'left',
			tools:[{
				id : 'xyzasdasdsddsadsadasdg',
		        iconCls:'icon-no',
		        handler:function(){
		        	var tab = $('#centerContentTabs').tabs('getSelected');
		        	if(!tab){
		        		return;
		        	}
		        	var currentTitle = tab.panel("options").title;
		        	
		            var tabs = $("#centerContentTabs").tabs('tabs');
		            var tabsTitles = new Array(tabs.length);
		            var titleC = currentTitle;
		            for(var i=0;i<tabs.length;i++){
		            	var titleT = tabs[i].panel("options").title;
		            	tabsTitles[i] = titleT;
		            }
		            for(var i=0;i<tabsTitles.length;i++){
		            	var titleT = tabsTitles[i];
		            	if(titleT!=titleC){
		            		$("#centerContentTabs").tabs('close',titleT);
		            	}
		            }
		        }
		    },{
		    	id : 'xyzasdjgashdga3eydyweada',
		        iconCls:'icon-reload',
		        handler:function(){ 
		        	var tab = $('#centerContentTabs').tabs("getSelected");
		        	if(!tab){
		        		return;
		        	}
		        	var id = tab.panel("body").find("iframe").attr("id");
		        	var url = tab.panel("body").find("iframe").attr("src");
		        	$("#"+id).attr("src",url);
		        }
		    }],
			onSelect:function(title,index){
				if(!xyzIsNull(title)){
					var t1 = title.split("[");
					if(t1.length>0){
						var bigTitle = t1[0];
						$("#helpGreatDiv").accordion("select",bigTitle);
						if(t1.length>1){
							var t2 = t1[1];
							var t3 = t2.split("]");
							if(t3.length>0){
								var t4 = t3[0];
								$("#helpGreatDiv_"+bigTitle).accordion("select",t4);
							}
						}
					}
				}
			},
			onClose : function(){
				var tabsLength = $('#centerContentTabs').tabs("tabs").length;
				if(tabsLength==0){
					$("#promptDiv").show();
				}
			}
		});
		
		$("#xyzasdasdsddsadsadasdg").tooltip({
			position: 'bottom',    
			content: '关闭所有未选中的选项卡'
		});
		
		$("#xyzasdjgashdga3eydyweada").tooltip({
			position: 'bottom',
			content: '刷新当前选项卡的工作面板'
		});
	}
});

function loginDecide(){
	xyzAjax({
		url : "../LoginWS/decideLogin.do",
		data : {},
		success : function(data) {
			if(data.status==1){
				$("#mainShadeDiv").remove();
				currentUserFunctions = data.content.securityFunctionList;
				currentUserButtons = data.content.buttonList;
				currentUserUsername = data.content.securityLogin.username;
				currentUserNickname = data.content.securityLogin.nickName;
				initHelp();
				//setInterval(getLogWarn,1000*60*5);
				//showNotice("big");
				//decideAlterPassword();
				initRemark();
				$("#currentUser").text(currentUserNickname);
				$("#currentUsername").val(currentUserUsername);
//				findInMail();
			}else{
				alert(data.msg);
				window.location.href = "../manager.html";
			}
		}
	});
}


function safeQuit(){
	xyzAjax({
		url : "../LoginWS/safeQuit.do",
		data : {},
		success : function(data) {
		}
	});
	deleteLoginCookie();
	window.location.href = "../manager.html";
}

function getInMailFlag(){
	$("#helpGreatDiv").accordion("select","站内信");
	$("#helpGreatDiv_站内信").accordion("select","收件箱");
}

function initHelp(){
	for(var i=0;i<currentUserFunctions.length;i++){
		var f = currentUserFunctions[i];
		if(f.isApi==1){
			continue;
		}
		var groupCn = f.groupCn;
		if($("#helpGreatDiv").accordion("getPanel",f.groupCn)){
			;
		}else{
			$("#helpGreatDiv").accordion("add",{
				title:groupCn,
				selected: true,
				collapsible:true,
				content:'<div id="helpGreatDiv_'+groupCn+'" style="padding: 5px;"></div>'
			});
			$('#helpGreatDiv_'+groupCn).accordion({    
				fit:true,
				animate:false,
				selected:100
			});
		}
		
		var nameCn = f.nameCn;
		if($('#helpGreatDiv_'+f.groupCn).accordion("getPanel",nameCn)){
			;
		}else{
			$('#helpGreatDiv_'+f.groupCn).accordion("add",{
				title:nameCn,
				selected: false,
				onExpand:function(){
					var title = $(this).panel("options").title;
					var numberCode = "";
					var url = "";
					for(var i=0;i<currentUserFunctions.length;i++){
						if(currentUserFunctions[i].nameCn==title){
							numberCode = currentUserFunctions[i].numberCode;
							url = ".."+currentUserFunctions[i].url;
							break;
						}
					}
					$("#promptDiv").hide();
					var titleBig = "";
					if($("#helpGreatDiv").accordion("getSelected")){
						titleBig = $("#helpGreatDiv").accordion("getSelected").panel("options").title;
					}
					var currentTitle=titleBig+"["+title+"]";
					var iframeid = "contentIframe_"+numberCode;
					if($('#centerContentTabs').tabs("exists",currentTitle)){
						$('#centerContentTabs').tabs("select",currentTitle);
						return;
					}
					$('#centerContentTabs').tabs('add',{
					    title:currentTitle,
					    content:"<iframe id='"+iframeid+"' name='centerContentIframe' scrolling='auto' frameborder='0' style='overflow-x:hidden; overflow-y:auto;'></iframe>",
					    closable:true
					});
					var tempWidth = $("#centerContentTabs").css("width");
					var tempHeight = $("#centerContentTabs").css("height");
					var tempWidth2 = parseInt(tempWidth.split("px")[0]);
					var tempHeight2 = parseInt(tempHeight.split("px")[0]);
					$("#"+iframeid).css("width",(tempWidth2)+"px");
					$("#"+iframeid).css("height",(tempHeight2-40)+"px");
					$("#"+iframeid).attr("src",url);
				}
			});
		}
	}
	//选中一级目录的时候让二级目录自动归位
	$("#helpGreatDiv").accordion({
		onSelect : function(title,index){
			var p = $('#helpGreatDiv_'+title).accordion('getSelected');
			if (p){
				var index = $('#helpGreatDiv_'+title).accordion('getPanelIndex', p);
				$('#helpGreatDiv_'+title).accordion("unselect",index);
			}
		}
	});
}

function initRemark(){
	var ttw = $("body").width();
	$('#remarkTopDialogDiv').window({
	    title: '备注区域',    
	    width: 200,
	    height: 400,
	    top:36,
	    left : ttw-190,
	    zIndex : 10000,
	    closable : false,
	    closed: false,
	    draggable : true,
	    resizable : false,
	    collapsible : true,
	    minimizable : false,
	    maximizable : false,
	    modal: false
	});
}
