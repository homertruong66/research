Ext.define('HVNExtjs.controller.User', {
    extend: 'Ext.app.Controller',

    views: [
        'user.List',
        'user.Edit',
        'user.Login'
    ],

    stores: ['Users'],

    models: ['User'],

    init: function() {
        this.control({
            "userLogin button#btnSubmit": {
                click: this.onButtonClickSubmit
            },
            "userLogin button#btnCancel": {
                click: this.onButtonClickCancel
            },
            "userLogin textfield": {
                specialkey: this.onTextfieldSpecialKey
            }
        });
    },

    //enter event;
    onTextfieldSpecialKey: function(field, e, options) {
        if (e.getKey() == e.ENTER){
            var submitBtn = field.up('window').down('button#btnSubmit');
            submitBtn.fireEvent('click', submitBtn, e, options);
        }
    },

    onButtonClickSubmit: function(button) {

    	var formPanel = button.up('window').down('form'),
        userName = formPanel.down('textfield[name=user]').getValue(),
        password = formPanel.down('textfield[name=password]').getValue();

    	if (formPanel.getForm().isValid()) {
            //call ajax to server;
            Ext.getCmp('userLogin').el.mask("Authenticating... Please wait...", 'loading'); 
            //Ext.getCmp(userLogin.getEl()).mask("Authenticating... Please wait...", 'loading');

            var params = [userName, password];
            sendAuthenticationRequest(LOGIN_AUTHENICATION, params);
            var model = RESPONSE_MODEL;
            //hide loading;
            Ext.getCmp('userLogin').el.unmask();
            if (model.status == RESPONSE_STATUS_SUCCESS) {
                //set token as a global varible;
                ACCESS_TOKEN = model.data.access_token;
                //show navigation form;
                var container = Ext.getCmp("my-contener");  
                container.removeAll(true);        
                container.add(Ext.create('HVNExtjs.view.navigation.MainNavigation'));
                //close login form;
                button.up('window').close();
            } else {
                //show error on messagebox;
                Ext.Msg.show({
                    title:'Error!',
                    msg: model.error,
                    icon: Ext.Msg.ERROR,
                    buttons: Ext.Msg.OK
                });
            }
        }
    },    

    onButtonClickCancel: function(button) {
    	button.up('window').down('form').getForm().reset();
    },
});