package com.technology.tts.job;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.technology.tts.exception.TTSException;
import com.technology.tts.mail.EmailInfo;
import com.technology.tts.mail.TTSMailService;
import com.technology.tts.util.DateUtils;
import com.technology.tts.util.MySQLManager;

/**
 * 
 * @author Tim
 */
public class TTSMailJob implements Job {

    private TTSMailService mailService = TTSMailService.getInstance();

    private MySQLManager sqlManager = MySQLManager.getInstance();

    /**
     * 执行
     */
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        //数据库查询数据
        Connection conn = null;
        //emailInfo
        EmailInfo emailInfo = new EmailInfo();
        int date = DateUtils.getCurrentDate();
        try {
            conn = sqlManager.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT statistics_key, current_count FROM STATISTICS");
            sql.append(" WHERE date = ").append(date);

            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql.toString());
            while (resultSet.next()) {
                int i = 1;
                String key = resultSet.getString(i++);
                int value = resultSet.getInt(i++);
                emailInfo.addParameter(key, value);
            }

            //STATISTICS_TOTAL
            sql.setLength(0);
            sql.append("SELECT statistics_key, count FROM STATISTICS_TOTAL");

            Statement statement_total = conn.createStatement();
            ResultSet resultSet_total = statement_total.executeQuery(sql.toString());
            while (resultSet_total.next()) {
                int i = 1;
                String key = resultSet_total.getString(i++);
                int value = resultSet_total.getInt(i++);
                emailInfo.addParameter(key, value);
            }

        } catch (TTSException | SQLException e) {
            throw new JobExecutionException("Job SQLException:" + e.getMessage());
        } finally {
            sqlManager.close(conn);
        }

        //发送邮件
        mailService.send(emailInfo);
    }
}
