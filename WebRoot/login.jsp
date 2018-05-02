<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%><!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="Shortcut Icon" href="/cmmgc.ico">
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title> 微信公众平台自助引擎 -  Powered by WE7.CC</title>
<meta name="keywords" content="微信,微信公众平台" />
<meta name="description" content="微信公众平台自助开源的微信公众平台管理系统。" />
<link type="text/css" rel="stylesheet" href="./resource/style/bootstrap.css" />
<link type="text/css" rel="stylesheet" href="./resource/style/font-awesome.css" />
<link type="text/css" rel="stylesheet" href="./resource/style/common.css?v=1383720245" />
<script type="text/javascript" src="./resource/script/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="./resource/script/bootstrap.js"></script>
<script type="text/javascript" src="./resource/script/common.js?v=1383720245"></script>
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
<style type="text/css">
body{background:#f9f9f9;}
.login{width:598px;height:230px; margin:0 auto;padding-top:30px;background:#EEE;border:1px #ccc solid;border-top:0;}
.login .table{width:500px;margin:0 auto;}
.login .table td{border:0;}
.login .table label{font-size:16px;}
.login-hd{width:600px;margin:0 auto;overflow:hidden;margin-top:35px;font-size:20px;font-weight:600;height:40px;}
.login-hd div{float:left;width:200px; height:35px; line-height:40px;cursor:pointer;}
.login-hd div a{color:#FFF; display:block; text-decoration:none; text-align:center;}
.login-hd-bottom{width:600px;margin:0 auto;height:5px;background:#CCC;margin-top:-5px;}
</style>
<script>
$(function() {
	$('.login-hd div').each(function() {
		$(this).css({'border-bottom': '5px '+$(this).css("background-color")+' solid', 'background': 'none'});
		$(this).find('a').hide();
	});
	$('.login-hd').delegate("div", "mouseover", function(){
		$('.login-hd div').each(function() {
			$(this).css({'border-bottom': '5px '+$(this).css("border-bottom-color")+' solid', 'background': 'none'});
			$(this).find('a').hide();
		});
		$(this).css('background-color', $(this).css("border-bottom-color"));
		$(this).find('a').show();
	});
	$('.login-hd').mouseleave(function() {
		$('.login-hd div').each(function() {
			$(this).css({'border-bottom': '5px '+$(this).css("border-bottom-color")+' solid', 'background': 'none'});
			$(this).find('a').hide();
		});
	});
});
</script>
<div style="height:100px;background: #F3F3F3;border-top: 5px #478FCE solid;">
	<a href="" style="background:url('http://xiaowangzi.touclick.com/login.jpg') no-repeat center; display:block;height:100px; width:980px; margin:0 auto;"></a>
</div>
<div class="login-hd">
	<div class="badge-success"><a href="http://" target="_blank">网站</a></div>
	<div class="badge-important"><a href="http://" target="_blank">论坛</a></div>
	<div class="badge-info"><a href="http://mp.weixin.qq.com" target="_blank">公众平台</a></div>
</div>
<div class="login-hd-bottom"></div>
<form action="Login" method="post">
<div class="login">
	<table class="table">
		<tr>
			<td style="width:120px;"><label>用户名：</label></td>
			<td><input type="text" class="span4" autocomplete="off" name="username" value="${clientuser}"></td>
		</tr>
		<tr>
			<td><label>密码：</label></td>
			<td><input type="password" class="span4" autocomplete="off" name="password" value=""></td>
		</tr>
		<tr>
			<td><label for="remember" class="checkbox inline"><input type="checkbox" name="rember" value="1" id="remember"> 记住账号</label></td>
		<td><label  class="checkbox inline">自动登陆：<select name="saveTime"> 
		<option value="0">选择期限</option> 
		 <option value="1">一天</option> 
		<option value="7">一周</option> 
		<option value="30">一月</option> 
    <option value="366">一年</option> 
    </select></label>  </td>
		</tr>
		<tr>
			<td></td>
			<td><input type="submit" name="submit" class="btn span2" value="登录"/><input type="hidden" name="token" value="0f1b5942" /></td>
		</tr>
	</table>
</div>
</form>

	<div class="emotions" style="display:none;"></div>
</body>
</html>