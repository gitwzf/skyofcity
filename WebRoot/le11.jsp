<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="model.Simplestyle"%>
<%@page import="model.Retxt"%>
<%@page import="pubvari.Variable"%>
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
<title>围观 - 微信公众平台自助引擎 </title>
<meta name="keywords" content="围观,微信,微信公众平台" />
<meta name="description" content="微信公众平台自助引擎，简称围观，围观是一款免费开源的微信公众平台管理系统。" />
<link type="text/css" rel="stylesheet" href="./resource/style/bootstrap.css" />
<link type="text/css" rel="stylesheet" href="./resource/style/font-awesome.css" />
<link type="text/css" rel="stylesheet" href="./resource/style/common.css?v=1382943174" />
<!-- <link type="text/css" rel="stylesheet" href="./resource/style/idapi.css" />   
<script type="text/javascript" src="./resource/script/idapi.js"></script>
-->
<script type="text/javascript" src="./resource/script/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="./resource/script/bootstrap.js"></script>
<script type="text/javascript" src="./resource/script/common.js"></script>
<script type="text/javascript" src="./resource/script/emotions.js"></script>
<script type="text/javascript">
cookie.prefix = 'f1c8_';
  
</script>
<style type="text/css">
  #xiugai{
  width:67%;
  }

</style>
</head>
<body >
<%Variable v=new Variable();%>
<%Retxt retxt=(Retxt)session.getAttribute("bynamearray");%>
<div align="center"><ul class="nav nav-tabs"> 
	<li class="active"><a href="le11.jsp">添加规则</a></li> 
	<li><a href="le111Serv">管理规则</a></li> 
</ul> 
</div> 
	<form class="form-horizontal form" action="le11Serv" method="post"  onsubmit="return formcheck(this)" accept-charset="UTF-8"> 
		<input type="hidden" name="id" value="" onblur=""> 
		<h4>添加规则 <small>删除，修改规则、关键字以及回复后，请提交规则以保存操作。</small></h4> 
		<table class="tb"> 
			<tr> 
				<th><label for="">规则名称</label></th> 
				<td> 
					<input type="text" id="rule-name" class="span6" placeholder="" name="name" value="${sessionScope.bynamearray.name}"> 
					<span class="help-block">您可以给这条规则起一个名字, 方便下次修改和查看.<a class="iconEmotion" href="javascript:;" inputid="rule-name"> 表情</a></span> 
				</td> 
			</tr> 
			<tr> 
				<th><label for="">规则描述</label></th> 
				<td> 
					<input type="text" id="rule-name" class="span6" placeholder="" name="nameintroduction" value="${sessionScope.bynamearray.nameintroduction}"> 
					<span class="help-block">给这条规则写一个简短描述,会显示在帮助菜单.</span> 
				</td> 
			</tr> 
			<tr>          <!--  document.getElementById($(this.options[this.selectedIndex]).attr('value')).display=false--> 
				<th><label for="">回复类型</label></th> 
				<td> <!-- document.getElementById('userapi').style.display='none';document.getElementById('basic').style.display='none';
document.getElementById('news').style.display='none';document.getElementById('music').style.display='none';document.getElementById($(this.options[this.selectedIndex]).attr('value')).style.display='block'; -->
<select name="module" id="module" class="span6" onchange="$(this).next().html($(this.options[this.selectedIndex]).attr('description'));document.getElementById('append-list').innerHTML='';buildAddForm($(this.options[this.selectedIndex]).attr('value')+'-item-html', $('#append-list'));document.getElementById('basic').style.display='none';"> 
																								<option value="userapi" description="自定义接口又称第三方接口，可以让开发者更方便的接入围观系统，高效的与微信公众平台进行对接整合。">自定义接口回复</option> 
																								<option value="basic" selected="selected" description="一问一答得简单对话. 当访客的对话语句中包含指定关键字, 或对话语句完全等于特定关键字, 或符合某些特定的格式时. 系统自动应答设定好的回复内容.">基本文字回复</option> 
																								<option value="news" description="一问一答得简单对话, 但是回复内容包括图片文字等更生动的媒体内容. 当访客的对话语句中包含指定关键字, 或对话语句完全等于特定关键字, 或符合某些特定的格式时. 系统自动应答设定好的图文回复内容.">基本混合图文回复</option> 
																								<option value="music" description="在回复规则中可选择具有语音、音乐等音频类的回复内容，并根据用户所设置的特定关键字精准的返回给粉丝，实现一问一答得简单对话。">基本语音回复</option> 
																								 
																								 
																</select> 
					<span class="help-block"></span> 
									</td> 
			</tr> 
			<tr> 
				<th><label for="">关键字</label></th> 
				<td> 
					<div class="keyword-list list" id="keyword-list"> 
