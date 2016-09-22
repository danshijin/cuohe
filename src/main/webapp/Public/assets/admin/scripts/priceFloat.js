/*======卖盘池========
 *author:曾世华
 *date:2015/07/29
 *=====================*/
var PriceFloat = {
		
	// 获取实时价格
	getPriceProduct : function() {
		if($("#itemName").length <= 0){
			return;
		}
		$.ajax({
			type : "GET",
			url : BASE_URL + "/dealMake/getPriceProduct.do",
			dataType : "json",
			success : function(res) {
				
				if(res.code=="succ"){
					if(res.avg){
						$('#avg').text(res.avg);
					}
					
					if(res.price){
						var price = res.price;
						$('#price').text(res.price);
						var cookiePrice = getCookie("price");
						var float=0;
						if(cookiePrice){
							float=(((price-cookiePrice)/cookiePrice)*100).toFixed(2);
						}
						if(float>1){
							$('#float').html(float+"%"+'<i class="fa fa-arrow-up" style="color: red"></i>&nbsp;&nbsp;');
							$('#remind1').attr("style","display:''");
							$('#remind2').attr("style","display:''");
							$('#remind2').text("15秒内上涨超过"+float+"%");
						}else if(float<-1){
							$('#float').html(float+"%"+'<i class="fa fa-arrow-down" style="color: red"></i>&nbsp;&nbsp;');
							$('#remind1').attr("style","display:''");
							$('#remind2').attr("style","display:''");
							$('#remind2').text("15秒内下跌超过"+float+"%");
						}else{
							$('#float').html(float+"%"+'&nbsp;&nbsp;');
							$('#remind1').attr("style","display:none");
							$('#remind2').attr("style","display:none");
							
						}
						setCookie("price",price);
					}
					
					if(res.itemName){
						$('#itemName').text(res.itemName);
					}
					
				}else if(res.code=="login"){
					alert("请先登录");
				}
				$('#updateTime').text(res.updateTime);
				
			}
		});
	}
};

