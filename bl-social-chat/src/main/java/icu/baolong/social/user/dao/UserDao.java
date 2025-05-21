package icu.baolong.social.user.dao;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.baolong.social.common.page.PageRequest;
import icu.baolong.social.common.utils.LambdaUtil;
import icu.baolong.social.user.domain.entity.User;
import icu.baolong.social.user.domain.request.UserQueryReq;
import icu.baolong.social.user.mapper.UserMapper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 用户表 (user) - 持久化服务
 *
 * @author Baolong 2025-05-21 21:01:07
 */
@Service
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
		lambdaQueryWrapper.eq(ObjUtil.isNotNull(id), User::getId, id);
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
}
