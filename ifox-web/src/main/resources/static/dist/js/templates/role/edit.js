function validate() {
    return $('#edit-role-form').validate({
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

function getRole(id) {
    $.ajax({
        url: role_get_URL + "/" + id,
        type: 'get',
        dataType: 'json',
        headers: {
            Accept: "*/*",
            'content-type': 'application/json',
            Authorization: sessionStorage.token,
            'api-version': '1.0'
        },
        success: function (res) {
            if (res.status == "200"){
                $("#id-edit").val(res.data.id);
                $("#buildinSystem-edit").selectpicker('val', new Object(res.data.buildinSystem).toString());
                $("#name-edit").val(res.data.name);
                $("#identifier-edit").val(res.data.identifier);
                $("#remark-edit").val(res.data.remark);
                $("#status-edit").selectpicker('val', new Object(res.data.status).toString());
            }
        },
        error: function (res) {

        }
    });
}

function editRole(callback) {
    if(!validate().form())
        return;

    var data = {
        "buildinSystem": $("#buildinSystem-edit").val(),
        "id": $("#id-edit").val(),
        "name": $("#name-edit").val(),
        "identifier": $("#identifier-edit").val(),
        "remark": $("#remark-edit").val(),
        "status": $("#status-edit").val()
    };

    $.ajax({
        url: role_update_URL,
        type: 'put',
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
    ifox_table.getDetail = getRole;
    ifox_table.edit = editRole;
});