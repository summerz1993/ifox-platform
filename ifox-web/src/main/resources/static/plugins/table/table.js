/**
 *	#table为列表的页面控件id
 */
var $table = $('#table');

/**
 *	#table为列表的页面控件中添加按钮
 */
var	$add = $('#add');

/**
 *	#table为列表的页面控件中删除按钮
 */
var	$remove = $('#remove');

/**
 *	#table为列表的页面控件中被选中记录
 */
var	selections = [];

/**
 *	自定义table列
 *   {
 *		'id': {
 *				value:'编号',
 *				disabled: true,
 *				formatter:rolFormatter
 *			}
 *	 }
 *	 columns结构为key-value形式，key对应服务器返回数据key，value包含table中显示值，是否显示，格式化规则，其中formatter为函数
 */
var columns = {
	'id': {
			value:'编号',
			disabled: true,
			formatter: ''
		}
}

/**
 *	#table为列表的页面控件中 bootstrap table相关初始化配置参数
 */
var options =  {
		/**
		 *	table数据请求url
		 */
		'url': '',
		/**
		 *	table数据请求方式 “get”、“post”
		 */
		'method': '',
		/**
		 *	table数据请求禁用ajax缓存
		 */
		'cache': false,
		/**
		 *	table数据请求类型
		 */
		'contentType': 'application/json',
		/**
		 *	table数据返回形式
		 */
		'dataType': 'json',
		/**
		 *	一个jQuery 选择器，指明自定义的toolbar
		 */
		'toolbar': "#toolbar",
		/**
		 *	是否启用搜索框
		 */
		//'search': true,
		/**
		 *	是否显示 刷新按钮
		 */
		'showRefresh': true,
		/**
		 *	是否显示 切换试图（table/card）按钮
		 */
		'showToggle': false,
		/**
		 *	是否显示 内容列下拉框
		 */
		'showColumns': true,
		/**
		 *	是否显示 详细页面模式
		 */
		'detailView': true,
		/**
		 *	当列数小于此值时，将隐藏内容列下拉框
		 */
		'minimumCountColumns': "2",
		/**
		 *	是否显示 表格底部显示分页条
		 */
		'pagination': true,
		/**
		 *	如果设置了分页，设置可供选择的页面数据条数。设置为All 则显示所有记录。
		 */
		'pageList': ['10', '25', '50', '100', 'ALL'],
		/**
		 *	设置在哪里进行分页，可选值为 'client' 或者 'server'。设置 'server'时，必须设置 服务器数据地址（url）或者重写ajax方法
		 */
		'sidePagination': "server",
		/**
		 *	请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果 queryParamsType = 'limit' 				,返回参数必须包含
		 *  limit, offset, search, sort, order 否则, 需要包含: 
		 *	pageSize, pageNumber, searchText, sortName, sortOrder. 
		 * 	返回false将会终止请求
		 */
		'queryParams': queryParams,
		/**
		 *	格式化详细页面模式的视图
		 */
		'detailFormatter': detailFormatter,
		/**
		 *	加载服务器数据之前的处理程序，可以用来格式化数据。参数：res为从服务器请求到的数据。
		 */
		'responseHandler': responseHandler,
		/**
		 *	提交ajax请求时的附加参数。
		 */
		'ajaxOptions': {},
		/**
		 *	列配置项
		 */
		'columns': []
	};

/**
 * table依赖的相关js
 */ 
var scripts = [
	location.search.substring(1) || '../plugins/bootstrap-table/bootstrap-table.js',
	'../plugins/bootstrap-table/extensions/editable/bootstrap-table-editable.js',
	'../plugins/bootstrap3-editable/js/bootstrap-editable.js',
	'../plugins/bootstrap-table/locale/bootstrap-table-zh-CN.js'
];

/**
 * table初始化，操作声明
 */
