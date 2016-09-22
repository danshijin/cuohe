var total=0; //总数
var strWhere = ""; //附加查询条件
$(document).ready(function() {
	
	initData();
});
//取得数据
function initData(){
	$.getJSON(actionUrl, {"start":start,"len":len,"strWhere":strWhere},function(data){
		total= data.total;
		//主要页面信息加载
		mainContent(data.rows);
		//分页
		pagingTable();
		if(typeof callback == 'function'){
			callback();
		}
	});
}
//分页操作
function pagingTable(){
	
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
	$("#toPageNum").val(currentPage); //设置显示当前页数
	$("#CPofAP").text("页次："+currentPage+"/"+pageNum+"页");
	$("#DataCount").text("共"+total+"条数据");
	$("#perPageNum").text("每页"+len+"条");
	//上一页，下一页的可点击状态
	if(currentPage==1){
		$("#prePageBtn").css("color","#A0A0A4");
	}
	if(currentPage>=pageNum){
		$("#nextPageBtn").css("color","#A0A0A4");
	} else if(currentPage<pageNum) {
		$("#nextPageBtn").css("color","#0000FF");
	}
}

//上一页
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

//下一页
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
//跳转
function toPage(){
	var newPage = $("#toPageNum").val();
	start = (newPage - 1) *len;
	initData();
	$("#toPageNum").val(newPage);
}

