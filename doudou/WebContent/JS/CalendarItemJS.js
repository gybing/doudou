$(document).ready(function(){
  
  $("#delEvent").bind({
	  "click": function(){
		  if(confirm("确定要删除吗？")){
			  delEventItem();
		  }
		  else{
			  return;
		  }
	  }
  });
  
  $("#preEvent").bind({
	  "click": preEventItem
  });
  
  $("#nextEvent").bind({
	  "click": nextEventItem
  });
  
  $("#returnCal").bind({
	  "click": function(){
		  window.location.href="../view/calendarList.jsp";
	  }
  });
  
  $("#modEvent").bind({
	  "click": function(){
		  window.location.href="../Event/modEventById.do?eventId="+$("#event-item-id").val();
	  }
  });
  
  $("#atChildList_add").multiselect({
	  minWidth: 200,
	  noneSelectedText: "添加本信息新的收件人"
  });
  
  $("#atChildList_add").bind("multiselectclick multiselectcheckall multiselectuncheckall", function(event, ui){
	  var htmlStr = "";
	  $("#atChildList_add").multiselect("widget").find(":checkbox").each(function(){
		   if(this.checked){
			   htmlStr += "<li>"+this.title+"</li>";
		   }
	  });
	  $("#childlist2").html(htmlStr);
  });
  
  $("#addChilds-form-sub").bind({
	  "click": function(){
		  var atChildList = $("#atChildList_before").val()+","+$("#atChildList_add").val();
		  alert(atChildList);
		  $("#atChildList_after").val(atChildList);
		  $("#addChilds-form").ajaxSubmit({
				url: "../Event/updateEvent.do",
				type: "POST", 
				success: function(data, textStatus, jqXHR) {
					if(data>0){
						alert("发送成功");
					}
					else{
						alert("发送失败");
					}
            }
          });
	  }
  });

});

function showEventItem(event){
	var index=event.data.index;
	$("#event-item-detail").removeData();
	$.ajax({
		type: "POST",
		url: "xxx.do",
		data: { id: idArray[index]},
		dataType: "json",
		success: function(data){
			eventObject = data;
			$("#event-item-detail").data(event.data);
			$("#event-item-title").text(data.title);
			$("#event-item-location").text(data.locale);
			$("#event-item-time").text(data.beginTime+" - "+data.endTime);
			if(data.allday=="1"){
				$("#event-item-allday").text("全天事件");
			}
			else{
				$("#event-item-allday").text("非全天事件");
			}
			$("#event-item-content").text(data.content);
			$("#event-item-caltype").text(data.caltype);
			if(index > 0){
				$("#preEvent").bind(
			       "click",{index: index-1}, showEventItem
			    );
			}
			else{
				$("#preEvent").unbind("click");
			}
			if(index < idArray.length-1){
				$("#nextEvent").bind(
			       "click",{index: index+1}, showEventItem
			    );
			}
			else{
				$("#nextEvent").unbind("click");
			}
			$("#event-home-detail").css("display","none");
			$("#event-item-detail").css("display","block");
		}
	});
}

function delEventItem(){
	var id = $("#event-item-id").val();
	$.ajax({
		async: false,
		type: "POST",
		url: "../Event/deleteEvent.do",
		data: { eventId: id },
		dataType: "html",
		success: function(data){
			if(data=="Failure"){
				alert("删除失败");
			}
			else{
				window.document.open();
				window.document.write(data);
				window.document.close();
				alert("删除成功");
			}
			/*
			$("#event-item-id").val(data.id);
			$("#event-item-title").text(data.title);
			$("#event-item-location").text(data.location);
			$("#event-item-time").text(data.beginTimeString+" - "+data.endTimeString);
			if(data.allday==true){
				$("#event-item-allday").text("全天事件");
			}
			else{
				$("#event-item-allday").text("非全天事件");
			}
			$("#event-item-content").text(data.content);
			if(data.publishLevel=="School"){
				$("#event-item-caltype").text("校日历");
			}
			else{
				$("#event-item-caltype").text("班日历");
			}*/
		}
	});
}

function preEventItem(){
	var id = $("#event-item-id").val();
	$.ajax({
		async: false,
		type: "POST",
		url: "../Event/getPreviousEvent.do",
		data: { eventId: id },
		dataType: "html",
		success: function(data){
			if(data=="NoPrevious"){
				alert("已到最前");
			}
			else{
				window.document.open();
				window.document.write(data);
				window.document.close();
			}
			/*
			$("#event-item-id").val(data.id);
			$("#event-item-title").text(data.title);
			$("#event-item-location").text(data.location);
			$("#event-item-time").text(data.beginTimeString+" -- "+data.endTimeString);
			if(data.allday==true){
				$("#event-item-allday").text("全天事件");
			}
			else{
				$("#event-item-allday").text("非全天事件");
			}
			$("#event-item-content").text(data.content);
			if(data.publishLevel=="School"){
				$("#event-item-caltype").text("校日历");
			}
			else{
				$("#event-item-caltype").text("班日历");
			}*/
		}
	});
}

function nextEventItem(){
	var id = $("#event-item-id").val();
	$.ajax({
		async: false,
		type: "POST",
		url: "../Event/getNextEvent.do",
		data: { eventId: id },
		dataType: "html",
		success: function(data){
			if(data=="NoNext"){
				alert("已到最后");
			}
			else{
				window.document.open();
				window.document.write(data);
				window.document.close();
			}
			/*
			$("#event-item-id").val(data.id);
			$("#event-item-title").text(data.title);
			$("#event-item-location").text(data.location);
			$("#event-item-time").text(data.beginTimeString+" -- "+data.endTimeString);
			if(data.allday==true){
				$("#event-item-allday").text("全天事件");
			}
			else{
				$("#event-item-allday").text("非全天事件");
			}
			$("#event-item-content").text(data.content);
			if(data.publishLevel=="School"){
				$("#event-item-caltype").text("校日历");
			}
			else{
				$("#event-item-caltype").text("班日历");
			}*/
		}
	});
}

