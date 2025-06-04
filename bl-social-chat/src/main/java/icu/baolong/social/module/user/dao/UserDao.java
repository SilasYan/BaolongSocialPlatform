package icu.baolong.social.module.user.dao;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.baolong.social.base.page.PageRequest;
import icu.baolong.social.common.utils.LambdaUtil;
import icu.baolong.social.repository.user.entity.User;
import icu.baolong.social.repository.user.mapper.UserMapper;
import icu.baolong.social.module.user.domain.request.UserQueryReq;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 用户表 (user) - 持久化服务
 *
 * @author Baolong 2025-05-21 21:01:07
 */
@Repository
public class UserDao extends ServiceImpl<UserMapper, User> {

	/**
	 * 获取查询条件构造器
	 *
	 * @return 查询条件构造器
	 */
	public LambdaQueryWrapper<User> lambdaQueryWrapper() {
		return new LambdaQueryWrapper<>();
	}

	/**
	 * 获取查询条件构造器
	 *
	 * @param request 查询请求对象
	 * @return 查询条件构造器
	 */
	@SneakyThrows
	public LambdaQueryWrapper<User> lambdaQueryWrapper(UserQueryReq request) {
		Long id = request.getUserId();
		String userAccount = request.getUserAccount();
		String userEmail = request.getUserEmail();
		String userPhone = request.getUserPhone();
		String userName = request.getUserName();
		String userProfile = request.getUserProfile();
		String wxOpenId = request.getWxOpenId();
		String shareCode = request.getShareCode();
		Integer isDisabled = request.getIsDisabled();
		LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.eq(ObjUtil.isNotNull(id), User::getUserId, id);
		lambdaQueryWrapper.eq(StrUtil.isNotEmpty(userAccount), User::getUserAccount, userAccount);
		lambdaQueryWrapper.eq(StrUtil.isNotEmpty(userEmail), User::getUserEmail, userEmail);
		lambdaQueryWrapper.eq(StrUtil.isNotEmpty(userPhone), User::getUserPhone, userPhone);
		lambdaQueryWrapper.eq(StrUtil.isNotEmpty(userName), User::getUserName, userName);
		lambdaQueryWrapper.like(StrUtil.isNotEmpty(userProfile), User::getUserProfile, userProfile);
		lambdaQueryWrapper.eq(StrUtil.isNotEmpty(wxOpenId), User::getWxOpenId, wxOpenId);
		lambdaQueryWrapper.eq(StrUtil.isNotEmpty(shareCode), User::getShareCode, shareCode);
		lambdaQueryWrapper.eq(ObjUtil.isNotNull(isDisabled), User::getIsDisabled, isDisabled);

		String startCreateTime = request.getStartCreateTime();
		String endCreateTime = request.getEndCreateTime();
		if (StrUtil.isNotEmpty(startCreateTime) && StrUtil.isNotEmpty(endCreateTime)) {
			Date startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startCreateTime);
			Date endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endCreateTime);
			lambdaQueryWrapper.ge(ObjUtil.isNotEmpty(startTime), User::getCreateTime, startTime);
			lambdaQueryWrapper.lt(ObjUtil.isNotEmpty(endTime), User::getCreateTime, endTime);
		}

		String startEditTime = request.getStartEditTime();
		String endEditTime = request.getEndEditTime();
		if (StrUtil.isNotEmpty(startEditTime) && StrUtil.isNotEmpty(endEditTime)) {
			Date startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startEditTime);
			Date endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endEditTime);
			lambdaQueryWrapper.ge(ObjUtil.isNotEmpty(startTime), User::getEditTime, startTime);
			lambdaQueryWrapper.lt(ObjUtil.isNotEmpty(endTime), User::getEditTime, endTime);
		}

		// 处理排序规则
		if (request.isMultipleSort()) {
			List<PageRequest.Sort> sorts = request.getSorts();
			if (CollUtil.isNotEmpty(sorts)) {
				sorts.forEach(sort -> {
					String sortField = sort.getField();
					boolean sortAsc = sort.isAsc();
					lambdaQueryWrapper.orderBy(
							StrUtil.isNotEmpty(sortField), sortAsc, LambdaUtil.getLambda(User.class, sortField)
					);
				});
			}
		} else {
			PageRequest.Sort sort = request.getSort();
			if (sort != null) {
				String sortField = sort.getField();
				boolean sortAsc = sort.isAsc();
				lambdaQueryWrapper.orderBy(
						StrUtil.isNotEmpty(sortField), sortAsc, LambdaUtil.getLambda(User.class, sortField)
				);
			} else {
				lambdaQueryWrapper.orderByDesc(User::getCreateTime);
			}
		}
		return lambdaQueryWrapper;
	}

	/**
	 * 判断用户是否存在
	 *
	 * @param userEmail 用户邮箱
	 * @return 是否存在 true：存在 false：不存在
	 */
	public boolean existsUserByUserEmail(String userEmail) {
		return this.exists(new LambdaQueryWrapper<User>().eq(User::getUserEmail, userEmail));
	}

	/**
	 * 根据账户获取用户信息
	 *
	 * @param account 账户: 账号或邮箱
	 * @return 用户信息
	 */
	public User getUserByAccount(String account) {
		return this.getOne(new LambdaQueryWrapper<User>()
				.eq(User::getUserAccount, account).or(qw -> qw.eq(User::getUserEmail, account)));
	}

	/**
	 * 更新用户名称
	 *
	 * @param userId   用户ID
	 * @param userName 用户名称
	 * @return 是否成功
	 */
	public boolean updateUserNameById(long userId, String userName) {
		return this.lambdaUpdate()
				.eq(User::getUserId, userId)
				.set(User::getUserName, userName)
				.update();
	}

	/**
	 * 更新用户徽章
	 *
	 * @param userId  用户ID
	 * @param badgeId 徽章ID
	 * @return 是否成功
	 */
	public boolean updateBadgeIdById(long userId, Long badgeId) {
		return this.lambdaUpdate()
				.eq(User::getUserId, userId)
				.set(User::getBadgeId, badgeId)
				.update();
	}

	/**
	 * 根据用户ID获取用户部分信息列表
	 *
	 * @param userIdList 用户ID列表
	 * @return 用户息列表
	 */
	public List<User> getUserBaseInfoListByUserIdList(List<Long> userIdList) {
		return this.lambdaQuery()
				.select(User::getUserId, User::getUserName, User::getUserAvatar, User::getOnlineStatus)
				.in(User::getUserId, userIdList)
				.list();
	}
}
