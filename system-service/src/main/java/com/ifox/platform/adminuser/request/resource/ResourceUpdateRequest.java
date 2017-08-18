package com.ifox.platform.adminuser.request.resource;

import com.ifox.platform.adminuser.dto.ResourceColumns;

public class ResourceUpdateRequest extends ResourceColumns {
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
