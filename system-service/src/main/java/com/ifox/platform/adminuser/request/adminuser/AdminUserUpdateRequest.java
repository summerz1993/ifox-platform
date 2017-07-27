package com.ifox.platform.adminuser.request.adminuser;

public class AdminUserUpdateRequest extends AdminUserSaveRequest {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "AdminUserUpdateRequest{" +
            "id='" + id + '\'' +
            "} " + super.toString();
    }
}
