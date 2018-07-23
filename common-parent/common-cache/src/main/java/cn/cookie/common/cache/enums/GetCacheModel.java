package cn.cookie.common.cache.enums;

/**
 * Created by qiancai on 2015/12/16 0016.
 */
public enum GetCacheModel {
    FLUSH("强制刷新缓存"),
    NO_FLUSH("不刷新"),
    ;

    private String type;
    private GetCacheModel(String type) {
        this.type = type;
    }
    @Override
    public String toString() {
        return type;
    }
}
