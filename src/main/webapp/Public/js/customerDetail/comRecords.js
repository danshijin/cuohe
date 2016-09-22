function mainContent(records){
	var html = "";
	for(var i =0; i < records.length; i++){
		
		html += '<div style="padding:.2em .5em;color:#1968A0;font-weight:bold;word-wrap: break-word;" class="crTitle'+records[i].id+'">'+records[i].title+'</div>';
		html += '<div style="padding:.2em .5em;word-wrap: break-word;" class="crContext'+records[i].id+'">'+records[i].context+'</div>';
		html += '<div class="entrust">';
		html += '<em>';
		html += '<a href="javascript:;" class="btn btn-xs blue crEdit" style="margin-right:10px;" crId='+records[i].id+'>';
		html += '<i class="fa fa-edit" ></i> 编辑';
		html += '</a>';
		
		html += '<a href="javascript:;" class="btn btn-xs blue crSave" style="margin-right:10px; display:none;" crId='+records[i].id+'>';
		html += '<i class="fa fa-edit" ></i> 保存';
		html += '</a>';
		
		html += '<a href="javascript:;" class="btn btn-xs green crDel" crId='+records[i].id+'>';
		html += '<i class="fa fa-trash-o"></i> 删除';
		html += '</a> &nbsp;';
		html += '<i class="fa fa-user"></i>'+records[i].updateUserName +'   &nbsp;';
		html += '<i class="fa fa-clock-o"></i>'+ new Date(records[i].updatedAt).format("yyyy-MM-dd hh:mm:ss");
		html += '</em>';
		html += '</div>';
		html += '<hr style="height:1px;">';
	}
	$("#rdBody").html(html);
	//添加
	$("#addRecord").click(function(){
		CommonUtils.editModal("showAddComRecordDialog.do", 600);
	});
	
	//可编辑状态
	$(".crEdit").each(function(index, element){

		$(element).click(function(){
			var id = $(element).attr("crId");
			var title = $(".crTitle"+id).text();
			var context = $(".crContext"+id).text();
			$(".crTitle"+id).html('<input type="text" value="'+title+'"/>');
			$(".crContext"+id).html('<textarea type="text" rows=3  style="width:100%;">'+context+'</textarea>');
			$(element).css("display","none");
			$(element).next().css("display","inline-block");
		});
	});
	//提交保存
	$(".crSave").each(function(index, element){
		$(element).click(function(){
			var id = $(element).attr("crId");
			var title = $(".crTitle"+id+" input").val();
			var context = $(".crContext"+id+" textarea").val();
			$(".crTitle"+id).html('<input type="text" value="'+title+'"/>');
			$(".crContext"+id).html('<textarea type="text" rows=2 cols=70>'+context+'</textarea>');
			var record = {};
			record.id = id;
			record.title=title;
			record.context=context;
			$.post("updateComRecord.do",record,function(data){
				if(data=="success"){
					alert("保存成功");
					$("#comRecordsA").click();
					$(element).prev().css("display","inline-block");
					$(element).css("display","none");
				}
			});
		});
	});
	//确认删除
	$(".crDel").each(function(index, element){
		$(element).click(function(){
			var id = $(element).attr("crId");
			CommonUtils.confirm("确认","将删除该沟通记录", "delRecord("+id+")");
		});
	});
}

//删除操作
function delRecord(id){
	
	$.get("deleteComRecord.do",{"id":id},function(data){
		if(data="success"){
			alert("删除成功");
			$("#comRecordsA").click();
		}
	});
}

