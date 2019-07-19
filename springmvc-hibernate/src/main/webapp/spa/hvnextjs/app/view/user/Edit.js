//Form edit user;
Ext.define('HVNExtjs.view.user.Edit', {
    extend: 'Ext.window.Window',
    alias : 'widget.useredit',

    title : 'User Editing',
    layout: 'fit',
    autoShow: true,

    initComponent: function() {
        this.items = [
            {
                xtype: 'form',
                items: [
                    {
                        xtype: 'textfield',
                        name : 'name',
                        fieldLabel: 'Name'
                    },
                    {
                        xtype: 'textfield',
                        name : 'email',
                        fieldLabel: 'Email'
                    }
                ]
            }
        ];

        this.buttons = [
            {
                text: 'Save',
                itemId: 'btnSave'
            },
            {
                text: 'Cancel',
                itemId: 'btnCancel'
            }
        ];

        this.callParent(arguments);
    }
});