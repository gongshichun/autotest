package com.technology.tts.test;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;

import com.technology.tts.mail.EmailInfo;
import com.technology.tts.mail.TTSMailService;
import com.technology.tts.mail.template.FreemarkerEmailTemplateService;

public class TestMailService {
    @Test
    public void test() {
        //加载Log4j
        PropertyConfigurator.configure("config/log4j.properties");

        //模板初始化
        FreemarkerEmailTemplateService.getInstance().init();

        EmailInfo emailInfo = new EmailInfo();
        emailInfo.addParameter("name", "Kevin");
        emailInfo.addParameter("newPassword", "123456");
        //发送邮件
        TTSMailService.getInstance().send(emailInfo);
    }
}
