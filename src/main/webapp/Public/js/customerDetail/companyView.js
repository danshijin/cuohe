$(function(){
	//初始化页面数据
	(function(){
		
		if(company.status == 1){
			$("#infoDel").css("display", "none");
		} else {
			$("#infoRestore").css("display", "none");
		}
		
		if(company.companyName.length > 9){
			$("#companyNameTitle").text(company.companyName.substr(0,9) + "...");
		} else {
			$("#companyNameTitle").text(company.companyName);
		}
		
		$("#createdAt").text(company.createdAt);
		$("#createdBy").text(company.createdBy==null?"":company.createdBy);
		//负责人$("#").text(company.);
		$("#categorySource").attr("category",company.categorySource);
		$("#categorySource").text(company.source==null?"":company.source);
		$("#name").text(company.companyName);
		
		$("#salesProducts").text(company.salesProducts==null ?"":company.salesProducts);
		$("#categoryCoo").attr("category", company.coo);
		$("#categoryCoo").text(company.coo==null ? "":company.coo);
		$("#province").text(company.province==null ? "" : company.province);
		
		var html = "";
		for(var i = 0, arr=selItems.企业类型; i < arr.length; i++){
			if(company.entTypesText != null && company.entTypesText != "" && company.entTypesText.indexOf(arr[i].name) >-1){
				html += '<label><input type="checkbox" value="'+arr[i].id+'" txt="'+arr[i].name+'" checked="checked" />'+arr[i].name+'&nbsp;</label>';
			} else {
				html += '<label><input type="checkbox" value="'+arr[i].id+'" txt="'+arr[i].name+'"  />'+arr[i].name+'&nbsp;</label>';
			}
		}
		$("#entTypes").html(html);
		
		$("#entTypes input:checkbox").each(function(index, element){
			$(element).prop("disabled", true);
			$(element).css("cursor", "default");
		});
		
		$("#address").text(company.address==null?"":company.address);
		
		for(var i = 0, arr=selItems.年销售额; i < arr.length; i++){
			if(company.salesVolume != null && company.salesVolume != "" && company.salesVolume == arr[i]['id']){
				$("#salesVolume").text(arr[i]['name']);
				break;
			}
		}
		
		$("#categoryEmployee").attr("category",company.categoryEmployee);
		$("#categoryEmployee").text(company.employee==null?"":company.employee);
		
		$("#corporate").text(company.corporate==null?"":company.corporate);
		$("#registerDate").text(company.registerDate==null?"":company.registerDate.substr(0, 10));
		$("#companyPhone").text(company.companyPhone==null?"":company.companyPhone);
		$("#companyProperty").attr("category", company.companyProperty);
		$("#companyProperty").text(company.property == null ? "" : company.property);
		$("#companySite").html('<a href = "'+(company.companySite == null ? "":company.companySite)+'" target="_blank">'+(company.companySite == null ? "":company.companySite)+"</a>");
		
		$("#pic").text(company.pic==null?"":company.pic);
		
		var contacters = company.contacters;
		var ctText = "";
		for(var i =0; i < contacters.length; i++){
			if(i >0){
				ctText +=",";
			}
			ctText += contacters[i].name;
		}
		$("#contacters").text(ctText); //联系人
		$("#companyAss").text(company.companyAss==null? "" : company.companyAss);
		if(company.primaryPurchaseContacts != null){
			$("#primaryPurchaseContacter").text(company.primaryPurchaseContacts.name == null ? "" : company.primaryPurchaseContacts.name);//首要采购联系人
		}
		$("#buyProducts").text(company.buyProducts == null ? "" :company.buyProducts);
		$("#categoryFreq").attr("category", company.categoryFreq);
		$("#categoryFreq").text(company.freq == null ? "" : company.freq);	
		if(company.lastPurchaseTime != null){
			$("#lastPurchaseTime").text(company.lastPurchaseTime == null ? "" : company.lastPurchaseTime);//上次采购时间
		}
		$("#commands").text(company.commands == null ? "" : company.commands);
	})();
	
	//将页面选项变为可编辑状态
	$("#infoUpdate").click(function(){
		
		//省份
		$.getJSON("getParentArea.do", {},function(data){
			var proHtml = '<select onchange="setChildAreas(this)" style="width: 110px; display:inline-block;" class="form-control">';
			proHtml += '<option selected="selected">请选择</option>';
			for(var i =0;i < data.parentAreas.length; i++) {
				proHtml += '<option value="'+data.parentAreas[i]['id']+'">'+data.parentAreas[i]['name']+'</option>';
			}
			proHtml += '</select><select style="width: 120px; margin-left:5px; display:inline-block;" class="form-control">';
			proHtml += '<option value="" selected="selected">请选择</option>';
			proHtml += '</select>';
			$("#province").html(proHtml);
			if(company.area){
				if(company.area.parentID == "0"){
					$("#province").find("select").eq(0).val(company.area.id);
					
					setChildAreas($("#province").find("select").eq(0), company.area.id);
				} else {
					$("#province").find("select").eq(0).val(company.area.parentID);
					
					setChildAreas($("#province").find("select").eq(0), company.area.id);
				}
				
			}
			
		});
		$("#salesProducts").html('<input id="salesInput" type="text" value="'+company.salesProducts+'" style="min-width:240px;max-width:400px;height: 34px;" />');
		//客户级别
		var html = "";
		var coo = selItems['客户级别'];
		html += '<select id="coo">';
		for(var i = 0; i < coo.length; i++){
			html += '<option value="'+coo[i]['id']+'">'+coo[i]['name']+'</option>';
		}
		$("#categoryCoo").html(html);
		$("#coo").val(company.level);
		
		$("#entTypes input:checkbox").each(function(index, element){
			$(element).prop("disabled", false);
		});
		
		$("#address").html('<input type="text" value="'+company.address+'" />');
		
		//年销售额
		html = "";
		html += '<select>';
		for(var i = 0, arr=selItems.年销售额; i < arr.length; i++){
			html += '<option value="'+arr[i].id+'">'+arr[i].name+'</option>';
		}
		html += '</select>';
		$("#salesVolume").html(html);
		$("#salesVolume select").val(company.salesVolume);
		
		//员工人数
		html = "";
		html += '<select id="employee">';
		for(var i = 0, arr=selItems.员工人数; i < arr.length; i++){
			html += '<option value="'+arr[i].id+'">'+arr[i].name+'</option>';
		}
		html += '</select>';
		$("#categoryEmployee").html(html);
		$("#employee").val(company.categoryEmployee);
		$("#corporate").html('<input type="text" value="'+(company.corporate==null ? "" : company.corporate)+'" />');
		$("#companyPhone").html('<input type="text" value="'+(company.companyPhone ==null ? "" : company.companyPhone)+'" />');
		
		//企业性质
		html = "";
		html += '<select id="property">';
		for(var i = 0, arr=selItems.企业性质; i < arr.length; i++){
			html += '<option value="'+arr[i].id+'">'+arr[i].name+'</option>';
		}
		html += '</select>';
		$("#companyProperty").html(html);
		$("#property").val(company.companyProperty);
		
		$("#companySite").html('<input type="text" value="'+(company.companySite == null ? "" : company.companySite)+'" />');
		$("#companyAss").html('<input type="text" value="'+(company.companyAss == null ? "" : company.companyAss)+'" />');
		$("#buyProducts").html('<input id="buyInput" type="text" value="'+(company.buyProducts == null ? "" : company.buyProducts)+'" style="min-width:240px;max-width:400px;height: 34px;" />');
		//采购周期
		html = "";
		html += '<select id="freq">';
		for(var i = 0, arr=selItems.采购周期; i < arr.length; i++){
			html += '<option value="'+arr[i].id+'">'+arr[i].name+'</option>';
		}
		html += '</select>';
		$("#categoryFreq").html(html);
		$("#freq").val(company.categoryFreq);
		$("#commands").html('<textarea type="text" cols =130 rows=3>'+(company.commands == null ? "" : company.commands)+'</textarea>');
		
		$("#infoUpdate").css("display","none");
		$("#infoSave").css("display", "inline-block");
		$("#frm1").validate();
		
		//select2使用需要预先定义的businessArr数组
		$(document.body).append('<script src="'+BASE_URL+'/Public/js/customerDetail/businessSel.js" type="text/javascript"><\/script>');
		
	});
	
	//提交修改的数据
	$("#infoSave").click(function(){
		
		var uCompany = {};
		
		uCompany.id = company.id;
		
		uCompany.companyName = company.companyName;
		
		uCompany.salesProducts = $("#salesInput").val();
		
		uCompany.level = $("#coo").val();
		
		uCompany.areaId = $("#province select:nth-child(2)").val() == "" ? $("#province select:nth-child(1)").val() : $("#province select:nth-child(2)").val();
		
		var entVal="";
		$("#entTypes :checkbox:checked").each(function(index, element){
			if(index >0){
				entVal += ",";
			}
			entVal += $(element).val();
		});
		
		uCompany.entTypes = entVal;
		
		uCompany.address = $("#address input").val();
		
		uCompany.salesVolume = $("#salesVolume select").val();
		
		uCompany.categoryEmployee = $("#categoryEmployee select").val();
		
		uCompany.corporate = $("#corporate input").val();
		
		uCompany.companyPhone = $("#companyPhone input").val();
		
		uCompany.companyProperty = $("#companyProperty select").val();
		
		uCompany.companySite = $("#companySite input").val();
		
		uCompany.companyAss = $("#companyAss input").val();
		
		uCompany.buyProducts = $("#buyInput").val();
		
		uCompany.categoryFreq = $("#categoryFreq select").val();
		
		uCompany.commands = $("#commands textarea").val();
		
		$.post("updateCompanyInfo.do", uCompany, function(data){
			if(data =="success"){
				alert("保存成功");
				$("#infoSave").css("display","none");
				$("#infoUpdate").css("display", "inline-block");
				$("#companyInfoA").click();
			}
		});
		location.reload();
	});
	
	$("#infoRestore").click(function(){
		CommonUtils.confirm("确认","将还原该企业信息", "restoreCompany()");
	});
	$("#infoDel").click(function(){
		CommonUtils.confirm("确认","将删除该企业信息", "delCompany()");
	});
});

