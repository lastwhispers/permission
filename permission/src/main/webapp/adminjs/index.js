window.onload = function() {
	$('#loading-mask').fadeOut();
}
var onlyOpenTitle = "欢迎使用";// 不允许关闭的标签的标题

var _menus = {
	"icon" : "icon-sys",
	"menuid" : "0",
	"menuname" : "系统菜单",
	"menus" : [ {
		"icon" : "icon-sys",
		"menuid" : "100",
		"menuname" : "基础数据",
		"menus" : [ {
			"icon" : "icon-sys",
			"menuid" : "101",
			"menuname" : "商品类型管理",
			"url" : "goodstype.html"
		}, {
			"icon" : "icon-sys",
			"menuid" : "102",
			"menuname" : "商品管理",
			"url" : "goods.html"
		} ]
	} ]
};

$(function() {

	// 显示登陆用户名
	showName();
	// 获取菜单数据
	$.ajax({
		url : 'menu/loadMenus',
		type : 'post',
		dataType : 'json',
		success : function(rtn) {
			// 给菜单赋值
			_menus = rtn;
			InitLeftMenu();
		}
	});
	// 初始化标签页
	tabClose();
	tabCloseEven();
	// 安全退出
	$('#loginOut').bind('click', function() {
		$.ajax({
			url : 'user/logout',
			success : function() {
				location.href = "login.html";
			}
		});
	});

})

/**
 * 显示登陆用户名
 */
function showName() {
	$.ajax({
		url : 'user/showName',
		dataType : 'json',
		contentType : 'utf-8',
		success : function(rtn) {
			if (rtn.status == 200) {
				$('#user_name').html(rtn.msg);
			} else if (rtn.status == 400) {
				$.messager.confirm('确认对话框', '您未登录，请先登录系统！！！', function(r) {
					if (r) {
						window.location.href = "login.html";
					} else {
						window.location.href = "login.html";
					}
				});
			}
		}
	});
}

// 初始化左侧
function InitLeftMenu() {
	$("#nav").accordion({
		animate : true,
		fit : true,
		border : true
	});
	var selectedPanelname = '';

	$.each(_menus.menus, function(i, n) {
		var menulist = '';
		menulist += '<ul class="navlist">';
		$.each(n.menus, function(j, o) {
			menulist += '<li><div ><a ref="' + o.menuid + '" href="#" rel="'
					+ o.url + '" ><span class="icon ' + o.icon
					+ '" >&nbsp;</span><span class="nav">' + o.menuname
					+ '</span></a></div> ';
			
//			  if(o.child && o.child.length>0) {
//			  // li.find('div').addClass('icon-arrow');
//			  
//			  menulist += '<ul class="third_ul">';
//			  $.each(o.child,function(k,p){ menulist += '<li><div><a
//			  ref="'+p.menuid+'" href="#" rel="' + p.url + '" ><span
//			  class="icon '+p.icon+'" >&nbsp;</span><span class="nav">' +
//			  p.menuname + '</span></a></div> </li>' }); menulist += '</ul>'; }
			 
			menulist += '</li>';
		})
		menulist += '</ul>';

		$('#nav').accordion('add', {
			title : n.menuname,
			content : menulist,
			border : false,
			iconCls : 'icon ' + n.icon
		});

		if (i == 0)
			selectedPanelname = n.menuname;

	});

	$('#nav').accordion('select', selectedPanelname);

	$('.navlist li a').click(function() {
		var tabTitle = $(this).children('.nav').text();

		var url = $(this).attr("rel");
		var menuid = $(this).attr("ref");
		var icon = $(this).find('.icon').attr('class');

		var third = find(menuid);
		if (third && third.child && third.child.length > 0) {
			$('.third_ul').slideUp();

			var ul = $(this).parent().next();
			if (ul.is(":hidden"))
				ul.slideDown();
			else
				ul.slideUp();
		} else {
			addTab(tabTitle, url, icon);
			$('.navlist li div').removeClass("selected");
			$(this).parent().addClass("selected");
		}
	}).hover(function() {
		$(this).parent().addClass("hover");
	}, function() {
		$(this).parent().removeClass("hover");
	});
	// 选中第一个
//	 var panels = $('#nav').accordion('panels');
//	 var t = panels[0].panel('options').title;
//	 $('#nav').accordion('select', t);
}
// 获取左侧导航的图标
function getIcon(menuid) {
	var icon = 'icon ';
	$.each(_menus.menus, function(i, n) {
		$.each(n.menus, function(j, o) {
			if (o.menuid == menuid) {
				icon += o.icon;
			}
		})
	})

	return icon;
}

function find(menuid) {
	var obj = null;
	$.each(_menus.menus, function(i, n) {
		$.each(n.menus, function(j, o) {
			if (o.menuid == menuid) {
				obj = o;
			}
		});
	});

	return obj;
}

function addTab(subtitle, url, icon) {
	if (!$('#tabs').tabs('exists', subtitle)) {
		$('#tabs').tabs('add', {
			title : subtitle,
			content : createFrame(url),
			closable : true,
			icon : icon
		});
	} else {
		$('#tabs').tabs('select', subtitle);
		$('#mm-tabupdate').click();
	}
	tabClose();
}

function createFrame(url) {
	var s = '<iframe scrolling="auto" frameborder="0"  src="' + url
			+ '" style="width:100%;height:100%;"></iframe>';
	return s;
}

