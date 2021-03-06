Ext.onReady(function(){ 

Ext.QuickTips.init();
Ext.form.Field.prototype.msgTarget = 'under';

var record_start = 0; 

var sm = new Ext.grid.CheckboxSelectionModel();
var cm=new Ext.grid.ColumnModel([ 
sm,
new Ext.grid.RowNumberer({header: "ID",
width:40,align:'center', 
renderer:function(value,metadata,record,rowIndex){ 
return record_start + 1 + rowIndex; 
} 
}),
{header:'ID',dataIndex:'id',hidden:true},
{header:'Title',dataIndex:'title',width:100,align:'center',sortable: true},
{header:'Course Category',dataIndex:'curriculumName',width:100,align:'center',sortable: true},
{header:'Available Date',dataIndex:'beginDate',width:100,align:'center',sortable: true},
{header:'Expiry Date',dataIndex:'endDate',width:100,align:'center',sortable: true},
{header:'File Name',dataIndex:'pdfPath',width:100,align:'center',sortable: true},
{header:'Publish Time',dataIndex:'createdTime',width:100,align:'center',sortable: true}
]); 
var ds = new Ext.data.Store({ 
proxy:new Ext.data.HttpProxy({
url:'../lesson.do?action=getLessonsList',
method:'POST'
}), 
reader:new Ext.data.JsonReader({  
	totalProperty : 'totalProperty',   //page
    root : 'lesson'},[
{name:'id'}, 
{name:'title'},
{name:'curriculumName'},
{name:'beginDate'},
{name:'endDate'},
{name:'pdfPath'},
{name:'createdTime'}
]) 
}); 

ds.load({params:{start:0,limit:20}}); 

var grid=new Ext.grid.GridPanel({ 
	id:'curriculums',
	title:'Lesson List',
	renderTo:"lesson_list", 
	stripeRows:true,//斑马线效果
	height:950, 
	cm:cm,
	store: ds,
	loadMask: { msg: "Loading..." }, 
	viewConfig: {   
	                            forceFit:true   
	            },   
	            bbar: new Ext.PagingToolbar({   
	                pageSize: 20,   
	                store: ds, 
	                displayInfo: true,   
	                displayMsg: 'Displaying lessons {0}-{1} of {2}',   
	                emptyMsg: "No data to display"   ,
	                doLoad : function(start){ 
	                	   record_start = start; 
	                	var o = {}, pn = this.getParams(); 
	                	          o[pn.start] = start; 
	                	          o[pn.limit] = this.pageSize; 
	                	this.store.load({params:o}); 
	                }
	            }),
	 tbar: [
	 {text:'Add',id: 'btnAdd',handler:btnAddClick},
	 '-',
	 {text:'Edit',handler:checkCurriculum},
	 '-',
	 {text:'Delete',handler:deleteCurriculum},
	 '->',
	 {xtype:'textfield',id:'keyword',title:'Course Category', width:180},
	 {text:'查询',handler:findCustomer},
	 '-',
	 {text:'清空',handler: callBack}
	 ]              
});

function findCustomer(){

	var condition = Ext.getCmp('condition').getValue();
	var keyword = Ext.getCmp('keyword').getValue();

	if(condition == '' || condition == null){
	Ext.Msg.alert('提示', '请选择查询条件！'); 
	}
	else{

	ds.baseParams={condition:condition, keyword: keyword};
	ds.proxy = new Ext.data.HttpProxy({
					url:'customer/findCustomer.do',
					method:'POST'
				}); 
	ds.reader = new Ext.data.JsonReader({ 
						totalProperty:'total',  
						root:'resultData'},[
											{name:'custId', mapping: 'custId'}, 
											{name:'custName', mapping: 'custName'},  
											{name:'industryType', mapping: 'industryType'},
											{name:'defaultStat', mapping: 'defaultStat'},
											{name:'exposureType', mapping: 'exposureType'},
											{name:'creditLevel',mapping:'creditLevel'},
											{name:'balance',mapping:'balance'},
											{name:'inputUserName', mapping:'inputUserName'},
											{name:'inputOrgName', mapping:'inputOrgName'},
					]);
					   
	ds.load({params:{start:0,limit:20}});
	record_start = 0;  	
	}
	}

	function callBack(){

	Ext.getCmp('condition').clearValue();
	Ext.getCmp('keyword').setValue('');
	ds.proxy = new Ext.data.HttpProxy({
					url:'customer/listCustomer.do',
					method:'POST'
				}); 
	ds.reader = new Ext.data.JsonReader({ 
						totalProperty:'total',  
						root:'customer'},[
											{name:'custId', mapping: 'custId'}, 
											{name:'custName', mapping: 'custName'},  
											{name:'industryType', mapping: 'industryType'},
											{name:'defaultStat', mapping: 'defaultStat'},
											{name:'exposureType', mapping: 'exposureType'},
											{name:'creditLevel',mapping:'creditLevel'},
											{name:'balance',mapping:'balance'},
											{name:'inputUserName', mapping:'inputUserName'},
											{name:'inputOrgName', mapping:'inputOrgName'},
					]);   
	ds.load({params:{start:0,limit:20}});
	record_start = 0;

	}

//first level curriculum data
var cStore=new Ext.data.Store({ 
	proxy:new Ext.data.HttpProxy({
	url:'../setting.do?action=getCurriculumListForCascading',
	method:'POST'
	}), 
	reader:new Ext.data.JsonReader({ },[
	{name:'id'}, 
	{name:'curriculumName'}
	]) ,
	autoLoad: true
	}); 

//新增一级课程表单
addCurriculumForm = new Ext.form.FormPanel({ 
id: 'addCurriculumForm', 
width: 520, 
height: 260, 
fileUpload: true,
//样式 
bodyStyle: 'margin:3px 3px 3px 3px', 
frame: true, 
xtype: 'filedset', 
labelWidth: 100, 
items: 
[ { xtype: 'fieldset', 
title: 'Lesson Info', 
collapsible: true, 
autoHeight: true, 
autoWidth: true, 
items: 
[{ layout : "form",
    items : [{
    	xtype: 'hidden', 
    	id : "lessonId",
    	name:'lessonId',
    	fieldLabel: 'Lesson ID',
    	allowBlank: true
    	}]},
    	{ layout : "form",
           items : [{
xtype: 'textfield',
id: 'title', 
name: 'title', 
fieldLabel: 'Course Name', 
emptyText: 'required', 
blankText: 'Course name is required', 
allowBlank: false,  
width:180
}]},{ layout : "form",
    items : [{
    	xtype: 'datefield',
    	id: 'beginDate', 
    	name: 'beginDate', 
    	format : 'Y-m-d',
    	fieldLabel: 'Available date', 
    	emptyText: 'required', 
    	blankText: 'Available date is required', 
    	allowBlank: false,
    	editable:false,
    	width:180
    	}]},
    	{ layout : "form",
    	    items : [{
    	    	xtype: 'datefield',
    	    	id: 'endDate', 
    	    	name: 'endDate', 
    	    	format : 'Y-m-d',
    	    	fieldLabel: 'Expiry date', 
    	    	emptyText: 'required', 
    	    	blankText: 'Expiry date is required', 
    	    	allowBlank: false,
    	    	editable:false,
    	    	width:180,
    	    	 listeners:{  
                     change:function()  
                     {  
     
                    	 var e = Ext.util.Format.date(Ext.getCmp('endDate').getValue(), 'Y-m-d');//格式化日期控件值 
                    	 var s= Ext.util.Format.date(Ext.getCmp('beginDate').getValue(), 'Y-m-d');//格式化日期控件值 
     
                    	 var end = new Date(e); 
                    	 var start = new Date(s); 
                    	 var elapsed = Math.round((end.getTime() - start.getTime())/(1000*60*60*24)); // 计算间隔天数 
     
                    	 if(elapsed < 0){
                        	 Ext.MessageBox.alert("Prompt","The expiry date must be after the available date"); 
                    	 }
                      } 
    	    	}}]},
    	    	{ layout : "form",
    	    	    items : [{
    	    	xtype: 'combo', 
    	    	id : "curriculumId",
    	    	       store :cStore,
    	    	       fieldLabel: 'Course Category',
    	    	       valueField : 'id',
    	    	       displayField : 'curriculumName',
    	    	       emptyText : 'Choose a category...',
    	    	       allowBlank : false,
    	    	       blankText:'Please choose a sub-curriculum',
    	    	       editable: false,
    	    	       typeAhead : true,
    	    	       width: 180,
    	    	       mode:'remote', 
    	    	       hiddenName:'curriculumId',
    	    	       triggerAction : 'all',
    	    	       selectOnFocus : true 
    	    	}]},
{ layout : "form",
           items : [{
        	   xtype: 'fileuploadfield',
        	   id: 'form-file',
        	   emptyText: 'Select a zip file',
        	   fieldLabel: 'Attachment',
        	   name: 'pdfPath',
        	   allowBlank : false,
    	       blankText:'Please choose a lesson attachment',
        	   anchor: '95%', 
        	   buttonText: 'Browse',
        	   validator: function(v){
        		    if(!/\.zip$/.test(v)){
        		     return 'Only .zip files allowed';
        		    }
        		     return true;
        		  }
        	   
        	   }]}
]}]});

//新增课程窗口 
addCurriculumWin = new Ext.Window({ 
id: 'addCurriculumWin', 
title: 'Add/Edit a Lesson', 
width: 530, 
height: 330, 
y:100,
//背景遮罩 
modal: true, 
//重置大小 
resizable: false, 
//当关闭按钮被点击时执行的动作 
closeAction: 'hide', 
plain: true, 
buttonAlign: 'right', 
items:addCurriculumForm, 
buttons: 
[  
{ text: 'Save', id: 'btnSubmit' } ,
{ text: 'Cancel', handler: function() { Ext.getCmp('addCurriculumWin').hide(); } }

] 
});


function addRoleFunction() {

	var addForm = Ext.getCmp("addCurriculumForm");
	if(addForm.form.isValid()){

	 addForm.form.doAction('submit', { 

                    url : '../upload.do?action=uploadFile', 
 
                    method : 'post', 
                    
                    waitMsg: 'Uploading your file...',
                       
                       success : function(form, action) { 
                    	   
                    	   if(action.result.success == 'true'){
                    		   
                    		   Ext.getCmp('addCurriculumWin').hide();
                        	   Ext.getCmp('curriculums').store.reload();
                        	   Ext.Msg.alert('Status', 'Upload Succeeded'); 
                    	   }
                    	   else{
                        	   Ext.Msg.alert('Status', 'Upload Failed. Please check the file.'); 

                    	   }
 
                       }, 
                        failure : function(form, action) { 

                           Ext.Msg.alert('Status', 'Upload Failed'); 
 
                        } 
                    });  
                    }
 }
  


//添加按钮单击事件 
function btnAddClick() { 
Ext.getCmp("addCurriculumWin").show();
addCurriculumForm.getForm().getEl().dom.reset(); 
Ext.getCmp("addCurriculumWin").hide(); 

Ext.getCmp("addCurriculumWin").buttons[0].handler = addRoleFunction; 
Ext.getCmp("addCurriculumWin").show(); 
}; 

//新增按钮 

//var addUserBtn = Ext.getCmp('btnAdd'); 
//addUserBtn.on('click', btnAddClick); 

//删除地址
function deleteCurriculum(){
//var grid = Ext.getCmp('myCustomers'); 
if (grid.getSelectionModel().getSelections()[0] == undefined) { 
Ext.Msg.alert("Prompt", "Please choose a lesson to delete"); 
} else {
	
	var e = Ext.util.Format.date(grid.getSelectionModel().getSelections()[0].data.endDate, 'Y-m-d');//格式化日期控件值 
	var s= Ext.util.Format.date(grid.getSelectionModel().getSelections()[0].data.beginDate, 'Y-m-d');//格式化日期控件值 

	var end = new Date(e); 
	var start = new Date(s); 
	var currentDate = new Date();
	//alert(currentDate);

	var elapsedE = Math.round((end.getTime() - currentDate.getTime())/(1000*60*60*24)); 
	var elapsedS = Math.round((currentDate.getTime() - start.getTime())/(1000*60*60*24)); 
	//alert(elapsedE+"+"+elapsedS);
	
	if(elapsedE>=0 && elapsedS>=0){
		 Ext.Msg.alert("Prompt", "This lesson is available and cannot be deleted"); 

	}
else{
	Ext.MessageBox.confirm('Confirm', 'Are you sure to delete this lesson?', deleteConfirm);
}
}
}

function deleteConfirm(btn){
if (btn == 'yes') {
//alert(grid.getSelectionModel().getSelections()[0].data.serialNo);
 Ext.Ajax.request({
  url:'../lesson.do?action=deleteLessonById',
  params : { lessonId: grid.getSelectionModel().getSelections()[0].data.id},
  method:'POST',
  success:function(){
   Ext.MessageBox.alert("Status","Deleted");
   Ext.getCmp('curriculums').store.reload(); 
  },failure:function(){
    Ext.MessageBox.alert("Status", "Delete failed"); 
  }
});
}
}

//编辑地址
function editCurriculumFunction(){
	
	//var addForm = Ext.getCmp("addCustomerForm");

if(addCurriculumForm.form.isValid()){
	addCurriculumForm.form.doAction('submit', { 

                    url : '../lesson.do?action=updateLesson', 
 
                    method : 'post', 
 
                       
                       success : function(form, action) { 
                    	   
                    	   if(action.result.success == 'true'){
                    		   
                    		   Ext.getCmp('addCurriculumWin').hide();
                        	   Ext.getCmp('curriculums').store.reload();
                        	   Ext.Msg.alert('Status', 'Edit Succeeded'); 
                    	   }
                    	   else{
                        	   Ext.Msg.alert('Status', 'Edit Failed. Please check the file.'); 

                    	   }

                    	  
 
                       }, 
                        failure : function(form, action) { 

                           Ext.Msg.alert('Status', 'Save failed'); 
 
                        } 
                    }); 
                    }
}

function checkCurriculum(){

	
if (grid.getSelectionModel().getSelections()[0] == undefined) { 
 Ext.Msg.alert("Prompt", "Please choose a lesson to edit"); 
}
else {
	
	var e = Ext.util.Format.date(grid.getSelectionModel().getSelections()[0].data.endDate, 'Y-m-d');//格式化日期控件值 
	var s= Ext.util.Format.date(grid.getSelectionModel().getSelections()[0].data.beginDate, 'Y-m-d');//格式化日期控件值 

	var end = new Date(e); 
	var start = new Date(s); 
	var currentDate = new Date();
	//alert(currentDate);

	var elapsedE = Math.round((end.getTime() - currentDate.getTime())/(1000*60*60*24)); 
	var elapsedS = Math.round((currentDate.getTime() - start.getTime())/(1000*60*60*24)); 
	//alert(elapsedE+"+"+elapsedS);
	
	if(elapsedE>=0 && elapsedS>=0){
		 Ext.Msg.alert("Prompt", "This lesson is available and cannot be edited"); 

	}

else{
	var id = grid.getSelectionModel().getSelections()[0].data.id;
	
	Ext.getCmp('addCurriculumForm').form.reset(); 
	Ext.getCmp("addCurriculumWin").buttons[0].handler = editCurriculumFunction; 

	Ext.Ajax.request({
		url:'../lesson.do?action=getLessonById',
		params : { lessonId: id},
		method:'POST',
		success:function(response){
            //alert(response.responseText);
			var addJSON = Ext.util.JSON.decode(response.responseText);
 
			//alert(addJSON.curriculumName);
	
  	Ext.getCmp("lessonId").setValue(addJSON.id);
	Ext.getCmp("title").setValue(addJSON.title);
  	Ext.getCmp("beginDate").setValue(addJSON.beginDate);
  	Ext.getCmp("endDate").setValue(addJSON.endDate);
  	Ext.getCmp("curriculumId").setValue(addJSON.curriculumId);		
  	Ext.getCmp("form-file").setValue(addJSON.pdfPath.substr(1));
  	
  	
	Ext.getCmp("addCurriculumWin").show(); 
	
	
  },failure:function(){
    Ext.MessageBox.alert("Status","Request exception");
  }
}); 


}
}
}
 
grid.addListener('rowdblclick',checkCurriculum);




}); 