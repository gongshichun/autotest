package com.technology.tts.mail;

import java.util.HashMap;
import java.util.Map;
/**
 * 邮件实体
 * @author Tim
 */
public class EmailInfo {
    /**
     * 邮件内容
     */
    private String content;
    /**
     * 邮件标题
     */
    private String title;
    /**
     * 模板id
     */
    private String templateId;
    /**
     * 邮件中的参数
     */
    private Map<Object, Object> parameters = new HashMap<Object, Object>();

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public Map<Object, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<Object, Object> parameters) {
        this.parameters = parameters;
    }

    public void addParameter(Object key, Object value) {
        this.parameters.put(key, value);
    }

    public void removeParameter(Object key) {
        this.parameters.remove(key);
    }
}
