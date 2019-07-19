var requestManager = {

}


/**
Response Format: {
  status  : false/true,
  error   : ?,
  type    : ?,
  message : ?,
  data    : [] / {}
}
**/
//var debugMode = true;
//
//var requestManager = {
//
//  defaults: {
//    callback      : $.noop,
//    contentType   : 'application/json; charset=utf-8',
//    context       : null,
//    dataType      : 'json',
//    httpMethod    : 'POST',
//    waitBoxId     : '#app-waitbox',
//    waitBoxHeight : 100,
//    waitBoxWidth  : 350
//  },
//
//  doAjaxRequest: function (options) {
//    var opts = $.extend({}, requestManager.defaults, options);
//
//    // validate options
//
//    // TODO: queue up requests
//    $.ajax({
//      url         : opts.url,
//      type        : opts.httpMethod,
//      data        : opts.data,
//      dataType    : opts.dataType,
//      contentType : opts.contentType,
//
//      beforeSend: function (jqXHR, settings) {
//
//      },
//
//      success: function (response, textStatus, jqXHR) {
//        // do biz successfully
//        if (response.status) {
//          // show success message
//          if ($.trim(response.message).length > 0) {
//            si.msgbox.showSuccess(response.message);
//          }
//
//          // clear errors
//          if (opts.context instanceof jQuery) {
//            opts.context.clearForm();
//          }
//
//          // call callback function
//          if ($.isFunction(opts.callback)) {
//            opts.callback(response.data, textStatus, jqXHR);
//          }
//        }
//        // failed
//        else {
//          // show error message
//          if ($.trim(response.error).length > 0) {
//            si.msgbox.showError(response.error);
//          }
//
//          // clear errors
//          var $form = opts.context;
//          if ($form instanceof jQuery) {
//            $form.clearForm();
//          }
//
//          switch (response.type) {
//            case SYSTEM_AUTHENTICATION_REQUIRED:
//              // redirect to login page if unauthenticated
//              window.location = response.data;
//              break;
//
//            case UNIQUE_CONSTRAINT:
//              if ($form instanceof jQuery) {
//                // insert error message on fields
//                $form.addError(response.data, "This value already exists. Please enter a different value.");
//              }
//              break;
//
//            case MEMBERSHIP_CREATE_USER_EXCEPTION:
//              if ($form instanceof jQuery) {
//                // insert error message on fields
//                $form.addError(response.data);
//              }
//              break;
//
//            case DB_ENTITY_VALIDATION_EXCEPTION:
//								var errors = response.data, isForm = $form instanceof jQuery;
//                // TODO: show ValidationErrorSummary
//
//                // show error on fields
//                var messages = '';
//                for (var i = 0, n = errors.length; i < n; i++) {
//                  var error = errors[i];
//                  messages += error.ErrorMessage + '. ';
//                	// insert error message on fields
//                  if (isForm) {
//                    $form.addError(error);
//                  }
//                }
//                si.msgbox.showError(messages);
//              break;
//
//          	default:
//          		console.error(response.error);
//              if (response.type == SYSTEM_UNHANDLED) {
//                si.msgbox.showError(SYSTEM_UNEXPECTED_ERROR);
//              }
//              break;
//          }
//        }
//      },
//
//      error: function (jqXHR, textStatus, errorThrown) {
//        var response;
//        try {
//          response = JSON.parse(jqXHR.responseText);
//        }
//        catch (ex) { }
//
//        if (response && response.error == SYSTEM_AUTHENTICATION_REQUIRED) {
//          // redirect to login page if unauthenticated
//          var redirectUrl = response.message;
//          window.location = redirectUrl;
//        }
//        else {
//          si.msgbox.showError(SYSTEM_AJAX_CALL_ERROR);
//          console.error('AJAX call error with textStatus: "' + textStatus + '" - errorThrown: "' + errorThrown + '"');
//        }
//      },
//
//      complete: function (jqXHR, textStatus) {
//        if ($.isFunction(opts.alwaysCallback)) {
//          opts.alwaysCallback(null, textStatus, jqXHR);
//        }
//
//        //var waitBoxId = opts.waitBoxId;
//        //if ($(waitBoxId).length == 0 || opts.showWait == false) {
//        //  return;
//        //}
//        si.waitbox.hide();
//      },
//
//      converters: {
//        "text json": function (json) {
//          return JSON.parse(json, function (key, value) {
//
//            var d = new Date();
//            if (typeof value === 'string') {
//              // IsoDate: "2008-01-01T12:00:00Z"
//
//              var a = /^(\d{4})-(\d{2})-(\d{2})T(\d{2}):(\d{2}):(\d{2}(?:\.\d*)?)(Z*)$/.exec(value);
//              if (a) {
//                //return new Date(Date.UTC(+a[1], +a[2] - 1, +a[3], +a[4], +a[5], +a[6]));
//                dateTime = new Date(+a[1], +a[2] - 1, +a[3], +a[4], +a[5], +a[6]);
//                return new Date(dateTime.getTime() - DATE_OFFSET);
//              } else {
//                // JsonDate: "\/Date(...)\/"
//                a = /\/Date\((\d*)\)\//.exec(value);
//                if (a) {
//                  dateTime = new Date(+a[1]);
//                  return new Date(dateTime.getTime() - DATE_OFFSET);
//                }
//              }
//            }
//            return value;
//          });
//        }
//      },
//    });
//  }
//};
