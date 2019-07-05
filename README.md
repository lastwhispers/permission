>该项目源码地址：https://github.com/ggb2312/permission
项目在线地址：http://47.100.54.177:8080/permission/login.html
初始用户：  
系统管理员：admin/admin   
经理：manager/manager  
销售主管：salemanager/salemanager  
销售员：saler/saler  
临时工：casual/casual

#  1. 简介
基于SSM框架简单的后台管理系统，整合spring + springmvc + shiro + mybatis + esayui。包含许多基础模块（用户管理、角色管理、菜单管理、角色权限管理、用户角色管理、日志管理、数据库监控）。

# 2. 项目演示

## 2.1 登录

使用md5加密，shiro权限校验

![登录页面](https://upload-images.jianshu.io/upload_images/5336514-35db64080a6380a3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 2.2 用户管理

**（1）ajax异步模糊搜索**

![ajax异步模糊搜索](https://upload-images.jianshu.io/upload_images/5336514-f8185c6d41a9069e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![模糊搜索](https://upload-images.jianshu.io/upload_images/5336514-7c25e83056b0f550.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

**（2）新增**

![新增](https://upload-images.jianshu.io/upload_images/5336514-5258e07b370b2b5d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![新增成功](https://upload-images.jianshu.io/upload_images/5336514-33e7ee0115168b84.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

**（3）修改**

点击修改

![修改](https://upload-images.jianshu.io/upload_images/5336514-8df5d8fc75e2e309.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

填写修改的信息

![修改成功](https://upload-images.jianshu.io/upload_images/5336514-94f68f820be65b99.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

**（4）删除**

选中一行，即可删除

![删除](https://upload-images.jianshu.io/upload_images/5336514-ef7079816396baa5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![删除成功](https://upload-images.jianshu.io/upload_images/5336514-1cad448b9e0e88ef.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

**（5）导出excel**

支持excel导出

![excel导出](https://upload-images.jianshu.io/upload_images/5336514-d4317323b8e74f0f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![excel](https://upload-images.jianshu.io/upload_images/5336514-2fc5491e0d47869c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

**（6）excel导入**

支持excel导入，并更新导入信息。
修改excel已存在的数据，并新增一行。

![修改excel](https://upload-images.jianshu.io/upload_images/5336514-09f917d627b83858.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

将修改的excel导入系统

![导入excel](https://upload-images.jianshu.io/upload_images/5336514-6b2fe6ebcab58851.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![上传成功](https://upload-images.jianshu.io/upload_images/5336514-94c13b6bd010db30.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![excel导入](https://upload-images.jianshu.io/upload_images/5336514-0e54cfe1d5b289cb.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 2.3 角色管理

与用户管理类似。

![角色管理](https://upload-images.jianshu.io/upload_images/5336514-25e2314daee40c9c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 2.4 菜单管理

菜单管理对应系统左侧的菜单，不同权限用户看到不同的菜单。

![菜单管理](https://upload-images.jianshu.io/upload_images/5336514-3004f2acc7659995.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

并且支持菜单的添加修改删除，以及菜单详细内容的修改。

![添加修改删除](https://upload-images.jianshu.io/upload_images/5336514-f43e97d24d32e59b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![菜单详细内容](https://upload-images.jianshu.io/upload_images/5336514-0dc718b8c94bc9e0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 2.5 角色权限管理

通过角色控制用户可以看到的菜单。

![角色权限管理](https://upload-images.jianshu.io/upload_images/5336514-65bcd0482fe6524d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 2.6 用户角色管理

在该页面给用户设置角色，可以是多个角色。

![初始](https://upload-images.jianshu.io/upload_images/5336514-178b4649b20edac1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![新增角色](https://upload-images.jianshu.io/upload_images/5336514-9df03afd1187fa47.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![保存成功](https://upload-images.jianshu.io/upload_images/5336514-94412e81c4101a00.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

刷新页面，新增的角色生效。

![image.png](https://upload-images.jianshu.io/upload_images/5336514-b6cd80ab63edb7a3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


## 2.7 日志管理

使用aop日志，记录用户的操作。刚才的操作日志，都被记录了下来。

![日志管理](https://upload-images.jianshu.io/upload_images/5336514-577f9c1bfe8cdd3c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 2.8 数据库监控

集成druid的监控页面。

![数据库监控](https://upload-images.jianshu.io/upload_images/5336514-6e22e68f965afdbd.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



# 3. 系统权限设计思路

1) 每个用户登陆进去看到的菜单应该取决于他们所拥有的权限，对于不同的用户，他们进入系统后看到的菜单可能是不同的。

2) 如果对每个用户都去设置权限，操作起来是非常繁琐的。实际中很多用户的权限是一样的。我们把这多个权限合起来，给它一个名称叫角色（有的系统称为“用户组”）。这样我们通过角色设置权限，即一个角色可以有多个权限（菜单项），一个权限也可以被多个角色同时拥有。在这种情况下，角色和权限之间就是典型的多对多关联

3) 每个角色包含了很多权限（菜单），那么用户指定为某种角色，即拥有该角色的权限。实际中一个用户可能同时拥有多种角色，而一个角色又包含了很多个用户。那么用户与角色之间又是典型的多对多关系。

4) 一个标准的权限系统包括哪些表呢？刚才我们提到了用户、角色、权限，这就是三张表。用户与角色的关系是多对多，对于多对多关系，我们通常会使用中间表来存储它们的关系。那么对应的角色与权限的关系，也会有中间表。因此，权限系统涉及的表就有5个了。

![系统权限](https://upload-images.jianshu.io/upload_images/5336514-9afdb6918b86d639.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

通过用户对应的角色加载角色对应的菜单。
集成shiro做后端url级别鉴权、方法级别鉴权，甚至方法内的鉴权。由于shiro鉴权每次都要查询数据库，所以使用redis缓存菜单信息。

# 4. 日志系统设计思路

使用aop环绕通知记录方法的操作。
参考：https://www.cnblogs.com/gj-blog/p/10803600.html

# 5. 如何运行项目

**第一步：启动redis**
**第二步：导入sql文件**
**第三步：导入项目，修改db.properties中的数据库账号密码**
**第四步：启动maven项目**

