/**  
 * @Title:  RoleController.java   
 * @Package cn.lastwhisper.controller   
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 最后的轻语_dd43     
 * @date:   2019年4月6日 下午3:00:04   
 * @version V1.0 
 */
package cn.lastwhisper.modular.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.lastwhisper.core.util.EasyUIDataGridResult;
import cn.lastwhisper.modular.pojo.Log;
import cn.lastwhisper.modular.service.LogService;

/**
 * 
 * @ClassName:  LogController   
 * @Description: 日志
 * @author:     最后的轻语_dd43
 * @date:       2019年5月1日
 */
@Controller
public class LogController {
	@Autowired
	private LogService logService;
	
	/**
	 * 
	 * @Title: rolelistByPage   
	 * @Description: 返回分页日志
	 * @author: 最后的轻语_dd43    
	 * @param log
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/log/loglistByPage")
	@ResponseBody
	public EasyUIDataGridResult rolelistByPage(Log log,
			@RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
			@RequestParam(value = "rows", required = true, defaultValue = "10") Integer rows) {
		EasyUIDataGridResult result = logService.findLoglistByPage(page, rows, log);
		return result;
	}
	
	/**
	 * 
	 * @Title: searchLogOperateor   
	 * @Description: 模糊查询用户名
	 * @author: 最后的轻语_dd43    
	 * @param q
	 * @return
	 */
	@RequestMapping(value = "/log/searchLogOperateor", method = RequestMethod.POST)
	@ResponseBody
	public List<Log> searchLogOperateor(String q) {
		List<Log> operateor = logService.findLogOperateor(q);
		return operateor;
	}
	
	
}
