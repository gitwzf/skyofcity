//   $(function(){
//     $('#showul ul li').bind('click',function(){ 
//    alert($(this).text());
//});
//});
  
     function show(){
      jQuery.ajax({
             type:"GET",
             url:"Dbapi",
             data:({"pub":"ch"}),
             dataType:"text",
             cache:true,
             contentType:"application/x-www-form-urlencoded;charset=utf-8",
             async:false,
             success:function(result){
               alert(result);
               if(result!=""){
            	   $('#showul ul').html("");
               for(var i=0;i<result.split(";").length;i++)
            	   $('#showul ul').append("<li align='left'>"+result.split(";")[i]+"</li>");
                 $('#showul').css("display","block");
               }
             }
         });

     }