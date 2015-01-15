package com.technology.tts.mail;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;

import com.technology.tts.config.Config;
import com.technology.tts.config.ConfigManager;
import com.technology.tts.mail.template.EmailTemplateService;
import com.technology.tts.mail.template.FreemarkerEmailTemplateService;
import com.technology.tts.util.ParaChecker;

/**
 * 流量统计系统邮件
 * @author Tim
 */
public class TTSMailService {
    private static Logger logger = Logger.getLogger(TTSMailService.class);

    private static TTSMailService mailService = new TTSMailService();

    private Config config = ConfigManager.getInstance().getConfig();

    private static final String CHARSET = "GB2312";

    //模板
    private static EmailTemplateService emailTemplateService = FreemarkerEmailTemplateService
        .getInstance();

    /**
     * 私有构造
     */
    private TTSMailService() {
    }

    /**
     * 实例化
     * @return
     */
    public static TTSMailService getInstance() {
        return mailService;
    }

    /**
     * 发送邮件
     */
    public void send(EmailInfo emailInfo) {
        //SimpleEmail email = new SimpleEmail();
        HtmlEmail email = new HtmlEmail();
        // email.setTLS(false); //是否TLS校验，，某些邮箱需要TLS安全校验，同理有SSL校验
        // email.setDebug(true);
        email.setSSL(true);

        String hostName = config.getMailHostName();
        String authentication = config.getMailAuthentication();
        String from = config.getMailFrom();
        String password = config.getMailPassword();
        String to = config.getMailTO();
        String cc = config.getMailCC();

        email.setHostName(hostName);
        //是否需要验证
        if (ParaChecker.isValidPara(authentication) && "YES".equalsIgnoreCase(authentication)) {
            email.setAuthentication(from, password);
        }

        try {
            //发送方
            email.setFrom(from);
            //接收方,这里可以写多个
            email.addTo(to);

            if (ParaChecker.isValidPara(cc)) {
                //抄送方
                email.addBcc(cc);
                //email.addBcc("yuaio@163.com"); //秘密抄送方
            }

            //邮件编码
            email.setCharset(CHARSET);

            //标题
            String subject = emailTemplateService.getText("mail-title", emailInfo.getParameters());
            //内容
            String content = emailTemplateService.getText("mail-body", emailInfo.getParameters());
            email.setSubject(subject);
            //email.setMsg(content);
            email.setHtmlMsg(content);

            //发送
            email.send();

            logger.debug("发送成功");
        } catch (EmailException e) {
            logger.error("EmailException", e);
        }
    }

}
