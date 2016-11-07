package org.system.controller.impl.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.main.result.ResultCode;
import org.main.result.ResultMap;
import org.redis.cache.RedisCacheManager;
import org.redis.cache.authentication.AuthenticationSession;
import org.redis.cache.authentication.AuthenticationToken;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.system.Global;
import org.system.controller.iface.user.IUserController;
import org.system.entity.user.User;
import org.system.service.iface.user.IUserService;
import org.tools.md5.MD5Util;

@Controller
public class UserController implements IUserController {
	// private Logger log = LoggerFactory.getLogger(UserController.class);
	@Resource
	private IUserService userService;
	@Resource
	private JavaMailSenderImpl mailSender;
	@Resource
	private RedisCacheManager cacheManager;

	@Override
	public Map<String, Object> login(User user, BindingResult result) {
		Map<String, Object> userMap = userService.getUser(user);
		Map<String, Object> resultMap = new HashMap<>();
		if (userMap != null && userMap.size() > 0) {
			/* 生成唯一的Token */
			String token = MD5Util.getInstance().getSessionToken(userMap.get("id"));
			/* 存入缓存 已实现自动踢出 */
			cacheManager.AuthenticationToken(new AuthenticationToken(Global.CACHE_USER, userMap.get("id").toString(),
					new AuthenticationSession(token, userMap)));
			resultMap.put("token", token);
			return ResultMap.convertMap(ResultCode.SUCCESS, resultMap);
		}
		return ResultMap.convertMap(ResultCode.FAIL);
	}

	@Override
	public Map<String, Object> insertUser(User user, BindingResult result) {
		if (userService.selectExist(user) != null) {
			return ResultMap.convertMap(ResultCode.DATA_EXIST);
		}
		if (userService.insertUser(user) > 0) {
			return ResultMap.convertMap(ResultCode.SUCCESS);
		}
		return ResultMap.convertMap(ResultCode.FAIL);
	}

	@Override
	public Map<String, Object> getUserList(User user, BindingResult result, String token) {
		List<Map<String, Object>> userList = userService.getUserList(user);
		if (userList != null && userList.size() > 0) {
			return ResultMap.convertMap(ResultCode.SUCCESS, user.getTotal(), userList);
		}
		return ResultMap.convertMap(ResultCode.DATA_UNEXIST);
	}
}
