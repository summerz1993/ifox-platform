new Vue({
    el: '#list-modal',
    data: {
        name: '',
        status: '',
        buildinSystem: '',
        responseColumns: {
            'id': {
                value:'ID',
                disabled: true
            },
            'name': {
                value:'角色名',
                disabled: false
            },
            'identifier': {
                value:'标识符',
                disabled: false
            },
            'buildinSystem': {
                value:'内置',
                disabled: false,
                formatter: function(value, row, index){
                    if(!isEmpty(value)){
                        if(value === true || value === 'true'){
                            return "是";
                        }else{
                            return "否";
                        }
                    }
                }
            },
            'remark': {
                value:'备注',
                disabled: true
            },
            'status': {
                value:'状态',
                disabled: false,
                formatter: function(value, row, index){
                    if(!isEmpty(value)){
                        if(value === "ACTIVE"){
                            return "有效";
                        }else{
                            return "无效";
                        }
                    }
                }
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

            if (!isEmpty(this.buildinSystem)) params.buildinSystem = this.buildinSystem;
            if (!isEmpty(this.status)) params.status = this.status;
            if (!isEmpty(this.name)) params.name = this.name;

            return params;
        },
        deleteRoles: function (ids, callback) {
            axios.post(role_delete_URL, ids, ifox_table_ajax_options)
                .then(function (res) {
                    layer.msg(res.data.desc);
                    if(res.data.status === 200){
                        callback();
                    }
                })
                .catch(function (err) {
                    serverError(err);
                });
        }
    },
    mounted: function () {
        ifox_table_delegate.searchParams = this.searchParams;
        ifox_table_delegate.delete = this.deleteRoles;

        ifox_table_setting.setColumns(this.responseColumns);
        ifox_table_setting.launch('role_table', role_page_URL, 'post');
    }
});
