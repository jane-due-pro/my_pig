package guat.lxy.bigdata.pig.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class SendMailService {
    @Autowired
    private JavaMailSender mailSender;
    public void sendVerificationCode(String to, String code) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom("3220417795@qq.com");
        helper.setTo(to);
        helper.setSubject("【你的APP名】邮箱验证码");
        String content = String.format(
                "<html><body><h3>您的验证码是：<strong>%s</strong></h3><p>验证码1分钟内有效，请勿泄露。</p></body></html>",
                code
        );
        helper.setText(content, true);
        mailSender.send(message);
    }
}