package com.technology.tts.exception;
/**
 * TTSException
 * @author Tim
 */
public class TTSException extends Exception {
    /**
     * 构造器
     * @param errorCode 错误代码。
     */
    public TTSException(String message) {
        super(message);
    }
}
