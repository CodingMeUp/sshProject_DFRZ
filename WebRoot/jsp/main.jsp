<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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

		<title>项目登录页</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		
<link rel="stylesheet" type="text/css" href="${ctx}/jquery-easyui-1.3.6/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/jquery-easyui-1.3.6/themes/icon.css">
<script type="text/javascript" src="${ctx}/jquery-easyui-1.3.6/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/js/main.js"></script>
<script type="text/javascript" src="${ctx}/js/tool.js"></script>
<script type="text/javascript" src="${ctx}/jquery-easyui-1.3.6/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/jquery-easyui-1.3.6/locale/easyui-lang-zh_CN.js"></script>

<style> 
	.west{ width:200px; padding:10px;}
	.footer{text-align:center; color:#15428B; margin:0px; padding:0px;line-height:23px; font-weight:bold;}  
	.tree{ margin-left:20px;margin-top:10px;font: 13px 微软雅黑;}
	.tree-title{ font: 14px 微软雅黑;}
</style>
    <style type="text/css">
		* { margin:0; padding:0;}
		body { color:#666; text-align:center;}
		#container { width:350px; margin:30px 0 30px -10px; text-align:left; padding:10px; border:1px solid #fff; height:250px; position:relative; overflow:hidden;}
		#noticeContainer { width:350px; margin:-430px 0 30px 510px; text-align:left; padding:10px; border:1px solid #fff; height:250px; position:relative; overflow:hidden;}
			.newsList { width:450px; list-style:inside; position:absolute; top:270px; left:10px;}
			.newsList li { padding-bottom:10px;}
			.newsList li a { color:#6696EE;}
			.newsList li a:hover { color:#09F;}
			.noticeList { width:450px; list-style:inside; position:absolute; top:270px; left:10px;}
			.noticeList li { padding-bottom:10px;}
			.noticeList li a { color:#6696EE;}
			.noticeList li a:hover { color:#09F;}
			
	</style>

<script type="text/javascript">
	jQuery(function($) {
		function ScrollAction(listObj, listElem, speed, isSeries) {	//listObj为需要滚动的列表，  speed为滚动速度
			var pos, top, aniTop, height;
			var id = '';  //记录setInterval的标记id
			
			pos = listObj.position();	
			top = pos.top;			//列表的top
			aniTop = top;				//记录当前运动时的top
			height = listObj.height();	//列表的高度
			
			var scrollUp = function() {
				aniTop--;
				if(!isSeries) {	//isSeries变量控制是否连续滚动，false不连续，true连续
					if(aniTop == -height) {	//不连续，滚动玩重新滚动
						listObj.css({'top': top});
						aniTop = top;
					};
				} else {
					if(aniTop == -listObj.children().eq(0).height()) {	//连续滚动
						var firstItem = '<' + listElem +'>' + listObj.children().eq(0).html() + '</' + listElem +'>';
						listObj.children().eq(0).remove();
						listObj.append(firstItem);
						aniTop = 4;
					};
				};
				listObj.css({'top': aniTop + 'px'});
			};
			
			var hover = function(id) {
				listObj.hover(function() {
					clearInterval(id);
				}, function() {
					id = setInterval(scrollUp, speed);
				});
			};
			
			this.start = function() {
				id = setInterval(scrollUp, speed);
				hover(id);
			};
			
		};
		var sa = new ScrollAction($('.noticeList'), 'li', 50, true);
		sa.start();
	});
	
jQuery(function($) {

	$("#noticeList").ready(function(){
			$.ajax({
				url			:			"<%=path%>/noticeAction!showOnHome?date="+new Date()+"",
				type		:			"post",
				datatype:			"json",
				success:function(dataObj){
					var jsonObj =	eval('('+dataObj+')');
					var flag = jsonObj.flag;
					if(flag==true){
						var noticeList = jsonObj.notice_list;
						var noticeIDList = jsonObj.notice_id;
						for(var i = 0; i< noticeList.length; i++){
							var title = noticeList[i];
							var id = noticeIDList[i];
							var str = "<%=path%>/jsp/noticeAction!edit?noticeid="+id;
							$("#noticeList").append("<li><a href='"+str+"'   title='"+title+"'>"+title+"</a></li>");
						}
					}
				}
			});
		});
	});
		jQuery(function($) {
		function ScrollAction(listObj, listElem, speed, isSeries) {	//listObj为需要滚动的列表，  speed为滚动速度
			var pos, top, aniTop, height;
			var id = '';  //记录setInterval的标记id
			
			pos = listObj.position();	
			top = pos.top;			//列表的top
			aniTop = top;				//记录当前运动时的top
			height = listObj.height();	//列表的高度
			
			var scrollUp = function() {
				aniTop--;
				if(!isSeries) {	//isSeries变量控制是否连续滚动，false不连续，true连续
					if(aniTop == -height) {	//不连续，滚动玩重新滚动
						listObj.css({'top': top});
						aniTop = top;
					};
				} else {
					if(aniTop == -listObj.children().eq(0).height()) {	//连续滚动
						var firstItem = '<' + listElem +'>' + listObj.children().eq(0).html() + '</' + listElem +'>';
						listObj.children().eq(0).remove();
						listObj.append(firstItem);
						aniTop = 4;
					};
				};
				listObj.css({'top': aniTop + 'px'});
			};
			
			var hover = function(id) {
				listObj.hover(function() {
					clearInterval(id);
				}, function() {
					id = setInterval(scrollUp, speed);
				});
			};
			
			this.start = function() {
				id = setInterval(scrollUp, speed);
				hover(id);
			};
			
		};
		var sa = new ScrollAction($('.newsList'), 'li', 50, true);
		sa.start();
	});
	</script>

  </head>
<body  class="easyui-layout" style="overflow-y: hidden"  scroll="no">
 <div data-options="region:'north',split:true,border:false"  
    		style="overflow: hidden; height: 40px;
        		   background: url(  ${ctx}/images/home.gif ) #7f99be no-repeat left;
                   line-height: 32px;color: #fff; font-family: Verdana, 微软雅黑,黑体;font-size: 18px;">
                   
        <span style="float:right; padding-right:20px;" class="head">欢迎 【${sessionScope.loginName}】 
        	<a href="${ctx}/exitAction!pwdModify" id="editpass">修改密码</a> 
        	<a href="${ctx}/exitAction!exit"			 id="loginOut">安全退出</a>
        </span>
        <span>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp陈 雨 浓 电 子 办 公 管 理 系 统</span>
    </div>
	<div data-options="region:'west',title:'系统菜单'" class="west" style="padding: 0px;margin: 0px;">
	  <ul id="tree"></ul>
	</div>

	<div data-options="region:'center'">
	  <div class="easyui-tabs" fit="true" border="false" id="tabs" tabWidth="120" >
			<div title="首页" style="padding:20px;overflow:hidden;" id="home">
					<div style="font-family:黑体;font-size: 17px;"><span>新闻滚动栏   &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp公告显示栏</span></div>
					<div id="container" style="font-family:微软雅黑;font-size: 15px;">
			    	<ul class="newsList"   >
			            <li><a href="http://network.51cto.com/art/201507/486891.htm" title="Oracle数据库错误消息手册">Oracle数据库错误消息手册</a></li>
			            <li><a href="http://network.51cto.com/art/201507/486891.htm" title="ORACLE_SQL性能优化(这个很全的)">ORACLE_SQL性能优化(这个很全的)</a></li>
			            <li><a href="http://network.51cto.com/art/201507/486891.htm" title="小马win10永久激活工具 v20150727">小马win10永久激活工具 v20150727</a></li>
			            <li><a href="http://network.51cto.com/art/201507/486891.htm" title="大数据可视化之敏捷BI Tableau系">大数据可视化之敏捷BI Tableau系</a></li>
			            <li><a href="http://network.51cto.com/art/201507/486891.htm" title="大公司里的草还是小公司里的宝？">大公司里的草还是小公司里的宝？</a></li>
			            <li><a href="http://network.51cto.com/art/201507/486891.htm" title="插件:我的第一个插件–简约的scrollTop滑动插件">插件:我的第一个插件–简约的scrollTop滑动插件</a></li>
			            <li><a href="http://network.51cto.com/art/201507/486891.htm" title="PageRank:庆贺我的博客PR连升3级">PageRank:庆贺我的博客PR连升3级</a></li>
			        </ul>
			    </div>
			    	<div style="height:50px;   margin-top:107px;   margin-left: -10px;">
 			 		 <embed width="180" height="60" align="middle" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" allowscriptaccess="always" name="honehoneclock" bgcolor="#ff2ff3" quality="high" src="http://chabudai.sakura.ne.jp/blogparts/honehoneclock/honehone_clock_tr.swf" wmode="transparent">
 					  </embed>
 		   			 </div>	
 		   			<div id="noticeContainer" style="font-family:微软雅黑;font-size: 15px;">
			    	<ul class="noticeList"  id="noticeList" >
			        </ul>
			    </div>  
			</div>	      
	  </div>
	</div>
    <div data-options="region:'south',split:true" style="height: 30px; background: #D2E0F2; ">
        <div class="footer"><span>By CYN Email:dojavaee@163.com</span></div>
    </div>
    <div id=mm class="easyui-menu" style="width:150px;">
		<div id="mm-tabclose">关闭</div>
		<div id="mm-tabcloseall">全部关闭</div>
		<div id="mm-tabcloseother">除此之外全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-tabcloseright">当前页右侧全部关闭</div>
		<div id="mm-tabcloseleft">当前页左侧全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-exit">退出</div>
	</div>  
</body>
</html>