var start = 0;
var type=0;
var len=9999;
var total=0;
var strWhere = "";
var titleArr = [];
var nameArr = [];
var delTd=false;
var checkArr = new Dictionary();

$(document).ready(function() {
	initData();
});

function sortArray(data){
	
	var head = [];
	var body = [];
	var foot = [];
	for(var i = 0 ; i < data.length; i++){
		if(data[i]['isClose'] == 1){
			foot.push(data[i]);
		} else if (data[i]["isConfirm"]==1) {
			head.push(data[i]);
		} else {
			body.push(data[i]);
		}
	}
	return head.concat(body).concat(foot);
}

function initData(){
	var searchParam = {};
	searchParam.start = start;
	searchParam.len = len;
	searchParam.strWhere = strWhere;
	searchParam.attribute = $("#attributeHidden").val();
	searchParam.operator = $("#operatorHidden").val();
	//alert($("#contentHidden").val());
	searchParam.content = $("#contentHidden").val();   
	searchParam.type=type;
	$.getJSON(actionUrl, searchParam,function(data){
		total= data.total;
		
		
		if(total <= 0){
			$("#grid").html('');
			$(".dataDiv").before('<div class="ajaxNoData" style="width:'+$(".dataDiv").width()+'px; height:'+$(".dataDiv").height()+'px; position:absolute; background-color:white;"><div style="text-align: center;">没有数据</div></div>');
			return;
		} else {
			$('.ajaxNoData').remove();
		}
		//alert(data.type);
		$("#type").val(data.type);
		titleArr = initTitleArr.concat();
		nameArr = initNameArr.concat();
		
		var attrView = data.attrView;
		for(var i = 0 ; i < attrView.length; i++){
			
			if(attrView[i].mainProperty == 1){
				
				titleArr.splice(2, 0, attrView[i].name);
				
				nameArr.splice(2,0, "___" + attrView[i].name);
			}
		}
		
		generateTable(sortArray(data.rows));
		if(typeof callback == 'function'){
			callback();
		}
		bindEvent();
		setTimeout("initData()", 15 * 1000);
	});
}