function toBack(){
	if(company.categoryBusiness == 1 || company.categoryBusiness == 3){
		window.location= BASE_URL + "/purchase/getAll.do?type=1";
	} else {
		window.location=BASE_URL + "/supply/getAll.do?type=1";
	}
}

function restoreCompany(){
	$.get(BASE_URL + "/customer/restoreCustomer.do",{"customerId":company.id},function(data){
		if(data.status == "ok"){
			alert(data.msg);
			toBack();
		} else {
			alert(data.msg);
		}
	});
}

function delCompany(){
	$.get("deleteCompany.do",{"id":company.id},function(data){
		if(data == "success"){
			alert("删除成功");
			toBack();
		}
	});
}
//区域
var provinceEl;
function setChildAreas(el,selectId){
	provinceEl = el;
	$(el).next().find('option').remove();
	$(el).next().append('<option value="">请选择</option>');
	var parentId = $(el).val();
	if(parentId){
		$.ajax({
			type : "POST",
			url : BASE_URL + "/areas/getChildAreas.do?parentId="+parentId,
			dataType : "json",
			success : function(areas) {
				if(areas && areas instanceof Array && areas.length>0){
					var area;
					for(var i=0;i<areas.length;i++){
						area = areas[i];
						if(area && typeof area=='object'){
							
							if(selectId&&selectId==area.id){
								$(el).next().append('<option selected="selected" value="'+ area.id +'">'+ area.name +'</option>');
							}else{
								$(el).next().append('<option value="'+ area.id +'">'+ area.name +'</option>');
							}
						}
					}
				}
			}
		});
	}
}