<div name="edit_key">
   <c:forEach items="${sessionScope.bynamearray.sstyle}" var="ss">
      <%int a=(int)(Math.random()*100); %>
   <div class="item" id="keyword-item-1<%=a %>">
			<div class="alert alert-block alert-new hide" id="form" style="display: none;">
	<a data-dismiss="alert" class="close">×</a>
	<h4 class="alert-heading">添加关键字</h4>
	<table>
		<tbody><tr>
			<th>关键字</th>
			<td>
				<input type="text" value="${ss.key}" autocomplete="off" placeholder="" class="span7 keyword-name-new" name="keyword-name-new[]" id="keyword-name-keyword-item-1<%=a %>">
				<span class="help-block">根据此处设置的关键字进行对应回复，关键词单次添一个，可多次添加 <a inputid="keyword-name-keyword-item-1<%=a %>" href="javascript:;" class="iconEmotion"><i class="icon-github-alt"></i> 表情</a></span>
			</td>
		</tr>
		<tr>
			<th>对应方式</th>
			<td>
				<input type="hidden" autocomplete="off" value="1" class="span7" name="keyword-type-new[]" id="keyword-type-new">
				<div data-toggle="buttons-radio" class="btn-group">
																	<c:if test="${ss.style==1}"><span description="用户进行微信交谈时，对话内容完全等于上述关键字才会执行这条规则。" value="1" class="btn active">完全等于上述关键字</span></c:if>
																	<c:if test="${ss.style!=1}"><span description="用户进行微信交谈时，对话内容完全等于上述关键字才会执行这条规则。" value="1" class="btn ">完全等于上述关键字</span></c:if>
																	<c:if test="${ss.style==2}"><span description="用户进行微信交谈时，对话中包含上述关键字就执行这条规则。" value="2" class="btn active">包含上述关键字</span></c:if>
																	<c:if test="${ss.style!=2}"><span description="用户进行微信交谈时，对话中包含上述关键字就执行这条规则。" value="2" class="btn ">包含上述关键字</span></c:if>
																	<c:if test="${ss.style==3}"><span description="用户进行微信交谈时，对话内容符合述关键字中定义的模式才会执行这条规则。&lt;br &gt;&lt;/span&gt;&lt;b&gt;/^微擎/&lt;/b&gt;匹配以“微擎”开头的语句&lt;br /&gt;&lt;b&gt;/微擎$/&lt;/b&gt;匹配以“微擎”结尾的语句&lt;br /&gt;&lt;b&gt;/^微擎$/&lt;/b&gt;匹配等同“微擎”的语句&lt;br /&gt;&lt;b&gt;/微擎/&lt;/b&gt;匹配包含“微擎”的语句&lt;br /&gt;&lt;b&gt;/[0-9\.\-]/&lt;/b&gt;匹配所有的数字，句号和减号&lt;br /&gt;&lt;b&gt;/^[a-zA-Z_]$/&lt;/b&gt;所有的字母和下划线&lt;br /&gt;&lt;b&gt;/^[[:alpha:]]{3}$/&lt;/b&gt;所有的3个字母的单词&lt;br /&gt;&lt;b&gt;/^a{4}$/&lt;/b&gt;aaaa&lt;br /&gt;&lt;b&gt;/^a{2,4}$/&lt;/b&gt;aa，aaa或aaaa&lt;br /&gt;&lt;b&gt;/^a{2,}$/&lt;/b&gt;匹配多于两个a的字符串" value="3" class="btn active">正则表达式匹配</span></c:if>
																	<c:if test="${ss.style!=3}"><span description="用户进行微信交谈时，对话内容符合述关键字中定义的模式才会执行这条规则。&lt;br &gt;&lt;/span&gt;&lt;b&gt;/^微擎/&lt;/b&gt;匹配以“微擎”开头的语句&lt;br /&gt;&lt;b&gt;/微擎$/&lt;/b&gt;匹配以“微擎”结尾的语句&lt;br /&gt;&lt;b&gt;/^微擎$/&lt;/b&gt;匹配等同“微擎”的语句&lt;br /&gt;&lt;b&gt;/微擎/&lt;/b&gt;匹配包含“微擎”的语句&lt;br /&gt;&lt;b&gt;/[0-9\.\-]/&lt;/b&gt;匹配所有的数字，句号和减号&lt;br /&gt;&lt;b&gt;/^[a-zA-Z_]$/&lt;/b&gt;所有的字母和下划线&lt;br /&gt;&lt;b&gt;/^[[:alpha:]]{3}$/&lt;/b&gt;所有的3个字母的单词&lt;br /&gt;&lt;b&gt;/^a{4}$/&lt;/b&gt;aaaa&lt;br /&gt;&lt;b&gt;/^a{2,4}$/&lt;/b&gt;aa，aaa或aaaa&lt;br /&gt;&lt;b&gt;/^a{2,}$/&lt;/b&gt;匹配多于两个a的字符串" value="3" class="btn ">正则表达式匹配</span></c:if>
									</div>
								<span class="help-block rule-description">用户进行微信交谈时，对话内容完全等于上述关键字才会执行这条规则。</span>
							</td>
		</tr>
		<tr>
			<th></th>
			<td><button onclick="keywordHandler.doAdd('keyword-item-1<%=a %>')" class="btn btn-primary span2" type="button">添加</button></td>
		</tr>
	</tbody></table>
</div>
<div class="alert alert-info " id="show" style="display: block;">
	<span class="pull-right"><a style="margin-right:5px;" href="javascript:;" onclick="doDeleteItem('keyword-item-1<%=a %>')">删除</a><a href="javascript:;" onclick="keywordHandler.doEditItem('keyword-item-1<%=a %>')">编辑</a></span>
	<div class="content"><span id="content">${ss.key}</span>&nbsp;（<span id="type"><c:if test="${ss.style==1}">完全等于上述关键字</c:if><c:if test="${ss.style==2}">包含上述关键字</c:if><c:if test="${ss.style==3}">正则表达式匹配（待开发）</c:if></span>）</div>
</div></div>
   </c:forEach>
 </div>			
		</div> 
					<a href="javascript:;" onclick="keywordHandler.buildForm()" class="add-kw-button"> 添加关键字</a> 
				</td> 
			</tr> 
			<tr> 
				<th><label for="">回复</label></th> 
				<td id="module-form"> 
    
				<div id="append-list" class="reply-list list"> 
				

	 </div>
	<div id="userapi" name="play" style="display: none;"> 
<a href="javascript:;" onclick="buildAddForm('userapi-item-html', $('#append-list'));" class="add-reply-button"> 添加回复内容</a> 
<script type="text/html" id="userapi-item-html">
		<div id="show" class="span6 alert alert-info hide">
	<div class="content" id="content"></div>
	<span class="pull-right">
		<a onclick="doDeleteItem('(itemid)')" href="javascript:;" style="margin-right:5px;">删除</a>		<a style="margin-right:5px;" onclick="doEditItem('(itemid)')" id="" href="javascript:;">编辑</a>
	</span>
</div>
<div id="form" class="alert alert-block alert-new ">
	<a class="close" data-dismiss="alert">×</a>
	<h4 class="alert-heading">添加回复内容</h4>
	<table>
		<tr>
			<th>请输入api类中已有的方法名(注意用；号隔开方法，以对应关键字)</th>
			<td>
				<textarea style="height:200px;" class="span7 basic-content-new" cols="70" id="basic-content-(itemid)" name="basic-content-new[]" autocomplete="off"><%=retxt==null?"":retxt.content%></textarea>
				<span class="help-block">用户进行微信交谈时，对话内容完全等于上述关键字才会执行这条规则。<a class="iconEmotion" href="javascript:;" inputid="basic-content-(itemid)"><i class="icon-github-alt"></i> 表情</a></span>
			</td>
		</tr>
		</table>
</div></script> 
</div> 
<div id="basic" name="play" style="display: none;"> 
<a href="javascript:;" onclick="buildAddForm('basic-item-html', $('#append-list'));" class="add-reply-button"> 添加回复内容</a> 
<script type="text/html" id="basic-item-html">
		<div id="show" class="span6 alert alert-info hide">
	<div class="content" id="content"></div>
	<span class="pull-right">
		<a onclick="doDeleteItem('(itemid)')" href="javascript:;" style="margin-right:5px;">删除</a>		<a style="margin-right:5px;" onclick="doEditItem('(itemid)')" id="" href="javascript:;">编辑</a>
	</span>
