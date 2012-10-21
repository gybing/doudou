Ext.onReady(function(){ 

Ext.QuickTips.init();
Ext.form.Field.prototype.msgTarget = 'under';

var record_start3 = 0; 

var sm3 = new Ext.grid.CheckboxSelectionModel();
var cm3 = new Ext.grid.ColumnModel([ 
sm3,
new Ext.grid.RowNumberer({header: "ID",
width:40, 
renderer:function(value,metadata,record,rowIndex){ 
return record_start3 + 1 + rowIndex; 
} 
}),
{header:'ID',dataIndex:'id',hidden:true},
{header:'School',dataIndex:'typeName',width:100},
{header:'Created by',dataIndex:'creator',width:100}
]); 
var ds3 = new Ext.data.Store({ 
proxy:new Ext.data.HttpProxy({
url:'../setting.do?action=getSchoolTypes',
method:'POST'
}), 
reader:new Ext.data.JsonReader({ },[
{name:'id'}, 
{name:'typeName'},
{name:'creator'}
]) 
}); 

ds3.load({params:{start:0,limit:20}}); 

var grid3 = new Ext.grid.GridPanel({ 
	id:'schools',
	renderTo:"school_list", 
	stripeRows:true,//斑马线效果
	title:"", 
	height:200, 
	cm:cm3,
	store: ds3,
	loadMask: { msg: "Loading..." }, 
	viewConfig: {   
	                            forceFit:true   
	            }
});

var s_toolBar = new Ext.Toolbar({  
	renderTo:'s_toolBar',
	autoWidth:true,  
	autoShow:true,  
	items:[  
	{text:'Add',handler:btnAddClick_S},
	 '-',
	 {text:'Edit',handler:checkSchool},
	 '-',
	 {text:'Delete',handler:deleteSchool} 
	]  
}) ;

var s_pageBar = new Ext.PagingToolbar({   
    renderTo:'s_pageBar',
    pageSize: 20,   
    store: ds3, 
    displayInfo: true,   
    displayMsg: 'Displaying schools {0}-{1} of {2}',   
    emptyMsg: "No data to display"   ,
    doLoad : function(start){ 
    	   record_start3 = start; 
    	var o = {}, pn = this.getParams(); 
    	          o[pn.start] = start; 
    	          o[pn.limit] = this.pageSize; 
    	this.store.load({params:o}); 
    }
});

addSchoolForm = new Ext.form.FormPanel({ 
id: 'addSchoolForm', 
width: 480, 
height: 100, 
//样式 
bodyStyle: 'margin:3px 3px 3px 3px', 
frame: true, 
xtype: 'filedset', 
labelWidth: 60, 
items: 
[ { xtype: 'fieldset', 
title: 'School Info', 
collapsible: true, 
autoHeight: true, 
autoWidth: true, 
items: 
[ { layout : "form",
           items : [{
xtype: 'textfield',
id: 'typeName', 
name: 'typeName', 
fieldLabel: 'School', 
emptyText: 'Required', 
blankText: 'Please enter a school name', 
allowBlank: false,  
anchor: '96%' 
}]},
{ layout : "form",
           items : [{
xtype: 'hidden', 
id : "schoolId",
name:'id',
fieldLabel: 'ID',
allowBlank: true
}]}
]}]});

//新增课程窗口 
addSchoolWin = new Ext.Window({ 
id: 'addSchoolWin', 
title: 'Add/Edit a School', 
width: 500, 
height: 150, 
y:100,
//背景遮罩 
modal: true, 
//重置大小 
resizable: false, 
//当关闭按钮被点击时执行的动作 
closeAction: 'hide', 
plain: true, 
buttonAlign: 'right', 
items:addSchoolForm, 
buttons: 
[  
{ text: 'Save', id: 'btnSubmit_S' } ,
{ text: 'Cancel', handler: function() { Ext.getCmp('addSchoolWin').hide(); } }

] 
});


function addSchoolFunction() {

	//var addForm = Ext.getCmp("addCurriculumForm");
	if(addSchoolForm.form.isValid()){

		addSchoolForm.form.doAction('submit', { 

                    url : '../setting.do?action=addSchoolType', 
 
                    method : 'post', 
 
                       
                       success : function() { 

                       Ext.getCmp('addSchoolWin').hide();
                       Ext.getCmp('schools').store.reload();
                      Ext.Msg.alert('Status', 'Save Succeeded'); 
 
                       }, 
                        failure : function(form, action) { 

                           Ext.Msg.alert('Status', 'Save failed'); 
 
                        } 
                    });  
                    }
 }
  


//添加按钮单击事件 
function btnAddClick_S() { 
Ext.getCmp("addSchoolWin").show();
addSchoolForm.getForm().getEl().dom.reset(); 
Ext.getCmp("addSchoolWin").hide(); 

Ext.getCmp("addSchoolWin").buttons[0].handler = addSchoolFunction; 
Ext.getCmp("addSchoolWin").show(); 
}; 

//新增按钮 

//var addUserBtn = Ext.getCmp('btnAdd'); 
//addUserBtn.on('click', btnAddClick); 

//删除地址
function deleteSchool(){
	//var grid = Ext.getCmp('myCustomers'); 
	if (grid3.getSelectionModel().getSelections()[0] == undefined) { 
	Ext.Msg.alert("Prompt", "Please choose a school to delete"); 
	} 
	else{
		Ext.MessageBox.confirm('Prompt', 'Campuses of this school will be deleted too，Continue?', ssConfirm);
	}
	}

	function ssConfirm(btn){
		if(btn == 'yes')
			Ext.MessageBox.confirm('Prompt', 'Are you sure to delete this school?', deleteConfirm_S);
	}

	function deleteConfirm_S(btn){
	if (btn == 'yes') {
	//alert(grid.getSelectionModel().getSelections()[0].data.serialNo);
	 Ext.Ajax.request({
	  url:'../setting.do?action=deleteSchoolTypeById',
	  params : { schoolTypeId: grid3.getSelectionModel().getSelections()[0].data.id},
	  method:'POST',
	  success:function(){
	   Ext.MessageBox.alert("Status","Deleted");
	   Ext.getCmp('schools').store.reload(); 
	  },failure:function(){
	    Ext.MessageBox.alert("Status", "Delete failed"); 
	  }
	});
	}
	}

//编辑
function editSchoolFunction(){
	
	//var addForm = Ext.getCmp("addCustomerForm");

if(addSchoolForm.form.isValid()){
	addSchoolForm.form.doAction('submit', { 

                    url : '../setting.do?action=updateSchoolType', 
 
                    method : 'post', 
 
                       
                       success : function() { 

                    	   Ext.getCmp('addSchoolWin').hide();
                       Ext.getCmp('schools').store.reload();
                       Ext.Msg.alert('Status', 'Save Succeeded'); 
 
                       }, 
                        failure : function(form, action) { 

                           Ext.Msg.alert('Status', 'Save failed'); 
 
                        } 
                    }); 
                    }
}

function checkSchool(){
if (grid3.getSelectionModel().getSelections()[0] == undefined) { 
Ext.Msg.alert("Prompt", "Please choose a school to edit"); 
}
else{
	var id = grid3.getSelectionModel().getSelections()[0].data.id;
	
	Ext.getCmp('addSchoolForm').form.reset(); 
	Ext.getCmp("addSchoolWin").buttons[0].handler = editSchoolFunction; 

	Ext.Ajax.request({
		url:'../setting.do?action=getSchoolTypeById',
		params : { id: id},
		method:'POST',
		success:function(response){
            //alert(response.responseText);
			var addJSON = Ext.util.JSON.decode(response.responseText);
 
			//alert(addJSON.curriculumName);
	
  	Ext.getCmp("schoolId").setValue(addJSON.id);
	Ext.getCmp("typeName").setValue(addJSON.typeName);
  	
  	
  	
  	
	Ext.getCmp("addSchoolWin").show(); 
	
	
  },failure:function(){
    Ext.MessageBox.alert("Status","Request exception");
  }
}); 


}
}

grid3.addListener('rowdblclick',checkSchool);




}); 