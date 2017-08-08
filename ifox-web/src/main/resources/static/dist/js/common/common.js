var system_service_URL = 'http://localhost:8081/';
var web_service_URL = 'http://localhost:8080/';

var admin_user_page_URL = system_service_URL + 'adminUser/page';
var admin_user_save_URL = system_service_URL + 'adminUser/save';
var admin_user_delete_URL = system_service_URL + 'adminUser/delete';
var admin_user_get_URL = system_service_URL + 'adminUser/get';
var admin_user_update_URL = system_service_URL + '/adminUser/update';

var ifox_table_ajax_options = {
    'headers': {
        "Authorization": sessionStorage.token,
        'api-version': '1.0'
    }
};

// 获取URL中的参数
function getURLQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}

function isEmpty(val) {
    if (val === null || val === "null" || val === "" || val === undefined){
        return true;
    }
    return false;
}
