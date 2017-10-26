package com.ifox.platform.system.request.resource;

import com.ifox.platform.system.dto.base.ResourceBaseColumns;

public class ResourceUpdateRequest extends ResourceBaseColumns {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ResourceUpdateRequest{" +
            "id='" + id + '\'' +
            "} " + super.toString();
    }
}
