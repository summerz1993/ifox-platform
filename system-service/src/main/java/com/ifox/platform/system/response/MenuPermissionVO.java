package com.ifox.platform.system.response;

import com.ifox.platform.system.dto.MenuPermissionDTO;

public class MenuPermissionVO extends MenuPermissionDTO {

    private String creatorName;

    private String resourceName;

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    @Override
    public String toString() {
        return "MenuPermissionVO{" +
            "createrName='" + creatorName + '\'' +
            ", resourceName='" + resourceName + '\'' +
            "} " + super.toString();
    }
}
