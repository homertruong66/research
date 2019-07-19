//form edit teacher
Ext.define('HVNExtjs.view.teacher.Edit', {
    extend: 'Ext.window.Window',
    alias : 'widget.teacherEdit',
    id: 'teacherEdit',
    title : 'Teacher Editing',
    layout: 'fit',
    autoShow: true,

    initComponent: function() {
        this.items = [
            {
                xtype: 'form',
                width: 310,
                
                defaults:{
                    width: 300,
                },

                items: [
					{
					    xtype: 'hiddenfield',
					    name : 'teacherId',
					    value: DEFAULT_ID
					},
                    {
                        xtype: 'textfield',
                        name : 'name',
                        fieldLabel: 'Name',
                        allowBlank: false
                    },
                    {
                        xtype: 'textfield',
                        name : 'phone',
                        fieldLabel: 'Phone',
                        maskRe: /[0-9]/
                    },
                    {
                        xtype: 'textfield',
                        name : 'email',
                        fieldLabel: 'Email',
                        vtype: 'email',
                        allowBlank: false
                    },
                    {
                        xtype: 'combobox',
                        name : 'department',
                        fieldLabel: 'Department',
                        valueField: 'id',
                        displayField: 'name'
                        
                    },
                    {
                        xtype: 'textfield',
                        name : 'degree',
                        fieldLabel: 'Degree'
                    }
                ]
            }
        ];

        this.buttons = [
            {
                text: 'Save',
                itemId: 'btnSave'
            },
            {
                text: 'Cancel',
                itemId: 'btnCancel'
            }
        ];

        this.callParent(arguments);
    },
    bind: function(model) {
    	var form = this.down('form').getForm();
    	
    	if (model.departments != null) {
	    	var store = Ext.create('Ext.data.Store', {
	    		fields: ['id', 'name', 'location'],
	    	    data : model.departments
	    	});
	    	var combobox = form.findField('department');
	    	combobox.clearValue();
	        combobox.bindStore(store);
    	}
    	
    	var teacher = model.teacher;
    	if (teacher != null) {
	    	form.findField('teacherId').setValue(teacher.id);
	    	form.findField('name').setValue(teacher.name);
	    	form.findField('phone').setValue(teacher.phone);
	    	form.findField('email').setValue(teacher.email);
	    	combobox.setValue(teacher.department.id);
	        combobox.setRawValue(teacher.department.name);
	    	form.findField('degree').setValue(teacher.degree);
    	}
    },
    unbind: function() {
    	var form = this.down('form').getForm();
    	values = form.getValues();
    	var teacherModel = Ext.create('HVNExtjs.model.Teacher');
        teacherModel.set('id', values.teacherId);
    	teacherModel.set('name', values.name);
    	teacherModel.set('phone', values.phone);
    	teacherModel.set('email', values.email);
    	teacherModel.set('department', {id: values.department});
    	teacherModel.set('degree', values.degree);
    	return teacherModel;
    }
});