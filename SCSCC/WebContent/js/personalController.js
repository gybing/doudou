Ext.onReady(function(){ 

Ext.QuickTips.init();
Ext.form.Field.prototype.msgTarget = 'under';

Ext.apply(Ext.form.VTypes,{
    password:function(val,field){//val指这里的文本框值，field指这个文本框组件，大家要明白这个意思
      if(field.confirmTo){//confirmTo是我们自定义的配置参数，一般用来保存另外的组件的id值
          var pwd=Ext.get(field.confirmTo);//取得confirmTo的那个id的值
          return (val==pwd.getValue());
      }
      return true;
    }
});

//新增帐号表单
addAccountForm = new Ext.form.FormPanel({ 
id: 'addAccountForm', 
renderTo:"personal_list", 
width: 1000, 
height: 200, 
//样式 
bodyStyle: 'margin:3px 3px 3px 3px', 
frame: true, 
xtype: 'filedset', 
labelWidth: 200, 
items: 
[ { xtype: 'fieldset', 
title: 'Modify Password', 
collapsible: true, 
autoHeight: true, 
autoWidth: true, 
items: 
[ { layout : "form",
           items : [{
xtype: 'textfield',
id: 'oldPwd', 
name: 'oldPwd', 
inputType: 'password',
fieldLabel: 'Old password', 
emptyText: 'Required', 
blankText: 'Please enter the old password', 
allowBlank: false,  
width:160
}]},{ layout : "form",
    items : [{
    	xtype: 'hidden', 
    	id : "oldPassword",
    	name:'oldPassword',
    	fieldLabel: 'pwd',
    	allowBlank: true
    	}]},
{ layout : "form",
    items : [{
    	xtype: 'textfield',
    	id: 'newPwd', 
    	name: 'newPwd', 
    	inputType: 'password',
    	fieldLabel: 'New Password', 
    	emptyText: 'Required', 
    	blankText: 'Please enter the new password', 
    	allowBlank: false,  
    	width:160
    	}]},{ layout : "form",
    	    items : [{
    	    	xtype: 'hidden', 
    	    	id : "newPassword",
    	    	name:'newPassword',
    	    	fieldLabel: 'pwd',
    	    	allowBlank: true
    	    	}]},
    	{ layout : "form",
            items : [{
 xtype: 'textfield',
 id: 'confirmNewPwd', 
 name: 'confirmNewPwd', 
 inputType: 'password',
 vtype:"password",//自定义的验证类型
 vtypeText:"The two passwords are inconsistent",
 confirmTo:"newPwd",//要比较的另外一个的组件的id
 fieldLabel: 'Confirm New Password', 
 emptyText: 'Required', 
 blankText: 'Please confirm the new password', 
 allowBlank: false,  
 width:160
 }]}
]}],
buttons: 
	[  
	{ text: 'Save', id: 'accountBtnSubmit',handler:addAccountFunction } ,
	{ text: 'Cancel', handler: function() { Ext.getCmp('addAccountForm').form.reset(); } }

	] 
});


function addAccountFunction() {

	var addForm = Ext.getCmp("addAccountForm");
	if(addForm.form.isValid()){
		
			var pwd1 = Ext.getCmp("oldPwd").getValue();
			var md5PWD1 = hex_md5(pwd1);
			Ext.getCmp("oldPassword").setValue(md5PWD1);
			
			var pwd2 = Ext.getCmp("newPwd").getValue();
			var md5PWD2 = hex_md5(pwd2);
			Ext.getCmp("newPassword").setValue(md5PWD2);
		

	 addForm.form.doAction('submit', { 

                    url : '../etonUser.do?action=modifyPwdForWeb', 
 
                    method : 'post', 
 
                    success : function(form, action) { 

                    	//alert(action.result.msg);
                       if(action.result.msg == 1){
                           Ext.Msg.alert('Status', 'Modify Succeeded');
                       }
                       else if(action.result.msg == 0){
                           Ext.Msg.alert('Status', 'Wrong old password');
                       }
  
                        }, 
                         failure : function(form, action) { 

                             Ext.Msg.alert('Status', 'Modify Failed'); 
  
                         } 
                       
                    });  
                    }
 }
  






        
}); 

