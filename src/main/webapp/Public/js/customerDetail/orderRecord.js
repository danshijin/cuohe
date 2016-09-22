var total=0;
var strWhere = "";
var delTd=false;
var titleArr = [];
var nameArr = [];
$(document).ready(function() {
	initData();
	$("#grid").addClass("ttTbl");
});
function initData(){
	$.getJSON(actionUrl, {"start":start,"len":len,"strWhere":strWhere},function(data){
		if(data.msg != "success"){
			alert("调用接口失败");
			return;
		}
		
		total = data.total;
		if(total < 1){
			$("#orderRecordBody").html('<div class="noAjaxData">没有数据</div>');
			return;
		}
		titleArr = initTitleArr.concat();
		nameArr = initNameArr.concat();
		
		var list = data.list;
		
		var attrView = data.attrView;
		
		for(var i = 0 ; i < attrView.length; i++){
			
			if(attrView[i].mainProperty == 1){
				
				titleArr.splice(2, 0, attrView[i].name);
				
				nameArr.splice(2,0, "___" + attrView[i].name);
			}
		}
		
		generateTable(list);
		if(typeof callback == 'function'){
			callback();
		}
	});
}
function generateTable(data){
	var html = "<thead>";
	//html +="<th>&nbsp;</th>"; //序号
	for(var i =0;i < titleArr.length; i++){
		html+="<th>"+titleArr[i]+"</th>";
	}
	if(delTd==true){
		html+="<th>&nbsp;</th>";
	}
	html+="</thead><tbody>";
	for(var i = 0 ; i < data.length; i++){
		var id = data[i].id;
		if(typeof(data[i].id)=='undefined'){
			if(typeof(data[i].keyid)!='undefined'){
				id = data[i].keyid;
			}else{
				id=start + i +1 ;
			}
		}
		html+="<tr>";
		//html += "<td data_id='"+id+"'>"+ (start + i +1 ) +"</td>";  //添加序号使用到的
		for(var j = 0; j < nameArr.length; j++){
			if(nameArr[j].indexOf("___") == 0){
				var attrName = nameArr[j].substr(3);
				var value = "";
				for(var k =0; k < data[i]["subOrderList"].length; k++){
					
					for(var t = 0; t < data[i]["subOrderList"][k]["commodityAttrList"].length; t++){
						
						if(data[i]["subOrderList"][k]["commodityAttrList"][t]["attrName"] == attrName){
							
							value = data[i]["subOrderList"][k]["commodityAttrList"][t]["attrValue"];
							break;
						}
					}
				}
				html+="<td>"+value+"</td>";
			} else if (nameArr[j].indexOf("fun_") == 0){
				var value = "";
				if(nameArr[j] == "fun_orderCode"){
					
					value='<a href="javascript:orders_details(\''+ data[i]["id"] +'\')">'+ (data[i]["orderCode"]||'') +'</a>';
						
				} else if(nameArr[j] == "fun_sellId"){
					
					value = getType(data[i]["sellId"]);
					
				} else if(nameArr[j] == "fun_contract"){
					
					value = getContract(data[i]["contract"], data[i]["id"]);
					
				} else if(nameArr[j] == "fun_status"){
					
					value = getStatus(data[i]["orderStatus"]);
					
				}
				
				html+="<td>"+value+"</td>";
			} else if (nameArr[j].indexOf("sub_") == 0){
				var value = "";
				if(nameArr[j] == "sub_quantity"){
					
					for(var k =0; k < data[i]["subOrderList"].length; k++){
						
						var quantity = data[i]["subOrderList"][k]["quantity"];
						var unit = "";
						if(data[i]["subOrderList"][k]["unit"]==0){
							unit = "吨";
						} else {
							unit ="千克";
						}
						value += quantity + unit  + "<br/>";
					}
					html+="<td>"+value+"</td>";
				} else if (nameArr[j] == "sub_total"){
															
					for(var k =0; k < data[i]["subOrderList"].length; k++){
						
						var quantity = data[i]["subOrderList"][k]["quantity"];
						
						var price = data[i]["subOrderList"][k]["price"];
						
						value += "￥" + quantity * price + "<br/>";
					}
					html+="<td>"+value+"</td>";
				}
				
			} else if (nameArr[j]=="createdAt"){
				html+="<td>" + new Date(data[i]["createdAt"]).format("yyyy-MM-dd hh:mm:ss") + "</td>";
			} else if (data[i][nameArr[j]]==undefined){
				html+="<td>&nbsp;</td>";
			} else {
				html+="<td>"+data[i][nameArr[j]]+"</td>";
			}
		}
		if(delTd==true){
			html+="<td><a class='btn_red50'  href='javascript:deleteZB("+id+")'>删除</a></td></tr>";
		}
	}
	$("#grid").html(html);
	var pageNum = Math.floor(total / len);
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

function getType(sellId){
	if(sellId == id){
		
		return "销售";
		
	} else {
		
		return "购买";
		
	}
}
function getContract(contract, id){
	if(contract == null){
		return '无';
	}
	var contractTxt = contract["content"];
	var contractPath = contract["filePath"];

	if(contractTxt != null && contractTxt.length > 0){
		
		return '<a href="'+BASE_URL+'/contract/generateContractByTemplate.do?orderId='+id+'">有</a>';
		
	} else if (contractPath !=null && contractPath.length > 0){
		
		return '<a href="'+BASE_URL+'/order/contacterDownload.do?orderId='+id+'">有</a>';
		
	} else {
		
		return '无';
		
	}
	
}

function getStatus(orderStatus){
	if(orderStatus == 0){
		return "已创建";
	} else if (orderStatus == 1){
		return "已确认交收";
	} else if (orderStatus == 2){
		return "已撤销";
	}
	
}