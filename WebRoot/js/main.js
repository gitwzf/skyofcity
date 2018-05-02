$(function(){ 
var flag=true;
	$('.edit-btn').click(function(){
		var tr=$(this).parent().parent();
		var etd=$('.editoritem');//alert($(etd[1]).attr('type'));
		var td=tr.find("td").each(function(i,v){
			var that=$(v);
		//	alert($(etd[i]).attr("type0"));
			if($(etd[i]).attr("type0")!="text")
			$(etd[i]).attr("value",that.html());
			else{alert(that.html());
				var editid=$(etd[i]).attr("id");
				var ue=UE.getEditor(editid);
				ue.setContent(that.html());
				}
		});
		$('.edit-box').css('display','block');
		flag=false;
	});
	$('.delete-btn').on('click',function(){
	if(flag){
		if(confirm("删除是不可恢复的，你确认要删除吗？")){
			var tr=$(this).parent().parent();
			var first=$($(this).parent().parent().children()[0]).html();
			$.post("crudServ",{first:first},function(data){
				if(data=='11') $(tr).remove();
				else
					if(data=='00')alert('删除失败');
			},'text');
			
			
			
		}
	}
	});
	$('.cancel').on('click',function(){
		$('.edit-box').css('display','none');
		flag=true;
	});
	$('.can-btn').on('click',function(){
		$('.edit-box').css('display','none');
		flag=true;
	});
	$('.new-btn').on('click',function(){
		$('.edit-box').css('display','block');
		flag=!flag;
	});
	$('.sav-btn').on('click',function(){
		$('#editorform').submit();
	//alert($($('.editoritem')[1]).val());
//		$.post("crudServ" ,{editoritem:[3,5,56,35,6]},function(data){},"json");
		
		
	});
	$('.search-btn').on('click',function(){
		$('#searchform').submit();
	});
	
	$('.editoritem').on("blur",function(){
		var length=$(this).attr("length0");
		var type=$(this).attr("type0");
		var comment=$(this).attr("comment0");
	//	alert(length+' '+type );
		//length
		if(length!=0)
		if($(this).val().length>length)
		{    
			alert(comment+'已超长度限制');
		}
		//type
		if(type=='int'){
			if(isNaN($(this).val())){
				alert(comment+'需是数字');
				$(this).attr("value","");
			}
		}
		if(type=='date'){
			
		}
		if(type=='datetime'){
			
		}
		
		
	});
	$('.editoritem').on("blur",function(){
		var length=$(this).attr("length0");
		var type=$(this).attr("type0");
		var comment=$(this).attr("comment0");
		//type
		if(type=='date'){
			
		}
		if(type=='datetime'){
			$(this).removeClass("ui_timepicker hasDatepicker");
		}
	});
	
	 
}); 
