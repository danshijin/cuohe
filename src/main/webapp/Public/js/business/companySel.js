/**
 * 选择公司.
 * 请预先引入jquery文件并定义要添加此功能的input的id数组，名称为businessArr;
 * 
 */
function selectCompany(companyId){
	$("#" + companyId).select2({
		language: "zh_CN",
        placeholder: "关键字搜素公司",//文本框的提示信息
        minimumInputLength: 0,//至少输入n个字符，才去加载数据
        allowClear: true, //是否允许用户清除文本信息
        /* multiple:true,//多选属性 */
        ajax: { // instead of writing the function to execute the request we use Select2's convenient helper
     	   url : BASE_URL + "/dealMake/queryCompanyByNm.do",
            dataType: 'json',//接收的数据类型
            type : "post",
            data: function (term, page) {//在查询时向服务器端传输的数据
                return {
                    companyName: term, //联动查询的字符
                    page: 10 //一次性加载的数据条数
                };
            },
            results: function (data, page) { // parse the results into the format expected by Select2.
                // since we are using custom formatting functions we do not need to alter remote JSON data
                var realNames = new Array();    
                     for (var i = 0; i < data.length; i++) {
                          realNames.push({id: data[i].id, text: data[i].name});
                   }
                return {results: realNames};
            }
        },
        formatResult: resultFormatResult, // omitted for brevity, see the source of this page
        formatSelection: resultFormatSelection,  // omitted for brevity, see the source of this page
        dropdownCssClass: "bigdrop", // apply css that makes the dropdown taller
        escapeMarkup: function (m) { return m; } // we do not want to escape markup since we are displaying html in results
    });
}

function resultFormatResult(medata) {
    return medata.text; 
} 
      
function resultFormatSelection(medata) {
	var selCompanyId = medata.id;
	
	var customerId = $("#customerId").val();
	//alert(selCompanyId+"------"+customerId);
	if(selCompanyId == customerId){
		$("#ERROR4").css("display","inline");
	}else {
		$("#ERROR4").css("display","none");
	}
	//追加联系人数据
	$("#companyNames").val(medata.text);
	//alert(medata.id);
	$.ajax({
		url : BASE_URL + "/business/checkBlacklist.do",
		type : "post",
		dataType : 'JSON',
		data :{"poolId" :$("#poolId").val(),"buyId":medata.id,"poolType":$("#poolType").val()},
		success : function(result) {
			if (result == "ERROR1") {
				$("#ERROR2").css("display","none");
				$("#ERROR3").css("display","none");
				$("#ERROR1").css("display","inline");
			}else if (result == "ERROR2") {
				$("#ERROR3").css("display","none");
				$("#ERROR1").css("display","none");
				$("#ERROR2").css("display","inline");
			}else if (result == "ERROR3") {
				$("#ERROR1").css("display","none");
				$("#ERROR2").css("display","none");
				$("#ERROR3").css("display","inline");
			}else{
				$("#ERROR1").css("display","none");
				$("#ERROR2").css("display","none");
				$("#ERROR3").css("display","none");
			}
		}
	});
	
    return medata.text; 
} 