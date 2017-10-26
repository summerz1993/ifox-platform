package com.ifox.platform.system.dto;

import com.ifox.platform.system.dto.base.ResourceBaseColumns;

public class ResourceDTO extends ResourceBaseColumns {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ResourceDTO{" +
            "id='" + id + '\'' +
            "} " + super.toString();
    }
}
