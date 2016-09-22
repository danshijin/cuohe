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
		var obj = $.parseJSON(data.result);
		if(obj.code != "success"){
			alert("接口返回错误信息："+obj.message);
		}
		total = obj.resultData.total;
		if(typeof total == "undefined" || total < 1){
			$("#offerRecordBody").html('<div class="noAjaxData">没有数据</div>');
			return;
		}
		titleArr = initTitleArr.concat();
		nameArr = initNameArr.concat();
		var head = obj.resultData.attrNameList;
		for(var i =0;i < head.length; i++){
			if(typeof(head[i]["is_main"]) != "undefined" && head[i]["is_main"] == 1){
				titleArr.splice(2, 0, head[i].attrName);
				nameArr.splice(2,0, "___" + head[i].attrId);
			}
		}
		generateTable(obj.resultData.quoteRecordList);
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
				var attrid = nameArr[j].substr(3);
				var value = "";
				for(var k =0; k < data[i]["AttrValue"].length; k++){
					if(data[i]["AttrValue"][k]["attrid"] == attrid){
						value = data[i]["AttrValue"][k]["attrValue"];
						break;
					}
				}
				if(value != ""){
					html+="<td>"+value+"</td>";
				}
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