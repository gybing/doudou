<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>EtonKids-iTeaching</title>
<link href="css/main.css" rel="stylesheet" type="text/css"/>
<link href="resources/stylesheets/tabs.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="js/jquery.form.js"></script>
<script type="text/javascript" src="js/jquery.validate.js"></script>
<script type="text/javascript" src="js/md5-min.js"></script>
<style>
	#container {
	/*background-color:#38b4e7;*/
	background: url(css/images/ui-bg_fine-grain_10_eceadf_60x60.png);
	width:960px;
	height: 640px;
	margin:0 auto;
	
	filter:progid:DXImageTransform.Microsoft.Shadow(color=#909090,direction=120,strength=4);
	-moz-box-shadow: 2px 2px 10px #909090;
	-webkit-box-shadow: 2px 2px 10px #909090;
	box-shadow:2px 2px 10px #909090;

	}

	#login {
	position:relative;
	top:260px;
	left:280px;
	}
	
	#container img {
	width: 30%;
	height: auto;
	float:left;
	position:relative;
	top:140px;
	left: 140px;
	}


</style>

<script type="text/javascript">
	
	$(document).ready(function(){
		document.getElementById("noticeID").style.display = "none";
		
		var options = { 
	            //target:        '#output2',   // target element(s) to be updated with server response 
	            beforeSerialize: md5PWD,
	            beforeSubmit:  showRequest,  // pre-submit callback 
	            success:       showResponse // post-submit callback 

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
	    
	    
		
	});
	
	function md5PWD(jqForm, options){
		var pwd = $('#formID :password').fieldValue()[0];
		var md5PWD = hex_md5(pwd);
		$('#hiddenpassword').val(md5PWD);
	}
	 
	// pre-submit callback 
	function showRequest(formData, jqForm, options) { 
		
		var usernameValue = $('input[name=userName]').fieldValue(); 
	    var passwordValue = $('input[name=passWord]').fieldValue(); 
	    // usernameValue and passwordValue are arrays but we can do simple 
	    // "not" tests to see if the arrays are empty 
	    if (!usernameValue[0] || !passwordValue[0]) { 
	        $(".notice").html("Please enter your email or password");
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

		if(responseText == null){
	    	 $(".notice").html("Wrong email or password");
		     $(".notice").show();
	    }
	    
	    else if(responseText == 1){
	    	$(".notice").hide();
	    	//login success jump
	    	window.location.href = "pages/Index.html";
	    }
	    else if(responseText == 0){
	    	$(".notice").html("You have no authorization to login.");
		     $(".notice").show();
	    }
	    
	} 
	
	
	
</script>

</head>

<body>
	<div id="container" style="margin-top: 40px">
		<img src="img/etonkids-logo.png" />
		<form id="formID" method="post" action="etonUser.do?action=loginForWeb">
			<div id="login">
				<div class="notice" id="noticeID" style="color:red;"></div>
				<div>
					<input type="text" class="box" id="usernameID" name="userName" placeholder="Email"></input>
				</div>
				<div>
					<input type="password" class="box" id="passwordID" name="passWord" placeholder="Password"></input>
					<input type="hidden" name="passWd" id="hiddenpassword" />
				</div>
				<div>
					<input type="submit" value="Sign In" />
				</div>
			</div>
	  </form>
    </div>
</body>
</html>