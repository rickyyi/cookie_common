package cn.cookie.common.util;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class PhoneUtils {
	public static String cleanMobile(String str){
		if(null == str){
			return null;
		}
		
		str = str.trim();
		str = str.replaceAll("[^\\d]", "");
		
		if(str.length() >= 13){
			str = str.replaceAll("^86", "");
		}
		if(str.length() >= 16){
			str = str.replaceAll("^(17950|17951|12593|17911|17901|17909)", "");
		}
		
		return str;
	}
	
	public static boolean isMobile(String str){
		if(null == str || str.isEmpty()){
			return false;
		}
		
		str = str.trim();
		
		return str.matches("^(?:134|135|136|137|138|139|147|150|151|152|157|158|159|187|188|130|131|132|155|156|185|186|133|153|180|181|182|183|184|189|170|177)\\d{8}");
	}

	public static String cleanTel(String str){
		if(null == str){
			return null;
		}
		
		str = str.trim();
		str = str.replaceAll("[^\\d]", "");
		
		return str;
	}
	
	/**
	 * clean first, then compare 
	 * @param phone1
	 * @param phone2
	 * @return
	 */
	public static boolean isMobileEqual(String phone1, String phone2){
		phone1 = (null == phone1) ? "" : phone1;
		phone2 = (null == phone2) ? "" : phone2;
		
		phone1 = cleanMobile(phone1);
		phone2 = cleanMobile(phone2);
		
		return phone1.equals(phone2);
	}

	/**根据号码和字典判断属于哪个运营商
	 * @param phone
	 * @param dict key {chinamobile,chinaunicome,chinatelecome} value{以| 分割的字符串}
	 * @return 1:中国移动，2：中国联通，3：中国电信
	 */
	public static int judgePhoneType(String phone, Map<String,String> dict){
		int type = 0;
		//先判读是否是移动
		String chinaMobileStr = dict.get("chinamobile");
		if (StringUtils.isNotBlank(chinaMobileStr)){
			String[] chinaMobiles = chinaMobileStr.split("\\|");
			if (ArrayUtils.isNotEmpty(chinaMobiles)){
				for (String index : chinaMobiles){
					if (phone.startsWith(index)){
						return 1;
					}
				}
			}
		}

		String chinaUnicomeStr = dict.get("chinaunicome");
		if (StringUtils.isNotBlank(chinaUnicomeStr)){
			String[] chinaUnicoms = chinaUnicomeStr.split("\\|");
			if (ArrayUtils.isNotEmpty(chinaUnicoms)){
				for (String index : chinaUnicoms){
					if (phone.startsWith(index)){
						return 2;
					}
				}
			}
		}

		String chinaTelecomStr = dict.get("chinatelecom");
		if (StringUtils.isNotBlank(chinaTelecomStr)){
			String[] chinatelecoms = chinaTelecomStr.split("\\|");
			if (ArrayUtils.isNotEmpty(chinatelecoms)){
				for (String index : chinatelecoms){
					if (phone.startsWith(index)){
						return 3;
					}
				}
			}
		}
		return 0;
	}
}
