/* $Id : orderprocess.js 2013-11-13 12:04:10 zhike $ */
/*前台激活调用*/
$(function(){
	orderprocess.init(),orderprocess.eventInit(),orderprocess.usePreferentialInit(),orderprocess.promoteInit(),orderprocess.addressGetTransFeeData(),orderprocess.addressGetPromoteFeeData(),orderprocess.addressGetSurplusFeeData(),orderprocess.surplusInit();
});

var orderprocess = window.orderprocess || function () { };
orderprocess.data = {
	CART_URL: "/cart/order.html",
	CREATE_CART_URL: "/gopay.html",
	address: {},
	address_data: {},
	infoDefault : {
		address: {},
		orderAmount : {},
		transFee: {},
		surplusMoney: {
			totalS: '0.00',
			useS: '0.00'
		},
		cashMoney: '0.00'
	}
};
orderprocess.status = {
	address:{},
	surplus:{},
	promote:{},
	need_order_amount:0
};
orderprocess.tpl = {
	new_addr: "<dl><dt>收货人：</dt><dd><input name='name' value='' size='15' class='dizhiInput' /></dd></dl><dl><dt>手机：</dt><dd><input name='mobile' value='' size='20' class='dizhiInput' /></dd></dl><dl><dt>收货区域：</dt><dd><input id='provinceIdValue' class='dizhi' type='hidden' value='0'><input id='cityIdValue' class='dizhi' type='hidden' value='0'><input id='districtIdValue' class='dizhi' type='hidden' value='0'><select name='province' id='provinceId' class='sgcategory'><option value=''>请选择省</option></select><select name='city' id='cityId' class='sgcategory'><option value=''>请选择城市</option></select><select name='district' id='districtId' class='sgcategory'><option value=''>请选择区县</option></select></dd></dl><dl><dt>详细地址：</dt><dd><input name='address' value='' size='60' class='dizhiInput' /></dd></dl><dl><dt>&nbsp;</dt><dd><input value='' type='button' t='add_save' class='dizhiBtn1' /><input value='' type='button' class='dizhiBtn2' t='add_cancel' /></dd></dl>",
	edit_addr: "<dl><dt>收货人：</dt><dd><input name='name' value='{{&name}}' size='15' class='dizhiInput' /></dd></dl><dl><dt>手机：</dt><dd><input value='{{&mobile}}' name='mobile' size='20' class='dizhiInput' /></dd></dl><dl><dt>收货区域：</dt><dd><input id='provinceIdValue' class='dizhi' type='hidden' value='{{&provinceIdValue}}'><input id='cityIdValue' class='dizhi' type='hidden' value='{{&cityIdValue}}'><input id='districtIdValue' class='dizhi' type='hidden' value='{{&districtIdValue}}'><select name='province' id='provinceId' class='sgcategory'><option value=''>请选择省</option></select><select name='city' id='cityId' class='sgcategory'><option value=''>请选择城市</option></select><select name='district' id='districtId' class='sgcategory'><option value=''>请选择区县</option></select></dd></dl><dl><dt>详细地址：</dt><dd><input name='address' value='{{&address}}' size='60' class='dizhiInput' /></dd></dl><dl><dt>&nbsp;</dt><dd><input value='' t='{{&save}}' type='button' class='dizhiBtn1' /><input t='{{&cancel}}' value='' type='button' class='dizhiBtn2' /><input aid={{&aid}} name='aid' value='' type='hidden' /></dd></dl>",
	item_addr: "<li id='{{&num}}' class='addressBox'><input type='radio' name='address' id='userAddr_{{&num}}' value='{{&aid}}' class='addressinput'><label for='{{&aid}}'><p><b>{{&name}}</b><em>收</em><span class='editBt' t='editing' aid='{{&aid}}'>编缉</span></p><p class='listAddressShow'>{{&address}}</p><p class='listAddressShow'>{{&address_all}}</p><p class='listAddressShow'>{{&address_shouji}}</p><sub></sub></label></li>",
	edit_item_addr: "<input type='radio' name='address' id='userAddr_{{&num}}' value='{{&aid}}' class='addressinput'><label for='{{&aid}}'><p><b>{{&name}}</b><em>收</em><span class='editBt' t='editing' aid='{{&aid}}'>编缉</span></p><p class='listAddressShow'>{{&address}}</p><p class='listAddressShow'>{{&address_all}}</p><p class='listAddressShow'>{{&address_shouji}}</p><sub></sub></label>"
};
orderprocess.init = function(a){
	var cT = $("#cartTable"), aL = $("#addressListBox"), sL = $("#statistiL");
	orderprocess.statusSet("need_order_amount", (parseFloat(orderprocess.data.infoDefault.orderAmount)+parseFloat(orderprocess.data.infoDefault.transFee)-parseFloat(orderprocess.data.infoDefault.cashMoney)).toFixed(2));
	$("input[t=edit_cancel],input[t=add_cancel]", cT).unbind("click").click(function(b) {
		$("div#cartTable").html('').hide();
		b.stopPropagation();
	});
	$("li.addressBox", aL).unbind("click").click(function(b) {
		$("li.addressBox").each(function(a){
			$(this).removeClass("slt");
		});
		$(this).addClass("slt");
		$("div#cartTable").html('').hide();
		var numid = $(this).attr("id"),aid = $("#userAddr_"+numid).val();
		openNewMsgDiv('地址变化，正在重新确认信息...', 1);
		$.ajax({type:'post', async: false, url:orderprocess.data.CART_URL,data:{a:'select_my_address',aid:encodeURIComponent(aid),r:Math.random()},success:function(c){
			//delete orderprocess.status.address;
			var c = eval("("+c+")");
			if(c.error==0){
				orderprocess.statusSet("address",c),closeMast();
			}else closeMast(),openNewMsgDiv(c.content, 1);return false;
		}});
		var f = {}, addr = orderprocess.statusGet("address");
		if(addr.has_exclud_goods){
			var hase_str = '';
			for (var hase in addr.has_exclud_goods){
				if(addr.has_exclud_goods.length > hase+1){
					hase_str += addr.has_exclud_goods[hase]+'，';
				}else{
					hase_str += addr.has_exclud_goods[hase];
				}
			}
			$("#DeliveryTip").show().html('您购买的（<font color=red>'+hase_str+'</font>）暂时不支持配送至您当前选择的收货地区，<a href="/cart.html" style="color:red"><u>修改购物车</u></a>或者<a href="#top" style="color:red"><u>使用收货地址</u></a>。');
			$("#btn_order").attr("disabled","disabled").removeClass("checkoutInput").addClass("checkoutInput1");
		}else{
			$("#DeliveryTip").hide().html('');
			$("#btn_order").attr("disabled",false).removeClass("checkoutInput1").addClass("checkoutInput");
		}
		f['cancel_action']=true,f['transFee'] = addr.trans_fee,f['cashMoney'] = orderprocess.data.infoDefault.cashMoney,orderprocess.data.infoDefault.surplusMoney.totalS = orderprocess.data.infoDefault.surplusMoney.useS,f['type_money']='0.00',f['total_goods_price'] = orderprocess.data.infoDefault.orderAmount;
		f['total_order_money']=(parseFloat(f['total_goods_price'])+parseFloat(f['transFee'])-parseFloat(f['type_money'])-parseFloat(f['cashMoney'])).toFixed(2)
		$("#promote_fee_list > li", $("#statistiL")).find("input[type=radio]").each(function(){this.checked=false}),$("#promote_fee_list").find("a[t=cancel_promote_use]").each(function(){$(this).hide()}),$("input[name=Balance]").attr('checked', false),orderprocess.addressShowEditData(f),b.stopPropagation();
	});
	$("span[t=editing]", aL).unbind("click").click(function(n){
		$("li.addressBox").each(function(a){
			$(this).removeClass("slt");
		});
		$(this).parent().parent().parent().addClass("slt");
		var aid = $(this).attr("aid");
		var o = orderprocess.tpl.edit_addr;
		var b = orderprocess.addressGetData(o, aid);
		cT.html(orderprocess.data.address_data['edit_info']).show(100);
		LoadArea(jQuery("#provinceIdValue").val(),jQuery("#cityIdValue").val(),jQuery("#districtIdValue").val()),orderprocess.init(),n.stopPropagation();
	});
	$("li[t=addAddress]", aL).unbind("click").click(function(){
		if($("div#cartTable").css("display") != 'none'){
			if($("input[name=name]").val()){
				$("#cartTable").show(100).html(orderprocess.tpl.new_addr);
				LoadArea(jQuery("#provinceIdValue").val(),jQuery("#cityIdValue").val(),jQuery("#districtIdValue").val());
				orderprocess.init();
			}else{
				$("div#cartTable").html('').hide();
			}
		}else{
			$("#cartTable").show(100).html(orderprocess.tpl.new_addr);
			LoadArea(jQuery("#provinceIdValue").val(),jQuery("#cityIdValue").val(),jQuery("#districtIdValue").val());
			orderprocess.init();
		}
	});
	$("input[t=edit_save]", cT).click(function(n) {
		var a = orderprocess.addressGetEditData(cT);
		var o = orderprocess.tpl.edit_item_addr;
		if (!1 !== a) {
			var aid = $("input[name=aid]", cT).attr("aid"), numid = $("input[value="+aid+"]", aL).attr("id").split("_");
			if($("#provinceId").val() && $("#cityId").val() && $("#districtId").val()){
				a['province'] = $("#provinceId").val();
				a['city'] = $("#cityId").val();
				a['district'] = $("#districtId").val();
			}
			openNewMsgDiv('地址变化，正在重新确认信息...', 1);
			var apost_param = '&';
			$.each(a,function(x,y){
				apost_param += 'al['+x+']='+y+'&';
			});
			$.ajax({type:'post', async: false, url:orderprocess.data.CART_URL,
				data:'a=update_my_address&aid='+encodeURIComponent(aid)+apost_param+(new Date).getTime(),
				success:function(c){
					delete orderprocess.status.address;
					var c = eval("("+c+")");
					if(c.error==0){
						var a = {num:numid[1]};
						$.extend(c, a);
						for(var f in c){
							if(f == 'aid'){
								o = o.replace(/{{&aid}}/g, c[f]);
							}else{
								o = o.replace("{{&"+f+"}}", c[f]);
							}
						}
						orderprocess.statusSet("address", c),orderprocess.status.address['address_id'] = c.aid, closeMast(),$("li.slt", aL).html(o),$("div#cartTable").html('').hide();
						orderprocess.init(),n.stopPropagation();
					}else closeMast(),openNewMsgDiv(c.content, 1);
				}
			});
			var f = {}, addr = orderprocess.statusGet("address");
			if(addr.has_exclud_goods){
				var hase_str = '';
				for (var hase in addr.has_exclud_goods){
					if(addr.has_exclud_goods.length > hase+1){
						hase_str += addr.has_exclud_goods[hase]+'，';
					}else{
						hase_str += addr.has_exclud_goods[hase];
					}
				}
				$("#DeliveryTip").show().html('您购买的（<font color=red>'+hase_str+'</font>）暂时不支持配送至您当前选择的收货地区，<a href="/cart.html" style="color:red"><u>修改购物车</u></a>或者<a href="#top" style="color:red"><u>使用收货地址</u></a>。');
				$("#btn_order").attr("disabled","disabled").removeClass("checkoutInput").addClass("checkoutInput1");
			}else{
				$("#DeliveryTip").hide().html('');
				$("#btn_order").attr("disabled",false).removeClass("checkoutInput1").addClass("checkoutInput");
			}
			f['cancel_action']=true,f['transFee'] = addr.trans_fee,f['cashMoney'] = orderprocess.data.infoDefault.cashMoney,orderprocess.data.infoDefault.surplusMoney.totalS = orderprocess.data.infoDefault.surplusMoney.useS,f['type_money']='0.00',f['total_goods_price'] = orderprocess.data.infoDefault.orderAmount;
			f['total_order_money']=(parseFloat(f['total_goods_price'])+parseFloat(f['transFee'])-parseFloat(f['type_money'])-parseFloat(f['cashMoney'])).toFixed(2)
			$("#promote_fee_list > li", $("#statistiL")).find("input[type=radio]").each(function(){this.checked=false}),$("#promote_fee_list").find("a[t=cancel_promote_use]").each(function(){$(this).hide()}),$("input[name=Balance]").attr('checked', false),orderprocess.addressShowEditData(f),n.stopPropagation();
			/*if (!1 === G.logic.login.ifLogin(this, arguments)) return ! 1;
			var b = G.logic.login.getLoginUid();
			a.aid = f.attr("aid");
			b = G.util.token.addToken("http://" + G.DOMAIN.BASE_ICSON_COM + "/json.php?mod=address&act=modify&uid=" + b, "jq");
			G.util.post(b, a,
			function(b) {
				if (b && 0 == b.errno) {
					$.extend(G.app.orderprocess.data.address[a.aid], G.app.orderprocess.addressEncode(a));
					b = $(f);
					var c = b.attr("aid");
					b.data("editing", !1); - 1 != c && (b.html(Mustache.render(G.app.orderprocess.tpl.AddressItem, {
						addressItem: G.app.orderprocess.data.address[c]
					})), G.app.orderprocess.event.addressList(b, c), G.app.orderprocess.addressSelect(c, !0))*/
				// } else 
				
			// })
		}
	});
	$("input[t=add_save]", cT).click(function(n) {
		var a = orderprocess.addressGetEditData(cT);
		var o = orderprocess.tpl.item_addr;
		if (!1 !== a) {
			var liL = $("li", aL).length;
			if(liL >5){openNewMsgDiv('您只能保存5个有效地址。', 1);return false;}
			if($("#provinceId").val() && $("#cityId").val() && $("#districtId").val()){
				a['province'] = $("#provinceId").val();
				a['city'] = $("#cityId").val();
				a['district'] = $("#districtId").val();
			}
			
			
			xyzAjax({
				url:"/UserWS/addAddress.cus",
				data:{
					linkName:$("input[name='name']").val().trim(),
					linkPhone:$("input[name='mobile']").val().trim(),
					address:$("input[name='address']").val().trim()
				},
				success:function(data){
					if(data.status==1){
						var html='<li id="1" class="addressBox slt">';
						html+=' <input type="radio" name="address" id="userAddr_1" value="18462" class="addressinput">';
						html+='<label for="18462">';
						html+='<p><b>'+data.content.linkName+'</b><em>收</em><span class="editBt" t="editing" aid="18462">编缉</span></p>';
						html+='<p class="listAddressShow">河南省 焦作市 博爱县 </p>';
						html+='<p class="listAddressShow">'+data.content.address+'</p>';
						html+='<p class="listAddressShow">'+data.content.linkPhone+'</p><sub></sub>';
						html+='</label>';
						html+='</li>';
						$("#addressList").prepend(html);
						$(".newSlt").css("display","block");
						$("#cartTable").css("display","none");
					}else{
						top.$.messager.alert("警告",data.msg,"warning");
					}
				}
			});
			
		}
	});
	$("#btn_order").unbind("click").click(function(b) {
		
		var rows=eval(getCookie("JP_SHOPPING_CART"));
		var remark=$("#remark").html();
		var carts="";
		if(rows.length>0){
			for(var i=0;i<rows.length;i++){
				carts+=rows[i].numberCode+",";
			}
		}
		var address=$(".slt").attr("id");
		xyzAjax({
			url:"/OrderWS/addOrder.cus",
			data:{
				carts:carts,
				address:address,
				remark:remark
			},
			success:function(data){
				if(data.status==1){
					initShoppingCartCookie();
					window.location.href="gopay.html?orderNum="+data.content;
				}else{
					top.$.messager.alert("警告",data.msg,"warning");
				}
			}
		});
		
	});
};

