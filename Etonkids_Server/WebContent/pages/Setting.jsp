<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
		<title>jQuery UI Example Page</title>
		<link type="text/css" href="../css/jquery-ui.css" rel="stylesheet" />
		<script type="text/javascript" src="../js/jquery-1.8.0.min.js"></script>
		<script type="text/javascript" src="../js/jquery-ui-1.8.23.custom.min.js"></script>
		
		<!-- extJS -->
		<link rel="stylesheet" type="text/css" href="../ext/resources/css/ext-all.css"/>
		<link rel="stylesheet" type="text/css" href="../ext/resources/css/ext-patch.css"/>
		<script type="text/javascript" src="../ext/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="../ext/ext-all.js"></script>
		
		<!-- lovcombo -->
		<link rel="stylesheet" type="text/css" href="../css/Ext.ux.form.LovCombo.css">
		<link rel="stylesheet" type="text/css" href="../css/empty.css" id="theme">
		<link rel="stylesheet" type="text/css" href="../css/lovcombo.css">

		<script type="text/javascript" src="../js/Ext.ux.form.LovCombo.js"></script>
		<script type="text/javascript" src="../js/Ext.ux.ThemeCombo.js"></script>
		
		<script type="text/javascript" src="../js/curriculumController.js"></script>
		<script type="text/javascript" src="../js/cCurriculumController.js"></script>  
		<script type="text/javascript" src="../js/accountController.js"></script>
		
		<script type="text/javascript" src="../js/schoolController.js"></script> 
		<script type="text/javascript" src="../js/campusController.js"></script> 
		
		
		
		<!-- Main tab-->
		<script type="text/javascript">
			$(function(){
				//Tabs
				$(function() {
					$("#tabs").tabs();
				});
				//Accordion
				$("#accordion").accordion({ header:"h3" });
				
			});
			
			
			
		</script>		
	</head>
	
	<body>
		
		<div id="tabs" style="height:100%">
			<ul>
				<li><a href="#CurriculumsTab">Curricula</a></li>
				<li><a href="#AccountsTab">Accounts</a></li>
				<li><a href="#SchoolsTab">Schools</a></li>
			</ul>
			<div id="CurriculumsTab">
					
				<!--  <div id="curriculum_list" style="width:80%"></div>-->
				<table id="ccontainer" width="100%">
				
				<tr><td>
				<div id="pTitle">>>Curricula List</div>
				<div id="pCurriculum_list" ></div>
				</td></tr>
				<tr><td style="height:20px">
				</td></tr>
				<tr><td>
				<div id="cTitle">>>Sub-Curricula List</div>
					<div id="cCurriculum_list"></div>
				</td></tr>
				</table>
								
				
					
			</div>
			
			<div id="AccountsTab">
			
			 
			<table width="100%">
				<tr><td>
					<div id="accountTitle">>>Users List</div>
				</td></tr>
				<tr><td>
					<div id="toolBar"></div>
				</td></tr>
				<tr><td>
					<div id="account_list"></div>
				</td></tr>
				<tr><td>
					<div id="pageBar"></div>
				</td></tr>
			</table>
				
			</div>
			
			<div id="SchoolsTab">
			<table width="100%">
				<tr><td>
					<div id="schoolTitle">>>School List</div>
				</td></tr>
				<tr><td>
					<div id="s_toolBar"></div>

				</td></tr>
				<tr><td>
					<div id="school_list"></div>
				</td></tr>
				<tr><td>
					<div id="s_pageBar"></div>
				</td></tr>
				<tr><td height="20px">
				</td></tr>
				<tr><td>
					<div id="campusTitle">>>Campus List</div>
				</td></tr>
				<tr><td>
					<div id="c_toolBar"></div>

				</td></tr>
				<tr><td>
					<div id="campus_list"></div>
				</td></tr>
				<tr><td>
					<div id="c_pageBar"></div>
				</td></tr>
				</table>
				
			</div>
		</div>
	</body>
</html>