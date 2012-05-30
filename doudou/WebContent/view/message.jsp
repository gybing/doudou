<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 
<title>Doudou, life around kids.</title>
<meta name="keywords" content="DouDou,兜兜,kids,communication,school,kindergarten,parents,children,tool,photos,events" /> 
<meta name="description" content="DouDou: Life around kids." /> 
<link rel="shortcut icon"  href="favicon.ico" />
<link type="text/css" rel="stylesheet" href="../CSS/ui-darkness/jquery-ui-1.8.20.custom.css"/>
<link type="text/css" rel="stylesheet" href="../JS/lib/Pager.css"/> 
<link type="text/css" rel="stylesheet" href="../JS/lib/jquery.multiselect.css"/> 
<link type="text/css" rel="stylesheet" href="../CSS/homeCSS.css"/>	
<link type="text/css" rel="stylesheet" href="../CSS/MlistCSS.css"/>
<script type="text/javascript" src="../JS/lib/jquery.js"></script>
<script type="text/javascript" src="../JS/jquery-ui-1.8.20.custom.min.js"></script>	
<script type="text/javascript" src="../JS/lib/jquery.form.js"></script>		
<script type="text/javascript" src="../JS/lib/jquery.pager.js"></script> 
<script type="text/javascript" src="../JS/lib/jquery.ui.multiselect.js"></script>
<script src="../JS/lib/jquery.bgiframe.js" type="text/javascript"></script>
<!--<script type="text/javascript" src="../JS/lib/jquery.multiselect.js"></script>-->	
<script type="text/javascript" src="../JS/homeJS.js"></script>
<script type="text/javascript" src="../JS/MlistJS.js"></script>
<script type="text/javascript " src="../JS/png.js"></script>

<script type="text/javascript">
$(document).ready(function(){
	
	$("#pager").pager({ pagenumber: 1, pagecount: 10, buttonClickCallback: PageClick });
	
	$.post("../Message/getClassMessageList.do",{pageIndex:1, perPageCount:10},function(result){
        //$("span").html(result);
        $.each(result,function(i,n){
        	
        });
      });
	/**
	$('.w-options').multiselect({
	    label: '-- Send to --',
	    minWidth: 400,
	    maxWidth: 400,
	    scroll: 100,
	    //deselect:function() { console.log(this.value, 'diselected'); },
	    //select:function() { console.log(this.value, 'selected'); }
	 });
	*/
	
	//form ajax submit
	var options = { 
	        //target:        '#output1',   // target element(s) to be updated with server response 
	        beforeSerialize: enTaged,
	        beforeSubmit:  showRequest,  // pre-submit callback 
	        success:       showResponse,  // post-submit callback 
	 
	        // other available options: 
	        url:       '../Message/addMessage.do',         // override for form's 'action' attribute 
	        type:      'post'        // 'get' or 'post', override for form's 'method' attribute 
	        //dataType:  null        // 'xml', 'script', or 'json' (expected server response type) 
	        //clearForm: true        // clear all form fields after successful submit 
	        //resetForm: true        // reset the form after successful submit 
	 
	        // $.ajax options can be used here too, for example: 
	        //timeout:   3000 
	    }; 
	 
	    // bind form using 'ajaxForm' 
	    $('#msgForm').ajaxForm(options); 
	    
	    
	    var edit_options = {
	    	beforeSerialize: enTaged,
	 	    beforeSubmit:  showRequest,  // pre-submit callback 
	 	    success:       showResponse,  // post-submit callback 
	 
	 	    url:       '../Message/editMessage.do',        
	 	    type:      'post'	
	    };
	    
	    $('#editMsgForm').ajaxForm(edit_options);
	    
	    $("#delSingleMsg").click(function(){
	    	  htmlobj=$.ajax({url:"../Message/deleteSingleMessage.do",async:false});
	    	  $("#myDiv").html(htmlobj.responseText);
	    	  });
});

function enTaged(){
	var childrenList = $('select#tagingList').val();
	alert(childrenList);
	//var array = childrenList.split(",");
	//var obj2 = "1,2,3".split(",");
	//alert(obj2);
	//for(var i=0;i <array.length;i++) 
	//	alert(array[i]); 
	$('#taged').val(childrenList);
}

