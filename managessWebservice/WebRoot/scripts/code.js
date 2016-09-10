//AJAX
var xmlhttp;
function createXMLHttpRequest() {
	if (window.ActiveXObject) {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	} else {
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		}
	}
}

    


       //AJAX执行完毕
function processFinishReload() {
	if (xmlhttp.readyState == 4) {
		if (xmlhttp.status == 200) {
			var status = xmlhttp.responseText;
			alert("\u5220\u9664\u6210\u529f");

            window.location.reload();
			//document.form1.submit();
		}
	}
}     

      //AJAX执行完毕
function processFinish() {
	if (xmlhttp.readyState == 4) {
		if (xmlhttp.status == 200) {
			var status = xmlhttp.responseText;
			alert("\u5220\u9664\u6210\u529f");

              //window.location.reload();
			document.form1.submit();
		}
	}
}


      

      //AJAX验证验证码的合法性
function checkValidatorCode() {
    
    var username=fntrimspace(document.forms[0].j_username.value);
    
    var password=fntrimspace(document.forms[0].j_password.value);
    
    if(username==""&&password=="")
    {
    var error = document.getElementById("valcode");
    error.innerHTML = "请输入用户名和密码";
    
    }else if(username==""){
    var error = document.getElementById("valcode");
    error.innerHTML = "请输入用户名";
    }else if(password==""){
    var error = document.getElementById("valcode");
    error.innerHTML = "请输入密码";
    }else{
    var code = document.forms[0].check.value; 
    
	createXMLHttpRequest();
	xmlhttp.onreadystatechange = validatorCode;
	
	xmlhttp.open("get", "/plms/fpreport/query/checkvalcode.htm?code=" + code, true);
	
	xmlhttp.send(null);
	
	}
}

function validatorCode(){
	if (xmlhttp.readyState == 4) {
		if (xmlhttp.status == 200) {
			var status = xmlhttp.responseText;
			if (status =="error"){
			
				var error = document.getElementById("valcode");
				document.forms[0].check.value = ""; 
				change();
				error.innerHTML = "验证码错误";
			}else{
			
			  document.forms[0].submit();
			}
		}
	}
	
}
// js中去掉空格的方法
function fntrimspace(str)//去除首尾空格函数
{
   while(str.substring(0,1)==" ")
   {
    str=str.substring(1);
   }
   while(str.substring(str.length-1)==" ")
   {
    str=str.substring(0,str.length-1);
   }
   return str;
}



//动态生成验证码图片
function change() 
{ 

	document.all.img.src='/plms/commons/img.jsp?id=' + (new Date()).getTime();
	
}

