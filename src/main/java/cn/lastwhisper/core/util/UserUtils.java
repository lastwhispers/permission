/**  
 * @Title:  RoleService.java   
 * @Package cn.lastwhisper.service   
 * @Description: TODO(用一句话描述该文件做什么)
 * @author:     最后的轻语_dd43   
 * @date:   2019年4月6日 下午2:45:32   
 * @version V1.0 
 */
package cn.lastwhisper.core.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.lastwhisper.modular.pojo.User;

/**
 * 
 * @ClassName:  UserUtils   
 * @Description:获取当前登录的用户  
 * @author:     最后的轻语_dd43
 * @date:       2019年4月5日
 */
public class UserUtils {
	/**
	 * 
	 * @Title: getSubjectUser   
	 * @Description: 获取shiro中登录的用户
	 * @return
	 */
	public static User getSubjectUser() {
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		return user;
	}
	/**
	 * 
	 * @Title: removeSubjectUser   
	 * @Description: 从shiro中移除登录的用户
	 * @author: 最后的轻语_dd43
	 */
	public static void removeSubjectUser() {
		SecurityUtils.getSubject().logout();
	}
	/**
	 * 
	 * @Title: getRequest   
	 * @Description: 获取当前的request
	 * @author: 最后的轻语_dd43    
	 * @return
	 */
	public static HttpServletRequest getRequest() {
		return  ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
	}
	
	/**
	 * 获取IP地址的方法
	 * 
	 * @return
	 */
	public static String getIpAddress() {
		HttpServletRequest request = getRequest();
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	/**
	 * 
	 * @Title: getSessionUser   
	 * @Description: 获取session中登录的用户（开启shiro后失效）
	 * @return
	 */
	@Deprecated
	public static User getSessionUser() {
		return (User)getRequest().getSession().getAttribute("user");
	}
	
	/**
	 * 
	 * @Title: setSessionUser   
	 * @Description: 将当前登录的用户信息放入session（开启shiro后失效）
	 * @param user
	 */
	@Deprecated
	public static void setSessionUser(User user) {
		getRequest().getSession().setAttribute("user", user);
	}
	/**
	 * 
	 * @Title: removeSessionUser   
	 * @Description: 从session中移除user（开启shiro后失效）
	 * @author: 最后的轻语_dd43
	 */
	@Deprecated
	public static void removeSessionUser() {
		getRequest().getSession().removeAttribute("user");;
	}
}
