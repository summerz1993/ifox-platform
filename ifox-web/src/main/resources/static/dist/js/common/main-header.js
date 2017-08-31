new Vue({
    el: '#main-header',
    methods: {
        logout: function () {
            var URL = web_service_URL + 'web/logout';
            axios.get(URL, {})
                .then(function (res) {
                    if (res.status === 200 && res.data === 200) {
                        delCookie('token');
                        window.location = web_service_URL + 'web/login';
                    }
                })
                .catch(function (err) {
                    serverError(err);
                });
        },
        changePwdModal: function () {
            $('#change-pwd-modal').modal('show');
        }
    }
});

new Vue({
    el: '#change-pwd-modal',
    data: {
        originalPassword: '',
        newPassword: '',
        confirmPassword: ''
    },
    methods: {
        validate: function () {
            return $('#change-pwd-form').validate({
                rules: {
                    originalPassword: {
                        required: true
                    },
                    newPassword: {
                        required: true,
                        regexPassword: true
                    },
                    confirmPassword: {
                        required: true,
                        equalTo: "#new-password"
                    }
                },
                messages: {
                    originalPassword: "请输入原始密码"
                }
            });
        },
        confirmChange: function () {
            if (!this.validate().form())
                return;

            var vm = this;
            axios.post(admin_user_change_pwd_URL, vm.$data, ifox_table_ajax_options)
                .then(function (res) {
                    if (res.data.status === 200) {
                        layer.msg("修改成功，2秒后重新登陆");
                        $('#change-pwd-modal').modal('hide');
                        delCookie('token');
                        setTimeout(function () {
                            window.location = web_service_URL + 'web/login';
                        }, 2000);
                    }
                })
                .catch(function (err) {
                   serverError(err);
                });
        }
    }
});
