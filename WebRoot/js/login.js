
var loginAndRegDialog;
$(function() {
	loginAndRegDialog = $("#loginAndRegDialog"); //取到DIV元素 渲染成对话
	loginAndRegDialog.dialog({
	    title:				"用户登陆",  
	    width:			500,
	    height:			300,   // 尺寸不用px单位
		closable : 		false, //是否可关闭
		modal : 			true,  //模态窗口 原窗口在新窗口弹出的时候是否可移动
		buttons : [ 
		{
			text 					: 		'注册',
			handler 				:		 function() {}
		},{
			text 					:		 '登陆',
			handler 				:		 function() {  login(); }
			}]
	});
});

function  login(){ 
  var username 	= document.getElementById("username").value;
  var password 	= document.getElementById("password").value;
  var vcode 	 	= document.getElementById("vcode").value;
  
if(username==null||username.length==0||password==null||password.length==0||vcode==null||vcode.length==0)
{ jQuery.messager.alert("操作提示","用户名,密码或验证码不能为空！","error");}
else{
		      var loginurl	 	=  getCurProjPath()+"/loginAction!login?"+new Date()+"";
		      var loginObj   	=
		       {
		             "username"		:		username,
		             "password"		:		password,
		             "vcode"			:		vcode
		       };
			 jQuery.post(loginurl,loginObj,function loginCallback(jsonData){
					var vcodeFlag	 = 	jsonData.vcodeFlag;
					if (vcodeFlag == false){
						jQuery.messager.alert("操作提示","验证码有误，请重新输入！","info",function(){
							$("#captcha").click(); 
							$("#vcode").val("");    // 值清空
							$("#vcode").focus();  // 验证码获取焦点
							});
					}else{
						var loginFlag	= 	jsonData.loginFlag;
						if(loginFlag==false){
						 	jQuery.messager.alert("操作提示","用户名或者密码错误！","error",function(){
								window.location.reload();   
							 	});
						}
						else{
							loginAndRegDialog.dialog('close');
							var mainURL = getCurProjPath()+"/jsp/main.jsp";
							window.location.href = mainURL;
						}
					}
				},"json");
    }
}

//<![CDATA[
$(function(){
		//如果是必填的，则加红星标识.
		$("form :input.required").each(function(){
			var $required = $("<strong class='high'>&nbsp&nbsp*</strong>"); //创建元素
			$(this).parent().append($required); //然后将它追加到文档中
		});
         //文本框失去焦点后
	    $('form :input.required').blur(function(){
			 var $parent = $(this).parent();
			 $parent.find(".formtips").remove();
			 //验证用户名
			 if( $(this).is('#username') ){
					if( this.value!="" && this.value.length <=15  && this.value.length>=3 && /^[A-Za-z0-9]+$/.test(this.value)){
						   var okMsg = '输入正确';
						    $parent.append('<span class="formtips onSuccess">'+okMsg+'</span>');
					}else{
					    var errorMsg = '3-15位数字英文组合';
                        $parent.append('<span class="formtips onError">'+errorMsg+'</span>');
					}
			 }
			 //密码
			 if( $(this).is('#password') ){
					if( this.value!=""){
						   var okMsg = '输入正确';
						    $parent.append('<span class="formtips onSuccess">'+okMsg+'</span>');
					}else{
					    var errorMsg = '密码不能为空';
                     $parent.append('<span class="formtips onError">'+errorMsg+'</span>');
					}
			 }
	});
});
//]]>