Ext.onReady(function(){ 

Ext.QuickTips.init();
Ext.form.Field.prototype.msgTarget = 'under';

var record_start = 0; 

var sm = new Ext.grid.CheckboxSelectionModel();
var cm=new Ext.grid.ColumnModel([ 
sm,
new Ext.grid.RowNumberer({header: "ID",
width:40, 
renderer:function(value,metadata,record,rowIndex){ 
return record_start + 1 + rowIndex; 
} 
}),
{header:'ID',dataIndex:'id',hidden:true},
{header:'Title',dataIndex:'title',width:100,sortable: true},
{header:'Begin Date',dataIndex:'beginDate',width:100,sortable: true},
{header:'End Date',dataIndex:'endDate',width:100,sortable: true},
{header:'Curriculum',dataIndex:'curriculumName',width:100,sortable: true},
{header:'Available',dataIndex:'available',width:100,sortable: true},
{header:'File',dataIndex:'pdfPath',width:100,sortable: true}
]); 
var ds = new Ext.data.Store({ 
proxy:new Ext.data.HttpProxy({
url:'../lesson.do?action=getLessonsList',
method:'POST'
}), 
reader:new Ext.data.JsonReader({ },[
{name:'id'}, 
{name:'title'},
{name:'beginDate'},
{name:'endDate'},
{name:'curriculumName'},
{name:'available'},
{name:'pdfPath'}
]) 
}); 

ds.load({params:{start:0,limit:20}}); 

var grid=new Ext.grid.GridPanel({ 
	id:'curriculums',
	title:'Lesson List',
	renderTo:"lesson_list", 
	stripeRows:true,//斑马线效果
	height:600, 
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
	 {text:'Delete',handler:deleteCurriculum}
	 ]              
});

//first level curriculum data
var cStore=new Ext.data.Store({ 
	proxy:new Ext.data.HttpProxy({
	url:'../setting.do?action=getCurriculumList',
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
width: 500, 
height: 260, 
fileUpload: true,
//样式 
bodyStyle: 'margin:3px 3px 3px 3px', 
frame: true, 
xtype: 'filedset', 
labelWidth: 70, 
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
fieldLabel: 'Title', 
emptyText: 'required', 
blankText: 'Title cannot be blank', 
allowBlank: false,  
width:180
}]},{ layout : "form",
    items : [{
    	xtype: 'datefield',
    	id: 'beginDate', 
    	name: 'beginDate', 
    	format : 'Y-m-d',
    	fieldLabel: 'Begin date', 
    	emptyText: 'required', 
    	blankText: 'Begin date cannot be blank', 
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
    	    	fieldLabel: 'End date', 
    	    	emptyText: 'required', 
    	    	blankText: 'End date cannot be blank', 
    	    	allowBlank: false,
    	    	editable:false,
    	    	width:180
    	    	}]},
    	    	{ layout : "form",
    	    	    items : [{
    	    	xtype: 'combo', 
    	    	id : "curriculumId",
    	    	       store :cStore,
    	    	       fieldLabel: 'Curriculum',
    	    	       valueField : 'id',
    	    	       displayField : 'curriculumName',
    	    	       emptyText : 'Choose a curriculum...',
    	    	       allowBlank : false,
    	    	       blankText:'Please choose a curriculum',
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
    	    	    	xtype: 'radiogroup',
    	    	    	id: 'availableCheck',
    	    	    	fieldLabel: 'Available',
    	    	    	items: [
    	    	    	{boxLabel: 'YES', name: 'available', inputValue: 1, checked: true},
    	    	    	{boxLabel: 'NO', name: 'available', inputValue: 0}
    	    	    	] 
    	    	    		
    	    	}]},
{ layout : "form",
           items : [{
        	   xtype: 'fileuploadfield',
        	   id: 'form-file',
        	   emptyText: 'Select a zip file',
        	   fieldLabel: 'Attachment',
        	   name: 'pdfPath',
        	   anchor: '95%', 
        	   buttonText: 'Browse'
        	   }]}
]}]});

//新增课程窗口 
addCurriculumWin = new Ext.Window({ 
id: 'addCurriculumWin', 
title: 'Add/Edit a Lesson', 
width: 510, 
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

                       Ext.getCmp('addCurriculumWin').hide();
                       Ext.getCmp('curriculums').store.reload();
                       Ext.Msg.alert('Status', 'Upload Succeeded'); 
 
                       }, 
                        failure : function(form, action) { 

                           Ext.Msg.alert('Status', 'Save Failed'); 
 
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
Ext.Msg.alert("Prompt", "Please choose a curriculum-first level to delete"); 
} 
else{
	Ext.MessageBox.confirm('Confirm', 'Second level curriculum included will be deleted too, Continue?', sConfirm);
}
}

function sConfirm(btn){
	if(btn == 'yes')
		Ext.MessageBox.confirm('Confirm', 'Are you sure you want to delete this curriculum?', deleteConfirm);
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
 
                       
                       success : function() { 

                    	   Ext.getCmp('addCurriculumWin').hide();
                       Ext.getCmp('curriculums').store.reload();
                       Ext.Msg.alert('Status', 'Save Succeeded'); 
 
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
  	
  	if(addJSON.available == true)
  	  	Ext.getCmp("availableCheck").setValue(1);
  	else if(addJSON.available == false)
  	  	Ext.getCmp("availableCheck").setValue(0);
  		
  	Ext.getCmp("form-file").setValue(addJSON.pdfPath);
  	
  	
	Ext.getCmp("addCurriculumWin").show(); 
	
	
  },failure:function(){
    Ext.MessageBox.alert("Status","Request exception");
  }
}); 


}
}
 
grid.addListener('rowdblclick',checkCurriculum);




}); 