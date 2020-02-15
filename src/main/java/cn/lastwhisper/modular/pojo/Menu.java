/**  
 * @Title:  Menu.java   
 * @Package cn.lastwhisper.pojo   
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 最后的轻语_dd43     
 * @date:   2019年4月6日 下午3:41:53   
 * @version V1.0 
 */
package cn.lastwhisper.modular.pojo;

import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName: Menu
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: 最后的轻语_dd43
 * @date: 2019年4月6日
 */
public class Menu {
	private String menuid;// 编号
	private String menuname;// 名称
	private String url;// 对应URL
	private String icon;// 图标样式
	private String pid;// 上一级菜单编号
	private Integer is_parent;// 该菜单是否为父菜单，1为true，0为false
	private List<Menu> menus = new LinkedList<Menu>();
	/**  
	 * @Title:  getMenuid <BR>  
	 * @Description: please write your description <BR>  
	 * @return: String <BR>  
	 */
	public String getMenuid() {
		return menuid;
	}
	/**
	 * @param menuid the menuid to set
	 */
	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}
	/**  
	 * @Title:  getMenuname <BR>  
	 * @Description: please write your description <BR>  
	 * @return: String <BR>  
	 */
	public String getMenuname() {
		return menuname;
	}
	/**
	 * @param menuname the menuname to set
	 */
	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}
	/**  
	 * @Title:  getUrl <BR>  
	 * @Description: please write your description <BR>  
	 * @return: String <BR>  
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**  
	 * @Title:  getIcon <BR>  
	 * @Description: please write your description <BR>  
	 * @return: String <BR>  
	 */
	public String getIcon() {
		return icon;
	}
	/**
	 * @param icon the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}
	/**  
	 * @Title:  getPid <BR>  
	 * @Description: please write your description <BR>  
	 * @return: String <BR>  
	 */
	public String getPid() {
		return pid;
	}
	/**
	 * @param pid the pid to set
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}
	/**  
	 * @Title:  getIs_parent <BR>  
	 * @Description: please write your description <BR>  
	 * @return: Integer <BR>  
	 */
	public Integer getIs_parent() {
		return is_parent;
	}
	/**
	 * @param is_parent the is_parent to set
	 */
	public void setIs_parent(Integer is_parent) {
		this.is_parent = is_parent;
	}
	/**  
	 * @Title:  getMenus <BR>  
	 * @Description: please write your description <BR>  
	 * @return: List<Menu> <BR>  
	 */
	public List<Menu> getMenus() {
		return menus;
	}
	/**
	 * @param menus the menus to set
	 */
	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

}
