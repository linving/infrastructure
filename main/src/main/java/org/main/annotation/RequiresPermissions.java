package org.main.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author <font color="red"><b>Liu.Gang.Qiang</b></font>
 * @Date 2016年10月28日
 * @Version 1.0
 * @Description 类似于shiro的权限注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequiresPermissions {
	/**
	 * @author <font color="green"><b>Liu.Gang.Qiang</b></font>
	 * @return {@link String[]}
	 * @date 2016年10月28日
	 * @version 1.0
	 * @description 权限描述值
	 */
	String[] value();
}
