package cn.cookie.framework.constant.enums;

/**
 * Package Name: cn.upenny.framework.constant.enums
 * Description:
 * Author: qiancai
 * Create Date:2015/12/7
 */
public enum PermissionResult {
    PERMISSION_SUCCESS("验证通过"),SESSION_TIMEOUT("登陆超时"),PERMISSION_DENIED("没有权限");

    private String type;

    private PermissionResult(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
