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
<link type="text/css" rel="stylesheet" href="../CSS/ui-darkness/jquery-ui-1.8.20.custom.css"/>
<link type="text/css" rel="stylesheet" href="../CSS/timepicker.css"/>	
<link type="text/css" rel="stylesheet" href="../JS/lib/jquery.multiselect.css"/> 
<script type="text/javascript" src="../JS/homeJS.js"></script>
<script type="text/javascript" src="../JS/lib/jquery.js"></script>
<script type="text/javascript" src="../JS/lib/jquery.form.js"></script>	
<script type="text/javascript" src="../JS/lib/jquery.validate.js"></script>
<script type="text/javascript" src="../JS/lib/jquery-ui-1.8.20.custom.min.js"></script>
<script type="text/javascript" src="../JS/lib/jquery.multiselect.js"></script>
<script type="text/javascript" src="../JS/lib/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="../JS/lib/jquery.ui.datepicker-zh-CN.js"></script>
<script type="text/javascript" src="../JS/CalendarEditJS.js"></script>
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
		    
		   <div id="event-mod-detail" class="eventitem_detail">
		     <div id="event-mod-header" class="eventitem_header">
		     	<ul>
			      <li id="saveMod" class="returnCal">
					 保存修改
			      </li>
				  <li id="cancelMod" class="returnCal">
					  取 消
				  </li>
				</ul>
			 </div>

			 <div id="event-mod-body" class="eventmod_body">   
			   <form id="modEvent-form">	
					<ul>
						<li>发送至学生</li>
						<li>
						<input type="hidden" id="id_mod" name="eventId" />
						<select id="atChildList_mod" multiple="multiple">
						   <option value="1">option1</option>
						   <option value="2">option2</option>
						   <option value="3">option3</option>
						   <option value="4">option4</option>
						</select>
						<input type="hidden" id="atChildList" name="atChildList"/>
						</li>
						<li></li>

						<li>标题</li>
						<li><input class="text-input1" type="text" id="title_mod" name="title" /></li>
						<li></li>
						<li>地点</li>
						<li><input class="text-input1" type="text" id="location_mod" name="locale" /></li>
						<li></li>
						<li>
							<span>开始时间</span><span><input class="beginTime" id="beginTime_mod" name="beginTime"/></span><span></span>
						</li>
						<li>
							<span>结束时间</span><span><input class="endTime" id="endTime_mod" name="endTime"/></span><span></span>
						</li>
						<li>
							<span>全天事件</span><span><input id="allday_mod1" class="allday_add1" name="allday" type="radio" value="true" /></span><span>是</span>
					             <span><input id="allday_mod2" name="allday" class="allday_add2" type="radio" value="false" /></span><span>否</span>
					             <span></span>
						</li>
						<li style="margin-top:10px;">内容</li>
						<li><textarea id="content_mod" name="content" rows="4" cols="60"></textarea></li>
						<li id="error"></li>
					</ul>
				 </form>

			 </div>
		   </div>

		</div>

		<div class="right">
		
		</div>
		<!-- Account submenu -->
			<div class="AccountSubmenu" id="AccountSubmenu">
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
		
	</div>
</body>
</html>