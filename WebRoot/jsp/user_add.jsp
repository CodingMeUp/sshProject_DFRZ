<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ include file="../commons/taglibs.jsp" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
<base href="<%=basePath%>">
    
<title>My JSP 'index.jsp' starting page</title>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>
<link rel="stylesheet" type="text/css" href="${ctx}/jquery-easyui-1.3.6/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/jquery-easyui-1.3.6/themes/icon.css">
<script type="text/javascript" src="${ctx}/js/tool.js"></script>
<script type="text/javascript" src="${ctx}/jquery-easyui-1.3.6/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/jquery-easyui-1.3.6/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/jquery-easyui-1.3.6/locale/easyui-lang-zh_CN.js"></script>

	
<script language="javascript">
function saveUser(){
	//使用ajax提交表单的数据。
	var formData = jQuery("#form1").serializeArray();
	var saveURL = "<%=path%>/userAction!save?date="+new Date()+"";
	jQuery.post(saveURL,formData,function(jsonData){
		var flag 			= jsonData.flag;
		var errormsg 		= jsonData.errormsg;
		if (flag == true){
			jQuery.messager.confirm("操作提示","用户添加成功，是否继续执行添加的操作?",function(btn){
				if (btn == true){
					document.getElementById("form1").reset();
				}else{
					parent.jQuery('#add_dialog_div').dialog("close");
					parent.jQuery("#user_grid_div").datagrid("reload");				
				}
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
  
<body class="easyui-layout">   
<div data-options="region:'center'">
	<form action="" id="form1" >
		<table border="0" width="100%" style="font-family: '新宋体';font-size: 13px;">
			<tr height="25px;">
				<td width="10%" align="right">用户ID号：</td>
				<td width="40%" align="left"><s:textfield name="userid" id="userid" 
														  cssClass="easyui-numberbox"
														  />
				</td>
			</tr>
			<tr height="25px;">
				<td align="right">用户名：</td>
				<td align="left">
					<s:textfield name="username" id="username" cssStyle="width:320px;"/>
				</td>
			</tr> 
			<tr height="25px;">
				<td align="right">真实姓名：</td>
				<td align="left">
					<s:textfield name="truename" id="truename" cssStyle="width:320px;"/>
				</td>
				
			</tr>   
			<tr height="25px;">
				<td align="right">性别：</td>
				<td align="left"><s:radio list="#{1:'男',0:'女'}" name="usersex" id="usersex"/></td>
				
			</tr>   
			<tr height="25px;">
				<td align="right">地址：</td>
				<td align="left">
					<s:textfield name="address" id="address" cssStyle="width:320px;"/>
				</td>
				
			</tr>   				  
			
			<tr height="25px;">
				<td align="right">用户权限：</td>
				<td align="left">
					<s:checkboxlist list="#roleList" name="roleListChk" listKey="ROLE_ID" listValue="ROLE_NAME"/>
				</td>
			</tr>     		   		 		   		
		</table>
	</form> 
</div>  
<div align="right" style="padding-top: 3px;" data-options="region:'south',height:31,border:false,noheader:true"> 
	 <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-save'"  	onclick="saveUser();">保存数据</a> 
	 &nbsp;&nbsp;&nbsp;     
	 <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"   onclick="closeWin();">关闭窗口</a>
	 &nbsp;&nbsp;      
 </div> 
</body>
</html>
