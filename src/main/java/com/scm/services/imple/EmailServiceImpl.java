package com.scm.services.imple;
 
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
 
import com.scm.services.EmailService;
 
@Service
public class EmailServiceImpl implements EmailService {
 
    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
 
    @Autowired
    private JavaMailSender mailSender;
 
    // Reads spring.mail.properties.domain_name from application.properties
    // This is now your SES-verified sender address
    @Value("${spring.mail.properties.domain_name}")
    private String fromAddress;
 
    /**
     * Sends a plain-text email via AWS SES SMTP.
     * Called by UserServiceImpl.saveUser() during registration.
     *
     * @param to      recipient email (must be SES-verified in Sandbox mode)
     * @param subject email subject line
     * @param body    plain text body (contains the verification link)
     */
    @Override
    public void sendEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            message.setFrom(fromAddress);
            mailSender.send(message);
            logger.info("[SES] Plain-text email sent to: {}", to);
        } catch (MailException e) {
            logger.error("[SES] Failed to send email to: {} | Reason: {}", to, e.getMessage());
            // Re-throw so calling code (UserServiceImpl) can handle it
            throw e;
        }
    }
 
    /**
     * Sends an HTML email via AWS SES SMTP.
     * Use this for branded verification emails with styled HTML.
     *
     * @param to          recipient email
     * @param subject     email subject
     * @param htmlContent HTML string for the body
     */
    @Override
    public void sendEmailWithHtml(String to, String subject, String htmlContent) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom(fromAddress);
            helper.setText(htmlContent, true); // true = isHtml
            mailSender.send(mimeMessage);
            logger.info("[SES] HTML email sent to: {}", to);
        } catch (MessagingException | MailException e) {
            logger.error("[SES] Failed to send HTML email to: {} | Reason: {}", to, e.getMessage());
            throw new RuntimeException("Email sending failed", e);
        }
    }
 
    /**
     * Sends verification email as styled HTML.
     * Call this from UserServiceImpl instead of sendEmail() for a
     * better-looking verification email.
     *
     * @param to            recipient email
     * @param verifyLink    the full verification URL
     */
    public void sendVerificationEmail(String to, String verifyLink) {
        String subject = "Verify Your Account — Smart Contact Manager";
        String html = "<!DOCTYPE html>"
            + "<html><body style='font-family:Arial,sans-serif;background:#f4f4f4;padding:30px'>"
            + "<div style='max-width:500px;margin:0 auto;background:#fff;border-radius:8px;padding:30px'>"
            + "<h2 style='color:#1A5276'>Smart Contact Manager</h2>"
            + "<p>Thank you for registering! Click the button below to verify your account:</p>"
            + "<a href='" + verifyLink + "' "
            + "style='display:inline-block;background:#1A5276;color:#fff;padding:12px 24px;"
            + "text-decoration:none;border-radius:5px;font-size:16px;margin:16px 0'>"
            + "Verify My Account</a>"
            + "<p style='color:#888;font-size:13px'>If you did not register, ignore this email.</p>"
            + "<hr style='border:none;border-top:1px solid #eee'>"
            + "<p style='color:#aaa;font-size:11px'>Sent via AWS SES | SCM 2.0</p>"
            + "</div></body></html>";
        sendEmailWithHtml(to, subject, html);
    }
 
    @Override
    public void sendEmailWithAttachment() {
        throw new UnsupportedOperationException("sendEmailWithAttachment not implemented");
    }
}



// package com.scm.services.imple;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.mail.SimpleMailMessage;
// import org.springframework.mail.javamail.JavaMailSender;
// import org.springframework.stereotype.Service;

// import com.scm.services.EmailService;

// @Service
// public class EmailServiceImpl implements EmailService {

//     @Autowired
//     private JavaMailSender eMailSender;

//     @Value("${spring.mail.properties.domain_name}")
   
//     private String domainName;

//     @Override
//     public void sendEmail(String to, String subject, String body) {

//         SimpleMailMessage message = new SimpleMailMessage();
//         message.setTo(to);
//         message.setSubject(subject);
//         message.setText(body);
//         message.setFrom(domainName);
//         eMailSender.send(message);

//     }

//     @Override
//     public void sendEmailWithHtml() {
//         // TODO Auto-generated method stub
//         throw new UnsupportedOperationException("Unimplemented method 'sendEmailWithHtml'");
//     }

//     @Override
//     public void sendEmailWithAttachment() {
//         // TODO Auto-generated method stub
//         throw new UnsupportedOperationException("Unimplemented method 'sendEmailWithAttachment'");
//     }

// }