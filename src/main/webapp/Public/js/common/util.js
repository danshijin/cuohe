//控制输入框小数点后两位
function checkNum(obj) {
	obj.on('keyup', function(event) {
		var $amountInput = $(this);
		// 响应鼠标事件，允许左右方向键移动
		event = window.event || event;
		if (event.keyCode == 37 | event.keyCode == 39) {
			return;
		}
		// 先把非数字的都替换掉，除了数字和.
		$amountInput.val($amountInput.val().replace(/[^\- \d.]/g,'').
		// 只允许一个小数点
		replace(/^\./g, "").replace(/\.{2,}/g, ".").
		// 只能输入小数点后两位
		replace(".", "$#$").replace(/\./g, "").replace("$#$", ".").replace(
				/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'));
	});
	obj.on('blur', function() {
		var $amountInput = $(this);
		// 最后一位是小数点的话，移除
		$amountInput.val(($amountInput.val().replace(/\.$/g, "")));
	});
}

//控制输入框
function checkNum_a(obj) {
	obj.on('keyup', function(event) {
		var $amountInput = $(this);
		// 响应鼠标事件，允许左右方向键移动
		event = window.event || event;
		if (event.keyCode == 37 | event.keyCode == 39) {
			return;
		}
		// 先把非数字的都替换掉
		$amountInput.val($amountInput.val().replace(/[^\-\d]/g,''));
	});
}

// 判断只能输入四位小数
function checkNum4(obj) {
	obj.on('keyup', function(event) {
		var $amountInput = $(this);
		// 响应鼠标事件，允许左右方向键移动
		event = window.event || event;
		if (event.keyCode == 37 | event.keyCode == 39) {
			return;
		}
		// 先把非数字的都替换掉，除了数字和.
		$amountInput.val($amountInput.val().replace(/[^\d.]/g, "").
		// 只允许一个小数点
		replace(/^\./g, "").replace(/\.{2,}/g, ".").
		// 只能输入小数点后四位
		replace(".", "$#$#$#$").replace(/\./g, "").replace("$#$#$#$", ".")
				.replace(/^(\-)*(\d+)\.(\d\d\d\d).*$/, '$1$2.$3'));
	});
	obj.on('blur', function() {
		var $amountInput = $(this);
		// 最后一位是小数点的话，移除
		$amountInput.val(($amountInput.val().replace(/\.$/g, "")));
	});

}

// 判断只能输入2位小数
function checkNum_2(obj) {
	obj.on('keyup', function(event) {
		var $amountInput = $(this);
		// 响应鼠标事件，允许左右方向键移动
		event = window.event || event;
		if (event.keyCode == 37 | event.keyCode == 39) {
			return;
		}
		// 先把非数字的都替换掉，除了数字和.
		$amountInput.val($amountInput.val().replace(/[^\- \d.]/g,'').
		// 只允许一个小数点
		replace(/^\./g, "").replace(/\.{2,}/g, ".").
		// 只能输入小数点后两位
		replace(".", "$#$").replace(/\./g, "").replace("$#$", ".").replace(
				/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'));
	});
	obj.on('blur', function() {
		var $amountInput = $(this);
		// 最后一位是小数点的话，移除
		$amountInput.val(($amountInput.val().replace(/\.$/g, "")));
	});

}

//判断只能输入五位小数
function checkNum5(obj) {
	obj.on('keyup', function(event) {
		var $amountInput = $(this);
		// 响应鼠标事件，允许左右方向键移动
		event = window.event || event;
		if (event.keyCode == 37 | event.keyCode == 39) {
			return;
		}
		// 先把非数字的都替换掉，除了数字和.
		$amountInput.val($amountInput.val().replace(/[^\d.]/g, "").
		// 只允许一个小数点
		replace(/^\./g, "").replace(/\.{2,}/g, ".").
		// 只能输入小数点后四位
		replace(".", "$#$#$#$#$").replace(/\./g, "").replace("$#$#$#$#$", ".")
				.replace(/^(\-)*(\d+)\.(\d\d\d\d\d).*$/, '$1$2.$3'));
	});
	obj.on('blur', function() {
		var $amountInput = $(this);
		// 最后一位是小数点的话，移除
		$amountInput.val(($amountInput.val().replace(/\.$/g, "")));
	});

}

