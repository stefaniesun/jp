jQuery(function(){
  jQuery(".addressListBox ul li.addressBox").click(function(){
    jQuery(".addressListBox ul li.addressBox").removeClass('slt');
	jQuery(this).addClass("slt");
  }); 
  jQuery(".addressListBox ul li.addressBox").hover(
    function(){$(this).addClass("hover");},
	function(){$(this).removeClass("hover");}
  );
  
  jQuery(".statistiL dl dt").click(function(){
	var attrClass = jQuery(this).parent().attr("class");
	if(attrClass=="off"){
	  jQuery(this).parent().removeClass('off');
	  jQuery(this).parent().addClass('on');
	}else{
	  jQuery(this).parent().removeClass('on');
	  jQuery(this).parent().addClass('off');
	}
	
	
  });
  
  jQuery(".payment ul li label").click(function(){
    jQuery(".payment ul li label").removeClass('onlickColor');
	jQuery(this).addClass("onlickColor");
  }); 
  
});