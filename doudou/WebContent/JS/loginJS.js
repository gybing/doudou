function InitalTextfield(){
	document.getElementById("usernameID").value = "";
	document.getElementById("passwordID").value = "";
	//document.getElementById("schoolID").options[0].selected = true;
	document.getElementById("codefield").value = "";
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

function start_onMouseDown(){
	document.getElementById("startBtnID").src = "img/startBtn_c.png";
}

function start_onMouseMove(){
	document.getElementById("startBtnID").src = "img/startBtn_h.png";
}

function start_onMouseOut(){
	document.getElementById("startBtnID").src = "img/startBtn.png";
}
