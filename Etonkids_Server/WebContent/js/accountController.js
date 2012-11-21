Ext.onReady(function(){ 

Ext.override(Ext.ux.form.LovCombo, {     
        beforeBlur: Ext.emptyFn     
     });  
	
Ext.QuickTips.init();
Ext.form.Field.prototype.msgTarget = 'under';

var record_start2 = 0; 

var sm2 = new Ext.grid.CheckboxSelectionModel();
var cm2 = new Ext.grid.ColumnModel([ 
sm2,
new Ext.grid.RowNumberer({header: "ID",
width:40, 
renderer:function(value,metadata,record,rowIndex){ 
return record_start2 + 1 + rowIndex; 
} 
}),
{header:'ID',dataIndex:'id',hidden:true},
{header:'First Name',dataIndex:'realName',width:80,sortable: true},
{header:'Role',dataIndex:'roleName',width:60,sortable: true},
{header:'Login Name',dataIndex:'userName',width:100,sortable: true},
{header:'School',dataIndex:'schoolType',width:80,sortable: true}, 
{header:'Campus',dataIndex:'address',width:100,sortable: true}, 
{header:'Curricula Access',dataIndex:'curriList',width:100,sortable: true},
{header:'Notes',dataIndex:'notes',width:100},
{header:'Created by',dataIndex:'createdBy',width:100,sortable: true},
{header:'Password',  
	xtype: 'actioncolumn',
	align:'center',
	items: [{icon   : '../img/undo.png',  
	         tooltip: 'Reset password',
	         handler: function(grid, rowIndex, colIndex) {
	                        var account = ds2.getAt(rowIndex);
	                        var accountId = account.get('id');
	                        //var reportScope = report.get('reportScope');
	                        Ext.Ajax.request({
								  url:'../etonUser.do?action=resetPwd&userId='+accountId,
								  method:'GET',
								  success:function(response, options){
									  //alert(response.responseText);
									  if(response.responseText == 'Success'){
										  Ext.MessageBox.alert("Status","Password has been reset");
									  }
									  else{
										  Ext.MessageBox.alert("Status","Operation failure. Try again.");
									  }
								  },failure:function(){
								    Ext.MessageBox.alert("Error","Request exception");
								  }}); 
	                  }
	       }]              
	}    
]); 

var ds2 = new Ext.data.Store({ 
proxy:new Ext.data.HttpProxy({
url:'../setting.do?action=getEtonUserList',
method:'POST'
}), 
reader:new Ext.data.JsonReader({
	totalProperty : 'totalProperty',   //page
    root : 'users' },[
{name:'id'}, 
{name:'realName'}, 
{name:'roleName'},
{name:'userName'},
{name : "schoolType",
mapping : "schoolInfo",
convert : function(schoolInfo){
  if(schoolInfo&&schoolInfo.schoolType){
    return schoolInfo.schoolType;
  }
  return null;
}},{name : "address",
	mapping : "schoolInfo",
	convert : function(schoolInfo){
	  if(schoolInfo&&schoolInfo.address){
	    return schoolInfo.address;
	  }
	  return null;
	}},
{name:'curriList'},
{name:'notes'},
{name:'createdBy'}
]) 
}); 

ds2.load({params:{start:0,limit:20}}); 

var grid2 = new Ext.grid.GridPanel({ 
	id:'accounts',
	renderTo:"account_list", 
	stripeRows:true,//斑马线效果
	title:"", 
	height: 800,
	cm:cm2,
	store: ds2,
	loadMask: { msg: "Loading..." }, 
	viewConfig: {   
	                            forceFit:true   
	            }
});


var toolBar = new Ext.Toolbar({  
	renderTo:'toolBar',
	autoWidth:true,  
	autoShow:true,  
	items:[  
	{text:'Add',handler:btnAddAccountClick},
	 '-',
	 {text:'Edit',handler:checkMyAccount},
	 '-',
	 {text:'Delete',handler:deleteAccount}  
	]  
}) ;

var pageBar = new Ext.PagingToolbar({   
    renderTo:'pageBar',
	pageSize: 20,   
    store: ds2, 
    displayInfo: true,   
    displayMsg: 'Displaying users {0}-{1} of {2}',   
    emptyMsg: "No users to display"   ,
    doLoad : function(start){ 
    	   record_start2 = start; 
    	var o = {}, pn = this.getParams(); 
    	          o[pn.start] = start; 
    	          o[pn.limit] = this.pageSize; 
    	this.store.load({params:o}); 
    }
})    ;
//var teacherTypeData = [['0','Admin'],['1','R&D'],['2','ED'],['3','Teacher']];
var teacherTypeStore = new Ext.data.SimpleStore({
    fields: ['teacherTypeId', 'teacherType'],
    data : [['1','R&D'],['2','ED'],['3','Teacher']] 
});

//school data
var schoolStore=new Ext.data.Store({ 
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
//campus data
var campusStore = new Ext.data.Store({ 
	reader:new Ext.data.JsonReader({ },[
	{name:'id'}, 
	{name:'address'}
	]) 
	});

//curriculum data
var curriStore=new Ext.data.Store({ 
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

//新增帐号表单
addAccountForm = new Ext.form.FormPanel({ 
id: 'addAccountForm', 
width: 500, 
height: 360, 
//样式 
bodyStyle: 'margin:3px 3px 3px 3px', 
frame: true, 
xtype: 'filedset', 
labelWidth: 80, 
items: 
[ { xtype: 'fieldset', 
title: 'Account Info', 
collapsible: true, 
autoHeight: true, 
autoWidth: true, 
items: 
[ { layout : "form",
           items : [{
xtype: 'textfield',
id: 'realName', 
name: 'realName', 
fieldLabel: 'First Name', 
emptyText: 'Required', 
blankText: 'Please enter the first name', 
allowBlank: false,  
width:160
}]},{layout : "form",
    items : [{
 	   xtype: 'combo', 
 	   id : "roleId",
       store :teacherTypeStore,
       fieldLabel: 'Role',
       valueField : 'teacherTypeId',
       displayField : 'teacherType',
       emptyText : 'Choose a role...',
       allowBlank : false,
       blankText:'Please choose a role',
       editable: false,
       typeAhead : true,
       width: 160,
       mode:'local',  
       triggerAction : 'all',
       selectOnFocus : true,
       hiddenName:'role'
  
}]},{ layout : "form",
    items : [{
   	 xtype: 'textfield',
   	 vtype: "email", //email格式验证  
        vtypeText: "invalid email format", //错误提示信息,默认值我就不说了 
   	 id: 'email', 
   	 name: 'email', 
   	 fieldLabel: 'Email', 
   	 emptyText: 'Required', 
   	 blankText: 'Please enter email', 
   	 allowBlank: false,  
   	 anchor: '96%' 
   	 }]},
{ layout : "form",
    items : [{
    	xtype: 'combo', 
    	id : "school_Id",
    	       store :schoolStore,
    	       fieldLabel: 'School',
    	       valueField : 'id',
    	       displayField : 'typeName',
    	       emptyText : 'Choose a school...',
    	       allowBlank : true,
    	       editable: false,
    	       typeAhead : true,
    	       width: 160,
    	       mode:'remote', 
    	       hiddenName:'sTypeId',
    	       triggerAction : 'all',
    	       selectOnFocus : true ,
    	       listeners:{        
            	   select : function(combo, record,index)   
            	   {   
            	   		var campusCombo = Ext.getCmp('campus_Id');
            	   		campusCombo.clearValue(); //可以实现当下拉值变更时，清空之前下拉选项中的值   
//                   alert(combo.value);
            	   		campusStore.proxy = new Ext.data.HttpProxy({
            	   				url:'../setting.do?action=getSchoolByTypeId&typeId='+combo.value,
            	   				method:'GET'
            	   		});
            	   		campusStore.load(); //加载下拉框的store      
            	   }      
               }
    	}]},
    	{ layout : "form",
    	    items : [{
    	    	xtype: 'combo', 
    	    	id : "campus_Id",
    	    	       store :campusStore,
    	    	       fieldLabel: 'Campus',
    	    	       valueField : 'id',
    	    	       displayField : 'address',
    	    	       emptyText : 'Choose a campus...',
    	    	       allowBlank : true,
    	    	       editable: false,
    	    	       typeAhead : true,
    	    	       anchor: '96%',
    	    	       mode:'remote', 
    	    	       hiddenName:'schoolId',
    	    	       triggerAction : 'all',
    	    	       selectOnFocus : true 
    	    	}]},
{layout : "form",
    items : [{
    	xtype: 'lovcombo',
    	id:'curriculumsId',
    		fieldLabel: 'Curricula Access',
    		name: 'curriculums',
    		hideOnSelect:false,
    		store: curriStore,
    		valueField: 'id',
            displayField: 'curriculumName',
    		hiddenName: 'curriculumsId',
    		triggerAction: 'all',
    		emptyText : 'Choose curricula access...',
    		allowBlank : true,
    	    editable: false,
    	    typeAhead : true,
    	    selectOnFocus : true,
    	    anchor: '96%',
    		height: 200,
    		mode: 'remote'
   
 }]},{ layout : "form",
     items : [{
       	 xtype: 'textarea',
       	 id: 'notes', 
       	 name: 'notes',  
       	 fieldLabel:'Notes',
       	 //maxLength:100,
       	 //maxLengthText:'最多只允许输入100个汉字',
       	 height:100,
       	 anchor: '96%' 
       	 }]},
{ layout : "form",
           items : [{
xtype: 'hidden', 
 id : "accountid",
 name:'id',
              fieldLabel: 'CID',
              allowBlank: true
}]}
]}]});

//新增窗口 
addAccountWin = new Ext.Window({ 
id: 'addAccountWin', 
title: 'Add/Edit a User', 
width: 520, 
height: 420, 
y:100,
//背景遮罩 
modal: true, 
//重置大小 
resizable: false, 
//当关闭按钮被点击时执行的动作 
closeAction: 'hide', 
plain: true, 
buttonAlign: 'right', 
items:addAccountForm, 
buttons: 
[  
{ text: 'Save', id: 'accountBtnSubmit' } ,
{ text: 'Cancel', handler: function() { Ext.getCmp('addAccountWin').hide(); } }

] 
});


function addAccountFunction() {

	var addForm = Ext.getCmp("addAccountForm");
	if(addForm.form.isValid()){

	 addForm.form.doAction('submit', { 

                    url : '../setting.do?action=addEtonUser', 
 
                    method : 'post', 
 
                    success : function() { 

                        
                        Ext.getCmp('addAccountWin').hide();
                        Ext.getCmp('accounts').store.reload();
                       Ext.Msg.alert('Status', 'Save Succeeded');
  
                        }, 
                         failure : function(form, action) { 

                             Ext.Msg.alert('Status', 'Save Failed'); 
  
                         } 
                       
                    });  
                    }
 }
  


//添加按钮单击事件 
function btnAddAccountClick() { 
Ext.getCmp("addAccountWin").show();
addAccountForm.getForm().getEl().dom.reset(); 
Ext.getCmp("addAccountWin").hide(); 

Ext.getCmp("addAccountWin").buttons[0].handler = addAccountFunction; 
Ext.getCmp("addAccountWin").show(); 
}; 

//新增按钮 
/**
var addABtn = Ext.getCmp('accountBtnAdd'); 
addABtn.on('click', btnAddAccountClick); 
*/

//删除地址
function deleteAccount(){
var grid2 = Ext.getCmp('accounts'); 
if (grid2.getSelectionModel().getSelections()[0] == undefined) { 
Ext.Msg.alert("Prompt", "Please choose a user account to delete"); 
} 
else{
Ext.MessageBox.confirm('Confirm', 'Are you sure to delete this user?', deleteAccountConfirm);
}
}

function deleteAccountConfirm(btn){
if (btn == 'yes') {
//alert(grid.getSelectionModel().getSelections()[0].data.serialNo);
 Ext.Ajax.request({
  url:'../setting.do?action=deleteEtonUserById',
  params : { userId: grid2.getSelectionModel().getSelections()[0].data.id},
  method:'POST',
  success:function(){
   Ext.MessageBox.alert("Status","Deleted");
   Ext.getCmp('accounts').store.reload(); 
  },failure:function(){
    Ext.MessageBox.alert("Status", "Delete failed"); 
  }
});
}
}

//编辑地址
function editAccountFunction(){
	
	var addForm = Ext.getCmp("addAccountForm");

if(addForm.form.isValid()){
	 addForm.form.doAction('submit', { 

                    url : '../setting.do?action=updateEtonUser', 
 
                    method : 'post', 
 
                       
                       success : function() { 

                       
                       Ext.getCmp('account').store.reload();
                       Ext.Msg.alert('Status', 'Save Succeeded'); 
 
                       }, 
                        failure : function(form, action) { 

                           Ext.Msg.alert('Status', 'Save Failed'); 
 
                        } 
                    }); 
                    }
}

function checkMyAccount(){
if (grid2.getSelectionModel().getSelections()[0] == undefined) { 
Ext.Msg.alert("Prompt", "Please choose a user account to edit"); 
}
else{
	var accountId = grid2.getSelectionModel().getSelections()[0].data.id;
	
	Ext.getCmp('addAccountForm').form.reset(); 
	Ext.getCmp("addAccountWin").buttons[0].handler = editAccountFunction; 

	Ext.Ajax.request({
url:'../setting.do?action=getEtonUserById',
params : { userId: accountId},
method:'POST',
  success:function(response){
 // alert(response.responseText);
 var addJSON = Ext.util.JSON.decode(response.responseText);
 
if(addJSON.schoolInfo){
 campusStore.proxy = new Ext.data.HttpProxy({
		url:'../setting.do?action=getSchoolByTypeId&typeId='+addJSON.schoolInfo.typeId,
		method:'GET'
});
campusStore.load(); //加载下拉框的store  
}

  	Ext.getCmp("accountid").setValue(addJSON.id);
	Ext.getCmp("realName").setValue(addJSON.realName);
  	Ext.getCmp("roleId").setValue(addJSON.role);
  	if(addJSON.schoolInfo){
  		Ext.getCmp("school_Id").setValue(addJSON.schoolInfo.typeId);
  		Ext.getCmp("campus_Id").setValue(addJSON.schoolId);
  	}
	Ext.getCmp("curriculumsId").setValue(addJSON.curriIdList);
	Ext.getCmp("email").setValue(addJSON.email);
  	Ext.getCmp("notes").setValue(addJSON.notes);
  	
  	
  	
	Ext.getCmp("addAccountWin").show(); 
	
	
  },failure:function(){
    Ext.MessageBox.alert("Status","Request exception");
  }
}); 

}
}

grid2.addListener('rowdblclick',checkMyAccount);
        
}); 

