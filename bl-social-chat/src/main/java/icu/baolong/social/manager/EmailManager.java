package icu.baolong.social.manager;

import icu.baolong.social.common.thread.ThreadPoolConfig;
import icu.baolong.social.common.exception.BusinessException;
import icu.baolong.social.common.response.RespCode;
import icu.baolong.social.common.utils.EmailUtil;
import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 邮箱处理服务
 *
 * @author Silas Yan 2025-04-06:01:19
 */
@Slf4j
@Component
public class EmailManager {

	@Resource
	private JavaMailSender javaMailSender;

	@Value("${spring.mail.nickname}")
	private String nickname;

	@Value("${spring.mail.username}")
	private String sender;

	/**
	 * 发送邮箱验证码
	 *
	 * @param recipient 收件人
	 * @param subject   主题
	 * @param code      验证码
	 */
	@Async(ThreadPoolConfig.EMAIL_EXECUTOR)
	public void sendEmailAsEmailCode(String recipient, String subject, String code) {
		log.info("[邮箱处理服务] 给 {} 发送验证码 {}", recipient, code);
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			// 组合邮箱发送的内容
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
			// 设置邮件发送者
			messageHelper.setFrom(nickname + "<" + sender + ">");
			// 设置邮件接收者
			messageHelper.setTo(recipient);
			// 设置邮件标题
			messageHelper.setSubject(subject);
			// 设置邮件内容
			Map<String, Object> contentMap = Map.of("code", code);
			messageHelper.setText(EmailUtil.emailContentTemplate("email/SendCodeTemplate.html", contentMap), true);
			javaMailSender.send(message);
		} catch (MessagingException e) {
			log.error("[邮箱处理服务] 给 {} 发送验证码失败", recipient, e);
			throw new BusinessException(RespCode.SYSTEM_ERROR, "验证码发送失败");
		}
	}

	/**
	 * 发送邮箱注册成功
	 *
	 * @param recipient 收件人
	 * @param subject   主题
	 * @param password  密码
	 */
	@Async(ThreadPoolConfig.EMAIL_EXECUTOR)
	public void sendEmailAsRegisterSuccess(String recipient, String subject, String password) {
		try {
			// 创建MIME消息
			MimeMessage message = javaMailSender.createMimeMessage();
			// 组合邮箱发送的内容
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
			// 设置邮件发送者
			messageHelper.setFrom(nickname + "<" + sender + ">");
			// 设置邮件接收者
			messageHelper.setTo(recipient);
			// 设置邮件主题
			messageHelper.setSubject(subject);
			// 设置邮件内容
			Map<String, Object> contentMap = Map.of("password", password);
			messageHelper.setText(EmailUtil.emailContentTemplate("templates/RegisterSuccessTemplate.html", contentMap), true);
			// 发送邮件
			javaMailSender.send(message);
		} catch (Exception e) {
			throw new BusinessException(RespCode.SYSTEM_ERROR, "邮箱发送失败");
		}
	}
}
