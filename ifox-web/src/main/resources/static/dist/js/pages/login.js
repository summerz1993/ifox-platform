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
            var homeURL = webServiceURL + 'web/home';
            var loginParams = {
                "loginName": this.userName,
                "password": this.password
            };
            var header = {
                headers: {"api-version": "1.0"}
            };
            axios.post(loginURL, loginParams, header)
                .then(function(res){
                    if (res.data.status === 200) {
                        var token = res.data.token;
                        sessionStorage.token = token;
                        window.location = homeURL + '?token=' + token;
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
