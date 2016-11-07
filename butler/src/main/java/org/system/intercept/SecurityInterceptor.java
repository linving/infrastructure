package org.system.intercept;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.main.annotation.RequiresPermissions;
import org.main.annotation.RequiresRoles;
import org.main.result.ResultCode;
import org.main.result.ResultMap;
import org.redis.cache.RedisCacheManager;
import org.redis.cache.authentication.AuthenticationSession;
import org.redis.cache.authentication.AuthenticationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.system.Global;
import org.system.entity.user.User;
import org.system.service.iface.user.IUserService;

import com.alibaba.fastjson.JSON;

/**
 * @author <font color="red"><b>Liu.Gang.Qiang</b></font>
 * @Date 2016年11月4日
 * @Version 1.0
 * @Description 权限拦截器 先判断角色后判断权限 角色>权限
 */
public class SecurityInterceptor extends HandlerInterceptorAdapter {
	Logger log = LoggerFactory.getLogger(SecurityInterceptor.class);
	private final String cacheName = Global.CACHE_USER;

	@Resource
	private RedisCacheManager cache;
	@Resource
	private IUserService userService;

	/**
	 * @author <font color="green"><b>Liu.Gang.Qiang</b></font>
	 * @param list
	 * @param auths
	 * @return {@link Boolean}
	 * @date 2016年11月4日
	 * @version 1.0
	 * @description 校验权限是否匹配
	 */
	private boolean validate(List<Map<String, Object>> list, String[] auths) {
		for (String auth : auths) {
			for (Map<String, Object> map : list) {
				if (auth.equals(map.get("value"))) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (handler instanceof HandlerMethod) {
			/* 获取认证token */
			String token = request.getHeader("token");

			/* 获取方法 */
			HandlerMethod method = (HandlerMethod) handler;

			/* 获取注解 包含角色注解和权限注解 */
			RequiresRoles roles = method.getMethodAnnotation(RequiresRoles.class);
			RequiresPermissions permissions = method.getMethodAnnotation(RequiresPermissions.class);

			/* 实例化用户对象 缓存对象 */
			AuthenticationSession session = null;
			User user = new User();
			/* 设置响应头为Json */
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			if (token != null && token != "") {
				/* 判断token是否符合系统定义规则 */
				if (token.length() <= 32) {
					/* token格式错误 */
					response.getWriter().write(JSON.toJSONString(ResultMap.convertMap(ResultCode.UNSIGNATURE)));
					return false;
				}
				/* 获取缓存 */
				session = cache.getSession(new AuthenticationToken(cacheName, token));
				if (session != null) {
					/* 获取用户唯一标识 这里默认采用用户的主键ID */
					Integer userId = (Integer) session.get(Map.class).get("id");
					/* 设置用户对象 ID属性 */
					user.setId(userId);
				}
			}

			if (roles != null) {
				/* 方法角色注解不为空 获取用户角色集合 */
				List<Map<String, Object>> roleList = userService.getRoleList(user);
				if (validate(roleList, roles.value())) {
					return true;
				} else {
					if (session == null) {
						/* 用户缓存信息不存在则表示未登录，可以提示用户登录后重试 */
						response.getWriter().write(JSON.toJSONString(ResultMap.convertMap(ResultCode.UNSIGNATURE)));
					} else {
						/* 用户缓存信息存在则表示无权限，直接提示无权限操作 */
						response.getWriter().write(JSON.toJSONString(ResultMap.convertMap(ResultCode.UNAUTHORIZED)));
					}
					return false;
				}
			} else if (permissions != null) {
				/* 方法权限注解不为空 获取用户权限集合 */
				List<Map<String, Object>> roleList = userService.getPermissionList(user);
				if (validate(roleList, permissions.value())) {
					return true;
				} else {
					if (session == null) {
						/* 用户缓存信息不存在则表示未登录，可以提示用户登录后重试 */
						response.getWriter().write(JSON.toJSONString(ResultMap.convertMap(ResultCode.UNSIGNATURE)));
					} else {
						/* 用户缓存信息存在则表示无权限，直接提示无权限操作 */
						response.getWriter().write(JSON.toJSONString(ResultMap.convertMap(ResultCode.UNAUTHORIZED)));
					}
					return false;
				}
			} else {
				/* 方法未定义安全访问提示此方法不可访问 */
				response.getWriter().write(JSON.toJSONString(ResultMap.convertMap(ResultCode.SYSTEM_DEFINE)));
				return false;
			}
		}
		return true;
	}
}
