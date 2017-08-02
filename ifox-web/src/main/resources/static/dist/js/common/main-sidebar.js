var mainSidebar = new Vue({
    el: '#main-sidebar',
    data: {
        homeActive: false,

        systemServiceActive: false,
        adminUserActive: false,
        roleActive: false,
        menuPermissionActive: false,
        resourceActive: false,

        token: '',
        action: ''
    },
    created: function () {
        this.token = sessionStorage.token;
        var menu = getURLQueryString('menu');
        console.log('menu : ' + menu);

        if ('home' === menu) {
            this.homeActive = true;
        } else if ('adminUser' === menu) {
            this.systemServiceActive = true;
            this.adminUserActive = true;
        }
    },
    methods: {
        menu: function (name) {
            if ('home' === name) {
                this.action = 'web/home';
            } else if ('adminUser' === name) {
                this.action = 'web/adminUser';
            }
            window.location = this.generateURL(name);
        },
        generateURL: function (menuName) {
            return web_service_URL + this.action + '?menu=' + menuName + '&token=' + this.token;
        }
    }
});
