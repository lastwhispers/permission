/**  
 * @Title:  MenuMapper.java   
 * @Package cn.lastwhisper.mapper   
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 最后的轻语_dd43     
 * @date:   2019年4月6日 下午2:35:30   
 * @version V1.0 
 */
package cn.lastwhisper.modular.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.lastwhisper.core.util.Tree;
import cn.lastwhisper.modular.pojo.Menu;

/**   
 * @ClassName:  MenuMapper   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author:     最后的轻语_dd43
 * @date:       2019年4月6日
 */
public interface MenuMapper {
	/**
	 * 
	 * @Title: selectMenuList   
	 * @Description: 查找所有数据
	 * @author: 最后的轻语_dd43    
	 * @return
	 */
	List<Tree> selectMenuList();

	/**
	 * 
	 * @Title: selectMenuById   
	 * @Description: 根据菜单id查找菜单，显示菜单详情
	 * @author: 最后的轻语_dd43    
	 * @param menuid
	 * @return
	 */
	List<Menu> selectMenuById(@Param("menuid") String menuid);

	/**
	 * 
	 * @Title: insertMenu   
	 * @Description: 添加数据
	 * @author: 最后的轻语_dd43    
	 * @param Menu
	 * @return
	 */
	Integer insertMenu(Menu Menu);

	/**
	 * 
	 * @Title: deleteMenuById   
	 * @Description: 根据id删除数据[修改状态]
	 * @author: 最后的轻语_dd43    
	 * @param menuid
	 * @return
	 */
	Integer deleteMenuById(String menuid);

	/**
	 * 
	 * @Title: updateMenuById   
	 * @Description: 根据id修改数据
	 * @author: 最后的轻语_dd43    
	 * @param Menu
	 * @return
	 */
	Integer updateMenuById(Menu Menu);
	
	/**
	 * 
	 * @Title: selectMenuIdName
	 * @Description: 根据pid获取所有权限菜单(menuid,menuname)
	 * @return List<Menu>
	 * @author 最后的轻语_dd43
	 * @date 2019年2月16日下午7:05:10
	 */
	public List<Menu> selectMenuIdName(@Param("pid") String pid);

	/**
	 * 
	 * @Title: selectMenuByUserid
	 * @Description: 根据userid加载对应菜单
	 * @param userid
	 * @return List<Menu>
	 * @author 最后的轻语_dd43
	 * @date 2019年2月16日下午8:40:39
	 */
	public List<Menu> selectMenuByUserid(@Param("userid") Integer userid);
	
	/**
	 * 
	* @Title: selectMenu 
	* @Description: 查询所有菜单的所有属性 
	* @param pid
	* @return List<Menu>
	* @author 最后的轻语_dd43
	* @date 2019年2月16日下午9:04:26
	 */
	public List<Menu> selectMenu(@Param("pid") String pid);

}
