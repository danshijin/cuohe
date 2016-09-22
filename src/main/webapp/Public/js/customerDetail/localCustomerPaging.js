var start = 0;
var len=5;
var total=0;
var strWhere = "";
var delTd=false;
$(document).ready(function() {
	initData();
	$("#grid").addClass("ttTbl");
});
function initData(){
	$.getJSON(actionUrl, {"start":start, "len":len,"strWhere":strWhere, "attribute":$("#attributeHidden").val(), "operator":$("#operatorHidden").val(), "content":$("#contentHidden").val()},function(data){
		total= data.total;
		if(total <= 0){
			$("#grid").html('');
			$(".dataDiv").before('<div class="ajaxNoData" style="width:'+$(".dataDiv").width()+'px; height:'+$(".dataDiv").height()+'px; position:absolute; background-color:white;"><div style="text-align: center;">没有数据</div></div>');
			return;
		} else {
			$('.ajaxNoData').remove();
		}
		generateTable(data.rows);
		if(typeof callback == 'function'){
			callback();
		}
	});
}
function generateTable(data){
	var html = "<thead><th>&nbsp;</th>";
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
		html+="<tr><td data_id='"+data[i]["id"]+"'  data_name='"+data[i]["companyName"]+"'>"+ (start + i +1 ) +"</td>";
		for(var j = 0; j < nameArr.length; j++){
			if(data[i][nameArr[j]]==undefined){
				html+="<td>&nbsp;</td>";
			}else{
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
	$("#grid tr").click(function(){
		$("#grid tr.companyClickColor").removeClass("companyClickColor");
		$(this).addClass("companyClickColor");
	});
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
function queryByCondition(){
	$("#attributeHidden").val($("#attribute").val());
	$("#operatorHidden").val($("#operator").val());
	$("#contentHidden").val($("#content").val());
	start = 0;
	initData();
}