</div>
<div id="form" class="alert alert-block alert-new ">
	<a class="close" data-dismiss="alert">×</a>
	<h4 class="alert-heading">添加回复内容</h4>
	<table>
		<tr>
			<th>内容</th>
			<td>
				<textarea style="height:200px;" class="span7 basic-content-new" cols="70" id="basic-content-(itemid)" name="basic-content-new[]" autocomplete="off"><%=retxt==null?"":retxt.content%></textarea>
				<span class="help-block">用户进行微信交谈时，对话内容完全等于上述关键字才会执行这条规则。<a class="iconEmotion" href="javascript:;" inputid="basic-content-(itemid)"><i class="icon-github-alt"></i> 表情</a></span>
			</td>
		</tr>
		</table>
</div></script> 
</div> 
<div id="news" name="play" style="display: none;"> 
			<a class="add-reply-button" onclick="buildAddForm('news-item-html', $('#append-list'));" href="javascript:;"> 添加回复内容</a> 
 
<script id="news-item-html" type="text/html">
<%int r=(retxt==null?1:retxt.news.length);%>
			<div id="news-list" name="news-list">
	<div class="alert alert-block alert-new wrap-item" id="news-wrap-item-0"><a data-dismiss="alert" class="close" >×</a><h4 class="alert-heading">添加回复内容</h4><div id="news-children-list"><div class="item" id="news-item-1">
</div>
<table class="tb reply-news-edit " >
	<tbody><tr>
		<th>标题</th>
		<td>
			<input type="text" value="<%=retxt==null?"":retxt.news[0].title%>" name="news-title-new[news-wrap-item-0][]" placeholder="" class="span7" id="">
		</td>
	</tr>
	<tr>
     <tr>
		<th>图片</th>
		<td>
			<!-- 此处增加class="reply-news-edit-cover-1"为编辑状态，反之则不显示封面图片，隐藏删除按钮 -->
			<div class="uneditable-input reply-edit-cover" id="">
				<div class="detail">
					<span class="pull-right">提醒：大图横宽比最好7:3</span>
					<div class="ke-inline-block"><iframe style="display:block;" name="upload_iframe" id="upload_iframe" height="50pix" scrolling="no" width="90pix"></iframe><form action="./index.php?act=image" target="upload_iframe" enctype="multipart/form-data" method="post" >
<span style="cursor:pointer;"></span><input type="file" tabindex="-1" name="imgFile" style="width: 100px;" onchange="picsub('')"></form></div>或直接填写url<input type="text" value="<%=retxt==null?"":retxt.news[0].urlo%>" name="news-picture-new[news-wrap-item-0][]"/>
				</div>
								<div id="upload-file-view"></div>
							</div>
		</td>
	</tr>
	<tr>
		<th>描述</th>
		<td>
			<textarea name="news-description-new[news-wrap-item-0][]" cols="70" class="span7" style="height:80px;"><%=retxt==null?"":retxt.news[0].main%></textarea>
		</td>
	</tr>
	<tr>
		<th>相关链接</th>
		<td>
			<input type="text" value="<%=v.url%>" name="news-url-new[news-wrap-item-0][]" placeholder="" class="span7" id="">
			<span class="help-block">（注：链接需加http://）</span>
		</td>
	</tr>
</tbody></table>  <input type="hidden" name="pic-count" value="1"/>
</div></div>
<div class="reply-news-edit-button" id="xiugai">*类别：<select name="news-type[]"><option value="0"><%=v.news_item%></option></select>*对应编号：<input type="text" name="news-typeid[]" value="<%=retxt==null?"":retxt.news[0].pid%>" id="ch" onclick="show()" onblur="document.getElementById('showul').style.display='none'"/><div id="showul"><ul></ul></div><div><a href="javascript:;" onclick="showitem()" class="btn"><i class="icon-plus"></i> 添加多条内容</a></div></div>  
<div id="news-list" name="news-list" style="display: none">
	<div class="alert alert-block alert-new wrap-item" id="news-wrap-item-0"><a data-dismiss="alert" class="close" >×</a><h4 class="alert-heading">添加回复内容</h4><div id="news-children-list"><div class="item" id="news-item-1">
</div>
<table class="tb reply-news-edit " >
	<tbody><tr>
		<th>标题</th>
		<td>
			<input type="text" value="<%=retxt==null?"":(r>1?retxt.news[1].title:"")%>" name="news-title-new[news-wrap-item-0][]" placeholder="" class="span7" id="">
		</td>
	</tr>
	<tr>
     <tr>
		<th>图片</th>
		<td>
			<!-- 此处增加class="reply-news-edit-cover-1"为编辑状态，反之则不显示封面图片，隐藏删除按钮 -->
			<div class="uneditable-input reply-edit-cover" id="">
				<div class="detail">
					<span class="pull-right">提醒：大图横宽比最好7:3</span>
					<div class="ke-inline-block"><iframe style="display:block;" name="upload_iframe1" id="upload_iframe1" height="50pix" scrolling="no" width="90pix" ></iframe><form action="./index.php?act=image" target="upload_iframe1" enctype="multipart/form-data" method="post" >
<span style="cursor:pointer;"></span><input type="file" tabindex="-1" name="imgFile" style="width: 100px;" onchange="picsub('1')"></form></div>或直接填写url<input type="text" value="<%=retxt==null?"":(r>1?retxt.news[1].urlo:"")%>" name="news-picture-new[news-wrap-item-0][]" id=""/>
				</div>
								<div id="upload-file-view"></div>
							</div>
		</td>
	</tr>
	<tr>
		<th>描述</th>
		<td>
			<textarea name="news-description-new[news-wrap-item-0][]" cols="70" class="span7" style="height:80px;"><%=retxt==null?"":(r>1?retxt.news[1].main:"")%></textarea>
		</td>
	</tr>
	<tr>
		<th>相关链接</th>
		<td>
			<input type="text" value="<%=v.url%>" name="news-url-new[news-wrap-item-0][]" placeholder="" class="span7" id="">
			<span class="help-block">（注：链接需加http://）</span>
		</td>
	</tr>
</tbody></table>
</div></div>
<div class="reply-news-edit-button" id="xiugai">*类别：<select name="news-type[]"><option value="0"><%=v.news_item%></option></select>*对应编号：<input type="text" name="news-typeid[]" value="<%=retxt==null?"":(r>1?retxt.news[1].pid:"")%>"/><div><a href="javascript:;" onclick="showitem()" class="btn"><i class="icon-plus"></i> 添加多条内容</a></div></div>  
<div id="news-list" name="news-list" style="display: none">
	<div class="alert alert-block alert-new wrap-item" id="news-wrap-item-0"><a data-dismiss="alert" class="close" >×</a><h4 class="alert-heading">添加回复内容</h4><div id="news-children-list"><div class="item" id="news-item-1">
