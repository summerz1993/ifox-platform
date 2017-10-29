package com.ifox.platform.jpa.converter;

import com.ifox.platform.common.page.SimplePage;
import org.springframework.data.domain.Page;

public class SpringDataPageConverter<T> {

    /**
     * 通过Page对象初始化
     * @param page Page
     * @return SimplePage
     */
    public SimplePage<T> convertToSimplePage(Page<T> page) {
        return new SimplePage<T>(page.getNumber(), page.getSize(), (int)page.getTotalElements(), page.getContent());
    }

}
