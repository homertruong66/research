//form edit university
Ext.define('HVNExtjs.view.university.Edit', {
    extend: 'Ext.window.Window',
    alias : 'widget.universityEdit',

    title : 'University Editing',
    layout: 'fit',
    autoShow: true,

    id: 'universityEditID',

    requires: [
        //import Views
        'HVNExtjs.view.university.Countries',
        'HVNExtjs.view.university.Provines',
    ],

    initComponent: function() {
        this.items = [
            {
                xtype: 'form',
                width: 310,
                
                defaults:{
                    width: 300,
                },

                items: [
                    {
                        xtype: 'textfield',
                        id: 'universityEditName',
                        name : 'name',
                        fieldLabel: 'Name'                       
                    },
                    {
                        xtype: 'textfield',
                        id: 'universityEditPhone',
                        name : 'phone',
                        fieldLabel: 'Phone'
                    },
                    {
                        xtype: 'textfield',
                        id: 'universityEditAddress',
                        name : 'address',
                        fieldLabel: 'Address'
                    },
                    {
                        xtype: 'cbbCountry',
                    },
                    {
                        xtype: 'cbbProvine',
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