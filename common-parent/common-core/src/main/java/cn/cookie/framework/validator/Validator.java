package cn.cookie.framework.validator;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;

import cn.cookie.common.exception.ErrorCode;
import cn.cookie.framework.dto.ResponseEntity;

/**
 * <br/> classpath中放入配置文件：validator.properties
 * <br/> 文件内容：$key=$regexp, 如：
 * <br/> mobile=^1\\d{10}$
 * <br/> idcard=^[1-9]\\d{16}[X0-9]$
 * <br/> 用法： Validator.getInstance().test(key, value)。
 * 
 * @author zhou shengzong 
 *
 */
public class Validator {
	private static final int ELEMENT_LENGTH = 9;
	private static final Map<String, ValidatorRule> REGEXP_RULE_MAP = new HashMap<String, ValidatorRule>();
	private static final Validator INSTANCE = new Validator();
	private static final Map<String, IValidator> VALIDATOR_INSTANCE = new HashMap<String, IValidator>(); // className => class instance 
	
	private class ValidatorRule {
		/**
		 * 是否可以为空(null或空字符串)
		 * @return
		 */
		public boolean empty;
		
		/**
		 * 最小值(验证整数，为空时不做检查)
		 * @return
		 */
		public String min;
		
		/**
		 * 最大值(验证整数，为空时不做检查)
		 * @return
		 */
		public String max;
		
		/**
		 * 最小长度(验证字符串，为空时不做检查)
		 * @return
		 */
		public String minLength;
		
		/**
		 * 最大长度(验证字符串，为空时不做检查)
		 * @return
		 */
		public String maxLength;
		
		/**
		 * regexp(正则验证规则，为空时不做检查)
		 * @return
		 */
		public String regexp;
		
		public String validatorClass;
		
		public String errorCode;
		
		public String errorMsg;

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + (empty ? 1231 : 1237);
			result = prime * result + ((errorCode == null) ? 0 : errorCode.hashCode());
			result = prime * result + ((errorMsg == null) ? 0 : errorMsg.hashCode());
			result = prime * result + ((max == null) ? 0 : max.hashCode());
			result = prime * result + ((maxLength == null) ? 0 : maxLength.hashCode());
			result = prime * result + ((min == null) ? 0 : min.hashCode());
			result = prime * result + ((minLength == null) ? 0 : minLength.hashCode());
			result = prime * result + ((regexp == null) ? 0 : regexp.hashCode());
			result = prime * result + ((validatorClass == null) ? 0 : validatorClass.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ValidatorRule other = (ValidatorRule) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (empty != other.empty)
				return false;
			if (errorCode == null) {
				if (other.errorCode != null)
					return false;
			} else if (!errorCode.equals(other.errorCode))
				return false;
			if (errorMsg == null) {
				if (other.errorMsg != null)
					return false;
			} else if (!errorMsg.equals(other.errorMsg))
				return false;
			if (max == null) {
				if (other.max != null)
					return false;
			} else if (!max.equals(other.max))
				return false;
			if (maxLength == null) {
				if (other.maxLength != null)
					return false;
			} else if (!maxLength.equals(other.maxLength))
				return false;
			if (min == null) {
				if (other.min != null)
					return false;
			} else if (!min.equals(other.min))
				return false;
			if (minLength == null) {
				if (other.minLength != null)
					return false;
			} else if (!minLength.equals(other.minLength))
				return false;
			if (regexp == null) {
				if (other.regexp != null)
					return false;
			} else if (!regexp.equals(other.regexp))
				return false;
			if (validatorClass == null) {
				if (other.validatorClass != null)
					return false;
			} else if (!validatorClass.equals(other.validatorClass))
				return false;
			return true;
		}

		private Validator getOuterType() {
			return Validator.this;
		}

		public String toString(){
			return JSON.toJSONString(this);
		}
	}
	
