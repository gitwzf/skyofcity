<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>微擎 - 微信公众平台自助引擎 -  Powered by WE7.CC</title>
<meta name="keywords" content="微擎,微信,微信公众平台" />
<meta name="description" content="微信公众平台自助引擎，简称微擎，微擎是一款免费开源的微信公众平台管理系统。" />
<link type="text/css" rel="stylesheet" href="./resource/style/bootstrap.css" />
<link type="text/css" rel="stylesheet" href="./resource/style/font-awesome.css" />
<link type="text/css" rel="stylesheet" href="./resource/style/common.css?v=1383055527" />
<script type="text/javascript" src="./resource/script/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="./resource/script/bootstrap.js"></script>
<script type="text/javascript" src="./resource/script/common.js?v=1383055527"></script>
<script type="text/javascript" src="./resource/script/emotions.js"></script>
<script type="text/javascript">
cookie.prefix = 'f1c8_';
</script>
<!--[if IE 7]>
<link rel="stylesheet" href="./resource/style/font-awesome-ie7.min.css">
<![endif]-->
<!--[if lte IE 6]>
<link rel="stylesheet" type="text/css" href="./resource/style/bootstrap-ie6.min.css">
<link rel="stylesheet" type="text/css" href="./resource/style/ie.css">
<![endif]-->
</head>
<body >
<ul class="nav nav-tabs">
	<li><a href="le11.jsp">添加规则</a></li>
	<li class="active"><a href="">管理规则</a></li>
</ul>
<div class="main">
	<div class="search">
		<form action="le111Serv" method="get">
		<input type="hidden" name="act" value="display" />
		<table class="table table-bordered tb">
			<tbody>
				<tr>
					<th>规则类型</th>
					<td>
					<ul class="nav nav-pills">
						<li class='active'><a href="le111Serv?kind=">全部</a></li><li ><a href="le111Serv?kind=0">基本文字回复</a></li><li ><a href="le111Serv?kind=1">基本混合图文回复</a></li><li ><a href="le111Serv?kind=2">基本音频回复</a></li><li ><a href="le111Serv?kind=-1">自定义接口回复</a></li>																																										<li class="dropdown">
							<ul class="dropdown-menu">
																																																																																																																																																																<li ><a href="rule.php?act=display&module=wxwall">微信墙</a></li>																							</ul>
						</li>
					</ul>
					</td>
				</tr>
				
				<tr>
					<th>关键字</th>
					<td>
							<input class="span6" name="word" id="" type="text" value="">
					</td>
				</tr>
				 <tr class="search-submit">
					<td colspan="2"><button class="btn pull-right span2"><i class="icon-search icon-large"></i> 搜索</button></td>
				 </tr>
			</tbody>
		</table>
		</form>
	</div>
	<div class="rule">
	        <c:forEach items="${rearray}" var="txt">
	         <c:if test="${!empty kinds}"> <c:if test="${txt.type==kinds}">
				<table class="tb table table-bordered">
			<tr class="control-group">
				<td class="rule-content">
					<h4><span class="pull-right"><c:if test="${txt.type!='-1'}"><a onclick="return confirm('删除规则将同时删除关键字与回复，确认吗？');return false;" href="le111Serv?name=${txt.name}&act=delete">删除</a><a href="le11Serv?name=${txt.name}&type=${txt.type}">编辑</a></c:if></span>
						${txt.name} <small><c:if test="${txt.type=='1'}">(基本混合图文回复）</c:if><c:if test="${txt.type=='0'}">(基本文字回复）</c:if><c:if test="${txt.type=='-1'}">(自定义接口回复）</c:if><c:if test="${txt.type=='2'}">(基本音频回复）</c:if></small>
					</h4>
				</td>
			</tr>
			<tr class="control-group">
				<td class="rule-kw">
					<div>
					                   <c:forEach items="${pageScope.txt.key}" var="a">
												<span>${a}</span>
										</c:forEach>
											</div>
				</td>
			</tr>
			<tr class="control-group">
				<td class="rule-manage">
					<span class="rule-type pull-right">
															</span>
										<div>
						<c:if test="${txt.identity!='welcome'}"><a href="le111Serv?name=${txt.name}&act=setwelcome" onclick="" style="color:#FF3300">设为欢迎信息</a></c:if><c:if test="${txt.identity=='welcome'}"><a href="le111Serv?name=${txt.name}&act=delwelcome" onclick="" style="color:#FF3300">取消欢迎</a></c:if>
						                               <c:if test="${txt.identity!='default'}"><a href="le111Serv?name=${txt.name}&act=setdefault" onclick="">设为默认回复</a></c:if><c:if test="${txt.identity=='default'}"><a href="le111Serv?name=${txt.name}&act=deldefault" onclick="">取消默认</a></c:if>
											</div>
				</td>
			</tr>
		</table>
		</c:if></c:if>
		<c:if test="${empty kinds}"><c:if test="${txt.type!='-1'}">
				<table class="tb table table-bordered">
			<tr class="control-group">
				<td class="rule-content">
					<h4><span class="pull-right"><a onclick="return confirm('删除规则将同时删除关键字与回复，确认吗？');return false;" href="le111Serv?name=${txt.name}&act=delete">删除</a><a href="le11Serv?name=${txt.name}&type=${txt.type}">编辑</a></span>
						${txt.name} <small><c:if test="${txt.type=='1'}">(基本混合图文回复）</c:if><c:if test="${txt.type=='0'}">(基本文字回复）</c:if><c:if test="${txt.type=='-1'}">(自定义接口回复）</c:if><c:if test="${txt.type=='2'}">(基本音频回复）</c:if></small>
					</h4>
				</td>
			</tr>
			<tr class="control-group">
				<td class="rule-kw">
					<div>
					                   <c:forEach items="${pageScope.txt.key}" var="a">
												<span>${a}</span>
										</c:forEach>
											</div>
				</td>
			</tr>
			<tr class="control-group">
				<td class="rule-manage">
					<span class="rule-type pull-right">
															</span>
					<div>
							<c:if test="${txt.identity!='welcome'}"><a href="le111Serv?name=${txt.name}&act=setwelcome" onclick="" style="color:#FF3300">设为欢迎信息</a></c:if><c:if test="${txt.identity=='welcome'}"><a href="le111Serv?name=${txt.name}&act=delwelcome" onclick="" style="color:#FF3300">取消欢迎</a></c:if>
						                               <c:if test="${txt.identity!='default'}"><a href="le111Serv?name=${txt.name}&act=setdefault" onclick="">设为默认回复</a></c:if><c:if test="${txt.identity=='default'}"><a href="le111Serv?name=${txt.name}&act=deldefault" onclick="">取消默认</a></c:if>
														</div>
				</td>
			</tr>
		</table>
		</c:if>
		</c:if>
		</c:forEach>
				
			</div>
	</div>
<script type="text/javascript">
 
</script>
	
	<div class="emotions" style="display:none;"></div>
</body>
</html>