package cn.cookie.framework.convert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.alibaba.fastjson.serializer.SerializeConfig;

import cn.cookie.common.util.ClassPathScanHandler;
import cn.cookie.framework.fastjson.serializer.EnumSerializer;

/**
 * Package Name: cn.upenny.framework.convert
 * Description:
 * Author: qiancai
 * Create Date:2015/12/9
 */
public class FastJsonHttpMessageConverter extends com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter {

    @Override
    protected void writeInternal(Object obj, HttpOutputMessage outputMessage) throws IOException,
            HttpMessageNotWritableException {
        /**
         * FastJson 1.1.42之前的版本反序列化对象时父对象属性无法被设值
         * 但是之后的版本Enum序列化使用的是toString方法
         * 临时解决方法强制使用Enum.name()方法序列化
         */
        List<String> classFilters = new ArrayList<String>();
        ClassPathScanHandler handler = new ClassPathScanHandler(true, true,classFilters);
        Set<Class<?>> calssList = handler.getPackageAllClasses("cn.upenny", true);
        for (Class<?> clazz : calssList) {
            if(clazz.isEnum() || (clazz.getSuperclass() != null && clazz.getSuperclass().isEnum())){
                SerializeConfig.getGlobalInstance().put(clazz,EnumSerializer.instance);
            }
        }
        super.writeInternal(obj, outputMessage);
    }
}
