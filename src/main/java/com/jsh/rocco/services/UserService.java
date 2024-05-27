package com.jsh.rocco.services;

import com.jsh.rocco.config.security.repositories.RoccoUserRepository;
import com.jsh.rocco.config.security.domains.RoccoUser;
import com.jsh.rocco.domains.enums.results.EmailAuthResult;
import com.jsh.rocco.util.misc.MailSender;
import com.jsh.rocco.domains.enums.results.CommonResult;
import com.jsh.rocco.domains.enums.results.Result;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.security.NoSuchAlgorithmException;

@Service
public class UserService {
    private final RoccoUserRepository roccoUserRepository;
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Autowired
    public UserService(RoccoUserRepository roccoUserRepository, JavaMailSender mailSender, SpringTemplateEngine templateEngine) {
        this.roccoUserRepository = roccoUserRepository;
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    public RoccoUser getUser(String email){
        return roccoUserRepository.findUserByEmail(email).orElse(null);
    }


    @Transactional
    public Result<?> sendRegisterEmail(String email,String authCode) throws NoSuchAlgorithmException, MessagingException {
        RoccoUser user = roccoUserRepository.findUserByEmail(email).orElse(null);
        if(user != null){
            return EmailAuthResult.FAILURE_DUPLICATE_EMAIL;
        }
        System.out.println(user);
        Context context = new Context();
        context.setVariable("code", authCode);
        new MailSender(this.mailSender)
                .setFrom("whtjdghks03@gmail.com")
                .setSubject("[Rocco] 회원가입 인증번호")
                .setText(this.templateEngine.process("user/registerEmail", context), true)
                .setTo(email)
                .send();
        return CommonResult.SUCCESS;
    }

}
