Ext.define('HVNExtjs.controller.MainNavigation', {
    extend: 'Ext.app.Controller',

    views: [
        //import views    
        'navigation.MainNavigation',
    ],

    init: function() {
       this.control({
            // click event of "University" button in westPanel 
    	   'westPanel button#btnUniversity': {
    		   click: this.showUniversity
           },
	       'westPanel button#btnTeacher': {
			   click: this.showTeacher
	       }
       });
    },
    
    showUniversity: function(button) {
        var content_region = Ext.getCmp("content-region-container");
        //rename title
        //content_region.setTitle('Universities');
        //remove old contents
        content_region.removeAll(true);
        //add view for universities
        content_region.add({
           xtype: 'universityList',
        });

        Ext.getCmp("universityListID").getStore().load({
            params:{
                start:0,    
                limit: PAGE_SIZE
            }
        });       
    },
    showTeacher: function(button) {
        var content_region = Ext.getCmp("content-region-container");
        //remove old contents
        content_region.removeAll(true);
        //add view for universities
        content_region.add({
           xtype: 'teacherList',
        });
        
        Ext.getCmp("teacherList").getStore().load({
            params:{
                start:0,    
                limit: PAGE_SIZE
            }
        });
    }
});