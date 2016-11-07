package org.system.encrypt;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.tools.des.DESUtil;

/**
 * @ClassName DBConfigurer
 * @author <font color="red"><b>LiuGangQiang</b></font>
 * @date 2016年2月17日 下午4:04:32
 * @version 1.0
 * @description 数据配置文件加密生成类
 */
public class DBConfigurer extends PropertyPlaceholderConfigurer {

	private final static String key = "scyxtyzg";

	private final static String URL = "db.url";

	private final static String USER = "db.user";

	private final static String PASSWORD = "db.password";

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props)
			throws BeansException {

		String url = props.getProperty(URL);
		if (url != null)
			try {
				props.setProperty(URL, DESUtil.decrypt(url, key));
			} catch (Exception e) {
				e.printStackTrace();
			}
		String user = props.getProperty(USER);
		if (user != null)
			try {
				props.setProperty(USER, DESUtil.decrypt(user, key));
			} catch (Exception e) {
				e.printStackTrace();
			}

		String password = props.getProperty(PASSWORD);
		if (password != null)
			try {
				props.setProperty(PASSWORD, DESUtil.decrypt(password, key));
			} catch (Exception e) {
				e.printStackTrace();
			}
		super.processProperties(beanFactory, props);
	}

	public static String getKey() {
		return key;
	}
	
}
