package org.system.message;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * <font color="green">提示资源绑定</font>
 * 
 * @ClassName BaseMessage
 * @author GangQiang
 * @date 2015年4月21日 上午11:05:41
 * 
 * @version 1.0
 */
public class Message
{
    private static String filePath = "org.system.message";
    
    /**
     * <font color="red">无占位符的字符资源</font>
     * 
     * @Title bundle
     * @param key
     * @return {@linkplain String}
     * @since 1.0
     */
    public static String bundle(String key)
    {
        return ResourceBundle.getBundle(filePath).getString(key);
    }
    
    /**
     * <font color="red">有占位符的字符资源</font>
     * 
     * @Title bundle
     * @param key
     * @param arguments
     * @return {@linkplain String}
     * @since 1.0
     */
    public static String bundle(String key, Object... arguments)
    {
        return MessageFormat.format(ResourceBundle.getBundle(filePath).getString(key), arguments);
    }
}
