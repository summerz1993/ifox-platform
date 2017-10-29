package com.ifox.platform.jpa.converter;

import com.ifox.platform.common.rest.request.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageRequestConverter {

    /**
     * 转换PageRequest为spring data Pageable
     * @param pageRequest PageRequest
     * @return Pageable
     */
    public static Pageable convertToSpringDataPageable(PageRequest pageRequest) {
        return new org.springframework.data.domain.PageRequest(pageRequest.getPageNo() - 1, pageRequest.getPageSize(), SimpleOrderConverter.convertToSort(pageRequest.getSimpleOrderList()));
    }

}
