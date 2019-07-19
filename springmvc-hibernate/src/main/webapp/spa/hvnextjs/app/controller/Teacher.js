Ext.define('HVNExtjs.controller.Teacher', {
    extend: 'Ext.app.Controller',

    views: [
        'teacher.List',
        'teacher.Edit'
    ],
    stores: ['Teachers'],
    models: ['Teacher'],
    
    init: function() {
        this.control({
            'teacherList': {
                itemdblclick: this.onUpdate
            },
            'teacherList button#btnAdd': {
            	click: this.onCreate
            },
            'teacherList button#btnDelete': {
            	click: this.onDelete
            },
            'teacherList button#btnTransfer': {
            	click: this.onTransfer
            },
            'teacherEdit button#btnSave': {
                click: this.onSave
            },
            'teacherEdit button#btnCancel': {
                click: this.onClose
            }
        });
    },
    
    onClose: function(button) {
        var win = button.up('window');
        win.close();
    },
    
    showTeacherEditView: function(teacherId) {
    	// Prepare view 
        var view = Ext.widget('teacherEdit');
        
        // Get Teacher info
        var params = [ACCESS_TOKEN, teacherId];
        sendGetRequest(TEACHER_GET_TO_EDIT, params, false, null);
        if (RESPONSE_MODEL.status == RESPONSE_STATUS_SUCCESS) {
        	// Prepare model
            var model = RESPONSE_MODEL.data;

            // Bind model to view
            view.bind(model);
        } else {
        	Ext.Msg.show({
                title      : 'Error',
                msg        : RESPONSE_MODEL.error,
                buttons    : Ext.MessageBox.OK,
                icon       : Ext.MessageBox.ERROR
             });
        }
    },
    
    onCreate: function() {
    	this.showTeacherEditView(DEFAULT_ID);
    },
    
    onDelete: function() {
    	var view = Ext.getCmp('teacherList');;
    	var store = this.getTeachersStore();
    	
		var selectedRow = view.getSelectionModel().getSelection()[0];
        
        if(selectedRow) {
        	store.remove(selectedRow);
	        store.sync({
	            callback: function()
	            {
	            	store.reload();
	            },
	            scope: this
	        });
        } else {
            Ext.Msg.alert('Status', 'Please select one record to delete!');
        }
    },

    onUpdate: function(grid, record) {
    	this.showTeacherEditView(record.data.id);
    },
    
    onSave: function(button) {
        var win = button.up('window'),
        form   = win.down('form');
        
        if (form.getForm().isValid()) {
        	var store = this.getTeachersStore();

            // create or edit mode
        	var teacherModel = win.unbind();
        	store.add(teacherModel);

            store.sync({
                callback: function()
                {
                	store.reload();
                },
                scope: this
            });
            win.close();
        }
    },
    onTransfer: function() {
    	alert('transfer');
    }
});