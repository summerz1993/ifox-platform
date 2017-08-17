var mainHeader = new Vue({
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
                    serverError();
                    console.log(err);
                });
        }
    }
});
