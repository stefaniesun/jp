$(function(){
	cartFocus();
	searchFocus();
	navListShow();
	navListMoreShow();
})
function searchFocus(){
	$('#searchKey').focus(function(){
		$('#searchForm').attr('class','searchForm searchFormFocus');
	}).blur(function(){
		$('#searchForm').attr('class','searchForm');
	});
}
function cartFocus(){
	var cartListTimeout,i=0;
	$('.cart').mouseenter(function(){
		var _obj = $(this);
		clearTimeout(cartListTimeout);
		_obj.attr('class','cart cartFocus');
		_obj.children('.cartList').show();
		(i==0) &&
		$.ajax({
			type: "GET",
			url: "/plugins/tcActionMini.php",
			data: "act=ajax_get_member_topcart",
			dataType: "jsonp",
			async: false,
			success: function(m){i=1,_obj.children('.cartList').html( m.memberfloatcart ),_obj.children('.cartNum').html(m.membercart),delegateCartDel();}
		});
	}).mouseleave(function(){
		obj = $(this),i=0;
		cartListTimeout = setTimeout(function(){
			obj.attr('class','cart');
			obj.children('.cartList').hide();
		},300);
	});
}
function navListShow(){
	var navListTimeout;
	$('.navMenu').mouseenter(function(){
		clearTimeout(navListTimeout);
		$('.navList').fadeIn('fast');
		$('.navMenu h3').attr('class','navMenuFocus');
	}).mouseleave(function(){
		navListTimeout = setTimeout(function(){
			$('.navMenu h3').removeAttr('class');
			$('.navList').fadeOut('fast');
		},400);
		setTimeout(function(){
			$('.navListMore').fadeOut('fast');
		},300);
	})
}
function navListMoreShow(){
	var navMoreTimeout;
	$('.navList li').mouseenter(function(){
		obj = $(this).children('.navListMore');
		navMoreTimeout = setTimeout(function(){
			$('.navListMore').hide();
			obj.show();			
		},200);
	})
	$('.navListMore').mouseenter(function(){
		clearTimeout(navMoreTimeout);
	})
}
function delegateCartDel(){
	$(".cart .cartList .delete").unbind("click").bind("click", function(e){
		var _curObj = $(this), _curTisId = _curObj.attr("data-id"), _curTisType = _curObj.attr("data-type");
		if(_curTisType == 'RemoveProduct' && !isNaN(parseInt(_curTisId))){
			$.ajax({
				type: "GET",
				url: "/plugins/tcActionMini.php",
				data: {act:'ajax_remove_member_topcart_product', pid:_curTisId},
				dataType: "jsonp",
				success: function(d){(d.error<1) && fun_get_member_topcart()}
			});
		}
	});
}
function fun_get_member_topcart(){
	var _obj = $('.cart');
	$.ajax({
		type: "GET",
		url: "/plugins/tcActionMini.php",
		data: "act=ajax_get_member_topcart",
		dataType: "jsonp",
		async: false,
		success: function(m){_obj.children('.cartList').html( m.memberfloatcart ),_obj.children('.cartNum').html(m.membercart),delegateCartDel();}
	});
}
userActionCollectioned = {
	add: function(goodsid){
		parseInt(goodsid) && jQuery.ajax({type:'get',url:'http://www.techan.com/myhome.html?act=collect',data:{id: goodsid, r:Math.random()},dataType:'jsonp',success:this.CollResponse})
	},
	CollResponse: function(result){
		alert(result.message)
	}
}