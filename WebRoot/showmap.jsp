<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="java.net.URLDecoder"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
<title><%=URLDecoder.decode((String)request.getAttribute("query"),"utf-8")%></title>
<style type="text/css">/*<![CDATA[*/
body{margin:0;padding:0;font-family:Times New Roman, serif}
p{margin:0;padding:0}
html,body{
    width:100%;
    height:100%;
}
#map_container{height:100%; border: 1px solid #999;height:100%;}
@media print{
 #notes{display:none}
  #map_container{margin:0}
}
BMap_bubble_content {
    font-size: 16px;
    line-height: 16px;
}
/*]]>*/
</style>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.4"></script>
<script type="text/javascript" src="http://api.map.baidu.com/getscript?v=1.4&ak=&services=&t=20130607025020"></script>
</head>
<body>
<div style="overflow: hidden; position: relative; z-index: 0; background-color: rgb(243, 241, 236); color: rgb(0, 0, 0); text-align: left;" id="map_container">
</div>
<script type="text/javascript">
var mp = new BMap.Map("map_container",{
enableHighResolution: true 
});
 <%=URLDecoder.decode((String)request.getAttribute("str"),"utf-8")%>
 
mp.enableInertialDragging();
mp.enableScrollWheelZoom(); 
</script>
</body></html>