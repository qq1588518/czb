function DelCookie(name)
{
	//var cval = getCookie (name);
	document.cookie = name + "=; expires="+ new Date(0).toGMTString();
}

function delCookies()
{
	DelCookie("nickname");
	DelCookie("password");
	DelCookie("session_id");
	DelCookie("rem");
	DelCookie("cookie");
	window.location.reload();
}

function getCookie(name)
	{
	var result=null;
	var myCookie=" "+document.cookie+";";
	var searchName=" "+name+"=";
	var startOfCookie=myCookie.indexOf(searchName);
	var endOfCookie;
	if(startOfCookie!=-1)
	{
	startOfCookie+=searchName.length;
	endOfCookie=myCookie.indexOf(";",startOfCookie);
	result=unescape(myCookie.substring(startOfCookie,endOfCookie));
	}
	return result;
}

function getMyhref()
{
		//remember the current href
    	var loc=self.location.href;
    	var local=loc.split("/");
    	var xxx=local[local.length-1];
    	var never=new Date();
    	never.setTime(never.getTime()+60*60*1000);
    	
    	var expString="expires="+never.toGMTString()+";";
    	
    	saveCookie("myhref",xxx,expString)
    	myhref=""+getCookie("myhref");
}

function saveMyhref(value)
{
	var never=new Date();
   	never.setTime(never.getTime()+60*60*1000);
    	
   	var expString="expires="+never.toGMTString()+";";
    	
   	saveCookie("myhref",escape(value),expString)
   	myhref=""+getCookie("myhref");
}

function saveCookie(key,value,expString)
{
   	document.cookie=""+key+"="+value+";"+expString;//iframe href
}

function createRandomMultiID(max){//产生随机数
	        var rtn="";
	        for(var i=0;i<max;i++){
	           rtn=rtn+this.createRandomSingleID();
	        }
	        
	        return rtn;
    	}

function createRandomSingleID(){//产生随机数
	        var max=35;
	        var rtn;
	        var rd=Math.random();
	        var tmp_rd=rd+"";
	        var tmp_rd_len=tmp_rd.length;
	        tmp_rd=tmp_rd.substring(tmp_rd_len-2,tmp_rd_len);
	
	        tmp_rd=tmp_rd % max;
	        var int_tmp=tmp_rd;
	        if(int_tmp>9){
	            var tmp_str="A";
	            var a_unicode_value=tmp_str.charCodeAt(0);
	            var disp=a_unicode_value + (int_tmp -10);
	            
	            rtn=String.fromCharCode(disp);
	
	        }
	        else{
	            rtn=int_tmp;
	        }

        	return rtn;
    	}
    	
    	
    	
    	
function createRandomMultiID(max){
       var rtn="";
       for(var i=0;i<max;i++){
          rtn=rtn+createRandomSingleID();
       }
       
       return rtn;
  	}
 function createRandomSingleID(){
       var max=35;
       var rtn;
       var rd=Math.random();
       var tmp_rd=rd+"";
       var tmp_rd_len=tmp_rd.length;
       tmp_rd=tmp_rd.substring(tmp_rd_len-2,tmp_rd_len);

       tmp_rd=tmp_rd % max;
       var int_tmp=tmp_rd;
       if(int_tmp>9){
           var tmp_str="A";
           var a_unicode_value=tmp_str.charCodeAt(0);
           var disp=a_unicode_value + (int_tmp -10);
           
           rtn=String.fromCharCode(disp);

       }
       else{
           rtn=int_tmp;
       }

      	return rtn;
  	}
 	