$(function(){
	itemImgList();
	itemAddCart();
	// itemBuyNumber();
	itemType();
	itemContentHead();
	itemBar();
})
function itemImgList(){
	$('.itemImgList li').mouseenter(function(){
		imgSrc = $(this).children('a').children('img').attr('data-src');
		imgFull = $(this).children('a').children('img').attr('data-full');
		$('.itemImgBig img').hide();
		$('.itemImgBig img').fadeIn('fast');
		$('.itemImgBig img').attr('src',imgSrc);
		$('.itemImgFull img').attr('src',imgSrc);
		$('.itemImgList li').removeAttr('class');
		$(this).attr('class','itemImgListFocus');
	})
	$('.itemImgBig img').click(function(){
		$('.itemImgFull').fadeIn('fast');
		$('html,body').animate({scrollTop: '0px'});
	})
	$('.itemImgList li').click(function(){
		$('.itemImgFull').fadeIn('fast');
		$('html,body').animate({scrollTop: '0px'});
	})
	$('.itemImgFull').click(function(){
		$(this).fadeOut('fast');
	})
}
function itemAddCart(){
	$('.itemAddCart').click(function(){
		$('.itemAddCartAnimate').show();
		$('.itemAddCartAnimate').animate({top:'-150px',left:'390px',width:'0',height:'0'},500,function(){
			$('.itemAddCartAnimate').hide();
			$('.itemAddCartAnimate').css({top:'420px',left:'100px',width:'70px',height:'70px'});
		});
	})
}
function itemBuyNumber(){
	itemStockNum = parseInt($('.itemStockNum em').text());
	$('.itemStockUp').click(function(){
			buyNum = parseInt($('#buyNumber').val());
			if(buyNum < itemStockNum){
				$('#buyNumber').val(buyNum+1);
				$('.itemStockDown').attr('class','itemStockDown');
			}else{
				$('.itemStockUp').attr('class','itemStockUp itemStockDisabled');
			}
	})
	$('.itemStockDown').click(function(){
			buyNum = parseInt($('#buyNumber').val());
			if(buyNum > 1){
				$('#buyNumber').val(buyNum-1);
				$('.itemStockUp').attr('class','itemStockUp');
			}else{
				$('.itemStockDown').attr('class','itemStockDown itemStockDisabled');
			}
	})
	$('#buyNumber').blur(function(){
		buyNum = parseInt($('#buyNumber').val());
		if(buyNum > itemStockNum){
			$('.itemStockUp').attr('class','itemStockUp itemStockDisabled');
			$('.itemStockDown').attr('class','itemStockDown');
			$(this).val(itemStockNum);
		}
		if(buyNum < 1){
			$('.itemStockDown').attr('class','itemStockDown itemStockDisabled');
			$('.itemStockUp').attr('class','itemStockUp');
			$(this).val(1);
		}
	})
}
setAmount = {
	min: 1,
	max: TECHAN_ITEM_INFO.store,
	count: 1,
	matchCountKey: ["pcount", "pCount", "num"],
	add: function() {
		return this.count >= this.max ? (($('.itemStockUp').attr('class','itemStockUp itemStockDisabled'),$('.itemStockDown').attr('class','itemStockDown')),!1) : (this.count++, $("#buyNumber").val(this.count), $('.itemStockDown').attr('class','itemStockDown'), this.setBuyLink(), void 0)
	},
	reduce: function() {
		return this.count <= this.min ? (($('.itemStockDown').attr('class','itemStockDown itemStockDisabled'),$('.itemStockUp').attr('class','itemStockUp')),!1) : (this.count--, $("#buyNumber").val(this.count), $('.itemStockUp').attr('class','itemStockUp'), this.setBuyLink(), void 0)
	},
	modify: function() {
		var t = parseInt($("#buyNumber").val(), 10);
		return "" == $("#buyNumber").val() ? !1 : isNaN(t) || this.min > t || t > this.max ? ($("#buyNumber").val(this.count), $('.itemStockDown').attr('class','itemStockDown'), $('.itemStockUp').attr('class','itemStockUp'), !1) : (this.count = t, this.setBuyLink(), void 0)
	},
	setBuyLink: function() {
		var t = this;
		$("#itemBuy .btn-append").each(function() {
			var e, i, s = $(this),
			a = s.prop("href"),
			n = a.split("?")[1];
			(function() {
				for (var o = 0; t.matchCountKey.length > o; o++) if (i = RegExp(t.matchCountKey[o] + "=\\d+"), i.test(n)) return e = a.replace(i, t.matchCountKey[o] + "=" + t.count),s.prop("href", e),!1
			})()
		}),onNumChange()
	}
}
function onNumChange() {
	var t = Number($("#buyNumber").val()),
	e = $(".itemBar .itemBuy a"),
	i = e.attr("href");
	e.length && e.attr("href", i.replace(/pcount=\d+/, "pcount=" + t))
}
function itemType(){
	$('.itemType li').click(function(){
		typeNum = $(this).children('a').attr('data-type');
		$('.itemType li').removeAttr('class');
		$(this).attr('class','itemTypeFocus');
		$('#itemType').val(typeNum);
	})
}
function itemContentScroll(type){
	$('html,body').animate({scrollTop:$('#'+type).offset().top -15},300);
}
function itemContentHead(){
	$('.itemContentHead li').click(function(){
		type = '#' + $(this).attr('data-position');
		$('html,body').animate({scrollTop:$(type).offset().top -15},300);
	})
	$('.itemMoreInfo a').click(function(){
		$('html,body').animate({scrollTop:$("#D3").offset().top -15},300);
	})
}
function itemBarHead(){
	$('#iteamBarHead li').click(function(){
		$('#iteamBarHead li').removeAttr('class');
		$(this).attr('class','itemContentHeadFocus');
	})
}
function itemBar(){
	$(window).scroll(function(){
		docT = $(document).scrollTop();
		objD1 = $('#D1').offset().top - 15;
		objD2 = $('#D2').offset().top - 15;
		objD3 = $('#D3').offset().top - 15;
		objD4 = $('#D4').offset().top - 15;
		//$('.info').text(docT+':'+objD2);
		if(docT > objD1){
			$('.itemBar').fadeIn();
		}else{
			$('.itemBar').fadeOut();
		}
		if(docT > objD1){
			$('#iteamBarHead li').removeAttr('class');
			$('#H1').attr('class','itemContentHeadFocus');
		}
		if(docT > objD2-1){
			$('#iteamBarHead li').removeAttr('class');
			$('#H2').attr('class','itemContentHeadFocus');
		}
		if(docT > objD3-1){
			$('#iteamBarHead li').removeAttr('class');
			$('#H3').attr('class','itemContentHeadFocus');
		}
		if(docT > objD4-1){
			$('#iteamBarHead li').removeAttr('class');
			$('#H4').attr('class','itemContentHeadFocus');
		}
	})
}