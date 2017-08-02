var systemServiceURL = 'http://localhost:8081/';
var webServiceURL = 'http://localhost:8080/';

// 获取URL中的参数
function getURLQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}
