Ext.define('HVNExtjs.store.Teachers', {
    extend: 'Ext.data.Store',
    model: 'HVNExtjs.model.Teacher',
    autoLoad:  false,
    remoteSort: true,
    pageSize: PAGE_SIZE,
    autoSync: false,
    proxy: {
    	type: 'ajax',
    	url: SERVER_URL + TEACHER_SEARCH,
    	api: {
            create: SERVER_URL + TEACHER_SAVE,
            destroy: SERVER_URL + TEACHER_DELETE,
            read: SERVER_URL + TEACHER_SEARCH,
            update: SERVER_URL + TEACHER_SAVE
        },
        actionMethods : {
            create : 'POST',
            destroy : 'POST',
            read : 'GET',
            update : 'POST'
        },
        reader: {
            type: 'json',
            root: 'data.teachers',
            totalProperty: 'data.totalResult'
        }
    },
    sorters: [{
        property: 'id',
        direction: 'asc'
    }],
    listeners: {
    	add: function(store, records, index, eOpts) {
    		var record = records[0];
    		var entity = {
    				id: record.data.id,
    				degree: record.data.degree,
    				department: record.data.department,
    				name: record.data.name,
    				email: record.data.email,
    				phone: record.data.phone
        		};
    		var myProxy = store.getProxy();
            myProxy.params = {
                access_token: '',
                entity: ''
            };
            myProxy.setExtraParam('access_token', ACCESS_TOKEN);
            myProxy.setExtraParam('entity', Ext.JSON.encode(entity));
    	},
    	beforeload: function(store, options, eOpts) {
            var criteria = {
        		pageSize: options.limit,
        		pageIndex: options.page,
        		sortBy: options.sorters[0].property,
        		sortDirection: options.sorters[0].direction
            };
            var myProxy = store.getProxy();
            myProxy.params = {
                access_token: '',
                criteria: ''
            };
            myProxy.setExtraParam('access_token', ACCESS_TOKEN);
            myProxy.setExtraParam('criteria', Ext.JSON.encode(criteria));
        },
    	remove: function(store, record, index, eOpts) {
    		var myProxy = store.getProxy();
            myProxy.params = {
                access_token: '',
                id: ''
            };
            myProxy.setExtraParam('access_token', ACCESS_TOKEN);
            myProxy.setExtraParam('id', record.data.id);
    	},
    	update: function(store, record, operation, eOpts) {
    		var entity = {
				id: record.data.id,
				degree: record.data.degree,
				department: {
					id: record.data.department.id
				},
				name: record.data.name,
				email: record.data.email,
				phone: record.data.phone
    		};
    		var myProxy = store.getProxy();
            myProxy.params = {
                access_token: '',
                entity: ''
            };
            myProxy.setExtraParam('access_token', ACCESS_TOKEN);
            myProxy.setExtraParam('entity', Ext.JSON.encode(entity));
    	}
    }
});
