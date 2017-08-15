function validate() {
    return $('#edit-user-form').validate({
        rules: {
            email:{
                required: false,
                email: true
            },
            mobile:{
                required: false,
                mobileZH: true
            },
            loginName:{
                required: true,
                rangelength: [4,15]
            },
            nickName:{
                required: false,
                rangelength: [2,20]
            },
            password:{
                required: true,
                password: 8
            }
        },
        messages: {
            email: "请输入一个正确的邮箱",
            mobile: "请输入正确的联系电话",
            loginName: "请输入4-15位有效的登录名",
            nickName: "请输入2-20位有效的昵称",
            password: "请输入至少8位，包含数字及字母的有效密码"
        }
    });
}

function getUser(id) {
    $.ajax({
        url: admin_user_get_URL + "/" + id,
        type: 'get',
        dataType: 'json',
        headers: {
            Accept: "*/*",
            'content-type': 'application/json',
            Authorization: sessionStorage.token,
            'api-version': '1.0'
        },
        success: function (res) {
            if (res.status === 200){
                $("#id-edit").val(res.data.id);
                $("#buildinSystem-edit").selectpicker('val', new Object(res.data.buildinSystem).toString());
                $("#email-edit").val(res.data.email);
                $("#loginName-edit").val(res.data.loginName);
                $("#mobile-edit").val(res.data.mobile);
                $("#password-edit").val(res.data.password);
                $("#nickName-edit").val(res.data.nickName);
                $("#remark-edit").val(res.data.remark);
                $("#status-edit").selectpicker('val', new Object(res.data.status).toString());
            } else {
                alert(res.desc);
            }
        },
        error: function (res) {
            alert('服务器异常');
        }
    });
}

function editUser(callback) {

    if(!validate().form())
        return;

    var data = {
        "buildinSystem": $("#buildinSystem-edit").val(),
        "email": $("#email-edit").val(),
        "headPortrait": "String",
        "id": $("#id-edit").val(),
        "password": $("#password-edit").val(),
        "loginName": $("#loginName-edit").val(),
        "mobile": $("#mobile-edit").val(),
        "nickName": $("#nickName-edit").val(),
        "remark": $("#remark-edit").val(),
        "status": $("#status-edit").val()
    };

    $.ajax({
        url: admin_user_update_URL,
        type: 'put',
        dataType: 'json',
        data: JSON.stringify(data),
        headers: {
            Accept: "*/*",
            'content-type': 'application/json',
            Authorization: sessionStorage.token,
            'api-version': '1.0'
        },
        success: function (res) {
            if (res.status === 200){
                callback();
            } else {
                layer.msg(res.desc);
            }
        },
        error: function () {
            layer.msg('服务器异常');
        }
    })
}

function initEditUserUploadFile() {
    $("#headPortrait-edit").fileinput({
        language: 'zh',
        uploadUrl: "/site/image-upload",
        allowedFileExtensions: ["jpg", "png", "gif"],
        maxFileCount: 1,
        previewClass: 'preview-size',
        showCaption: false,
        showRemove: false,
        showUpload: false,
        autoReplace: true,
        showUploadedThumbs: true,
        showBrowse: false,
        browseOnZoneClick: true
    });

    $("#edit-modal .file-preview").attr("style", "width:160px;height:230px;");
    $("#edit-modal .clickable").attr('style', 'width:120px;height:195px;');
    $("#edit-modal .file-drop-zone-title").css('padding', '45px 10px');

    $('#headPortrait-edit').on('fileloaded', function(event, file, previewId, index, reader) {
        $('.kv-file-content').css('height', '100px');
        $('.krajee-default.file-preview-frame').css('margin', '1px');
        $('.file-footer-caption').css('width', '110px');
    });

    $('#headPortrait-edit').on('fileremoved', function(event, id, index) {
        $('.file-drop-zone-title').css('padding', '45px 10px');
    });

    $('#headPortrait-edit').on('fileuploaderror', function(event, data, msg) {
        $('.text-success').remove();
        $('.file-error-message').remove();
    });
}

$(function () {
    ifox_table_delegate.getDetail = getUser;
    ifox_table_delegate.edit = editUser;
    initEditUserUploadFile();
});