/**  
 * @Title:  RoleService.java   
 * @Package cn.lastwhisper.service   
 * @Description: TODO(用一句话描述该文件做什么)
 * @author:     最后的轻语_dd43   
 * @date:   2019年4月6日 下午2:45:32   
 * @version V1.0 
 */
package cn.lastwhisper.modular.service;

import java.util.List;

import cn.lastwhisper.core.util.EasyUIDataGridResult;
import cn.lastwhisper.core.util.EasyUIOptionalTreeNode;
import cn.lastwhisper.core.util.GlobalResult;
import cn.lastwhisper.modular.pojo.Role;


/**
 * 
 * @ClassName: RoleService 
 * @Description: 处理角色相关业务
 * @author:     最后的轻语_dd43 
 * @date: 2019年2月14日 下午6:31:37
 */
public interface RoleService {
	/**
	 * 
	* @Title: findRoleByPage 
	* @Description: 根据查询条件所有角色 
	* @param page
	* @param rows
	* @param role
	* @return EasyUIDataGridResult
	* @author 最后的轻语_dd43 
	* @date 2019年2月14日下午7:08:40
	 */
	public EasyUIDataGridResult findRolelistByPage(Integer page, Integer rows,Role role);
	/**
	 * 
	* @Title: findRoleByEnble 
	* @Description: 查询所有可用的角色 
	* @return EasyUIDataGridResult
	* @author 最后的轻语_dd43 
	* @date 2019年2月16日下午12:34:06
	 */
	public EasyUIDataGridResult findRoleList();
	
	/**
	 * 
	 * @Title: findUserName   
	 * @Description: like查询角色名
	 * @param q 角色名
	 * @return
	 */
	public List<Role> findRoleName(String q);
	
	/**
	 * 
	* @Title: updateRole 
	* @Description: 更新角色的名称或者状态 
	* @param role
	* @return ManagerResult
	* @author 最后的轻语_dd43 
	* @date 2019年2月14日下午8:18:37
	 */
	public GlobalResult updateRole(Role role);
	/**
	 * 
	* @Title: addRole 
	* @Description: 添加角色 
	* @param role
	* @return ManagerResult
	* @author 最后的轻语_dd43 
	* @date 2019年2月14日下午10:11:23
	 */
	public GlobalResult addRole(Role role);
	/**
	 * 
	 * @Title: deleteRoleById   
	 * @Description: 根据uuid删除角色
	 * @param uuid
	 * @return
	 */
	public GlobalResult deleteRoleById(Integer uuid);
	
	/**
	 * 
	* @Title: findRoleMenu 
	* @Description: 获取角色菜单权限 
		-- 1.根据角色id获取对应的权限菜单id,比如角色id roleuuid=1
		select role_menu.menuuuid from role,role_menu WHERE role_menu.roleuuid=1;
		-- 2.获取所有权限菜单(menuid,menuname)
		SELECT menuid,menuname FROM menu;
	* @param roleuuid
	* @return List<EasyUIOptionalTreeNode>
	* @author 最后的轻语_dd43 
	* @date 2019年2月16日下午4:40:34
	 */
	public List<EasyUIOptionalTreeNode> findRoleMenuByRoleid(Integer roleuuid);
	/**
	 * 
	* @Title: updateRoleMenu
	* @Description: 更新角色权限菜单 
	* @param roleuuid
	* @param checkedIds
	* @return ManagerResult
	* @author 最后的轻语_dd43 
	* @date 2019年2月16日下午8:10:20
	 */
	public GlobalResult updateRoleMenu(Integer roleuuid, String checkedIds);
}
