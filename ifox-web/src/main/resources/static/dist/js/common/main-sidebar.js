var mainSidebar = new Vue({
    el: '#main-sidebar',
    data: {
        homeActive: false,

        systemServiceActive: false,
        adminUserActive: false,
        roleActive: false,
        menuPermissionActive: false,
        resourceActive: false,

        action: ''
    },
    created: function () {
        var menu = getCookie('menu');

        if ('home' === menu) {
            this.homeActive = true;
        } else if ('adminUser' === menu) {
            this.systemServiceActive = true;
            this.adminUserActive = true;
        } else if ('role' === menu) {
            this.systemServiceActive = true;
            this.roleActive = true;
        }else if('resource' === menu){
            this.systemServiceActive = true;
            this.resourceActive = true;
        }
    },
    methods: {
        menu: function (name) {
            if ('home' === name) {
                this.action = 'web/home';
            } else if ('adminUser' === name) {
                this.action = 'web/adminUser';
            } else if ('role' === name) {
                this.action = 'web/role';
            }else if('resource' === name){
                this.action = 'web/resource';
            }
            setCookie('menu', name);
            window.location = web_service_URL + this.action;
        }
    }
});
