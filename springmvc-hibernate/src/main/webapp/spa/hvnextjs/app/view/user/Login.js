//Login form
Ext.define('HVNExtjs.view.user.Login' ,{
    extend: 'Ext.window.Window',
    alias : 'widget.userLogin', 
    id: 'userLogin',   
    autoShow: true,              
    height: 170,                 
    width: 360,                  
    layout: {
        type: 'fit'              
    },
    iconCls: 'icon-login',              
    title: "Login",              
    closable: true ,
    cls:'login-window',
    items: 
    [
        {
            xtype: 'form',
            frame: false,
            bodyPadding: 15,
            defaults: {
                xtype: 'textfield',
                anchor: '100%', 
                labelWidth: 60,
                allowBlank: false,
                vtype: 'alphanum',
                msgTarget: 'under',
                minLength: 3,
            },
            items: [
                {
                    name: 'user',    
                    fieldLabel: "User",
                    maxLength: 25
                },
                {
                    inputType: 'password',
                    name: 'password',
                    fieldLabel: "Password",
                    maxLength: 15
                }
            ]
        }
    ],
    
    dockedItems: 
    [
      {
          xtype: 'toolbar',
          dock: 'bottom',
          items: [
              {
                  xtype: 'tbfill'
              },
              {
                  xtype: 'button',
                  itemId: 'btnCancel',
                  iconCls: 'icon-cancel',
                  text: 'Cancel'
              },
              {
                  xtype: 'button',
                  itemId: 'btnSubmit',
                  formBind: true,
                  iconCls: 'icon-submit',
                  text: "Submit"
              }
          ]
      }
  ]
});