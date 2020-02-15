// Ajax 文件下载
$.download = function(url, data){    // 获得url和data
    var inputs = '';    
    $.each(data, function(name, value) {        	
        inputs+='<input type="hidden" name="'+ name +'" value="'+ value +'" />'; 
    }); 
    $('<form action="'+ url +'" method="post">'+inputs+'</form>')
    .appendTo('body').submit().remove();        
};