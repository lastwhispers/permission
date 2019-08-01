package cn.lastwhisper.core.aop;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import cn.lastwhisper.core.annotation.LogAnno;
import cn.lastwhisper.core.util.UserUtils;
import cn.lastwhisper.modular.pojo.Log;
import cn.lastwhisper.modular.pojo.User;
import cn.lastwhisper.modular.service.LogService;

/**
 * AOP实现日志
 * 
 * @author 最后的轻语_dd43
 * 
 */
@Order(3)
@Component
@Aspect
public class LogAopAspect {
	// 日志Service
	@Autowired
	private LogService logService;

	/**
	 * 环绕通知记录日志通过注解匹配到需要增加日志功能的方法
	 * 
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Around("@annotation(cn.lastwhisper.core.annotation.LogAnno)")
	public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
		// 1.方法执行前的处理，相当于前置通知
		// 获取方法签名
		MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
		// 获取方法
		Method method = methodSignature.getMethod();
		// 获取方法上面的注解
		LogAnno logAnno = method.getAnnotation(LogAnno.class);
		// 获取操作描述的属性值
		String operateType = logAnno.operateType();
		// 创建一个日志对象(准备记录日志)
		Log log = new Log();
		log.setOperatetype(operateType);// 操作说明

		// 当前登录的用户
		User user = UserUtils.getSubjectUser();
		// 设置操作人账号
		log.setOperateor(user.getUser_code());
		String ip = UserUtils.getIpAddress();
		log.setIp(ip);
		Object result = null;
		try {
			// 让代理方法执行
			result = pjp.proceed();
			// 2.相当于后置通知(方法成功执行之后走这里)
			log.setOperateresult("正常");// 设置操作结果
		} catch (SQLException e) {
			// 3.相当于异常通知部分
			log.setOperateresult("失败");// 设置操作结果
		} finally {
			// 4.相当于最终通知
			log.setOperatedate(new Date());// 设置操作日期
			logService.addLog(log);// 添加日志记录
		}
		return result;
	}

	
}