package cn.lastwhisper.modular.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.lastwhisper.modular.pojo.Role;
import cn.lastwhisper.modular.pojo.User;
/**
 * 
 * @ClassName:  UserMapper   
 * @Description:mybatis的接口，用于操作用户相关表  
 * @author:     最后的轻语_dd43
 * @date:       2019年4月5日
 */
public interface UserMapper {
	/**
	 * 
	 * @Title: selectUserBycodeAndpwd   
	 * @Description: 根据user_code和user_pwd查询user表
	 * @param user_code 账号
	 * @param user_pwd 密码
	 * @return
	 */
	public User selectUserBycodeAndpwd(@Param("user_code") String user_code,
			@Param("user_pwd") String user_pwd);
	/**
	 * 
	 * @Title: selectUserList   
	 * @Description: 查询所有用户信息   
	 * @param user 查询条件
	 * @return
	 */
	public List<User> selectUserlistByPage(User user);
	/**
	 * 
	 * @Title: selectUserName   
	 * @Description: 查询用户姓名，自动补全
	 * @param user_name
	 * @return
	 */
	public List<User> selectUserName(@Param("user_name")String user_name);
	
	/**
	 * 
	 * @Title: selectUserRole
	 * @Description: 根据userid获取用户对应的角色,例如user_id=221
	 * @param userId
	 * @return List<Role>
	 * @author gj
	 * @date 2019年2月16日下午2:50:20
	 */
	public List<Role> selectUserRole(@Param("user_id") Integer user_id);
	/**
	 * 
	 * @Title: selectUserByUserCode   
	 * @Description: 证据用户账号查询对应用户
	 * @author: 最后的轻语_dd43    
	 * @param user_code 用户账号
	 * @return
	 */
	public List<User> selectUserByUserCode(String user_code);
	
	/**
	 * 
	 * @Title: insertUser   
	 * @Description: 添加用户
	 * @param user 用户
	 * @return
	 */
	public Integer insertUser(User user);
	
	/**
	 * 
	* @Title: addUserRole 
	* @Description: 给用户添加角色 
	* @param user_id
	* @param roleuuid
	* @return int
	* @author gj
	* @date 2019年2月16日下午3:53:14
	 */
	public void insertUserRole(@Param("user_id") Integer user_id,@Param("roleuuid") Integer roleuuid);
	/**
	 * 
	 * @Title: updateUser   
	 * @Description: 更新用户信息
	 * @param user 用户信息
	 * @return
	 */
	public Integer updateUser(User user);
	
	/**
	 * 
	 * @Title: updatePwd   
	 * @Description: 更新密码
	 * @author: 最后的轻语_dd43    
	 * @param oldPwd
	 * @param newPwd
	 * @return
	 */
	public Integer updatePwdById(@Param("user_id") Integer user_id,@Param("user_pwd") String user_pwd);
	/**
	 * 
	 * @Title: updateUser   
	 * @Description: 更新用户信息根据用户账号
	 * @param user 用户账号
	 * @return
	 */
	public Integer updateUserByUserCode(User user);
	/**
	 * 
	 * @Title: deleteUserById   
	 * @Description: 根据主键删除用户信息 
	 * @param user_id 主键
	 * @return
	 */
	public Integer deleteUserById(@Param("user_id") Integer user_id);
	
	/**
	 * 
	* @Title: deleteUserRole 
	* @Description: 删除用户关联的角色id
	* @param user_id
	* @return int
	* @author gj
	* @date 2019年2月16日下午3:51:24
	 */
	public void deleteUserRole(@Param("user_id") Integer user_id);

}