</div>
<table class="tb reply-news-edit " >
	<tbody><tr>
		<th>标题</th>
		<td>
			<input type="text" value="<%=retxt==null?"":(r>2?retxt.news[2].title:"")%>" name="news-title-new[news-wrap-item-0][]" placeholder="" class="span7" id="">
		</td>
	</tr>
	<tr>
     <tr>
		<th>图片</th>
		<td>
			<!-- 此处增加class="reply-news-edit-cover-1"为编辑状态，反之则不显示封面图片，隐藏删除按钮 -->
			<div class="uneditable-input reply-edit-cover" id="">
				<div class="detail">
					<span class="pull-right">提醒：大图横宽比最好7:3</span>
					<div class="ke-inline-block"><iframe style="display:block;" name="upload_iframe2" id="upload_iframe2" height="50pix" scrolling="no" width="90pix" ></iframe><form action="./index.php?act=image" target="upload_iframe2" enctype="multipart/form-data" method="post" >
<span style="cursor:pointer;"></span><input type="file" tabindex="-1" name="imgFile" style="width: 100px;" onchange="picsub('2')"></form></div>或直接填写url<input type="text" value="<%=retxt==null?"":(r>2?retxt.news[2].urlo:"")%>" name="news-picture-new[news-wrap-item-0][]" id=""/>
				</div>
								<div id="upload-file-view"></div>
							</div>
		</td>
	</tr>
	<tr>
		<th>描述</th>
		<td>
			<textarea name="news-description-new[news-wrap-item-0][]" cols="70" class="span7" style="height:80px;"><%=retxt==null?"":(r>2?retxt.news[2].main:"")%></textarea>
		</td>
	</tr>
	<tr>
		<th>相关链接</th>
		<td>
			<input type="text" value="<%=v.url%>" name="news-url-new[news-wrap-item-0][]" placeholder="" class="span7" id="">
			<span class="help-block">（注：链接需加http://）</span>
		</td>
	</tr>
</tbody></table>
</div></div>
<div class="reply-news-edit-button" id="xiugai">*类别：<select name="news-type[]"><option value="0"><%=v.news_item%></option></select>*对应编号：<input type="text" name="news-typeid[]" value="<%=retxt==null?"":(r>2?retxt.news[2].pid:"")%>"/><div><a href="javascript:;" onclick="showitem()" class="btn"><i class="icon-plus"></i> 添加多条内容</a></div></div> 
<div id="news-list" name="news-list" style="display: none">
	<div class="alert alert-block alert-new wrap-item" id="news-wrap-item-0"><a data-dismiss="alert" class="close" >×</a><h4 class="alert-heading">添加回复内容</h4><div id="news-children-list"><div class="item" id="news-item-1">
</div>
<table class="tb reply-news-edit " >
	<tbody><tr>
		<th>标题</th>
		<td>
			<input type="text" value="<%=retxt==null?"":(r>3?retxt.news[3].title:"")%>" name="news-title-new[news-wrap-item-0][]" placeholder="" class="span7" id="">
		</td>
	</tr>
	<tr>
     <tr>
		<th>图片</th>
		<td>
			<!-- 此处增加class="reply-news-edit-cover-1"为编辑状态，反之则不显示封面图片，隐藏删除按钮 -->
			<div class="uneditable-input reply-edit-cover" id="">
				<div class="detail">
					<span class="pull-right">提醒：大图横宽比最好7:3</span>
					<div class="ke-inline-block"><iframe style="display:block;" name="upload_iframe3" id="upload_iframe3" height="50pix" scrolling="no" width="90pix" ></iframe><form action="./index.php?act=image" target="upload_iframe3" enctype="multipart/form-data" method="post" >
<span style="cursor:pointer;"></span><input type="file" tabindex="-1" name="imgFile" style="width: 100px;" onchange="picsub('3')"></form></div>或直接填写url<input type="text" value="<%=retxt==null?"":(r>3?retxt.news[3].urlo:"")%>" name="news-picture-new[news-wrap-item-0][]" id=""/>
				</div>
								<div id="upload-file-view"></div>
							</div>
		</td>
	</tr>
	<tr>
		<th>描述</th>
		<td>
			<textarea name="news-description-new[news-wrap-item-0][]" cols="70" class="span7" style="height:80px;"><%=retxt==null?"":(r>3?retxt.news[3].main:"")%></textarea>
		</td>
	</tr>
	<tr>
		<th>相关链接</th>
		<td>
			<input type="text" value="<%=v.url%>" name="news-url-new[news-wrap-item-0][]" placeholder="" class="span7" id="">
			<span class="help-block">（注：链接需加http://）</span>
		</td>
	</tr>
</tbody></table>
</div></div>
<div class="reply-news-edit-button" id="xiugai">*类别：<select name="news-type[]"><option value="0"><%=v.news_item%></option></select>*对应编号：<input type="text" name="news-typeid[]" value="<%=retxt==null?"":(r>3?retxt.news[3].pid:"")%>"/><div><a href="javascript:;" onclick="showitem()" class="btn"><i class="icon-plus"></i> 添加多条内容</a></div></div> 
<div id="news-list" name="news-list" style="display: none">
	<div class="alert alert-block alert-new wrap-item" id="news-wrap-item-0"><a data-dismiss="alert" class="close" >×</a><h4 class="alert-heading">添加回复内容</h4><div id="news-children-list"><div class="item" id="news-item-1">
</div>
<table class="tb reply-news-edit " >
	<tbody><tr>
		<th>标题</th>
		<td>
			<input type="text" value="<%=retxt==null?"":(r>4?retxt.news[4].title:"")%>" name="news-title-new[news-wrap-item-0][]" placeholder="" class="span7" id="">
		</td>
	</tr>
	<tr>
     <tr>
		<th>图片</th>
		<td>
			<!-- 此处增加class="reply-news-edit-cover-1"为编辑状态，反之则不显示封面图片，隐藏删除按钮 -->
			<div class="uneditable-input reply-edit-cover" id="">
				<div class="detail">
					<span class="pull-right">提醒：大图横宽比最好7:3</span>
					<div class="ke-inline-block"><iframe style="display:block;" name="upload_iframe4" id="upload_iframe4" height="50pix" scrolling="no" width="90pix" ></iframe><form action="./index.php?act=image" target="upload_iframe4" enctype="multipart/form-data" method="post" >
<span style="cursor:pointer;"></span><input type="file" tabindex="-1" name="imgFile" style="width: 100px;" onchange="picsub('4')"></form></div>或直接填写url<input type="text" value="<%=retxt==null?"":(r>4?retxt.news[4].urlo:"")%>" name="news-picture-new[news-wrap-item-0][]" id=""/>
				</div>
								<div id="upload-file-view"></div>
							</div>
		</td>
	</tr>
	<tr>
		<th>描述</th>
		<td>
			<textarea name="news-description-new[news-wrap-item-0][]" cols="70" class="span7" style="height:80px;"><%=retxt==null?"":(r>4?retxt.news[4].main:"")%></textarea>
		</td>
	</tr>
	<tr>
		<th>相关链接</th>
		<td>
			<input type="text" value="<%=v.url%>" name="news-url-new[news-wrap-item-0][]" placeholder="" class="span7" id="">
			<span class="help-block">（注：链接需加http://）</span>
		</td>
	</tr>