function initComponent(){
		$table.bootstrapTable(options);
		
		// sometimes footer render error.
		setTimeout(function () {
			$table.bootstrapTable('resetView');
		}, 200);
		
		$table.on('check.bs.table uncheck.bs.table ' +
			'check-all.bs.table uncheck-all.bs.table', function () {
			$remove.prop('disabled', !$table.bootstrapTable('getSelections').length);
			
			// save your data, here just save the current page
			selections = getIdSelections();
			// push or splice the selections if you want to save all data selections
		});
		
		$add.click(function () {
			$('#addModal').modal(options);
			
			$table.bootstrapTable('refresh', {silent: true});
		});
		
		$remove.click(function () {
			var ids = getIdSelections();
			$table.bootstrapTable('remove', {
				field: 'id',
				values: ids
			});
			
			$table.bootstrapTable('refresh', {silent: true});
			$remove.prop('disabled', true);
		});
				
		$(window).resize(function () {
			$table.bootstrapTable('resetView', {
				height: $(window).height()-110
			});
		});
}

/**
 * 初始化table，获取相应js资源
 * @param arr
 * @param iterator
 * @param callback
 * @returns {*}
 */
function eachSeries(arr, iterator, callback) {
	callback = callback || function () {};
	if (!arr.length) {
		return callback();
	}
	var completed = 0;
	var iterate = function () {
		iterator(arr[completed], function (err) {
			if (err) {
				callback(err);
				callback = function () {};
			}else {
				completed += 1;
				if (completed >= arr.length) {
					callback(null);
				}else {
					iterate();
				}
			}
		});
	};
	iterate();
}

/**
 * 获取依赖js，并添加至页面
 * @param url
 * @param callback
 * @returns {undefined}
 */
function getScript(url, callback) {
	var head = document.getElementsByTagName('head')[0];
	var script = document.createElement('script');
	script.src = url;

	var done = false;
	// Attach handlers for all browsers
	script.onload = script.onreadystatechange = function() {
		if (!done && (!this.readyState ||
				this.readyState == 'loaded' || this.readyState == 'complete')) {
			done = true;
			if (callback)
				callback();

			// Handle memory leak in IE
			script.onload = script.onreadystatechange = null;
		}
	};

	head.appendChild(script);

	// We handle everything using the script element injection
	return undefined;
}

/**
 * 获取被选中列
 */
function getIdSelections() {
	return $.map($table.bootstrapTable('getSelections'), function (row) {
		return row.id
	});
}

/**
 * 自定义查询参数
 * @param params
 * @returns {{buildinSystem: string, loginName: (*|jQuery), pageNo: number, pageSize: *, status: string}}
 */
function queryParams(params) {
    var temp = table_oper.searchParams(params);
    return temp;
}

/**
 * 加载服务器数据之前的处理程序，可以用来格式化数据。参数：res为从服务器请求到的数据
 * @param res
 * @returns {{rows: Array, total: string, pageNo: string, pageSize: string}}
 */
function responseHandler(res){
	var table_res = {
		rows: [],
		total: '',
		pageNo: '',
		pageSize: '',
	}
	table_res.rows = res.data;
	table_res.total = res.pageInfo.totalCount;
	table_res.pageNo = res.pageInfo.pageNo;
	table_res.pageSize = res.pageInfo.pageSize;
	
	$.each(table_res.rows, function (i, row) {
		row.state = $.inArray(row.id, selections) !== -1;
	});
	return table_res;
}

/**
 * 格式化详细页面模式的视图
 * @param index
 * @param row
 * @returns {string}
 */
function detailFormatter(index, row) {
	var html = [];
	$.each(row, function (key, value) {
		if(key != 'state'){
			var col_ = columns[key];
			var col_key = col_.value;
			
			if(col_.hasOwnProperty('formatter')){
				var col_formatter = col_.formatter;
				html.push('<p><b>' + col_.value + ':</b> ' + col_formatter(value, row, index) + '</p>');
			}else{
				html.push('<p><b>' + col_.value + ':</b> ' + value + '</p>');
			}
		}
	});
	return html.join('');
} 

/**
 *  初始化列
 *	res：{'row1':'row1','row2':'row2','row3':'row3'}
 * @param res
 * @returns {[*]}
 */
