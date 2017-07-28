var loginApp = new Vue({
    el: '#login-app',
    data: {
        userName: 'admin',
        password: '12345678z'
    },
    methods: {
        login: function () {
            console.log('click login method, userName=' + this.userName + ' password=' + this.password);
            var url = 'http://localhost:8081/adminUser/login';
            var params = {
                "loginName": this.userName,
                "password": this.password
            };
            var header = {
                headers: {"api-version": "1.0"}
            }
            axios.post(url, params, header)
                .then(function(res){
                    if (res.data.status === 200) {
                        window.location = '/web/home?token=' + res.data.token;
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
