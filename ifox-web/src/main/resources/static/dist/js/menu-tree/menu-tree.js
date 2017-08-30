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
										var node = _menu.data.jsTree.jstree('get_node',data.reference[0]).original;
										$("#edit-menu").modal('show');
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
                            .catch(function () {
                                serverError();
                            });
                    }
				}
			};
			
			_tree_option.types = _menu.types;
			
			if(option.contains("contextmenu")){
				_menu.plugins.push("contextmenu");
				_tree_option.contextmenu = _menu.contextmenu;
			}
			
			if(option.contains("checkbox"))
				_menu.plugins.push("checkbox");
			
			_tree_option.plugins = _menu.plugins;
			
			var js_tree = $("#" + element).jstree(_tree_option);
			
			this.data.jsTree = js_tree;
		},
		initEvent: function(element, option, clickable){
            if(option.contains("contextmenu")){
                add_model_vue = addModelVue();
                edit_model_vue = editModelVue();
            }

			if(clickable){
                detail_vue = getDetailVue();
				$("#" + element).bind("select_node.jstree deselect_node.jstree", 
                    function(e, data){
                        var node = data.node;
                        _menu.operation.getDetail(node.id);
                });
			}
		},
		operation: {
			add: function(data){
				var inst = $.jstree.reference(data.reference),
							obj = inst.get_node(data.reference);
				inst.create_node(obj, {}, "last", function (new_node) {
					inst.set_id (new_node, "5");
					inst.set_text (new_node, "menu5");
					inst.set_type (new_node, "menu");
					try {
							inst.edit(new_node);
						} catch (ex) {
							setTimeout(function () { inst.edit(new_node); },0);
						}
				});	
			},
			update: function (data) {
				var inst = $.jstree.reference(data.reference),
							obj = inst.get_node(data.reference);
				
				inst.set_text (obj, "test");
				inst.set_type (obj, "button");				
				
				inst.edit(obj);
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
                    .catch(function () {
                        serverError();
                    });
			},
            getDetail: function (id) {
                detail_vue.detail(id);
            },
			refresh: function(data){
				_menu.data.jsTree.jstree('refresh');
			}
		}
	}
	
	return {
		init: function(element, url, option, clickable){
			_menu.initMenu(element, url, option, clickable);
			_menu.initEvent(element, option, clickable);
		}
	};
})();


function getDetailVue() {
    return new Vue({
        el: "#selected-menu-detail",
        data: {
            object: {
                name: {
                    lable: "名称",
                    value: "",
                    isActive: true
                },
                link: {
                    lable: "链接",
                    value: ""
                },
                level: {
                    lable: "级别",
                    value: ""
                },
                creator: {
                    lable: "创建者",
                    value: ""
                },
                buildinSystem: {
                    lable: "系统内置",
                    value: ""
                },
                resource: {
                    lable: "所属资源",
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
                            vm.object.creator.value = vm.getCreator(res_data.creator);
                            vm.object.buildinSystem.value = res_data.buildinSystem ? "是" : "否";
                            vm.object.resource.value = vm.getResource(res_data.resource);
                        }else{
                            layer.msg(res.data.desc);
                        }
                    })
                    .catch(function () {
                        serverError();
                    });
            },
            /**
             * 获取菜单创建者
             * @param id
             */
            getCreator: function (id) {
                var url = admin_user_get_URL + "/" + id;
                var vm = this;
                axios.get(url, ifox_table_ajax_options)
                    .then(function (res) {
                        if(res.data.status === 200){
                            var res_data = res.data.data;
                            vm.object.creator.value =  res_data.loginName;
                        }else{
                            layer.msg(res.data.desc);
                        }
                    })
                    .catch(function () {
                        serverError();
                    });
            },
            /**
             * 获取菜单所属资源
             * @param id
             */
            getResource: function (id) {
                var url = resource_get_URL + "/" + id;
                var vm = this;
                axios.get(url, ifox_table_ajax_options)
                    .then(function (res) {
                        if(res.data.status === 200){
                            var res_data = res.data.data;
                            vm.object.resource.value =  res_data.name;
                        }else{
                            layer.msg(res.data.desc);
                        }
                    })
                    .catch(function () {
                        serverError();
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
}

function addModelVue() {
    return new Vue({
        el: "#add-modal",
        data: {

        },
        methods: {
            resetData: function () {

            }
        }
    });
}

function editModelVue() {
    return new Vue({
        el: "#edit-modal",
        data: {

        },
        methods: {
            resetData: function () {

            }
        }
    });
}