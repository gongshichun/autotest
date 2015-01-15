package com.technology.tts.config;
/**
 * 文件配置类
 * @author Tim
 */
public class Config {
    //自动任务配置
    private String expression;

    //发送邮件服务器
    private String mailHostName;

    //邮件发送方
    private String mailFrom;

    //邮件密码
    private String mailPassword;

    //邮件验证
    private String mailAuthentication;

    //邮件接收方
    private String mailTO;

    //邮件抄送方
    private String mailCC;

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getMailHostName() {
        return mailHostName;
    }

    public void setMailHostName(String mailHostName) {
        this.mailHostName = mailHostName;
    }

    public String getMailFrom() {
        return mailFrom;
    }

    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }

    public String getMailPassword() {
        return mailPassword;
    }

    public void setMailPassword(String mailPassword) {
        this.mailPassword = mailPassword;
    }

    public String getMailAuthentication() {
        return mailAuthentication;
    }

    public void setMailAuthentication(String mailAuthentication) {
        this.mailAuthentication = mailAuthentication;
    }

    public String getMailTO() {
        return mailTO;
    }

    public void setMailTO(String mailTO) {
        this.mailTO = mailTO;
    }

    public String getMailCC() {
        return mailCC;
    }

    public void setMailCC(String mailCC) {
        this.mailCC = mailCC;
    }
}