	private Validator() {
		String file = "/cn.upenny.validator.properties";
		InputStream in = this.getClass().getResourceAsStream(file);
		//System.out.println("in:" + in);
		Properties prop = new Properties();
		try {
			prop.load(in);
			Set<Entry<Object, Object>> set = prop.entrySet();
			for (Entry<Object, Object> item : set) {
				String value = (String) item.getValue(); // password=^.{6,20}$`E03010003`
				value = value.trim();
				String[] arr = value.split("`", -1); // 保留最后的空值
				if(ELEMENT_LENGTH != arr.length){
					throw new RuntimeException("validator config error, expect element length: " + ELEMENT_LENGTH + ", given:" + arr.length + ", item key:" + item.getKey() + ", file: " + file);
				}
				
				ValidatorRule rule = new ValidatorRule();
				rule.empty = (! StringUtils.isEmpty(arr[0]) && 1 == Integer.parseInt(arr[0]));
				rule.min = arr[1];
				rule.max = arr[2];
				rule.minLength = arr[3];
				rule.maxLength = arr[4];
				rule.regexp = arr[5];
				rule.validatorClass = arr[6];
				rule.errorCode = arr[7];
				rule.errorMsg = arr[8];
				
				REGEXP_RULE_MAP.put((String) item.getKey(), rule);
			}
			
			System.out.println("REGEXP_RULE_MAP:" + REGEXP_RULE_MAP);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	
	public static final Validator getInstance(){
		return INSTANCE;
	}

	/**
	 * 仅验证正则表达式
	 * @param ruleKey ruleKey defined in validator properties
	 * @param value value for tested
	 * @return
	 */
	public boolean test(String ruleKey, String value){
		if(null == ruleKey){
			return true;
		}
		
		if(null == value){
			return false;
		}
		
		ValidatorRule rule = REGEXP_RULE_MAP.get(ruleKey);
		
		return (rule == null || null == rule.regexp) ? true : value.matches(rule.regexp);
	}
	
	public String getErrorCode(String ruleKey){
		ValidatorRule rule = REGEXP_RULE_MAP.get(ruleKey);
		String errorCode = (null == rule || StringUtils.isEmpty(rule.errorCode)) ? ErrorCode.DEFAULT_FAIL_CODE : rule.errorCode;
		
		return errorCode;
	}
	
	public String getErrorMsg(String ruleKey){
		ValidatorRule rule = REGEXP_RULE_MAP.get(ruleKey);
		String errorMsg = (null == rule || StringUtils.isEmpty(rule.errorMsg)) ? ErrorCode.getMsg(this.getErrorCode(ruleKey)) : rule.errorMsg;
		
		return errorMsg;
	}
	
	public boolean test(String ruleKey, String value, ResponseEntity entity){
		boolean ret = true;
		ValidatorRule rule  = StringUtils.isEmpty(ruleKey) ? null : REGEXP_RULE_MAP.get(ruleKey);
		if(rule != null){
			boolean empty = rule.empty;
			if(empty && StringUtils.isEmpty(value)){
				ret = true;
			}else{
	        	if(ret && ! empty){
	        		if(StringUtils.isEmpty(value)){
	        			ret = false;
	        		}
	        	}
	        	
	        	String min = rule.min;
	        	if(ret && ! StringUtils.isEmpty(min)){
	        		try{
		        		long minLong = Long.parseLong(min);
		        		long valueLong = Long.parseLong(value);
		        		if(valueLong < minLong){
		        			ret = false;
		        		}
	        		}catch(Exception ex){
	        			ex.printStackTrace();
	        			ret = false;
	        		}
	        	}
	        	
	        	String max = rule.max;
	        	if(ret && ! StringUtils.isEmpty(max)){
	        		try{
		        		long maxLong = Long.parseLong(max);
		        		long valueLong = Long.parseLong(value);
		        		if(valueLong > maxLong){
		        			ret = false;
		        		}
	        		}catch(Exception ex){
	        			ex.printStackTrace();
	        			ret = false;
	        		}
	        	}
	        	
	        	String minLength = rule.minLength;
	        	if(ret && ! StringUtils.isEmpty(minLength)){
	        		long minLengthLong = Long.parseLong(minLength);
	        		if(minLengthLong > 0){
	        			if(StringUtils.isEmpty(value)){
	        				ret = false;
	        			}else{
	        				long valueLength = value.length();
	        				if(valueLength < minLengthLong){
	        					ret = false;
	        				}
	        			}
	        		}
	        	}
	        	
	        	String maxLength = rule.maxLength;
	        	if(ret && ! StringUtils.isEmpty(maxLength)){
	        		long maxLengthLong = Long.parseLong(maxLength);
	        		if(maxLengthLong < 1){
	        			if(! StringUtils.isEmpty(value)){
	        				ret = false;
	        			}else{
	        				long valueLength = value.length();
	        				if(valueLength > maxLengthLong){
	        					ret = false;
	        				}
	        			}
	        		}
	        	}
	        	
				String regexp = rule.regexp;
	        	if(ret && ! StringUtils.isEmpty(regexp)){
	        		ret = value.matches(regexp);
	        	}
	        	
	        	String validatorClass = rule.validatorClass;
	        	if(ret && ! StringUtils.isEmpty(validatorClass)){
	        		IValidator v = null;
	        		if(VALIDATOR_INSTANCE.containsKey(validatorClass)){
	        			v = VALIDATOR_INSTANCE.get(validatorClass);
	        		}else{
	        			try {
							v = (IValidator) Class.forName(validatorClass).newInstance();
						} catch (InstantiationException | IllegalAccessException | ClassNotFoundException ex) {
							throw new RuntimeException("validate exception", ex);
						}
	        			VALIDATOR_INSTANCE.put(validatorClass, v);
	        		}

	        		ret = v.test(value);
	        	}
				
				if(null != entity){
					entity.setData(rule);
					if(! ret){
						entity.setStatus(ResponseEntity.STATUS_FAIL);
						
						String errorCode = this.getErrorCode(ruleKey);
						String errorMsg = this.getErrorMsg(ruleKey);
						entity.setError(errorCode);
						entity.setMsg(errorMsg);
					}
				}
			}
		}
		
		if(ret && null != entity){
			entity.setStatus(ResponseEntity.STATUS_OK);
			entity.setError(ErrorCode.DEFAULT_OK_CODE);
			entity.setMsg(ErrorCode.getMsg(ErrorCode.DEFAULT_OK_CODE));
		}
		
		return ret;
	}
}
