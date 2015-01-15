package com.technology.tts.mail.template;

import java.util.Map;

/**
 * �ʼ�ģ�������
 * @author Tim
 */
public interface EmailTemplateService {
    /**
     * ģ�������ʼ��
     */
    public void init();

    /**
     * ��ȡģ������
     * @param templateId
     * @param parameters
     * @return
     */
    public abstract String getText(String templateId, Map<Object, Object> parameters);

}
