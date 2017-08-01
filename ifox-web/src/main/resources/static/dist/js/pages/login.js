var loginApp = new Vue({
    el: '#login-app',
    data: {
        userName: 'admin',
        password: '12345678z'
    },
    methods: {
        login: function () {
            console.log('click login method, userName=' + this.userName + ' password=' + this.password);
            var loginURL = systemServiceURL + 'adminUser/login';
            var loginParams = {
                "loginName": this.userName,
                "password": this.password
            };
            var loginConfig = {
                headers: {"api-version": "1.0"}
            };
            axios.post(loginURL, loginParams, loginConfig)
                .then(function(res){
                    if (res.data.status === 200) {
                        var token = res.data.token;
                        sessionStorage.token = token;

                        var homeURL = webServiceURL + 'web/home';
                        var tokenParam = {
                            'token': token
                        };
                        var homeConfig = {
                            headers: {
                                'Content-Type': 'application/x-www-form-urlencoded'
                            },
                            transformRequest: [function (data) {
                                // Do whatever you want to transform the data
                                var ret = '';
                                for (var it in data) {
                                    ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&';
                                }
                                return ret;
                            }]
                        };
                        axios.post(homeURL, tokenParam, homeConfig)
                            .then(function (res) {
                                console.log(res);
                            })
                            .catch(function (err) {
                                console.log(err);
                            });

                        // window.location = homeURL + '?token=' + token;
                    } else {
                        alert(res.data.desc);
                    }
                })
                .catch(function(err){
                    alert('服务器错误');
                    console.log(err);
                });
        }
    }
});
