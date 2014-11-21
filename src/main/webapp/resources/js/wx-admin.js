var WxAdmin = function () {
    return {
    	init:function(){
    		//刪除
    		$("[data-fn=remove]").click(function(){
    			if(!confirm("确认刪除？")){
    				return;
    			}
    			var $this=$(this);
    			var $tr=$this.closest("tr");
    			var url=window.base+"/wx_admin/delete";
    			var param={}
    				param.id=$this.attr("id");
    			var callback=function(data){
    				if(data &&　data["isOK"]){
    					$tr.remove();
    					window.location.reload();
    				}
    			}
    			$.get(url,param,callback,'json')
    		})
    		
    	}
    }
}();