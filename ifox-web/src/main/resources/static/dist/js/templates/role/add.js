new Vue({
    el: '#add-role-form',
    data: {
        name: '',
        identifier: '',
        buildinSystem: 'true',
        status: 'ACTIVE',
        remark: ""
    },
    methods: {
        validate: function () {
            return $('#add-role-form').validate({
                rules: {
                    name:{
                        required: true,
                        rangelength: [2,10]
                    },
                    identifier: {
                        required: true
                    },
                    buildinSystem: {
                        required: true
                    },
                    status: {
                        required: true
                    },
                    remark: {
                        required: false,
                        rangelength: [0, 500]
                    }
                },
                messages: {
                    name: "请输入2-10位有效的角色名",
                    remark: "备注最多500字"
                }
            });
        },
        save: function (callback) {
            if(!this.validate().form())
                return;

            var vm = this;
            axios.post(role_save_URL, vm.$data, ifox_table_ajax_options)
                .then(function (res) {
                    layer.msg(res.data.desc);
                    if(res.data.status === 200){
                        vm.resetData();
                        callback();
                    }
                })
                .catch(function (err) {
                    layer.msg(err.response.data.desc);
                });
        },
        resetData: function () {
            this.name = '';
            this.identifier = '';
            this.buildinSystem = "true";
            this.status = "ACTIVE";
            this.remark = "";
        }
    },
    mounted: function () {
        ifox_table_delegate.add = this.save;
    }
});
