package com.technology.tts.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 配置管理器
 * @author Tim
 */
public class ConfigManager {
    private static Logger logger = Logger.getLogger(ConfigManager.class);

    private static ConfigManager manager = new ConfigManager();

    private static final String CONFIG_PATH = "config/tts.properties";

    private Config config = new Config();

    /**
     * 私有构造
     */
    private ConfigManager() {
        try {
            load();
        } catch (FileNotFoundException e) {
            logger.error("Config Load FileNotFoundException", e);
        } catch (IOException e) {
            logger.error("Config Load IOException", e);
        }
    }

    /**
     * 实例化
     * @return
     */
    public static ConfigManager getInstance() {
        return manager;
    }

    /**
     * 加载
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void load() throws FileNotFoundException, IOException {
        Properties prop = new Properties();
        prop.load(new FileInputStream(CONFIG_PATH));

        config.setExpression(prop.getProperty("cron-expression"));

        config.setMailHostName(prop.getProperty("mail.hostName"));

        config.setMailFrom(prop.getProperty("mail.from"));

        config.setMailPassword(prop.getProperty("mail.password"));

        config.setMailAuthentication(prop.getProperty("mail.authentication"));

        config.setMailTO(prop.getProperty("mail.to"));

        config.setMailCC(prop.getProperty("mail.cc"));
    }

    /**
     * 获取配置文件内容
     * @return
     */
    public Config getConfig() {
        return config;
    }
}
