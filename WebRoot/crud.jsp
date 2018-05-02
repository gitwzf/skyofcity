<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>

<html>
<head>
	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width" />
    <title>编辑页</title>
	<script type="text/javascript" src="js/jquery1.8.2.min.js"></script>
	<script type="text/javascript" src="js/main.js?f=3"></script> 
	<link type="text/css" href="timpicker/css/jquery-ui-1.8.17.custom.css" rel="stylesheet" />
    <link type="text/css" href="timpicker/css/jquery-ui-timepicker-addon.css" rel="stylesheet" />
    <script type="text/javascript" src="timpicker/js/jquery-ui-1.8.17.custom.min.js"></script>
    <script type="text/javascript" src="timpicker/js/jquery-ui-timepicker-addon.js"></script>
    <script type="text/javascript" src="timpicker/js/jquery-ui-timepicker-zh-CN.js"></script>
    <script type="text/javascript" charset="utf-8" src="ueditor1.4/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="ueditor1.4/ueditor.all.min.js"> </script>
     <script type="text/javascript" charset="utf-8" src="ueditor1.4/lang/zh-cn/zh-cn.js"></script>
	
	<link rel="stylesheet" type="text/css" href="css/style0.css" />
	<script type="text/javascript">
	  $(function () {
		$('#table').change(function(){
		//	alert("选择了表");
		});  

        $("input[type0='datetime']").datetimepicker({
        //showOn: "button",
        //buttonImage: "./css/images/icon_calendar.gif",
        //buttonImageOnly: true,
        showSecond: true,
        timeFormat: 'hh:mm:ss',
        stepHour: 1,
        stepMinute: 1,
        stepSecond: 1
    })
    $("input[type0='date']").datetimepicker({
        //showOn: "button",
        //buttonImage: "./css/images/icon_calendar.gif",
        //buttonImageOnly: true,
        showHour: false,
        showMinute: false,
        showSecond: false,
        timeFormat: ''
    })
    
    var script=$('.edit-box').find("script").each(function(i,v){
         var that=$(v);
         UE.getEditor(that.attr("id"));
    });
    
    })
    var x1,y1;
    function mousePos(e){
var x,y;
var e = e||window.event;
return {
x:e.clientX+document.body.scrollLeft+document.documentElement.scrollLeft,
y:e.clientY+document.body.scrollTop+document.documentElement.scrollTop
};
};
    function showdown(e){
x1=mousePos(e).x;
y1=mousePos(e).y;
}
function showup(e){
var x0=mousePos(e).x;
var y0=mousePos(e).y;
var top=$('.edit-box').css('top').replace('px','');
var left=$('.edit-box').css('left').replace('px','');
$('.edit-box').css('top',Number(top)+(y0-y1)+'px');
$('.edit-box').css('left',Number(left)+(x0-x1)+'px');
}
	</script>
</head>
	<body>
		<div style="overflow:hidden" >
			<i class="new-btn"></i>
		<!-- 	<i class="delete-btn" style="float:left"></i>   -->
			<form id="searchform" method="get">
			<input type="text" style="float:left" name="svalue"><i class="search-btn"></i>
			<select name="table" id="table" data-role='none' class="salary">
                               <option value="">请选择查询表</option>
                               <%int i=0; %>
                               <c:forEach items="${tables}" var="year">
                                <option value="<%=i++ %>">${year}</option>
                               </c:forEach>
                            </select>
            <select name="field" id="field" data-role='none' class="salary">
                               <option value="">请选择查询字段</option>
                               <c:forEach items="${titles}" var="year0">
                                <option value="${year0.name }">${year0.comment }</option>
                               </c:forEach>
                            </select>                
                            
                            </form>
		</div>
		<table border=1>
			 <tr>
				<c:forEach items="${titles}" var="title">
				<th>${title.comment}</th>
				</c:forEach>
				<th>修改</th>
				<th>删除</th>
		    </tr>
		    <c:forEach items="${datalist}" var="data">
		    <tr class="line">
				 <c:forEach items="${pageScope.data}" var="da">
				<td>${da}</td>
				</c:forEach>
				<th><span class="edit-btn"></span></th>
				<th><i class="delete-btn"></i></th>
			</tr>
			</c:forEach>
		</table>
		<div class="edit-box"   onmousedown="showdown(event)" onmouseup="showup(event)">
			<form id="editorform" method="post">
			<c:forEach items="${titles}" var="title">
			<c:if test="${title.type!='text'}">
				<div class="item"><label readonly>${title.comment }</label><input type="text"  name="editoritem" class="editoritem" length0="${title.length }" type0="${title.type }" comment0="${title.comment }"/></div>
			</c:if>
			<c:if test="${title.type=='text'}">
				<div class=""><label>${title.comment }</label>
				           <script  name="editoritem" id="editoritem${title.name}" class="editoritem"  type0="${title.type }" comment0="${title.comment }"></script>
				</div>
			</c:if>
			</c:forEach>
			</form>
		<!-- 	 <div class="upload-photo">
				<form id="photo" name="photo" action="" enctype="multipart/form-data" method="post">

					<label>对应图标</label>
					<div class="upload-box">
						<img id="pre" src="" />
					</div> 
					<input id="" name="" class="file" type="file" /> 
					<input class="choose-btn" type="button" value="选择">
					<input class="upload-btn" type="button" value="保存">
				</form>   
			</div>    -->
			
			<input class="can-btn" type="button" value="取消">
			<input class="sav-btn" type="button" value="保存">
			<div class="cancel"></div>
		</div>
		</body>
</html>