orderprocess.usePreferentialInit = function(a){
	var sL = $("#statistiL");
	$("input[name=cartCashMoneyBtn]", $("#cash_default_box")).unbind("click").click(function(n) {
		var c = orderprocess.preferentialGetEditData('cash', "use_cashmanye_no use_cashmanye_pwd", sL);
		if (!1 !== c) {
			// console.log(orderprocess.statusGet('address'));
			/*var addr = orderprocess.statusGet('address'),promote = orderprocess.statusGet('promote'),surplus = orderprocess.statusGet('surplus'), cM = orderprocess.statusGet('cashMoney');
			openNewMsgDiv('正在提交订单...', 1);*/
			$.post(orderprocess.data.CART_URL,{a:'activat_my_cashmoney',myc:c, r:Math.random()},function(c){
				var c = eval("("+c+")");
				if(c.error!=0) {orderprocess.common.easyForm.show(c.content, $("input[name=use_cashmanye_no]", $("#cash_default_box")))}
				else{
					var o = c.content;
					orderprocess.init(),orderprocess.eventInit(),orderprocess.usePreferentialInit(),orderprocess.promoteInit(),orderprocess.addressGetTransFeeData(),orderprocess.addressGetPromoteFeeData(),orderprocess.addressGetSurplusFeeData(),orderprocess.surplusInit();
					var f = {}, addr = orderprocess.statusGet('address'), cM = orderprocess.statusGet('cashMoney');
					f['cancel_action']=true,f['transFee'] = addr.trans_fee,f['cashMoney'] = cM.cash_money,orderprocess.data.infoDefault.surplusMoney.totalS = orderprocess.data.infoDefault.surplusMoney.useS,f['type_money']='0.00',f['total_goods_price'] = cM.orderAmount;
					f['total_order_money']=(parseFloat(f['total_goods_price'])+parseFloat(f['transFee'])-parseFloat(f['type_money'])-parseFloat(f['cashMoney'])).toFixed(2)
					$("#promote_fee_list > li", $("#statistiL")).find("input[type=radio]").each(function(){this.checked=false}),$("#promote_fee_list").find("a[t=cancel_promote_use]").each(function(){$(this).hide()}),$("input[name=Balance]").attr('checked', false),orderprocess.addressShowEditData(f);
					o = o.replace(/{{&money}}/g, f['cashMoney']);
					o = o.replace(/{{&totalmoney}}/g, 88-parseFloat(f['cashMoney']));
					$("#cash_default_box").html(o);
					$("#cashcard_fee").parents("li").show(),n.stopPropagation();
					
				}
			});
			n.stopPropagation();
		}
	});
	$("input[name=cartGiftBtn]", $("#gift_default_box")).unbind("click").click(function(n) {
		var c = orderprocess.preferentialGetEditData('gift', "use_gift_no use_gift_pwd", sL);
		if (!1 !== c) {
			// console.log(orderprocess.statusGet('address'));
			/*var addr = orderprocess.statusGet('address'),promote = orderprocess.statusGet('promote'),surplus = orderprocess.statusGet('surplus'), cM = orderprocess.statusGet('cashMoney');
			openNewMsgDiv('正在提交订单...', 1);*/
			$.post(orderprocess.data.CART_URL,{a:'activat_my_giftcard',myc:c, r:Math.random()},function(c){
				var c = eval("("+c+")");
				if(c.error!=0) {orderprocess.common.easyForm.show(c.content, $("input[name=use_gift_no]", $("#gift_default_box")))}
				else{
					orderprocess.common.easyForm.show(c.content, $("input[name=use_gift_no]", $("#gift_default_box")));
					$("input[name=use_gift_no]").val($("input[name=use_gift_no]").val().replace(/^\d{9,10}$/, '请输入礼品卡号码'));
					$("input[name=use_gift_pwd]").val($("input[name=use_gift_pwd]").val().replace(/^\w*$/, '请输入礼品卡密码'));
					orderprocess.init(),orderprocess.eventInit(),orderprocess.usePreferentialInit(),orderprocess.promoteInit(),orderprocess.addressGetTransFeeData(),orderprocess.addressGetPromoteFeeData(),orderprocess.addressGetSurplusFeeData(),orderprocess.surplusInit();
					var f = {}, addr = orderprocess.statusGet('address'), cM = orderprocess.statusGet('cashMoney');

					f['cancel_action']=true,f['transFee'] = addr.trans_fee,f['cashMoney'] = cM.cash_money,orderprocess.data.infoDefault.surplusMoney.totalS = orderprocess.data.infoDefault.surplusMoney.useS,f['type_money']='0.00',f['total_goods_price'] = cM.orderAmount;
					f['total_order_money']=(parseFloat(f['total_goods_price'])+parseFloat(f['transFee'])-parseFloat(f['type_money'])-parseFloat(f['cashMoney'])).toFixed(2)
					$("#promote_fee_list > li", $("#statistiL")).find("input[type=radio]").each(function(){this.checked=false}),$("#promote_fee_list").find("a[t=cancel_promote_use]").each(function(){$(this).hide()}),$("input[name=Balance]").attr('checked', false),orderprocess.addressShowEditData(f),n.stopPropagation();
				}
			});
			n.stopPropagation();
		}
	});
}

