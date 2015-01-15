package com.technology.tts.gather;

import java.util.List;

import redis.clients.jedis.Jedis;

import com.technology.tts.engine.TTSRealTimeEngine;
import com.technology.tts.exception.TTSException;
import com.technology.tts.util.ParaChecker;
import com.technology.tts.util.TTSGlobalConst;
/**
 * TTS Website收集
 * @author Tim
 */
public class TTSWebSiteGather extends TTSGather {
    private static TTSWebSiteGather instance = new TTSWebSiteGather();

    /**
     * 私有构造
     */
    private TTSWebSiteGather() {
        this.start();
    }

    /**
     * 获取实例
     * @return
     */
    public static TTSWebSiteGather getInstance() {
        return instance;
    }

    @Override
    public void execute() throws TTSException {
        Jedis jedis = jedisManager.getResource();

        //阻塞去Redis拿数据
        List<String> list = jedis.brpop(0, TTSGlobalConst.WEBSITE_KEY);

        //获取数据
        String data = list.get(1);

        if (ParaChecker.isNull(data)) {
            return;
        }

        //拼接成格式
        data =
            TTSGlobalConst.WEBSITE
                + data.replaceFirst(data.substring(0, 1), data.substring(0, 1).toUpperCase());

        //实时引擎处理数据
        TTSRealTimeEngine.getInstance().dealData(data);

        //放入池中
        jedisManager.recycleJedisOjbect(jedis);

    }
}
