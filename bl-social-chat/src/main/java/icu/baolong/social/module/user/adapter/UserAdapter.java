package icu.baolong.social.module.user.adapter;

import cn.hutool.core.util.ObjectUtil;
import icu.baolong.social.common.enums.BoolEnum;
import icu.baolong.social.module.user.domain.enums.UserSexEnum;
import icu.baolong.social.module.user.domain.response.UserBadgeResp;
import icu.baolong.social.module.user.domain.response.UserInfoResp;
import icu.baolong.social.repository.items.entity.Items;
import icu.baolong.social.repository.user.entity.User;
import icu.baolong.social.repository.user.entity.UserBackpack;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户适配器
 *
 * @author Silas Yan 2025-05-24 19:17
 */
public class UserAdapter {

	public static UserInfoResp buildUserInfoResp(User user) {
		return UserInfoResp.builder()
				.userId(user.getUserId())
				.userAccount(user.getUserAccount())
				.userEmail(user.getUserEmail())
				.userPhone(user.getUserPhone())
				.userName(user.getUserName())
				.userAvatar(user.getUserAvatar())
				.userProfile(user.getUserProfile())
				.userSex(user.getUserSex())
				.wxOpenId(user.getWxOpenId())
				.shareCode(user.getShareCode())
				.build();
	}

	/**
	 * 生成默认用户
	 *
	 * @param userEmail         用户邮箱
	 * @param userPassword      用户密码（已经加密的密码）
	 * @param userAccountPrefix 用户账号前缀
	 * @return 用户对象
	 */
	public static User buildDefaultUser(String userEmail, String userPassword, String userAccountPrefix) {
		User user = new User();
		user.setUserEmail(userEmail).setUserPassword(userPassword);
		String account = userAccountPrefix + user.getUserEmail().split("@")[0];
		user.setUserAccount(account).setUserName(account)
				.setUserSex(UserSexEnum.OTHER.getKey()).setUserProfile("用户暂未填写个人简介~");
		return user;
	}

	/**
	 * 生成微信用户
	 *
	 * @param wxOpenId     微信公众号OpenId
	 * @param userPassword 用户密码（已经加密的密码）
	 * @return 用户对象
	 */
	public static User buildUserByWxOpenId(String wxOpenId, String userPassword) {
		User user = new User();
		user.setWxOpenId(wxOpenId).setUserPassword(userPassword)
				.setUserSex(UserSexEnum.OTHER.getKey()).setUserProfile("用户暂未填写个人简介~");
		return user;
	}

	/**
	 * 生成用户徽章列表
	 *
	 * @param user          用户对象
	 * @param badgeList     所有徽章列表
	 * @param userBackpacks 拥有的徽章列表
	 * @return 用户徽章列表
	 */
	public static List<UserBadgeResp> buildUserBadgeRespList(User user, List<Items> badgeList, List<UserBackpack> userBackpacks) {
		if (ObjectUtil.isNull(user)) return List.of();
		Map<Long, UserBackpack> ownBadgeMap = userBackpacks.stream().collect(Collectors.toMap(UserBackpack::getItemId, ub -> ub));
		Set<Long> ownBadgeIds = userBackpacks.stream().map(UserBackpack::getItemId).collect(Collectors.toSet());
		return badgeList.stream()
				.map(badge -> UserBadgeResp.builder()
						.badgeId(badge.getItemId())
						.badgeName(badge.getItemName())
						.badgeDesc(badge.getItemDesc())
						.badgeImage(badge.getItemImage())
						.obtainTime(ownBadgeMap.get(badge.getItemId()) != null ? ownBadgeMap.get(badge.getItemId()).getCreateTime() : null)
						.ownStatus(BoolEnum.is(ownBadgeIds.contains(badge.getItemId())))
						.wearStatus(BoolEnum.is(Objects.equals(user.getBadgeId(), badge.getItemId())))
						.build())
				.sorted(Comparator.comparing(UserBadgeResp::getWearStatus, Comparator.reverseOrder())
						.thenComparing(UserBadgeResp::getOwnStatus, Comparator.reverseOrder()))
				.toList();
	}
}