function generateTable(data){
	var html = "<thead><th><input type='checkbox' id='chkAll' onclick='chkAll()'></th>";
	for(var i =0;i < titleArr.length; i++){
		html+="<th>"+titleArr[i]+"</th>";
	}
	if(delTd==true){
		html+="<th>&nbsp;</th>";
	}
	html+="</thead><tbody>";
	for(var i = 0 ; i < data.length; i++){
		var id = data[i].id;
		//alert(data[i]["isClose"]);
		if(data[i]['isConfirm'] == 1 && data[i]["isClose"] == 0){
			//alert("变绿色");
			//刷新，以前选中的值
			
			html+="<tr class='trDeal'>";
			//卖盘
			if (data[i]["poolType"] == 1) {
				html+="<td  data_id='"+data[i]["id"]+"' data_index='" + i + "' data_name='"+data[i]["companyName"]+"'  title='第" + (start + i +1 ) +"条' >"+ "<input type='checkbox' classValue='sellPool'  class='checkboxes' name='checkboxes' onclick='check(this);' checkid='"+data[i]["id"]+"' poolType='"+data[i]["poolType"]+"' value='"+data[i]["id"]+"|"+data[i]['poolType']+"|"+data[i]['counterNum']+"'>" +"</td>";
			}
			//买盘
			else {
				html+="<td  data_id='"+data[i]["id"]+"' data_index='" + i + "' data_name='"+data[i]["companyName"]+"'  title='第" + (start + i +1 ) +"条' >"+ "<input type='checkbox' classValue='buyPool'  class='checkboxes' name='checkboxes' onclick='check(this);' checkid='"+data[i]["id"]+"' poolType='"+data[i]["poolType"]+"' value='"+data[i]["id"]+"|"+data[i]['poolType']+"|"+data[i]['counterNum']+"'>" +"</td>";
			}
			
		} else if(data[i]["isClose"] == 1){
			//变灰色的时候，checked不可选
			//alert("变灰色");
			html+="<tr class='trClose'>";
			//卖盘
			if (data[i]["poolType"] == 1) {
				html+="<td  data_id='"+data[i]["id"]+"' data_index='" + i + "' data_name='"+data[i]["companyName"]+"'  title='第" + (start + i +1 ) +"条' >"+ "<input type='checkbox' classValue='sellPool'  disabled='true' class='checkboxes' name='checkboxes' onclick='check(this);' checkid='"+data[i]["id"]+"' poolType='"+data[i]["poolType"]+"' value='"+data[i]["id"]+"|"+data[i]['poolType']+"|"+data[i]['counterNum']+"'>" +"</td>";
			}
			//买盘
			else {
				html+="<td  data_id='"+data[i]["id"]+"' data_index='" + i + "' data_name='"+data[i]["companyName"]+"'  title='第" + (start + i +1 ) +"条' >"+ "<input type='checkbox' classValue='buyPool'  disabled='true' class='checkboxes' name='checkboxes' onclick='check(this);' checkid='"+data[i]["id"]+"' poolType='"+data[i]["poolType"]+"' value='"+data[i]["id"]+"|"+data[i]['poolType']+"|"+data[i]['counterNum']+"'>" +"</td>";
			}
			
		}else{
//			var xuanzhong = $("#xuanzhong").val();
//			alert(xuanzhong);
//			if (xuanzhong != "") {
//				var checkeds = xuanzhong.split(","); 
//				alert(checkeds);
//			}
			html+="<tr>";
			//卖盘
			if (data[i]["poolType"] == 1) {
				html+="<td  data_id='"+data[i]["id"]+"' data_index='" + i + "' data_name='"+data[i]["companyName"]+"'  title='第" + (start + i +1 ) +"条' >"+ "<input type='checkbox' classValue='sellPool' class='checkboxes' name='checkboxes' onclick='check(this);' checkid='"+data[i]["id"]+"' poolType='"+data[i]["poolType"]+"' value='"+data[i]["id"]+"|"+data[i]['poolType']+"|"+data[i]['counterNum']+"'>" +"</td>";
			}
			//买盘
			else {
				html+="<td  data_id='"+data[i]["id"]+"' data_index='" + i + "' data_name='"+data[i]["companyName"]+"'  title='第" + (start + i +1 ) +"条' >"+ "<input type='checkbox' classValue='buyPool'  class='checkboxes' name='checkboxes' onclick='check(this);' checkid='"+data[i]["id"]+"' poolType='"+data[i]["poolType"]+"' value='"+data[i]["id"]+"|"+data[i]['poolType']+"|"+data[i]['counterNum']+"'>" +"</td>";
			}
			
		}
		//html+="<td data_id='"+data[i]["id"]+"' data_index='" + i + "' data_name='"+data[i]["companyName"]+"'  title='第" + (start + i +1 ) +"条' >"+ "<input type='checkbox' class='checkboxes' name='checkboxes' onclick='check(this);' checkid='"+data[i]["id"]+"' poolType='"+data[i]["poolType"]+"' value='"+data[i]["id"]+"|"+data[i]['poolType']+"|"+data[i]['counterNum']+"'>" +"</td>";

		for(var j = 0; j < nameArr.length; j++){
			
			if(nameArr[j].indexOf("___") == 0){
				var attrName = nameArr[j].substr(3);
				var value = "";
				for(var k =0; k < data[i]["attr"].length; k++){
					
					if(data[i]["attr"][k]["attrName"] == attrName){
						value = data[i]["attr"][k]["attrValue"];
						break;
					}
				}
				html+="<td>"+value+"</td>";
			} else if (nameArr[j].indexOf("fun_") == 0){
				var value = "";
				if(nameArr[j] == "fun_companyName"){
					value = getCompanyNameTd(data[i]["contacters"], data[i]["companyName"]);
				}
				html+=value;
			} else if (nameArr[j].indexOf("time_") == 0){
				var value = "";
				if(nameArr[j] == "time_publishTime"){
					value = getFormatTime(data[i]["publishTime"]);
				} else if(nameArr[j] == "time_lastTime"){
					value = getFormatTime(data[i]["lastTime"]);
				}
				
				html+="<td>"+value+"</td>";
			} else if(data[i][nameArr[j]]==undefined){
				html+="<td>&nbsp;</td>";
			} else{
				html+="<td>"+data[i][nameArr[j]]+"</td>";
			}
		}
		
		if(data[i]["isClose"] == 1){
			if(data[i]["orderId"]){
				html += "<td> <a class='btn_red50'  href='javascript:viewOrder("+data[i]['id']+", "+data[i]['poolType']+", "+data[i]['orderId']+")'>查看订单</a>";
			}else{
				html += "<td><a class='btn_red50'  href='#'></a>";
			}
		}else if(data[i]['status'] == 0){
			html += "<td><a class='btn_red50'  href='javascript:getCounterList("+data[i]['id']+", "+data[i]['poolType']+")'>查看还盘</a>" 
				+"&nbsp;&nbsp;<a class='btn_red50'  href='javascript:modifyOfferInfo("+data[i]['id']+", "+data[i]['poolType']+")'>修改报盘</a>";
				//【撮合交易管理】界面每条报盘后增加“发送到卖盘池”的按钮,业务逻辑按附件处理
			if(data[i]['poolType'] == 1){
				if (data[i]['createSource'] == 1) {
					html +="&nbsp;&nbsp;<a class='btn_red50'  href='javascript:publishToSellPool("+data[i]['id']+", "+data[i]['poolType']+","+data[i]['createSource']+")'>发送到卖盘池</a>&nbsp;&nbsp;";
				}else {
					
				}
			}else{
				//撮合交易管理新建的买盘暂时不需要同步到商城和买卖盘
				if (data[i]['createSource'] == 1) {
					//html +="&nbsp;&nbsp;<a class='btn_red50'  href='javascript:publishToByPool("+data[i]['id']+", "+data[i]['poolType']+","+data[i]['createSource']+")'>发送到买盘池</a>&nbsp;&nbsp;";
				}else {
					
				}
			}
			html +="&nbsp;&nbsp;<a class='btn_red50'  href='javascript:confirmCloseOffer("+data[i]['id']+", "+data[i]['poolType']+", "+data[i]['counterNum']+")'>关闭</a>&nbsp;&nbsp;";
			if(data[i]['isConfirm'] == 1){
				html += "<a class='btn_red50'  href='javascript:generateOrder("+data[i]['id']+", "+data[i]['poolType']+")'>生成订单</a>";
			} else {
				html += "<a class='btn_red50'  href='javascript:addCounterView("+data[i]['id']+", "+data[i]['poolType']+", "+data[i]['quantity']+", "+data[i]['priceType']+")'>进行还盘</a>";
			}
		} else if(data[i]['status'] == 1){
			//html += "<td>&nbsp;&nbsp;<a class='btn_red50'  href='javascript:confirmRestoreOffer("+data[i]['id']+", "+data[i]['poolType']+", "+data[i]['counterNum']+")'>还原</a>&nbsp;&nbsp;";
			
			//舍弃 回收站还原功能   2015年12月23日下午3:03:38
			html += "<td>&nbsp;&nbsp;<a style='color:red;'>已经关闭</a>&nbsp;&nbsp;";
		}
		
		html += "</td></tr>";
		
		if(delTd==true){
			html+="<td><a class='btn_red50'  href='javascript:deleteZB("+id+")'>删除</a></td></tr>";
		}
	}
	$("#grid").html(html);
	/*var pageNum = Math.floor(total / len);
	if(total % len > 0){
		pageNum +=1;
	}
	var selectHtml = "";
	for(var i = 1 ; i <= pageNum; i++){
		selectHtml+="<option value='"+i+"'>"+i+"</option>";
	}
	$("#toPageNum").html(selectHtml);
	var currentPage = (start / len)+1;
	$("#toPageNum").val(currentPage);
	$("#CPofAP").text("页次："+currentPage+"/"+pageNum+"页");
	$("#DataCount").text("共"+total+"条数据");
	$("#perPageNum").text("每页"+len+"条");
	if(currentPage==1){
		$("#prePageBtn").css("color","#A0A0A4");
	}
	if(currentPage>=pageNum){
		$("#nextPageBtn").css("color","#A0A0A4");
	} else if(currentPage<pageNum) {
		$("#nextPageBtn").css("color","#0000FF");
	}
	$("#grid tr").click(function(){
		$("#grid tr.companyClickColor").removeClass("companyClickColor");
		$(this).addClass("companyClickColor");
	});*/
	
	//对已选中的行进行还原
	restoreCheck();
	
}
function prePage(){
	if(start <= 0){
		alert("已经是第一页");
		$("#prePageBtn").css("color","#A0A0A4");
		return;
	}
	start-=len;
	initData();
	$("#nextPageBtn").css("color","#0000FF");
}
function nextPage(){
	if(start +len >= total){
		alert("已经是最后一页");
		$("#nextPageBtn").css("color","#A0A0A4");
		return;
	}
	start+=len;
	initData();
	$("#prePageBtn").css("color","#0000FF");
}
function toPage(){
	var newPage = $("#toPageNum").val();
	start = (newPage - 1) *len;
	initData();
	$("#toPageNum").val(newPage);
}
//表头点击搜索
function queryByCondition(){
	$("#attributeHidden").val($("#attribute").val());
	$("#operatorHidden").val($("#operator").val());
	$("#contentHidden").val($("#content").val());
	start = 0;
	initData();
}
function chkAll(){
	if($("#chkAll").attr("checked")){
		$("#grid tbody input[name='checkboxes']").each(function(){
			setCheckIds($(this).attr('checkId'));
			$(this).attr("checked", "checked");
			
		});
	} else {
		$("#grid tbody input[name='checkboxes']").each(function(){
			removeCheckIds($(this).attr('checkId'));
			$(this).removeAttr("checked");
		});
	}
}

