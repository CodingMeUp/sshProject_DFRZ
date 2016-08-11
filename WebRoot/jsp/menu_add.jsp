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
 		
	
			function closeWin(){
				parent.jQuery('#add_dialog_div').dialog("close");
			}	
			function funOld(){
			    document.getElementById('havingTr').style.display = 'inline'; 
				document.getElementById('addTop').style.display = 'none'; 
				jQuery("#topMenu").val("");
			}
			function funNew(){
				document.getElementById('addTop').style.display = 'inline'; 
				document.getElementById('havingTr').style.display = 'none'; 
				document.getElementById('havingAddDir').style.display = 'none'; 
				document.getElementById('havingAddItem').style.display = 'none';
				jQuery("#itemMenu").val("");
				jQuery("#dirMenu").val("");
			}
			function funDir(){
			    document.getElementById('havingAddDir').style.display = 'inline'; 
				document.getElementById('havingAddItem').style.display = 'none';
				jQuery("#itemMenu").val("");
			}
			function funItem(){
				document.getElementById('havingAddItem').style.display = 'inline'; 
				document.getElementById('havingAddDir').style.display = 'none'; 
				jQuery("#dirMenu").val("");
			}
 		
 jQuery(function(){
			$('#combobox').combobox({    
							url						:		"<%=path%>/menuAction!loadMenuCombobox?date="+new Date()+"",
							valueField			:		"menu_id",
							textField				: 		"menu_name",
							onLoadSuccess	: 		function () {   					//默认勾选第一个菜单
 																var data = $('#combobox').combobox('getData');
            														 if (data.length > 0) { $('#combobox').combobox('select', data[0].menu_id); } 
															}   
			});  
		});
			
			function saveMenu(){
				var formData = jQuery("#form1").serializeArray();
				var saveURL = "<%=path%>/menuAction!save?date="+new Date()+"";
				jQuery.post(saveURL,formData,function(jsonData){
					var flag 				=	 	jsonData.flag;
					var errormsg 		= 		jsonData.errormsg;
					if (flag == true){
						jQuery.messager.alert("操作提示","菜单保存成功","info",function(){
								parent.jQuery('#add_dialog_div').dialog("close");
								parent.jQuery("#menu_grid_div").treegrid("reload");  //表格内的数据菜单刷新
								parent.parent.jQuery("#tree").tree("reload");          // 左边系统菜单的刷新
						});	
					}else{
						jQuery.messager.alert("操作提示","数据保存失败，错误原因:"+errormsg,"error");	
					}
				},"json");
			}	
		
</script>    
</head>
  
<body>   
<form action="" id="form1">
	<table border="0" width="100%" style="font-family: '微软雅黑';font-size: 15px;">
		<tr height="25px;">
			<td width="20%" align="right">选择添加情况:</td>
			<td width="20%" align="center"><input  type="radio" name="chooseMenu"   value="old"  onclick="funOld();">已有的菜单夹</input></td>
			<td width="40%" align="left"><input  type="radio" name="chooseMenu"   value="new"   onclick="funNew();"">创建顶级菜单</input></td>
		</tr>
	</table>	
	<table border="0" width="100%" style="font-family: '微软雅黑';font-size: 15px;">
		<tr  height="50px;"   id="havingTr" style="display:none;"   >
				<td width="186px" align="right"   >已有菜单列表:</td>
				<td width="200px" align="center"    >
				<input  id="combobox" class="easyui-combobox"  name="selectBox"  />
				</td> 
				<td width="70px" align="center"    >
				  	   <a href="javascript:void(0);"   class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="funDir();">菜单夹</a>
				</td>
				<td width="70px" align="center"   >
				  	   <a href="javascript:void(0);"   class="easyui-linkbutton" data-options="iconCls:'icon-add'"   onclick="funItem();">菜单项</a>
				</td>
		</tr>
		<tr  height="50px;"   id="havingAddDir" style="display:none;"   >
		  	<td width="186px" align="right"   >输入新菜单夹:</td>
		  	<td width="200px" align="center"    ><input type="text"   id="dirMenu" name="dirMenu"  /></td>
            </td>
		</tr>
		<tr  height="50px;"   id="havingAddItem" style="display:none;"   >
		  	<td width="186px" align="right"   >输入新菜单项:</td>
		  	<td width="200px" align="center"    ><input type="text"  id="itemMenu"  name="itemMenu"  /></td>
		</tr>
		<tr  height="50px;"   id="addTop" style="display:none;"   >
		  	<td width="186px" align="right"   >顶级菜单名称:</td>
		  	<td width="200px" align="center"    ><input type="text" id="topMenu"  name="topMenu"  /></td>
		</tr>
	</table>	
</form>   
<div align="right" > 
	 <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-save'"  		 onclick="saveMenu();">保存数据</a>      
	 <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"  	 onclick="closeWin();">关闭窗口</a>  
 </div> 
</body>
</html>
