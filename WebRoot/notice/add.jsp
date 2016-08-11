<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'add.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
<script language="javascript" type="text/javascript" src="<%=path%>/My97DatePicker/WdatePicker.js"></script>	
<script type="text/javascript" charset="utf-8" src="<%=path%>/ueditor1_3_6/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=path%>/ueditor1_3_6/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="<%=path%>/ueditor1_3_6/lang/zh-cn/zh-cn.js"></script>
<script language="javascript" type="text/javascript" src="<%=path%>/easyui_1.3.6/jquery.min.js"></script>
<script language="javascript">
jQuery(function(){
	var UEDITOR_CONFIG = {
        initialFrameWidth: "100%" //初始化编辑器宽度,默认1000
        ,initialFrameHeight:250  //初始化编辑器高度,默认320
        ,autoHeightEnabled: false		
	}
	
	UE.getEditor('notice_content',UEDITOR_CONFIG);
});
</script>
</head>
  
  <body>
	<form action="<%=path%>/testAction_3!save" id="form1" enctype="multipart/form-data" method="post">
		<table border="0" width="100%" style="font-family: '新宋体';font-size: 13px;">
			<tr height="25px;">
				<td width="10%" align="right">公吿标题：</td>
				<td width="40%" align="left"><s:textfield name="notice_title" id="notice_title"/>
				</td>
			</tr>
			<tr height="25px;">
				<td align="right">发布人：</td>
				<td align="left">
					<s:textfield name="notice_adduser" id="notice_adduser" cssStyle="width:320px;"/>
				</td>
			</tr> 
			<tr height="25px;">
				<td align="right">发布时间：</td>
				<td align="left">
					
					<input name="notice_addtime" id="notice_addtime" style="width:320px;" 
								 class="Wdate" value="${notice_addtime}"
								 onclick="WdatePicker({skin:'whyGreen',minDate:'${firstDay}',maxDate:'${lastDay }'});" 
								 />
				</td>
				
			</tr>   
			<tr height="25px;">
				<td align="right">内容：</td>
				<td align="left" width="70%">
					<script id="notice_content" name="notice_content" type="text/plain" 
							style="width:100%;"></script>
				</td>
				
			</tr>   
			<tr height="25px;">
				<td align="right">公告附件：</td>
				<td align="left">
					<s:file name="notice_file" id="notice_file" cssStyle="width:320px;"/>
				</td>
				
			</tr> 
			<tr height="25px;">
				<td align="center" colspan="2">
					<input type="submit" value="保存数据"/>
				</td>
				
			</tr>  			  				     		   		 		   		
		</table>
	</form>   
  </body>
</html>
