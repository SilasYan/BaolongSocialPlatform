package icu.baolong.social.events;

import icu.baolong.social.repository.user.entity.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 用户拉黑事件发布者
 *
 * @author Silas Yan 2025-05-26 20:57
 */
@Getter
public class UserBlackEventPublisher extends ApplicationEvent {
	private final User user;

	public UserBlackEventPublisher(Object source, User user) {
		super(source);
		this.user = user;
	}
}
