new Vue({
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
        remark: ""
    },
    methods: {
        initEditUserUploadFile: function() {
            $("#headPortrait-edit").fileinput({
                language: 'zh',
                uploadUrl: "/site/image-upload",
                allowedFileExtensions: ["jpg", "png", "gif"],
                maxFileCount: 1,
                previewClass: 'preview-size',
                showCaption: false,
                showRemove: false,
                showUpload: false,
                autoReplace: true,
                showUploadedThumbs: true,
                showBrowse: false,
                browseOnZoneClick: true
            });

            $("#edit-modal .file-preview").attr("style", "width:160px;height:230px;");
            $("#edit-modal .clickable").attr('style', 'width:120px;height:195px;');
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
                $('.text-success').remove();
                $('.file-error-message').remove();
            });
        },
        validate: function() {
            return $('#edit-user-form').validate({
                rules: {
                    email:{
                        required: true,
                        email: true
                    },
                    mobile:{
                        required: true,
                        mobileZH: true
                    },
                    loginName:{
                        required: true,
                        rangelength: [4,15]
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
            var config = {
                headers: {
                    "api-version": "1.0",
                    "Authorization": sessionStorage.token
                }
            };

            var vm = this;
            axios.get(url, config)
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
                    }else{
                        alert(res.data.desc);
                    }
                })
                .catch(function (err) {
                    alert(err);
                });
        },
        update: function (callback) {
            if(!this.validate().form())
                return;

            var config = {
                headers: {
                    "api-version": "1.0",
                    "Authorization": sessionStorage.token
                }
            };
            var vm = this;
            axios.put(admin_user_update_URL, vm.$data, config)
                .then(function (res) {
                    alert(res.data.desc);
                    if (res.data.status === 200){
                        callback();
                    }
                })
                .catch(function (err) {
                    alert(err);
                });
        }
    },
    mounted: function () {
        ifox_table_delegate.getDetail = this.detail;
        ifox_table_delegate.edit = this.update;
        this.initEditUserUploadFile();
    }
});