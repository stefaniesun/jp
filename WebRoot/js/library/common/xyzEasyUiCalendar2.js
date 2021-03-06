/**
 * 参数说明
 1、id  容器唯一标示
 2、data  数据 orderTkviewStock
 3、isShowOldDate 是否显示当前日期以前的库存价格  默认不显示   内部使用需要设置成true 用于删除之前的库存或者看之前的库存
 4、functionClick  点击事件，参数date 返回点击的日期  
 5、functionValidator 验证事件 ，参数date 
 * */

/**
 * 为calendar增加自定义事件
 * 此处有对源码进行增加代码
 * 请在源码查找关键字 '//X'
 */
/**
 * 事件参数说明
 * cal calendar对象本身
 * year （number）当前显示的年
 * month （number）当前显示的月
 */
function xyzGetCalendar(xyzData){
	//数据
	var orderTkviewStock = xyzData.data;
	var currerentDate = new Date().Format("yyyy-MM-dd");
	//是否在日历上展示库存价格，库存默认展示，价格默认不展示
	var isShwoStock = "";
	var isShwoPrice = "";

	if(!xyzIsNull(xyzData.isShwoStock)){
		isShwoStock = xyzData.isShwoStock;
	}else{
		isShwoStock = true;
	}
	if(!xyzIsNull(xyzData.isShwoPrice)){
		isShwoPrice = xyzData.isShwoPrice;
	}else{
		isShwoPrice = false;
	}
	//是否显示当天之前的库存价格
	var isShowOldDate = "";
	if(!xyzIsNull(xyzData.isShowOldDate)){
		isShowOldDate = xyzData.isShowOldDate;
	}else{
		isShowOldDate = false;
	}
	$('#'+xyzData.id).calendar({
		//firstDay : 1,
		//current : new Date(),
		months : ["1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"],
		weeks:["日","一","二","三","四","五","六"],
		formatter: function(date){
			//Date 为了解决啃爹的火狐
			date = date.Format("yyyy-MM-dd");
			var tempDate = new Date(date.replace(/-/g,"/"));
			var result ="<span style='color:#333;float:left;'>";
			if(isShowOldDate){
				for(var indexP in orderTkviewStock){
					if(date== (orderTkviewStock[indexP].dateInfo).substring(0,10)){
						result += tempDate.getDate()+"</span><br/>";
						if(isShwoPrice==true){
							result += "<input type='hidden' id='"+tempDate.getTime()+"_price' value='"+orderTkviewStock[indexP].price+"'/>";
							result += "<span  style='color:#ff6000;float:left;font-size:12px;'>￥"+orderTkviewStock[indexP].price+"</span><br/>";
						}
						if(isShwoStock==true){
							result += "<input type='hidden' id='"+tempDate.getTime()+"_stock' value='"+orderTkviewStock[indexP].count+"'/>";
							result += "<span  style='color:#ff6000;float:left;font-size:12px;'>"+orderTkviewStock[indexP].count+"/"+(
									orderTkviewStock[indexP].count+orderTkviewStock[indexP].useCount+orderTkviewStock[indexP].stockTempCount
							)+"</span>";
						}
						return result;
					}
				}
			}else{
				if(date>=currerentDate){
					for(var indexP in orderTkviewStock){
						if(date == (orderTkviewStock[indexP].dateInfo).substring(0,10)){
							result += tempDate.getDate()+"</span><br/>";
							if(isShwoPrice==true){
								result += "<input type='hidden' id='"+tempDate.getTime()+"_price' value='"+orderTkviewStock[indexP].price+"'/>";
								result += "<span  style='color:#ff6000;float:left;font-size:12px;'>￥"+orderTkviewStock[indexP].price+"</span><br/>";
							}
							if(isShwoStock==true){
								result += "<input type='hidden' id='"+tempDate.getTime()+"_stock' value='"+orderTkviewStock[indexP].count+"'/>";
								result += "<span  style='color:#ff6000;float:left;font-size:12px;'>"+orderTkviewStock[indexP].count+"/"+(
									orderTkviewStock[indexP].count+orderTkviewStock[indexP].useCount+orderTkviewStock[indexP].stockTempCount
								)+"</span>";
							}
							return result;
						}
					}
				}
			}
			return result + tempDate.getDate()+"</span><br/><br/><br/>";
		},
		onSelect: function(date){
			xyzData["functionClick"](date);
		},
		validator: function(date){
			var falg = xyzData["functionValidator"](date);
			if(falg){
				return true;
			}else{
				return false;
			}
		},
	});
}


