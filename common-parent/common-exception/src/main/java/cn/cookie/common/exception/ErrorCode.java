package cn.cookie.common.exception;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

/**
 * 
 * 请使用classpath下的cn.upenny.error.properties配置文件定义错误码, 使用getMsg获取消息
 * 
 * @author zhou shengzong
 * 
 */
public class ErrorCode implements Serializable {
	private static final long serialVersionUID = -8786587857867857901L;
	public static final String DEFAULT_OK_CODE = "E00000000";
	public static final String DEFAULT_FAIL_CODE = "E00090000";
	
	private static Properties ERROR_CODE = new Properties();

	// load error code
	static {
		InputStream in = ErrorCode.class.getResourceAsStream("/cn.upenny.error.properties");
		try {
			ERROR_CODE.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getMsg(String errorCode) {
		if (null == errorCode || errorCode.isEmpty()) {
			return "";
		}

		return ERROR_CODE.getProperty(errorCode, "");
	}
	
	public static void main(String[] arg){
		String code = "E03010001";
		System.out.println(code + ":" + getMsg(code));
		System.out.println(Long.MIN_VALUE + ":" + Long.MAX_VALUE);
		
		String a = ",,a,b,c,,,,";
		String[] arr = a.split(",", -1);
		System.out.println(Arrays.toString(arr));
		
		TreeMap sort = new TreeMap();
		sort.putAll(ERROR_CODE);
		Set<Entry<Object, Object>> set = sort.entrySet();
		for(Entry entry : set){
			System.out.println(entry.getKey() + "," + entry.getValue());
		}
	}
}
