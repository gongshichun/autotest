package com.technology.tts.main;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.PropertyConfigurator;

import com.technology.tts.engine.TTSRealTimeEngine;
import com.technology.tts.exception.TTSException;
import com.technology.tts.job.JobManager;
import com.technology.tts.mail.template.FreemarkerEmailTemplateService;
import com.technology.tts.util.JedisManager;
import com.technology.tts.util.MySQLManager;
/**
 * 流量统计启动类
 * @author Tim
 */
public class TTSServer {
    public static void main(String[] args) throws FileNotFoundException, IOException, TTSException {
        //加载Log4j
        PropertyConfigurator.configure("config\\log4j.properties");

        //初始化MySQL
        MySQLManager.getInstance().init();

        //初始化Jedis
        JedisManager.getInstance().init();

        //实时引擎启动
        TTSRealTimeEngine.getInstance().init();

        //Freemarker邮件模板初始化
        FreemarkerEmailTemplateService.getInstance().init();

        //启动自动任务
        JobManager.getInstance().startJobs();
    }
}
