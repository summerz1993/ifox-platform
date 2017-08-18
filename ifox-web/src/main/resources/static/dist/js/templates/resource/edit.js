new Vue({
    el: "#edit-resource-form",
    data: {
        id: "",
        name: "",
        controller: "",
        type: "PUBLIC",
        remark: ""
    },
    methods: {
        validate: function() {
            return $("#edit-resource-form").validate({
                rules: {
                    name: {
                        required: true,
                        rangelength: [4,15]
                    },
                    controller: {
                        required: true,
                        rangelength: [4,30]
                    }
                },
                messages: {
                    name: "请输入4-15位有效的资源名称",
                    controller: "请输入4-30位有效的控制器名称",
                }
            });
        },
        detail: function (id) {
            var url = resource_get_URL + "/" + id;

            var vm = this;
            axios.get(url, ifox_table_ajax_options)
                .then(function (res) {
                    if(res.data.status === 200){
                        var res_data = res.data.data;
                        vm.id = res_data.id;
                        vm.name = res_data.name;
                        vm.controller = res_data.controller;
                        vm.type = res_data.type;
                        vm.remark = res_data.remark;
                    }else{
                        layer.msg(res.data.desc);
                    }
                })
                .catch(function () {
                    serverError();
                });
        },
        update: function (callback) {
            if(!this.validate().form())
                return;

            var vm = this;
            axios.put(resource_update_URL, vm.$data, ifox_table_ajax_options)
                .then(function (res) {
                    layer.msg(res.data.desc);
                    if (res.data.status === 200){
                        vm.resetData();
                        callback();
                    }
                })
                .catch(function () {
                    serverError();
                });
        },
        resetData: function () {
            this.id = "";
            this.name = "";
            this.controller = "";
            this.type = "PUBLIC";
            this.remark = "";
        }
    },
    mounted: function () {
        ifox_table_delegate.getDetail = this.detail;
        ifox_table_delegate.edit = this.update;
    }
});