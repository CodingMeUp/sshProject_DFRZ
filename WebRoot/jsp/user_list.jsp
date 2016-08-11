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
<script type="text/javascript" src="${ctx}/js/valid.js"></script>
<script language="javascript">
	//jQuery的初始化函数
	jQuery(function(){
		var toolArray = [
			{id:"search",			text:"查询数据",		iconCls:"icon-search",		handler:function(){
				searchRole();
			}},
			{id:"select",			text:"选择用户",		iconCls:"icon-tip",				handler:function(){
				checkRole();
			}},
			{id:"addUser",			text:"添加用户",		iconCls:"icon-add",			handler:function(){
				addUser();
			}},
			{id:"edit",				text:"修改用户",		iconCls:"icon-edit",			handler:function(){
				editUser();
			}},
			{id:"delete",			text:"删除用户",		iconCls:"icon-remove",		handler:function(){
				deleteUser();
			}},
			{id:"refresh",			text:"刷新列表",		iconCls:"icon-reload",		handler:function(){
				jQuery("#user_grid_div").datagrid("reload");
				
			}}
		];
		jQuery("#user_grid_div").datagrid({
			rownumbers:		true,
			striped:				true,
			fit:					true,  
			remoteSort:		true,  		//配合字段的属性使用
			pagination:			true,
			pageList:			[10,30,50],
			pageSize:			30,
			toolbar:				toolArray,
			loadMsg:				"正在加载数据，请稍等...",
		    url:					"<%=path%>/userAction!listGridJson?date="+new Date()+"",  
		    frozenColumns:[[
		    	{field:"ck",					width:50,			checkbox:true},    
		        {field:"userid",				title:"用户id",		width:50,	align:"center",		halign:"center",	sortable:true},    
		        {field:"username",			title:"用户名",		width:120,	align:'center',		halign:"center",	sortable:true}		    	
		    ]],  
		    columns:[[    
		        {field:"truename",		title:"真实姓名",		width:150,  	align:'center',			halign:"center"},
	            {field:"usersex",		title:"性别",				width:50, 		align:'center',		halign:"center",
										formatter: function(value,row,index){
											if (value == "1"){
												return "<font color='blue'>男</font>";
											} else {
												return "<font color='red'>女</font>";
											}
										}
	            },
	            {field:"userage",		title:"年龄",				width:50,  			align:'center',			halign:"center"},
	            {field:"telphone",		title:"联系电话",		width:100, 			align:'center',			halign:"center"},
	            {field:"address",		title:"地址",				width:250,  		align:"center",			halign:"center"},
	            {field:"mail",			title:"邮箱",				width:250,			align:'left',				halign:"center"}
		    ]]    
		}); 
		
		function deleteUser(){
			var selectArray = jQuery("#user_grid_div").datagrid("getSelections");
			if (selectArray == null || selectArray.length == 0){
				jQuery.messager.alert("操作提示","无法执行该操作，您还未选择数据","error");
			}else{
				var delete_user_id = "";
				for(var i = 0;i<selectArray.length;i++){
					var userObj = selectArray[i];
					var user_id = userObj.userid;
					if (delete_user_id == ""){
						delete_user_id = user_id;
					}else{
						delete_user_id = delete_user_id + "," + user_id;
					}
				}
				var paramObj = {
					"delete_user_id"	:		delete_user_id
				};
				
				var deleteURL = "<%=path%>/userAction!deleteUser?date="+new Date()+"";
				
				jQuery.post(deleteURL,paramObj,function(jsonData){
					var flag 			= 			jsonData.flag;
					var errormsg 	= 			jsonData.errormsg;
					if (flag == true){
						jQuery.messager.alert("操作提示","数据删除成功","info",function(){
							jQuery("#user_grid_div").datagrid("reload");
						});
					}else{
						jQuery.messager.alert("操作提示","数据删除失败，错误原因:"+errormsg,"error");	
					}
				},"json");
			}
		}
		
		function searchRole(){
			var begin_user_id 	= 	 	document.getElementById("begin_user_id").value;
			var end_user_id   	=  		jQuery("#end_user_id").val();
			var user_name   		= 		jQuery("#user_name").val();
			var user_truename   =      jQuery("#user_truename").val();
			var user_phone   	= 		jQuery("#user_phone").val();
			
			var paramObj = {
				"begin_user_id"		:			begin_user_id,
				"end_user_id"			:			end_user_id,
				"user_name"			:			user_name,
				"user_truename"		:			user_truename,
				"user_phone"			:			user_phone
			};
			jQuery("#user_grid_div").datagrid({
				queryParams: paramObj
			});
		}
		
		function addUser(){
			var addURL = "<%=path%>/userAction!add?date="+new Date()+"";
			jQuery('#add_dialog_div').dialog({    
				title: 			"添加用户",  
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
		function editUser(){
			var selectArray = jQuery("#user_grid_div").datagrid("getSelections");
			if (selectArray == null || selectArray.length == 0){
				jQuery.messager.alert("操作提示","无法执行该操作，您还未选择数据","error");
			}else{
				if (selectArray.length>1){
					jQuery.messager.alert("操作提示","修改时，只能选择一条记录","error");
					return;
				}
				var selectObj	 = 			selectArray[0];
				var userid 		 = 			selectObj.userid;
				var editURL		 =			 "<%=path%>/userAction!edit?userid="+userid+"&date="+new Date()+"";
				jQuery('#add_dialog_div').dialog({    
					title: 				"修改用户",  
					width: 			800,  
					height: 			390,  
					closed: 			false,  
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
			jQuery("#user_grid_div").datagrid("checkAll");
		}
		
		jQuery("#begin_user_id").numberbox({    
			    min:0, 
			    max:9999,   
			    precision:0    
		});
		jQuery("#end_user_id").numberbox({    
			    min:0, 
			    max:9999,   
			    precision:0    
		});
		jQuery('input[type=text]').validatebox();
		
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
  
<body class="easyui-layout">   
    <div class="easyui-panel" data-options="region:'north',title:'用户列表'"  style="height: 100px;">
    	<table border="0" width="100%"  data-options="fit:true" style="margin-top: 5px">
    		<tr height="25px;">
    			<td width="10%" align="right"><span class="spanCss">用户ID号：</span></td>
    			<td width="40%" align="left">
    				<s:textfield name="begin_user_id" id="begin_user_id"  maxlength="10" />
    				<span class="spanCss">~&nbsp</span>
    				<s:textfield name="end_user_id" id="end_user_id" 		 maxlength="10"  /></td>
    			<td width="10%" align="right"><span class="spanCss">用户名称：</span></td>
    			<td width="40%" align="left"><s:textfield name="user_name" id="user_name"/></td>
    		</tr>
    		<tr height="25px;">
    			<td  width="10%"  align="right"><span class="spanCss">真实姓名：</span></td>
    			<td  width="40%"	  align="left">
    				<s:textfield name="user_truename" id="user_truename" validtype="chinese"  />
    			</td>
    			<td  width="10%"  align="right"><span class="spanCss">联系电话：</span></td>
    			<td  width="40%"	  align="left">
    				<s:textfield name="user_phone" id="user_phone"  validtype="mobile"  />
    			</td>
    		</tr>    		
    	</table>
    </div>     
    <div data-options="region:'center'">
    	<div id="user_grid_div"></div>
    </div>   
	<div id="add_dialog_div"></div>
</body>
</html>
