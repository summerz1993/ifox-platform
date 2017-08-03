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
<<<<<<< HEAD:ifox-web/src/main/resources/static/dist/js/pages/adminUser/admin-user-oper.js
    TableComponent.init('tableId', 'http://localhost:8081/adminUser/page', 'post');

    $("#headPortrait").fileinput({
        language: 'zh',
        uploadUrl: "/site/image-upload",
        allowedFileExtensions: ["jpg", "png", "gif"],
        maxFileCount: 1,
        maxImageWidth: 160,
        maxImageHeight: 100,
        resizePreference: 'width',
        previewClass: 'preview-size',
        previewSettings:{
            image: {width: "50%", height: "110px"}
        },
        previewZoomSettings:{
            image: {width: "50%", height: "110px"}
        },
        resizeImage: true,
        resizeImage: true,
        showCaption: false,
        showRemove: false,
        showUpload: false,
        autoReplace: true,
        showUploadedThumbs: true,
        showBrowse: false,
        browseOnZoneClick: true
    });
=======
    TableComponent.init('admin_user_table', admin_user_page_URL, 'POST');
>>>>>>> 9493aebdba2496a2a72ff609ba6425c566f4412b:ifox-web/src/main/resources/static/dist/js/pages/admin-user/admin-user.js
});
