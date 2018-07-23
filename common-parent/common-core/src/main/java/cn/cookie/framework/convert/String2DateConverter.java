package cn.cookie.framework.convert;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import cn.cookie.common.util.DateUtils;

/**
 *
 *
 * 类名称：Date2StringConverter
 * 类描述：
 * 创建人：chenbo
 * 修改人：qiancai
 * 修改时间：2015年12月12日 下午4:49:20
 * 修改备注：
 * @version 1.0.0
 *
 */
public class String2DateConverter implements Converter<String, Date>{
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public Date convert(String source) {
		logger.debug("=====================String2DateConverter InIt===============");
		if(StringUtils.isBlank(source)){
			return null;
		}
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//	    dateFormat.setLenient(false);
//	    try {
        return  DateUtils.convert(source);
//        } catch (ParseException e) {
//	    	logger.error("String2DateConverter Error",e);
//	    }
//	    return null;
	}

}
