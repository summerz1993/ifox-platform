var loginApp = new Vue({
    el: '#login-app',
    data: {
        userName: '',
        password: ''
    },
    methods: {
        login: function () {
            var loading = layer.load(1, {
                shade: [0.1,'#fff'] //0.1透明度的白色背景
            });
            var loginURL = system_service_URL + 'adminUser/login';
            var loginParams = {
                "loginName": this.userName,
                "password": this.password
            };
            var loginConfig = {
                headers: {"api-version": "1.0"}
            };
            axios.post(loginURL, loginParams, loginConfig)
                .then(function(res){
                    layer.close(loading);
                    if (res.data.status === 200) {
                        var token = res.data.token;
                        var homeURL = web_service_URL + 'web/home';
                        setCookie('menu', 'home');
                        setCookie('token', token);
                        window.location = homeURL;
                    } else {
                        layer.msg(res.data.desc);
                    }
                })
                .catch(function(err){
                    layer.close(loading);
                    serverError(err);
                });
        }
    }
});
