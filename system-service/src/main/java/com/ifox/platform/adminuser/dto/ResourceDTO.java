package com.ifox.platform.adminuser.dto;

public class ResourceDTO extends ResourceColumns {
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
