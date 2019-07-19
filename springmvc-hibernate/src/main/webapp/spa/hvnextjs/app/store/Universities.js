Ext.define('HVNExtjs.store.Universities', {
    extend: 'Ext.data.Store',
    model: 'HVNExtjs.model.University',
    autoLoad: {start: 0, limit: PAGE_SIZE},
    pageSize: PAGE_SIZE,
    autoLoad: false,

    proxy: {
        type: 'ajax',        
        url : UNIVERSITY_SEARCH,
        reader: {
            type: 'json',
            root: 'data.universityEditModels',
            totalProperty: 'data.totalResult'
        }
    },

    listeners: {
        beforeload: function (store, operation, eOpts) {
            var page = operation.page;
            var limit = operation.limit;  
            
            var criteria = {
              pageSize: limit,
              pageIndex: page,              
            };

            var myProxy = store.getProxy();
            myProxy.params = {
                access_token: '',
                criteria: ''
            };
            myProxy.setExtraParam('access_token', ACCESS_TOKEN);
            myProxy.setExtraParam('criteria', Ext.JSON.encode(criteria));
        },
    }
});