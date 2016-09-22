/**
 * 选择行业.
 * 请预先引入jquery文件并定义要添加此功能的input的id数组，名称为businessArr;
 * 
 */
$(function(){
	if(businessArr){
		for(var i=0; i < businessArr.length; i++){
			var placeholderStr = '请输入行业';
			if(businessArr[i] == 'buyInput'){
				placeholderStr = '请输入商品';
			}
			$("#"+businessArr[i]).select2({
				language: "zh_CN",
			    placeholder: placeholderStr,//文本框的提示信息
			    minimumInputLength: 0,//至少输入n个字符，才去加载数据
			    allowClear: true, //是否允许用户清除文本信息
			    multiple:true,//多选属性 */
			    cache: true,
			    separator : ",", // 分隔符
			    /*maximumSelectionSize : 5, // 限制数量
			    createSearchChoice   : function(term, data) {           // 创建搜索结果（使用户可以输入匹配值以外的其它值）
			        return { id: term, text: term };
			    },*/
			    initSelection: function(element, callback) {
			    	 var data = [];
			         $(element.val().split(",")).each(function () {
			             data.push({ id: this, text: this });
			         });
			         callback(data);
			    },
			    ajax: { // instead of writing the function to execute the request we use Select2's convenient helper
			 	   url : BASE_URL +"/customer/searchCategory.do",
			        dataType: 'json',//接收的数据类型
			        type : "post",
			        data: function (term, page) {//在查询时向服务器端传输的数据
			            return {
			                name: term, //联动查询的字符
			                page: 10 //一次性加载的数据条数
			            };
			        },
			        results: function (data, page) { // parse the results into the format expected by Select2.
			            // since we are using custom formatting functions we do not need to alter remote JSON data
			            var realNames = new Array();    
			                 for (var i = 0; i < data.data.length; i++) {
			                      realNames.push({id: data.data[i].catname, text: data.data[i].catname});
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
	}
});

function resultFormatResult(medata) {
    return medata.text; 
} 
      
function resultFormatSelection(medata) {
	//追加联系人数据
	//$("#companyid").attr("companyName",medata.text);
    return medata.text; 
} 