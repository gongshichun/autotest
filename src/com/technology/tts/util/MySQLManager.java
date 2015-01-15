package com.technology.tts.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.technology.tts.exception.TTSException;

/**
 * MySQL管理
 * @author Tim
 */
public class MySQLManager {
    private static final Logger logger = LoggerFactory.getLogger(MySQLManager.class);

    private static final MySQLManager instance = new MySQLManager();

    private DataSource dataSource;//dataSource

    //配置文件路径
    private static String CONFIG_PATH = "config/mysql.properties";

    public static MySQLManager getInstance() {
        return instance;
    }

    /**
     * 初始化连接池
     * @throws TTSException
     */
    public void init() throws TTSException {
        logger.debug("init mysql.");

        Properties prop = new Properties();
        prop = new Properties();
        try {
            prop.load(new FileInputStream(CONFIG_PATH));
        } catch (FileNotFoundException e) {
            throw new TTSException("TTSException mysql.properties FileNotFund:" + e.getMessage());
        } catch (IOException e) {
            throw new TTSException("TTSException mysql.properties IOException:" + e.getMessage());
        }

        try {
            dataSource = BasicDataSourceFactory.createDataSource(prop);
        } catch (Exception e) {
            throw new TTSException("TTSException mysql createDataSource:" + e.getMessage());
        }

    }

    /**
     * 获取连接
     * @return
     * @throws TTSException
     */
    public Connection getConnection() throws TTSException {
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new TTSException("TTSException mysql.properties IOException:" + e.getMessage());
        }

        return connection;
    }

    /**
     * 关闭连接，真实是放入连接池中
     * @param connection
     */
    public void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                connection = null;
            }
            connection = null;
        }
    }
}