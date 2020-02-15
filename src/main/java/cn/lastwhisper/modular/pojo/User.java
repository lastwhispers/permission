
package cn.lastwhisper.modular.pojo;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @ClassName:  User   
 * @Description:用户实体类
 * @author:     最后的轻语_dd43
 * @date:       2019年4月5日
 */
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer user_id;
	private String user_name;

	private String user_code;
	//不转换json字符串
	@JsonIgnore
	private String user_pwd;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date user_birthday;
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_code() {
		return user_code;
	}
	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}
	public String getUser_pwd() {
		return user_pwd;
	}
	public void setUser_pwd(String user_pwd) {
		this.user_pwd = user_pwd;
	}
	/**  
	 * @Title:  getUser_birthday <BR>  
	 * @Description: please write your description <BR>  
	 * @return: Date <BR>  
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getUser_birthday() {
		return user_birthday;
	}
	/**
	 * @param user_birthday the user_birthday to set
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	public void setUser_birthday(Date user_birthday) {
		this.user_birthday = user_birthday;
	}
	
}