orderprocess.eventInit = function(a){
	$("input[name=Balance]").attr('checked', false),$(this).find("input[type=radio]").attr("checked",false);
	$("#cash_default_box .cashCoupon2 .cashmanye_no").unbind("focus").focus(function() {
		$("#cash_default_box");
		if ("请输入现金券号码" == $(this).val()) {
			$(this).val("");
			try {
				$(this)[0].select()
			} catch(a) {}
		}
		$(".dd_mod_tip_info", $("#cash_default_box")).hide();
		$("#statistiL").each(function() {
			$(this).find("span.dd_mod_tip_info").css("display", "none")
		});
	}).blur(function() { /^\s*$/.test($(this).val()) && $(this).val("请输入现金券号码")
	});
	$("#cash_default_box .cashCoupon2 .cashmanye_pwd").unbind("focus").focus(function() {
		$("#cash_default_box");
		if ("请输入现金券密码" == $(this).val()) {
			$(this).val("");
			try {
				$(this)[0].select()
			} catch(a) {}
		}
		$(".dd_mod_tip_info", $("#cash_default_box")).hide();
		$("#statistiL").each(function() {
			$(this).find("span.dd_mod_tip_info").css("display", "none")
		});
	}).blur(function(n) { /^\s*$/ .test($(this).val()) && $(this).val("请输入现金券密码")
	});
	$("#gift_default_box .cashCoupon2 .gift_no").unbind("focus").focus(function() {
		$("#cash_default_box");
		if ("请输入礼品卡号码" == $(this).val()) {
			$(this).val("");
			try {
				$(this)[0].select()
			} catch(a) {}
		}
		$(".dd_mod_tip_info", $("#gift_default_box")).hide();
		$("#statistiL").each(function() {
			$(this).find("span.dd_mod_tip_info").css("display", "none")
		});
	}).blur(function() { /^\s*$/.test($(this).val()) && $(this).val("请输入礼品卡号码")
	});
	$("#gift_default_box .cashCoupon2 .gift_pwd").unbind("focus").focus(function() {
		$("#gift_default_box");
		if ("请输入礼品卡密码" == $(this).val()) {
			$(this).val("");
			try {
				$(this)[0].select()
			} catch(a) {}
		}
		$(".dd_mod_tip_info", $("#gift_default_box")).hide();
		$("#statistiL").each(function() {
			$(this).find("span.dd_mod_tip_info").css("display", "none")
		});
	}).blur(function(n) { /^\s*$/ .test($(this).val()) && $(this).val("请输入礼品卡密码")
	});
};

