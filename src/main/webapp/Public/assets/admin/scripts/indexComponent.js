	
/**
 * 首页组件操作
 */
var IndexComponent = function() {

	var webParts=$("#webParts").val();
	
	
	

	
	/*==============组织记录开始=====================*/
	/**
	 * 修改 or 新增
	 * 
	 */
	var addNotesOrgan = function() {
		
		$('#notes').blur(function() {
			
			var context = $.trim($('#notes').val());

			if(context=="") return false;
			$.ajax({
				type : "POST",
				url : BASE_URL + "/notes/addnotesByOrgan.do",
				data:{
					"context":context
				},
				dataType : "json",
				success : function(res) {
					//提示
					if(res.msg != ""){
						CommonUtils.notific8('succ',res.msg);
					}
				}
			});
		});
		
		
	};

	/**
	 * 组织记录
	 * 初始化
	 */
	var notesByOrgan = function() {

		$.ajax({
			type : "POST",
			url : BASE_URL + "/notes/notesByOrgan.do",
			dataType : "json",
			success : function(res) {
				if(res.notes.context){
					$("#notes").val(res.notes.context);
					if(res.editFlag=="yes"){
						$("#date-picker").attr("data-date",res.nowDate);
					}
					
				}else{
					$("#notes").val("");
				}
			}
		});

	};
	/*==============组织记录结束=====================*/
	
	
	/*==============个人便签开始=====================*/
	/**
	 * 个人便签
	 * 初始化
	 */
	var notesByMe = function() {
		$.ajax({
			type : "POST",
			url : BASE_URL + "/notes/notesByMe.do",
			dataType : "json",
			success : function(res) {
				if(res.notes.context){
					$("#myNotes").val(res.notes.context);
				}else{
					$("#myNotes").val("");
				}
				if(res.notes.id){
					$("#myNotesId").val(res.notes.id);
				}
				
			}
		});

	};
	/**
	 * 修改
	 */
	var addNotesMe = function() {
		
		$('#myNotes').blur(function() {
			
			var context = $('#myNotes').val().trim();
			var myNotesId = $('#myNotesId').val();

			if(context=="") return false;
			
			$.ajax({
				type : "POST",
				url : BASE_URL + "/notes/addnotesByMe.do",
				data:{
					"context":context,
					"id":myNotesId
				},
				dataType : "json",
				success : function(res) {
					CommonUtils.notific8('succ',res.msg);
				}
			});
		});
	};
	/*==============个人便签结束=====================*/
	/**
	 * 最近走势
	 */
	var recentlyTrend = function() {
		var ysId = $("#ysId").val();
		$.ajax({
			type : "POST",
			url : BASE_URL + "/notes/recentlyTrend.do",
			dataType : "json",
			success : function(res) {
				if(ysId=="0"){
				
				var qw=new Array();
				if(res.seriesVal){
					qw=JSON.parse(res.seriesVal);
				}
				var itemName="";
				if(res.itemName){
					var iName=res.itemName.split("");
					for(var i=0;i<iName.length;i++){
						itemName+=iName[i];
					}
				}
				//
				$('#container1').highcharts({
			    	chart: {
			            type: 'spline',
			            marginTop: 30,
			        },
					plotOptions: {  
			            spline: {  
			                lineWidth: 1.5,  
			                marker: {  
			                    enabled: false,  
			                    states: {  
			                        hover: {  
			                            enabled: false,  
			                        }  
			                    }  
			                },  
			                shadow: false
			                
			            }
			        },  
			        title: {
			            text: itemName+""+'最近半年趋势图(%)',
			            align:'center'
			        },
			        xAxis: {
			            type: 'datetime',
			            labels: {  
			               step: 1,   
			               formatter: function () {  
			                 //  return Highcharts.dateFormat('%Y-%m-%d', this.value);
			                   return Highcharts.dateFormat('%m-%d', this.value);
			               }
			           },
			           endOnTick: true,
			            showFirstLabel: true,
			            showLastLabel:true,
			            startOnTick: true,
			            min:res.minDate,
			            max:res.maxDate
			        },
			        yAxis: {
			            tickInterval:3, // 刻度值  
			            title: {
			                text: '',
			                rotation:0,
			                y: -90,
			                offset:50
			            },
			            plotLines: [{
			                value: 0,
			                width: 1,
			                color: '#808080'
			            }],
			            labels: {
			                enabled: false
			              
			            }
			            
			        },
			        tooltip: {
			            enabled: true,
			            formatter: function() {
				         	var s = [40350];
				         	var a = [res.itemName];
				         	var k = a.indexOf(this.series.name);
				        	var p = s[k];
							//if(this.x == res.maxDate){
								return this.series.name+":￥"+Math.floor(this.y/100*p+p)+'元/吨 '+'<br/>日期:'+Highcharts.dateFormat('%Y-%m-%d', this.x);
							//}else{
							//	return false;
							//}
				         } 
			        },
			        
			        legend: {
			            layout: 'vertical',
			            align: 'right',
			            verticalAlign: 'middle',
			            borderWidth: 0,
			            itemStyle: {
			                fontWeight: 'default'
			            }
			        },
			        series: [{
			            name: res.itemName,
				        data:qw,
			            color:"#d7000f",
			            visible:true
			        }]
			    });
			}else if(ysId=="1"){
				var str ="<table   class='table table-striped table-bordered table-advance table-hover ttTbl' ><thead><tr><th id='lastBrand'> 品名</th><th> 价格</th><th> 日期</th></tr></thead><tbody>";
				var qw=new Array();
				qw=JSON.parse(res.seriesVal);
				for(var i=qw.length-1;i>qw.length-5;i--){
					var temp = qw[i]+"";
					str+="<tr><td>"+res.itemName+"</td><td>"+Math.floor(temp.split(",")[1]/100*40350+40350)+"</td><td>"+Highcharts.dateFormat('%Y-%m-%d', temp.split(",")[0]) +"</td></tr>";
					
				}
				str+="</tbody></table>";
				$("#container1").html(str);
			}
		}
		
		});
		
		
		
	};
	var component=function(){

		if (webParts) {
			var webPart= new Array();
			webPart = webParts.split(",");
			for (var i = 0; i < webPart.length; i++) {

				switch (webPart[i]) {

				case "1":
					// 执行最近最近走势函数
					recentlyTrend();
					// 显示
					$("#recentlyTrend").removeAttr("style");
					break;
				case "2":
					customer();//最新加入客户
					$("#customer").removeAttr("style");
					break;
				case "3":
					//最新卖盘
					sellPool();
					$("#sellPool").removeAttr("style");
					break;
				case "4":
					//最新买盘
					buyPool();
					$("#buyPool").removeAttr("style");
					break;
				case "5":
					//个人便签
					notesByMe();
					$("#notesByMe").removeAttr("style");
					break;
				case "6":
					//推荐采购商
					reminCustom();
					$("#remindCustom").removeAttr("style");
					break;
				}
			}
		}
//		else{
//			alert("加载组件失败");
//		}
	};
	/**
	 * 最新加入公司客户
	 */
	var customer = function() {
		
		var str="<tr><td>##id##</td><td>##conpanyName##</td><td>##entTypes##</td><td>##salesVolume##</td>" +
				"<td>##buyBrand##</td><td>##nameByUser##</td><td><a href='##getCustom##' class='btn btn-xs blue'><i class='fa fa fa-search'></i>查看</a>&nbsp;</td></tr>";
		$.ajax({
			type : "POST",
			url : BASE_URL + "/notes/getCustomsList.do",
			dataType : "json",
			success : function(res) {
				var tmp=""; 
				var i=0;
				for(var customsPOJO in res.plist){
					i++;
					if(res.plist[customsPOJO].buyBrand==null){
						res.plist[customsPOJO].buyBrand="";
					}
					var beand=res.plist[customsPOJO].buyBrand;
					if(beand.length > 6){
						beand=beand.substring(0,5)+"...";
					}
					var types=res.plist[customsPOJO].entTypes;
					var arr = [];
					arr=types.split(",");
					types=arr[0];
					tmp=tmp+str.replace("##id##", i).replace("##conpanyName##", res.plist[customsPOJO].conpanyName).replace("##entTypes##",types)
					.replace("##salesVolume##", res.plist[customsPOJO].salesVolume).replace("##buyBrand##", beand)
					.replace("##nameByUser##", res.plist[customsPOJO].nameByUser).replace("##getCustom##", BASE_URL +"/customerDetail/index.do?id="+res.plist[customsPOJO].id+"&initPage=companyInfo");
				}
				$("#customs").append(tmp);
			}
		});
	};

	/**
	 * 
	 * 最新卖盘
	 */
	var sellPool = function() {
		var sta=false;
		$.ajax({
			type : "POST",
			url : BASE_URL + "/notes/getSellPoolList.do",
			dataType : "json",
			success : function(res) {
				var tmp=""; 
				for(var SellPoolPOJO in res.slist){
					if(res.slist[SellPoolPOJO].itemAttrName !=null){
						$("#lastBrand").after("<th>"+res.slist[SellPoolPOJO].itemAttrName+"</th>");
						sta=true;
						break;
					}
				}
				var i=0;
				for(var SellPoolPOJO in res.slist){
					i++;
					tmp=tmp+"<tr><td>"+i+"</td><td>"+res.slist[SellPoolPOJO].commodityName+"</td>";
					if(sta){
						tmp=tmp+"<td>"+res.slist[SellPoolPOJO].itemAttrOptions+"</td>";
					}
					tmp=tmp+"<td>"+res.slist[SellPoolPOJO].price+"</td><td>"+res.slist[SellPoolPOJO].quantity+"</td>" +
							"<td>"+res.slist[SellPoolPOJO].wareName+"</td><td>"+res.slist[SellPoolPOJO].companyName+"</td>"+
							"<td><a href='"+BASE_URL+"/buypool/toaddsellorder.do?sid="+res.slist[SellPoolPOJO].id+"' class='btn btn-xs green'><i class='fa fa-edit'></i> 查看并生成订单</a> &nbsp;</td>"+"</tr>";
				}
				$("#contextList").append(tmp);
			}
		});
	};

	/**
	 * 最新买盘
	 */
	var buyPool = function() {
		
		var str="<div class='entrust'>" +
		"<span>##title##</span><em>##context##</em><em><a href='##getBuyPool##' class='btn btn-xs blue'><i class='fa fa fa-search'></i>查看</a>&nbsp;"
		+"<a href='##goBuyPool##' class='btn btn-xs green'><i class='fa fa-edit'></i> 生成订单</a> &nbsp;&nbsp;" +
			"<i class='fa fa-user'></i>##account##&nbsp;<i class='fa fa-clock-o'></i>##createdAt##</em><hr id='hr'></hr></div>";
		$.ajax({
			type : "POST",
			url : BASE_URL + "/notes/getBuyPoolList.do",
			dataType : "json",
			success : function(res) {
				var tmp=""; 
				var i=0;
				for(var BuyPoolPOJO in res.blist){
					tmp=tmp+str.replace("##getBuyPool##", BASE_URL +"/notes/getBuyPool.do?bid="+res.blist[BuyPoolPOJO].bid).replace("##goBuyPool##", BASE_URL+"/buypool/toaddbuyorder.do?ids="+res.blist[BuyPoolPOJO].bid)
					.replace("##title##",res.blist[BuyPoolPOJO].title )
					.replace("##context##", res.blist[BuyPoolPOJO].context).replace("##account##", res.blist[BuyPoolPOJO].account)
					.replace("##createdAt##",res.blist[BuyPoolPOJO].createdAt);
//					alert(tmp);
				}
				$("#region_statistics_content").append(tmp);
			}
		});
	};

	return {

		init : function() {
				addNotesOrgan();
				addNotesMe();
				notesByOrgan();
				component();
		},	
		callRecentlyTrend : function(){
			recentlyTrend();
		}
	};

}();

