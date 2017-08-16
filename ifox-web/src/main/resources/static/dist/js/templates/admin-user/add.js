new Vue({
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
        remark: ""
    },
    methods: {
        initAddUserFileUpload: function () {
            $("#headPortrait-add").fileinput({
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

            $("#add-modal .file-preview").attr("style", "width:160px;height:230px;");
            $("#add-modal .clickable").attr('style', 'width:120px;height:195px;');
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
                $('.text-success').remove();
                $('.file-error-message').remove();
            });
        },
        validate: function () {
            return $('#add-user-form').validate({
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
                        required: true,
                        rangelength: [2,20]
                    },
                    password:{
                        required: true,
                        password: 8
                    }
                },
                messages: {
                    email: "请输入一个正确的邮箱",
                    mobile: "请输入正确的联系电话",
                    loginName: "请输入4-15位有效的登录名",
                    nickName: "请输入2-20位有效的昵称",
                    password: "请输入至少8位，包含数字及字母的有效密码"
                }
            });
        },
        save: function (callback) {
            if(!this.validate().form())
                return;

            var config = {
                headers: {
                    "api-version": "1.0",
                    "Authorization": sessionStorage.token
                }
            };
            var vm = this;

            axios.post(admin_user_save_URL, vm.$data, config)
                .then(function (res) {
                    alert(res.data.desc);
                    if(res.data.status === 200){
                        callback();
                    }
                })
                .catch(function (err) {
                    alert(err);
                });
        }
    },
    mounted: function () {
        ifox_table_delegate.add = this.save;
        this.initAddUserFileUpload();
    }
});