orderprocess.promoteInit = function (a) {
	var cT = $("#cartTable"), aL = $("#addressListBox"), sL = $("#statistiL");
	$("#promote_fee_list > li", sL).unbind("click").click(function(n) {
		var noa = orderprocess.statusGet("need_order_amount")
		if(noa!=0){
		$("a[t=cancel_promote_use]").hide(),$(this).find("a[t=cancel_promote_use]").show();
		$.ajax({type:'post', async: false, url:orderprocess.data.CART_URL,
			data:'a=my_use_promote&id='+$(this).find("input[name=statisti]").val()+'&'+(new Date).getTime(),dataType:'json',
			success:function(c){$(this).parent().find("em[t=promoteNotUseTips]").each(function(i){$(this).hide()}),orderprocess.statusSet("promote", c)}
		});
		$(this).parent().find("em[t=promoteNotUseTips]").each(function(i){$(this).hide()})
		var f = i = {}, b = orderprocess.statusGet("promote"), adr = orderprocess.statusGet('address'), sur = orderprocess.statusGet('surplus');
		$.isEmptyObject(sur)?f['surplusMoney']=0:f['surplusMoney'] = orderprocess.data.infoDefault.surplusMoney.useS;
		f['cancel_action']=false,f['transFee'] = adr.trans_fee,f['cashMoney'] = orderprocess.data.infoDefault.cashMoney,f['total_order_money']=(parseFloat(b.total_goods_price)+parseFloat(f['transFee'])-parseFloat(b.type_money)-parseFloat(f['cashMoney'])-parseFloat(f['surplusMoney'])).toFixed(2),i = $.extend(b, f),orderprocess.addressShowEditData(i),n.stopPropagation();
		}else{
			$(this).find("em[t=promoteNotUseTips]").show(),$(this).find("input[type=radio]").attr("checked",false);
		}
	});
	
	$("a[t=cancel_promote_use]").unbind("click").click(function(n) {
		var f = i = {}, b = orderprocess.statusGet("promote"), noa = orderprocess.statusGet("need_order_amount"), adr = orderprocess.statusGet('address'), sur = orderprocess.statusGet('surplus');
		if(noa!=0){
		$.isEmptyObject(sur)?f['surplusMoney']=0:f['surplusMoney'] = orderprocess.data.infoDefault.surplusMoney.useS;
		f['cancel_action']=true,f['transFee'] = adr.trans_fee,f['cashMoney'] = orderprocess.data.infoDefault.cashMoney,f['total_order_money']=(parseFloat(b.total_goods_price)+parseFloat(f['transFee'])-parseFloat(f['cashMoney'])-parseFloat(f['surplusMoney'])).toFixed(2),i = $.extend(b, f),orderprocess.addressShowEditData(i),orderprocess.statusSet("promote", {}),
		$("#promote_fee_list > li", sL).find("input[type=radio]").each(function(){this.checked=false}),$(this).hide(100),n.stopPropagation();
		}
	});
};

