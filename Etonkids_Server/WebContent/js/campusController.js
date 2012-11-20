Ext.onReady(function(){ 

Ext.QuickTips.init();
Ext.form.Field.prototype.msgTarget = 'under';

var record_start4 = 0; 

var sm4 = new Ext.grid.CheckboxSelectionModel();
var cm4 = new Ext.grid.ColumnModel([ 
sm4,
new Ext.grid.RowNumberer({header: "ID",
width:40, 
renderer:function(value,metadata,record,rowIndex){ 
return record_start4 + 1 + rowIndex; 
} 
}),
{header:'ID',dataIndex:'id',hidden:true},
{header:'Campus Name',dataIndex:'address',width:300,sortable: true},
{header:'School',dataIndex:'schoolType',width:200,sortable: true}, 
{header:'Created by',dataIndex:'createdBy',width:100,sortable: true}
]); 
var ds4 = new Ext.data.Store({ 
proxy:new Ext.data.HttpProxy({
url:'../setting.do?action=getSchoolList',
method:'POST'
}), 
reader:new Ext.data.JsonReader({ 
	totalProperty : 'totalProperty',   //page
    root : 'campus'
},[
{name:'id'}, 
{name:'address'},
{name:'schoolType'},  
{name:'createdBy'}
]) 
}); 

ds4.load({params:{start:0,limit:15}}); 

var grid4 = new Ext.grid.GridPanel({ 
	id:'campuses',
	renderTo:"campus_list", 
	stripeRows:true,//斑马线效果
	title:"", 
	height:500, 
	cm:cm4,
	store: ds4,
	loadMask: { msg: "Loading..." }, 
	viewConfig: {   
	                            forceFit:true   
	            } 
});

var c_toolBar = new Ext.Toolbar({  
	renderTo:'c_toolBar',
	autoWidth:true,  
	autoShow:true,  
	items:[  
	{text:'Add', handler:btnAddClick_Campus},
	 '-',
	 {text:'Edit',handler:checkCampus},
	 '-',
	 {text:'Delete',handler:deleteCampus}
	]  
}) ;

var c_pageBar = new Ext.PagingToolbar({   
    renderTo:'c_pageBar',
    pageSize: 15,   
    store: ds4, 
    displayInfo: true,   
    displayMsg: 'Displaying campuses {0}-{1} of {2}',   
    emptyMsg: "No data to display"   ,
    doLoad : function(start){ 
    	   record_start4 = start; 
    	var o = {}, pn = this.getParams(); 
    	          o[pn.start] = start; 
    	          o[pn.limit] = this.pageSize; 
    	this.store.load({params:o}); 
    }
});

//first level curriculum data
var sStore=new Ext.data.Store({ 
	proxy:new Ext.data.HttpProxy({
	url:'../setting.do?action=getSchoolTypes',
	method:'POST'
	}), 
	reader:new Ext.data.JsonReader({ },[
	{name:'id'}, 
	{name:'typeName'}
	]) ,
	autoLoad: true
	}); 

//新增表单
addCampusForm = new Ext.form.FormPanel({ 
id: 'addCampusForm', 
width: 480, 
height: 300, 
//样式 
bodyStyle: 'margin:3px 3px 3px 3px', 
frame: true, 
xtype: 'filedset', 
labelWidth: 60, 
items: 
[ { xtype: 'fieldset', 
title: 'Campus Info', 
collapsible: true, 
autoHeight: true, 
autoWidth: true, 
items: 
[ 
{ layout : "form",
    items : [{
xtype: 'combo', 
id : "cSchool",
       store :sStore,
       fieldLabel: 'School',
       valueField : 'id',
       displayField : 'typeName',
       emptyText : 'Choose a school...',
       allowBlank : false,
       blankText:'Please choose a school',
       editable: false,
       typeAhead : true,
       width: 130,
       mode:'remote', 
       hiddenName:'typeId',
       triggerAction : 'all',
       selectOnFocus : true ,
       listeners:{        
           select : function(combo, record,index)   
           {   
			Ext.getCmp('schoolName').setValue(combo.getRawValue());
         
       }
     }
}]},
{ layout : "form",
    items : [{
xtype: 'hidden',
id: 'schoolName', 
name: 'schoolType', 
fieldLabel: 'School Name',  
allowBlank: true,  
anchor: '96%' 
}]},
{ layout : "form",
    items : [{
xtype: 'textfield',
id: 'address', 
name: 'address', 
fieldLabel: 'Campus Name', 
emptyText: 'Required', 
blankText: 'Please enter a campus name', 
allowBlank: false,  
anchor: '96%' 
}]},
{ layout : "form",
           items : [{
xtype: 'hidden', 
 id : "campusId",
 name:'id',
              fieldLabel: 'CID',
              allowBlank: true
}]}
]}]});

//新增窗口 
addCampusWin = new Ext.Window({ 
id: 'addCampusWin', 
title: 'Add/Edit a Campus', 
width: 500, 
height: 350, 
//背景遮罩 
modal: true, 
//重置大小 
resizable: false, 
//当关闭按钮被点击时执行的动作 
closeAction: 'hide', 
plain: true, 
buttonAlign: 'right', 
items:addCampusForm, 
buttons: 
[  
{ text: 'Save', id: 'btnSubmit_C' } ,
{ text: 'Cancel', handler: function() { Ext.getCmp('addCampusWin').hide(); } }

] 
});


function addCampusFunction() {

	//var addForm = Ext.getCmp("addCCurriculumForm");
	if(addCampusForm.form.isValid()){

	 addCampusForm.form.doAction('submit', { 

                    url : '../setting.do?action=addSchool', 
 
                    method : 'post', 
                       
                       success : function() { 

                       Ext.getCmp('addCampusWin').hide();
                       Ext.getCmp('campuses').store.reload();
                      Ext.Msg.alert('Status', 'Save Succeeded'); 
 
                       }, 
                        failure : function(form, action) { 

                           Ext.Msg.alert('Status', 'Save failed'); 
 
                        } 
                    });  
                    }
 }
  


//添加按钮单击事件 
function btnAddClick_Campus() { 
Ext.getCmp("addCampusWin").show();
addCampusForm.getForm().getEl().dom.reset(); 
Ext.getCmp("addCampusWin").hide(); 

Ext.getCmp("addCampusWin").buttons[0].handler = addCampusFunction; 
Ext.getCmp("addCampusWin").show(); 
}; 

//新增按钮 
//var addCBtn = Ext.getCmp('btnAdd_C'); 
//addCBtn.on('click', btnCAddClick); 

//删除地址
function deleteCampus(){
//var grid = Ext.getCmp('myCustomers'); 
if (grid4.getSelectionModel().getSelections()[0] == undefined) { 
Ext.Msg.alert("Prompt", "Please choose a campus to delete"); 
} 
else{
Ext.MessageBox.confirm('Prompt', 'Are you sure to delete this campus？', deleteConfirm_Campus);
}
}

function deleteConfirm_Campus(btn){
if (btn == 'yes') {
//alert(grid.getSelectionModel().getSelections()[0].data.serialNo);
 Ext.Ajax.request({
  url:'../setting.do?action=deleteSchoolById',
  params : { schoolId: grid4.getSelectionModel().getSelections()[0].data.id},
  method:'POST',
  success:function(){
   Ext.MessageBox.alert("Status","Deleted");
   Ext.getCmp('campuses').store.reload(); 
  },failure:function(){
    Ext.MessageBox.alert("Status", "Delete failed"); 
  }
});
}
}

//编辑地址
function editCampusFunction(){
	
	//var addForm = Ext.getCmp("addCCurriculumForm");

if(addCampusForm.form.isValid()){
	addCampusForm.form.doAction('submit', { 

                    url : '../setting.do?action=updateSchool', 
 
                    method : 'post', 
 
                       
                       success : function() { 

                    	   Ext.getCmp('addCampusWin').hide();
                       Ext.getCmp('campuses').store.reload();
                       Ext.Msg.alert('Status', 'Save Succeeded'); 
 
                       }, 
                        failure : function(form, action) { 

                           Ext.Msg.alert('Status', 'Save failed'); 
 
                        } 
                    }); 
                    }
}

function checkCampus(){
if (grid4.getSelectionModel().getSelections()[0] == undefined) { 
 Ext.Msg.alert("Alert", "Please choose a campus to edit！"); 
}
else{
	var cid = grid4.getSelectionModel().getSelections()[0].data.id;
	
	Ext.getCmp('addCampusForm').form.reset(); 
	Ext.getCmp("addCampusWin").buttons[0].handler = editCampusFunction; 

	Ext.Ajax.request({
url:'../setting.do?action=getSchoolById',
params : { schoolId: cid},
method:'POST',
  success:function(response){
 // alert(response.responseText);
 var addJSON = Ext.util.JSON.decode(response.responseText);
 
 
	
  	Ext.getCmp("campusId").setValue(addJSON.id);
	Ext.getCmp("address").setValue(addJSON.address);
	Ext.getCmp("cSchool").setValue(addJSON.typeId);
	Ext.getCmp("schoolName").setValue(addJSON.schoolType);
  	//Ext.getCmp("parentCurriName").setValue(addJSON.parentCurriName);
  	
	Ext.getCmp("addCampusWin").show(); 
	
	
  },failure:function(){
    Ext.MessageBox.alert('Status','Request exception');
  }
}); 


}
}

grid4.addListener('rowdblclick',checkCampus);




}); 