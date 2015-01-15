package com.technology.tts.mail.template;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * ����Freemarkerģ�弼�����ʼ�ģ�����
 * @author Tim
 *
 */
public class FreemarkerEmailTemplateService implements EmailTemplateService {
    //�ʼ�ģ��Ĵ��λ��
    private static final String TEMPLATE_PATH = "../../../../../resource/";

    //����ģ�建��
    private static final Map<String, Template> TEMPLATE_CACHE = new HashMap<String, Template>();

    //ģ���ļ���׺
    private static final String SUFFIX = ".ftl";

    //ģ����������
    private static Configuration configuration;

    private static FreemarkerEmailTemplateService template = new FreemarkerEmailTemplateService();

    /**
     * ˽�й���
     */
    private FreemarkerEmailTemplateService() {
    }

    /**
     * ��ȡʵ��
     * @return
     */
    public static FreemarkerEmailTemplateService getInstance() {
        return template;
    }

    /**
     * ��ʼ��
     */
    public void init() {
        configuration = new Configuration();

        configuration.setTemplateLoader(new ClassTemplateLoader(
            FreemarkerEmailTemplateService.class,
            TEMPLATE_PATH));

        configuration.setEncoding(Locale.getDefault(), "UTF-8");

        configuration.setDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * ��ȡ����
     */
    public String getText(String templateId, Map<Object, Object> parameters) {
        String templateFile = templateId + SUFFIX;
        try {
            Template template = TEMPLATE_CACHE.get(templateFile);

            //template
            if (template == null) {
                template = configuration.getTemplate(templateFile);

                TEMPLATE_CACHE.put(templateFile, template);
            }

            StringWriter stringWriter = new StringWriter();

            template.process(parameters, stringWriter);

            return stringWriter.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}