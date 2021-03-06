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
{header:'Curriculum',dataIndex:'curriculumName',width:200,sortable: true},
{header:'Created by',dataIndex:'createdBy',width:100,sortable: true}
]); 
var ds = new Ext.data.Store({ 
proxy:new Ext.data.HttpProxy({
url:'../setting.do?action=getFirstClassCurriList',
method:'POST'
}), 
reader:new Ext.data.JsonReader({ },[
{name:'id'}, 
{name:'curriculumName'},
{name:'createdBy'}
]) 
}); 

ds.load({params:{start:0,limit:20}}); 

var grid=new Ext.grid.GridPanel({ 
	id:'curriculums',
	renderTo:"pCurriculum_list", 
	stripeRows:true,//斑马线效果
	height:300, 
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
	                displayMsg: 'Displaying curricula {0}-{1} of {2}',   
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

//新增一级课程表单
addCurriculumForm = new Ext.form.FormPanel({ 
id: 'addCurriculumForm', 
width: 480, 
height: 100, 
//样式 
bodyStyle: 'margin:3px 3px 3px 3px', 
frame: true, 
xtype: 'filedset', 
labelWidth: 80, 
items: 
[ { xtype: 'fieldset', 
title: 'Curriculum Info', 
collapsible: true, 
autoHeight: true, 
autoWidth: true, 
items: 
[ { layout : "form",
           items : [{
xtype: 'textfield',
id: 'p_curriculumName', 
name: 'curriculumName', 
fieldLabel: 'Curriculum Name', 
emptyText: 'required', 
blankText: 'Curriculum cannot be blank', 
allowBlank: false,  
anchor: '96%' 
}]},
{ layout : "form",
           items : [{
xtype: 'hidden', 
id : "parentCurriculumId",
fieldLabel: 'parentCurriculumId',
value:'0',
allowBlank: true
}]},{ layout : "form",
    items : [{
    	xtype: 'hidden', 
    	id : "curriId",
    	name:'id',
    	fieldLabel: 'curriId',
    	allowBlank: true
    	}]}
]}]});

//新增课程窗口 
addCurriculumWin = new Ext.Window({ 
id: 'addCurriculumWin', 
title: 'Add/Edit a Curriculum', 
width: 500, 
height: 170, 
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

                    url : '../setting.do?action=addCurriculum', 
 
                    method : 'post', 
 
                       
                       success : function(form, action) { 

                       Ext.getCmp('addCurriculumWin').hide();
                       Ext.getCmp('curriculums').store.reload();
                      Ext.Msg.alert('Status', 'Save Succeeded'); 
 
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
Ext.Msg.alert("Prompt", "Please choose a curriculum to delete"); 
} 
else{
	Ext.MessageBox.confirm('Confirm', 'Sub-curriculum included will be deleted too, Continue?', sConfirm);
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
  url:'../setting.do?action=deleteCurriculumById',
  params : { curriId: grid.getSelectionModel().getSelections()[0].data.id},
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

                    url : '../setting.do?action=updateCurriculum', 
 
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
Ext.Msg.alert("Prompt", "Please choose a curriculum to edit"); 
}
else{
	var id = grid.getSelectionModel().getSelections()[0].data.id;
	
	Ext.getCmp('addCurriculumForm').form.reset(); 
	Ext.getCmp("addCurriculumWin").buttons[0].handler = editCurriculumFunction; 

	Ext.Ajax.request({
		url:'../setting.do?action=getCurriculumById',
		params : { curriId: id},
		method:'POST',
		success:function(response){
            //alert(response.responseText);
			var addJSON = Ext.util.JSON.decode(response.responseText);
 
			//alert(addJSON.curriculumName);
	
  	Ext.getCmp("curriId").setValue(addJSON.id);
	Ext.getCmp("p_curriculumName").setValue(addJSON.curriculumName);
  	
  	
  	
  	
	Ext.getCmp("addCurriculumWin").show(); 
	
	
  },failure:function(){
    Ext.MessageBox.alert("Status","Request exception");
  }
}); 


}
}
 
grid.addListener('rowdblclick',checkCurriculum);




}); 