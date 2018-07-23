package cn.cookie.common.cache.dao;

import cn.cookie.common.cache.entity.SystemCfg;
import cn.cookie.framework.dao.BaseDao;

public interface SystemCfgDao extends BaseDao<SystemCfg> {
    void setSysteCfg(SystemCfg systeCfg);
}