	function getCurProjPath() {
		var curWwwPath = window.document.location.href;
		var pathName = window.document.location.pathname;
		var pos = curWwwPath.indexOf(pathName);
		var localhostPath = curWwwPath.substring(0, pos);
		var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
		return (localhostPath + projectName);
	}
	
function moreThanZeroD(){     // 大于0 的正整数
	if(this.value.length==1)
	{ 
	  this.value=this.value.replace(/[^1-9]/g,'')
	}
	else{
		this.value=this.value.replace(/\D/g,'')
	}
}
