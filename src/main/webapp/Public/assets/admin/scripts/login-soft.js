var Login = {

	doLogin : function() {
		if (!Login.validate()) {
			return;
		}
		
		var account = $('#account').val();
		var pwd = hex_md5($('#pwd').val());
		var vCode=$('#vCode').val().toLocaleLowerCase();
		
		$.ajax({
			type : "GET",
			url : BASE_URL + "/user/userLogin.do",
			data :{
				"account":account,
				"pwd":pwd,
				"vCode":vCode
			},
			dataType : "json",
			success : function(res) {
				if (res.code == "SUCC") {
					// 成功
					window.location.href = BASE_URL + "/user/loginSucc.do";
				} else if (res.code == "EXE") {
					alert(res.msg);
					
				} else if (res.code == "VCODE_ERROR") {
					alert(res.msg);
					
				} else if (res.code == "VCODE_NULL") {
					alert(res.msg);
					
				} else {
					alert(res.msg);
					
				}
			}
		});
	},
	validate : function() {
		var account = $('#account').val();
		var userPwd = $('#pwd').val();
		var vCode=$('#vCode').val();
		var flag = false;
		if (account) {
			flag = true;
		} else {
			flag = false;
			alert("请输入用户名");
		}
		if (userPwd) {
			flag = true;
		} else {
			flag = false;
			alert("请输入密码");
		}
		if (vCode) {
			flag = true;
		} else {
			flag = false;
			alert("请输入验证码");
		}
		return flag;
	},

	verifyCode : function() {

		$("#verifyCode").attr("src", BASE_URL + "/user/verifyCode.do?time=" + new Date().getTime());

	}
}
