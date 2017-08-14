function validate() {
   return $('#add-role-form').validate({
        rules: {
            name:{
                required: true,
                rangelength: [2,10]
            }
        },
        messages: {
            name: "请输入4-10位有效的角色名"
        }
   });
}

function addRole(callback) {
    if(!validate().form())
        return;

    var data = {
        "buildinSystem": $("#buildinSystem-add").val(),
        "identifier": $("#identifier-add").val(),
        "name": $("#name-add").val(),
        "remark":  $("#remark-add").val(),
        "status": $("#status-add").val()
    };

    $.ajax({
        url: role_save_URL,
        type: 'post',
        dataType: 'json',
        data: JSON.stringify(data),
        headers: {
            Accept: "*/*",
            'content-type': 'application/json',
            Authorization: sessionStorage.token,
            'api-version': '1.0'
        },
        success: function () {
            callback();
        },
        error: function () {

        }
    })
}

$(function () {
    ifox_table.add = addRole;
});