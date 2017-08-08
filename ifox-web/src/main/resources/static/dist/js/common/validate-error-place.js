$.validator.addMethod("mobileZH", function(value, element) {
    var mobile = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
    return this.optional(element) || (mobile.test(value));
}, "");

$.validator.addMethod("password", function(value, element, length) {
    var pass = new RegExp("[a-zA-Z0-9]" + "{" + length + "}$");
    return this.optional(element) || (pass.test(value));
}, "");

$.validator.setDefaults({
    errorClass: "error",
    success: 'valid',
    unhighlight: function (element, errorClass, validClass) {
        $(element).tooltip('destroy').removeClass(errorClass);
    },
    highlight: function (element, errorClass, validClass) {
        $(element).tooltip('destroy').addClass(errorClass);
    },
    errorPlacement: function (error, element) {
        if ($(element).next("div").hasClass("tooltip")) {
            $(element).attr("data-original-title", $(error).text());

            $(element).tooltip('show');
            $(element).tooltip({
                trigger: "hover focus click"
            });
        } else {
            $(element).attr("title", $(error).text());

            $(element).tooltip('show');
            $(element).tooltip({
                trigger: "hover focus click"
            });
        }
    }
});