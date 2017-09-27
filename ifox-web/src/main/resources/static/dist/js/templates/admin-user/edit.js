$('#edit-modal').on('show.bs.modal', function (e) {
    admin_user_edit_vue.initRoleList();
});

$('#edit-modal').on('hide.bs.modal', function (e) {
    admin_user_edit_vue.resetData();
    // $('#headPortrait-add').fileinput('destroy');
});

var admin_user_edit_vue = new Vue({
    el: "#edit-user-form",
    data: {
        id: "",
        loginName: "",
        headPortrait: "",
        nickName: "",
        mobile: "",
        email: "",
        buildinSystem: "true",
        status: "ACTIVE",
        remark: "",
        roleList: [],
        checkedRole: []
    },
    methods: {
        initEditUserUploadFile: function() {
            var vm = this;
            var initImageURL = encodeURI(file_service_URL + "file/get?fileType=PICTURE&path=" + vm.headPortrait);
            $("#headPortrait-edit").fileinput({
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
                // initialPreview: [
                //     "<img src='" + initImageURL + "' class='file-preview-image' alt='Desert' title='Desert' style='width:50%; height:50%;'>"
                // ],
                uploadUrl: file_service_URL + "file/upload?serviceName=system-service&fileType=PICTURE",
                ajaxSettings: {
                    'headers': {
                        "Authorization": getCookie('token'),
                        'api-version': '1.0'
                    }
                }
            });

            $("#edit-modal .file-preview").attr("style", "width:220px; height:220px;");
            $("#edit-modal .clickable").attr('style', 'width:185px; height:185px;');
            $("#edit-modal .file-drop-zone-title").css('padding', '45px 10px');

            $('#headPortrait-edit').on('fileloaded', function(event, file, previewId, index, reader) {
                $('.kv-file-content').css('height', '100px');
                $('.krajee-default.file-preview-frame').css('margin', '1px');
                $('.file-footer-caption').css('width', '110px');
            });

            $('#headPortrait-edit').on('fileremoved', function(event, id, index) {
                $('.file-drop-zone-title').css('padding', '45px 10px');
            });

            $('#headPortrait-edit').on('fileuploaderror', function(event, data, msg) {
                console.log('file upload error, data=' + data + ' msg=' + msg);
                $('.text-success').remove();
                $('.file-error-message').remove();
                try {
                    layer.msg(JSON.parse(msg).desc);
                } catch(err) {
                    layer.msg(msg);
                }
            });

            $('#headPortrait-edit').on('fileuploaded', function(event, data, previewId, index) {
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
                    }else{
                        layer.msg(res.data.desc);
                    }
                })
                .catch(function (err) {
                    serverError(err);
                });
        },
        initCheckedRole: function () {
            var vm = this;
            var url = system_service_URL + 'adminUser/' + vm.id + "/role";
            axios.get(url, ifox_table_ajax_options)
                .then(function (res) {
                    if(res.data.status === 200){
                        var roleList = res.data.data;
                        for (var i = 0; i < roleList.length; i ++) {
                            vm.checkedRole.push(roleList[i].id);
                        }
                    }else{
                        layer.msg(res.data.desc);
                    }
                })
                .catch(function (err) {
                    serverError(err);
                });
        },
        validate: function() {
            return $('#edit-user-form').validate({
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
                    buildinSystem: {
                        required: true
                    },
                    status: {
                        required: true
                    },
                    nickName:{
                        required: false,
                        rangelength: [2,20]
                    }
                },
                messages: {
                    email: "请输入一个正确的邮箱",
                    mobile: "请输入正确的联系电话",
                    loginName: "请输入4-15位有效的登录名",
                    nickName: "请输入2-20位有效的昵称"
                }
            });
        },
        detail: function (id) {
            var url = admin_user_get_URL + "/" + id;

            var vm = this;
            axios.get(url, ifox_table_ajax_options)
                .then(function (res) {
                    if(res.data.status === 200){
                        var res_data = res.data.data;
                        vm.id = res_data.id;
                        vm.loginName = res_data.loginName;
                        vm.headPortrait = res_data.headPortrait;
                        vm.nickName = res_data.nickName;
                        vm.mobile = res_data.mobile;
                        vm.email = res_data.email;
                        vm.buildinSystem = res_data.buildinSystem;
                        vm.status = res_data.status;
                        vm.remark = res_data.remark;

                        vm.initCheckedRole();
                    }else{
                        layer.msg(res.data.desc);
                    }
                })
                .catch(function (err) {
                    serverError(err);
                });
        },
        update: function (callback) {
            if(!this.validate().form())
                return;

            var vm = this;
            axios.put(admin_user_update_URL, vm.$data, ifox_table_ajax_options)
                .then(function (res) {
                    layer.msg(res.data.desc);
                    if (res.data.status === 200){
                        callback();
                    }
                })
                .catch(function (err) {
                    serverError(err);
                });
        },
        resetData: function () {
            this.id = '';
            this.loginName = '';
            this.headPortrait = '';
            this.nickName = '';
            this.mobile = '';
            this.email = '';
            this.buildinSystem = "true";
            this.status = "ACTIVE";
            this.remark = "";
            this.roleList = [];
            this.checkedRole = [];
            $('#headPortrait-edit').fileinput('clear');
        }
    },
    mounted: function () {
        ifox_table_delegate.getDetail = this.detail;
        ifox_table_delegate.edit = this.update;
        this.initEditUserUploadFile();
    }
});