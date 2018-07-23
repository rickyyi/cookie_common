package cn.cookie.common.cache;

import cn.cookie.common.cache.entity.ThirdServiceCfg;
import cn.cookie.common.cache.enums.GetCacheModel;
import cn.cookie.common.cache.dao.SystemCfgDao;
import cn.cookie.common.cache.dao.ThirdServiceCfgDao;
import cn.cookie.common.cache.entity.SystemCfg;
import cn.cookie.common.cache.enums.CacheCfgType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by qiancai on 2016/02/29 0029.
 */
@Component
public class LocalCache implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private ThirdServiceCfgDao thirdServiceCfgDao;

    @Resource
    private SystemCfgDao systemCfgDao;

    private HashMap<String,HashMap<String,String>> cfg = null;


    /**
     * 启动后自动加载第三方配置到内存
     */
    public synchronized void initLoaclCache(){
        if(null == cfg){
            cfg = new HashMap<String,HashMap<String,String>>();
            //加载第三方服务配置
            HashMap<String,String> thirdCfg = new HashMap<String,String>();
            List<ThirdServiceCfg> list = thirdServiceCfgDao.getAll();
            if(!CollectionUtils.isEmpty(list)){
                for(Iterator<ThirdServiceCfg> it = list.iterator(); it.hasNext();){
                    ThirdServiceCfg thirdServiceCfg = it.next();
                    thirdCfg.put(thirdServiceCfg.getKey(),thirdServiceCfg.getValue());
                }
                //后面可能还有其它配置大项，故分开
                cfg.put(CacheCfgType.THIRDSERVICECFG.name(),thirdCfg);
            }

            //加载应用系统配置
            HashMap<String,String> systemCfg = new HashMap<String,String>();
            List<SystemCfg> systemCfgList = systemCfgDao.getAll();
            if(!CollectionUtils.isEmpty(systemCfgList)){
                for(Iterator<SystemCfg> it = systemCfgList.iterator(); it.hasNext();){
                    SystemCfg cfg = it.next();
                    systemCfg.put(cfg.getKey(),cfg.getValue());
                }
                //后面可能还有其它配置大项，故分开
                cfg.put(CacheCfgType.SYSTEMCFG.name(),systemCfg);
            }
        }
    }

    /**指定读取模式和读取的类型获取本地缓存
     * @param model
     * @param type
     * @return
     */
    public HashMap<String,String> getLocalCacheCfg(GetCacheModel model, CacheCfgType type, String key){
        if(GetCacheModel.FLUSH == model){
            //同步开启
            synchronized (GetCacheModel.FLUSH){
                if(CacheCfgType.SYSTEMCFG == type){
                    HashMap<String,String> systemCfg = cfg.get(type.name());
                    if(null != systemCfg){
                        SystemCfg systemCfgFind = new SystemCfg();
                        systemCfgFind.setKey(key);
                        SystemCfg systemCfgRs = systemCfgDao.getOne(systemCfgFind);
                        systemCfg.put(key,systemCfgRs.getValue());
                    }
                }else if(CacheCfgType.THIRDSERVICECFG == type){
                    HashMap<String,String> thirdServiceCfg = cfg.get(type.name());
                    if(null != thirdServiceCfg){
                        ThirdServiceCfg thirdServiceCfgFind = new ThirdServiceCfg();
                        thirdServiceCfgFind.setKey(key);
                        ThirdServiceCfg thirdServiceCfgRs = thirdServiceCfgDao.getOne(thirdServiceCfgFind);
                        thirdServiceCfg.put(key,thirdServiceCfgRs.getValue());
                    }
                }
            }
        }
        return  cfg.get(type.name());
    }

    /**查询对应缓存模块的key
     * @param model
     * @param type
     * @param key
     * @return
     */
    public String getLocalCache(GetCacheModel model, CacheCfgType type, String key){
        HashMap<String,String> cfg = getLocalCacheCfg(model,type,key);
        if(null != cfg){
           return cfg.get(key);
        }
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initLoaclCache();
    }
}
