package icu.baolong.social.module.friend.adapter;

import cn.hutool.core.util.ObjectUtil;
import icu.baolong.social.module.friend.domain.response.FriendResp;
import icu.baolong.social.repository.friend.entity.Friend;
import icu.baolong.social.repository.user.entity.User;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 朋友适配器
 *
 * @author Silas Yan 2025-05-31 02:34
 */
public class FriendAdapter {

	/**
	 * 构建朋友响应对象列表
	 *
	 * @param friendList 朋友列表
	 * @param userList   用户列表
	 * @return 朋友响应对象列表
	 */
	public static List<FriendResp> buildFriendRespList(List<Friend> friendList, List<User> userList) {
		Map<Long, User> userMap = userList.stream().collect(Collectors.toMap(User::getUserId, user -> user));
		return friendList.stream().map(friend -> {
			User user = userMap.get(friend.getFriendId());
			if (ObjectUtil.isNotNull(user)) {
				return FriendResp.builder()
						.userId(friend.getFriendId())
						.userName(user.getUserName())
						.userAvatar(user.getUserAvatar())
						.onlineStatus(user.getOnlineStatus())
						.build();
			}
			return null;
		}).filter(ObjectUtil::isNotNull).toList();
	}
}
