new Vue({
    el: "#add-resource-form",
    data: {
        name: "",
        controller: "",
        type: "PUBLIC",
        remark: ""
    },
    methods: {
        validate: function () {
            return $("#add-resource-form").validate({
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
        save: function (callback) {
            if(!this.validate().form())
                return;

            var vm = this;
            axios.post(resource_save_URL, vm.$data, ifox_table_ajax_options)
                .then(function (res) {
                    layer.msg(res.data.desc);
                    if(res.data.status === 200){
                        vm.resetData();
                        callback();
                    }
                })
                .catch(function (err) {
                    serverError();
                });
        },
        resetData: function () {
            this.name = "";
            this.controller = "";
            this.type = "PUBLIC";
            this.remark = "";
        }
    },
    mounted: function () {
        ifox_table_delegate.add = this.save;
    }
});