/**  
 * @Title:  MenuController.java   
 * @Package cn.lastwhisper.controller   
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 最后的轻语_dd43     
 * @date:   2019年4月6日 下午5:05:32   
 * @version V1.0 
 */
package cn.lastwhisper.modular.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.lastwhisper.core.util.GlobalResult;
import cn.lastwhisper.core.util.Tree;
import cn.lastwhisper.core.util.UserUtils;
import cn.lastwhisper.modular.pojo.Menu;
import cn.lastwhisper.modular.pojo.User;
import cn.lastwhisper.modular.service.MenuService;

/**   
 * @ClassName:  MenuController   
 * @Description:菜单管理
 * @author:     最后的轻语_dd43
 * @date:       2019年4月6日
 */
@Controller
public class MenuController {
	
	@Autowired
	private MenuService menuService;
	/**
	 * 查找所有
	 * @author Zoe
	 * @date 2019年2月15日下午3:46:48
	 * @return
	 */
	@RequestMapping(value="/menu/menulist")
	@ResponseBody
	public List<Tree> findAll() {
		return menuService.findMenuList(); 
	}
	
	/**
	 * 根据菜单id查找菜单，显示菜单详情
	 * @author Zoe
	 * @date 2019年2月15日下午8:15:02
	 * @param menuid 主键
	 * @return
	 */
	@RequestMapping("/menu/menufindById")
	@ResponseBody
	public List<Menu> findById(String menuid) {
		return menuService.findMenuById(menuid);
	}
	/**
	 * 添加数据
	 * @author Zoe
	 * @date 2019年2月15日下午9:47:56
	 * @param menu 菜单对象
	 * @return
	 */
	@RequestMapping(value="/menu/menuadd")
	@ResponseBody
	public GlobalResult insert(Menu menu) {
		return menuService.addMenu(menu);
	}
	
	/**
	 * 根据id删除数据[修改状态]
	 * @author Zoe
	 * @date 2019年2月15日下午9:47:41
	 * @param menuid 主键
	 * @return
	 */
	@RequestMapping(value="/menu/menudelete")
	@ResponseBody
	public GlobalResult deleteById(String menuid) {
		return menuService.deleteMenuById(menuid);
	}
	
	/**
	 * 根据id修改数据
	 * @author Zoe
	 * @date 2019年2月15日下午9:48:22
	 * @param menu 菜单对象
	 * @return
	 */
	@RequestMapping(value="/menu/menuupdate")
	@ResponseBody
	public GlobalResult updateById(Menu menu) {
		return menuService.updateMenuById(menu);
	}
	
	
	/**
	 * 
	* @Title: loadMenu 
	* @Description: 根据session中的user_id加载菜单
	* @return Menu
	* @author 最后的轻语_dd43
	* @date 2019年2月16日下午9:18:20
	 */
	@RequestMapping(value="/menu/loadMenus")
	@ResponseBody
	public Menu loadMenus() {
		User user = UserUtils.getSubjectUser();
		Menu menus = null;
		if(user!=null) {
			menus = menuService.findMenuByUserid(user.getUser_id());
		}
		return menus;
	}
}
