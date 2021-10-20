package backend.controller;

import backend.config.Config;
import backend.config.responseException.BadRequest;
import backend.controller.forgetpassword.*;
import backend.entity.User;
import backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/v1")
public class ForgetPassword {

    private final EmailSender emailSender;

    private final EmailTemplate emailTemplate;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Value("${expired.token.password.minute:}")
    private int expiredToken;

    Config config = new Config();

    @Autowired
    public ForgetPassword(EmailSender emailSender,
                          EmailTemplate emailTemplate,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.emailSender = emailSender;
        this.emailTemplate = emailTemplate;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/forgot-password")//send OTP
    public Map sendEmailPassword(@RequestBody UserUpdateModel user) {
        String message = "Thanks, please check your email";

        if (StringUtils.isEmpty(user.getEmail())) return TemplateReqRes.isRequired("No email provided");
        User found = userRepository.findOneByUsername(user.getEmail());
        if (found == null) return TemplateReqRes.notFound("Email not found"); //throw new BadRequest("Email not found");

        String template = emailTemplate.getResetPassword();
        if (StringUtils.isEmpty(found.getOtp())) {
            User search;
            String otp;
            do {
                otp = SimpleStringUtils.randomString(6, true);
                search = userRepository.findOneByOTP(otp);
            } while (search != null);
            Date dateNow = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateNow);
            calendar.add(Calendar.MINUTE, expiredToken);
            Date expirationDate = calendar.getTime();

            found.setOtp(otp);
            found.setOtpExpiredDate(expirationDate);
            template = template.replaceAll("\\{\\{PASS_TOKEN}}", otp);
            userRepository.save(found);
        } else {
            template = template.replaceAll("\\{\\{PASS_TOKEN}}", found.getOtp());
        }
        emailSender.sendAsync(found.getUsername(), "Forget Password", template);


        return TemplateReqRes.template1("success");

    }

    @PostMapping("/forgot-password-reset")
    public Map resetPassword(@RequestBody UserUpdateModel model) {
        if (model.getOtp() == null) return TemplateReqRes.notFound("Token " + config.isRequired);
        if (model.getNewPassword() == null) return TemplateReqRes.notFound("New Password " + config.isRequired);
        User user = userRepository.findOneByOTP(model.getOtp());
        String success;
        if (user == null) return TemplateReqRes.notFound("Token not valid");

        user.setPassword(passwordEncoder.encode(model.getNewPassword().replaceAll("\\s+", "")));
        user.setOtpExpiredDate(null);
        user.setOtp(null);

        try {
            userRepository.save(user);
            success = "success";
        } catch (Exception e) {
            throw new BadRequest(e.getMessage());
        }
        return TemplateReqRes.template1(success);
    }

}
