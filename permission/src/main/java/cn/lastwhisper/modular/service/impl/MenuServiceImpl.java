/**  
 * @Title:  MenuServiceImpl.java   
 * @Package cn.lastwhisper.service.impl   
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 最后的轻语_dd43     
 * @date:   2019年4月6日 下午5:10:31   
 * @version V1.0 
 */
package cn.lastwhisper.modular.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

import cn.lastwhisper.core.annotation.LogAnno;
import cn.lastwhisper.core.util.GlobalResult;
import cn.lastwhisper.core.util.Tree;
import cn.lastwhisper.modular.mapper.MenuMapper;
import cn.lastwhisper.modular.pojo.Menu;
import cn.lastwhisper.modular.service.MenuService;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @ClassName: MenuServiceImpl
 * @Description: 菜单相关的操作
 * @author: 最后的轻语_dd43
 * @date: 2019年4月6日
 */
@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
@Service
public class MenuServiceImpl implements MenuService {
	
	private static Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);
	
	@Autowired
	private MenuMapper menuMapper;

//	@Autowired
//	private Jedis jedis;
	@Autowired
	private JedisPool jedisPool;

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Override
	public List<Tree> findMenuList() {
		List<Tree> tree = menuMapper.selectMenuList();
		return tree;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Override
	public List<Menu> findMenuById(String menuid) {
		return menuMapper.selectMenuById(menuid);
	}

	@LogAnno(operateType = "添加权限菜单")
	@Override
	public GlobalResult addMenu(Menu menu) {
		// 设置默认添加的菜单的状态为使用中
		Integer insertCount = menuMapper.insertMenu(menu);
		if (insertCount != null && insertCount > 0) {
			// 更新标签为父标签
			Menu m = new Menu();
			m.setMenuid(menu.getPid());
			m.setIs_parent(1);
			if (200 == updateMenuById(m).getStatus()) {
				batchDel("menus");
				return new GlobalResult(200, "数据添加成功", null);
			} else {
				return new GlobalResult(400, "数据添加失败", null);
			}
		} else {
			return new GlobalResult(400, "数据添加失败", null);
		}
	}

	/**
	 * 根据id删除数据
	 * 
	 * @author
	 * @date 2019年2月15日下午9:51:50
	 * @param menuid 主键
	 * @return
	 */
	@LogAnno(operateType = "删除权限菜单")
	@Override
	public GlobalResult deleteMenuById(String menuid) {
		Integer deleteCount = menuMapper.deleteMenuById(menuid);
		if (deleteCount != null && deleteCount > 0) {
			batchDel("menus");
			return new GlobalResult(200, "数据删除成功", null);
		} else {
			return new GlobalResult(400, "数据删除失败", null);
		}
	}

	/**
	 * @author
	 * @date 2019年2月15日下午9:51:50
	 * @param menu
	 * @return
	 */
	@LogAnno(operateType = "更新权限菜单")
	@Override
	public GlobalResult updateMenuById(Menu menu) {
		Integer updateCount = menuMapper.updateMenuById(menu);
		if (updateCount != null && updateCount > 0) {
			batchDel("menus");
			return new GlobalResult(200, "数据修改成功", null);
		} else {
			return new GlobalResult(400, "数据修改失败", null);
		}
	}

	/**
	 * 根据key前缀批量删除缓存
	 * 
	 * @param key
	 * @return
	 */
	private void batchDel(String key) {
		Jedis jedis = jedisPool.getResource();
		try {
			Set<String> set = jedis.keys(key + "*");
			Iterator<String> it = set.iterator();
			while (it.hasNext()) {
				String keyStr = it.next();
				jedis.del(keyStr);
			}
		} catch (Exception e) {
		}finally {
			if(jedis!=null)jedis.close();
		}
	}

	/**
	 * 
	 * @Title: cloneMenu
	 * @Description: 复制menu
	 * @author: 最后的轻语_dd43
	 * @param src
	 * @return
	 */
	private Menu cloneMenu(Menu src) {
		Menu menu = new Menu();
		menu.setIcon(src.getIcon());
		menu.setMenuid(src.getMenuid());
		menu.setMenuname(src.getMenuname());
		menu.setUrl(src.getUrl());
		menu.setMenus(new ArrayList<Menu>());
		return menu;
	}

	@Override
	public Menu findMenuByUserid(Integer userid) {
		// 从缓存中读取数据
		Jedis jedis = jedisPool.getResource();
		Menu menu;
		try {
			String easyuiMenusJson = jedis.get("menusEasyui_" + userid);
			if (easyuiMenusJson == null) {
				// 获取根菜单
				List<Menu> root = menuMapper.selectMenu("-1");
				// 用户下的菜单集合 找数据库
				//			List<Menu> userMenus = menuMapper.selectMenuByUserid(userid);
				// 用户下的菜单集合 找缓存
				List<Menu> userMenus = findMenuListByUserid(userid);
				// 根菜单
				menu = cloneMenu(root.get(0));
				// 暂存一级菜单
				Menu _m1 = null;
				// 暂存二级菜单
				Menu _m2 = null;
				// 获取全部的一级菜单
				List<Menu> parentMenus = menuMapper.selectMenu("0");
				// 循环一级菜单
				for (Menu m1 : parentMenus) {
					_m1 = cloneMenu(m1);
					// 获取当前一级菜单的所有二级菜单
					List<Menu> leafMenus = menuMapper.selectMenu(_m1.getMenuid());
					// 循环匹配二级菜单
					for (Menu m2 : leafMenus) {
						for (Menu userMenu : userMenus) {
							if (userMenu.getMenuid().equals(m2.getMenuid())) {
								// 将二级菜单加入一级菜单
								_m2 = cloneMenu(m2);
								_m1.getMenus().add(_m2);
							}
						}
					}
					// 有二级菜单我们才加进来
					if (_m1.getMenus().size() > 0) {
						// 把一级菜单加入到根菜单下
						menu.getMenus().add(_m1);
					}
				}
				logger.debug("从数据库读取，设置缓存");
				//			System.out.println("从数据库读取，设置缓存");
				jedis.set("menusEasyui_" + userid, JSON.toJSONString(menu));
			} else {
				menu = JSON.parseObject(easyuiMenusJson, Menu.class);
				//			menu = JSON.parseArray(easyuiMenusJson, Menu.class).get(0);
				//			System.out.println("从缓存读取");
				logger.debug("从缓存读取");
			} 
		} finally {
			if(jedis!=null)jedis.close();
		}
		return menu;
	}

	@Override
	public List<Menu> findMenuListByUserid(Integer userid) {
		Jedis jedis = jedisPool.getResource();
		List<Menu> menuList;
		try {
			String menuListJson = jedis.get("menusList_" + userid);
			menuList = null;
			if (menuListJson == null) {
				// 1.从数据库中查出来，放入缓存中
				menuList = menuMapper.selectMenuByUserid(userid);
				jedis.set("menusList_" + userid, JSON.toJSONString(menuList));
				logger.debug("从数据库中查询menuList");
			} else {
				// 2.直接从缓存中拿
				logger.debug("从缓存中查询menuList" + menuListJson);
				menuList = JSON.parseArray(menuListJson, Menu.class);
			} 
		} finally {
			if(jedis!=null)jedis.close();
		}
		return menuList;
	}
}