function queryNotes(queryDate) {

	$.ajax({
		type: "GET",
		url: BASE_URL + "/notes/notesByOrgan.do",
		data: {
			"noteDate":queryDate
		},
		dataType : "json",
		success : function(res) {
			
			if(res.editFlag=="yes"){
				$("#notes").removeAttr("disabled");
			}else{
				$("#notes").attr("disabled","disabled");
			}
			
			if(res.notes.context){
				$("#notes").val(res.notes.context);
				
			}else{
				$("#notes").val("");
			}
		}
	});
}

function addOrdelWebparts(status,wpartsId,divId){
	$.ajax({
		type : "POST",
		url : BASE_URL + "/notes/addOrDelWpById.do",
		data:{
			"wpartsId":wpartsId,
			"status":status
		},
		dataType : "json",
		success : function(res) {
			window.location.href=BASE_URL + "/user/loginSucc.do";
		}
	});
		
}



/**
 *推荐采购商
 */
var reminCustom = function() {
	var sta=false;

	$.ajax({
		type : "POST",
		url : BASE_URL + "/notes/geTreminCustom.do",
		dataType : "json",
		success : function(res) {
			var tmp=""; 
			for(var RemindCustomerEntity in res.culist){
				var salesVolume = (res.culist[RemindCustomerEntity].salesVolumeName != null)?res.culist[RemindCustomerEntity].salesVolumeName:"";
				var buybrand = (res.culist[RemindCustomerEntity].buybrand != null)?res.culist[RemindCustomerEntity].buybrand:"";
				tmp=tmp+"<tr><td>"+res.culist[RemindCustomerEntity].companyName+"</td>";
			
				tmp=tmp+"<td>"+res.culist[RemindCustomerEntity].categoryfreqName+"</td>";
			
				tmp=tmp+"<td>"+salesVolume+"</td><td>"+buybrand+"</td>" +
						"<td><a href='"+BASE_URL+"/buypool/toaddPurchasebuyorder.do?ids="+res.culist[RemindCustomerEntity].customerId+"' " +
						"class='btn btn-xs green'><i class='fa fa-edit'></i> 生成订单</a>" +
						"<a href='javascript:tiaoguo("+res.culist[RemindCustomerEntity].customerId+");' class='btn btn-xs green'><i class='fa fa-edit'></i> 跳过</a> &nbsp;&nbsp;</td>" +
						"<td>"+res.culist[RemindCustomerEntity].picName+"</td></tr>";
			}
			$("#remindCustomList").html(tmp);
		}
	});
};

function  tiaoguo(customerId){
	  CommonUtils.editModal(BASE_URL+"/notes/toTaoguo.do?customerId="+customerId,600);
}

