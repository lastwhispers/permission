/**  
 * @Title:  MenuService.java   
 * @Package cn.lastwhisper.service   
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 最后的轻语_dd43     
 * @date:   2019年4月6日 下午5:10:15   
 * @version V1.0 
 */
package cn.lastwhisper.modular.service;

import java.util.List;

import cn.lastwhisper.core.util.GlobalResult;
import cn.lastwhisper.core.util.Tree;
import cn.lastwhisper.modular.pojo.Menu;

/**   
 * @ClassName:  MenuService   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author:     最后的轻语_dd43
 * @date:       2019年4月6日
 */
public interface MenuService {
	
	/**
	 * 
	 * @Title: findMenuList   
	 * @Description: 查找所有数据
	 * @author: 最后的轻语_dd43    
	 * @return
	 */
	List<Tree> findMenuList();

	/**
	 * 
	 * @Title: findMenuById   
	 * @Description: 根据菜单id查找菜单，显示菜单详情
	 * @author: 最后的轻语_dd43    
	 * @param menuid
	 * @return
	 */
	List<Menu> findMenuById(String menuid);

	/**
	 * 
	 * @Title: addMenu   
	 * @Description: 添加数据
	 * @author: 最后的轻语_dd43    
	 * @param Menu
	 * @return
	 */
	GlobalResult addMenu(Menu Menu);
	
	/**
	 * 
	 * @Title: deleteMenuById   
	 * @Description: 根据id删除数据
	 * @author: 最后的轻语_dd43    
	 * @param menuid
	 * @return
	 */
	GlobalResult deleteMenuById(String menuid);

	/**
	 * 
	 * @Title: updateMenuById   
	 * @Description: 根据id修改数据
	 * @author: 最后的轻语_dd43    
	 * @param Menu
	 * @return
	 */
	GlobalResult updateMenuById(Menu Menu);
	/**
	 * 
	* @Title: findMenuByUserid 
	* @Description: 根据userid加载对应菜单 
	* @param userid
	* @return Menu
	* @author gj
	* @date 2019年2月16日下午8:43:39
	 */
	Menu findMenuByUserid(Integer userid);
	/**
	 * 
	* @Title: findMenuListByUserid 
	* @Description: 根据userid加载对应菜单无序列表 
	* @param userid
	* @return List<Menu>
	* @author gj
	* @date 2019年2月17日下午8:55:10
	 */
	List<Menu> findMenuListByUserid(Integer userid);

}