function tabClose() {
	/* 双击关闭TAB选项卡 */
	$(".tabs-inner").dblclick(function() {
		var subtitle = $(this).children(".tabs-closable").text();
		$('#tabs').tabs('close', subtitle);
	})
	/* 为选项卡绑定右键 */
	$(".tabs-inner").bind('contextmenu', function(e) {
		$('#mm').menu('show', {
			left : e.pageX,
			top : e.pageY
		});

		var subtitle = $(this).children(".tabs-closable").text();

		$('#mm').data("currtab", subtitle);
		$('#tabs').tabs('select', subtitle);
		return false;
	});
}

// 绑定右键菜单事件
function tabCloseEven() {

	$('#mm').menu({
		onClick : function(item) {
			closeTab(item.id);
		}
	});

	return false;
}

function closeTab(action) {
	var alltabs = $('#tabs').tabs('tabs');
	var currentTab = $('#tabs').tabs('getSelected');
	var allTabtitle = [];
	$.each(alltabs, function(i, n) {
		allTabtitle.push($(n).panel('options').title);
	})

	switch (action) {
	case "refresh":
		var iframe = $(currentTab.panel('options').content);
		var src = iframe.attr('src');
		$('#tabs').tabs('update', {
			tab : currentTab,
			options : {
				content : createFrame(src)
			}
		})
		break;
	case "close":
		var currtab_title = currentTab.panel('options').title;
		$('#tabs').tabs('close', currtab_title);
		break;
	case "closeall":
		$.each(allTabtitle, function(i, n) {
			if (n != onlyOpenTitle) {
				$('#tabs').tabs('close', n);
			}
		});
		break;
	case "closeother":
		var currtab_title = currentTab.panel('options').title;
		$.each(allTabtitle, function(i, n) {
			if (n != currtab_title && n != onlyOpenTitle) {
				$('#tabs').tabs('close', n);
			}
		});
		break;
	case "closeright":
		var tabIndex = $('#tabs').tabs('getTabIndex', currentTab);

		if (tabIndex == alltabs.length - 1) {
			alert('亲，后边没有啦 ^@^!!');
			return false;
		}
		$.each(allTabtitle, function(i, n) {
			if (i > tabIndex) {
				if (n != onlyOpenTitle) {
					$('#tabs').tabs('close', n);
				}
			}
		});

		break;
	case "closeleft":
		var tabIndex = $('#tabs').tabs('getTabIndex', currentTab);
		if (tabIndex == 1) {
			alert('亲，前边那个上头有人，咱惹不起哦。 ^@^!!');
			return false;
		}
		$.each(allTabtitle, function(i, n) {
			if (i < tabIndex) {
				if (n != onlyOpenTitle) {
					$('#tabs').tabs('close', n);
				}
			}
		});

		break;
	case "exit":
		$('#closeMenu').menu('hide');
		break;
	}
}

// 弹出信息窗口 title:标题 msgString:提示信息 msgType:信息类型 [error,info,question,warning]
function msgShow(title, msgString, msgType) {
	$.messager.alert(title, msgString, msgType);
}

// 设置登录窗口
function openPwd() {
	$('#w').dialog({
		title : '修改密码',
		width : 300,
		height : 180,
		modal : true,
		closed : true,
		buttons : [ {
			text : '保存',
			iconCls : 'icon-save',
			handler : function() {
				// 提交保存
				var oldPwd = $('#txtOldPass').val();
				var newPwd = $('#txtNewPass').val();
				var rePwd = $('#txtRePass').val();

				if (oldPwd == '') {
					$.messager.alert('提示', '原密码不能为空', 'info');
					return;
				}

				if (newPwd == '') {
					$.messager.alert('提示', '新密码不能为空', 'info');
					return;
				}

				if (rePwd != newPwd) {
					$.messager.alert('提示', '确认密码不一致', 'info');
					return;
				}

				$.ajax({
					url : 'user/updatePwd',
					data : {
						"oldPwd" : oldPwd,
						"newPwd" : newPwd
					},
					dataType : 'json',
					type : 'post',
					success : function(rtn) {
						$.messager.alert('提示', rtn.msg, 'info', function() {
							if (rtn.status==200) {
								$('#w').dialog('close');
								// 清空内容
								$('#txtOldPass').val('');
								$('#txtNewPass').val('');
								$('#txtRePass').val('');
							}
						});
					}
				});
			}
		}, {
			text : '关闭',
			iconCls : 'icon-cancel',
			handler : function() {

			}
		} ]
	});
}
// 关闭登录窗口
function closePwd() {
	$('#w').window('close');
}

// 修改密码
function serverLogin() {
	var $newpass = $('#txtNewPass');
	var $rePass = $('#txtRePass');

	if ($newpass.val() == '') {
		msgShow('系统提示', '请输入密码！', 'warning');
		return false;
	}
	if ($rePass.val() == '') {
		msgShow('系统提示', '请在一次输入密码！', 'warning');
		return false;
	}

	if ($newpass.val() != $rePass.val()) {
		msgShow('系统提示', '两次密码不一至！请重新输入', 'warning');
		return false;
	}

	$.post('/ajax/editpassword.ashx?newpass=' + $newpass.val(), function(msg) {
		msgShow('系统提示', '恭喜，密码修改成功！<br>您的新密码为：' + msg, 'info');
		$newpass.val('');
		$rePass.val('');
		close();
	})

}

$(function() {
	openPwd();
	$('#editpass').click(function() {
		$('#w').window('open');
	});
	$('#btnEp').click(function() {
		serverLogin();
	})
	$('#btnCancel').click(function() {
		closePwd();
	})

});
