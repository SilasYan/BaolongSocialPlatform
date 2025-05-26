package icu.baolong.social.events;

import icu.baolong.social.repository.user.entity.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 用户注册事件发布者
 *
 * @author Silas Yan 2025-05-26 20:57
 */
@Getter
public class UserRegisterEventPublisher extends ApplicationEvent {
	private final User user;

	public UserRegisterEventPublisher(Object source, User user) {
		super(source);
		this.user = user;
	}
}
