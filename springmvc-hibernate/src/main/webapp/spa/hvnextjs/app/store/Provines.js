Ext.define('HVNExtjs.store.Provines', {
    extend: 'Ext.data.Store',
    model: 'HVNExtjs.model.Domain',
    autoLoad: false,

    proxy: {
        type: 'ajax',        
        url : DOMAIN_SEARCH,
        reader: {
            type: 'json',
            root: 'data'
        }
    },

    listeners: {
        beforeload: function (store, operation, eOpts) {

            var myProxy = store.getProxy();
            myProxy.params = {
                access_token: '',
                criteria: ''
            };
            myProxy.setExtraParam('access_token', ACCESS_TOKEN);
        },
    }
});