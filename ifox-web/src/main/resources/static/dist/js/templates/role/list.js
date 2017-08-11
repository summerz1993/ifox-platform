/**
 * 自定义查询参数
 * @param params
 * @returns {{buildinSystem: string, loginName: (*|jQuery), pageNo: number, pageSize: *, status: string}}
 */
function searchParams(params) {
    var simpleOrderList = new Array();
    if (params.sort != null && params.sort != "null" && params.sort != undefined && params.sort != ""){
        var simpleOrder = {};
        simpleOrder.property = params.sort;
        simpleOrder.orderMode = params.order.toUpperCase();
        simpleOrderList.push(simpleOrder);
    }

    var temp = {
        "pageNo": params.offset/params.limit + 1,
        "pageSize": params.limit
    };

    if (!isEmpty($("#buildinSystem").val())){
        temp.buildinSystem = $("#buildinSystem").val();
    }

    if(!isEmpty($("#name").val())){
        temp.name = $("#name").val();
    }

    if(!isEmpty($("#status").val())){
        temp.status = $("#status").val();
    }

    if (simpleOrderList.length > 0){
        temp.simpleOrderList = simpleOrderList;
    }

    return temp;
}

$(function () {
    ifox_table.searchParams = searchParams;

    columns = {
        'id': {
            value:'ID',
            disabled: true
        },
        'name': {
            value:'角色名称',
            disabled: false
        },
        'identifier': {
            value:'角色标识符',
            disabled: false
        },
        'buildinSystem': {
            value:'内置',
            disabled: false,
            formatter: function(value, row, index){
                if(!isEmpty(value)){
                    if(value == true || value == 'true'){
                        return "是";
                    }else{
                        return "否";
                    }
                }
            }
        },
        'remark': {
            value:'备注',
            disabled: true
        },
        'status': {
            value:'状态',
            disabled: false,
            formatter: function(value, row, index){
                if(!isEmpty(value)){
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
    TableComponent.init('role_table', role_page_URL, 'post');
});
