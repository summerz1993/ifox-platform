package com.ifox.platform.system.request.adminuser;

public class AdminUserChangePwdRequest {

    private String originalPassword;

    private String newPassword;

    private String confirmPassword;

    public String getOriginalPassword() {
        return originalPassword;
    }

    public void setOriginalPassword(String originalPassword) {
        this.originalPassword = originalPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String toString() {
        return "AdminUserChangePwdRequest{" +
            "originalPassword='" + originalPassword + '\'' +
            ", newPassword='" + newPassword + '\'' +
            ", confirmPassword='" + confirmPassword + '\'' +
            '}';
    }
}
