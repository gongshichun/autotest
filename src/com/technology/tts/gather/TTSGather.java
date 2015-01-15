package com.technology.tts.gather;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.technology.tts.exception.TTSException;
import com.technology.tts.util.JedisManager;
/**
 * TTS收集
 * @author Tim
 */
public abstract class TTSGather extends Thread {
    private static Logger logger = LoggerFactory.getLogger(TTSGather.class);

    protected JedisManager jedisManager = JedisManager.getInstance();

    //线程处理
    public void run() {
        while (true) {
            try {
                //业务逻辑处理    
                execute();
            } catch (TTSException e) {
                logger.error("Gather Exception:" + e.getMessage());
            }
        }
    }

    /**
     * 业务数据处理
     */
    public abstract void execute() throws TTSException;
}
