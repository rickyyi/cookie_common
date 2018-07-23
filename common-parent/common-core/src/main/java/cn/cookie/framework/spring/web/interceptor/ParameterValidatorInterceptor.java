package cn.cookie.framework.spring.web.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.cookie.framework.validator.Validator;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;

import cn.cookie.framework.dto.ResponseEntity;
import cn.cookie.framework.validator.ParameterValidator;
import cn.cookie.framework.validator.ParameterValidators;
import lombok.extern.slf4j.Slf4j;

/**
 * 实现注解{@link cn.upenny.userproduct.web.interceptor#ParameterValidator}定义的行为
 * 
 * @author zhou shengzong
 *
 */
@Slf4j

public class ParameterValidatorInterceptor extends HandlerInterceptorAdapter {
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if(log.isDebugEnabled()){
    		log.debug("enter method");
    	}
		 
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        ParameterValidators validators = method.getAnnotation(ParameterValidators.class);
        if(log.isDebugEnabled()){
    		log.debug("got validators: {}", validators);
    	}
        
        if(null != validators){
        	ResponseEntity respEntity = new ResponseEntity();
        	for(ParameterValidator validator : validators.value()){
        		boolean flag = true; // validate result
            	String field = validator.field();
            	String ruleKey = validator.ruleKey();
            	
            	if(! StringUtils.isEmpty(field)){
            		String value = request.getParameter(field);
    	        	if(! StringUtils.isEmpty(ruleKey)){
    	        		flag = Validator.getInstance().test(ruleKey, value, respEntity);
    	        		if(log.isDebugEnabled()){
    	        			log.debug("validator check result:{}, field:{}, value:{}, ruleKey:{}, rule:{}", new Object[]{flag, field, value, ruleKey, respEntity.getData()});
    	        		}
    	        	}
            	}
	            
	            if(! flag){
	            	respEntity.setData(null);
	            	response.getWriter().print(JSON.toJSONString(respEntity));

	            	return false;
	            }
            }
        }

		if(log.isDebugEnabled()){
			log.debug("exit method, return true");
		}

        return true;
    }
}
