/**  
 * @Title:  LogMapper.java   
 * @Package cn.lastwhisper.modular.mapper   
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 最后的轻语_dd43     
 * @date:   2019年4月30日 下午9:42:54   
 * @version V1.0 
 */
package cn.lastwhisper.modular.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.lastwhisper.modular.pojo.Log;

/**   
 * @ClassName:  LogMapper   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author:     最后的轻语_dd43
 * @date:       2019年4月30日
 */
public interface LogMapper {

	/**   
	 * @Title: insertLog   
	 * @Description: 向数据库添加日志
	 * @author: 最后的轻语_dd43    
	 * @param log
	 * @return      
	 */
	int insertLog(Log log);
	/**
	 * 
	 * @Title: selectUserlistByPage   
	 * @Description: 查询
	 * @author: 最后的轻语_dd43    
	 * @param log
	 * @return
	 */
	public List<Log> selectLoglistByPage(Log log);
	/**
	 * 
	 * @Title: selectLogOperateor   
	 * @Description: 模糊查询操作人
	 * @author: 最后的轻语_dd43    
	 * @param operateor
	 * @return
	 */
	public List<Log> selectLogOperateor(@Param("operateor")String operateor);
	
}
