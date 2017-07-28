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

$(function () {
    columns = {
        'id': {
            value:'编号',
            disabled: true,
            query: false
        },
        'buildinSystem': {
            value:'内置',
            disabled: false,
            query: false,
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
            disabled: false,
            query: true
        },
        'loginName': {
            value:'登陆名',
            disabled: false,
            query: true
        },
        'mobile': {
            value:'手机号',
            disabled: false,
            query: false
        },
        'nickName': {
            value:'昵称',
            disabled: false,
            query: true
        },
        'remark': {
            value:'备注',
            disabled: true,
            query: false
        },
        'status': {
            value:'状态',
            disabled: false,
            query: false,
            formatter: function(value, row, index){
                if(value != undefined && value != null && value != 'null'){
                    if(value == "ACTIVE"){
                        return "有效";
                    }else{
                        return "无效";
                    }
                }
            }
        }
    };

    var ajaxOptions = {
        'headers': {
            "Authorization": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlSWRMaXN0IjpbXSwibG9naW5OYW1lIjoiYWRtaW4iLCJpc3MiOiJ3d3cueWVhZ2VyLnZpcCIsImV4cCI6MTUwMTIzNDUzMywiaWF0IjoxNTAxMjI4NTMzLCJ1c2VySWQiOiI4YWIyYThjNTVkNjM1ZDZiMDE1ZDYzNWU1MjIyMDAwMCIsImp0aSI6IjY2ZTQ4ZmUyNWM2MjQ0NjM5MmNiMWJjMzZhODA5N2Y1In0.74TZ9LarMgxYGl50DsRM_ECMd5lXlRkjR_EZ_tlvij0",
            'api-version': '1.0'
        }
    };
    table_oper.searchParams = searchParams;
    TableComponent.setAjaxOptions(ajaxOptions);
    TableComponent.setColumns(initColumns(getShowColumns(columns)));
    TableComponent.init('tableId', 'http://localhost:8081/adminUser/page', 'post');
});
 