orderprocess.surplusInit = function (a) {
	var uB = $("#useBalance");
	$("input[name=Balance]", uB).unbind("click").click(function(n) {
		if($(this).is(":checked")){
			$(this).attr("disabled","disabled");
			$.ajax({type:'post', async: false, url:orderprocess.data.CART_URL,data:{a:'my_change_surplus',surplus:1,r:Math.random()},success:function(c){
				var c = eval("("+c+")");
				$("input[name=Balance]", uB).attr("disabled",false);
				if(c.error==0){
					$.each("totalS useS".split(" "), function(h, e){
						orderprocess.data.infoDefault.surplusMoney[e] = c.total[e];
					})
					var f = {}, b = orderprocess.statusGet("promote"), adr = orderprocess.statusGet('address');
					f['cancel_action']=false,f['transFee'] = adr.trans_fee,f['cashMoney'] = orderprocess.data.infoDefault.cashMoney;
					$.isEmptyObject(b)?(f['type_money']='0.00',f['total_goods_price'] = c.total['goods_price']):$.extend(f, b)
					var needFee = parseFloat(c.total['goods_price'])+parseFloat(f['transFee'])-parseFloat(f['cashMoney'])-parseFloat(f['type_money']);
					if(needFee < c.total['totalS']){
						orderprocess.data.infoDefault.surplusMoney['totalS'] = (parseFloat(c.total['totalS']) - needFee)>0?(parseFloat(c.total['totalS']) - needFee).toFixed(2):'0.00',orderprocess.data.infoDefault.surplusMoney['useS'] = (parseFloat(c.total['totalS'])).toFixed(2),f['total_order_money'] = 0;
					}else {
						orderprocess.data.infoDefault.surplusMoney['totalS'] = (needFee-parseFloat(c.total['totalS']))>0?'0.00':(needFee-parseFloat(c.total['totalS'])).toFixed(2),orderprocess.data.infoDefault.surplusMoney['useS'] = c.total['totalS'],f['total_order_money'] = (needFee-parseFloat(c.total['totalS'])).toFixed(2);
					}
					f['surplusMoney'] = parseFloat(orderprocess.data.infoDefault.surplusMoney.useS),orderprocess.statusSet('surplus', orderprocess.data.infoDefault.surplusMoney),orderprocess.statusSet('need_order_amount', f['total_order_money']),orderprocess.addressShowEditData(f);
				}else{
					openNewMsgDiv(c.content, 1);return false;
				}
			}});
			// $("a[t=cancel_promote_use]").hide(),$(this).find("a[t=cancel_promote_use]").show();
			// $.ajax({type:'post', async: false, url:orderprocess.data.CART_URL,
				// data:'a=my_use_promote&id='+$(this).find("input[name=statisti]").val()+'&'+(new Date).getTime(),dataType:'json',
				// success:function(c) orderprocess.statusSet("promote", c)
			// });
			// var f = i = {}, b = orderprocess.statusGet("promote")		
			// f['cancel_action']=false,f['transFee'] = orderprocess.data.infoDefault.transFee,f['cashMoney'] = orderprocess.data.infoDefault.cashMoney,f['surplusMoney'] = orderprocess.data.infoDefault.surplusMoney.useS,i = $.extend(b, f),orderprocess.addressShowEditData(i),
			//n.stopPropagation()
		}else{
			var f = {}, b = orderprocess.statusGet("promote"), adr = orderprocess.statusGet('address');
			$.isEmptyObject(b)?(f['type_money']='0.00',f['total_goods_price'] = orderprocess.data.infoDefault.orderAmount):$.extend(f, b)
			
			f['cancel_action']=false,f['transFee'] = adr.trans_fee,f['cashMoney'] = orderprocess.data.infoDefault.cashMoney,f['surplusMoney'] = parseFloat(orderprocess.data.infoDefault.surplusMoney.totalS);
			f['total_order_money'] = parseFloat(orderprocess.data.infoDefault.orderAmount)-parseFloat(f['type_money'])+parseFloat(f['transFee'])-parseFloat(f['cashMoney']).toFixed(2);
			orderprocess.data.infoDefault.surplusMoney.totalS=orderprocess.data.infoDefault.surplusMoney.useS,orderprocess.data.infoDefault.surplusMoney.useS=0,orderprocess.statusSet('surplus', {}),orderprocess.statusSet('need_order_amount', f['total_order_money']),orderprocess.addressShowEditData(f);
			
		}
		n.stopPropagation()
	});
	
	// $("a[t=cancel_promote_use]").unbind("click").click(function(n) {
		// var f = i = {}, b = orderprocess.statusGet("promote");
		// f['cancel_action']=true,f['transFee'] = orderprocess.data.infoDefault.transFee,f['cashMoney'] = orderprocess.data.infoDefault.cashMoney,f['surplusMoney'] = orderprocess.data.infoDefault.surplusMoney.useS,i = $.extend(b, f),orderprocess.addressShowEditData(i),orderprocess.statusSet("promote", null),
		// $("#promote_fee_list > li", uB).find("input[type=radio]").each(function(){this.checked=false}),$(this).hide(100),n.stopPropagation()
	// });
};