//pre-submit callback 
function showRequest(formData, jqForm, options) { 
	var queryString = $.param(formData); 
    alert('About to submit: \n\n' + queryString); 
    return true; 
} 
 
// post-submit callback 
function showResponse(responseText, statusText, xhr, $form)  { 
    alert('发送成功！'); 
    $('#msgForm')[0].reset();
	$("#NewMesLightBox").css("display","none");
	
	//refresh message list
	$.post("../Message/getClassMessageList.do",{pageIndex:1, perPageCount:10},function(result){
        //$("span").html(result);
        $.each(result,function(i,n){
        	
        });
      });
} 

PageClick = function(pageclickednumber) 
{
	//window.location.href="jQuery_Page.aspx?page="+pageclickednumber;
	//alert("Page "+pageclickednumber+" was clicked");
    $("#pager").pager({ pagenumber: pageclickednumber, pagecount: 10, buttonClickCallback: PageClick });
    
    $.post("../Message/getClassMessageList.do",{pageIndex:pageclickednumber, perPageCount:10},function(result){
        //$("span").html(result);
        $.each(result,function(i,n){
        	
        });
      });
    
    htmlStr = "<div class='MsgItem' id='MsgItem_3'><input type='checkbox' class='MsgItem_CheckBox' />";
    htmlStr += "<div style='float:left;width:60%;min-width: 400px;margin-right: 10px;margin-top: 10px;'>";
    htmlStr += "<div class='MsgItem_notice'><span class='sender' id='sender'>王老师 </span><span>发送了一个</span><span class='noticeclass' id='noticeclass'> 开学通知</span></div>";
    htmlStr += "<div class='MsgItem_title' id='MsgItemTitle'>2013年开学</div><div class='MsgItem_date' id='MsgItemDate'>发送日期：2012-11-23</div>";
    htmlStr += "</div></div>";
    $("#MsgListContainer").html(htmlStr);
};


</script>

