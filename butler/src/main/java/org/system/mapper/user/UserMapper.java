package org.system.mapper.user;

import java.util.List;
import java.util.Map;

import org.main.mapper.IBaseMapper;
import org.system.entity.user.User;

public interface UserMapper extends IBaseMapper<User> {

	List<Map<String, Object>> getRoleList(User user);

	List<Map<String, Object>> getPermissionList(User user);
}