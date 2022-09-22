package com.ktk.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.ktk.domain.entity.NotificationEmail;
import com.ktk.exception.RedditException;
import com.ktk.util.MailContentBuilder;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@AllArgsConstructor
public class MailService {

	private final JavaMailSender mailSender;
	private final MailContentBuilder mailContentBuilder;

	void sendMail(NotificationEmail notificationEmail) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();

		try {
			/**
			 * 첨부 파일(Multipartfile) 보낼거면 true
			 */
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
			mimeMessageHelper.setTo(notificationEmail.getRecipient());
			mimeMessageHelper.setSubject(notificationEmail.getSubject());

			/**
			 * html 템플릿으로 보낼거면 true plaintext로 보낼거면 false
			 */
			mimeMessageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()), true);
			mailSender.send(mimeMessage);
			log.info("Activation email sent!!");
		} catch (MessagingException e) {
			e.printStackTrace();
			log.error("[EmailService.send()] error {}", e.getMessage());

		}

	}

//	MimeMessagePreparator messagePreparator = mimeMessage -> {
//            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
//            messageHelper.setFrom("springreddit@email.com");
//            messageHelper.setTo(notificationEmail.getRecipient());
//            messageHelper.setSubject(notificationEmail.getSubject());
//            messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
//        };try
//	{
//		mailSender.send(messagePreparator);
//		log.info("Activation email sent!!");
//	}catch(
//	MailException e)
//	{
//		e.printStackTrace();
//		throw new RedditException("Exception occurred when sending mail to " + notificationEmail.getRecipient());
//	}
//}

}
