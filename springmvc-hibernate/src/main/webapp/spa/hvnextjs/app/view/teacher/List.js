Ext.define('HVNExtjs.view.teacher.List' ,{
    extend: 'Ext.grid.Panel',
    id: 'teacherList',
    alias : 'widget.teacherList',
    store: 'Teachers',
    title : 'Teachers',
    initComponent: function() {
    	this.columns = [{
            id: 'id',
            text: 'ID',
            dataIndex: 'id',
            flex: 1,
            sortable: false
        },{
        	id: 'name',
            text: "Name",
            dataIndex: 'name',
            flex: 1,
            sortable: true
        },{
        	id: 'phone',
            text: 'Phone',
            dataIndex: 'phone',
            flex: 1,
            sortable: true
        },{
        	id: 'email',
            text: 'Email',
            dataIndex: 'email',
            flex: 1,
            sortable: true
        },{
        	id: 'department',
            text: 'Department',
            dataIndex: 'department',
            renderer: renderDepartment,
            flex: 1,
            sortable: false
        },{
        	id: 'degree',
            text: 'Degree',
            dataIndex: 'degree',
            flex: 1,
            sortable: true
        }];
        // paging bar on the bottom
    	this.dockedItems = [{
			xtype: 'pagingtoolbar',
			store: 'Teachers',
			dock: 'bottom',
			displayInfo: true,
			items: ['-',{
				itemId: 'btnAdd',
                text: 'Add',
                iconCls: 'icon-add',
            },'-',{
            	itemId: 'btnDelete',
                text: 'Delete',
                iconCls: 'icon-delete'
            },'-',{
            	itemId: 'btnTransfer',
                text: 'Transfer',
                iconCls: 'icon-transfer'
            }]
		}];
        
        this.callParent(arguments);
    }
});

function renderDepartment(value, p, record) {
	return value.name;
}