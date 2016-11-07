package org.service.utils.mail;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.service.utils.spring.SpringContextUtil;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * @ClassName PushMail
 * @author <font color="red"><b>Liu.Gang.Qiang</b></font>
 * @date 2016年11月6日
 * @version 1.0
 * @description 邮件推送工具类 多线程执行不关心结果
 */
public class PushMail {
	private static JavaMailSenderImpl mailSender;
	static ExecutorService pool = Executors.newCachedThreadPool();

	static {
		mailSender = SpringContextUtil.getBean("mailSender", JavaMailSenderImpl.class);
	}

	/**
	 * @author <font color="green"><b>Liu.Gang.Qiang</b></font>
	 * @param mail
	 * @date 2016年11月6日
	 * @version 1.0
	 * @description 推送文本格式邮件
	 */
	public static void pushText(final TextMail mail) {
		pool.execute(new Runnable() {
			@Override
			public void run() {
				SimpleMailMessage message = new SimpleMailMessage();
				message.setTo(mail.getTo());
				message.setFrom(mailSender.getUsername());
				message.setSubject(mail.getSubject());
				message.setText(mail.getText());
				message.setSentDate(new Date());
				mailSender.send(message);
			}
		});
	}

	/**
	 * @author <font color="green"><b>Liu.Gang.Qiang</b></font>
	 * @param mail
	 * @date 2016年11月6日
	 * @version 1.0
	 * @description 推送富文本格式邮件包括附件
	 */
	public static void pushHtml(final HtmlMail mail) {
		pool.execute(new Runnable() {
			@Override
			public void run() {
				MimeMessage mimeMessage = mailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
				try {
					helper.setTo(mail.getTo());
					helper.setFrom(mailSender.getUsername());
					helper.setSubject(mail.getSubject());
					helper.setText(mail.getText(), true);
					helper.setSentDate(new Date());
					Set<File> files = mail.getFiles();
					Multipart mp = new MimeMultipart();
					MimeBodyPart mbp = null;
					if (files != null && files.size() > 0) {
						for (File file : files) {
							mbp = new MimeBodyPart();
							mbp.setDataHandler(new DataHandler(new FileDataSource(file)));
							mbp.setFileName(MimeUtility.encodeWord(file.getName()));
							mp.addBodyPart(mbp);
						}
						mimeMessage.setContent(mp);
					}
					mimeMessage.setSentDate(new Date());
					mailSender.send(mimeMessage);
				} catch (MessagingException | UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
