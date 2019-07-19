Ext.define('HVNExtjs.view.university.Provines', {
    
    extend: 'Ext.form.ComboBox',

    alias: 'widget.cbbProvine',

    displayField: 'name',

    valueField: 'code',  

    fieldLabel: 'Provine',    

    store: Ext.create('HVNExtjs.store.Provines'),
});