</head>
<body>
	<!-- 正常页面部分 -->
	<div class="upbar">
		<div class="logo"><img src="" /></div>
		<div class="nav">
				<ul class="Buttons">
					<li class="nav1"><input type="image" src="../img/btn_navihist_N.png" onmousemove="navHome_onMousemove()" onmouseout="navHome_onMouseout()" onclick=""/></li>
					<li class="split1"><img src="../img/dot.png"/></li>
					<li class="nav2"><input type="image" src="../img/btn_navihist_N.png" onmousemove="navPhoto_onMousemove()" onmouseout="navPhoto_onMouseout()" onclick=""/></li>
					<li class="split1"><img src="../img/dot.png"/></li>
					<li class="nav3"><input type="image" src="../img/btn_navihist_N.png" onmousemove="navCal_onMousemove()" onmouseout="navCal_onMouseout()" onclick=""/></li>
					<li class="split1"><img src="../img/dot.png"/></li>
					<li class="nav4"><input type="image" src="../img/btn_navihist_N.png" onmousemove="navMes_onMousemove()" onmouseout="navMes_onMouseout()" onclick=""/></li>
					<li class="split1"><img src="../img/dot.png"/></li>
					<li class="nav11"><input type="image" src="../img/btn_navihist_N.png" onmousemove="navInfo_onMousemove()" onmouseout="navInfo_onMouseout()" onclick=""/></li>
					
					<!--<li class="split2"><img src="img/dot.png"/></li>
					<li class="nav3"><a href="#"><img src="img/btn.jpg"/></a></li>
					<li class="split3"><img src="img/dot.png"/></li>
					<li class="nav4"><a href="#"><img src="img/btn.jpg"/></a></li>-->
				</ul>
		</div>
		<div class="nav5"><input type="image" src="../img/btn_navihist_N.png" onmousemove="navAccount_onMousemove()" onmouseout="navAccount_onMouseout()" onclick=""/></div>	
	</div>
	<div class="main-container">
		<div class="left">
			<div class="portrait"><img id="portrait" src="../img/portrait_test.jpg" /></div>
			<div class="name" id="name">Mayaya</div>
			<div class="identity" id="identity">班级管理员@佳育苑</div>
		</div>
		<div class="middle">
			<!-- Message List -->
		 	<div class="MsgListConPanel" id="MsgListConPanel">
			<form>
			<div class="FilterPanel">
				<div class="fact">
					性质：
					<select id="fact" onfocus="fact_onFocus()">
						<option value="1">1</option>
						<option value="2">2</option>
					</select>
				</div>
				<div class="category">
					类别：
					<select id="gategory" onfocus="gategory_onFocus()">
						<option value="1">1</option>
						<option value="2">2</option>
					</select>
				</div>
				<div class="starttime">
					开始时间：
					<input type="text" class="inputDatef" id="inputDatef" value="01/01/2012" />
				</div>
				<div class="endtime">
					结束时间：
					<input type="text" class="inputDatet" id="inputDatet" value="01/01/2012" />
				</div>
				<div class="title">
					标题：
					<input type="text" id="title" onfocus="title_onFocus()" />
				</div>
				<div class="publisher">
					发布人：
					<select id="publisher" onfocus="publisher_onFocus()">
						<option value="1">1</option>
						<option value="2">2大法师</option>
					</select>
				</div>
				<div class="feedback">
					<input type="radio" id="radio_feedback" />
					需要反馈
				</div>
			</div>
			</form>
			<div class="search" onclick="search_onClick()" onmousemove="search_onMouseMove()" onmouseout="search_onMouseOut()">
				开始查询
			</div>
			<div class="MsgDetailList">
				<div class="MsgListHeader">
					<span>消息列表</span>
					<div class="delMsg" onclick="delMsg_onClick()" onmousemove="delMsg_onMouseMove()" onmouseout="delMsg_onMouseOut()"></div>
				</div>
				<div class="MsgListBody">
				<form>
					<div class="MsgItem" id="MsgItem_1">
						<input type="checkbox" class="MsgItem_CheckBox" />
						<div style="float:left;width:60%;min-width: 400px;margin-right: 10px;margin-top: 10px;">
							<div class="MsgItem_notice">
								<span class="sender" id="sender">张老师</span>
								<span>发送了一个</span>
								<span class="noticeclass" id="noticeclass">开学通知</span>
							</div>
							<div class="MsgItem_title" id="MsgItemTitle">2013年开学</div>
							<div class="MsgItem_date" id="MsgItemDate">发送日期：2012-11-23</div>
							<!--<div class="MsgItem_content" id="MsgItemContent">学校定于2013年8月17日上午学校定于2013年8月17日上午学校定于2013年8月17日上午学校定于2013年8月17日上午学校定于2013年8月17日上午学校定于2013年8月17日上午学校定于2013年8月17日上午9:00blabladla</div>-->
						</div>
						<div style="float:right;width: 20%;min-width:150px;margin-right: 0px;margin-top: 10px;">
							
							<div class="FeedbackCon">
								<div class="feedbacked">应反馈</div>
								<div class="NumOfFeedback" id="NumOfFeedback">87</div>
							</div>
							<div class="Unfeedback">
								<div class="unfeedbacked">未反馈</div>
								<div class="NumOfUnfeedback" id="NumOfUnfeedback">0</div>
							</div>
						</div>
					</div>
				</form>
				</div>
			</div>
			</div>
			<!-- /////////-->
			
			<!-- One Message -->
			<div class="OneMessageCon" id="OneMessageCon">
			<div class="MessageTool">
					<div class="returnList" id="returnList">返回信息列表</div>
					<div class="PreMes" id="preMsg">上一封</div>
					<div class="NextMes" id="nextMsg">下一封</div>
					<!-- <input type="image" src="" class="editMsg" id="editMsg"/> -->
					<div class="editMsg" id="editMsg">编辑</div>
					<div class="delSingleMsg" id="delSingleMsg">删除</div>
					<input type="image" src="" class="delsingleMsg"/>
			</div>
			<div class="MessageBody">
				<ul>
					<li>
						<span class="MessageTitle">2013年开学</span>
					</li>
					<li>
						<span class="MessageContent">学校定于2013年8月17日上午9:00举行...</span>
					</li>
					<li>
						<span class="MessageClass">类别：活动通知</span>
						<span class="MessageFact">性质：班级通知</span>
						<span class="SendTime">发送时间：2012-3-1 20:38</span>
					</li>
				</ul>
			</div>
			</div>
			<!-- /////// -->
			
			<!-- Edit One Message -->
			<div class="EditOneMessage" id="EditOneMessage">
			<div class="EditMesTool">
				<form>
					<span class="save" id="saveBtn">保存修改</span><span class="cancel" id="cancelBtn">取消修改</span>
				</form>
			</div>
			<div class="EditMesList">
			<form id="editMsgForm">
			<input type="submit" value="保存修改"/>
				<ul>
					<li>
						<!-- 选择孩子 -->
						<div><select multiple="multiple" class="w-options">			
  						<c:forEach var="entry" items="${sessionData.tagedInfoMap}" varStatus="theCount">
							<c:forEach var="ccEntry" items="${entry.value.classChildMap}">
							   <c:forEach var="child" items="${ccEntry.value}">
							   		<option value="${child.id}">${child.firstName}</option>
							   </c:forEach>
							</c:forEach>
						</c:forEach>
						</select></div>
					</li>
					<li>
					必须反馈／不必须反馈
					</li>
					<li>
						<input type="text" value="标题" class="editTitle" name="title"/>
					</li>
					<li>
						<select class="editSelection" name="type">
							<option value="1">活动</option>
							<option value="2">缴费通知</option>
							<option value="3">慈善</option>
						</select>
					</li>
					<li>
						<textarea class="editContent" name="content"></textarea>
					</li>
				</ul>
				
			</form>
			</div>
			</div>
			

		</div>
		<!-- ////// -->
		
		<div class="right">
			<div class="AddMsg" id="AddMsg">添加新信息</div>
			<!-- 右侧选孩子的列表 -->
			<div class="SelectKidsList" id="SelectKidsR"></div>
			<div class="FeedBacksAll" id="FeedBackAll">
				<div class="FeedBacksAll_Header">本信息需要反馈</div>
				<div class="FeedBackItem"><span>学生</span><span>家长反馈</span></div>
			</div>
		</div>
		
		
		<!-- 添加新信息的页面部分-->
		<div class="NewMesLightBox" id="NewMesLightBox">
			<div class="overlay"></div>
			<div class="new-conversation" style="width: 720px; margin-left: -360px; visibility: visible; top: 60px; ">
				<div class="newConversation_header">
					<span>创建新信息</span>
					<div class="newConversation_close"><img src="" /></div>
				</div>
				<form id="msgForm">
				<div class="newConversation_body">
					<ul>
						<li>发送至学生</li>
						<li>
						<input type="hidden" id="taged" name="atChildList"/>
						<select multiple="multiple" class="tagingList" id="tagingList">
						<c:forEach var="entry" items="${sessionData.tagedInfoMap}" varStatus="theCount">
							<c:forEach var="ccEntry" items="${entry.value.classChildMap}">
							   <c:forEach var="child" items="${ccEntry.value}">
							   		<option value="${child.id}">${child.firstName}</option>
							   </c:forEach>
							</c:forEach>
						</c:forEach>
						</select></li>
						<!--<li></li> 选孩子的框-->
						<li>类别<select name="messageType" id="type">
								<option value="1">活动</option>
								<option value="2">缴费通知</option>
								<option value="3">慈善</option>
								</select>
							<span><input type="checkbox" class="isNeedFeedback" name="isNeedFeedback" value="true"/>是否必须反馈 </span>
						</li>
						<li>标题</li>
						<li><input type="text" class="NewMsgCaption" name="title"/></li>
						<li>内容</li>
						<li><textarea name="content" class="msgContent" id="content"></textarea></li>
					</ul>
				</div>
				<div class="newConversation_footer">
					<div class="NewMsgSubmit"><input type="submit" value="发送"/></div>
				</div>
				</form>
			</div>
		</div>
		<!-- ///////-->
	</div>
</body>
</html>