orderprocess.cartPost = function (url, callback) {
    var sc = document.createElement("script");
    sc.type = 'text/javascript';
    sc.async = true;
    sc.src = url;
    if (callback) {
        if (sc.onload) sc.onload = callback;
        else sc.onreadystatechange = callback;
    }
    document.body.appendChild(sc);
};

orderprocess.addressGetData = function(o, a) {
	$.ajax({type:'get', async: false, url:orderprocess.data.CART_URL,data:{a:'my_address',aid:encodeURIComponent(a),r:Math.random()},dataType:'jsonp',success:function(c){
		if(c.error==0){
			var a = {
				save: "edit_save",
				cancel: "edit_cancel"
			};
			$.extend(c,a);
			for(var f in c){
				o = o.replace("{{&"+f+"}}", c[f]);
			}
			orderprocess.data.address[c.aid]=c;
			orderprocess.data.address_data['edit_info']=o;
		}else return !1;
	}});
};

orderprocess.addressGetTransFeeData = function(o) {
	$.ajax({type:'get', async: false, url:orderprocess.data.CART_URL,data:{a:'my_TransFee',r:Math.random()},dataType:'json',success:function(c){orderprocess.data.infoDefault.transFee = c.order_fee, c['trans_fee'] = c.order_fee,orderprocess.statusSet('address', c);
	}});
};

