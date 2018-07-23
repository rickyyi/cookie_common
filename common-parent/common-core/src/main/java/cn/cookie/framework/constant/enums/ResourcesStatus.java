package cn.cookie.framework.constant.enums;

/**
 * Created by qiancai on 2015/12/7.
 */
public enum  ResourcesStatus {
    NORMAL("正常"), FROZEN("冻结");
    private String type;

    private ResourcesStatus(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
