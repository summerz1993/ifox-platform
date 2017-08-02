var mainHeader = new Vue({
    el: '#main-header',
    methods: {
        logout: function () {
            console.log('logout method');

            var token = sessionStorage.token;
            var URL = webServiceURL + 'web/logout?token=' + token;
            axios.get(URL, {})
                .then(function (res) {
                    console.log(res);
                    if (res.status === 200 && res.data === 200) {
                        sessionStorage.token = '';
                        window.location = webServiceURL + 'web/login';
                    }
                })
                .catch(function (err) {
                    alert('服务器错误');
                    console.log(err);
                });
        }
    }
});
