new Vue({
    el: '#list-modal',
    data: {
        loginName: '',
        status: '',
        buildinSystem: '',
        columns: {
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
            var simpleOrderList = [];
            if (!isEmpty(bootstrap_table_params.sort && !isEmpty(bootstrap_table_params.order))){
                var simpleOrder = {};
                simpleOrder.property = bootstrap_table_params.sort;
                simpleOrder.orderMode = bootstrap_table_params.order.toUpperCase();
                simpleOrderList.push(simpleOrder);
            }

            var search = {
                "pageNo": bootstrap_table_params.offset/bootstrap_table_params.limit + 1,
                "pageSize": bootstrap_table_params.limit
            };

            if (!isEmpty(this.buildinSystem)) search.buildinSystem = this.buildinSystem;
            if (!isEmpty(this.status)) search.status = this.status;
            if (!isEmpty(this.loginName)) search.loginName = this.loginName;
            if (simpleOrderList.length > 0) search.simpleOrderList = simpleOrderList;

            return search;
        }
    },
    created: function () {
        ifox_table.searchParams = this.searchParams;

        TableComponent.setAjaxOptions(ifox_table_ajax_options);
        TableComponent.setColumns(initColumns(getShowColumns(this.columns), this.columns));
        TableComponent.init('admin_user_table', admin_user_page_URL, 'post');

    }
});