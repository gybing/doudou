$(document).ready(function(){
  
  $("#delEvent").bind({
	  "click": delEventItem
  });
  
  $("#preEvent").bind({
	  "click": preEventItem
  });
  
  $("#nextEvent").bind({
	  "click": nextEventItem
  });
  
  $("#returnCal").bind({
	  "click": function(){
		  window.location.href="xxx.do";
	  }
  });
  
  $("#modEvent").bind({
	  "click": function(){
		  window.location.href="xxx.do?id="+$("#event-item-id").val();
	  }
  });
  
  $("#atChildList_add").multiselect({
	  minWidth: 200,
	  noneSelectedText: "添加本信息新的收件人"
  });
  
  $("#atChildList_add").bind("multiselectclick", function(event, ui){
	  if(ui.checked){
		  var htmlStr = "<li id='" +ui.value+ "'>"+ui.text+"</li>";
		  $("#2").append(htmlStr);
	  }
	  else{
		  $("li#"+ui.value).remove();
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
		type: "POST",
		url: "../Event/deleteEvent.do",
		data: { eventId: id },
		dataType: "json",
		succcess: function(data){
			$("#event-item-title").text(data.title);
			$("#event-item-location").text(data.location);
			$("#event-item-time").text(data.beginTime+" - "+data.endTime);
			if(data.allday==true){
				$("#event-item-allday").text("全天事件");
			}
			else{
				$("#event-item-allday").text("非全天事件");
			}
			$("#event-item-content").text(data.content);
			if(data.publishLevel=="School"){
				$("#event-item-allday").text("校日历");
			}
			else{
				$("#event-item-allday").text("班日历");
			}
		}
	});
}

function preEventItem(){
	var id = $("#event-item-id").val();
	$.ajax({
		type: "POST",
		url: "../Event/getPreviousEvent.do",
		data: { eventId: id },
		dataType: "json",
		succcess: function(data){
			$("#event-item-title").text(data.title);
			$("#event-item-location").text(data.location);
			$("#event-item-time").text(data.beginTime+" - "+data.endTime);
			if(data.allday==true){
				$("#event-item-allday").text("全天事件");
			}
			else{
				$("#event-item-allday").text("非全天事件");
			}
			$("#event-item-content").text(data.content);
			if(data.publishLevel=="School"){
				$("#event-item-allday").text("校日历");
			}
			else{
				$("#event-item-allday").text("班日历");
			}
		}
	});
}

function nextEventItem(){
	var id = $("#event-item-id").val();
	$.ajax({
		type: "POST",
		url: "../Event/getNextEvent.do",
		data: { eventId: id },
		dataType: "json",
		succcess: function(data){
			$("#event-item-title").text(data.title);
			$("#event-item-location").text(data.location);
			$("#event-item-time").text(data.beginTime+" - "+data.endTime);
			if(data.allday==true){
				$("#event-item-allday").text("全天事件");
			}
			else{
				$("#event-item-allday").text("非全天事件");
			}
			$("#event-item-content").text(data.content);
			if(data.publishLevel=="School"){
				$("#event-item-allday").text("校日历");
			}
			else{
				$("#event-item-allday").text("班日历");
			}
		}
	});
}

