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
			{id:"search",			text:"查询数据",		iconCls:"icon-search",		handler:function(){
				searchRole();
			}},
			{id:"select",			text:"选择角色",		iconCls:"icon-tip",				handler:function(){
				checkRole();
			}},
			{id:"addRole",			text:"添加角色",		iconCls:"icon-add",			handler:function(){
				addRole();
			}},
			{id:"edit",				text:"修改角色",		iconCls:"icon-edit",			handler:function(){
				editRole();
			}},
			{id:"delete",			text:"删除角色",		iconCls:"icon-remove",		handler:function(){
				deleteRole();
			}},
			{id:"refresh",			text:"刷新列表",		iconCls:"icon-reload",		handler:function(){
				jQuery("#role_grid_div").datagrid("reload");
				
			}}
		];
		jQuery("#role_grid_div").datagrid({
			rownumbers:		true,
			striped:		true,
			fit:			true,  
			remoteSort:		true,  		//配合字段的属性使用
			pagination:			true,
			pageList:			[10,30,50],
			pageSize:			30,
			toolbar:				toolArray,
			loadMsg:		"正在加载数据，请稍等...",
		    url:			"<%=path%>/roleAction!listGridJson?date="+new Date()+"",    
		    columns:[[    
		    	{field:"ck",				checkbox:true},    
		        {field:"role_id",			width:100,		title:"角色ID号",			align:"center",			sortable:true},    
		        {field:"role_name",	width:350,		title:"角色名称",			align:"center",			halign:"center",	sortable:true},    
		        {field:"role_remark",	width:605,		title:"角色备注",		  	align:'center',			halign:"center"}    
		    ]]    
		}); 
		
		function deleteRole(){
			var selectArray = jQuery("#role_grid_div").datagrid("getSelections");
			if (selectArray == null || selectArray.length == 0){
				jQuery.messager.alert("操作提示","无法执行该操作，您还未选择数据","error");
			}else{
				var delete_role_id = "";
				for(var i = 0;i<selectArray.length;i++){
					var rowObj = selectArray[i];
					var role_id = rowObj.role_id;
					if (delete_role_id == ""){
						delete_role_id = role_id;
					}else{
						delete_role_id = delete_role_id + "," + role_id;
					}
				}
				var paramObj = {
					"delete_role_id":		delete_role_id
				};
				
				var deleteURL = "<%=path%>/roleAction!deleteRole?date="+new Date()+"";
				
				jQuery.post(deleteURL,paramObj,function(jsonData){
					var flag = 			jsonData.flag;
					var errormsg = 		jsonData.errormsg;
					if (flag == true){
						jQuery.messager.alert("操作提示","数据删除成功","info",function(){
							jQuery("#role_grid_div").datagrid("reload");
						});
					}else{
						jQuery.messager.alert("操作提示","数据删除失败，错误原因:"+errormsg,"error");	
					}
				},"json");
			}
		}
		
		function searchRole(){
			var begin_role_id 	= document.getElementById("begin_role_id").value;
			var end_role_id   	= jQuery("#end_role_id").val();
			var role_name   	= jQuery("#role_name").val();
			var role_remark   	= jQuery("#role_remark").val();
			
			var paramObj = {
				"begin_role_id":			begin_role_id,
				"end_role_id":				end_role_id,
				"role_name":				role_name,
				"role_remark":				role_remark
			};
			jQuery("#role_grid_div").datagrid({
				queryParams: paramObj
			});
		}
		
		function addRole(){
			var addURL = "<%=path%>/roleAction!add?date="+new Date()+"";
			jQuery('#add_dialog_div').dialog({    
				title: 			"添加角色",  
				width: 			800,  
				height: 		390,  
				closed: 		false,  
				modal: 			true, 
				minimizable: 	false, //是否可最小化，默认false
				maximizable: 	false, //是否可最大化，默认false
				resizable: 		false, 
				cache: 			false,  
				content:		"<iframe name='roleFrame' id='roleFrame' scrolling='no' frameborder='0' style=\"width:100%;height:99%;\" src='"+addURL+"'></iframe>" 
			}); 			
		}
		function editRole(){
			var selectArray = jQuery("#role_grid_div").datagrid("getSelections");
			if (selectArray == null || selectArray.length == 0){
				jQuery.messager.alert("操作提示","无法执行该操作，您还未选择数据","error");
			}else{
				if (selectArray.length>1){
					jQuery.messager.alert("操作提示","修改时，只能选择一条记录","error");
					return;
				}
				var selectObj = selectArray[0];
				var role_id = 	selectObj.role_id;
				var editURL = "<%=path%>/roleAction!edit?role_id="+role_id+"&date="+new Date()+"";
				jQuery('#add_dialog_div').dialog({    
					title: 			"修改角色",  
					width: 			800,  
					height: 		390,  
					closed: 		false,  
					modal: 			true, 
					minimizable: 	false, //是否可最小化，默认false
					maximizable: 	false, //是否可最大化，默认false
					resizable: 		false, 
					cache: 			false,  
					content:		"<iframe name='roleFrame' id='roleFrame' scrolling='no' frameborder='0' style=\"width:100%;height:99%;\" src='"+editURL+"'></iframe>" 
				}); 			
			}			
		}	
		function checkRole(){
			jQuery("#role_grid_div").datagrid("checkAll");
		}
		
	
		jQuery("#begin_role_id").numberbox({    
			    min:0, 
			    max:9999,   
			    precision:0    
		});
		jQuery("#end_role_id").numberbox({    
			    min:0, 
			    max:9999,   
			    precision:0    
		});
		
	});
</script>    
<style>
.spanCss {
   font-family: "微软雅黑";
   font-size: 15px;
}
.datagrid-header-row{
    height						: 		30px;
    font							:  		 bold 20px 微软雅黑;
    background-color			:		#E0ECFF;
}
</style>
</head>
  
<body class="easyui-layout" >   
    <div class="easyui-panel" data-options="region:'north',title:'角色列表'" style="height: 100px;">
    	<table border="0" width="100%"  data-options="fit:true" style="margin-top: 5px">
    		<tr height="30px;">
    			<td width="10%" align="right"><span class="spanCss">角色ID号：</span></td>
    			<td width="40%" align="left">
	    			 <s:textfield name="begin_role_id"  	  id="begin_role_id"  maxlength="10"/>
	    			 <span class="spanCss">~&nbsp</span>
	    			 <s:textfield name="end_role_id"	      id="end_role_id"	    maxlength="10"/>
    			 </td>
    			<td width="10%" align="right"><span class="spanCss">角色名称：</span></td>
    			<td width="40%" align="left"><s:textfield name="role_name" id="role_name"  cssStyle="height:20px"/></td>
    		</tr>
    		<tr height="25px;" >
    			<td align="right"><span class="spanCss">角色备注：</span></td>
    			<td colspan="3" align="left">
    				<s:textfield name="role_remark" id="role_remark" cssStyle="width:330px;height:20px"/>
    			</td>
    		</tr>    		
    	</table>
    </div>     
    <div data-options="region:'center'">
    	<div id="role_grid_div"></div>
    </div>   
	<div id="add_dialog_div"></div>
</body>
</html>