package icu.baolong.social.events;

import icu.baolong.social.repository.user.entity.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 用户下线事件发布者
 *
 * @author Silas Yan 2025-05-26 20:57
 */
@Getter
public class UserOfflineEventPublisher extends ApplicationEvent {
	private final User user;

	public UserOfflineEventPublisher(Object source, User user) {
		super(source);
		this.user = user;
	}
}
