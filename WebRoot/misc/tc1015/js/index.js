$(function(){
	slideTabPosition();
	slideTab();
	slideAutoPlay();
	slideState();
	slidePrev();
	slideNext();
	// popBox();
})
var slideTabNum;
var slideFocus = 1;
var slideAutoTimeout;
var slideSpeed = 5000;

function slideTabPosition(){
	slideTabNum = $('.slideTab li').length;
	$('.slideTab').css({'width':slideTabNum * 18 +'px','margin-left':'-' + slideTabNum * 18 / 2 +'px'});
}
function slideTab(){
	$('.slideTab li').mouseenter(function(){
		slideScroll(null,$(this));
		slideFocus = $(this).attr('id').split('S');
		slideFocus = parseInt(slideFocus[1]);
	})
}
function slideAutoPlay(){
	slideAutoTimeout = setInterval(function(){
		if(slideFocus < slideTabNum){
			slideScroll(slideFocus + 1,null);
			slideFocus ++;
		}else{
			slideScroll(1,null);
			slideFocus = 1;
		}
	},slideSpeed);
}
function slideState(){
	$('.slide').mouseenter(function(){
		clearInterval(slideAutoTimeout);
		$('.slidePrev').css({left:'0px'});
		$('.slideNext').css({right:'0px'});
	}).mouseleave(function(){
		slideAutoPlay();
		$('.slidePrev').css({left:'-30px'});
		$('.slideNext').css({right:'-30px'});
	})
}
function slidePrev(){
	$('.slidePrev').click(function(){
		slidePrevNum = slideFocus - 1;
		if(slidePrevNum <= 0){
			slideFocus = slideTabNum;
			slideScroll(slideFocus,null);
		}else{
			slideFocus--;
			slideScroll(slidePrevNum,null);
		}
	})
}
function slideNext(){
	$('.slideNext').click(function(){
		slideNext = slideFocus + 1;
		if(slideNext > slideTabNum){
			slideFocus = 1;
			slideScroll(slideFocus,null);
		}else{
			slideFocus++;
			slideScroll(slideNext,null);
		}
	})
}
function slideScroll(slideNum,slideObj){
	if(slideNum != null){
		slideObj = $('#S'+(slideNum));
	}
	$('.slideTab li').removeAttr('class');
	$(slideObj).attr('class','slideTabFocus');
	$('#slideImg').hide();
	$('#slideImg').attr('src',$(slideObj).attr('data-src'));
	$('#slideHref').attr('href',$(slideObj).attr('data-href'));
	$('#slideImg').fadeIn('fast');
}
function popBox(){
	if($.cookie('popClose') != 1){
		$('#popBox').fadeIn('fast');
		$('#popBox').height($(document).height());
	}
}
function popBoxClose(){
	$('#popBox').fadeOut('fast');
	$.cookie('popClose', '1', { expires: 1 });
}
function popBoxGo(){
	$.cookie('popClose', '1', { expires: 1 });
	window.location='/shatianyou.html';
}