package cn.lastwhisper.core.task;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;

/**
 *
 * @author lastwhisper
 * @date 2020/2/16
 */
@Component
public class GoBackTask implements ApplicationContextAware {

    private static Logger logger = LoggerFactory.getLogger(GoBackTask.class);
    private static ApplicationContext applicationContext;

    @Autowired
    private JedisPool jedisPool;

    /**
     * CronTrigger配置完整格式为： [秒][分][小时][日][月][周][年]
     * (cron = "0/2 * * * * ?") //每两秒
     *
     * 每3小时重置mysql和redis
     *
     */
    @Scheduled(cron = "0 0 0/3 * * ?")
    public void goBack() {
        // 清空缓存
        flushRedis();
        // 重置mysql
        resetDb();
    }

    private void flushRedis() {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.flushAll();
        } catch (Exception ignored) {
        }
    }

    private void resetDb() {
        logger.info("数据库重置开始");
        SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) applicationContext.getBean("sqlSessionFactory");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Connection conn = sqlSession.getConnection();
        ScriptRunner runner = new ScriptRunner(conn);
        Resources.setCharset(StandardCharsets.UTF_8); //设置字符集,不然中文乱码插入错误
        runner.setLogWriter(null);//设置是否输出日志
        // 绝对路径读取
        // 从class目录下直接读取
        Reader read;
        try {
            read = Resources.getResourceAsReader("reset.sql");
            runner.runScript(read);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            runner.closeConnection();
        }
        logger.info("数据库重置完毕");
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }
}