</tbody></table>
</div></div>
<div class="reply-news-edit-button" id="xiugai">*类别：<select name="news-type[]"><option value="0"><%=v.news_item%></select>*对应编号：<input type="text" name="news-typeid[]" value="<%=retxt==null?"":(r>4?retxt.news[4].pid:"")%>"/><div><a href="javascript:;" onclick="showitem()" class="btn"><i class="icon-plus"></i> 添加多条内容</a></div></div> 
<div id="news-list" name="news-list" style="display: none">
	<div class="alert alert-block alert-new wrap-item" id="news-wrap-item-0"><a data-dismiss="alert" class="close" >×</a><h4 class="alert-heading">添加回复内容</h4><div id="news-children-list"><div class="item" id="news-item-1">
</div>
<table class="tb reply-news-edit " >
	<tbody><tr>
		<th>标题</th>
		<td>
			<input type="text" value="<%=retxt==null?"":(r>5?retxt.news[5].title:"")%>" name="news-title-new[news-wrap-item-0][]" placeholder="" class="span7" id="">
		</td>
	</tr>
	<tr>
     <tr>
		<th>图片</th>
		<td>
			<!-- 此处增加class="reply-news-edit-cover-1"为编辑状态，反之则不显示封面图片，隐藏删除按钮 -->
			<div class="uneditable-input reply-edit-cover" id="">
				<div class="detail">
					<span class="pull-right">提醒：大图横宽比最好7:3</span>
					<div class="ke-inline-block"><iframe style="display:block;" name="upload_iframe5" id="upload_iframe5" height="50pix" scrolling="no" width="90pix" ></iframe><form action="./index.php?act=image" target="upload_iframe5" enctype="multipart/form-data" method="post" >
<span style="cursor:pointer;"></span><input type="file" tabindex="-1" name="imgFile" style="width: 100px;" onchange="picsub('5')"></form></div>或直接填写url<input type="text" value="<%=retxt==null?"":(r>5?retxt.news[5].urlo:"")%>" name="news-picture-new[news-wrap-item-0][]" id=""/>
				</div>
								<div id="upload-file-view"></div>
							</div>
		</td>
	</tr>
	<tr>
		<th>描述</th>
		<td>
			<textarea name="news-description-new[news-wrap-item-0][]" cols="70" class="span7" style="height:80px;"><%=retxt==null?"":(r>5?retxt.news[5].main:"")%></textarea>
		</td>
	</tr>
	<tr>
		<th>相关链接</th>
		<td>
			<input type="text" value="<%=v.url%>" name="news-url-new[news-wrap-item-0][]" placeholder="" class="span7" id="">
			<span class="help-block">（注：链接需加http://）</span>
		</td>
	</tr>
</tbody></table>
</div></div>
<div class="reply-news-edit-button" id="xiugai">*类别：<select name="news-type[]"><option value="0"><%=v.news_item%></option></select>*对应编号：<input type="text" name="news-typeid[]" value="<%=retxt==null?"":(r>5?retxt.news[5].pid:"")%>"/><div><a href="javascript:;" onclick="showitem()" class="btn"><i class="icon-plus"></i> 添加多条内容</a></div></div> 
<div id="news-list" name="news-list" style="display: none">
	<div class="alert alert-block alert-new wrap-item" id="news-wrap-item-0"><a data-dismiss="alert" class="close" >×</a><h4 class="alert-heading">添加回复内容</h4><div id="news-children-list"><div class="item" id="news-item-1">
</div>
<table class="tb reply-news-edit " >
	<tbody><tr>
		<th>标题</th>
		<td>
			<input type="text" value="<%=retxt==null?"":(r>6?retxt.news[6].title:"")%>" name="news-title-new[news-wrap-item-0][]" placeholder="" class="span7" id="">
		</td>
	</tr>
	<tr>
     <tr>
		<th>图片</th>
		<td>
			<!-- 此处增加class="reply-news-edit-cover-1"为编辑状态，反之则不显示封面图片，隐藏删除按钮 -->
			<div class="uneditable-input reply-edit-cover" id="">
				<div class="detail">
					<span class="pull-right">提醒：大图横宽比最好7:3</span>
					<div class="ke-inline-block"><iframe style="display:block;" name="upload_iframe6" id="upload_iframe6" height="50pix" scrolling="no" width="90pix" ></iframe><form action="./index.php?act=image" target="upload_iframe6" enctype="multipart/form-data" method="post" >
<span style="cursor:pointer;"></span><input type="file" tabindex="-1" name="imgFile" style="width: 100px;" onchange="picsub('6')"></form></div>或直接填写url<input type="text" value="<%=retxt==null?"":(r>6?retxt.news[6].urlo:"")%>" name="news-picture-new[news-wrap-item-0][]" id=""/>
				</div>
								<div id="upload-file-view"></div>
							</div>
		</td>
	</tr>
	<tr>
		<th>描述</th>
		<td>
			<textarea name="news-description-new[news-wrap-item-0][]" cols="70" class="span7" style="height:80px;"><%=retxt==null?"":(r>6?retxt.news[6].main:"")%></textarea>
		</td>
	</tr>
	<tr>
		<th>相关链接</th>
		<td>
			<input type="text" value="<%=v.url%>" name="news-url-new[news-wrap-item-0][]" placeholder="" class="span7" id="">
			<span class="help-block">（注：链接需加http://）</span>
		</td>
	</tr>
</tbody></table>
</div></div>
<div class="reply-news-edit-button" id="xiugai">*类别：<select name="news-type[]"><option value="0"><%=v.news_item%></option></select>*对应编号：<input type="text" name="news-typeid[]" value="<%=retxt==null?"":(r>6?retxt.news[6].pid:"")%>"/><div><a href="javascript:;" onclick="showitem()" class="btn"><i class="icon-plus"></i> 添加多条内容</a></div></div> 
<div id="news-list" name="news-list" style="display: none">
	<div class="alert alert-block alert-new wrap-item" id="news-wrap-item-0"><a data-dismiss="alert" class="close" >×</a><h4 class="alert-heading">添加回复内容</h4><div id="news-children-list"><div class="item" id="news-item-1">
