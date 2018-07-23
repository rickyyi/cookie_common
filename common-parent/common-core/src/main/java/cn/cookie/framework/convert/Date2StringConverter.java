package cn.cookie.framework.convert;

import java.util.Date;

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
public class Date2StringConverter implements Converter<Date,String>{
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public String convert(Date source) {
		logger.debug("=====================Date2StringConverter InIt===============");
		if(source==null){
			return null;
		}
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//	    dateFormat.setLenient(false);
	    return DateUtils.convert(source,DateUtils.DATE_TIME_FORMAT);
	}

}
