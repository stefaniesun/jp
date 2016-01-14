$(function(){
	var msghtml = '<div class="thickcon" id="" style="width: 260px; height: 80px; padding-left: 10px; padding-right: 10px;"><div id="model-collect" class="model-prompt"><div class="con"><b class="{{&tips}}"></b><h5><font color="#009900">{{&content}}</font></h5><a href="http://www.techan.com/order/cart.php">查看购物车</a></div></div></div><div class="thickcountdown" style="width:300"><span id="tc-countdown"></span>秒后自动关闭</div>';
	$(".p-btn .btn-append").unbind("click").bind("click", function(e){
		var currentObj = $(this), tagVal = currentObj.attr("tctag"),currentVal = tagVal.split("|")[2];
		$.ajax({
			type: "get",
			url: "/order/InitCart.php",
			data: {step:'ajaxAddToCart', pid:currentVal, pcount:1, ptype:1},
			dataType: 'jsonp',
			success: ajaxAddToCartResponse
		})
	});
	function ajaxAddToCartResponse(data){
		msghtml = msghtml.replace('{{&tips}}',data.tipstyle);
		msghtml = msghtml.replace('{{&content}}',data.content);
		$.prompt.setDefaults({
			prefix: 'messaget',
			timeout: 5000
		});
		var message = {
			message_c: {
				title: '提示',
				html:msghtml,
				show: 'show',
				
				buttons: false
			}
		};
		$.prompt(message),intervalNum();
	}
	function intervalNum(){
		var b = 5;
		$("#tc-countdown").html(b)
		var a = setInterval(function() {
			b--,
			$("#tc-countdown").html(b);
			b ==0 && clearInterval(a);
		},
		1e3);
	}
})