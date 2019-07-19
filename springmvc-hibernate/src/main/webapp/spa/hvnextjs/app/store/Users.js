Ext.define('HVNExtjs.store.Users', {
    extend: 'Ext.data.Store',
    model: 'HVNExtjs.model.User',
    autoLoad: true,

    proxy: {
        type: 'ajax',
        url: 'resources/data/users.json',
        reader: {
            type: 'json',
            root: 'users',
            successProperty: 'success'
        }
    }

});