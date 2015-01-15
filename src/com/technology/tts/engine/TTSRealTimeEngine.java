package com.technology.tts.engine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.StringTokenizer;

import com.technology.tts.exception.TTSException;
import com.technology.tts.gather.TTSAndroidGather;
import com.technology.tts.gather.TTSIosGather;
import com.technology.tts.gather.TTSWebSiteGather;
import com.technology.tts.util.DateUtils;
import com.technology.tts.util.MySQLManager;
import com.technology.tts.util.ParaChecker;
import com.technology.tts.util.TTSGlobalConst;

/**
 * 流量统计系统引擎
 * @author Tim
 */
public class TTSRealTimeEngine {
    private static TTSRealTimeEngine engine = new TTSRealTimeEngine();

    private static MySQLManager mysqlManager = MySQLManager.getInstance();

    //更新当前统计表
    private static final String UPDATE_CURRENT_SQL =
        "UPDATE STATISTICS SET current_count = current_count + ?"
            + " WHERE statistics_key = ? AND date = ?";

    //插入当前统计表
    private static final String INSERT_CURRENT_SQL =
        "INSERT INTO STATISTICS(statistics_key, date, current_count) VALUES (?, ?, ?)";

    //更新统计总表
    private static final String UPDATE_TOTAL_SQL = "UPDATE STATISTICS_TOTAL SET count = count + ?"
        + " WHERE statistics_key = ?";

    //插入统计总表
    private static final String INSERT_TOTAL_SQL =
        "INSERT INTO STATISTICS_TOTAL(statistics_key, count) VALUES (?, ?)";

    /**
     * 私有构造
     */
    private TTSRealTimeEngine() {
    }

    /**
     * 获取实例
     * @return
     */
    public static TTSRealTimeEngine getInstance() {
        return engine;
    }

    /**
     * 处理主体
     */
    public void init() {
        //Android收集
        TTSAndroidGather.getInstance();

        //Ios收集
        TTSIosGather.getInstance();

        //Website收集
        TTSWebSiteGather.getInstance();
    }

    /**
     * 处理数据
     * @param value
     * @throws TTSException
     */
    public void dealData(String value) throws TTSException {
        if (ParaChecker.isValidPara(value)) {
            String[] data = parseString(value);
            String key = data[0];//key
            int count = Integer.parseInt(data[1]);//量
            int date = DateUtils.getCurrentDate();//当前日期

            Connection conn = null;
            PreparedStatement updateCurrentPs = null;//更新当天量
            PreparedStatement insertCurrentPs = null;//插入当天
            PreparedStatement updateTotalPs = null;//更新总量
            PreparedStatement insertTotalPs = null;//插入总量
            try {
                //获取连接
                conn = mysqlManager.getConnection();

                updateCurrentPs = conn.prepareStatement(UPDATE_CURRENT_SQL);
                int i = 1;
                updateCurrentPs.setInt(i++, count);
                updateCurrentPs.setString(i++, key);
                updateCurrentPs.setInt(i++, date);

                //更新
                boolean isUpdateFlag = updateCurrentPs.execute();

                //没有更新上，则进行插入
                if (!isUpdateFlag) {
                    insertCurrentPs = conn.prepareStatement(INSERT_CURRENT_SQL);
                    i = 1;
                    insertCurrentPs.setString(i++, key);
                    insertCurrentPs.setInt(i++, date);
                    insertCurrentPs.setInt(i++, count);

                    //插入
                    insertCurrentPs.execute();
                }

                String totalKey = key + TTSGlobalConst.TOTAL;
                updateTotalPs = conn.prepareStatement(UPDATE_TOTAL_SQL);
                i = 1;
                updateTotalPs.setInt(i++, count);
                updateTotalPs.setString(i++, totalKey);
                //更新
                isUpdateFlag = updateTotalPs.execute();

                //更新不上，则进行插入
                if (!isUpdateFlag) {
                    insertTotalPs = conn.prepareStatement(INSERT_TOTAL_SQL);
                    i = 1;
                    insertTotalPs.setString(i++, totalKey);
                    insertTotalPs.setInt(i++, count);

                    //插入
                    insertTotalPs.execute();
                }

            } catch (SQLException e) {
                throw new TTSException("TTSException RealTimeEngine SQL Error:" + e.getMessage());
            } finally {
                if (updateCurrentPs != null) {
                    try {
                        updateCurrentPs.close();
                    } catch (SQLException e) {
                        updateCurrentPs = null;
                    }
                }
                if (insertCurrentPs != null) {
                    try {
                        insertCurrentPs.close();
                    } catch (SQLException e) {
                        insertCurrentPs = null;
                    }
                }
                if (updateTotalPs != null) {
                    try {
                        updateTotalPs.close();
                    } catch (SQLException e) {
                        updateTotalPs = null;
                    }
                }
                if (insertTotalPs != null) {
                    try {
                        insertTotalPs.close();
                    } catch (SQLException e) {
                        insertTotalPs = null;
                    }
                }

                //关闭连接
                mysqlManager.close(conn);
            }
        }
    }

    /**
     * 解析String
     * @param str
     * @return
     */
    private String[] parseString(String str) {
        StringTokenizer st = new StringTokenizer(str, "=");

        String key = st.nextToken();
        String value = st.nextToken();

        return new String[]{key, value};
    }
}
