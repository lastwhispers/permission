/**  
 * @Title:  A.java   
 * @Package cn.lastwhisper   
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 最后的轻语_dd43     
 * @date:   2019年5月1日 上午12:17:53   
 * @version V1.0 
 */
package cn.lastwhisper;

import org.apache.shiro.crypto.hash.Md5Hash;

/**   
 * @ClassName:  A   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author:     最后的轻语_dd43
 * @date:       2019年5月1日
 */
public class MD5 {
	/**
	 * 加密
	 * 
	 * @param source 密码
	 * @param salt   账号
	 * @return
	 */
	private static String encrypt(String source, String salt) {
		Md5Hash md5 = new Md5Hash(source, salt, 2);
		return md5.toString();
	}
	public static void main(String[] args) {
		System.out.println(encrypt("admin", "admin"));
	}
}