orderprocess.addressGetPromoteFeeData = function(o) {
	$.ajax({type:'get', async: false, url:orderprocess.data.CART_URL,data:{a:'my_PromoteFee',r:Math.random()},dataType:'json',success:function(c){orderprocess.data.infoDefault.cashMoney = c.cash_money,orderprocess.statusSet('cashMoney', c),orderprocess.data.infoDefault.orderAmount = c.orderAmount;
	}});
	
};
orderprocess.addressGetSurplusFeeData = function(o) {
	$.ajax({type:'get', async: false, url:orderprocess.data.CART_URL,data:{a:'my_ok_surplus',r:Math.random()},dataType:'json',success:function(c){orderprocess.data.infoDefault.surplusMoney['totalS'] = c.your_surplus,orderprocess.data.infoDefault.surplusMoney['useS'] = c.your_surplus;
	}});
	
};

orderprocess.addressGetEditData = function(a) {
	var b = {};
	$.each("address name mobile".split(" "),
	function(c, e) {
		b[e] = $.trim($("input[name=" + e + "]", a).val())
	});
	var c = orderprocess.addressValidCheck(b);
	if (!0 !== c) return orderprocess.common.easyForm.show(c.msg, c.pos),
	!1;
	return b;
};

orderprocess.preferentialGetEditData = function(d, f, a) {
	//f = "address name mobile"
	var b = {};
	$.each(f.split(" "),
	function(c, e) {
		b[e] = $.trim($("input[name=" + e + "]", a).val())
	});
	if(d=='gift'){
		var c = orderprocess.preferentialGiftValidCheck(b);
	}else if(d=='cash'){
		var c = orderprocess.preferentialCashMoneyValidCheck(b);
	}
	if (!0 !== c) return orderprocess.common.easyForm.show(c.msg, c.pos),
	!1;
	return b;
};

orderprocess.addressShowEditData = function(a) {
	$.each("trans_fee promote_fee cashcard_fee order_money currentUserSur order_money2".split(" "),
	function(c, e) {
		if(a['cancel_action']){
			if(e=='trans_fee'){
				$("#" + e).html($.trim(a['transFee']));
			}else if(e=='promote_fee') {
				$("#" + e).html('0.00');
			}else if(e=='cashcard_fee') {
				$("#" + e).html(a['cashMoney']);
			}else if(e=='order_money' || e=='order_money2') {
				$("#" + e).html((parseFloat(a['total_order_money'])).toFixed(2));
			}
			else if(e=='currentUserSur') {
				$("#" + e).html($.trim(orderprocess.data.infoDefault.surplusMoney.totalS));
			}
		}else{
			if(e=='trans_fee'){
				$("#" + e).html($.trim(a['transFee']));
			}else if(e=='promote_fee') {
				$("#" + e).html($.trim(a['type_money']));
			}else if(e=='cashcard_fee') {
				$("#" + e).html(a['cashMoney']);
			}else if(e=='order_money' || e=='order_money2') {
				$("#" + e).html((parseFloat(a['total_order_money'])).toFixed(2));
			}
			else if(e=='currentUserSur') {
				$("#" + e).html($.trim(orderprocess.data.infoDefault.surplusMoney.totalS));
			}
		}
		//$("#" + e).html($.trim(a[e]))
	});
};

