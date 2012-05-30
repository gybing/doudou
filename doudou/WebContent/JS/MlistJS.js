$(document).ready(function(){
	$("#SelectKidsR").css("display","none");
	$("#FeedBackAll").css("display","none");
	$("#NewMesLightBox").css("display","none");
	$("#MsgListConPanel").css("display","block");
	$("#OneMessageCon").css("display","none");
	$("#EditOneMessage").css("display","none");
	
		
	$("#AddMsg").bind("click",function(){
		//get message type
		
		//send to list
		$("#tagingList").multiselect();
		
		$("#NewMesLightBox").css("display","block");
			
	});
	
	$("#saveBtn").bind("click",function(){
			$("#EditOneMessage").css("display","none");
			$("#OneMessageCon").css("display","block");
		
			$("#MsgListConPanel").css("display","none");
			$("#SelectKidsR").css("display","none");
			$("#FeedBacksAll").css("display","none");
			$("#NewMesLightBox").css("display","none");
	});
	
	$("#cancelBtn").bind("click",function(){
			$("#EditOneMessage").css("display","none");
			$("#OneMessageCon").css("display","block");
		
			$("#MsgListConPanel").css("display","none");
			$("#SelectKidsR").css("display","none");
			$("#FeedBacksAll").css("display","none");
			$("#NewMesLightBox").css("display","none");
	});
	
	$("#editMsg").bind("click",function(){
		
			$("#EditOneMessage").css("display","block");
			$("#OneMessageCon").css("display","none");
		
			$("#MsgListConPanel").css("display","none");
			$("#SelectKidsR").css("display","none");
			$("#FeedBacksAll").css("display","none");
			$("#NewMesLightBox").css("display","none");
			
		//$("#NewMesLightBox").css("display","block");
	});
	$("#returnList").bind("click",function(){
			$("#EditOneMessage").css("display","none");
			$("#OneMessageCon").css("display","none");
		
			$("#MsgListConPanel").css("display","block");
			$("#SelectKidsR").css("display","none");
			$("#FeedBacksAll").css("display","none");
			$("#NewMesLightBox").css("display","none");
	});

	$("#MsgItem_1").bind("click",function(){
		  
		    $.post("../Message/getOneMessage.do",{messageId:$(this).attr("id")},function(result){
		        //$("span").html(result);
		        $.each(result,function(i,n){
		        	
		        });
		      });
		  
			$("#EditOneMessage").css("display","none");
			$("#OneMessageCon").css("display","block");
		
			$("#MsgListConPanel").css("display","none");
			$("#SelectKidsR").css("display","none");
			$("#FeedBacksAll").css("display","none");
			$("#NewMesLightBox").css("display","none");
	});
	
	$("#preMsg").bind("click", function(){
		//request previous message
		
	});
	
	$("#nextMsg").bind("click",function(){
		//request next message
		
	});
	
	
	$("#newConversation_close").bind("click",function(){
			$("#NewMesLightBox").css("display","none");
	});
	
	$("#inputDatef").datepicker();
	$("#inputDatet").datepicker();



});