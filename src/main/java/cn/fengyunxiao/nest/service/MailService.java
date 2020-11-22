package cn.fengyunxiao.nest.service;

import cn.fengyunxiao.nest.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {
    private final static Logger logger = LoggerFactory.getLogger(MailService.class);

    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String mailFrom;

    @Autowired
    public void setJavaMailSender(final JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    // 发送邮件耗时任务，另起线程，不影响主线程
    private void sendSimpleMail(String to, String title, String text) {
        try {
            sendSimpleMail2(mailFrom, to, title, text);
            Thread.sleep(1100);
        } catch (Exception e) {
            logger.error("邮件失败1: {},{}", to, text);
            // 如果发送失败，发送第2遍
            try {
                sendSimpleMail2(mailFrom, to, title, text);
                Thread.sleep(1100);
            } catch (Exception e1) {
                logger.error("邮件失败2: {},{}", to, text);
                // 如果发送失败，发送第3遍
                try {
                    sendSimpleMail2(mailFrom, to, title, text);
                    Thread.sleep(1100);
                } catch (Exception e2) {
                    logger.error("邮件失败3: {},{}", to, text);
                }
            }
        }
    }

    private void sendSimpleMail2(String from, String to, String title, String text) throws Exception {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
        mimeMessageHelper.setFrom(new InternetAddress(mailFrom, "nest", "UTF-8"));
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setSubject(title);
        mimeMessageHelper.setText(text);
        javaMailSender.send(message);
    }

    public void sendMail(final String title, final String text) {
        if (this.mailFrom == null || this.mailFrom.isEmpty()) {
            MailService.logger.info("not admin mail");
            return;
        }
        new Thread(() -> {
            for (final String mailTo : Config.adminMail) {
                this.sendSimpleMail(mailTo, title, text);
            }
        }).start();
    }
}
