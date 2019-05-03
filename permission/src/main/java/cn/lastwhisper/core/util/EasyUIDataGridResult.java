/**  
 * @Title:  RoleService.java   
 * @Package cn.lastwhisper.service   
 * @Description: TODO(用一句话描述该文件做什么)
 * @author:     最后的轻语_dd43   
 * @date:   2019年4月6日 下午2:45:32   
 * @version V1.0 
 */
package cn.lastwhisper.core.util;

import java.io.Serializable;
import java.util.List;
/**
 * 
 * @ClassName:  EasyUIDataGridResult   
 * @Description:easyui分页
 * @author:     最后的轻语_dd43
 * @date:       2019年4月6日
 */
public class EasyUIDataGridResult implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 数据库中总记录数
	 */
	private Integer total;
	/**
	 * 当前页数据
	 */
	private List<?> rows;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}
	
}
