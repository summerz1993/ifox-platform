Array.prototype.contains = function ( ele ) {
	for (i in this) {
		if (this[i] === ele) return true;
	}
	return false;
}

var menuTree = (function(){
    //点击详情vue
    var detail_vue = "";
    //添加vue
    var add_model_vue = "";
   //编辑vue
    var edit_model_vue = "";

	var _menu = {
		data: {},
		plugins: ["types"],
		contextmenu: {
		    "select_node": false,
			"items":{  
					"create":null,  
					"rename":null,  
					"remove":null,  
					"ccp":null,  
					"添加":{  
								"label": "添加",  
								"action": function(data){
										var node = _menu.data.jsTree.jstree('get_node',data.reference[0])
										var pid = node.id;
										$("#add-menu").modal('show');
										add_model_vue.initData(pid);
                                        $("#save-add-modal-btn").unbind('click');
                                        $("#save-add-modal-btn").click(function () {
                                            add_model_vue.save(data);
                                        });
										// _menu.operation.add(data);
									}  
							},
					"删除":{  
								"label": "删除",  
								"action": function (data) {
										var node = _menu.data.jsTree.jstree('get_node',data.reference[0]);
										//提示是否删除
                                        layer.open({
                                            content: '确认删除菜单“' + node.text + "“?"
                                            ,btn: ['确定', '取消']
                                            ,yes: function(index, layero){
                                                //删除
                                                _menu.operation.delete(data);
                                                layer.closeAll()
                                            }
                                            ,btn2: function(index, layero){

                                            }
                                            ,cancel: function(){

                                            }
                                        });
									}  
							},
					"编辑":{  
								"label": "编辑",  
								"action": function(data){
                                        var node = _menu.data.jsTree.jstree('get_node',data.reference[0]);
										$("#edit-menu").modal('show');
										edit_model_vue.initData(node.id);
                                        $("#save-edit-modal-btn").unbind('click');
                                        $("#save-edit-modal-btn").click(function () {
                                            edit_model_vue.save(data);
                                        });
										// _menu.operation.update(data);
									}  
							}		
					}
		},
		types: {
            "menu": {
                    "icon" : "glyphicon glyphicon-indent-left"
            },
            "button": {
                    "icon" : "glyphicon glyphicon-th-large"
            }
		},
		initMenu: function(element, url, option, clickable){
			var _tree_option = {
				core: {
					"animation" : 0,
					"check_callback" : true,
					"themes" : { "stripes" : true },
                    'data' :function (obj, callback) {
                        axios.get(url, ifox_table_ajax_options)
                            .then(function (res) {
                                if(res.data.status === 200){
                                    var data = res.data.data;
                                    callback.call(this, data);

                                    if(clickable){
                                        var default_ = data[0];
                                        _menu.operation.getDetail(default_.id);
                                    }
                                }else{
                                    layer.msg(res.data.desc);
                                }
                            })
                            .catch(function (err) {
                                serverError(err);
                            });
                    }
				}
			};
			
			_tree_option.types = _menu.types;
			
			if(option.contains("contextmenu")){
			    _menu.plugins = ["types"];
				_menu.plugins.push("contextmenu");
				_tree_option.contextmenu = _menu.contextmenu;
			}
			
			if(option.contains("checkbox")){
                _menu.plugins = ["types"];
                _menu.plugins.push("checkbox");
            }

			_tree_option.plugins = _menu.plugins;

            $("#" + element).jstree("destroy");
			var js_tree = $("#" + element).jstree(_tree_option);

			this.data.jsTree = js_tree;
		},
		initEvent: function(element, option, clickable){
            if(option.contains("contextmenu")){
                add_model_vue = _menu.operation.getAddModelVue();
                edit_model_vue = _menu.operation.getEditModelVue();
            }

			if(clickable){
                detail_vue = _menu.operation.getDetailVue();
				$("#" + element).bind("select_node.jstree deselect_node.jstree", 
                    function(e, data){
                        var node = data.node;
                        _menu.operation.getDetail(node.id);
                });
			}
		},
		operation: {
			add: function(data, newNode){
				var inst = $.jstree.reference(data.reference),
							obj = inst.get_node(data.reference);
				inst.create_node(obj, {}, "last", function (new_node) {
					inst.set_id (new_node, newNode.id);
					inst.set_text (new_node, newNode.name);
					inst.set_type (new_node, newNode.button ? "button" : "menu");
					try {
							inst.edit(new_node);
						} catch (ex) {
							setTimeout(function () { inst.edit(new_node); },0);
						}

                    $("#add-menu").modal('hide');
				});
			},
			update: function (data, node) {
				var inst = $.jstree.reference(data.reference),
							obj = inst.get_node(data.reference);

				inst.set_text (obj, node.name);
				inst.set_type (obj, node.button ? "button" : "menu");
                try {
                    inst.edit(obj, function () {
                        return true;
                    });
                } catch (ex) {
                    setTimeout(function () { inst.edit(obj, function () {
                        return true;
                    }); },0);
                }

                $("#edit-menu").modal('hide');
			},
			delete: function(data){
				var inst = $.jstree.reference(data.reference),
						obj = inst.get_node(data.reference);

				var url = menu_permission_delete_URL + "/" + obj.id;
                axios.delete(url, ifox_table_ajax_options)
                    .then(function (res) {
                        if(res.data.status === 200){
                            inst.delete_node(obj);
                            //删除成功后刷新
                            _menu.operation.refresh(data);
                        }else{
                            layer.msg(res.data.desc);
                        }
                    })
                    .catch(function (err) {
                        serverError(err);
                    });
			},
            getDetail: function (id) {
                detail_vue.detail(id);
            },
			refresh: function(data){
				_menu.data.jsTree.jstree('refresh');
			},
            selectNode: function (arr) {
                _menu.data.jsTree.on('loaded.jstree', function(e, data){
                    var inst = data.instance;
                    for (var i = 0; i < arr.length; i ++){
                        var obj = inst.get_node(arr[i]);
                        inst.select_node(obj);
                    }
                });
            },
            getAllSelected: function () {
                var selected_arr = _menu.data.jsTree.jstree('get_selected');
                return selected_arr;
            },
            getDetailVue: function () {
                return new Vue({
                    el: "#selected-menu-detail",
                    data: {
                        object: {
                            name: {
                                label: "名称",
                                value: "",
                                isActive: true
                            },
                            link: {
                                label: "链接",
                                value: ""
                            },
                            level: {
                                label: "级别",
                                value: ""
                            },
                            status: {
                                label: "状态",
                                value: ""
                            },
                            buildinSystem: {
                                label: "系统内置",
                                value: ""
                            },
                            resource: {
                                label: "所属资源",
                                value: ""
                            },
                            creator: {
                                label: "创建者",
                                value: ""
                            },
                            remark: {
                                label: "备注",
                                value: ""
                            }
                        }
                    },
                    methods: {
                        /**
                         * 查看菜单详情
                         * @param id
                         */
                        detail: function (id) {
                            var url = menu_permission_get_URL + "/" + id;
                            this.resetData();
                            var vm = this;
                            axios.get(url, ifox_table_ajax_options)
                                .then(function (res) {
                                    if(res.data.status === 200){
                                        var res_data = res.data.data;
                                        vm.object.name.value = res_data.name;
                                        vm.object.link.value = res_data.url;
                                        vm.object.level.value = res_data.level;
                                        vm.object.creator.value =res_data.creatorName;
                                        vm.object.buildinSystem.value = res_data.buildinSystem ? "是" : "否";
                                        vm.object.resource.value = res_data.resourceName;
                                        vm.object.remark.value = res_data.remark;
                                        if (res_data.status === "ACTIVE") {
                                            vm.object.status.value = "有效";
                                        } else if (res_data.status === "INVALID") {
                                            vm.object.status.value = "无效";
                                        } else {
                                            vm.object.status.value = "";
                                        }
                                    }else{
                                        layer.msg(res.data.desc);
                                    }
                                })
                                .catch(function (err) {
                                    serverError(err);
                                });
                        },
                        resetData: function () {
                            this.object.name.value = "";
                            this.object.link.value = "#";
                            this.object.creator.value = "";
                            this.object.buildinSystem.value = "";
                            this.object.resource.value = "";
                        }
                    }
                });
            },
            getAddModelVue: function () {
                return new Vue({
                    el: "#add-modal",
                    data: {
                        parentId: "",
                        name: "",
                        url: "",
                        buildinSystem: true,
                        button: false,
                        resource: "",
                        resources: [],
                        remark: "",
                        status: "ACTIVE"
                    },
                    methods: {
                        validate: function () {
                            return $('#menu-add-form').validate({
                                rules: {
                                    name:{
                                        required: true,
                                        minlength: 2
                                    },
                                    url:{
                                        required: true,
                                        resourceUrl: true
                                    }
                                },
                                messages: {
                                    name: "请输入2位有效的菜单名称！",
                                    url: "请输入正确的资源路径！"
                                }
                            });
                        },
                        initData: function (parentId) {
                            var vm = this;
                            vm.resetData();
                            vm.parentId = parentId;
                            vm.initResources();
                        },
                        initResources: function () {
                            var vm = this;
                            axios.get(resource_list_URL, ifox_table_ajax_options)
                                .then(function (res) {
                                    if(res.data.status === 200){
                                        var res_data = res.data.data;
                                        vm.resources =  res_data;
                                        vm.resource = res_data[0].id;
                                    }else{
                                        layer.msg(res.data.desc);
                                    }
                                })
                                .catch(function (err) {
                                    serverError(err);
                                });
                        },
                        selectVal: function(ele) {
                            this.resource = ele.target.value;
                        },
                        save: function (data) {
                            if(!this.validate().form())
                                return;

                            var vm = this;
                            var req_data = {
                                parentId: vm.parentId,
                                name: vm.name,
                                url: vm.url,
                                buildinSystem: vm.buildinSystem,
                                button: vm.button,
                                resource: vm.resource,
                                remark: vm.remark,
                                status: vm.status
                            };
                            axios.post(menu_permission_save_URL, req_data, ifox_table_ajax_options)
                                .then(function (res) {
                                    layer.msg(res.data.desc);
                                    if(res.data.status === 200){
                                        _menu.operation.add(data, res.data.data);
                                    }
                                })
                                .catch(function (err) {
                                    serverError(err);
                                });
                        },
                        resetData: function () {
                            this.name = "";
                            this.url = "";
                            this.buildinSystem = true;
                            this.button = false;
                            this.resource = "";
                            this.resources = [];
                            this.remark = "";
                        }
                    }
                });
            },
            getEditModelVue: function () {
                return new Vue({
                    el: "#edit-modal",
                    data: {
                        id: "",
                        parentId: "",
                        name: "",
                        url: "",
                        level: "",
                        buildinSystem: true,
                        button: false,
                        status: "",
                        creator: "",
                        resource: "",
                        resources: [],
                        remark: ""
                    },
                    methods: {
                        validate: function () {
                            return $('#menu-edit-form').validate({
                                rules: {
                                    name:{
                                        required: true,
                                        minlength: 2
                                    },
                                    url:{
                                        required: true,
                                        resourceUrl: true
                                    }
                                },
                                messages: {
                                    name: "请输入2位有效的菜单名称！",
                                    url: "请输入正确的资源路径！"
                                }
                            });
                        },
                        initData: function (id) {
                            var url = menu_permission_get_URL + "/" + id;
                            var vm = this;
                            vm.resetData();
                            vm.initResources();
                            axios.get(url, ifox_table_ajax_options)
                                .then(function (res) {
                                    if(res.data.status === 200){
                                        var res_data = res.data.data;
                                        vm.id = res_data.id;
                                        vm.parentId = res_data.parentId;
                                        vm.name = res_data.name;
                                        vm.url = res_data.url;
                                        vm.level = res_data.level;
                                        vm.status = res_data.status;
                                        vm.creator = res_data.creator;
                                        vm.buildinSystem = res_data.buildinSystem;
                                        vm.button = res_data.button;
                                        vm.remark = res_data.remark;
                                        vm.resource = res_data.resource;
                                    }else{
                                        layer.msg(res.data.desc);
                                    }
                                })
                                .catch(function (err) {
                                    serverError(err);
                                });
                        },
                        initResources: function () {
                            var vm = this;
                            axios.get(resource_list_URL, ifox_table_ajax_options)
                                .then(function (res) {
                                    if(res.data.status === 200){
                                        var res_data = res.data.data;
                                        vm.resources = res_data;
                                    }else{
                                        layer.msg(res.data.desc);
                                    }
                                })
                                .catch(function (err) {
                                    serverError(err);
                                });
                        },
                        selectVal: function(ele) {
                            this.resource = ele.target.value;
                        },
                        save: function (data) {
                            if(!this.validate().form())
                                return;

                            var vm = this;
                            var req_data = {
                                id: vm.id,
                                parentId: vm.parentId,
                                name: vm.name,
                                url: vm.url,
                                level: vm.level,
                                buildinSystem: vm.buildinSystem,
                                button: vm.button,
                                status: vm.status,
                                creator: vm.creator,
                                resource: vm.resource,
                                remark: vm.remark
                            }

                            axios.put(menu_permission_update_URL, req_data, ifox_table_ajax_options)
                                .then(function (res) {
                                    layer.msg(res.data.desc);
                                    if(res.data.status === 200){
                                        _menu.operation.update(data, res.data.data);
                                    }
                                })
                                .catch(function (err) {
                                    serverError(err);
                                });
                        },
                        resetData: function () {
                            this.name = "";
                            this.url = "";
                            this.buildinSystem = true;
                            this.button = false;
                            this.resource = "";
                            this.resources = [];
                            this.remark = "";
                        }
                    }
                });
            }
		}
	}
	
	return {
		init: function(element, url, option, clickable){
			_menu.initMenu(element, url, option, clickable);
			_menu.initEvent(element, option, clickable);
		},
        defaultSelected: function (arr) {
            _menu.operation.selectNode(arr);
        },
        getAllSelected: function () {
            return _menu.operation.getAllSelected();
        }
	};
})();