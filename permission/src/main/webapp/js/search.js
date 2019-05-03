/**
 * esayui通用搜索
 */
$(function() {
	// 自动补全
	$('#inputtable').combobox(
			{
				prompt : '输入关键字后自动搜索',
				mode : 'remote',
				url : _url,// _url,_value已经在各自的js文件中定义
				valueField : _value,
				textField : _value,
				panelHeight : 'auto',
				panelMaxHeight : 150,
				editable : true,
				hasDownArrow : false,
				onBeforeLoad : function(param) {
					if (param == null || param.q == null
							|| param.q.replace(/ /g, '') == '') {
						var value = $(this).combobox('getValue');
						if (value) {// 修改的时候才会出现q为空而value不为空
							param.id = value;
							return true;
						}
						return false;
					}
				}
			});
	//回车事件绑定 搜索框是esayui动态生成的<input type="text" class="combo-text validatebox-text" autocomplete="off" style="width: 167px; height: 20px; line-height: 20px;">
    $('.combo-text').bind('keyup', function(event) {
        if (event.keyCode == "13") {
            //回车执行查询
        	//$('#btnSearch').click();
        	reloadgrid();
        }
    });
	// 点击查询按钮
	$('#btnSearch').bind('click', function() {
		reloadgrid();
	});

	// 点击重置按钮
	$('#btnReset').bind('click', function() {
		$('#searchForm').form('clear');
	});
	
	function reloadgrid() {
		// 把表单数据转换成json对象
		var formData = $('#searchForm').serializeJSON();
		$('#grid').datagrid('load', formData);
	}
})