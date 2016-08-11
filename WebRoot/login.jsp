<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="commons/taglibs.jsp"  %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>项目登录页</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		
<link rel="stylesheet" type="text/css" href="${ctx}/jquery-easyui-1.3.6/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/jquery-easyui-1.3.6/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/style.css">
<script type="text/javascript" src="${ctx}/jquery-easyui-1.3.6/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/js/tool.js"></script>
<script type="text/javascript" src="${ctx}/js/login.js"></script>
<script type="text/javascript" src="${ctx}/jquery-easyui-1.3.6/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/jquery-easyui-1.3.6/locale/easyui-lang-zh_CN.js"></script>
<style type="text/css">  /* 标签居右  */th{text-align: right;}</style>
</head>
  <body onload="createRequire()">

	<div id="loginAndRegDialog"  >
		<form id="loginInputForm" action="post">
			<table>
				<tr>
					<th width="90px;" height="15px">&nbsp</th>
					<td></td>
				</tr>
				<tr>
					<th width="90px;" height="45px;">用户名：</th>
					<td><input name="username" id="username"  class="required"  type="text"/></td>
				</tr>
				<tr>
					<th width="90px;">密&nbsp&nbsp码：</th>
					<td><input name="password"  id="password"  class="required"  type="password"/></td>
				</tr>
				<tr>
					<th width="90px;" height="15px">&nbsp</th>
					<td></td>
				</tr>
				<tr>
				      <th></th>
				      <td><img id="captcha" alt="载入中.."  title="点击更换验证码图片"   src="captcha.svl"   onclick="this.src='captcha.svl?d='+new Date()*1"/></td>
				 </tr>
			    <tr>
			         <th width="90px;">验证码：</th>
			         <td><input type="text" name="vcode"   class="required"    id="vcode"    value="" /></td>
			    </tr>
			</table>
		</form>
	</div>
	
  </body>
</html>
