package com.technology.tts.test;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;

import com.technology.tts.exception.TTSException;
import com.technology.tts.job.JobManager;
import com.technology.tts.mail.template.FreemarkerEmailTemplateService;
import com.technology.tts.util.MySQLManager;
/**
 * Test Job
 * @author Tim
 */
public class TestJob {
    @Test
    public void test() throws TTSException {
        //加载Log4j
        PropertyConfigurator.configure("config/log4j.properties");

        //初始化MySQL
        MySQLManager.getInstance().init();

        //模板初始化
        FreemarkerEmailTemplateService.getInstance().init();

        //启动
        JobManager.getInstance().startJobs();
    }

    public static void main(String[] args) throws TTSException {
        //加载Log4j
        PropertyConfigurator.configure("config/log4j.properties");

        //初始化MySQL
        MySQLManager.getInstance().init();

        //模板初始化
        FreemarkerEmailTemplateService.getInstance().init();

        //启动
        JobManager.getInstance().startJobs();
    }
}
