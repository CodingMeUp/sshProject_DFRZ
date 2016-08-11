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
<script type="text/javascript" src="${ctx}/js/valid.js"></script>
<script language="javascript">
	//jQuery的初始化函数
	jQuery(function(){
		var toolArray = [
			{id:"search",			text:"查询数据",		iconCls:"icon-search",		handler:function(){
				searchRole();
			}},
			{id:"addMenu",			text:"添加菜单",		iconCls:"icon-add",			handler:function(){
				addMenu();
			}},
			{id:"edit",				text:"修改菜单",		iconCls:"icon-edit",			handler:function(){
				editMenu();
			}},
			{id:"delete",			text:"删除菜单",		iconCls:"icon-remove",		handler:function(){
				deleteMenu();
			}},
			{id:"refresh",			text:"刷新列表",		iconCls:"icon-reload",		handler:function(){
				jQuery("#menu_grid_div").treegrid("reload");		
				
			}}
		];
		jQuery("#menu_grid_div").treegrid({
			rownumbers:		true,
			striped:				true,
			fit:					true,  
		    idField:				'id',   
		    singleSelect:		false,
		    treeField:			'menu_name',
			toolbar:				toolArray,
			loadMsg:				"正在加载数据，请稍等...",
		    url:					"<%=	path%>/menuAction!listGridJson?date="+new Date()+"",    
		    columns:[[    
		        {field:"ck",						width:50,				checkbox:true																	  },
		        {field:"id",						title:"菜单编码",		width:100,	align:"center",		halign:"center",  		sortable:true},    
		        {field:"roleid",					title:"角色ID号",		width:100,	align:"center",		halign:"center",  		sortable:true},    
		        {field:"menu_name",			title:"菜单名称",		width:300,	align:"left",			halign:"center",		sortable:true},    
		        {field:"menu_href",			title:"菜单链接",		width:200,  align:"left",	    	halign:"center",					      },
		        {field:"grade",					title:"级别",				width:50, 	align:"center",		halign:"center",						  },
		        {field:"isleaf_str",				title:"叶子节点",		width:100,	align:"center",		halign:"center",						  },
		    ]],
		      onClickRow:function(row){  
                    //级联选择  
                    $(this).treegrid('cascadeCheck',{  
                        id:row.id, //节点ID  
                        deepCascade:true //深度级联  
                    });  
                }      
		}); 
		  
		  //属于删除的方法 发送URL 独立出来
			function delPostMethod(paramObj){
			var deleteURL = "<%=path%>/menuAction!deleteMenu?date="+new Date()+"";
													jQuery.post(deleteURL,paramObj,function(jsonData){
														var flag 				=		jsonData.flag;
														var errormsg	    =  		jsonData.errormsg;
														var admin           =      jsonData.admin;
														if (flag == true){
															    jQuery.messager.alert("操作提示","菜单删除成功","info");
															    if(admin == true)
																	{ 
																		 searchRole();
																		 parent.jQuery("#tree").tree("reload"); 
																	 }else{
																	    searchRole();
																	 }
														}else{
															jQuery.messager.alert("操作提示","菜单删除失败，错误原因:"+errormsg,"error");	
														}
													},"json");
		}
		
		function deleteMenu(){
		    var selectArray = jQuery("#menu_grid_div").datagrid("getSelections");
			if (selectArray == null || selectArray.length == 0){
				jQuery.messager.alert("操作提示","无法执行该操作，您还未选择菜单","error");
			}else{
				var delete_menu_id = "";
				var isLeaf = "";
				var role_id= "";
				for(var i = 0;i<selectArray.length;i++){
					var rowObj = selectArray[i];
					var menu_id = rowObj.id;
					var leaf       = rowObj.isleaf_str;
					var role_id   = rowObj.roleid;
				
					if (delete_menu_id == ""){
						delete_menu_id = menu_id;
						isLeaf = leaf;
					}else{
						delete_menu_id = delete_menu_id + "," + menu_id;
						isLeaf= isLeaf + "," + leaf;
					}
				}
				var paramObj = {
					"delete_menu_id"	:	delete_menu_id,
					"isLeaf"				: 	isLeaf,
					"role_id"				:	role_id
				};
		   
			if(paramObj.isLeaf.indexOf("否")>0)  //   true/false:非叶子节点(做/不做)提示 
			{	
              jQuery("#del_dialog_div").dialog({    
					    title				:		 "友情提示",    
					    width				: 		 400,
					    height			: 		 130, 
					   	closed			: 		 false,  
					   	closable			:		 false,
						modal				: 		 true, 
						minimizable		: 		 false, //是否可最小化，默认false
						maximizable		: 		 false, //是否可最大化，默认false
						resizable			: 		 false, 
						cache			: 		 false,  
					    content			:		"<table align='center'  style='font-family: 微软雅黑;font-size: 14px;font-weight:bold'><tr style='color:red'><td>提示：</td><td>删除大菜单时底下的小菜单也会一并被删除</td></tr><td>点击确定：</td><td>完成删除功能</td><tr></tr></table>",
					    buttons	:[
					     		{
									text:'确定',
									handler:function(){   
												jQuery("#del_dialog_div").dialog("close"); 
									     		var ipt_role_id = jQuery("#role_id").val();
												if(selectArray[0].roleid != ipt_role_id){
														jQuery("#role_id").val(paramObj.role_id);	
												}
												 jQuery("#menu_grid_div").datagrid("uncheckAll");
									    		 delPostMethod(paramObj);
									 }},
								{
									text:'取消',
									handler:function(){    
									     jQuery("#menu_grid_div").datagrid("uncheckAll");
									     jQuery("#del_dialog_div").dialog("close"); 
									     }
			                       }]
	           });   
             }else{
							var ipt_role_id = jQuery("#role_id").val();
									if(selectArray[0].roleid != ipt_role_id){
										jQuery("#role_id").val(paramObj.role_id);	
									}
								 jQuery("#menu_grid_div").datagrid("uncheckAll");
								 delPostMethod(paramObj);
			 }
		}
	}
		
		function searchRole(){
			var role_id 				= 		jQuery("#role_id").val();
			var role_name   		= 		jQuery("#role_name").val();
			var role_remark   	= 		jQuery("#role_remark").val();
			
			var paramObj = {
				"role_id":						role_id,
				"role_name":				role_name,
				"role_remark":				role_remark
			};
			jQuery("#menu_grid_div").treegrid({
				queryParams: paramObj
			});
		}
		
		function addMenu(){
		var role_id = $('td[field=roleid]:eq(1)').text();
		if(role_id==1){
				var addURL = "<%=path%>/menuAction!add?date="+new Date()+"";
				jQuery('#add_dialog_div').dialog({    
					title: 				"添加菜单",  
					width: 			800,  
					height: 			300,  
					closed: 			false,  
					modal: 			true, 
					minimizable: 	false, //是否可最小化，默认false
					maximizable: 	false, //是否可最大化，默认false
					resizable: 		false, 
					cache: 			false,  
					content:			"<iframe name='roleFrame' id='roleFrame' scrolling='no' frameborder='0' style=\"width:100%;height:99%;\" src='"+addURL+"'></iframe>" 
				}); 	
			}else{
			     	jQuery.messager.alert("操作提示","没有权限！","warning");
			}	
	}
		function editMenu(){
			var role_id = $('td[field=roleid]:eq(1)').text();
			if(role_id==1){
				var selectArray = jQuery("#menu_grid_div").datagrid("getSelections");
				if (selectArray == null || selectArray.length == 0){
					jQuery.messager.alert("操作提示","无法执行该操作，您还未选择菜单","error");
				}else{
					if (selectArray.length>1){
						jQuery.messager.alert("操作提示","修改时，只能选择一条记录","error");
						return;
					}
					var selectObj	= 	selectArray[0];
					var menu_id 	= 	selectObj.id;
					var editURL	 	= "<%=path%>/menuAction!edit?menu_id="+menu_id+"&date="+new Date()+"";
					jQuery('#add_dialog_div').dialog({    
						title: 			"修改菜单",  
						width: 			800,  
						height: 			200,  
						closed: 			false,  
						modal: 			true, 
						minimizable: 	false, //是否可最小化，默认false
						maximizable: 	false, //是否可最大化，默认false
						resizable: 		false, 
						cache: 			false,  
						content:		"<iframe name='menuFrame' id='menuFrame' scrolling='no' frameborder='0' style=\"width:100%;height:99%;\" src='"+editURL+"'></iframe>" 
					}); 			
				}		
					jQuery("#menu_grid_div").datagrid("uncheckAll");
					
			}else{
			     	jQuery.messager.alert("操作提示","没有权限！","warning");
	}
	}	
		

/**
    $.extend($.fn.treegrid.methods,{  
        /** 
         * 级联选择 
         * @param {Object} target 
         * @param {Object} param  
         *      param包括两个参数: 
         *          id:勾选的节点ID 
         *          deepCascade:是否深度级联 
         * @return {TypeName}  
         */  
         /**
        cascadeCheck : function(target,param){  
            var opts = $.data(target[0], "treegrid").options;  
            if(opts.singleSelect)  
                return;  
            var idField = opts.idField;
            var status = false;
            var selectNodes = $(target).treegrid('getSelections');
            for(var i=0;i<selectNodes.length;i++){  
                if(selectNodes[i][idField]==param.id)  
                    status = true;  
            }  
            selectParent(target[0],param.id,idField,status);  
            selectChildren(target[0],param.id,idField,param.deepCascade,status);  
            /** 
             * 级联选择父节点 
             * @param {Object} target 
             * @param {Object} id 节点ID 
             * @param {Object} status 节点状态，true:勾选，false:未勾选 
             * @return {TypeName}  
             */  
             /**
            function selectParent(target,id,idField,status){  
                var parent = $(target).treegrid('getParent',id);  
                if(parent){  
                    var parentId = parent[idField];  
                    if(status)  
                        $(target).treegrid('select',parentId);  
                    else  
                        $(target).treegrid('unselect',parentId);  
                    selectParent(target,parentId,idField,status);  
                }  
            }  
            /** 
             * 级联选择子节点 
             * @param {Object} target 
             * @param {Object} id 节点ID 
             * @param {Object} deepCascade 是否深度级联 
             * @param {Object} status 节点状态，true:勾选，false:未勾选 
             * @return {TypeName}  
             */  
             /**
            function selectChildren(target,id,idField,deepCascade,status){  
                if(!status&&deepCascade)  
                    $(target).treegrid('expand',id);  
                var children = $(target).treegrid('getChildren',id);  
                for(var i=0;i<children.length;i++){  
                    var childId = children[i][idField];  
                    if(status)  
                        $(target).treegrid('select',childId);  
                    else  
                        $(target).treegrid('unselect',childId);  
                    selectChildren(target,childId,idField,deepCascade,status);
                }  
            }  
        }  
    }); 
**/
		jQuery("#role_id").numberbox({    
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
  
<body class="easyui-layout">   
     <div class="easyui-panel" data-options="region:'north',title:'角色列表'" style="height: 70px;">
    	<table border="0" width="100%"  data-options="fit:true" style="margin-top: 5px;">
    		<tr height="20px;"  >
    			<td width="10%" align="right"><span class="spanCss">角色ID号：</span></td>
    			<td width="10%" align="left"><s:textfield name="role_id"  	  id="role_id"    maxlength="10"/> </td>
    			<td width="10%" align="right"><span class="spanCss">角色名称：</span></td>
    			<td width="15%" align="left"><s:textfield name="role_name" id="role_name"  /></td>
    			<td width="10%" align="right"><span class="spanCss">角色备注：</span></td>
    			<td width="45%" align="left"><s:textfield name="role_remark" id="role_remark" cssStyle="width:330px"/></td>
    		</tr>
    	</table>
    </div>     
    <div data-options="region:'center'">
    	<div id="menu_grid_div"></div>
    </div>   
	<div id="add_dialog_div"></div>
	<div id="del_dialog_div"></div>
</body>
</html>