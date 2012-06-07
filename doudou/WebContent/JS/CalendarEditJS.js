$(document).ready(function(){

//表单验证
	  var vali = $("#modEvent-form").validate({
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
		    	//var beginTime = form.beginTime.value;
		    	//alert($(form).find('[name=beginTime]').val());
		    	$(form).ajaxSubmit({
					url: "../Event/updateEvent.do",
					type: "POST", 
					success: function(data, textStatus, jqXHR) {
						if(data>0){
							alert("修改成功");
						}
						else{
							$("#error").text("提交失败，请重新提交");
						}
	              },
					resetForm: true
	            });
		    }
  });
	  
  $("#beginTime_mod").datetimepicker();
  $("#endTime_mod").datetimepicker();
  
  $("#atChildList_mod").multiselect({
	  minWidth: 500,
	  noneSelectedText: "请选择要发送的学生名单",
	  selectedList: 4
  });

//绑定各种事件
  $("#saveMod").bind({
	  "click": function(){
		  var atChildList = $("#atChildList_mod").val();
		  $("#atChildList").val(atChildList);
		  $("#modEvent-form").submit();
	  }
  });
  
  $("#cancelMod").bind({
	  "click": function(){
		  window.location.href="../Event/getEventById.do?eventId="+$("#id_mod").val();
	  }
  });

});

function setEventModForm(){
	var index = $("#event-item-detail").data("index");

	$("#id_mod").val( idArray[index] );
	$("#atChildList_mod").val(eventObject.atChildList);
	$("#title_mod").val(eventObject.atChildList);
	$("#location_mod").val(eventObject.locael);
	$("#beginTime_mod").val(eventObject.beginTime);
	$("#endTime_mod").val(eventObject.endTime);
	$("input:radio[name='allday_mod']").val(eventObject.allday);
	$("#content_mod").val(eventObject.content);

	$("#event-item-detail").css("display","none");
    $("#event-mod-detail").css("display","block");
}

