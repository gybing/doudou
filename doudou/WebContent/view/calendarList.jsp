<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Doudou, life around kids.</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 
<meta name="keywords" content="DouDou,兜兜,kids,communication,school,kindergarten,parents,children,tool,photos,events" /> 
<meta name="description" content="DouDou: Life around kids." /> 
<link rel="shortcut icon"  href="favicon.ico" /> 
<link type="text/css" rel="stylesheet" href="../CSS/homeCSS.css"/>	
<link type="text/css" rel="stylesheet" href="../CSS/CalendarCSS.css"/>
<link type="text/css" rel="stylesheet" href="../JS/lib/Pager.css"/> 
<link type="text/css" rel="stylesheet" href="../CSS/ui-darkness/jquery-ui-1.8.20.custom.css"/>
<link type="text/css" rel="stylesheet" href="../CSS/timepicker.css"/>	
<link type="text/css" rel="stylesheet" href="../JS/lib/jquery.multiselect.css"/> 
<script type="text/javascript" src="../JS/homeJS.js"></script>
<script type="text/javascript" src="../JS/lib/jquery.js"></script>
<script type="text/javascript" src="../JS/lib/json2.js"></script>
<script type="text/javascript" src="../JS/lib/jquery.form.js"></script>	
<script type="text/javascript" src="../JS/lib/jquery.validate.js"></script>
<script type="text/javascript" src="../JS/lib/jquery.pager.js"></script> 
<script type="text/javascript" src="../JS/lib/jquery-ui-1.8.20.custom.min.js"></script>
<script type="text/javascript" src="../JS/lib/jquery.multiselect.js"></script>
<script type="text/javascript" src="../JS/lib/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="../JS/lib/jquery.ui.datepicker-zh-CN.js"></script>
<script type="text/javascript" src="../JS/CalendarListJS.js"></script>
<script type="text/javascript">

</script>
</head>

