<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 
<title>Doudou, life around kids.</title>
<meta name="keywords" content="DouDou,兜兜,kids,communication,school,kindergarten,parents,children,tool,photos,events" /> 
<meta name="description" content="DouDou: Life around kids." /> 
<link rel="shortcut icon"  href="favicon.ico" /> 
<link type="text/css" rel="stylesheet" href="CSS/loginCSS.css"/>	
<script type="text/javascript" src="JS/loginJS.js"></script>
<script type="text/javascript" src="JS/lib/jquery.js"></script>
<script type="text/javascript" src="JS/lib/jquery.form.js"></script>
<script type="text/javascript" src="JS/lib/jquery.validate.js"></script>
<script type="text/javascript" src="JS/lib/jquery.cookie.js"></script>
<script type="text/javascript" src="JS/lib/jquery.placeholder.js"></script>
<script type="text/javascript" src="JS/md5-min.js"></script>
<script type="text/javascript">
	
	$(document).ready(function(){
		$("#formID").validate({
			rules:{
				username:"required",
				password:"required",
				vericode:"required"
			},
			messages:{
				username:"please enter your username.",
				password:"Please enter your password."
			}
		});
		
		var options = { 
	            //target:        '#output2',   // target element(s) to be updated with server response 
	            beforeSerialize: md5PWD,
	            beforeSubmit:  showRequest,  // pre-submit callback 
	            success:       showResponse,  // post-submit callback 

	            //url:       'WebLogin/login.do' ,        // override for form's 'action' attribute 
	            //type:      'post'      // 'get' or 'post', override for form's 'method' attribute 
	            //dataType:  null        // 'xml', 'script', or 'json' (expected server response type) 
	     
	            // $.ajax options can be used here too, for example: 
	            //timeout:   3000 
	        }; 
	    
	    $('#formID').submit(function() { 
	        // inside event callbacks 'this' is the DOM element so we first 
	        // wrap it in a jQuery object and then invoke ajaxSubmit 
	        $(this).ajaxSubmit(options); 
	 
	        // always return false to prevent standard browser submit and page navigation 
	        return false; 
	    }); 
	    
	    if($.cookie("stayon")=="true"){
	    	$("#stayon").attr("checked",true);
	    	$("#usernameID").val($.cookie("username"));
	    	$("#passwordID").val($.cookie("password"));
	    }
		
	});
	
	function md5PWD(jqForm, options){
		var pwd = $('#formID :password').fieldValue()[0];
		var md5PWD = hex_md5(pwd);
		$('#hiddenpassword').val(md5PWD);
	}
	 
	// pre-submit callback 
	function showRequest(formData, jqForm, options) { 
		
		var usernameValue = $('input[name=username]').fieldValue(); 
	    var passwordValue = $('input[name=password]').fieldValue(); 
	 	var codeValue = $('input[name=code]').fieldValue();
	    // usernameValue and passwordValue are arrays but we can do simple 
	    // "not" tests to see if the arrays are empty 
	    if (!usernameValue[0] || !passwordValue[0]) { 
	        $(".notice").html("请输入用户名和密码！");
	        $(".notice").show();
	        return false; 
	    }
	    else if (!codeValue[0]) {
	    	$(".notice").html("请输入验证码");
	        $(".notice").show();
	        return false; 
	    }
	    else {
	    	 var queryString = $.param(formData); 
	    	 return true; 
	    }
		
	   
	} 
	 
	// post-submit callback 
	function showResponse(responseText, statusText, xhr, $form)  {  
	    if(responseText == -1){
	    	 $(".notice").html("用户名和密码不匹配");
		     $(".notice").show();
		     reloadVerifyCode();
	    }
	    else if (responseText == -2){
	    	 $(".notice").html("验证码输入有误");
		     $(".notice").show();
		     reloadVerifyCode();
	    }
	    else if(responseText == 1){
	    	alert("Login succeed!");
	    	//login success jump
	    	//window.location.href = "http://localhost:8080/Mayaya_Spring/jsp/operationMenu.jsp";
	    }
	    
	} 
	
	function saveToCookie(){
		if($("#stayon").attr("checked")==true){
			var username = $("#usernameID").val();
			var password = $("#passwordID").val();
			alert(username+","+password);
			$.cookie("stayon","true",{expire:30});
			$.cookie("username",username,{expire:30});
			$.cookie("password",password,{expire:30});
		}
		else { 
			$.cookie("stayon", "false", { expires: -1 }); 
			$.cookie("username", '', { expires: -1 }); 
			$.cookie("password", '', { expires: -1 }); 
		} 
	}
	
	function reloadVerifyCode(){
        var timenow = new Date().getTime();                          
		document.getElementById("codepic").src="WebLogin/getIdentifyCode.do?d="+timenow;
	}
	 
</script>

</head>
<body  onload="InitalTextfield()" onresize="resizeWindow()">
	<script type="text/javascript " src="JS/png.js"></script>
	<div class="main-container">
		<div class="titleimg" id="titleimgID"><img src="img/titleimg.png"/></div>
		<form id="formID" method="post" action="WebLogin/login.do">
			<div style="float:left; width:500px;">
				<div class="notice" id="noticeID">*Error!</div>
			</div>
			<div style="float:left; width:500px;">
				<input type="text" class="username" id="usernameID" name="username" placeholder="用户名"></input>
				<input type="password" class="password" id="passwordID" name="passwd" placeholder="密码"></input>
				<input type="hidden" name="password" id="hiddenpassword" />
			</div>
			
			<div style="float:left; width:500px;">
				<input type="text" class="code" id="codefield" name="code" placeholder="验证码"></input>
				<div id="codepic_field" class="codepic_field"><img id="codepic" src="WebLogin/getIdentifyCode.do"/></div>
				<a href="javascript:reloadVerifyCode();">看不清楚？</a>
			</div>
			
			<div style="float:left; width:500px;">
				<input type="checkbox" class="stayon" id="stayon" onclick="saveToCookie()">记住登录状态</input> | 
				<a href="">哦不，忘记密码了？</a>
			</div>
			
			<input type="image" src="img/startBtn.png" class="start" id="startBtnID" onmousedown="start_onMouseDown()" onmousemove="start_onMouseMove()" onmouseout="start_onMouseOut()"></input>
			
		</form>
	</div>
</body>
</html>