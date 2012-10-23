Ext.onReady(function(){ 

Ext.QuickTips.init();
Ext.form.Field.prototype.msgTarget = 'under';

var record_start1 = 0; 

var sm1 = new Ext.grid.CheckboxSelectionModel();
var cm1 = new Ext.grid.ColumnModel([ 
sm1,
new Ext.grid.RowNumberer({header: "ID",
width:40, 
renderer:function(value,metadata,record,rowIndex){ 
return record_start1 + 1 + rowIndex; 
} 
}),
{header:'ID',dataIndex:'id',hidden:true},
{header:'Name',dataIndex:'curriculumName',width:160},
{header:'Cover',dataIndex:'imgPath',width:160}, 
{header:'Curriculum',dataIndex:'parentCurriName',width:160},
{header:'Created by',dataIndex:'creator',width:100}
]); 
var ds1 = new Ext.data.Store({ 
proxy:new Ext.data.HttpProxy({
url:'../setting.do?action=getCurriculumList',
method:'POST'
}), 
reader:new Ext.data.JsonReader({ },[
{name:'id'}, 
{name:'curriculumName'},
{name:'imgPath'},  
{name:'parentCurriName'},
{name:'creator'}
]) 
}); 

ds1.load({params:{start:0,limit:20}}); 

var grid1 = new Ext.grid.GridPanel({ 
	id:'c_curriculums',
	renderTo:"cCurriculum_list", 
	stripeRows:true,//斑马线效果
	height:500, 
	cm:cm1,
	store: ds1,
	sortable: true,
	loadMask: { msg: "Loading..." }, 
	viewConfig: {   
	                            forceFit:true   
	            },   
	            bbar: new Ext.PagingToolbar({   
	                pageSize: 20,   
	                store: ds1, 
	                displayInfo: true,   
	                displayMsg: 'Displaying curriculums-second level {0}-{1} of {2}',   
	                emptyMsg: "No data to display"   ,
	                doLoad : function(start){ 
	                	   record_start1 = start; 
	                	var o = {}, pn = this.getParams(); 
	                	          o[pn.start] = start; 
	                	          o[pn.limit] = this.pageSize; 
	                	this.store.load({params:o}); 
	                }
	            }),
	 tbar: [
	 {text:'Add',id: 'btnAdd_C', handler:btnCAddClick},
	 '-',
	 {text:'Edit',handler:checkCCurriculum},
	 '-',
	 {text:'Delete',handler:deleteCCurriculum}
	 ]              
});

//first level curriculum data
var cStore=new Ext.data.Store({ 
	proxy:new Ext.data.HttpProxy({
	url:'../setting.do?action=getFirstClassCurriList',
	method:'POST'
	}), 
	reader:new Ext.data.JsonReader({ },[
	{name:'id'}, 
	{name:'curriculumName'}
	]) ,
	autoLoad: true
	}); 

//新增表单
addCCurriculumForm = new Ext.form.FormPanel({ 
id: 'addCCurriculumForm', 
width: 480, 
height: 300, 
//样式 
bodyStyle: 'margin:3px 3px 3px 3px', 
frame: true, 
xtype: 'filedset', 
labelWidth: 60, 
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
id: 'curriculumName', 
name: 'curriculumName', 
fieldLabel: 'Name', 
emptyText: 'Required', 
blankText: 'Please enter curriculum name', 
allowBlank: false,  
anchor: '96%' 
}]},{ layout : "form",
           items : [{
xtype: 'textfield',
id: 'cover', 
name: 'cover', 
fieldLabel: 'Cover',  
allowBlank: true,  
anchor: '96%' 
}]},
{ layout : "form",
    items : [{
xtype: 'combo', 
id : "parentCurriculumId",
       store :cStore,
       fieldLabel: 'Curriculum',
       valueField : 'id',
       displayField : 'curriculumName',
       emptyText : 'Choose a curriculum-first level...',
       allowBlank : false,
       blankText:'Please choose a curriculum-first level',
       editable: false,
       typeAhead : true,
       width: 130,
       mode:'remote', 
       hiddenName:'parentCurriculumId',
       triggerAction : 'all',
       selectOnFocus : true ,
       listeners:{        
           select : function(combo, record,index)   
           {   
			Ext.getCmp('parentCurriName').setValue(combo.getRawValue());
         
       }
     }
}]},
{ layout : "form",
    items : [{
xtype: 'hidden',
id: 'parentCurriName', 
name: 'parentCurriName', 
fieldLabel: 'PCName',  
allowBlank: true,  
anchor: '96%' 
}]},
{ layout : "form",
           items : [{
xtype: 'hidden', 
 id : "c_id",
 name:'id',
              fieldLabel: 'CID',
              allowBlank: true
}]}
]}]});