</div>
<table class="tb reply-news-edit " >
	<tbody><tr>
		<th>标题</th>
		<td>
			<input type="text" value="<%=retxt==null?"":(r>7?retxt.news[7].title:"")%>" name="news-title-new[news-wrap-item-0][]" placeholder="" class="span7" id="">
		</td>
	</tr>
	<tr>
     <tr>
		<th>图片</th>
		<td>
			<!-- 此处增加class="reply-news-edit-cover-1"为编辑状态，反之则不显示封面图片，隐藏删除按钮 -->
			<div class="uneditable-input reply-edit-cover" id="">
				<div class="detail">
					<span class="pull-right">提醒：大图横宽比最好7:3</span>
					<div class="ke-inline-block"><iframe style="display:block;" name="upload_iframe7" id="upload_iframe7" height="50pix" scrolling="no" width="90pix" ></iframe><form action="./index.php?act=image" target="upload_iframe7" enctype="multipart/form-data" method="post" >
<span style="cursor:pointer;"></span><input type="file" tabindex="-1" name="imgFile" style="width: 100px;" onchange="picsub('7')"></form></div>或直接填写url<input type="text" value="<%=retxt==null?"":(r>7?retxt.news[7].urlo:"")%>" name="news-picture-new[news-wrap-item-0][]" id=""/>
				</div>
								<div id="upload-file-view"></div>
							</div>
		</td>
	</tr>
	<tr>
		<th>描述</th>
		<td>
			<textarea name="news-description-new[news-wrap-item-0][]" cols="70" class="span7" style="height:80px;"><%=retxt==null?"":(r>7?retxt.news[7].main:"")%></textarea>
		</td>
	</tr>
	<tr>
		<th>相关链接</th>
		<td>
			<input type="text" value="<%=v.url%>" name="news-url-new[news-wrap-item-0][]" placeholder="" class="span7" id="">
			<span class="help-block">（注：链接需加http://）</span>
		</td>
	</tr>
</tbody></table>
</div></div>
<div class="reply-news-edit-button" id="xiugai">*类别：<select name="news-type[]"><option value="0"><%=v.news_item%></option></select>*对应编号：<input type="text" name="news-typeid[]" value="<%=retxt==null?"":(r>7?retxt.news[7].pid:"")%>"/><div><a href="javascript:;" onclick="showitem()" class="btn"><i class="icon-plus"></i> 添加多条内容</a></div></div> 
<div id="news-list" name="news-list" style="display: none">
	<div class="alert alert-block alert-new wrap-item" id="news-wrap-item-0"><a data-dismiss="alert" class="close" >×</a><h4 class="alert-heading">添加回复内容</h4><div id="news-children-list"><div class="item" id="news-item-1">
</div>
<table class="tb reply-news-edit " >
	<tbody><tr>
		<th>标题</th>
		<td>
			<input type="text" value="<%=retxt==null?"":(r>8?retxt.news[8].title:"")%>" name="news-title-new[news-wrap-item-0][]" placeholder="" class="span7" id="">
		</td>
	</tr>
	<tr>
     <tr>
		<th>图片</th>
		<td>
			<!-- 此处增加class="reply-news-edit-cover-1"为编辑状态，反之则不显示封面图片，隐藏删除按钮 -->
			<div class="uneditable-input reply-edit-cover" id="">
				<div class="detail">
					<span class="pull-right">提醒：大图横宽比最好7:3</span>
					<div class="ke-inline-block"><iframe style="display:block;" name="upload_iframe8" id="upload_iframe8" height="50pix" scrolling="no" width="90pix" ></iframe><form action="./index.php?act=image" target="upload_iframe8" enctype="multipart/form-data" method="post" >
<span style="cursor:pointer;"></span><input type="file" tabindex="-1" name="imgFile" style="width: 100px;" onchange="picsub('8')"></form></div>或直接填写url<input type="text" value="<%=retxt==null?"":(r>8?retxt.news[8].urlo:"")%>" name="news-picture-new[news-wrap-item-0][]" id=""/>
				</div>
								<div id="upload-file-view"></div>
							</div>
		</td>
	</tr>
	<tr>
		<th>描述</th>
		<td>
			<textarea name="news-description-new[news-wrap-item-0][]" cols="70" class="span7" style="height:80px;"><%=retxt==null?"":(r>8?retxt.news[8].main:"")%></textarea>
		</td>
	</tr>
	<tr>
		<th>相关链接</th>
		<td>
			<input type="text" value="<%=v.url%>" name="news-url-new[news-wrap-item-0][]" placeholder="" class="span7" id="">
			<span class="help-block">（注：链接需加http://）</span>
		</td>
	</tr>
</tbody></table>
</div></div>
<div class="reply-news-edit-button" id="xiugai">*类别：<select name="news-type[]"><option value="0"><%=v.news_item%></option></select>*对应编号：<input type="text" name="news-typeid[]" value="<%=retxt==null?"":(r>8?retxt.news[8].pid:"")%>"/><div><a href="javascript:;" onclick="showitem()" class="btn"><i class="icon-plus"></i> 添加多条内容</a></div></div>  
<div id="news-list" name="news-list" style="display: none">
	<div class="alert alert-block alert-new wrap-item" id="news-wrap-item-0"><a data-dismiss="alert" class="close" >×</a><h4 class="alert-heading">添加回复内容</h4><div id="news-children-list"><div class="item" id="news-item-1">
</div>
<table class="tb reply-news-edit " >
	<tbody><tr>
		<th>标题</th>
		<td>
			<input type="text" value="<%=retxt==null?"":(r>9?retxt.news[9].title:"")%>" name="news-title-new[news-wrap-item-0][]" placeholder="" class="span7" id="">
		</td>
	</tr>
	<tr>
     <tr>
		<th>图片</th>
		<td>
			<!-- 此处增加class="reply-news-edit-cover-1"为编辑状态，反之则不显示封面图片，隐藏删除按钮 -->
			<div class="uneditable-input reply-edit-cover" id="">
				<div class="detail">
					<span class="pull-right">提醒：大图横宽比最好7:3</span>
					<div class="ke-inline-block"><iframe style="display:block;" name="upload_iframe9" id="upload_iframe9" height="50pix" scrolling="no" width="90pix" ></iframe><form action="./index.php?act=image" target="upload_iframe9" enctype="multipart/form-data" method="post" >
<span style="cursor:pointer;"></span><input type="file" tabindex="-1" name="imgFile" style="width: 100px;" onchange="picsub('9')"></form></div>或直接填写url<input type="text" value="<%=retxt==null?"":(r>9?retxt.news[9].urlo:"")%>" name="news-picture-new[news-wrap-item-0][]" id=""/>
				</div>
								<div id="upload-file-view"></div>
							</div>
		</td>
	</tr>
	<tr>
		<th>描述</th>
		<td>
			<textarea name="news-description-new[news-wrap-item-0][]" cols="70" class="span7" style="height:80px;"><%=retxt==null?"":(r>9?retxt.news[9].main:"")%></textarea>
		</td>
	</tr>
	<tr>
		<th>相关链接</th>
		<td>
			<input type="text" value="<%=v.url%>" name="news-url-new[news-wrap-item-0][]" placeholder="" class="span7" id="">
			<span class="help-block">（注：链接需加http://）</span>
		</td>
	</tr>
