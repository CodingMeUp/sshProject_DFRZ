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
	jQuery(function(){ 
		    var $province =				 $('#province');  
		    var $city 		=				 $('#city');  
		    var $area 		= 				 $('#area');  
		    var provinceId = $province.combobox({
		    				url						:		"<%=path%>/dataAction!loadProvCombobox?date="+new Date()+"",
		    				width					:		160,
							valueField			:		"province_id",
							textField				: 		"province_name",
							onLoadSuccess	: 		function () {   					//默认勾选第一个菜单
 																var data = $('#province').combobox('getData');
            														 if (data.length > 0) { $('#province').combobox('select', "请选择"); } 
																},
							onSelect				:		function(record){
          														 //刷新数据，重新读取省份下的城市，并清空当前输入的值
          														if(record.province_id != null || record.province_id > 0 ){
          															var pid = parseInt(record.province_id);
	          														cityId.combobox({
													                    disabled		:		false,
													                    url				:		"<%=path%>/dataAction!loadCityCombobox?pid="+pid+"&date="+new Date()+"",
													                    width			:		160,
																		valueField	:		"city_id",
																		textField		: 		"city_name"	
													                }).combobox('clear');
												              	}
												              }	  
    			    });
    		var cityId = $city.combobox({
		    				url						:		"<%=path%>/dataAction!loadCityCombobox?pid="+provinceId.val()+"&date="+new Date()+"",
		    				width					:		160,
							valueField			:		"city_id",
							textField				: 		"city_name",
							onLoadSuccess	: 		function () {   					//默认勾选第一个菜单
 																var data = $('#city').combobox('getData');
            														 if (data.length > 0) { $('#city').combobox('select', "请选择"); } 
																},
							onSelect				:		function(record){
          														 //刷新数据，重新读取省份下的城市，并清空当前输入的值
          														if(record.city_id != null || record.city_id > 0 ){
          															var cid = parseInt(record.city_id);
	          														areaId.combobox({
													                    disabled		:		false,
													                    url				:		"<%=path%>/dataAction!loadAreaCombobox?cid="+cid+"&date="+new Date()+"",
													                    width			:		160,
																		valueField	:		"area_id",
																		textField		: 		"area_name"	
													                }).combobox('clear');
												              	}
												              }	  
    			    });
  			 var areaId = $area.combobox({
		    				url						:		"<%=path%>/dataAction!loadAreaCombobox?cid="+cityId.val()+"&date="+new Date()+"",
		    				width					:		160,
							valueField			:		"area_id",
							textField				: 		"area_name",
							onLoadSuccess	: 		function () {   					//默认勾选第一个菜单
 																var data = $('#area').combobox('getData');
            														 if (data.length > 0) { $('#area').combobox('select', "请选择"); } 
																}   
		      	
    			    });
          });
</script>    
<style>
body
{
   text-align:center;
}
table
{
   margin:0px auto;
}

</style>

</head>
  
<body class="easyui-layout" >

<table style="font-family: 微软雅黑;font-size: 15px"  cellpadding="0" cellspacing="0" border="0" width="80%" height="50%">
   	<tr height="50px"  >
   		<td  width="50px" align="right">省份名:</td><td  	align="center"><input  id="province"   		 class="easyui-combobox"  name="province"  /></td>
		<td  width="50px" align="right">县市名:</td><td  	align="center"><input  id="city" 					 class="easyui-combobox"  name="city"  /></td>
		<td  width="50px" align="right">乡镇名:</td><td  	align="center"><input  id="area" 				 class="easyui-combobox"  name="area"  /></td>
	</tr>
</table>
</body>
</html>