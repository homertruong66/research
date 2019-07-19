Ext.define('HVNExtjs.controller.University', {
    extend: 'Ext.app.Controller',

    views: [
        'university.List',
        'university.Edit',
        'university.Countries'
    ],

    stores: ['Universities'],

    models: ['University'],

    init: function() {
        this.control({
            'universityList': {
                itemdblclick: this.showEdit
            },
            'universityEdit button#btnSave': {
                click: this.update
            },
            'universityEdit button#btnCancel': {
                click: this.closeUniversity
            },
            'universityList button#btnAdd': {
                click: this.showAdd
            }
        });
    },

    showAdd: function(){
        var addView = Ext.widget('universityEdit');
        addView.down('form').load();

        var coboCountry = addView.down('cbbCountry');
        var coboProvine = addView.down('cbbProvine');
        coboCountry.setRawValue('Select Country');
        coboProvine.setRawValue('Select Provine');

        UNIVERSITY_ID = '-1';
    },

    showEdit: function(grid, record) {
        PROVINE_NAME = record.data.provine;
        UNIVERSITY_ID = record.data.id;

        var editView = Ext.widget('universityEdit');

        //call ajax get university by universityID;
        var university = sendRequestToServer(UNIVERSITY_GET_TO_EDIT, record.data.id, 'GET');
        //call ajax get countries;
        var country = sendRequestToServer(COUNTRY_SEARCH, '', 'GET');
        
        var cbbCountries = editView.down('cbbCountry');
        var storeCountries = cbbCountries.getStore();
        storeCountries.load();   

        //bind data to compoment;
        this.bind(university, country, cbbCountries);

        //show edit popup;
        editView.down('form').load();
    },

    update: function(button) {

        var university = this.unbind();

        var criteria = {
            id: university.get('id'),
            name: university.get('name'),        
            address: university.get('address'),
            provine: university.get('provine'),
            country: university.get('country'),
            phone: university.get('phone')
        };

        var param = Ext.JSON.encode(criteria);

        var idUpdated = sendRequestToServer(UNIVERSITY_SAVE, param, 'POST');
        var editView = Ext.getCmp("universityEditID");
        if(idUpdated > 1){
            editView.close();
            Ext.getCmp('universityListID').getStore().load();
        }else{
            alert("Please check your network");
            editView.close();
        }
    },

    closeUniversity: function(button) {
        var win = button.up('window');
        win.close();
    },

    bind: function(university, country, cbbCountries){
        //find and set current country to "country" combobox; 
        for(var i=0; i<country.length; i++){
            if(country[i].name == university.country){
                cbbCountries.setValue(country[i].code);
            }   
        }
        
        //set values to each textfied;
        Ext.getCmp('universityEditName').setValue(university.name);
        Ext.getCmp('universityEditPhone').setValue(university.phone);
        Ext.getCmp('universityEditAddress').setValue(university.address);
    },

    unbind: function(){

        var name = Ext.getCmp('universityEditName').getValue();
        var phone = Ext.getCmp('universityEditPhone').getValue();
        var address = Ext.getCmp('universityEditAddress').getValue();

        var editView = Ext.getCmp("universityEditID");
        var coboCountry = editView.down('cbbCountry');
        var coboProvine = editView.down('cbbProvine');

        var country = coboCountry.getRawValue();
        var provine = coboProvine.getRawValue();

        var university = Ext.create('HVNExtjs.model.University');
        university.set('id', UNIVERSITY_ID);
        university.set('name', name);
        university.set('phone', phone);
        university.set('address', address);
        university.set('country', country);
        university.set('provine', provine);

        return university;
    }
});