</tbody></table>
</div></div>
<div class="reply-news-edit-button" id="xiugai">*类别：<select name="news-type[]"><option value="0"><%=v.news_item%></option></select>*对应编号：<input type="text" name="news-typeid[]" value="<%=retxt==null?"":(r>9?retxt.news[9].pid:"")%>"/><div><a href="javascript:;" onclick="showitem()" class="btn"><i class="icon-plus"></i> 添加多条内容</a></div></div> 
</script> 
</div> 
</script> 
</div> 
 
<div id="music" name="play" style="display: none;"> 
<a class="add-reply-button" onclick="buildAddForm('music-item-html', $('#append-list'));" href="javascript:;"> 添加语音</a> 
 
<script id="music-item-html" type="text/html">

<div class="reply-list-common list" id="append-list">
<div class="item" id="music-item-1">
			<div class="span6 alert alert-info hide" id="show">
	<span class="pull-right">
		<a style="margin-right:5px;" href="javascript:;" onclick="doDeleteItem('music-item-1');">删除</a>		<a href="javascript:;" id="" onclick="doEditItem('music-item-1');musicHandler.doEditItem('music-item-1');" style="margin-right:5px;">编辑</a>
	</span>
	<div id="music_off" class="content"><span music_switch="1" music_url="" style="display:inline-block;width:20px;color:#58859b;cursor:pointer;" class="music_button"><i class="icon-play icon-large"></i></span><span id="title" class="music_title"></span><span id="jp-1" class="jp"></span></div>
</div>
<div class="alert alert-block alert-new " id="form">
	<a onclick="$(this).parents('.item').find('#form').hide();$(this).parents('.item').find('#show').show();musicHandler.doEditItem('music-item-1');" class="close" ">×</a>
	<h4 class="alert-heading">添加回复内容</h4>
	<table>
		<tbody><tr>
			<th>歌名</th>
			<td>
				<input type="text" value="<%=retxt==null?"":retxt.news[0].title%>" name="music-title-new[]" placeholder="" class="span7" id="item-title">
			</td>
		</tr>
		<tr>
			<th>链接</th>
			<td>
								<div class="input-append">
					<input type="text" placeholder="" value="<%=retxt==null?"":retxt.news[0].urlo%>" name="music-url-new[]" class="span6" id="item-url">
					<div class="ke-inline-block"><iframe style="display:block;" name="upload_iframe" height="10pix" scrolling="no" width="10pix"></iframe><form action="index2.php?act=audio&amp;do=uploadmusic&amp;name=music" target="upload_iframe" enctype="multipart/form-data" method="post" class="ke-upload-area ke-form"><span style="cursor:pointer;"><button style="font-size:14px;width:80px;margin-left:-1px;" class="btn"><i class="icon-upload-alt"></i></button></span><input type="file" tabindex="-1" name="attachFile" class="ke-upload-file" style="width: 100px;"></form></div>
				</div>
				<span class="help-block">选择上传的音频文件或直接输入URL地址，常用格式：mp3</span>
			</td>
		</tr>
		<tr>
			<th>高品质链接</th>
			<td>
				<input type="text" value="<%=retxt==null?"":(retxt.news[0].url==null?"":retxt.news[0].url)%>" name="music-hqurl-new[]" placeholder="" class="span7" id="">
				<span class="help-block">没有高品质音乐链接，请留空。高质量音乐链接，WIFI环境优先使用该链接播放音乐</span>
			</td>
		</tr>
		<tr>
		<th>描述</th>
			<td>
				<textarea name="music-description-new[]" cols="70" class="span7" style="height:80px;"><%=retxt==null?"":retxt.news[0].main%></textarea>
				<span class="help-block">描述内容将出现在音乐名称下方，建议控制在20个汉字以内最佳</span>
			</td>
		</tr>
		</tbody></table>
</div></div></div>
</td></script> 
</div> 
 
 
 
<script type="text/javascript">

   function showitem(){
  var a=document.getElementsByName("news-list");
   var b=document.getElementsByName("pic-count");
   for(var i=0;i<a.length;i++)
      if(a[i].style.display=='none'){a[i].style.display='block';break;}
   for(var i=0;i<a.length;i++)
     if(a[i].style.display!='none')
      b[0].value=i+1;
      else break;
   }
	var basicHandler = {
		'doAdd' : function(itemid) {
			var parent = $('#' + itemid);
			if ($('.basic-content-new', parent).val() == '') {
				message('请输入回复内容！', '', 'error');
				return false;
			}
			$('#show #content', parent).html($('.basic-content-new', parent).val());
			$('#show', parent).css('display', 'block');
			$('#form', parent).css('display', 'none');
			buildAddForm('basic-form-html', $('#append-list'));		}
	};
	
</script>					<script></script> 
									</td> 
			</tr> 
			<tr> 
				<th><br></th> 
				<td> 
					<button type="submit" class="btn btn-primary span3" name="submit" onclick="return subIfram()" value="提交">提交</button> 
					<input type="hidden" name="token" value="da083f61"> 
				</td> 
			</tr> 
		</table> 
	</form> 
<div align="center"> 
<script type="text/html" id="keyword-item-html">
			<div id="form" class="alert alert-block alert-new hide">
	<a class="close" data-dismiss="alert">×</a>
	<h4 class="alert-heading">添加关键字</h4>
	<table>
		<tr>
			<th>关键字</th>
			<td>
				<input type="text" id="keyword-name-(itemid)" name="keyword-name-new[]" class="span7 keyword-name-new" placeholder="" autocomplete="off" value="">
				<span class="help-block">根据此处设置的关键字进行对应回复，关键词单次添一个，可多次添加 <a class="iconEmotion" href="javascript:;" inputid="keyword-name-(itemid)"><i class="icon-github-alt"></i> 表情</a></span>
			</td>
		</tr>
		<tr>
			<th>对应方式</th>
			<td>
				<input type="hidden" id="keyword-type-new" name="keyword-type-new[]" class="span7" value="1" autocomplete="off">
				<div class="btn-group" data-toggle="buttons-radio">
																														<span class="btn active" value="1" description="用户进行微信交谈时，对话内容完全等于上述关键字才会执行这条规则。">完全等于上述关键字</span>
																	<span class="btn " value="2" description="用户进行微信交谈时，对话中包含上述关键字就执行这条规则。">包含上述关键字</span>
																	<span class="btn " value="3" description="用户进行微信交谈时，对话内容符合述关键字中定义的模式才会执行这条规则。<br /><b>/^围观/</b>匹配以“围观”开头的语句<br /><b>/围观$/</b>匹配以“围观”结尾的语句<br /><b>/^围观$/</b>匹配等同“围观”的语句<br /><b>/围观/</b>匹配包含“围观”的语句<br /><b>/[0-9\.\-]/</b>匹配所有的数字，句号和减号<br /><b>/^[a-zA-Z_]$/</b>所有的字母和下划线<br /><b>/^[[:alpha:]]{3}$/</b>所有的3个字母的单词<br /><b>/^a{4}$/</b>aaaa<br /><b>/^a{2,4}$/</b>aa，aaa或aaaa<br /><b>/^a{2,}$/</b>匹配多于两个a的字符串">正则表达式匹配</span>
									</div>
								<span class="help-block rule-description">用户进行微信交谈时，对话内容完全等于上述关键字才会执行这条规则。</span>
							</td>
		</tr>
		<tr>
			<th></th>
			<td><button type="button" class="btn btn-primary span2" onclick="keywordHandler.doAdd('(itemid)')">添加</button></td>
		</tr>
	</table>
