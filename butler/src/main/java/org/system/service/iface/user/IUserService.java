package org.system.service.iface.user;

import java.util.List;
import java.util.Map;

import org.system.entity.user.User;

public interface IUserService {
	/**
	 * @author <font color="green"><b>Liu.Gang.Qiang</b></font>
	 * @param user
	 * @return {@link List}
	 * @date 2016年11月3日
	 * @version 1.0
	 * @description 获取用户角色集合
	 */
	List<Map<String, Object>> getRoleList(User user);

	/**
	 * @author <font color="green"><b>Liu.Gang.Qiang</b></font>
	 * @param user
	 * @return {@link List}
	 * @date 2016年11月3日
	 * @version 1.0
	 * @description 获取用户权限集合
	 */
	List<Map<String, Object>> getPermissionList(User user);

	Map<String, Object> getUser(User user);

	int insertUser(User user);

	List<Map<String, Object>> getUserList(User user);

	Map<String, Object> selectExist(User user);

}
