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
            axios.post()
                .then(function (res) {

                })
                .catch(function (err) {
                   serverError(err);
                });
        }
    }
});
