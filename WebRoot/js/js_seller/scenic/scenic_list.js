$(document).ready(function(){
	
	 tableInit();
	
});


function tableInit(){
	$('#scenicTable').bootstrapTable({
		url:'../../SellerScenicWS/queryProductScenicList.do',
	    columns: [
          {field: 'provider',title: '资源'},  
          {field: 'nameCn',title: '名称'},
          {field: 'nameCn',title: '库存',align:'center',formatter:function(){
        	  return '<button type="button" class="btn btn-primary btn-xs">库存管理</button>';
          }}
	    ],
	    queryParams:function(params) {
	    	params.page=1;
	     	params.rows=10;
	    	return params;
	    },
	    responseHandler : function(data){
	    	console.log(data);
			if(data.status==1){
				return  data.content.rows;
			}else{
				top.window.layer.alert(data.msg);
			}
		}
	});
}