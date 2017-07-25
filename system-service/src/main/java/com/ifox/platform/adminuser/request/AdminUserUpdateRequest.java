package com.ifox.platform.adminuser.request;

public class AdminUserUpdateRequest extends AdminUserSaveRequest {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
