package com.technology.tts.job;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.technology.tts.config.Config;
import com.technology.tts.config.ConfigManager;
import com.technology.tts.util.TTSGlobalConst;

/**
 * 任务管理器 
 * @author Tim
 */
public class JobManager {
    private static Logger logger = Logger.getLogger(JobManager.class);

    private static SchedulerFactory factory = new StdSchedulerFactory();

    private static JobManager manager = new JobManager();

    private static Config config = ConfigManager.getInstance().getConfig();

    /**
     * 私有构造
     */
    private JobManager() {
    }

    /**
     * 实例化
     * @return
     */
    public static JobManager getInstance() {
        return manager;
    }

    /**
     * 添加任务
     * @param jobName任务名
     * @param jobClass类名称（全名）
     * @param timeStart开始时间
     * @param timeEnd结束时间
     * @param timeSpace时间间隔
     */
    private static void addJob(Scheduler sched) {
        try {
            //详细任务
            JobDetail jobDetail =
                JobBuilder
                    .newJob(TTSMailJob.class)
                    .withIdentity(TTSGlobalConst.TTS_JOB_NAME, TTSGlobalConst.TRIGGER_GROUP_NAME)
                    .build();

            //Cron调度
            CronScheduleBuilder schedBuilder =
                CronScheduleBuilder.cronSchedule(config.getExpression());

            //监听
            Trigger trigger =
                TriggerBuilder
                    .newTrigger()
                    .withIdentity(TTSGlobalConst.TTS_JOB_NAME, TTSGlobalConst.TRIGGER_GROUP_NAME)
                    .withSchedule(schedBuilder)
                    .build();

            //将任务和监听放入调度器
            sched.scheduleJob(jobDetail, trigger);

        } catch (Exception e) {
            logger.error("Job Exception", e);
        }
    }

    /** 
     * 启动所有定时任务 
     */
    public void startJobs() {
        try {
            //获取调度器
            Scheduler sched = factory.getScheduler();

            //添加Job
            addJob(sched);

            //启动调度器
            sched.start();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /** 
     * 关闭所有定时任务 
     */
    public void shutdownJobs() {
        try {
            Scheduler sched = factory.getScheduler();
            if (!sched.isShutdown()) {
                sched.shutdown();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
