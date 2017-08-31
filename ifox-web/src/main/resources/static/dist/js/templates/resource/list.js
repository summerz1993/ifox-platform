new Vue({
    el: '#list-modal',
    data: {
        name: '',
        type: '',
        responseColumns: {
            'id': {
                value:'ID',
                disabled: true
            },
            'name': {
                value:'资源名',
                disabled: false
            },
            'type': {
                value:'资源类型',
                disabled: false,
                formatter: function(value, row, index){
                    if(!isEmpty(value)){
                        if(value === "PUBLIC"){
                            return "公共资源";
                        }else if(value === "ROLE"){
                            return "角色资源";
                        }else if(value === "PERSONAL"){
                            return "私人资源";
                        }
                    }
                }
            },
            'controller': {
                value:'控制器',
                disabled: false
            },
            'remark': {
                value:'备注',
                disabled: true
            }
        }
    },
    methods: {
        /**
         * 自定义查询参数
         * @bootstrap_table_params bootstrap-table的params
         */
        searchParams: function (bootstrap_table_params) {
            var params = initParams(bootstrap_table_params);

            if (!isEmpty(this.name)) params.name = this.name;
            if (!isEmpty(this.type)) params.type = this.type;

            return params;
        },
        deleteResource: function (ids, callback) {
            axios.post(resource_delete_URL, ids, ifox_table_ajax_options)
                .then(function (res) {
                    layer.msg(res.data.desc);
                    if(res.data.status === 200){
                        callback();
                    }
                })
                .catch(function (err) {
                    layer.msg(err.response.data.desc);
                });
        }
    },
    mounted: function () {
        ifox_table_delegate.searchParams = this.searchParams;
        ifox_table_delegate.delete = this.deleteResource;

        ifox_table_setting.setColumns(this.responseColumns);
        ifox_table_setting.launch('resource_table', resource_page_URL, 'post');
    }
});
