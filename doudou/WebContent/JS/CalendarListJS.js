$(document).ready(function(){

//表单验证
  var vali = $("#addEvent-form").validate({
			rules:{
				atChildList:"required",
				title:"required",
				locale:"required",
				beginTime:"required",
				endTime:"required",
				allday:"required",
				content:"required"
			},
			messages:{
				atChildList:"请选择学生名单",
				title:"请输入事件标题",
				locale:"请输入事件地点",
				beginTime:"请输入事件开始时间",
				endTime:"请输入事件结束时间",
				allday:"请选择是否全天事件",
				content:"请输入事件内容"
			},
			ignore: "",
			//wrapper: "li",
			errorPlacement: function(error, element) {
				if(element.attr("name")=="beginTime" || element.attr("name")=="endTime"){
					error.appendTo(element.parent("span").next("span"));
				}
				else if(element.attr("name")=="allday" ){
					error.appendTo(element.parent("span").next().next().next().next());
				}
				else{
					error.appendTo(element.parent("li").next("li"));
				} 
		    },

		    submitHandler:function(form){
		    	var beginTime = form.beginTime.value;
		    	//alert($(form).find('[name=beginTime]').val());
		    	$(form).ajaxSubmit({
					url: "../Event/addEvent.do",
					type: "POST", 
					success: function(data, textStatus, jqXHR) {
						if(data>0){
							$(".NewMesLightBox").css("display","none");
							$("#datepicker").datepicker( "setDate" , beginTime.substr(0,10) );
							searchEventByDate( beginTime.substr(0,10) );
						}
						else{
							$("#error").text("提交失败，请重新提交");
						}
	              },
					resetForm: true
	            });
		    }
  });
  
//设置日期选择事件
  $("#datepicker").datepicker({
   onSelect: function(dateText, inst) {
	   searchEventByDate(dateText);
       }
  });

  $("#beginTime_add").datetimepicker();
  $("#endTime_add").datetimepicker();

//绑定各种事件
  $("#searchByTitle").bind({
	  "click": searchEventByTitle
  });

  $(".button1").bind({
	  "click": function(){
		  $("#datepicker").datepicker( "setDate" , null );
		  searchEventByDate(today);
	  }
  });

  $(".button2").bind({
	  "click": searchEventAll
  });

  $(".AddEventBtn").bind({
      "click": function() {
	  $(".NewMesLightBox").css("display","block");
	  $("#atChildList_add").focus();
	  }
  });

  $("#newConversation_close").bind({
       "click": function() {
      vali.resetForm();
	  $(".NewMesLightBox").css("display","none");
	   }
  });

  $("#atChildList_add").multiselect({
	  minWidth: 500,
	  noneSelectedText: "请选择要发送的学生名单",
	  selectedList: 4
  });
  
  $(".NewMsgSubmit").bind({
	  "click": function() {
		  var atChildList = $("#atChildList_add").val();
		  $("#atChildList").val(atChildList);
		  $("#addEvent-form").submit();
	  }
  });
  
  $("div.event-item").live(
	  "click", function(){
		  window.location.href="xxx.do?id="+$(this).attr("id");
	  }
  );

//查询今天日历事件
  //searchEventByDate(today);

});

var now = new Date();
var year = now.getFullYear();
var month = now.getMonth() + 1; // 记得当前月是要+1的
month = month < 10 ? ("0" + month) : month;
var day = now.getDate();
day = day < 10 ? ("0" + day) : day;
var today = year + "-" + month + "-" + day;
var perPageCount = 10;   //分页每页数量

Array.prototype.del=function(n) {
	if(n<0)
		return this;
	else
		return this.slice(0,n).concat(this.slice(n+1,this.length));
};

function searchEventByTitle(){
	var title = $("#title").val();
	var publishLevel = $("#caltype").val();
	if(title==null || title.trim()==""){
		alert("请输入事件标题");
		$("#title").focus();
		return false;
	}
	$.ajax({
			  type: "POST",
			  url: "../Event/queryClassEventList.do",
	          data:{title:title, publishLevel:publishLevel},
			  dataType: "json",
			  success: function(data) {
				   var total = Math.ceil(data.size/perPageCount);//总页数
				   
				   PageClick = function (pageclickednumber) {
                $("#pager").pager({ pagenumber: pageclickednumber, pagecount: total, buttonClickCallback: PageClick });
                $.ajax({
                    url: "../Event/queryClassEventList.do",
                    type: "post",
                    data: { title:title, publishLevel:publishLevel, pageIndex: pageclickednumber, perPageCount:perPageCount },
                    dataType: "json",
                    success: function (data) {
                    	$(".event-list").empty();
                    	for(var i in data.entities){
                    		var event = data.entities[i];
                            var htmlStr = "<div id='" +event.id+ "' class='event-item'><div class='event-date'>";
                            htmlStr += event.beginTimeString.substr(0,10)+" - "+event.endTimeString.substr(0,10);
                            htmlStr += "</div><div class='event-content'><div class='event-content-title'>";
                            htmlStr += event.title;
                            htmlStr += "</div><div class='event-content-time'>";
                            htmlStr += event.beginTimeString+" - "+event.endTimeString;
                            htmlStr += "</div></div></div>";
                            $(".event-list").append(htmlStr);
                    	}

                    }
                });
               };
			      PageClick(1);
             }
	});

}

function searchEventByDate(date){
	alert(date);

	$.ajax({
			  type: "POST",
			  url: "../Event/getClassEventListByDate.do",
	          data:{date:date},
			  dataType: "json",
			  success: function(data) {
				  $(".event-list").empty();
				  for(var i in data){
					  var event = data[i];
                      var htmlStr = "<div id='" +event.id+ "' class='event-item'><div class='event-date'>";
                      htmlStr += event.beginTimeString.substr(0,10)+" - "+event.endTimeString.substr(0,10);
                      htmlStr += "</div><div class='event-content'><div class='event-content-title'>";
                      htmlStr += event.title;
                      htmlStr += "</div><div class='event-content-time'>";
                      htmlStr += event.beginTimeString+" - "+event.endTimeString;
                      htmlStr += "</div></div></div>";
                      $(".event-list").append(htmlStr);
              	}
              }
	});
}


function searchEventAll(){

	$.ajax({
			  type: "GET",
			  url: "../Event/getAllEvent.do",
			  dataType: "json",
			  success: function(data) {
				  var total = Math.ceil(data.size/perPageCount);//总页数
				   
				   PageClick = function (pageclickednumber) {
                $("#pager").pager({ pagenumber: pageclickednumber, pagecount: total, buttonClickCallback: PageClick });
                $.ajax({
                    url: "../Event/getAllEvent.do",
                    type: "post",
                    data: { pageindex: pageclickednumber, perPageCount:perPageCount },
                    success: function (data) {
                    	$(".event-list").empty();
                    	for(var i in data.entities){
                    		var event = data.entities[i];
                            var htmlStr = "<div id='" +event.id+ "' class='event-item'><div class='event-date'>";
                            htmlStr += event.beginTimeString.substr(0,10)+" - "+event.endTimeString.substr(0,10);
                            htmlStr += "</div><div class='event-content'><div class='event-content-title'>";
                            htmlStr += event.title;
                            htmlStr += "</div><div class='event-content-time'>";
                            htmlStr += event.beginTimeString+" - "+event.endTimeString;
                            htmlStr += "</div></div></div>";
                            $(".event-list").append(htmlStr);
                    	}
                    }
                });
               };
			      PageClick(1);
              }
	});
}

