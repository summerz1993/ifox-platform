var loginApp = new Vue({
    el: '#login-app',
    data: {
        userName: '',
        password: ''
    },
    methods: {
        login: function () {

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
                        window.location = homeURL + '?menu=home&token=' + token;
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
