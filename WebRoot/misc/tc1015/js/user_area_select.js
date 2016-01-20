var areas = new Array();
function LoadArea(provinceId,cityId, districtId)
{
	var noId = arguments[3] ? arguments[3] : ''; 
	$.get("/Jp/misc/tc1015/address.json?"+Math.random(),function(data){
		areas=eval(data);
		var provinces =getOptionsByParentId(-1);
		$("#provinceId"+noId).empty();
		$("#provinceId"+noId).append("<option value=''>请选择省</option>");
		for(var i = 0; i<provinces.length; i++)
		{
			$("#provinceId"+noId).append("<option value="+provinces[i].areaId+">"+provinces[i].areaName+"</option>");
		}
		setTimeout(function(){
			$("#provinceId"+noId).val(provinceId);
		},200);
		
		bindCity(provinceId, noId);
		
		setTimeout(function(){
			$("#cityId"+noId).val(cityId);
		},400);
		
		bindCounty(cityId);
		setTimeout(function(){
			$("#districtId").val(districtId);
		},600);
	},"html");	
	$("#provinceId"+noId).change(function(){
		bindCity($("#provinceId"+noId).val(), noId);
	});
	$("#cityId").change(function(){
		bindCounty($("#cityId").val());
	});
	
}
function bindCity(parentID, noId)
{
	
	$("#cityId"+noId).empty();
	$("#cityId"+noId).append("<option value=''>请选择城市</option>");
	$("#districtId").empty();
	$("#districtId").append("<option value=''>请选择区县</option>");
	var provinces =getOptionsByParentId(parentID);
	for(var i=0;i<provinces.length;i++)
	{
		$("#cityId"+noId).append("<option value="+provinces[i].areaId+">"+provinces[i].areaName+"</option>");
	}
}
function bindCounty(parentID)
{
	$("#districtId").empty();
	$("#districtId").append("<option value=''>请选择区县</option>");
	var provinces =getOptionsByParentId(parentID);
	for(var i=0 ;i<provinces.length;i++)
	{
		$("#districtId").append("<option value="+provinces[i].areaId+">"+provinces[i].areaName+"</option>");
	}
}

function getOptionsByParentId(id)
{
	var childrens=new Array();
	for(var i=0;i<areas.length;i++)
	{
		if(areas[i][1]==id){
			childrens.push({"areaId":areas[i][0],"areaName":areas[i][2]});
		}
	}
	return childrens;
}