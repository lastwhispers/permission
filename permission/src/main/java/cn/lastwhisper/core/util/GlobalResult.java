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


/**
 * 
 * @ClassName:  GlobalResult   
 * @Description:全局返回值
 * @author:     最后的轻语_dd43
 * @date:       2019年4月5日
 */
public class GlobalResult implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    // 响应业务状态
    private Integer status;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object data;

    public static GlobalResult build(Integer status, String msg, Object data) {
        return new GlobalResult(status, msg, data);
    }

    public static GlobalResult ok(Object data) {
        return new GlobalResult(data);
    }

    public static GlobalResult ok() {
        return new GlobalResult(null);
    }

    public GlobalResult() {

    }

    public static GlobalResult build(Integer status, String msg) {
        return new GlobalResult(status, msg, null);
    }

    public GlobalResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public GlobalResult(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    public Boolean isOK() {
        return this.status == 200;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ManagerResult [status=" + status + ", msg=" + msg + ", data=" + data + "]";
	}
    

}