orderprocess.addressValidCheck = function(a) {
	var b = $("#cartTable");
	$.each(a,
	function(b, d) {
		a[b] = $.trim(d)
	});
	if (!a.name) return {
		msg: "收货人不能为空，请填写",
		pos: $("input[name=name]", b)
	};
	if (!$("#provinceId").val()) return {
		msg: "请选择收货省份",
		pos: $("#provinceId", b)
	};
	if (!$("#cityId").val()) return {
		msg: "请选择收货城市",
		pos: $("#cityId", b)
	};
	/*if (!$("#districtId").val()) return {
		msg: "请选择收货县/区",
		pos: $("#districtId", b)
	};*/
	if (20 < a.name.length) return {
		msg: "收货人不能超过10个汉字或者20个字母，请重新填写",
		pos: $("input[name=name]", b)
	};
	"如13612345678" == a.mobile && (a.mobile = "");
	if (!a.mobile) return {
		msg: "“手机”不能为空，请填写",
		pos: $("input[name=mobile]", b)
	};
	if(!(/^1[3|4|5|8][0-9]\d{8}$/.test(a.mobile))) return {
		msg: "手机号码填写有误，格式：13612345678，请重新填写",
		pos: $("input[name=mobile]", b)
	};
	if (!a.address) return {
		msg: "详细地址不能为空，请填写",
		pos: $("input[name=address]", b)
	};
	if (52 < a.address.length) return {
		msg: "详细地址不能超过26个汉字或者52个字母，请重新填写",
		pos: $("input[name=address]", b)
	};
	return ! 0;
};

orderprocess.preferentialGiftValidCheck = function(a) {
	var b = $("#statistiL");
	$.each(a,
	function(b, d) {
		a[b] = $.trim(d)
	});
	if (!a.use_gift_no) return {
		msg: "礼品卡号码不能为空，请填写",
		pos: $("input[name=use_gift_no]", b)
	};
	if (10 < a.use_gift_no.length) return {
		msg: "礼品卡号码不能超过10个数字，请重新填写",
		pos: $("input[name=use_gift_no]", b)
	};
	"请输入礼品卡号码" == a.use_gift_no && (a.use_gift_no = "");
	if (!a.use_gift_no) return {
		msg: "礼品卡号码不能为空，请填写",
		pos: $("input[name=use_gift_no]", b)
	};
	if(!(/^\d{10}$/.test(a.use_gift_no))) return {
		msg: "礼品卡号码填写有误，格式：5001564811，请重新填写",
		pos: $("input[name=use_gift_no]", b)
	};
	if (!a.use_gift_pwd) return {
		msg: "礼品卡密码不能为空，请填写",
		pos: $("input[name=use_gift_pwd]", b)
	};
	"请输入礼品卡密码" == a.use_gift_pwd && (a.use_gift_pwd = "");
	if (!a.use_gift_pwd) return {
		msg: "礼品卡密码不能为空，请填写",
		pos: $("input[name=use_gift_pwd]", b)
	};
	return ! 0;
};
orderprocess.preferentialCashMoneyValidCheck = function(a) {
	var b = $("#statistiL");
	$.each(a,
	function(b, d) {
		a[b] = $.trim(d)
	});

	if (!a.use_cashmanye_no) return {
		msg: "现金券号码不能为空，请填写",
		pos: $("input[name=use_cashmanye_no]", b)
	};
	if (10 < a.use_cashmanye_no.length) return {
		msg: "现金券号码不能超过10个数字，请重新填写",
		pos: $("input[name=use_cashmanye_no]", b)
	};
	"请输入现金券号码" == a.use_cashmanye_no && (a.use_cashmanye_no = "");
	if (!a.use_cashmanye_no) return {
		msg: "现金券号码不能为空，请填写",
		pos: $("input[name=use_cashmanye_no]", b)
	};
	if(!(/^TC88\d{6}$/.test(a.use_cashmanye_no))) return {
		msg: "现金券号码填写有误，格式：TC88888888，请重新填写",
		pos: $("input[name=use_cashmanye_no]", b)
	};
	if (!a.use_cashmanye_pwd) return {
		msg: "现金券密码不能为空，请填写",
		pos: $("input[name=use_cashmanye_pwd]", b)
	};
	"请输入现金券密码" == a.use_cashmanye_pwd && (a.use_cashmanye_pwd = "");
	if (!a.use_cashmanye_pwd) return {
		msg: "现金券密码不能为空，请填写",
		pos: $("input[name=use_cashmanye_pwd]", b)
	};
	return ! 0;
};

orderprocess.common = {
	easyForm: {
		show: function(a, b) {
			this.hide(b);
			$(b).parents("dd").append('<span class="dd_mod_tip_simple dd_mod_tip_info"><i class="ico_dd_info">&nbsp;</i>' + a + "</span>");
			$(b).data("bind_blur") || ($(b).data("bind_blur", !0), $(b).blur(function() {
				orderprocess.common.easyForm.hide(b);
			}))
		},
		hide: function(a) {
			$("span.dd_mod_tip_simple").remove();
		}
	}
};


orderprocess.statusSet = function(a, b) {
	orderprocess.status[a] = b;
};
orderprocess.statusGet = function(a) {
	return orderprocess.status[a];
};
orderprocess.statusReset = function(a) {
	a in orderprocess.status && delete orderprocess.status[a];
};