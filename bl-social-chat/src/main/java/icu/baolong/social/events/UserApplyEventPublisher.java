package icu.baolong.social.events;

import icu.baolong.social.repository.user.entity.UserApply;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 用户申请事件发布者
 *
 * @author Silas Yan 2025-05-26 20:57
 */
@Getter
public class UserApplyEventPublisher extends ApplicationEvent {
	private final UserApply userApply;

	public UserApplyEventPublisher(Object source, UserApply userApply) {
		super(source);
		this.userApply = userApply;
	}
}
