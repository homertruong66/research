//list of buttons
Ext.define('HVNExtjs.view.navigation.West' ,{

    extend: 'Ext.panel.Panel',
    alias: 'widget.westPanel',
    region: 'west',
    title: 'Navigation',
    width: 180,
    collapsible: true,
    bodyStyle: 'padding:15px',
    
    defaults: 
    {
        xtype: 'button',
        width: 150,
        height: 35,
        style: 'margin-bottom: 10px;',
    },

    items: 
    [
        {
            text: 'University',            
            itemId: 'btnUniversity'
        },
        {
            text: 'Deparment',
        },
        {
            text: 'Course',
        },
        {
            text: 'Employee',
        },
        {
            text: 'Teacher',
            itemId: 'btnTeacher'
        },
        {
            text: 'Student',
        },
        {
            text: 'Class',
        },
        {
            text: 'Deparment',
        }
    ]
});