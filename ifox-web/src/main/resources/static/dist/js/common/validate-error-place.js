$.validator.addMethod("mobileZH", function(value, element) {
    var mobile = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
    return this.optional(element) || (mobile.test(value));
}, "");

$.validator.addMethod("regexPassword", function(value, element) {
    return this.optional(element) || /^(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$/.test(value);
}, "密码长度不少于8个字符,并且包含大小写字母和数字");

$.validator.addMethod("resourceUrl", function(value, element, length) {
    var resource_url = new RegExp("^\/");
    return this.optional(element) || (resource_url.test(value));
}, "");

$.validator.setDefaults({
    errorClass: "error",
    success: 'valid',
    unhighlight: function (element, errorClass, validClass) {
        $(element).removeClass(errorClass);
    },
    highlight: function (element, errorClass, validClass) {
        $(element).addClass(errorClass);
    },
    errorPlacement: function (error, element) {
        $(element).attr("data-original-title", $(error).text());

        $(element).tooltip('show');
        $(element).tooltip({
            trigger: "hover focus click"
        });
    }
});