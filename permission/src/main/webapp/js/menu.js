$(function() {
	loadTree();
	// 添加数据对话框
	$('#insertDlg').dialog({
		title : '添加数据',
		width : 360,
		height : 200,
		closed : true,
		modal : true,
		buttons : [ {
			text : '保存',
			handler : function() {
				// 访问后台数据
				insertData();
			}
		}, {
			text : '关闭',
			handler : function() {
				// 关闭对话框
				$("#insertDlg").dialog('close');
			}
		} ]
	});
	// 修改数据对话框
	$('#updateDlg').dialog({
		title : '修改数据',
		width : 400,
		height : 260,
		closed : true,
		modal : true,
		buttons : [ {
			text : '保存',
			handler : function() {
				// 访问后台数据
				updateData();
			}
		}, {
			text : '关闭',
			handler : function() {
				// 关闭对话框
				$("#updateDlg").dialog('close');
			}
		} ]
	});
	// 右键菜单
	$('#mm').menu({
		onClick : function(item) {
			var rowData = $('#grid').datagrid('getData').rows[0];
			switch (item.text) {
			case '添加':
				// 获取当前被选中的节点
				var selected = $('#tt').tree('getSelected');
				var children = selected.children;
				if(children.length==0){
					$.messager.alert("提示", "该菜单目录暂时不支持三级以上的菜单", 'warning');
				}else{
					$("#insertDlg").dialog('open');
				}
				break;
			case '修改':
				$("#updateDlg").dialog('open');
				// 填充后台数据
				if (rowData.is_parent == 1) {
					rowData.is_parent = '是';
				} else if (rowData.is_parent == 0) {
					rowData.is_parent = '否';
				}
				$('#updateForm').form('load', rowData);
				break;
			case '重命名':
				$("#renameDlg").dialog('open');
				// 填充后台数据
				$('#renameForm').form('load', rowData);
				break;
			case '删除':
				deleteData(item.id);
				break;
			}
		}
	});
	// 菜单重命名
	$('#renameDlg').dialog({
		title : '菜单重命名',
		width : 250,
		height : 100,
		closed : true,
		modal : true,
		buttons : [ {
			text : '保存',
			handler : function() {
				// 访问后台数据
				renameMenu();
			}
		}, {
			text : '关闭',
			handler : function() {
				// 关闭对话框
				$("#renameDlg").dialog('close');
			}
		} ]
	});
});

/**
 * 加载两侧菜单
 */
function loadTree() {
	$.ajax({
		type : 'POST',
		url : 'menu/menulist',
		dataType : 'json',
		success : function(rtn) {
			disposeTree(rtn);
		}
	});
}
/**
 * 处理左侧菜单
 */
function disposeTree(data) {
	// 加载左侧菜单
	$('#tt').tree({
		data : data,
		onContextMenu : function(e, node) {
			e.preventDefault();
			var rowData = $('#grid').datagrid('getRows')[0];
			if (rowData != null) {
				// 找到菜单项
				var item = $('#mm').menu('findItem', '删除');
				if (1 == rowData.is_parent) {
					if (item) {
						// 移除菜单项
						$('#mm').menu('removeItem', item.target);
					}
				} else {
					if (item == null) {
						// 追加一个顶部菜单
						$('#mm').menu('appendItem', {
							text : '删除',
							iconCls : 'icon-cut',
							onClick : function(item) {
								deleteData(rowData.id);
							}
						});
					}
				}
				// 显示快捷菜单
				$('#mm').menu('show', {
					left : e.pageX,
					top : e.pageY
				});
			} else {
				$.messager.alert("提示", "请选中后操作", 'warning');
			}
		},
		onClick : function(node) {
			// 显示子菜单到datagrid
			loadDataGrid(node.id);
		}
	});
}

/**
 * 加载表格数据 menuid：菜单id
 */
