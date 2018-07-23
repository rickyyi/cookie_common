package cn.cookie.common.util;

import org.apache.commons.beanutils.Converter;

/**
 * Created by qiancai on 2015/12/7.
 */
public class BigDecimalConvert implements Converter {

    @Override
    public Object convert(Class type, Object value) {
        if(value==null){
            return null;
        }
        return value;
    }
}
