var userEditModel = {

  // Properties
  id		: -1,
  email		: '',
  name		: '',
  password	: '',

  // Methods
  create: function () {
    return $.extend({}, this);
  },

  update: function (item) {
    if (item) {
        $.extend(this, item);
	}
  },

  validate: function (errors) {
    
    return true;
  }
};
