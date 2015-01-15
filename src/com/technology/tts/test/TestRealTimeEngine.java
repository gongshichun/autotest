package com.technology.tts.test;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;

import com.technology.tts.engine.TTSRealTimeEngine;
import com.technology.tts.exception.TTSException;
import com.technology.tts.util.JedisManager;
import com.technology.tts.util.MySQLManager;
import com.technology.tts.util.TTSGlobalConst;

public class TestRealTimeEngine {
    @Test
    public void test() throws FileNotFoundException, IOException, TTSException {
        //加载Log4j
        PropertyConfigurator.configure("config/log4j.properties");

        //初始化Jedis
        JedisManager.getInstance().init();

        //初始化MySQL
        MySQLManager.getInstance().init();

        //实时引擎启动
        TTSRealTimeEngine.getInstance().init();

        //测试push
        JedisManager.getInstance().getResource().lpush(TTSGlobalConst.IOS_KEY, "installsCount=1");

        //测试push
        JedisManager.getInstance().getResource().lpush(TTSGlobalConst.IOS_KEY, "openCount=1");
    }

    public static void main(String[] args) throws FileNotFoundException, IOException, TTSException {
        //加载Log4j
        PropertyConfigurator.configure("config/log4j.properties");

        //初始化Jedis
        JedisManager.getInstance().init();

        //初始化MySQL
        MySQLManager.getInstance().init();

        //实时引擎启动
        TTSRealTimeEngine.getInstance().init();

        JedisManager.getInstance().getResource().del(TTSGlobalConst.ANDROID_KEY);
        JedisManager.getInstance().getResource().del(TTSGlobalConst.IOS_KEY);
        JedisManager.getInstance().getResource().del(TTSGlobalConst.WEBSITE_KEY);

        //测试push
        pushData(TTSGlobalConst.ANDROID_KEY);

        pushData(TTSGlobalConst.IOS_KEY);

        pushData(TTSGlobalConst.WEBSITE_KEY);

    }

    public static void pushData(String key) {
        //测试push
        JedisManager.getInstance().getResource().lpush(key, "installsCount=1");
        //测试push
        JedisManager.getInstance().getResource().lpush(key, "openCount=2");
        //测试push
        JedisManager.getInstance().getResource().lpush(key, "registrationCount=3");
        //测试push
        JedisManager.getInstance().getResource().lpush(key, "loginCount=4");
        //测试push
        JedisManager.getInstance().getResource().lpush(key, "orderCount=5");
        //测试push
        JedisManager.getInstance().getResource().lpush(key, "productViewCount=10");
    }
}
