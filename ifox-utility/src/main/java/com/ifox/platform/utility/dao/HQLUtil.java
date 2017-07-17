package com.ifox.platform.utility.dao;


import com.ifox.platform.common.bean.QueryConditions;
import com.ifox.platform.common.bean.QueryProperty;
import com.ifox.platform.common.bean.SimpleOrder;
import com.ifox.platform.utility.converter.OperationConverter;
import org.hibernate.Session;
import org.hibernate.Query;

/**
 * @author Yeager
 *
 * HQL工具类
 */
public class HQLUtil {

    private static final String ENTITY_ALIAS = "entityAlias";

    // ================= 内容查询相关 =================

    /**
     * 生成最终的HQL
     * @param propertyArray 查询字段
     * @param entityName 实体名称
     * @param params 参数条件
     * @param simpleOrders 排序
     * @return HQL语句
     */
    private static String generateFinalHQL(String[] propertyArray, String entityName, QueryProperty[] params, SimpleOrder[] simpleOrders){
        return  //拼接查询字段
                convertSelectPropertyToHQL(propertyArray) +
                //拼接表
                generateFromEntityHQL(entityName) +
                //拼接WHERE条件
                convertQueryPropertyToHQL(params) +
                //拼接ORDER BY排序
                convertOrderToHQl(simpleOrders);
    }

    /**
     * 生成最终的HQL
     * @param entityName 实体名称
     * @param queryConditions 查询条件集合
     * @return HQL语句
     */
    public static String generateFinalHQL(String entityName, QueryConditions queryConditions){
        return generateFinalHQL(queryConditions.getProperties(), entityName, queryConditions.getQueryProperties(), queryConditions.getSimpleOrders());
    }

    /**
     * 生成最终的HQL
     * @param entityName 实体名称
     * @param params 参数条件
     * @return HQL语句
     */
    public static String generateFinalHQL(String entityName, QueryProperty[] params){
        return generateFinalHQL(null, entityName, params, null);
    }

    /**
     * 获取查询entity的HQL
     * @return HQL语句
     */
    public static String generateFromEntityHQL(String entityName){
        return " FROM " + entityName + " AS " + ENTITY_ALIAS;
    }

    // ================= count数量查询相关 =================

    /**
     * 生成最终计数的HQL
     * @param entityName 实体名称
     * @param params 参数条件
     * @param simpleOrders 排序
     * @return HQL语句
     */
    private static String generateCountEntityHQL(String entityName, QueryProperty[] params, SimpleOrder[] simpleOrders){
        return  //拼接表
                generateCountEntityHQL(entityName) +
                //拼接WHERE条件
                convertQueryPropertyToHQL(params) +
                //拼接ORDER BY排序
                convertOrderToHQl(simpleOrders);
    }

    /**
     * 生成最终的HQL
     * @param entityName 实体名称
     * @param queryConditions 查询条件集合
     * @return HQL语句
     */
    public static String generateCountEntityHQL(String entityName, QueryConditions queryConditions){
        return generateCountEntityHQL(entityName, queryConditions.getQueryProperties(), queryConditions.getSimpleOrders());
    }

    /**
     * 生成查询数量的HQL
     * @param entityName 实体名称
     * @param params 参数条件
     * @return HQL语句
     */
    public static String generateCountEntityHQL(String entityName, QueryProperty[] params){
        return generateCountEntityHQL(entityName, params, null);
    }

    /**
     * 生成查询数量的HQL
     * @param entityName 实体名称
     * @return HQL语句
     */
    public static String generateCountEntityHQL(String entityName){
        return " SELECT COUNT(*) FROM " + entityName + " AS " + ENTITY_ALIAS;
    }


    // ================= 分页相关方法 =================




    // ================= 公共方法 =================

    /**
     * 根据HQL创建hibernate的Query对象
     * @param session hibernate session
     * @param hql HQL
     * @param params 参数值
     * @return Query对象
     */
    public static Query createQueryByHQL(Session session, String hql, Object... params){
        Query query = session.createQuery(hql);
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i, params[i]);
            }
        }
        return query;
    }

    /**
     * 转换property为HQL查询语句
     * @param propertyArray 属性
     * @return HQL语句
     */
    private static String convertSelectPropertyToHQL(String[] propertyArray){
        StringBuilder selectHQL = new StringBuilder();
        if (propertyArray != null && propertyArray.length > 0) {
            selectHQL.append(" SELECT ");
            for (int i = 0; i < propertyArray.length; i++) {
                selectHQL.append(ENTITY_ALIAS + ".").append(propertyArray[i]);
                if(i < propertyArray.length - 1){
                    selectHQL.append(",");
                }
            }
        }
        return selectHQL.toString();
    }

    /**
     * 转换QueryProperty为HQL条件语句
     * @param params QueryProperty对象
     * @return HQL语句
     */
    private static String convertQueryPropertyToHQL(QueryProperty[] params){
        StringBuilder whereHQL=new StringBuilder();
        if (params != null && params.length > 0) {
            whereHQL.append(" WHERE 1 = 1 ");
            for (QueryProperty param : params) {
                whereHQL.append(" AND ");
                whereHQL.append(ENTITY_ALIAS + ".").append(param.getProperty());
                whereHQL.append(" ").append(OperationConverter.convertOperation(param.getOperation())).append(" ");
                if (param.getValue() == null) {
                    whereHQL.append(" ").append(param.getValue()).append(" ");
                } else {
                    whereHQL.append("'").append(param.getValue()).append("'");
                }
            }
        }
        return whereHQL.toString();
    }

    /**
     * 转换排序对象为HQL语句
     * @param simpleOrders 排序对象
     * @return HQL语句
     */
    private static String convertOrderToHQl(SimpleOrder[] simpleOrders){
        StringBuilder orderByHQL = new StringBuilder();
        if (simpleOrders != null && simpleOrders.length > 0) {
            orderByHQL.append(" ORDER BY ");
            for (int i = 0; i < simpleOrders.length; i++) {
                orderByHQL.append(ENTITY_ALIAS + ".").append(simpleOrders[i].getProperty());
                orderByHQL.append(" ");
                orderByHQL.append(simpleOrders[i].getOrderMode().toString());
                if(i < simpleOrders.length - 1){
                    orderByHQL.append(",");
                }
            }
        }
        return orderByHQL.toString();
    }

}