//异步刷新之后还原勾选
function restoreCheck(){
	$("#grid tbody input[name='checkboxes']").each(function(){
		var flag = checkArr.get($(this).attr('checkId'));
		if(flag!=null){
			$(this).prop('checked',true);
		}
	});
	restoreCheckAll();
}
//还原全选按钮
function restoreCheckAll(){
	var f = 0;
	$("#grid tbody input[name='checkboxes']").each(function(){
		var flag = $(this).attr('checked');
		if(flag!='checked'){
			f++;
		}
	});
	if(f==0){
		$("#chkAll").prop('checked',true);
	}else{
		$("#chkAll").prop('checked',false);
	}
}



function setCheckIds(checkid){
	checkArr.put(checkid,checkid);
	
}
function removeCheckIds(checkid){
	checkArr.remove(checkid,checkid);
}

//选中按钮,每次选中之后，将选中值保存到全局，列表ajax刷新后，原来选中的继续选中
function check(obj){
	var checkId =  obj.attributes['checkId'].nodeValue;
	var xuanzhong = $("#xuanzhong").val();
	if(obj.checked){
		
		xuanzhong = xuanzhong+""+checkId+",";
		$("#xuanzhong").val(xuanzhong);
		
		setCheckIds(checkId);
	}else{
		if (xuanzhong.indexOf(checkId+",")==-1?false:true) {
			xuanzhong = xuanzhong.replace(checkId+",","");
			$("#xuanzhong").val(xuanzhong);
		}
		removeCheckIds(checkId);
	}
	restoreCheckAll();
}

