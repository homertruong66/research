var defaultUrl = 'http://yds.truonglehoang.club/v1/users/';

var UserController = {

    create: function () {
        var $scope = $('#user-edit');
        $scope.show();
        $('input', $scope).val('');
        $('input', $scope).attr('disabled', false);
    },

    del: function (id) {
        if (!confirm('Are you sure you want to delete this User?')) {
            return;
        }

        var me = this;
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfHttpHeader = {};
         csrfHttpHeader[csrfHeader] = csrfToken;
        $.ajax({
            url: defaultUrl + id,
            type: 'DELETE',
            headers: csrfHttpHeader,
            success: function(result){
                me.get();
                $('#user-edit').hide();
            },
            error: function(err) {
                alert(err.responseText);
            }
        });
    },

    get: function () {
        function toString (item) {
            return item.name + ' (' + item.roles + ') - age: ' + item.age + ' - ' + item.email;
        };

        $.ajax({
            url: defaultUrl + 'search?pageSize=20&sortName=name',
            success: function(result){
                $('#ulist').empty();
                result.forEach(function(item, index) {
                    $('#ulist').append(
                        '<li>' +
                            toString(item) +
                            ' <button onclick=\'UserController.update("' + item.id + '")\'>Update</button> ' +
                            ' <button onclick=\'UserController.del("' + item.id + '")\'>Delete</button> '
                        + '</li>');
                });
            },
            error: function(err) {
                alert(err.responseText);
            }
        });
    },

    save: function () {
        var me = this;
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfHttpHeader = {};
        csrfHttpHeader[csrfHeader] = csrfToken;

        var $scope = $('#user-edit');
        var id = $('[name=id]', $scope).val();
        var params = 'name=' + $('[name=name]', $scope).val() +
                     '&age=' + $('[name=age]', $scope).val() +
                     '&roles=ROLE_USER';
        if (!id) {  // create
            params += '&email=' + $('[name=email]', $scope).val() +
                      '&password=' + $('[name=password]', $scope).val();
            $.ajax({
                url: defaultUrl + '?' + params,
                type: 'POST',
                headers: csrfHttpHeader,
                success: function(result){
                    me.get();
                    $('#user-edit').hide();
                    $('input', $scope).val('');
                },
                error: function(err) {
                    alert(err.responseText);
                }
            });
        }
        else {      // update
            $.ajax({
                url: defaultUrl + id + "?" + params,
                type: 'PUT',
                headers: csrfHttpHeader,
                success: function(result){
                    me.get();
                    $('#user-edit').hide();
                },
                error: function(err) {
                    alert(err.responseText);
                }
            });
        }
    },

    update: function (id) {
        $.ajax({
            url: defaultUrl + id,
            type: 'GET',
            success: function(result){
                var $scope = $('#user-edit');
                $scope.show();
                $('[name=id]', $scope).val(result.id);
                $('[name=email]', $scope).val(result.email);
                $('[name=name]', $scope).val(result.name);
                $('[name=password]', $scope).val(result.password);
                $('[name=age]', $scope).val(result.age);

                $('[name=email]', $scope).attr('disabled', true);
                $('[name=password]', $scope).attr('disabled', true);
            },
            error: function(err) {
                alert(err.responseText);
            }
        });
    }
}
