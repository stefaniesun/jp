$(document).ready(function(){
	//initShoppingCart();
	
	$("#shopping-cart-button").click(function(){
		//关闭购物车
		$(".small-chat-box").attr("class","small-chat-box fadeInRight animated");
		
		if($(".total-count").text()<1){
			layer.alert('购物车中还没添加商品哦！', {icon: 0}); 
			return;
		}
		var index = parent.layer.open({
		    type: 2,
		    fix: false,
		    title: '订单信息',
		    maxmin: false,
		    shadeClose:true,
		    content: ['../index/shoppingCart_dialog.html'],
		    area: ['1340px', '800px'],
		    close: function(index){
		    }
		});
	});
	
});

function calculate(){
	var totalPrice=0;
	$(".rcart-list li").each(function(){
		var count=$(this).data("count");
		var price=$(this).data("price");
		totalPrice+=count*price;
		$(this).find(".rcart-d-total").html(count*price);
	});
	$(".totalPrice").html(totalPrice);
}

function buildShoppingCart(data){
	$(".badge").html(data.content.total);
	var products=data.content.rows;
	var html="";
	$.each(products, function(index, product){ 
		html+='<li  class="rcart-dish eleme_view" data-numberCode="'+product.numberCode+'" data-count="'+product.count+'"  data-price="'+product.price+'">';
		html+=	'<div class="rcart-d-name">'+product.productNameCn+'</div>';
		html+=	'<div class="rcart-d-modify">';
		html+=		'<a class="rcart-d-act minus d_btn" >-</a>';
		html+=		'<input  readonly="readonly" class="rcart-d-qty set_num_in" type="text" value="'+product.count+'" />';
		html+=		'<a class="rcart-d-act add i_btn" >+</a>';
		html+=	'</div>';
		html+=	'<div class="rcart-d-total symbol-rmb"></div>';
		html+=	'<a class="rcart-d-del r_btn">×</a>';
		html+='</li>';
	});
	$(".rcart-list").html(html);
	$(".total-count").html(products.length);
	calculate();
	
	$(".rcart-d-del").click(function(){
		$.ajax({
			url : "../ShoppingCartWS/deleteShoppingCart.cus",
			type : "POST",
			data : {
				numberCode:$(this).parents("li").data("numbercode")
			},
			async : false,
			dataType : "json",
			success : function(data) {
				if(data.status==1){
					initShoppingCart();
				}else{
					layer.alert(data.msg,-1,function(){
					});
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				B2BAjaxError(textStatus);
			}
		});
	});
	
	
	$(".minus").click(function(){
		var count=$(this).parents("li").data("count");
		if(count<2){
			return;
		}
		count--;
		var numberCode=$(this).parents("li").data("numbercode");
		
		$.ajax({
			url : "../ShoppingCartWS/editShoppingCart.cus",
			type : "POST",
			data : {
				numberCode:numberCode,
				count:count
			},
			async : false,
			dataType : "json",
			success : function(data) {
				if(data.status==1){
					$(".rcart-list li[data-numbercode='"+numberCode+"']").data("count",count);
					$(".rcart-list li[data-numbercode='"+numberCode+"']").find(".set_num_in").val(count);
					calculate();
				}else{
					layer.alert(data.msg,-1,function(){
					});
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				B2BAjaxError(textStatus);
			}
		});
	});
	
	$(".add").click(function(){

		var count=$(this).parents("li").data("count");
		if(count>98){
			return;
		}
		count++;
		var numberCode=$(this).parents("li").data("numbercode");
		$.ajax({
			url : "../ShoppingCartWS/editShoppingCart.cus",
			type : "POST",
			data : {
				numberCode:numberCode,
				count:count
			},
			async : false,
			dataType : "json",
			success : function(data) {
				if(data.status==1){
					$(".rcart-list li[data-numbercode='"+numberCode+"']").data("count",count);
					$(".rcart-list li[data-numbercode='"+numberCode+"']").find(".set_num_in").val(count);
					calculate();
				}else{
					layer.alert(data.msg,-1,function(){
					});
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				B2BAjaxError(textStatus);
			}
		});
	});
	
}

function initShoppingCart(){
	$.ajax({
		url : "../ShoppingCartWS/queryShoppingCart.cus",
		type : "POST",
		data : {
			page:1,
			rows:5
		},
		async : false,
		dataType : "json",
		success : function(data) {
			if(data.status==1){
				buildShoppingCart(data);
			}else{
				layer.alert(data.msg,-1,function(){
				});
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			B2BAjaxError(textStatus);
		}
	});
}