/*======JS共用工具========
 *author:曾世华
 *date:2015/07/29
 *=====================*/
var CommonUtils = {

	/*======全选checkBoxs====
	 *
	 *全选checkBoxs
	 * 
	 *=====================*/
	checkInit: function(){
		$('.group-checkable').change(function () {
            var set = jQuery(this).attr("data-set");
            var checked = jQuery(this).is(":checked");
            jQuery(set).each(function () {
                if (checked) {
                    $(this).attr("checked", true);
                } else {
                    $(this).attr("checked", false);
                }
            });
            jQuery.uniform.update(set);
        });
	},
	/*====获取全选checkBox值====
	 *
	 * 获取全选checkBox值
	 * 
	 *======================*/
	getCheckedBoxs: function(){
		
		var boxes = $("input[name='checkboxes']:checked");
		var result = new Array();
		for ( var i = 0; i < boxes.length; i++) {
			result.push(boxes[i].value);
		}
		return result;
		
	},
	/*======批量操作========
	 *_action:请求地址
	 * 
	 *=================*/
	doExecute: function(_action){
		
		var boxes = CommonUtils.getCheckedBoxs();
		if (boxes.length == 0) {
			CommonUtils.succModal("系统提示", "至少选择一项进行操作，请选择...");
			return;
		}
		
		var pm = "";
		if (_action.indexOf("?") > -1) {
			pm = _action.substring(_action.indexOf("?")+1, _action.length);
		}
		var data="ids=" + boxes.join(",")+"&"+pm;
		
		CommonUtils.confirm("系统提示","确认进行此操作吗？","CommonUtils.doCallBack('"+_action+"','"+data+"')");
		
	},
	doCallBack: function(_action,_data){
		$.ajax({
        		type: "GET",
        		url: _action,
        		data: _data,
        		dataType: "json",
        		success: function(res){
        			if(res.code=='succ'){ 
        				CommonUtils.succModal2("系统提示",res.msg);
        			}
        			else {
        				CommonUtils.succModal2("系统提示","操作失败");
        			}
        		}
       });
	},
	/*======编辑模态框========
	 *url:请求地址
	 *width：编辑模态框宽度，不填则默认
	 * 
	 *=================*/
	editModal: function(url,width){
		
			var tmpl = ['<div class="modal fade modal-scroll in editModal" role="basic" aria-hidden="false" data-backdrop="true" data-keyboard="true">',
							'<div class="page-loading page-loading-boxed">',
								'<img src="../Public/assets/global/img/loading-spinner-grey.gif" alt="" class="loading">',
								'<span>&nbsp;&nbsp;正在加载.....</span>',
							'</div>',
							'<div class="modal-dialog" style="width:##width##">',
								'<div class="modal-content">##contentHtml##</div>',
							'</div>',
						'</div>'
						].join(''); 
			var w="760px";
			if(typeof width != "undefined"){
				w=width+"px";
		    }
			tmpl=tmpl.replace("##width##",w);
			var model = $(tmpl);
			$.get(url,"", function (d) {
				//tmpl=tmpl.replace("##width##",w).replace("##contentHtml##",d);
				model.find('.modal-content').html(d);
				model.on("hidden.bs.modal", function() {
				    $(this).remove();
				});
				model.modal();
		    });
	},
	
	/*======编辑模态框========
	 * url:请求地址
	 * width:弹出窗宽度，number类型,默认600
	 * height:弹出窗高度，number类型,默认200
	 *=================*/
	editDialog: function(url, width, height){
		width = (typeof width=="number") ? width : 600;
		height = (typeof height=="number") ? height : 'auto';
		var top = ($(window).height() - height) / 2;
		var style ='style="position:fixed; width:'+width+'px; height:'+ height +'px;top:'+ top +'px;"';
		
		var html = '<div class="modal fade modal-scroll in editModal" id="myModal" role="basic" aria-hidden="false" data-backdrop="true" data-keyboard="true">'
				+ '<div class="page-loading page-loading-boxed">'
				+ '<img src="../Public/assets/global/img/loading-spinner-grey.gif" alt="" class="loading">'
				+ '<span>&nbsp;&nbsp;正在加载.....</span>'
				+ '</div>'
				+ '<div class="modal-dialog" >'
				+ '<div class="modal-content" ' + style + '>';
		$.get(url, {}, function (data) {
			html += data + '</div></div></div>';
			var dialog = $(html);
			dialog.on("hidden.bs.modal", function() {
			    $(this).remove();
			});
			dialog.modal();
	    });
	},
	
	/*======询问模态框========
	 *title:标题 
	 *message：消息
	 *callBack:确认按钮点击事件
	 * 
	 *=================*/
	confirm:function(title,message,callBack){
		 	var tmpl = ['<div id="static" class="modal fade" tabindex="-1" data-backdrop="true" data-keyboard="true">',
		 	            '<div class="modal-dialog">',
			 	            '<div class="modal-content">',
								'<div class="modal-header">',
									'<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>',
									'<h3 class="modal-title">'+title+'</h3>',
								'</div>',
								'<div class="modal-body">',
									'<p style="font-size: 15px;text-align: center">'+message+'</p>',
								'</div>',
								'<div class="modal-footer">',
									'<button type="button" data-dismiss="modal" class="btn default fa fa-times">取消</button>',
									'<button type="button" data-dismiss="modal" class="btn green  fa fa-check" onclick="'+callBack+';">确定</button>',
								'</div>',
							'</div>',
						'</div>',
					'</div>'
	               ].join('');
		 	var dialog = $(tmpl);
		 	dialog.on("hidden.bs.modal", function() {
			    $(this).remove();
			});
		 	dialog.modal();
	},
	confirm2:function(title,message,ok,cancel){
	 	var tmpl = ['<div id="static" class="modal fade" tabindex="-1" data-backdrop="static" data-keyboard="false">',
	 	            '<div class="modal-dialog">',
		 	            '<div class="modal-content">',
							'<div class="modal-header">',
								'<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>',
								'<h3 class="modal-title">'+title+'</h3>',
							'</div>',
							'<div class="modal-body">',
								'<p style="font-size: 15px;text-align: center">'+message+'</p>',
							'</div>',
							'<div class="modal-footer">',
								'<button type="button" data-dismiss="modal" class="btn default fa fa-times onclick="'+cancel+'>取消</button>',
								'<button type="button" data-dismiss="modal" class="btn green  fa fa-check" onclick="'+ok+';">确定</button>',
							'</div>',
						'</div>',
					'</div>',
				'</div>'
               ].join('');
	 	var dialog = $(tmpl);
	 	dialog.on("hidden.bs.modal", function() {
		    $(this).remove();
		});
	 	dialog.modal();
},

	/*======提示模态框========
	 *title:标题 
	 *message：消息
	 * 
	 *=================*/
	succModal:function(title,message){
		
		var tmpl = ['<div id="basic" class="modal fade" tabindex="-1" role="basic" data-backdrop="true" data-keyboard="true">',
		            '<div class="modal-dialog">',
			            '<div class="modal-content">',
							'<div class="modal-header">',
								'<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>',
								'<h3 class="modal-title">'+title+'</h3>',
							'</div>',
							'<div class="modal-body">', 
								'<p style="font-size: 15px;text-align: center">'+message+'</p>',
							'</div>',
							'<div class="modal-footer">',
								'<button type="button" data-dismiss="modal" class="btn green  fa fa-check">确定</button>',
							'</div>',
						'</div>',
					'</div>',
				'</div>'
	           ].join('');
		var dialog = $(tmpl);
		dialog.on("hidden.bs.modal", function(){
			$(this).remove();
		});
		dialog.modal();
	},
	/*======提示模态框========
	 *title:标题 
	 *message：消息
	 *点击确定按钮刷新页面
	 * 
	 *=================*/
	succModal2:function(title,message,url){
		var goUrl=window.location.href;
		if(url){
			goUrl=url;
		}
		var tmpl = ['<div id="basic" class="modal fade" tabindex="-1" role="basic" data-backdrop="true" data-keyboard="true">',
		            '<div class="modal-dialog">',
			            '<div class="modal-content">',
							'<div class="modal-header">',
								'<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>',
								'<h3 class="modal-title">'+title+'</h3>',
							'</div>',
							'<div class="modal-body">', 
								'<p style="font-size: 15px;text-align: center">'+message+'</p>',
							'</div>',
							'<div class="modal-footer">',
								'<button type="button"  class="btn green  fa fa-check" onclick="CommonUtils.refresh(\''+goUrl+'\');">确定</button>',
							'</div>',
						'</div>',
					'</div>',
				'</div>'
	           ].join('');
		var dialog = $(tmpl);
		dialog.on("hidden.bs.modal", function(){
			$(this).remove();
		});
		dialog.modal();
	},
	refresh:function(gourl){
		$("#basic").modal('hide');
		$("#basic").empty();
		window.location.href=gourl;
	},
	/*======右下角淡入淡出提示========
	 *theme:主题，succ： 成功，fail： 失败|警告
	 *message：消息
	 * 
	 *=================*/
	notific8:function(theme,message){
		var setThems;
		if(theme=='succ'){
			setThems='teal';
		}else if(theme=='fail'){
			setThems='rudy';
		}else{
			setThems='lemon';
		}
		var settings = {
                theme: setThems,
                horizontalEdge: 'bottom',
                verticalEdge: 'right',
                life:'3000'
            };
        $.notific8('zindex', 11500);
        $.notific8($.trim(message), settings);
	}
};