</div>
<div id="show" class="alert alert-info ">
	<span class="pull-right"><a onclick="doDeleteItem('(itemid)')" href="javascript:;" style="margin-right:5px;">删除</a><a onclick="keywordHandler.doEditItem('(itemid)')" href="javascript:;">编辑</a></span>
	<div class="content"><span id="content"></span>&nbsp;（<span id="type">完全等于上述关键字</span>）</div>
</div></script> 
</div><script type="text/javascript">
function picsub(obj){
  var aa=document.getElementsByTagName("form");
  var bb="upload_iframe"+obj;
  for(var i=0;i<aa.length;i++)
  {//alert(aa[i].target);
  if(aa[i].target==bb)
     {
     aa[i].submit();return;
     }
     }
}

function subIfram(){
var a=document.getElementsByTagName('iframe');
var b=document.getElementsByName('news-picture-new[news-wrap-item-0][]');
var c=document.getElementsByName('music-url-new[]');
for(var i=0;i<b.length;i++){
if(b[i].value.length<10){
//alert(a[i*2].contentWindow.document.getElementsByName('_picture')[0].value);
//b[i].value=a[i].contentWindow.document.getElementsByName('_picture')[0].value;
b[i].value=a[i].contentWindow.document.getElementsByTagName('img')[0].src;
}
}
for(var i=0;i<c.length;i++){
if(c[i].value.length<10)
//c[i].value=a[i].contentWindow.document.getElementsByName('_picture')[0].value;
c[i].value=a[i].contentWindow.document.getElementsByTagName('img')[0].src;
}
//document.getElementsByName('upload_iframe')[0].contentWindow.document.getElementById('apath').value.submit;
return true;
}


	var category = null;
	var keywordHandler = {
		'buildForm' : function() {
			var obj = buildAddForm('keyword-item-html', $('#keyword-list'));
			obj.find('.btn-group .btn').on('click', function(){
				$(this).parent().next().html($(this).attr('description'));
				obj.find('#keyword-type-new').val($(this).attr('value'));
			});
			obj.find('#form').show();
			obj.find('#show').hide();
		},
		'doAdd' : function(itemid) {
			var parent = $('#' + itemid);
			if ($('.keyword-name-new', parent).val() == '') {
				message('请输入关键字！', '', 'error');
				return false;
			}
			var typetips = $('.active', parent).html();
			$('#show #type', parent).html(typetips);
			$('#show #content', parent).html($('.keyword-name-new', parent).val());
			$('#show', parent).css('display', 'block');
			$('#form', parent).css('display', 'none');
			this.buildForm();		}, 
		'doEditItem' : function(itemid) {
			$('#keyword-list .item').each(function(){
				$('#form', $(this)).css('display', 'none');
				$('#show', $(this)).css('display', 'block');		
			});
			doEditItem(itemid);
		}
	};


	function formcheck(form) {
		if (form['name'].value == '') {
			message('抱歉，规则名称为必填项，请返回修改！', '', 'error');
			return false;
		}
		if ($('.keyword-name-new').val() == '') {
			message('抱歉，您至少要设置一个触发关键字！', '', 'error');
			return false;
		}
		return true;
	}

		$(function(){
		keywordHandler.buildForm();
	//	alert("222");
 var t=<%=(session.getAttribute("bynamearray")==null?0:((Retxt)session.getAttribute("bynamearray")).type)%>;
 //alert(t);
 document.getElementById("module").options[t+1].selected="selected";   //385空值异常
if(t==-1){
 document.getElementById('append-list').innerHTML='';
  buildAddForm('userapi-item-html', $('#append-list'));
    }
    else
     if(t==0){
    document.getElementById('append-list').innerHTML='';
  buildAddForm('basic-item-html', $('#append-list'));
     //  alert(sessionScope.bynamearray.name);
   }
    else
    if(t==1){  
    document.getElementById('append-list').innerHTML='';
  buildAddForm('news-item-html', $('#append-list'));
  var ntype=document.getElementsByName("news-type[]");
  var nlist=document.getElementsByName("news-list");
 // alert("nlength="+ntitle.length);
 var j=<%=(session.getAttribute("bynamearray")==null?0:((Retxt)session.getAttribute("bynamearray")).news.length)%>;
 <%int k=0;%>
  for(var i=0;i<j;i++)
  {  
  nlist[i].style.display='block';
  }
  ntype[0].value="<%=retxt==null?"":retxt.news[0].pty%>";
  ntype[1].value="<%=retxt==null?"":(r>1?retxt.news[1].pty:"")%>";
  ntype[2].value="<%=retxt==null?"":(r>2?retxt.news[2].pty:"")%>";
  ntype[3].value="<%=retxt==null?"":(r>3?retxt.news[3].pty:"")%>";
  ntype[4].value="<%=retxt==null?"":(r>4?retxt.news[4].pty:"")%>";
  ntype[5].value="<%=retxt==null?"":(r>5?retxt.news[5].pty:"")%>";
  ntype[6].value="<%=retxt==null?"":(r>6?retxt.news[6].pty:"")%>";
  ntype[7].value="<%=retxt==null?"":(r>7?retxt.news[7].pty:"")%>";
  ntype[8].value="<%=retxt==null?"":(r>8?retxt.news[8].pty:"")%>";
  ntype[9].value="<%=retxt==null?"":(r>9?retxt.news[9].pty:"")%>";
  
  
  }
    else
    if(t==2){
    document.getElementById('append-list').innerHTML='';
  buildAddForm('music-item-html', $('#append-list'));}
	});
	//-->
</script>
	<div class="emotions" style="display:none;"></div>
</body>
</html>