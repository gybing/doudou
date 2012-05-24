function InitalTextfield(){
	document.getElementById("usernameID").value = "用户名";
	document.getElementById("passwordID").value = "";
	//document.getElementById("schoolID").options[0].selected = true;
	document.getElementById("codefield").value = "验证码";
	document.getElementById("noticeID").style.display = "none";
	var widthClient = document.body.clientWidth;
	widthClient = widthClient-500;
	if(widthClient >=0)
	{
		var marginsize = widthClient/2;
		document.getElementById("formID").style.marginLeft = marginsize.toString()+"px";
		document.getElementById("titleimgID").style.marginLeft = marginsize.toString()+"px";
	}
	else{
		document.getElementById("formID").style.marginLeft = "0px";
		document.getElementById("titleimgID").style.marginLeft = "0px";
	}
}
function resizeWindow(){
	var widthClient = document.body.clientWidth;
	widthClient = widthClient-500;
	if(widthClient >=0)
	{
		var marginsize = widthClient/2;
		document.getElementById("formID").style.marginLeft = marginsize.toString()+"px";
		document.getElementById("titleimgID").style.marginLeft = marginsize.toString()+"px";
	}
	else{
		document.getElementById("formID").style.marginLeft = "0px";
		document.getElementById("titleimgID").style.marginLeft = "0px";
	}
}

function un_onFocus(){
	if(document.getElementById("usernameID").value == "用户名"){
		document.getElementById("usernameID").style.outline = "0 none";
		document.getElementById("usernameID").style.color = "#949494";
		document.getElementById("usernameID").value = "";
	}
}

function un_onBlur(){
	if(document.getElementById("usernameID").value == ""){
		document.getElementById("usernameID").style.color = "#c1c1c1";
		document.getElementById("usernameID").value = "用户名";
	}
}

function code_onFocus(){
	if(document.getElementById("codefield").value == "验证码"){
		document.getElementById("codefield").style.outline = "0 none";
		document.getElementById("codefield").style.color = "#949494";
		document.getElementById("codefield").value = "";
	}
}

function code_onBlur(){
	if(document.getElementById("codefield").value == ""){
		document.getElementById("codefield").style.color = "#c1c1c1";
		document.getElementById("codefield").value = "验证码";
	}
}


function psw_onFocus(){
	document.getElementById("passwordID").style.outline = "0 none";
	document.getElementById("passwordID").style.color = "#949494";
	document.getElementById("passwordlabelID").style.display = "none";
}

function psw_onBlur(){
	if(document.getElementById("passwordID").value == ""){
		document.getElementById("passwordID").style.color = "#c1c1c1";
		document.getElementById("passwordlabelID").style.display = "block";
	}
}
function start_onMouseDown(){
	document.getElementById("startBtnID").src = "img/startBtn_c.png";
}

function pswla_onClick(){
	document.getElementById("passwordlabelID").style.display = "none";
	document.getElementById("passwordID").focus();
}
function start_onClick(){
	
}
function start_onMouseMove(){
	document.getElementById("startBtnID").src = "img/startBtn_h.png";
}
function start_onMouseOut(){
	document.getElementById("startBtnID").src = "img/startBtn.png";
}
