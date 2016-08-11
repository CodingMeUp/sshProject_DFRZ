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
<script type="text/javascript" src="${ctx}/js/tool.js"></script>
<script type="text/javascript" src="${ctx}/jquery-easyui-1.3.6/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/jquery-easyui-1.3.6/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/jquery-easyui-1.3.6/locale/easyui-lang-zh_CN.js"></script>
	
	
<script language="javascript">
	//jQuery的初始化函数
	jQuery(function(){
		var toolArray = [
			{id:"search",			text:"保存数据",		iconCls:"icon-save",		handler:function(){
				saveRole();
			}},
			{id:"select",			text:"关闭窗口",		iconCls:"icon-cancel"}
		];
		jQuery("#role_menu_div").tree({
			url:				"<%=path%>/roleAction!loadMenuTree?date="+new Date()+"",
			method:				"GET",
			lines:					true ,
			checkbox:			true
		}); 	
	});
	
function saveRole(){
	var checkTreeNodes = $('#role_menu_div').tree('getChecked', ['checked','indeterminate']);
	if (checkTreeNodes == null || checkTreeNodes.length == 0){
		jQuery.messager.alert("操作提示","不能执行保存的操作，您还未选择角色的权限","error");
		return;	
	}
	var total_menu_id = "";	
	for(var i = 0;i<checkTreeNodes.length;i++){
		var treeNode = checkTreeNodes[i];
		
		var menu_id = treeNode.id;
		if (total_menu_id == ""){
			total_menu_id = menu_id;
		}else{
			total_menu_id = total_menu_id + "," + menu_id;
		}	
	}
	jQuery("#total_menu_id").val(total_menu_id);
	
	//使用ajax提交表单的数据。
	var formData = jQuery("#form1").serializeArray();
	var saveURL = "<%=path%>/roleAction!save?date="+new Date()+"";
	jQuery.post(saveURL,formData,function(jsonData){
		var flag 				=	 	jsonData.flag;
		var errormsg 		= 		jsonData.errormsg;
		if (flag == true){
			jQuery.messager.alert("操作提示","数据保存成功","info",function(){
				parent.jQuery('#add_dialog_div').dialog("close");
				parent.jQuery("#role_grid_div").datagrid("reload");
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
	<table border="0" width="100%" style="font-family: '新宋体';font-size: 13px;">
		<tr height="25px;">
			<td width="10%" align="right">角色ID号：</td>
			<td width="40%" align="left"><s:textfield name="role_id" id="role_id" maxlength="10"   cssClass="easyui-numberbox" data-options="min:1,max:999" />
			</td>
		</tr>
		<tr height="25px;">
			<td align="right">角色名称：</td>
			<td align="left">
				<s:textfield name="role_name" id="role_name" cssStyle="width:320px;"/>
			</td>
			
		</tr> 
		<tr height="25px;">
			<td align="right">角色备注：</td>
			<td align="left">
				<s:textfield name="role_remark" id="role_remark" cssStyle="width:320px;"/>
			</td>
			
		</tr>     
		
		<tr height="25px;">
			<td align="right">角色权限：</td>
			<td align="left"><div id="role_menu_div" style="height: 200px;overflow-y:scroll; "></div>
			</td>
		</tr>    
		<tr height="25px;" style="display: none;">
			<td align="right">勾选的菜单ID号：</td>
			<td align="left"><s:textfield name="total_menu_id" id="total_menu_id"/>
			</td>
		</tr>  		   		 		   		
	</table>
</form>   
<div align="right" > 
	 <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-save'"  		 onclick="saveRole();">保存数据</a>      
	 <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"  	 onclick="closeWin();">关闭窗口</a>  
 </div> 
</body>
</html>
