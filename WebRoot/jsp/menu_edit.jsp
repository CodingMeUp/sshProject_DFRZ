<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ include file="../commons/taglibs.jsp" %>

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

		<title>..</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">

<link rel="stylesheet" type="text/css" href="${ctx}/jquery-easyui-1.3.6/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/jquery-easyui-1.3.6/themes/icon.css">
<script type="text/javascript" src="${ctx}/jquery-easyui-1.3.6/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/jquery-easyui-1.3.6/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/jquery-easyui-1.3.6/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/js/valid.js"></script>
<script language="javascript">
	//jQuery的初始化函数
	jQuery(function(){
		var toolArray =
		 [
			{id:"search",			text:"保存数据",		iconCls:"icon-save",		handler:function(){  saveMenu(); }   },
			{id:"select",			text:"关闭窗口",		iconCls:"icon-cancel"		   }											
		 ]
   });
	function saveMenu(){
	
	var formData = jQuery("#form1").serializeArray();
	var saveURL = "<%=path%>/menuAction!modify?date="+new Date()+"";
	jQuery.post(saveURL,formData,function(jsonData){
		var flag 			 =		 jsonData.flag;
		var errormsg 	 = 		 jsonData.errormsg;
		if (flag == true){
			jQuery.messager.alert("操作提示","数据保存成功","info",function(){
				parent.jQuery('#add_dialog_div').dialog("close");
				parent.jQuery("#menu_grid_div").treegrid("reload");  //表格内的数据菜单刷新
				parent.parent.jQuery("#tree").tree("reload");          // 左边系统菜单的刷新
			});	
		}else{
			jQuery.messager.alert("操作提示","数据保存失败，错误原因:"+errormsg,"error");	
		}
	},"json");
}	

	function closeWin(){ 
			 parent.jQuery('#add_dialog_div').dialog("close"); 
	 }	
</script>    
</head>
  
<body>   
<form action="" id="form1">
	<table border="0" width="100%" style="font-family: '微软雅黑';font-size: 13px;">
		<tr height="25px;">
			<td width="10%" align="right">菜单ID号：</td>
			<td width="40%" align="left"><s:textfield name="menu_id" id="menu_id"  readonly="true" cssClass="easyui-numberbox"  data-options="min:1,max:99999999"/></td>
		</tr>
		<tr height="25px;">
			<td align="right">菜单名称：</td>
			<td align="left"><s:textfield name="menu_name" id="menu_name" cssStyle="width:320px;"/></td>
		</tr>  	
		<tr height="25px;">
			<td align="right">菜单链接：</td>
			<td align="left"><s:textfield name="menu_href" id="menu_href" cssStyle="width:320px;" /></td>
		</tr>	   		
	</table>
</form>   
<div align="right" > 
	 <a href="javascript:void(0);" class="easyui-linkbutton" 	data-options="iconCls:'icon-save'"  		 onclick="saveMenu();">保存数据</a>      
	 <a href="javascript:void(0);" class="easyui-linkbutton" 	data-options="iconCls:'icon-cancel'"     onclick="closeWin();">关闭窗口</a>  
 </div> 
</body>
</html>
