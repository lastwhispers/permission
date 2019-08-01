package cn.lastwhisper.modular.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
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
import cn.lastwhisper.modular.mapper.RoleMapper;
import cn.lastwhisper.modular.mapper.UserMapper;
import cn.lastwhisper.modular.pojo.Role;
import cn.lastwhisper.modular.pojo.User;
import cn.lastwhisper.modular.service.UserService;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 
 * @ClassName: UserServiceImpl
 * @Description: 用户相关
 * @author: 最后的轻语_dd43
 * @date: 2019年4月30日
 */
@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
@Service
public class UserServiceImpl implements UserService {

	private int hashIterations = 2;
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private RoleMapper roleMapper;
//	@Autowired
//	private Jedis jedis;
	
	@Autowired
	private JedisPool jedisPool;

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Override
	public User findUserByCodeAndPwd(String user_code, String user_pwd) {
		// 密码加密
		user_pwd = encrypt(user_pwd, user_code);
		System.out.println(user_pwd);
		// 获取数据库用户信息
		User userinfo = userMapper.selectUserBycodeAndpwd(user_code, user_pwd);
		return userinfo;
	}

	/**
	 * 加密
	 * 
	 * @param source 密码
	 * @param salt   账号
	 * @return
	 */
	private String encrypt(String source, String salt) {
		Md5Hash md5 = new Md5Hash(source, salt, hashIterations);
		return md5.toString();
	}

	@LogAnno(operateType = "添加用户")
	@RequiresPermissions("用户管理")
	@Override
	public GlobalResult addUser(User user) {
		if (user == null) {
			return new GlobalResult(400, "用户信息为空，添加失败！", null);
		}
		// 密码不为空时，对密码进行加密
		if ("".equals(user.getUser_pwd())) {
			user.setUser_pwd(null);
		} else {
			String user_pwd = encrypt(user.getUser_pwd(), user.getUser_code());
			user.setUser_pwd(user_pwd);
		}
		Integer integer = userMapper.insertUser(user);
		if (integer == 0) {
			return new GlobalResult(400, "用户添加失败", null);
		} else {
			return new GlobalResult(200, "用户添加成功", null);
		}
	}

	@LogAnno(operateType = "更新用户信息")
	@RequiresPermissions("用户管理")
	@Override
	public GlobalResult updateUser(User user) {
		if (user == null) {
			return new GlobalResult(400, "用户信息为空，修改失败！", 400);
		}
		// 密码不为空时，对密码进行加密
		if ("".equals(user.getUser_pwd())) {
			user.setUser_pwd(null);
		} else {
			String user_pwd = encrypt(user.getUser_pwd(), user.getUser_code());
			user.setUser_pwd(user_pwd);
		}
		Integer integer = userMapper.updateUser(user);
		if (integer == 0) {
			return new GlobalResult(400, "用户信息更新失败", null);
		} else {
			return new GlobalResult(200, "用户信息更新成功", null);
		}
	}