<body>
	<script type="text/javascript " src="../JS/png.js"></script>
	<div class="upbar">
		<div class="logo"><img src="" /></div>
		<div class="nav">
				<ul class="Buttons">
					<li class="nav1"><input id="nav1" type="image" src="../img/menu/home.png" onmousemove="navHome_onMousemove()" onmouseout="navHome_onMouseout()" onclick=""/></li>
					<li class="split1"><img  src="../img/dot.png"/></li>
					<li class="nav2"><input id="nav2" type="image" src="../img/menu/photo.png" onmousemove="navPhoto_onMousemove()" onmouseout="navPhoto_onMouseout()" onclick=""/></li>
					<li class="split1"><img src="../img/dot.png"/></li>
					<li class="nav3"><input id="nav3" type="image" src="../img/menu/calender.png" onmousemove="navCal_onMousemove()" onmouseout="navCal_onMouseout()" onclick=""/></li>
					<li class="split1"><img src="../img/dot.png"/></li>
					<li class="nav4"><input  id="nav4" type="image" src="../img/menu/msg.png" onmousemove="navMes_onMousemove()" onmouseout="navMes_onMouseout()" onclick=""/></li>
					
					<li class="split1" id="split"><img src="../img/dot.png"/></li>
					<li class="nav6" id="manageExtra"><input  id="nav6" type="image" src="../img/menu/msg.png" onmousemove="navMes_onMousemove()" onmouseout="navMes_onMouseout()" onclick=""/></li>
					
					<!--<li class="split2"><img src="img/dot.png"/></li>
					<li class="nav3"><a href="#"><img src="img/btn.jpg"/></a></li>
					<li class="split3"><img src="img/dot.png"/></li>
					<li class="nav4"><a href="#"><img src="img/btn.jpg"/></a></li>-->
				</ul>
		</div>

		<div class="nav5"><input type="image" src="../img/menu/user.png" onmousemove="navAccount_onMousemove()" onmouseout="navAccount_onMouseout()" onclick="navAccount_onClick()"/><span>我的帐户</span></div>
	</div>
	<div class="main-container">
		<div class="left">
			<div class="portrait"><img id="portrait" src="../img/portrait_test.jpg" /></div>
			<div class="name" id="name">Mayaya</div>
			<div class="identity" id="identity">班级管理员@佳育苑</div>
		</div>
		<div class="middle">
		   <div id="enent-home-detail" class="event-home-detail">
		     <div class="QueryPanel">
			   <div class="title">
                    标题:
					<input type="text" id="title" />
               </div>
               <div class="caltype">
                    日历性质:
			        <select id="caltype">
                       <option value="School">校日历</option>
                       <option value="Class">班日历</option>
                    </select>
               </div>
               <div class="search" id="searchByTitle">
				查 询
			   </div>
			 </div>

		     <div class="calendarPanel">
		     	<div class="btns">
					<div class="button1">
					回到今天
			    	</div>
					<div class="button2">
					全部事件
			    	</div>
			    </div>
			    <div class="calendar">
             		<div id="datepicker"></div>
             	</div>
			 </div>
			 
		     <div class="event-list">
		        <div class="event-item">
				     <div class="event-date">
					 今天
					 </div>
					 <div class="event-content">
						<div class="event-content-title">游玩黑龙潭</div>
						<div class="event-content-time">9:00AM - 11:40AM</div>
					 </div>
				</div> 
		     </div>	
		     <div id="pager"></div>		
		   </div>

		</div>

		<div class="right">
		
			
			<div style="float:left; width:100%; height:99px;border-bottom: 1px solid #e0e0e0;">
		    	<div class="AddEventBtn">
					添加日历事件
				</div>
			</div>
			
		</div>
		<!-- Account submenu -->
			<div class="AccountSubmenu"style="margin-top:-105px;" id="AccountSubmenu">
				<ul>
					<li>
						<a href="#">密码修改</a>
					</li>
					<li>
						<a href="#">个人信息修改</a>
					</li>
					<li id="third">
						<a  href="#">常用语维护</a>
					</li>
				</ul>
			</div>

		<div class="NewMesLightBox">
			<div class="overlay"></div>
			<div class="new-conversation" style="width: 720px; margin-left: -360px; visibility: visible; top: 60px; ">
			   <div class="newConversation_header">
					<span>创建日历事件</span>
					<input type="image" src="../img/newConversation_close_n.png" class="newConversation_close" id="newConversation_close" />
			   </div>
				
				
			   <div class="newConversation_body">
			     <form id="addEvent-form">	
					<ul>
						<li>发送至学生</li>
						<li>
						<select id="atChildList_add" multiple="multiple">
						   <option value="1">option1</option>
						   <option value="2">option2</option>
						   <option value="3">option3</option>
						   <option value="4">option4</option>
						</select>
						<input type="hidden" id="atChildList" name="atChildList"/> 
						</li>
						<li></li>

						<li>标题</li>
						<li><input class="text-input" type="text" id="title_add" name="title" /></li>
						<li></li>
						<li>地点</li>
						<li><input class="text-input" type="text" id="location_add" name="locale" /></li>
						<li></li>
						<li>
							<span>开始时间</span><span><input class="beginTime" id="beginTime_add" name="beginTime"/></span><span></span>
						</li>
						<li>
							<span>结束时间</span><span><input class="endTime" id="endTime_add" name="endTime"/></span><span></span>
						</li>
						<li>
							<span>全天事件</span><span><input id="allday_add1" class="allday_add1" name="allday" type="radio" value="true" /></span><span>是</span>
					             <span><input id="allday_add2" name="allday" class="allday_add2" type="radio" value="false" /></span><span>否</span>
					             <span></span>
						</li>
						<li style="color:#d5d5d5;">- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -</li>
						<li style="margin-top:10px;">内容</li>
						<li><textarea id="content_add" name="content" rows="4" cols="60"></textarea></li>
						<li id="error"></li>
					</ul>
				 </form>
			   </div>
			   <div class="newConversation_footer">
			     <div class="NewMsgSubmit">发 送</div>
			   </div>
            </div>
		</div>
		
	</div>
</body>
</html>