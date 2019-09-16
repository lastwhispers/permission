/**  
 * @Title:  RoleServiceImpl.java   
 * @Package cn.lastwhisper.service.impl   
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 最后的轻语_dd43     
 * @date:   2019年4月6日 下午2:52:25   
 * @version V1.0 
 */
package cn.lastwhisper.modular.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.lastwhisper.core.annotation.LogAnno;
import cn.lastwhisper.core.util.EasyUIDataGridResult;
import cn.lastwhisper.core.util.EasyUIOptionalTreeNode;
import cn.lastwhisper.core.util.GlobalResult;
import cn.lastwhisper.modular.mapper.MenuMapper;
import cn.lastwhisper.modular.mapper.RoleMapper;
import cn.lastwhisper.modular.pojo.Menu;
import cn.lastwhisper.modular.pojo.Role;
import cn.lastwhisper.modular.service.RoleService;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @ClassName: RoleServiceImpl
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: 最后的轻语_dd43
 * @date: 2019年4月6日
 */
@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private MenuMapper menuMapper;
//	@Autowired
//	private Jedis jedis;
	@Autowired
	private JedisPool jedisPool;
	
	@Transactional(propagation =Propagation.NOT_SUPPORTED)	
	@Override
	public EasyUIDataGridResult findRolelistByPage(Integer page, Integer rows, Role role) {
		// 1.分页查询
		PageHelper.startPage(page, rows);
		List<Role> list = roleMapper.selectRolelistByPage(role);
		PageInfo<Role> pageInfo = new PageInfo<>(list);
		// 2.封装EasyUIDataGridResult
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setTotal((int) pageInfo.getTotal());
		result.setRows(pageInfo.getList());
		// 3.返回分页的结果
		return result;
	}

	@Transactional(propagation =Propagation.NOT_SUPPORTED)
	@Override
	public List<Role> findRoleName(String q) {
		List<Role> list = roleMapper.selectRoleName(q);
		return list;
	}

	@LogAnno(operateType = "删除角色")
	@Override
	public GlobalResult updateRole(Role role) {
		try {
			if (role != null) {
				Integer row = roleMapper.updateRole(role);
				if (row > 0) {
					return new GlobalResult(200, "角色信息更新成功", null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new GlobalResult(400, "角色信息更新失败", null);
	}

	@LogAnno(operateType = "添加角色")
	@Override
	public GlobalResult addRole(Role role) {
		if (role != null) {
			Integer row = roleMapper.insertRole(role);
			if (row > 0) {
				return new GlobalResult(200, "角色信息添加成功", null);
			}
		}
		return new GlobalResult(400, "角色信息添加失败", null);
	}
	
	@Transactional(propagation =Propagation.NOT_SUPPORTED)
	@Override
	public EasyUIDataGridResult findRoleList() {
		List<Role> list = roleMapper.selectRoleList();
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);
		result.setTotal(list.size());
		return result;
	}
	@LogAnno(operateType = "删除角色")
	@Override
	public GlobalResult deleteRoleById(Integer uuid) {
		if (uuid == null) {
			return new GlobalResult(400, "角色id为空，添加失败！", 400);
		}
		Integer integer = roleMapper.deleteRoleById(uuid);
		if (integer == 0) {
			return new GlobalResult(400, "用户删除失败", null);
		} else {
			return new GlobalResult(200, "用户删除成功", null);
		}
	}

	@Override
	public List<EasyUIOptionalTreeNode> findRoleMenuByRoleid(Integer roleUuid) {
		// 1.根据角色id获取角色对应的菜单id
		List<String> menuIdList = roleMapper.selectRoleMenuidByRoleid(roleUuid);
		// 2.获取一级菜单
		List<Menu> parentMenu = menuMapper.selectMenuIdName("0");
		// 3.当前角色对象对应的菜单权限
		List<EasyUIOptionalTreeNode> treeList = new ArrayList<EasyUIOptionalTreeNode>();
		// 暂存一级菜单
		EasyUIOptionalTreeNode t1 = null;
		// 暂存二级菜单
		EasyUIOptionalTreeNode t2 = null;
		// 一级菜单遍历
		for (Menu m1 : parentMenu) {
			t1 = new EasyUIOptionalTreeNode();
			t1.setId(m1.getMenuid());
			t1.setText(m1.getMenuname());
			List<Menu> leafMenu = menuMapper.selectMenuIdName(m1.getMenuid());
			// 二级菜单遍历
			for (Menu m2 : leafMenu) {
				t2 = new EasyUIOptionalTreeNode();
				t2.setId(m2.getMenuid());
				t2.setText(m2.getMenuname());
				// 如果角色下包含有这个权限菜单，让它勾选上
				for (String menuId : menuIdList) {
					if (m2.getMenuid().equals(menuId)) {
						t2.setChecked(true);
					}
				}
				t1.getChildren().add(t2);
			}
			treeList.add(t1);
		}
		return treeList;
	}


	@LogAnno(operateType = "更新角色对应权限菜单")
	@Override
	public GlobalResult updateRoleMenu(Integer roleuuid, String checkedIds) {
		Jedis jedis = jedisPool.getResource();
		try {
			// 清空角色下的权限菜单
			roleMapper.deleteMenuidByRoleid(roleuuid);
			// 权限角色id
			if (checkedIds != null) {
				String[] ids = checkedIds.split(",");
				for (String menuUuid : ids) {
					roleMapper.insertRolemenu(menuUuid, roleuuid);
				}
			}
			List<Integer> userIdList = roleMapper.selectUseridByRoleuuid(roleuuid);
			for (Integer userId : userIdList) {
				
				jedis.del("menusEasyui_" + userId);
				jedis.del("menusList_" + userId);
			}
			System.out.println("更新角色对应的对应的权限菜单 ，清除缓存");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(jedis!=null)jedis.close();
		}
		return GlobalResult.build(200, "权限设置成功");
	}
}