function getCompanyNameTd(contacters, companyName){
	//debugger;
	if(contacters.length>0){
	value = '<td data_id="'+contacters[0]['id']
			+ '" data_name="'+contacters[0]['name']
			+ '" data_mobilePhone="'+contacters[0]['mobilePhone']
			+ '" title = "' + contacters[0]['name'] + "\n" +contacters[0]['mobilePhone']
			+ '" class="companyNameHover" >' + companyName + '</td>';
	return value;
	}else{
    value = '<td data_id="" data_name="" data_mobilePhone="" title = "" class="companyNameHover" >' + companyName + '</td>';
    return value;
	}
}
function bindEvent(){
	
	/*$("#grid td.companyNameHover").hover(function(){
		var layerEleWidth = $(this).width() / 3;
		var name = $(this).attr("data_name");
		var mobilePhone = $(this).attr("data_mobilePhone");
		var popupText = name + "<br />" + mobilePhone;
		if(popupText!="") {
			layer.tips(popupText, this, {
					style: ['background-color:#26a69a; color:#fff; margin-left:'+ layerEleWidth +'px;', '#26a69a'],
					guide: 2,
					maxWidth:400,
					time: 5,
					isGuide: true,
					closeBtn:[0, false]
			});
		}
	}, function(){
		layer.closeTips();
	});*/
}
/*function queryByCondition(){
	$("#attributeHidden").val($("#attribute").val());
	$("#operatorHidden").val($("#operator").val());
	$("#contentHidden").val($("#content").val());
	start = 0;
	initData();
}*/

//点击视图
function queryView(ttype){
	type=ttype;	
	if(type==3){
		$("#del").attr("disabled","disabled");
		$("#addOffer").attr("disabled","disabled");
		$("#export").attr("disabled","disabled");
		$("#toorder").attr("disabled","disabled");
	}else{
		$("#del").attr("disabled",false);
		$("#addOffer").attr("disabled",false);
		$("#export").attr("disabled",false);
		$("#toorder").attr("disabled",false);
	}
	start = 0;
	initData();
}


