var is_view_tip = "1";
var interval_time = 1;


		
		
/** 初始载入数据时候的 弹出消息框
		function alertLogin(){
				jQuery.messager.show({
					title 			: 		"提示",
					msg 			:	 	"恭喜您，登录成功！",
					timeout		:		1500
				});
		}
*/

$(function(){  
	$("#tree").tree({
		url						:		getCurProjPath()+"/mainAction!leftTreeMenu?date="+new Date()+"",
		lines					:		false, 
		animate 				:		true,
		onClick:function (node) {
			var isleaf 				= 		node.isleaf;
			var menu_href 		= 		node.menu_href;
			if (isleaf == 1){
				addTab(node.text, menu_href, null);
			}
	}});
	function addTab(subtitle, url, icon) {
		if (!$("#tabs").tabs("exists", subtitle)) {
			$("#tabs").tabs("add", {
					title:			subtitle, 
					content:		"<iframe scrolling=\"auto\" frameborder=\"0\"  src=\"" + url + "\" style=\"width:100%;height:100%;\"></iframe>", 
					closable:		true, 
					icon:			icon
			});
		} else {
			$("#tabs").tabs("select", subtitle);
		}
		tabClose();
	}
	function tabClose() {
		/*双击关闭TAB选项卡*/
		$(".tabs-inner").dblclick(function () {
			var subtitle = $(this).children(".tabs-closable").text();
			$("#tabs").tabs("close", subtitle);
		});
		/*为选项卡绑定右键*/
		$(".tabs-inner").bind("contextmenu", function (e) {
			$("#mm").menu("show", {left:e.pageX, top:e.pageY});
			var subtitle = $(this).children(".tabs-closable").text();
			$("#mm").data("currtab", subtitle);
			$("#tabs").tabs("select", subtitle);
			return false;
		});
	}
	//绑定右键菜单事件
	
	
		//关闭当前
	$("#mm-tabclose").click(function () {
		var currtab_title = $("#mm").data("currtab");
		$("#tabs").tabs("close", currtab_title);
	});
		//全部关闭
	$("#mm-tabcloseall").click(function () {
		$(".tabs-inner span").each(function (i, n) {
			var t = $(n).text();
			if (t != "\u9996\u9875") {
				$("#tabs").tabs("close", t);
			}
		});
	});
		//关闭除当前之外的TAB
	$("#mm-tabcloseother").click(function () {
		var currtab_title = $("#mm").data("currtab");
		$(".tabs-inner span").each(function (i, n) {
			var t = $(n).text();
			if (t != "\u9996\u9875") {
				if (t != currtab_title) {
					$("#tabs").tabs("close", t);
				}
			}
		});
	});
		//关闭当前右侧的TAB
	$("#mm-tabcloseright").click(function () {
		var nextall = $(".tabs-selected").nextAll();
		if (nextall.length == 0) {
				//msgShow('系统提示','后边没有啦~~','error');
			alert("\u540e\u8fb9\u6ca1\u6709\u5566~~");
			return false;
		}
		nextall.each(function (i, n) {
			var t = $("a:eq(0) span", $(n)).text();
			if (t != "\u9996\u9875") {
				$("#tabs").tabs("close", t);
			}
		});
		return false;
	});
		//关闭当前左侧的TAB
	$("#mm-tabcloseleft").click(function () {
		var prevall = $(".tabs-selected").prevAll();
		if (prevall.length == 0) {
			alert("\u5230\u5934\u4e86\uff0c\u524d\u8fb9\u6ca1\u6709\u5566~~");
			return false;
		}
		prevall.each(function (i, n) {
			var t = $("a:eq(0) span", $(n)).text();
			if (t != "\u9996\u9875") {
				$("#tabs").tabs("close", t);
			}
		});
		return false;
	});
	
		//退出
	$("#mm-exit").click(function () {
		$("#mm").menu("hide");
	});
	
	if (is_view_tip = "1"){
		viewTipFun();
		window.setInterval(viewTipFun,100000*60*interval_time);
	}
});

function viewTipFun(){
	//获取提醒的消息内容。
	var url = "SSH_Project_3/messageAction!viewTipMsg?date="+new Date()+"";
	jQuery.post(url,function(msgtxt){
		if (msgtxt != ""){
			//弹出消息框
			jQuery.messager.show({
				title:				"系统消息提醒",
				width:				300,
				height:				200,
				msg:					msgtxt,
				timeout:				1000*10,
				showType:			'slide'
			});			
		}
	},"text");
}

function addTab(subtitle, url, icon) {
	
	if (!$("#tabs").tabs("exists", subtitle)) {
		$("#tabs").tabs("add", {
				title:			subtitle, 
				content:		"<iframe scrolling=\"auto\" frameborder=\"0\"  src=\"" + url + "\" style=\"width:100%;height:100%;\"></iframe>", 
				closable:		true, 
				icon:			icon
		});
	} else {
		$("#tabs").tabs("select", subtitle);
	}
	tabClose();
}
function tabClose() {
	/*双击关闭TAB选项卡*/
	$(".tabs-inner").dblclick(function () {
		var subtitle = $(this).children(".tabs-closable").text();
		$("#tabs").tabs("close", subtitle);
	});
	/*为选项卡绑定右键*/
	$(".tabs-inner").bind("contextmenu", function (e) {
		$("#mm").menu("show", {left:e.pageX, top:e.pageY});
		var subtitle = $(this).children(".tabs-closable").text();
		$("#mm").data("currtab", subtitle);
		$("#tabs").tabs("select", subtitle);
		return false;
	});
}
