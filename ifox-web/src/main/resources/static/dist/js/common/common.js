var system_service_URL = 'http://localhost:8081/';
var web_service_URL = 'http://localhost:8080/';

<!-- admin user -->
var admin_user_save_URL = system_service_URL + 'adminUser/save';
var admin_user_delete_URL = system_service_URL + 'adminUser/delete';
var admin_user_update_URL = system_service_URL + '/adminUser/update';
var admin_user_get_URL = system_service_URL + 'adminUser/get';
var admin_user_page_URL = system_service_URL + 'adminUser/page';

<!-- role -->
var role_save_URL = system_service_URL + 'role/save';
var role_delete_URL = system_service_URL + 'role/delete';
var role_update_URL = system_service_URL + '/role/update';
var role_get_URL = system_service_URL + 'role/get';
var role_page_URL = system_service_URL + 'role/page';

var ifox_table_ajax_options = {
    'headers': {
        "Authorization": getCookie('token'),
        'api-version': '1.0'
    }
};

// 获取URL中的参数
function getURLQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r !== null) return unescape(r[2]); return null;
}

//是否为空
function isEmpty(val) {
    return val === null || val === "null" || val === "" || val === undefined;
}

// 根据bootstrap_table参数，初始化排序属性
function initSimpleOrderList(bootstrap_table_params) {
    var simpleOrderList = [];
    if (!isEmpty(bootstrap_table_params.sort && !isEmpty(bootstrap_table_params.order))){
        var simpleOrder = {};
        simpleOrder.property = bootstrap_table_params.sort;
        simpleOrder.orderMode = bootstrap_table_params.order.toUpperCase();
        simpleOrderList.push(simpleOrder);
    }
    return simpleOrderList;
}

// 根据bootstrap_table参数，初始化查询参数
function initParams(bootstrap_table_params) {
    var params = {
        "pageNo": bootstrap_table_params.offset/bootstrap_table_params.limit + 1,
        "pageSize": bootstrap_table_params.limit
    };
    var simpleOrderList = initSimpleOrderList(bootstrap_table_params);
    if (simpleOrderList.length > 0) params.simpleOrderList = simpleOrderList;
    return params;
}

//写cookies
function setCookie(name, value) {
    var days = 1;
    var second = days*24*60*60;
    document.cookie = name + "="+ escape (value) + ";max-age=" + second;
}

//读取cookies
function getCookie(name) {
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
    if(arr=document.cookie.match(reg))
        return unescape(arr[2]);
    else
        return null;
}

//删除cookies
function delCookie(name) {
    var value=getCookie(name);
    if(value !== null)
        document.cookie= name + "=" + value + ";max-age=-2";
}

//服务器异常提示信息
function serverError() {
    layer.msg('服务器异常');
}
