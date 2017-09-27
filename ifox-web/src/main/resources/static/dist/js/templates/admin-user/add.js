$('#add-modal').on('show.bs.modal', function (e) {
    admin_user_add_vue.initRoleList();
});

$('#add-modal').on('hide.bs.modal', function (e) {
    admin_user_add_vue.resetData();
    // $('#headPortrait-add').fileinput('destroy');
});

var admin_user_add_vue = new Vue({
    el: "#add-user-form",
    data: {
        loginName: "",
        headPortrait: "",
        nickName: "",
        password: "",
        mobile: "",
        email: "",
        buildinSystem: "true",
        status: "ACTIVE",
        remark: "",
        roleList: [],
        checkedRole: []
    },
    methods: {
        initAddUserFileUpload: function () {
            var vm = this;
            $("#headPortrait-add").fileinput({
                language: 'zh',
                allowedFileTypes: ["image"],
                allowedFileExtensions: ["jpg", "png", "gif", "bmp", "jpeg", "psd", "svg"],
                maxFileSize: 50,
                maxFileCount: 1,
                previewClass: 'preview-size',
                showCaption: false,
                showRemove: false,
                showUpload: false,
                autoReplace: true,
                showUploadedThumbs: true,
                showBrowse: false,
                browseOnZoneClick: true,
                uploadUrl: file_service_URL + "file/upload?serviceName=system-service&fileType=PICTURE",
                ajaxSettings: {
                    'headers': {
                        "Authorization": getCookie('token'),
                        'api-version': '1.0'
                    }
                }
            });

            $("#add-modal .file-preview").attr("style", "width:220px; height:220px;");
            $("#add-modal .clickable").attr('style', 'width:185px; height:185px;');
            $("#add-modal .file-drop-zone-title").css('padding', '45px 10px');

            $('#headPortrait-add').on('fileloaded', function(event, file, previewId, index, reader) {
                $('.kv-file-content').css('height', '100px');
                $('.krajee-default.file-preview-frame').css('margin', '1px');
                $('.file-footer-caption').css('width', '110px');
            });

            $('#headPortrait-add').on('fileremoved', function(event, id, index) {
                $('.file-drop-zone-title').css('padding', '45px 10px');
            });

            $('#headPortrait-add').on('fileuploaderror', function(event, data, msg) {
                console.log('file upload error, data=' + data + ' msg=' + msg);
                $('.text-success').remove();
                $('.file-error-message').remove();
                try {
                    layer.msg(JSON.parse(msg).desc);
                } catch(err) {
                    layer.msg(msg);
                }
            });

            $('#headPortrait-add').on('fileuploaded', function(event, data, previewId, index) {
                if (data.response.status === 200) {
                    vm.headPortrait = data.response.desc;
                } else {
                    layer.msg(data.response.desc);
                }
                console.log('File uploaded triggered, response.status=' + data.response.status + " response.desc=" + data.response.desc);
            });

        },
        initRoleList: function () {
            var vm = this;
            var params = {
                'status': 'ACTIVE'
            };
            axios.post(role_list_URL, params, ifox_table_ajax_options)
                .then(function (res) {
                    if(res.data.status === 200){
                        vm.roleList = res.data.data;
                        /*setTimeout(function () {
                            $('.role-list input').iCheck({
                                checkboxClass: 'icheckbox_square-blue',
                                radioClass: 'iradio_square-blue',
                                increaseArea: '20%' // optional
                            });
                        }, 500);*/
                    }else{
                        layer.msg(res.data.desc);
                    }
                })
                .catch(function (err) {
                    serverError(err);
                });
        },
        validate: function () {
            return $('#add-user-form').validate({
                rules: {
                    email:{
                        required: false,
                        email: true
                    },
                    mobile:{
                        required: false,
                        mobileZH: true
                    },
                    loginName:{
                        required: true,
                        rangelength: [4,15]
                    },
                    nickName:{
                        required: false,
                        rangelength: [2,20]
                    },
                    password:{
                        required: true,
                        regexPassword: true
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
                    email: "请输入一个正确的邮箱",
                    mobile: "请输入正确的联系电话",
                    loginName: "请输入4-15位有效的登录名",
                    nickName: "请输入2-20位有效的昵称",
                    remark: "备注最多500字"
                }
            });
        },
        save: function (callback) {
            if(!this.validate().form())
                return;

            var vm = this;
            axios.post(admin_user_save_URL, vm.$data, ifox_table_ajax_options)
                .then(function (res) {
                    layer.msg(res.data.desc);
                    if(res.data.status === 200){
                        callback();
                    }
                })
                .catch(function (err) {
                    serverError(err);
                });
        },
        resetData: function () {
            this.loginName = '';
            this.headPortrait = '';
            this.nickName = '';
            this.password = '';
            this.mobile = '';
            this.email = '';
            this.buildinSystem = "true";
            this.status = "ACTIVE";
            this.remark = "";
            this.roleList = [];
            this.checkedRole = [];
            $('#headPortrait-add').fileinput('clear');
            // $('.role-list input').iCheck('destroy');
        }
    },
    mounted: function () {
        ifox_table_delegate.add = this.save;
        this.initAddUserFileUpload();
        // this.initRoleList();
    }
});