package com.ifox.platform.entity.base;

import com.ifox.platform.utility.datetime.DateTimeUtil;
import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;
import org.hibernate.event.spi.PreUpdateEvent;
import org.hibernate.event.spi.PreUpdateEventListener;

/**
 * @author Yeager
 *
 * entity事件监听器
 */
public class EntityListener implements PreInsertEventListener, PreUpdateEventListener {

    /**
     * 创建时间字段
     */
    private static final String CREATE_DATE_PROPERTY = "createDate";

    /**
     * 修改时间字段
     */
    private static final String MODIFY_DATE_PROPERTY = "modifyDate";

    @Override
    public boolean onPreInsert(PreInsertEvent event) {

        if (event.getEntity() instanceof BaseEntity){
            //当前entity的属性名称
            String[] propertyNames = event.getPersister().getEntityMetamodel().getPropertyNames();
            //当前entity的属性值
            Object[] state = event.getState();
            for (int i = 0; i < propertyNames.length ; i ++) {
                if (CREATE_DATE_PROPERTY.equals(propertyNames[i]) || MODIFY_DATE_PROPERTY.equals(propertyNames[i])){
                    state[i] = DateTimeUtil.getCurrentDate(null);
                }
            }
        }

        return false;
    }

    @Override
    public boolean onPreUpdate(PreUpdateEvent event) {

        if (event.getEntity() instanceof BaseEntity){
            //当前entity的属性名称
            String[] propertyNames = event.getPersister().getEntityMetamodel().getPropertyNames();
            //当前entity的属性值
            Object[] state = event.getState();
            for (int i = 0; i < propertyNames.length ; i ++) {
                if (MODIFY_DATE_PROPERTY.equals(propertyNames[i])){
                    state[i] = DateTimeUtil.getCurrentDate(null);
                }
            }
        }

        return false;
    }
}