function initColumns(res){
	var column_key = Object.keys(res);
	column_key.sort(sortByLength);
	var col = [
			{
				'field': 'state',
				'checkbox': true,
				'align': 'center',
				'valign': 'middle'
			}
	];
	
	for(var i = 0; i < column_key.length; i ++){
		var col_formatter = columns[column_key[i]].formatter;
		var col_opt = {
				'field': column_key[i],
				'title': res[column_key[i]],
				'sortable': true,
				'align': 'center',
				'formatter': col_formatter
			}
		
		col.push(col_opt);	
	}
	
	var operate = {
			'field': 'operate',
			'title': '操作',
			'align': 'center',
			'events': {
				'click .edit': function (e, value, row, index) {
					$('#editModal').modal(options);
				},
				'click .remove': function (e, value, row, index) {
					$table.bootstrapTable('remove', {
						field: 'id',
						values: [row.id]
					});
					
					$table.bootstrapTable('refresh', {silent: true});
				}
			},
			'formatter': function(value, row, index) {
				return [
					'<a class="edit" href="javascript:void(0)" title="edit" style="margin-right:5px;">',
						'<i class="glyphicon glyphicon-edit"></i>',
					'</a>  ',
					'<a class="remove" href="javascript:void(0)" title="Remove" style="margin-left:5px;">',
						'<i class="glyphicon glyphicon-remove"></i>',
					'</a>'
				].join('');
			}
		}
	col.push(operate);
	return col;
}

/**
 * 获取table需要显示的列
 * @param res
 * @returns {Object}
 */
function getShowColumns(res){
	var res_key = Object.keys(res);
	
	var opt_str = "{"
	for(var i = 0; i < res_key.length; i ++){
		var key = res_key[i];
		var col = res[key];
		
		if(col.disabled == false){
			opt_str += ("'" + key + "'" + ':' + "'" + col.value + "'");
			if(i != (res_key.length - 1)){
				opt_str += ",";
			}
		}
	}
	opt_str += "}";
	var opt = eval("(" + opt_str + ")");
	
	return opt;
}

/**
 *
 * @param res
 */
function initQuery(res){
	var res_key = Object.keys(res);
	
	var html_ = '';
	for(var i = 0; i < res_key.length; i ++){
		var key = res_key[i];
		var col = res[key];
		
		if(col.query == true){
			var search_ = "<div class='form-group col-xs-3 col-md-3'>" + 
								"<input type='text' class='form-control' id='" + key + "' placeholder='" + col.value + "'>" + 
						  "</div>";
			
			html_ += search_;	
		}
	}
	
	var sear_btn = "<button id='search' class='btn btn-info' disabled>" + 
						"<i class='glyphicon glyphicon-search'></i> 搜索" + 
				   "</button>";
	html_ += sear_btn;
	$("#search-group").append(html_);
}

/**
 * 数组排序，按照键值长度排序
 * @param arr1
 * @param arr2
 * @returns {number}
 */
function sortByLength(arr1, arr2){
	var str1 = new String(arr1);
	var str2 = new String(arr2);
	return str1.length - str2.length;
} 

/**
 *
 * @type {{setOptions, setColumns, setAjaxOptions, init}}
 */
var TableComponent = (function(){
		return {
            /**
             * 设置table options，支持自定义options，详情参考bootstrap table官方文档
             * @param opt
             */
			setOptions: function (opt){
				if(opt != undefined && opt != null){
					options = opt;
				}
			},
            /**
             * 设置table options.columns，支持自定义columns，详情参考bootstrap table官方文档
             * @param columns
             */
			setColumns: function (columns){
				if(columns != undefined && columns != null){
					options.columns = columns;
				}
			},
            /**
             * 设置提交ajax请求时的附加参数
             * @param ajaxOptions
             */
			setAjaxOptions: function(ajaxOptions){
				if(ajaxOptions != undefined && ajaxOptions != null){
					options.ajaxOptions = ajaxOptions;
				}
			},
            /**
             * 初始化table
             * @param tableId
             * @param url
             * @param method
             */
			init: function(tableId, url, method){
				if(tableId != undefined && tableId != null){
					$table = $('#' + tableId);
				}
				
				if(url != undefined && url != null){
					options.url = url;
				}
				
				if(method != undefined && method != null){
					options.method = method;
				}
				
				eachSeries(scripts, getScript, initComponent);
			}
		}
	}
).call(this);
