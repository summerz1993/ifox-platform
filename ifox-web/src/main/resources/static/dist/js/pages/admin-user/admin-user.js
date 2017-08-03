/**
 *	user operate
 * 	用户操作
 */

/**
 * 自定义查询参数
 * @param params
 * @returns {{buildinSystem: string, loginName: (*|jQuery), pageNo: number, pageSize: *, status: string}}
 */
function searchParams(params) {
    var temp = {
        "buildinSystem": "",
        "loginName": $("#loginName").val(),
        "pageNo": params.offset/params.limit + 1,
        "pageSize": params.limit,
        "status": "ACTIVE"
    }
    return temp;
}

function addUser() {
    var data = {
        "buildinSystem": true,
        "email": "string",
        "headPortrait": "string",
        "loginName": "test23",
        "mobile": "string",
        "nickName": "test23",
        "password": "12345678z",
        "remark": "string",
        "status": "ACTIVE"
    }

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

        },
        error: function () {

        }
    })
}

/**
 * 删除用户
 * @param ids
 */
function deleteUsers(ids) {
    $.ajax({
        url: admin_user_delete_URL + ids,
        type: 'DELETE',
        dataType: 'json',
        withCredentials:true,
        headers: {
            'content-type': 'application/json',
            Authorization: sessionStorage.token,
            'api-version': '1.0'
        },
        success: function () {
            
        },
        error: function () {
            
        }
    });
}

$(function () {
    columns = {
        'id': {
            value:'编号',
            disabled: true
        },
        'buildinSystem': {
            value:'内置',
            disabled: false,
            formatter: function(value, row, index){
                if(value != undefined && value != null && value != 'null'){
                    if(value == true || value == 'true'){
                        return "是";
                    }else{
                        return "否";
                    }
                }
            }
        },
        'email': {
            value:'邮箱',
            disabled: false
        },
        'loginName': {
            value:'登陆名',
            disabled: false
        },
        'mobile': {
            value:'手机号',
            disabled: false
        },
        'nickName': {
            value:'昵称',
            disabled: false
        },
        'remark': {
            value:'备注',
            disabled: true
        },
        'status': {
            value:'状态',
            disabled: false,
            formatter: function(value, row, index){
                if(value !== undefined && value !== null && value !== 'null'){
                    if(value === "ACTIVE"){
                        return "有效";
                    }else{
                        return "无效";
                    }
                }
            }
        }
    };

    ifox_table.searchParams = searchParams;
    ifox_table.delete = deleteUsers;
    ifox_table.add = addUser;

    TableComponent.setAjaxOptions(ifox_table_ajax_options);
    TableComponent.setColumns(initColumns(getShowColumns(columns)));
    TableComponent.init('admin_user_table', admin_user_page_URL, 'post');

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

    $("#editModal .file-preview").attr("style", "width:160px;height:230px;");
    $("#editModal .clickable").attr('style', 'width:120px;height:195px;');
    $("#editModal .file-drop-zone-title").css('padding', '45px 10px');

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
});
