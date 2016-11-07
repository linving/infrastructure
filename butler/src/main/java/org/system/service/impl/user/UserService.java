package org.system.service.impl.user;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.system.Global;
import org.system.entity.user.User;
import org.system.mapper.user.UserMapper;
import org.system.service.iface.user.IUserService;

@Service
public class UserService implements IUserService {
	@Resource
	private UserMapper userMapper;

	@Override
	@Cacheable(value = Global.CACHE_PERMISSION, key = "'user:role:'+#user.id", condition = "#user.id ne null", unless = "#result eq null")
	public List<Map<String, Object>> getRoleList(User user) {
		return userMapper.getRoleList(user);
	}

	@Override
	@Cacheable(value = Global.CACHE_PERMISSION, key = "'user:permission:'+#user.id", condition = "#user.id ne null", unless = "#result eq null")
	public List<Map<String, Object>> getPermissionList(User user) {
		return userMapper.getPermissionList(user);
	}

	@Override
	@Cacheable(value = Global.CACHE_USER, key = "#user.phone", condition = "#user.phone ne null", unless = "#result eq null")
	public Map<String, Object> getUser(User user) {
		return userMapper.selectOne(user);
	}

	@Override
	public int insertUser(User user) {
		return userMapper.insert(user);
	}

	@Override
	public List<Map<String, Object>> getUserList(User user) {
		return userMapper.selectAll(user);
	}

	@Override
	public Map<String, Object> selectExist(User user) {
		return userMapper.selectIsExists(user);
	}
}
