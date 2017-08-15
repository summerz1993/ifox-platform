function validate() {
   return $('#add-user-form').validate({
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

function addUser(callback) {
    if(!validate().form())
        return;

    var data = {
        "buildinSystem": $("#buildinSystem-add").val(),
        "email": $("#email-add").val(),
        "headPortrait": "string",
        "loginName": $("#loginName-add").val(),
        "mobile":  $("#mobile-add").val(),
        "nickName": $("#nickName-add").val(),
        "password":  $("#password-add").val(),
        "remark":  $("#remark-add").val(),
        "status": $("#status-add").val()
    };

    $.ajax({
        url: admin_user_save_URL,
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

function initAddUserFileUpload() {
    $("#headPortrait-add").fileinput({
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

    $("#addModal .file-preview").attr("style", "width:160px;height:230px;");
    $("#addModal .clickable").attr('style', 'width:120px;height:195px;');
    $("#addModal .file-drop-zone-title").css('padding', '45px 10px');

    $('#headPortrait-add').on('fileloaded', function(event, file, previewId, index, reader) {
        $('.kv-file-content').css('height', '100px');
        $('.krajee-default.file-preview-frame').css('margin', '1px');
        $('.file-footer-caption').css('width', '110px');
    });

    $('#headPortrait-add').on('fileremoved', function(event, id, index) {
        $('.file-drop-zone-title').css('padding', '45px 10px');
    });

    $('#headPortrait-add').on('fileuploaderror', function(event, data, msg) {
        $('.text-success').remove();
        $('.file-error-message').remove();
    });
}

$(function () {
    ifox_table_delegate.add = addUser;
    initAddUserFileUpload();
});