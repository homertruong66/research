Ext.Loader.setConfig({enabled:true});
Ext.application({
    // define application's name
    name: 'HVNExtjs',

    // define app folder
    appFolder: 'app',

    // define controllers
    controllers: [
        'Container',
        'MainNavigation',
        'University',
        'User',
        'Teacher'
    ],

    // app's launch function
    launch: function() {

        Ext.create('Ext.container.Viewport', {
            items: 
            [
                {
                    xtype: 'mycontainer'
                }
            ]
        });
    }
});