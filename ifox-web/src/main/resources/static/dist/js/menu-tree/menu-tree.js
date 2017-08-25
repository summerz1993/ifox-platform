Array.prototype.contains = function ( ele ) {
	for (i in this) {
		if (this[i] === ele) return true;
	}
	return false;
}

var menuTree = (function(){
    var detail_vue = new Vue({
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
                creator: {
                    lable: "创建者",
                    value: ""
                },
                level: {
                    lable: "级别",
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
            getCreator: function (id) {
                return id;
            },
            getResource: function (id) {
                return id;
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

	var _menu = {
		data: {},
		plugins: ["types"],
		contextmenu: {
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
										$("#menu").modal('show');
										_menu.operation.add(data);
									}  
							},
					"删除":{  
								"label": "删除",  
								"action": function (data) {
										var node = _menu.data.jsTree.jstree('get_node',data.reference[0]);
										//提示是否删除
										console.log("delete node : " + node.id);
										//删除
										_menu.operation.delete(data);
										//删除成功后刷新
										_menu.operation.refresh(data);
									}  
							},
					"编辑":{  
								"label": "编辑",  
								"action": function(data){
										var node = _menu.data.jsTree.jstree('get_node',data.reference[0]).original;
										$("#menu").modal('show');
										_menu.operation.update(data);
									}  
							}		
					}
		},
		types: {
				"#": {
						"max_children" : 1,
						"max_depth" : 4,
						"valid_children" : ["root"]
					},
				"root": {
						"icon" : "glyphicon glyphicon-link",
						"valid_children" : ["default","file","menu","button"]
					},
				"default": {
						"valid_children" : ["default","file","menu","button"]
					},
				"file": {
						"icon" : "glyphicon glyphicon-folder-open",
						"valid_children" : ["default","file","menu","button"]
					},
				"menu": {
						"icon" : "glyphicon glyphicon-indent-left",
						"valid_children" : ["default","file","menu","button"]
					},
				"button": {
						"icon" : "glyphicon glyphicon-th-large",
						"valid_children" : []
					}
		},
		initMenu: function(element, url, option){
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
                                    var default_ = data[0];
                                    detail_vue.detail(default_.id);
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
			
			if(option.contains("dnd"))
				_menu.plugins.push("dnd");
			
			_tree_option.plugins = _menu.plugins;
			
			var js_tree = $("#" + element).jstree(_tree_option);
			
			console.log(_tree_option.plugins);
			
			this.data.jsTree = js_tree;
		},
		initEvent: function(element, clickable){
			$("#" + element).on('move_node.jstree', function(e, data){
				console.log(data.node);
				console.log(data.parent);
				_menu.operation.move();
			});
			
			if(clickable){
				$("#" + element).bind("select_node.jstree deselect_node.jstree", 
										function(e, data){
											var node = data.node;
											console.log(node);
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
				
				//if(inst.is_selected(obj)) {
				//	inst.delete_node(inst.get_selected());
				//} else {
					inst.delete_node(obj);
				//}
			},
			move: function(){
				
			},
            getDetail: function (id) {
                detail_vue.detail(id);
            },
			refresh: function(data){
				console.log("refresh");
				_menu.data.jsTree.jstree('refresh');
			}
		}
	}
	
	return {
		init: function(element, url, option, clickable){
			_menu.initMenu(element, url, option);
			_menu.initEvent(element, clickable);
		}
	};
})();