package cn.lastwhisper.modular.service;

import java.sql.SQLException;
import java.util.List;

import cn.lastwhisper.core.util.EasyUIDataGridResult;
import cn.lastwhisper.modular.pojo.Log;

/**
 * 日志Service
 * 
 * @author lastwhisper
 *
 */
public interface LogService {
    /**
     * 增加日志
     * @param log
     * @return
     * @throws SQLException
     */
    public boolean addLog(Log log) throws SQLException;

	/**   
	 * @Title: findLoglistByPage   
	 * @Description: 查询日志
	 * @author: 最后的轻语_dd43    
	 * @param page
	 * @param rows
	 * @param log
	 * @return      
	 */
	public EasyUIDataGridResult findLoglistByPage(Integer page, Integer rows, Log log);

	/**   
	 * @Title: findLogOperateor   
	 * @Description: 模糊查询用户名
	 * @author: 最后的轻语_dd43    
	 * @param q
	 * @return      
	 */
	public List<Log> findLogOperateor(String q);
}