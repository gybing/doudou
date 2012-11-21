Ext.onReady(function(){ 

Ext.QuickTips.init();
Ext.form.Field.prototype.msgTarget = 'under';

var record_start = 0; 

var sm = new Ext.grid.CheckboxSelectionModel();
var cm=new Ext.grid.ColumnModel([ 
sm,
new Ext.grid.RowNumberer({header: "ID",
width:40, align:'center',
renderer:function(value,metadata,record,rowIndex){ 
return record_start + 1 + rowIndex; 
} 
}),
{header:'ID',dataIndex:'id',hidden:true},
{header:'User',dataIndex:'realName',width:100,align:'center',sortable: true},
{header:'Last Login Time',dataIndex:'lastLoginTime',width:100,align:'center',sortable: true},
{header:'Device ID',dataIndex:'username',width:100,align:'center',sortable: true},
{header:'Last Operation',dataIndex:'username',width:100,align:'center',sortable: true},
{header:'Remote Wipe',  
	xtype: 'actioncolumn',
	align:'center',
	items: [{icon   : '../img/wipe.png',  
	         tooltip: 'Remote Wipe',
	         handler: function(grid, rowIndex, colIndex) {
	                        var user = ds.getAt(rowIndex);
	                        var apn_userName = user.get('username');
	                        //var reportScope = report.get('reportScope');
	                        Ext.Ajax.request({
								  url:'../user.do?action=remoteWipe&apn_userName='+apn_userName,
								  method:'GET',
								  success:function(){
									    Ext.MessageBox.alert("Status","Remote Wipe has been triggered and data will be wiped when the tablet is connected to the Internet.");
								  },failure:function(){
								    Ext.MessageBox.alert("Error","Request exception");
								  }}); 
	                  }
	       }]              
}
]); 
var ds = new Ext.data.Store({ 
proxy:new Ext.data.HttpProxy({
url:'../user.do?action=getUserList',
method:'POST'
}), 
reader:new Ext.data.JsonReader({
	totalProperty : 'totalProperty',   //page
    root : 'user' },[
{name:'id'}, 
{name:'realName'},
{name:'lastLoginTime'},
{name:'username'},
]) 
}); 

ds.load({params:{start:0,limit:20}}); 

var grid=new Ext.grid.GridPanel({ 
	id:'curriculums',
	renderTo:"security_list", 
	title:"User and Device List",
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
	                displayMsg: 'Displaying users {0}-{1} of {2}',   
	                emptyMsg: "No data to display"   ,
	                doLoad : function(start){ 
	                	   record_start = start; 
	                	var o = {}, pn = this.getParams(); 
	                	          o[pn.start] = start; 
	                	          o[pn.limit] = this.pageSize; 
	                	this.store.load({params:o}); 
	                }
	            })              
});

}); 