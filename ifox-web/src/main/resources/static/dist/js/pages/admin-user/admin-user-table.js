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

$(function () {
    ifox_table.searchParams = searchParams;

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

    TableComponent.setAjaxOptions(ifox_table_ajax_options);
    TableComponent.setColumns(initColumns(getShowColumns(columns)));
    TableComponent.init('admin_user_table', admin_user_page_URL, 'post');
});
