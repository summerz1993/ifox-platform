new Vue({
    el: '#edit-role-form',
    data: {
        id: '',
        name: '',
        identifier: '',
        buildinSystem: 'true',
        status: 'ACTIVE',
        remark: ""
    },
    methods: {
        validate: function () {
            return $('#edit-role-form').validate({
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
        detail: function (id) {
            var url = role_get_URL + "/" + id;

            var vm = this;
            axios.get(url, ifox_table_ajax_options)
                .then(function (res) {
                    if(res.data.status === 200){
                        var res_data = res.data.data;
                        vm.id = res_data.id;
                        vm.name = res_data.name;
                        vm.identifier = res_data.identifier;
                        vm.buildinSystem = res_data.buildinSystem;
                        vm.status = res_data.status;
                        vm.remark = res_data.remark;
                    }else{
                        layer.msg(res.data.desc);
                    }
                })
                .catch(function (err) {
                    layer.msg(err.response.data.desc);
                });
        },
        update: function (callback) {
            if(!this.validate().form())
                return;

            var vm = this;
            axios.put(role_update_URL, vm.$data, ifox_table_ajax_options)
                .then(function (res) {
                    layer.msg(res.data.desc);
                    if (res.data.status === 200){
                        vm.resetData();
                        callback();
                    }
                })
                .catch(function (err) {
                    layer.msg(err.response.data.desc);
                });
        },
        resetData: function () {
            this.id = '';
            this.name = '';
            this.identifier = '';
            this.buildinSystem = "true";
            this.status = "ACTIVE";
            this.remark = "";
        }
    },
    mounted: function () {
        ifox_table_delegate.getDetail = this.detail;
        ifox_table_delegate.edit = this.update;
    }
});
