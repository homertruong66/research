//this view will be displayed when user logined successfully;
//left: list of buttons;
//right: content's detail will be showed when you click on a button in left;
Ext.define('HVNExtjs.view.navigation.MainNavigation' ,{
    extend: 'Ext.panel.Panel',
    alias : 'widget.mainNavigation',    
    height: 650,
    title: '<div class="parent-title">University Management</div>',
    layout: 'border',
    header: {
        titleAlign: 'center',
        height: 40,
    },
    
    //auto hiden split
    defaults: {
	    split: true,
	},
    
    requires: [
        //import Views
        'HVNExtjs.view.navigation.West',
        'HVNExtjs.view.navigation.Center',
    ],

    items: 
    [
        {   xtype: 'westPanel' }, 

    	{   xtype: 'centerPanel' }
    ],
});