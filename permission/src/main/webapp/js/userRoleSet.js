$(function() {
	$('#grid').datagrid({
		url : 'user/userlistByPage',
		frozenColumns : [ [ {
			field : 'ck',
			checkbox : true
		}, {
			title : '编号',
			field : 'user_id',
			sortable : true,
			width : 100
		} ] ],
		columns : [ [ {
			field : 'user_code',
			title : '账号',
			width : 100
		}, {
			field : 'user_name',
			title : '姓名',
			width : 100
		} ] ],
		singleSelect : true,// 如果为true，则只允许选择一行。
		pagination : true,// 如果为true，则在DataGrid控件底部显示分页工具栏。
		striped : true,// 是否显示斑马线效果。
		collapsible : true, // 定义是否显示可折叠按钮。
		rownumbers : true,// 如果为true，则显示一个行号列。
		nowrap : true,// 如果为true，则在同一行中显示数据。设置为true可以提高加载性能。
		sortName : 'user_id',// 定义哪些列可以进行排序。
		sortOrder : 'desc',// 定义列的排序顺序，只能是'asc'或'desc'。
		remoteSort : false,// 定义从服务器对数据进行排序。
		loading : true,//显示载入状态。
		loadMsg : '数据加载中...',// 在从远程站点加载数据的时候显示提示消息。
		pageNumber : 1,// 在设置分页属性的时候初始化页码。
		pageSize : 50,// 在设置分页属性的时候初始化页面大小。
		pageList : [ 10, 20, 30, 40, 50 ],// 在设置分页属性的时候 初始化页面大小选择列表。
		onClickRow : function(rowIndex, rowData) {
			$('#tree').tree({
				url : 'user/findUserRole?id=' + rowData.user_id,
				animate : true,
				checkbox : true
			});
		}
	});
	$('#btnSave').bind("click", function() {
		// 角色id
		var user_id = $('#grid').datagrid("getSelected").user_id;
		// 选择的节点
		var nodes = $('#tree').tree('getChecked');
		var checkedIds = new Array();
		$.each(nodes, function(i, node) {
			checkedIds.push(node.id);
		})
		// 权限菜单对应id
		checkedIds = checkedIds.join(",");
		var formData = {
			'id' : user_id,
			'checkedIds' : checkedIds
		};
		$.ajax({
			url : 'user/updateUserRole',
			data : formData,
			dataType : 'json',
			type : 'post',
			success : function(data) {
				$.messager.alert("提示", data.msg, 'info', function() {
				});
			}
		});
	});
})
