// this is parent container that includes all of components
Ext.define('HVNExtjs.view.Container' ,{
    extend: 'Ext.panel.Panel',
    alias : 'widget.mycontainer',
    border: 0,
    id: 'my-contener',
    items: 
    [	
    	//set view which you want to show at the first time application runs.
        {   xtype: 'userLogin' }, 
    ],
});