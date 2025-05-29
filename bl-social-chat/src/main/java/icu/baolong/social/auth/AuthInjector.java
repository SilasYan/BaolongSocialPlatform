package icu.baolong.social.auth;

import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.json.JSONUtil;
import icu.baolong.social.auth.dto.AuthDTO;
import icu.baolong.social.cache.AuthCache;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限注入器
 *
 * @author Silas Yan 2025-05-29 21:01
 */
@Slf4j
@Component
public class AuthInjector implements StpInterface {

	@Resource
	private AuthCache authCache;

	/**
	 * 返回指定账号id所拥有的权限码集合
	 *
	 * @param loginId   账号id
	 * @param loginType 账号类型
	 * @return 该账号id具有的权限码集合
	 */
	@Override
	public List<String> getPermissionList(Object loginId, String loginType) {
		AuthDTO userAuth = authCache.getUserAuth(Long.parseLong(loginId.toString()));
		log.info("[权限注入器]权限: {}", JSONUtil.toJsonStr(userAuth));
		return new ArrayList<>(userAuth.getPermSignList());
	}

	/**
	 * 返回指定账号id所拥有的角色标识集合
	 *
	 * @param loginId   账号id
	 * @param loginType 账号类型
	 * @return 该账号id具有的角色标识集合
	 */
	@Override
	public List<String> getRoleList(Object loginId, String loginType) {
		AuthDTO userAuth = authCache.getUserAuth(Long.parseLong(loginId.toString()));
		log.info("[权限注入器]角色: {}", JSONUtil.toJsonStr(userAuth));
		return new ArrayList<>(userAuth.getRoleSignList());
	}
}
