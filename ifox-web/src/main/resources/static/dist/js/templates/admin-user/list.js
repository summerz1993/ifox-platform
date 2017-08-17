new Vue({
    el: '#list-modal',
    data: {
        loginName: '',
        status: '',
        buildinSystem: '',
        responseColumns: {
            'id': {
                value:'ID',
                disabled: true
            },
            'loginName': {
                value:'登陆名',
                disabled: false
            },
            'nickName': {
                value:'昵称',
                disabled: false
            },
            'mobile': {
                value:'手机号',
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
            'email': {
                value:'邮箱',
                disabled: true
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
            if (!isEmpty(this.loginName)) params.loginName = this.loginName;

            return params;
        },
        deleteAdminUsers: function (ids, callback) {
            var url = admin_user_delete_URL + "/" + ids;
            axios.delete(url, ifox_table_ajax_options)
                .then(function (res) {
                    layer.msg(res.data.desc);
                    if(res.data.status === 200){
                        callback();
                    }
                })
                .catch(function (err) {
                    serverError();
                });
        }
    },
    mounted: function () {
        ifox_table_delegate.searchParams = this.searchParams;
        ifox_table_delegate.delete = this.deleteAdminUsers;

        ifox_table_setting.setColumns(this.responseColumns);
        ifox_table_setting.launch('admin_user_table', admin_user_page_URL, 'post');
    }
});
