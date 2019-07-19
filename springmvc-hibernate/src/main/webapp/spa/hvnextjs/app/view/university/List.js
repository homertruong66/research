//list universities will be showed right here
Ext.define('HVNExtjs.view.university.List' ,{
    extend: 'Ext.grid.Panel',
    alias : 'widget.universityList',
    store: 'Universities',
    title : 'Universities',
    id: 'universityListID',
    initComponent: function() {

        this.columns = [
            {header: 'Name',  dataIndex: 'name',  flex: 1},
            {header: 'Phone',  dataIndex: 'phone'},
            {header: 'Address',  dataIndex: 'address', flex: 1},
            {header: 'Country',  dataIndex: 'country'},
            {header: 'Provine', dataIndex: 'provine'}
        ];

        this.dockedItems = [ {
            xtype : 'pagingtoolbar',
            store : 'Universities',
            dock : 'bottom',
            displayInfo : true,
            items: ['-',{
                itemId: 'btnAdd',
                text: 'Add',
                iconCls: 'icon-add',
            }]
        } ];

        this.callParent(arguments);
    }
});