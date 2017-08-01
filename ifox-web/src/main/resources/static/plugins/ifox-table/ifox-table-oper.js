/**
 *	user operate
 *  提供列表的基本操作
 *	调用仅需要实现
 * 	添加、修改、删除、查询、查询参数
 */
 var table_oper = {
	 add:function () {
         
     },
	 edit: function () {
         
     },
    /**
     * 删除用户
     * @param ids
     */
	 delete:function (ids) {
         
     },
    /**
     *  扩展bootstrap搜索，自定义搜索条件
     */
	 search: function () {
         
     },
	 /**
	  *	用于bootstrap table查询，返回查询参数
	  * 其中params为bootstrap table默认查询参数
	  *	@param Object
	  * 	{
	  *	    	limit:10
	  *			offset:0
	  *			order:"asc"
	  *			search:undefined
	  *			sort:"nickName"
	  *		}
	  */
     searchParams: function (params) {
         
     }
 }