//新增窗口 
addCWin = new Ext.Window({ 
id: 'addCWin', 
title: 'Add/Edit a Curriculum-Second Level', 
width: 500, 
height: 350, 
y:500,
//背景遮罩 
modal: true, 
//重置大小 
resizable: false, 
//当关闭按钮被点击时执行的动作 
closeAction: 'hide', 
plain: true, 
buttonAlign: 'right', 
items:addCCurriculumForm, 
buttons: 
[  
{ text: 'Save', id: 'btnSubmit_C' } ,
{ text: 'Cancel', handler: function() { Ext.getCmp('addCWin').hide(); } }

] 
});


function addCFunction() {

	var addForm = Ext.getCmp("addCCurriculumForm");
	if(addForm.form.isValid()){

	 addForm.form.doAction('submit', { 

                    url : '../setting.do?action=addCurriculum', 
 
                    method : 'post', 
                       
                       success : function() { 

                    	   Ext.getCmp('addCWin').hide();
                       Ext.getCmp('c_curriculums').store.reload();
                      Ext.Msg.alert('Status', 'Save Succeeded'); 
 
                       }, 
                        failure : function(form, action) { 

                           Ext.Msg.alert('Status', 'Save Failed'); 
 
                        } 
                    });  
                    }
 }
  


//添加按钮单击事件 
function btnCAddClick() { 
Ext.getCmp("addCWin").show();
addCCurriculumForm.getForm().getEl().dom.reset(); 
Ext.getCmp("addCWin").hide(); 

Ext.getCmp("addCWin").buttons[0].handler = addCFunction; 
Ext.getCmp("addCWin").show(); 
}; 

//新增按钮 
//var addCBtn = Ext.getCmp('btnAdd_C'); 
//addCBtn.on('click', btnCAddClick); 

//删除地址
function deleteCCurriculum(){
//var grid = Ext.getCmp('myCustomers'); 
if (grid1.getSelectionModel().getSelections()[0] == undefined) { 
Ext.Msg.alert("Prompt", "Please choose a curriculum-second level to delete"); 
} 
else{
Ext.MessageBox.confirm('Confirm', 'Are you sure you want to delete this curriculum?', deleteCConfirm);
}
}

function deleteCConfirm(btn){
if (btn == 'yes') {
//alert(grid.getSelectionModel().getSelections()[0].data.serialNo);
 Ext.Ajax.request({
  url:'../setting.do?action=deleteCurriculumById',
  params : { curriId: grid1.getSelectionModel().getSelections()[0].data.id},
  method:'POST',
  success:function(){
   Ext.MessageBox.alert("Status","Deleted");
   Ext.getCmp('c_curriculums').store.reload(); 
  },failure:function(){
    Ext.MessageBox.alert("Status", "Delete failed"); 
  }
});
}
}

//编辑地址
function editCFunction(){
	
	var addForm = Ext.getCmp("addCCurriculumForm");

if(addForm.form.isValid()){
	 addForm.form.doAction('submit', { 

                    url : '../setting.do?action=updateCurriculum', 
 
                    method : 'post', 
 
                       
                       success : function() { 

                    	   Ext.getCmp('addCWin').hide();
                       Ext.getCmp('c_curriculums').store.reload();
                       Ext.Msg.alert('Status', 'Save Succeeded'); 
 
                       }, 
                        failure : function(form, action) { 

                           Ext.Msg.alert('Status', 'Save failed'); 
 
                        } 
                    }); 
                    }
}

function checkCCurriculum(){
if (grid1.getSelectionModel().getSelections()[0] == undefined) { 
Ext.Msg.alert("Prompt", "Please choose a curriculum-second level to edit"); 
}
else{
	var cid = grid1.getSelectionModel().getSelections()[0].data.id;
	
	Ext.getCmp('addCCurriculumForm').form.reset(); 
	Ext.getCmp("addCWin").buttons[0].handler = editCFunction; 

	Ext.Ajax.request({
url:'../setting.do?action=getCurriculumById',
params : { curriId: cid},
method:'POST',
  success:function(response){
 // alert(response.responseText);
 var addJSON = Ext.util.JSON.decode(response.responseText);
 
 
	
  	Ext.getCmp("c_id").setValue(addJSON.id);
	Ext.getCmp("curriculumName").setValue(addJSON.curriculumName);
  	Ext.getCmp("cover").setValue(addJSON.imgPath);
	Ext.getCmp("parentCurriculumId").setValue(addJSON.parentCurriculumId);
  	Ext.getCmp("parentCurriName").setValue(addJSON.parentCurriName);
  	
	Ext.getCmp("addCWin").show(); 
	
	
  },failure:function(){
    Ext.MessageBox.alert("Status","Request exception");
  }
}); 


}
}

grid1.addListener('rowdblclick',checkCCurriculum);

}); 