package cn.cookie.framework.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ParameterValidators.class)
/**
 * 标识控制器方法，检查参数合法性。可以出现多次，按顺序验证, 验证不通过的中止后续验证。
 * @author zhou shengzong
 *
 */
public @interface ParameterValidator {
	/**
	 * 参数名
	 * @return
	 */
	public String field() default "";
	
	/**
	 * 正则表达式验证，cn.upenny.validator.properties文件中到key
	 * @return
	 */
	public String ruleKey() default "";
}
