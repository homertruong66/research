Ext.define('HVNExtjs.view.university.Countries', {
    
    extend: 'Ext.form.ComboBox',

    alias: 'widget.cbbCountry',

    displayField: 'name',

    valueField: 'code',  

    fieldLabel: 'Country',

    store: Ext.create('HVNExtjs.store.Domains'),

    listeners: {
        change: function(combobox) {
            // get editUniversityView
            var editView = Ext.getCmp("universityEditID");
            //get "Provine Combobox"
            var coboProvine = editView.down('cbbProvine');
            //get "Provine" store
            var provineStore = coboProvine.getStore();
            provineStore.getProxy().setExtraParam("criteria", combobox.getValue());
            provineStore.load();
            //get provinse from server;
            var provines = sendRequestToServer(COUNTRY_SEARCH, combobox.getValue(), 'GET');
            //set current provine;
            for(var i=0; i<provines.length; i++){
                if(provines[i].name == PROVINE_NAME){
                    coboProvine.setValue(provines[i].code);
                    return;
                }
            }
            //if provines are empty, combobox will be cleaned;
            coboProvine.clearValue();
        }
    }
});