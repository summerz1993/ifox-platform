package com.ifox.platform.system.request.resource;

import com.ifox.platform.common.rest.request.PageRequest;
import com.ifox.platform.system.entity.ResourceEO;

public class ResourcePageRequest extends PageRequest{

    /**
     * 资源名称
     */
    private String name;

    /**
     * 资源类型
     */
    private ResourceEO.ResourceEOType type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ResourceEO.ResourceEOType getType() {
        return type;
    }

    public void setType(ResourceEO.ResourceEOType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ResourcePageRequest{" +
            "name='" + name + '\'' +
            ", type=" + type +
            "} " + super.toString();
    }
}