function loadDataGrid(menuid) {
	$('#grid').datagrid({
		url : 'menu/menufindById?menuid=' + menuid,
		columns : [ [ {
			field : 'menuid',
			title : '编号',
			width : 100
		}, {
			field : 'menuname',
			title : '名称',
			width : 100
		}, {
			field : 'url',
			title : '对应URL',
			width : 100
		}, {
			field : 'icon',
			title : '图标样式',
			width : 100
		}, {
			field : 'pid',
			title : '上级菜单编号',
			width : 100,
		}, {
			field : 'is_parent',
			title : '是否为父菜单',
			width : 100,
			formatter : function(value, rowData, index) {
				if (rowData.is_parent == 1) {
					return '是';
				} else {
					return '否';
				}
			}
		} ] ],
		loading : true,
		striped : true,
		rownumbers : true,
		singleSelect : true
	});
	$('#grid').datagrid({
		toolbar : [ {
			iconCls : 'icon-add',
			text : '添加',
			handler : function() {
				// 打开添加窗口
				var selected = $('#tt').tree('getSelected');
				var children = selected.children;
				if(children.length==0){
					$.messager.alert("提示", "该菜单目录暂时不支持三级以上的菜单", 'warning');
				}else{
					$("#insertDlg").dialog('open');
				}
			}
		}, '-', {
			iconCls : 'icon-save',
			text : '修改',
			handler : function() {
				var rowData = $('#grid').datagrid('getSelected');
				if (null != rowData) {
					// 打开修改窗口
					$('#updateDlg').dialog('open');
					// 填充后台数据
					if (rowData.is_parent == 1) {
						rowData.is_parent = '是';
					} else if(rowData.is_parent == 0){
						rowData.is_parent = '否';
					}
					$('#updateForm').form('load', rowData);
				} else {
					$.messager.alert("提示", "请选中要修改的行", 'error');
				}
			}
		}, '-', {
			iconCls : 'icon-cut',
			text : '删除',
			handler : function() {
				// 删除
				var rowData = $('#tt').tree('getSelected');
				if (null != rowData) {
					if (rowData.id % 100 == 0) {
						$.messager.alert("提示", "父级菜单不可删除", 'error');
					} else {
						deleteData(rowData.id);
					}
				} else {
					$.messager.alert("提示", "请选中要删除数据", 'error');
				}
			}
		} ],
		onDblClickRow : function(rowIndex, rowData) {
			// 打开修改窗口
			$('#updateDlg').dialog('open');
			// 填充后台数据
			if (rowData.is_parent == 1) {
				rowData.is_parent = '是';
			} else if (rowData.is_parent == 0){
				rowData.is_parent = '否';
			}
			$('#updateForm').form('load', rowData);
		}
	});
}

/**
 * 添加数据
 */
function insertData() {
	var rowData = $('#grid').datagrid('getData').rows[0];
	// 提交添加数据的表单
	var formData = $('#insertForm').serializeJSON();
	formData.pid = rowData.menuid;
	formData.is_parent = rowData.is_parent;
	$.ajax({
		type : 'POST',
		url : 'menu/menuadd',
		data : formData,
		dataType : 'json',
		success : function(data) {
			$.messager.alert("提示", data.msg, 'info', function() {
				if (data.status == 200) {
					// 刷新表格数据
					$('#grid').datagrid('reload');
					// 刷新树形菜单
					loadTree();
					// 关闭对话框
					$('#insertDlg').dialog('close');
					// 清除表单数据
					$('#insertForm').form('clear');
				}
			});
		}
	});
}

/**
 * 删除数据
 */
function deleteData(menuid) {
	var gridData = $('#grid').datagrid('getData').rows[0];
	$.messager.confirm('警告', '确认要删除“' + gridData.menuname + '”菜单吗?',function(r) {
				if (r) {
					$.ajax({
						type : 'POST',
						url : 'menu/menudelete',
						data : {
							'menuid' : menuid
						},
						dataType : 'json',
						success : function(data) {
							$.messager.alert("提示", data.msg, 'info',function() {
												if (data.status == 200) {
													// 刷新表格数据
//													$('#grid').datagrid('deleteRow', 0);
//													loadDataGrid();
													// 刷新树形菜单
													loadTree();
												}
											});
						}
					});
				}
			});
}

/**
 * 修改数据
 */
function updateData() {
	var formData = $('#updateForm').serializeJSON();
	if(formData.is_parent == '是'){
		formData.is_parent = 1;
	} else{
		formData.is_parent = 0;
	}
	$.ajax({
		type : 'POST',
		url : 'menu/menuupdate',
		data : formData,
		dataType : 'json',
		success : function(data) {
			$.messager.alert("提示", data.msg, 'info', function() {
				if (data.status == 200) {
				// 刷新表格数据
				$('#grid').datagrid('reload');
				// 刷新树形菜单
				loadTree();
				// 关闭对话框
				$('#updateDlg').dialog('close');
				// 清除表单数据
				$('#updateForm').form('clear');
				}
			});
		}
	});
}
/**
 * 重命名菜单
 */
function renameMenu() {
	var formData = $('#renameForm').serializeJSON();
	formData.menuid = $('#tt').tree('getSelected').id;
	$.ajax({
		type : 'POST',
		url : 'menu/menuupdateById',
		data : formData,
		dataType : 'json',
		success : function(data) {
			$.messager.alert("提示", data.msg, 'info', function() {
				if (data.status == 200) {
					// 刷新表格数据
					$('#grid').datagrid('reload');
					// 刷新树形菜单
					loadTree();
					// 关闭对话框
					$('#renameDlg').dialog('close');
					// 清除表单数据
					$('#renameForm').form('clear');
				}
			});
		}
	});
}