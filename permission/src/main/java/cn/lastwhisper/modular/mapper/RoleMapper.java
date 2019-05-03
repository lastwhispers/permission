/**  
 * @Title:  RoleMapper.java   
 * @Package cn.lastwhisper.mapper   
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 最后的轻语_dd43     
 * @date:   2019年4月6日 下午2:35:46   
 * @version V1.0 
 */
package cn.lastwhisper.modular.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.lastwhisper.modular.pojo.Role;

/**
 * @ClassName: RoleMapper
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: 最后的轻语_dd43
 * @date: 2019年4月6日
 */
public interface RoleMapper {
	/**
	 * 
	 * @Title: selectRoleByPage
	 * @Description: 根据条件查询所有角色
	 * @param role
	 * @return List<Role>
	 * @author 最后的轻语_dd43
	 * @date 2019年2月14日下午7:12:56
	 */
	public List<Role> selectRolelistByPage(Role role);

	/**
	 * 
	 * @Title: selectAllRole
	 * @Description: 查询所有角色
	 * @param role
	 * @return List<Role>
	 * @author 最后的轻语_dd43
	 * @date 2019年2月16日下午12:28:09
	 */
	public List<Role> selectRoleList();
	/**
	 * 
	 * @Title: selectRoleName   
	 * @Description: 查询角色，自动补全   
	 * @param name
	 * @return
	 */
	public List<Role> selectRoleName(@Param("name")String name);

	/**
	 * 
	 * @Title: selectRoleMenuidByRoleid
	 * @Description: 根据角色id获取对应的权限菜单id
	 * @param roleuuid
	 * @return List<Integer>
	 * @author 最后的轻语_dd43
	 * @date 2019年2月16日下午4:35:08
	 */
	public List<String> selectRoleMenuidByRoleid(@Param("roleuuid") Integer roleuuid);

	/**
	 * 
	 * @Title: selectUseridByRoleuuid
	 * @Description: 根据角色id获取对应的用户id
	 * @param roleuuid
	 * @return List<Integer>
	 * @author 最后的轻语_dd43
	 * @date 2019年2月17日上午11:18:02
	 */
	public List<Integer> selectUseridByRoleuuid(@Param("roleuuid") Integer roleuuid);

	/**
	 * 
	 * @Title: updateRole
	 * @Description: 更新角色信息
	 * @param role
	 * @return Integer
	 * @author 最后的轻语_dd43
	 * @date 2019年2月14日下午8:22:57
	 */
	public Integer updateRole(Role role);

	/**
	 * 
	 * @Title: insertRole
	 * @Description: 添加角色
	 * @param role
	 * @return Integer
	 * @author 最后的轻语_dd43
	 * @date 2019年2月15日上午11:50:36
	 */
	public Integer insertRole(Role role);
	
	/**
	 * 
	 * @Title: insertRolemenu
	 * @Description: 新增角色权限菜单关系
	 * @param menuuuid
	 * @param roleuuid void
	 * @author 最后的轻语_dd43
	 * @date 2019年2月16日下午8:07:02
	 */
	public void insertRolemenu(@Param("menuuuid") String menuuuid, @Param("roleuuid") Integer roleuuid);
	
	/**
	 * 
	 * @Title: deleteRoleByid   
	 * @Description: 根据id删除对应角色
	 * @param uuid
	 * @return
	 */
	public Integer deleteRoleById(@Param("uuid") Integer uuid);
	
	/**
	 * 
	 * @Title: deleteMenuidByRoleid
	 * @Description: 根据roleuuid删除拥有的角色信息
	 * @param roleuuid void
	 * @author 最后的轻语_dd43
	 * @date 2019年2月16日下午8:05:53
	 */
	public void deleteMenuidByRoleid(@Param("roleuuid") Integer roleuuid);

}
