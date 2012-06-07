<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>  
<%@ page import="doudou.vo.Event" %> 
<%@ page import="java.util.*" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
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
<link type="text/css" rel="stylesheet" href="../JS/lib/jquery.multiselect.css"/> 
<script type="text/javascript" src="../JS/homeJS.js"></script>
<script type="text/javascript" src="../JS/lib/jquery.js"></script>
<script type="text/javascript" src="../JS/lib/jquery-ui-1.8.20.custom.min.js"></script>
<script type="text/javascript" src="../JS/lib/jquery.multiselect.js"></script>
<script type="text/javascript" src="../JS/CalendarItemJS.js"></script>
<script type="text/javascript">

</script>
</head>
<%
Event eventItem = (Event)request.getAttribute("eventItem");
String[] childs = eventItem.getAtChildList().split(",");
List<String> childList =  Arrays.asList(childs);
%>
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

		   <div id="event-item-detail" class="eventitem_detail">
		     <div id="event-item-header" class="eventitem_header">
		     	<ul>
			      <li id="returnCal" class="returnCal">
					 返回日历列表
			      </li>
				  <li class="preEvent">
					  <input id="preEvent"  type="image" src="../img/pre_n.png" />
				  </li>
				  <li class="nextEvent">
					  <input id="nextEvent" type="image" src="../img/next_n.png" />
				  </li>
				  <li class="delEvent">
					   <input id="delEvent" type="image" src="../img/del_n.png" />
				  </li>
				  <li class="modEvent">
					   <input id="modEvent" type="image" src="../img/edit_n.png" />
				  </li>
				  
				</ul>
			 </div>

			 <div id="event-item-body" class="eventitem_body">
			      <input type="hidden" id="event-item-id" name="event-item-id" value="<%=eventItem.getId() %>"/>
				  <h3 id="event-item-title"><%=eventItem.getTitle() %></h3>
				  <ul>
				  	<li id="location-div">
				  		<span><img src="../img/locationLabel.png" /></span><span id="event-item-location"><%=eventItem.getLocation() %></span>
				  	</li>
				  	<li id="time-div">
				  		<span><img src="../img/dateLabel.png" /></span><span id="event-item-time"><%=eventItem.getBeginTimeString() %> -- <%=eventItem.getEndTimeString() %></span>
				  	</li>
				  	<li id="allday-div">
				  		<span><img src="../img/clockLabel.png" /></span>
				  		<span id="event-item-allday">
				  		<%if(eventItem.isAllday()){
				  			out.print("全天事件");
				  		} else{
				  			out.print("非全天事件");
				  		}%>
				  		</span>
				  	</li>
				  	<li class="splitline"></li>
				  	<li id="event-item-content">
				  		<%=eventItem.getContent() %>
				  	</li>
				  	<li class="splitline"></li>
				  	<li id="type-div">
				  		<span><img src="../img/tagLabel.png" /></span>
				  		<span id="event-item-caltype">
				  		<%if(eventItem.getPublishLevel().toString().equalsIgnoreCase("school")){
				  			out.print("校日历");
				  		} else{
				  			out.print("班日历");
				  		}%>
				  		</span>
				  	</li>
				  </ul>
			 </div>
		   </div>

		</div>

		<div class="right">
			<!--select kids-->
			<div style="color:black;">谁收到了这个信息</div>
			<div class="childlistAdd">
			   <ul id="childlist1">
			         <%for(int i=0;i<childList.size();++i) {
			        	 out.print("<li>"+childList.get(i)+"</li>");
			         }%>
			   </ul>
			   <ul id="childlist2">
			   </ul>
			</div>
			<div>
			<select id="atChildList_add" multiple="multiple">
						   <c:forEach var="entry" items="${SessionData.tagedInfoMap}" varStatus="theCount">
							<c:forEach var="ccEntry" items="${entry.value.classChildMap}">
							   <c:forEach var="child" items="${ccEntry.value}">
							        <c:set var="childId" scope="page" value="${child.id}"/>
							        <%String childId = ((Integer)pageContext.getAttribute("childId")).toString();%>
							        <%if(!childList.contains(childId)){ %>
							   		<option value="${child.id}" >${child.firstName}</option>
							   		<%} %>
							   </c:forEach>
							</c:forEach>
						   </c:forEach>
			</select>
			</div>
			
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