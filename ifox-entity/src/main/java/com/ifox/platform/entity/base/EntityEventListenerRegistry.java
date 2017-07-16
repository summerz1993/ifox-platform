package com.ifox.platform.entity.base;

import org.hibernate.SessionFactory;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author Yeager
 *
 * Entity监听器注册器
 */
@SuppressWarnings("unchecked")
@Component
public class EntityEventListenerRegistry {

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * spring容器初始化bean之前调用
     * EventListenerRegistry参照:http://docs.jboss.org/hibernate/orm/5.2/userguide/html_single/Hibernate_User_Guide.html#annotations-jpa-entitylisteners
     */
    @PostConstruct
    public void registerListeners(){
        EventListenerRegistry eventListenerRegistry = ((SessionFactoryImplementor) sessionFactory).getServiceRegistry().getService(EventListenerRegistry.class);
        eventListenerRegistry.prependListeners(EventType.PRE_INSERT, EntityListener.class);
        eventListenerRegistry.prependListeners(EventType.PRE_UPDATE, EntityListener.class);
    }

}