	@LogAnno(operateType = "删除用户信息")
	@RequiresPermissions("用户管理")
	@Override
	public GlobalResult deleteUser(Integer user_id) {
		Jedis jedis = jedisPool.getResource();
		try {
			if (user_id == null) {
				return new GlobalResult(400, "用户id为空，添加失败！", 400);
			}
			Integer integer = userMapper.deleteUserById(user_id);
			if (integer == 0) {
				return new GlobalResult(400, "用户删除失败", null);
			} else {
				// 删除用户下的所有角色
				userMapper.deleteUserRole(user_id);
				// 删除用户下的所有缓存
				jedis.del("menusEasyui_" + user_id);
				jedis.del("menusList_" + user_id);
				return new GlobalResult(200, "用户删除成功", null);
			} 
		} finally {
			if(jedis!=null)jedis.close();
		}
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Override
	public EasyUIDataGridResult findUserlistByPage(User user, Integer page, Integer rows) {
		PageHelper.startPage(page, rows);
		List<User> list = userMapper.selectUserlistByPage(user);
		PageInfo<User> pageInfo = new PageInfo<>(list);
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setTotal((int) pageInfo.getTotal());
		result.setRows(pageInfo.getList());
		return result;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Override
	public List<User> findUserName(String q) {
		List<User> list = userMapper.selectUserName(q);
		return list;
	}

	@LogAnno(operateType = "更新用户密码")
	@Override
	public GlobalResult updatePwd(User user, String oldPwd, String newPwd) {
		String msg = "用户未登录";
		// 用户登录了
		if (user != null) {
			String encryptOldPwd = encrypt(oldPwd, user.getUser_code());
			// 用户密码正确
			if (encryptOldPwd.equals(user.getUser_pwd())) {
				String user_pwd = encrypt(newPwd, user.getUser_code());
				Integer row = userMapper.updatePwdById(user.getUser_id(), user_pwd);
				if (row > 0) {
					return new GlobalResult(200, "密码修改成功", null);
				} else {
					msg = "密码修改失败";
				}
			} else {
				msg = "密码错误";
			}
		}
		return new GlobalResult(400, msg, null);
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Override
	public List<EasyUIOptionalTreeNode> findUserRole(Integer user_id) {
		// 1.获取当前用户的所有角色
		List<Role> userRoleList = userMapper.selectUserRole(user_id);
		// 2.获取系统中所有角色
		List<Role> roleList = roleMapper.selectRoleList();
		// 3.设置返回值
		List<EasyUIOptionalTreeNode> treeList = new ArrayList<EasyUIOptionalTreeNode>();
		EasyUIOptionalTreeNode t1 = null;
		// 4.封装返回值将用户对应的角色设置为true
		for (Role role : roleList) {
			t1 = new EasyUIOptionalTreeNode();
			t1.setId(role.getUuid() + "");
			t1.setText(role.getName());
			// 如果用户拥有这个角色，设为true
			for (Role userRole : userRoleList) {
				if (userRole.getUuid() == role.getUuid()) {
					t1.setChecked(true);
				}
			}
			treeList.add(t1);
		}
		return treeList;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Override
	public List<Role> findUserRoleByUserid(Integer user_id) {
		return userMapper.selectUserRole(user_id);
	}

	@LogAnno(operateType = "更新用户对应角色")
	@Override
	public GlobalResult updateUserRole(Integer user_id, String checkedIds) {
		Jedis jedis = jedisPool.getResource();
		try {
			// 先删除用户下的所有角色
			userMapper.deleteUserRole(user_id);
			if (checkedIds != null) {
				String[] ids = checkedIds.split(",");
				for (String roleuuid : ids) {
					// 设置用户的角色
					userMapper.insertUserRole(user_id, Integer.parseInt(roleuuid));
				}
			}
			// 清除缓存
			jedis.del("menusEasyui_" + user_id);
			jedis.del("menusList_" + user_id);
			System.out.println("更新用户对应的角色 ，清除缓存");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (jedis!=null)jedis.close();
		}
		return GlobalResult.build(200, "保存成功");
	}

	/**
	 * 导出excel文件
	 */
	@LogAnno(operateType = "excel导出用户信息")
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Override
	public void export(OutputStream os, User user) {
		// 获取所有供应商信息
		List<User> UserList = userMapper.selectUserlistByPage(user);
		// 1.创建excel工作薄
		HSSFWorkbook wk = new HSSFWorkbook();
		// 2.创建一个工作表
		HSSFSheet sheet = wk.createSheet("系统用户");
		// 3.写入表头
		HSSFRow row = sheet.createRow(0);
		// 表头
		String[] headerName = { "账号", "密码", "真实姓名 ", "出生日期 " };
		// 列宽
		int[] columnWidths = { 6000, 6000, 6000, 6000 };
		HSSFCell cell = null;
		for (int i = 0; i < headerName.length; i++) {
			// 创建表头单元格
			cell = row.createCell(i);
			// 向表头单元格写值
			cell.setCellValue(headerName[i]);
			sheet.setColumnWidth(i, columnWidths[i]);
		}
		// 4.向内容单元格写值
		int i = 1;
		for (User u : UserList) {
			row = sheet.createRow(i);
			row.createCell(0).setCellValue(u.getUser_code());// 账号
			row.createCell(1).setCellValue("********");// 密码
			if (u.getUser_name() != null) {
				row.createCell(2).setCellValue(u.getUser_name());// "真实姓名
			}
			if (u.getUser_birthday() != null) {
				HSSFCellStyle style_date = wk.createCellStyle();
				DataFormat df = wk.createDataFormat();
				style_date.setDataFormat(df.getFormat("yyyy-MM-dd"));
				row.createCell(3).setCellValue(u.getUser_birthday());// 出生日期
				sheet.getRow(i).getCell(3).setCellStyle(style_date);
			}
			i++;
		}
		try {
			// 写入到输出流中
			wk.write(os);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 关闭工作簿
				wk.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 数据导入
	 */
	@LogAnno(operateType = "excel导入用户信息")
	@Override
	public void doImport(InputStream is) throws IOException {
		HSSFWorkbook wb = null;
		try {
			wb = new HSSFWorkbook(is);
			HSSFSheet sheet = wb.getSheetAt(0);
			// 读取数据
			// 最后一行的行号
			int lastRow = sheet.getLastRowNum();
			User user = null;
			for (int i = 1; i <= lastRow; i++) {
				// 账号
				user = new User();
				user.setUser_code(sheet.getRow(i).getCell(0).getStringCellValue());
				// 判断是否已经存在，通过账号来判断
				List<User> list = userMapper.selectUserByUserCode(user.getUser_code());
				if (list.size() > 0) {
					// 说明存在用户，需要更新
					user = list.get(0);
				}
				HSSFCell cell = null;
				// 密码
				cell = sheet.getRow(i).getCell(1);
				cell.setCellType(CellType.STRING);
				if(!cell.getStringCellValue().equals("********")) {
					user.setUser_pwd(encrypt(cell.getStringCellValue(), user.getUser_code()));
				}
				// 真实姓名
				cell = sheet.getRow(i).getCell(2);
				cell.setCellType(CellType.STRING);
				user.setUser_name(sheet.getRow(i).getCell(2).getStringCellValue());
				// 出生日期
				cell = sheet.getRow(i).getCell(3);
				cell.setCellType(CellType.NUMERIC);
//				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//				Date birthday = df.parse(sheet.getRow(i).getCell(3).getDateCellValue());
				user.setUser_birthday(sheet.getRow(i).getCell(3).getDateCellValue());
				if (list.size() == 0) {
					// 说明不存在用户信息，需要新增
					userMapper.insertUser(user);
				} else {
					// 更新用户信息
					userMapper.updateUserByUserCode(user);
				}
			}
		} finally {
			if (null != wb) {
				try {
					wb.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
