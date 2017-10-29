package com.ifox.platform.system.request.adminuser;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

public class AdminUserChangePwdRequest {

    @NotBlank
    private String originalPassword;

    @NotBlank
    @Size(min = 8)
    private String newPassword;

    @NotBlank
    @Size(min = 8)
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