function getCounterList(poolId, poolType){
	document.location.href = BASE_URL + '/poolprice/getPoolPrice.do?poolId='+ poolId+'&poolType=' + poolType;
}
function modifyOfferInfo(poolId, poolType){
	document.location.href = BASE_URL + '/PoolManage/toupdatePool.do?poolId='+ poolId+'&poolType=' + poolType+'&high=' + $("#high").val()+'&low=' + $("#low").val();
}
function confirmCloseOffer(poolId, poolType, counterNum){
	CommonUtils.confirm("关闭确认", "关闭报盘的同时，对应的所有还盘也会被关闭！", "closeOffer("+poolId+", "+poolType+", "+counterNum+")");
}
function confirmRestoreOffer(poolId, poolType, counterNum){
	CommonUtils.confirm("还原确认", "还原报盘的同时，对应的所有还盘也会被还原！", "restoreOffer("+poolId+", "+poolType+", "+counterNum+")");
}

function closeOffer(poolId, poolType, counterNum){
	$.get("closeOffer.do", {"poolId":poolId, "poolType":poolType, "counterNum": counterNum}, function(data){
		if(data.status == "ok"){
			alert(data.msg);
			initData();
		} else {
			alert(data.msg);
		}
	});
}

function restoreOffer(poolId, poolType, counterNum){
	$.get("restoreOffer.do", {"poolId":poolId, "poolType":poolType, "counterNum": counterNum}, function(data){
		if(data.status == "ok"){
			alert(data.msg);
			initData();
		} else {
			alert(data.msg);
		}
	});
}

function getFormatTime(time){
	if(time == null || time == ""){
		return "";
	}
	return new Date(time).format("yyyy-MM-dd hh:mm:ss");
}
//买卖盘生成订单
function generateOrder(poolId, poolType){
	//卖盘
	if(poolType!==null && poolType==1){
		document.location.href = BASE_URL+'/buyOrder/toaddsellorder.do?sid='+poolId;
	}
	//买盘
	if(poolType!==null && poolType==2){
		document.location.href = BASE_URL+'/buyOrder/toaddbuyorder.do?ids='+poolId;
	}
	
}
function viewOrder(poolId, poolType, orderId){
	CommonUtils.editModal(BASE_URL + "/customerDetail/orderRecord/orderDetail.do?orderId="+orderId, 800);
}
function addCounterView(poolId, poolType, quantity,priceType){
	//还盘最低价
	var low = $("#low").val();
	CommonUtils.editModal(BASE_URL + "/business/addCounterView.do?poolId=" + poolId+"&poolType="+poolType +"&quantity="+quantity +"&priceType="+priceType+"&low="+low, 600);
}

/**
 * 字典类 key value
 */
function Dictionary(){
 this.data = new Array();
 
 this.put = function(key,value){
  this.data[key] = value;
 };

 this.get = function(key){
  return this.data[key];
 };

 this.remove = function(key){
  this.data[key] = null;
 };
 
 this.isEmpty = function(){
  return this.data.length == 0;
 };

 this.size = function(){
  return this.data.length;
 };
}

/** 撮合交易管理 卖盘发布到买卖盘，并且同步到商城
 * @Title: publishToSellPool 
 * @Description: TODO
 * @author zhangnan/张楠
 * @return: void
 * @createTime 2015年12月21日
 */
//卖盘
function publishToSellPool(poolId,poolType,createSource){
	$.ajax({
		url : BASE_URL + "/dealMake/publishToSellPool.do",
		type : "post",
		dataType : 'JSON',
		data : {
			"poolId" : poolId,
			"poolType" : poolType
		},
		success : function(result) {
			if (result.success == "OK") {
				alert(result.msg);
				window.location.href=BASE_URL + "/business/index.do";
			}else {
				alert(result.msg);
				window.location.href=BASE_URL + "/business/index.do";
			}
			
		}
	});
}

//买盘
function publishToByPool(poolId,poolType,createSource){
	$.ajax({
		url : BASE_URL + "/buypool/publishToByPool.do",
		type : "post",
		dataType : 'JSON',
		data : {
			"poolId" : poolId,
			"poolType" : poolType
		},
		success : function(result) {
			if (result.success == "OK") {
				alert(result.msg);
				window.location.href=BASE_URL + "/business/index.do";
			}else {
				alert(result.msg);
				window.location.href=BASE_URL + "/business/index.do";
			}
			
		}
	});
}