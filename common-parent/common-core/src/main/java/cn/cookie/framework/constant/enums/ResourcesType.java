package cn.cookie.framework.constant.enums;

/**
 * Created by qiancai on 2015/12/7.
 */
public enum ResourcesType {
    MENU("菜单"),BUTTON("按钮"),API("页面调用API接口");

    private String type;

    private ResourcesType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
