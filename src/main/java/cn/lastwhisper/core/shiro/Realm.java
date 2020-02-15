package cn.lastwhisper.core.shiro;


import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import cn.lastwhisper.modular.pojo.Menu;
import cn.lastwhisper.modular.pojo.User;
import cn.lastwhisper.modular.service.MenuService;
import cn.lastwhisper.modular.service.UserService;

public class Realm extends AuthorizingRealm {
	
	@Autowired
	private UserService userService;
	@Autowired
	private MenuService menusService;
	/**
	 * 授权方法
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("授权中......");
		//获取当前登录的用户
		User user = (User)principals.getPrimaryPrincipal();
		//获取用户的所有菜单
		List<Menu> menus = menusService.findMenuListByUserid(user.getUser_id());
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		for (Menu menu : menus) {
			info.addStringPermission(menu.getMenuname());
		}
		return info;
	}

	/**
	 * 认证方法
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
			throws AuthenticationException {
		System.out.println("认证中......");
		UsernamePasswordToken upt = (UsernamePasswordToken)token;
		String pwd = new String(upt.getPassword());
//		// 根据用户名和密码查找用户
		User user = userService.findUserByCodeAndPwd(upt.getUsername(), pwd);
		if(user != null) {
			//返回认证信息
			//参数1：主角，就是登陆的用户
			//参数2：证书，就是凭证，对应密码
			//参数3：当前realm的名称
			return new SimpleAuthenticationInfo(user, pwd, getName());
		}
		return null;
	}

}
