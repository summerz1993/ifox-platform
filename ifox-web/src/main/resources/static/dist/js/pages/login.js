var loginApp = new Vue({
    el: '#login-app',
    data: {
        userName: 'uuu',
        password: 'xxxx'
    },
    methods: {
        login: function () {
            console.log('click login method, userName=' + this.userName + ' password=' + this.password);
            var url = 'http://localhost:8081/adminUser/login';
            axios.post(url,
                {
                "loginName": "string",
                "password": "string"
                },
                {
                    headers: {
                        "Accept": "*/*",
                        "api-version": "1.0"
                    }
                }
            ).then(function(res){
                if (res.data.status === 200) {
                    window.location = '/web/home?token=' + res.data.token;
                }
            })
            .catch(function(err){
                console.log(err);
            });
        }
    }
});
