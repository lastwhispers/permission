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
import cn.lastwhisper.core.util.EasyUIOptionalTreeNode;
import cn.lastwhisper.core.util.GlobalResult;
import cn.lastwhisper.modular.pojo.Role;
import cn.lastwhisper.modular.service.RoleService;

/**   
 * @ClassName:  RoleController   
 * @Description:接收角色相关请求
 * @author:     最后的轻语_dd43
 * @date:       2019年4月6日
 */
@Controller
public class RoleController {
	@Autowired
	private RoleService roleService;
	/**
	 * 
	* @Title: findRoleByEnble 
	* @Description: 查询角色列表 
	* @param page
	* @param rows
	* @return EasyUIDataGridResult
	* @author 最后的轻语_dd43
	* @date 2019年2月14日下午6:48:36
	 */
	@RequestMapping("/role/rolelistByPage")
	@ResponseBody
	public EasyUIDataGridResult rolelistByPage(Role role,
			@RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
			@RequestParam(value = "rows", required = true, defaultValue = "10") Integer rows) {
		EasyUIDataGridResult result = roleService.findRolelistByPage(page, rows, role);
		return result;
	}
	
	/**
	 * 
	 * @Title: searchRoleName   
	 * @Description: 角色名自动补全
	 * @param q
	 * @return
	 */
	@RequestMapping(value = "/role/searchRoleName", method = RequestMethod.POST)
	@ResponseBody
	public List<Role> searchRoleName(String q) {
		List<Role> roleName = roleService.findRoleName(q);
		return roleName;
	}
	
	/**
	 * 
	* @Title: updateRole 
	* @Description: 接收更新角色的数据 
	* @param role
	* @return GlobalResult
	* @author 最后的轻语_dd43
	* @date 2019年2月14日下午8:19:07
	 */
	@RequestMapping(value = "/role/roleupdate" , method = RequestMethod.POST)
	@ResponseBody
	public GlobalResult updateRole(Role role) {
		GlobalResult result = roleService.updateRole(role);
		return result;
	}
	/**
	 * 
	* @Title: addRole 
	* @Description: 添加角色 
	* @param role
	* @return GlobalResult
	* @author 最后的轻语_dd43
	* @date 2019年2月15日上午11:52:43
	 */
	@RequestMapping(value = "/role/roleadd" , method = RequestMethod.POST)
	@ResponseBody
	public GlobalResult addRole(Role role) {
		GlobalResult result = roleService.addRole(role);
		return result;
	}
	
	@RequestMapping(value = "/role/roledelete" , method = RequestMethod.POST)
	@ResponseBody
	public GlobalResult deleteRole(Role role) {
		GlobalResult result = roleService.deleteRoleById(role.getUuid());
		return result;
	}
	/**
	 * 
	* @Title: rolelistByEnble 
	* @Description: 返回datagrid格式json 
	* @return EasyUIDataGridResult
	* @author 最后的轻语_dd43
	* @date 2019年2月16日下午12:36:00
	 */
	@RequestMapping(value = "/role/rolelist")
	@ResponseBody
	public EasyUIDataGridResult rolelist() {
		EasyUIDataGridResult result = roleService.findRoleList();
		return result;
	}
	/**
	 * 
	* @Title: findRoleMenuByRoleid 
	* @Description: 根据角色id加载对应权限菜单 
	* @param roleuuid
	* @return List<EasyUIOptionalTreeNode>
	* @author 最后的轻语_dd43
	* @date 2019年2月16日下午7:19:56
	 */
	@RequestMapping(value = "/role/findRoleMenuByRoleid" , method = RequestMethod.POST)
	@ResponseBody
	public List<EasyUIOptionalTreeNode> findRoleMenuByRoleid(
			@RequestParam(value = "id", required = true)Integer roleuuid) {
		return roleService.findRoleMenuByRoleid(roleuuid);
	}
	/**
	 * 
	* @Title: updateRoleMenu 
	* @Description: 更新角色权限菜单 
	* @param roleuuid
	* @param checkedIds
	* @return GlobalResult
	* @author 最后的轻语_dd43
	* @date 2019年2月16日下午8:16:05
	 */
	@RequestMapping(value = "/role/updateRoleMenu" , method = RequestMethod.POST)
	@ResponseBody
	public GlobalResult updateRoleMenu(
			@RequestParam(value = "id", required = true) Integer roleuuid,
			@RequestParam(value = "checkedIds", required = true) String checkedIds) {
		GlobalResult result = roleService.updateRoleMenu(roleuuid, checkedIds);
		return result;
	}
}
