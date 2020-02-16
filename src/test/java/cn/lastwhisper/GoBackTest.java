package cn.lastwhisper;

import cn.lastwhisper.core.task.GoBackTask;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @author lastwhisper
 * @date 2020/2/16
 */
public class GoBackTest {
    
    public static void main(String[] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:applicationContext*.xml");
        GoBackTask mysqlTask = context.getBean(GoBackTask.class);
        mysqlTask.goBack();
    }
    
}
