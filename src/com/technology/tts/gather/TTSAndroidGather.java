package com.technology.tts.gather;

import java.util.List;

import com.technology.tts.engine.TTSRealTimeEngine;
import com.technology.tts.exception.TTSException;
import com.technology.tts.util.ParaChecker;
import com.technology.tts.util.TTSGlobalConst;

import redis.clients.jedis.Jedis;

/**
 * TTS Android收集
 * @author Tim
 */
public class TTSAndroidGather extends TTSGather {
    private static TTSAndroidGather instance = new TTSAndroidGather();

    /**
     * 私有构造
     */
    private TTSAndroidGather() {
        this.start();
    }

    /**
     * 获取实例
     * @return
     */
    public static TTSAndroidGather getInstance() {
        return instance;
    }

    public void execute() throws TTSException {
        Jedis jedis = jedisManager.getResource();

        //阻塞去Redis拿数据
        List<String> list = jedis.brpop(0, TTSGlobalConst.ANDROID_KEY);

        //获取数据
        String data = list.get(1);

        if (ParaChecker.isNull(data)) {
            return;
        }

        //拼接成格式
        data =
            TTSGlobalConst.ANDROID
                + data.replaceFirst(data.substring(0, 1), data.substring(0, 1).toUpperCase());

        //实时引擎处理数据
        TTSRealTimeEngine.getInstance().dealData(data);

        //放入池中
        jedisManager.recycleJedisOjbect(jedis);

    }

}
