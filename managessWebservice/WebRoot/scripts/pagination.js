function pagination(pageid,eachpagenum){
	var target = document.forms[0];
	target.pageid.value = pageid;
	target.eachpagenum.value = eachpagenum;
	target.submit();
}

function jumpto(total,eachpagenum){
	var target = document.forms[0];
	 var reg =/^\d+$/;
     var title = document.getElementById("goto");
          if (reg.test(title.value)){
              var totals = parseInt(total,10);
              if(title.value>0&&title.value<=totals){
                  target.pageid.value = title.value;
				  target.eachpagenum.value = eachpagenum;
				  target.submit();
              }else{
                  if(total===0){
                  	window.alert("当前无可用页码");
                  }else{
                  window.alert("页码范围为1至"+total);
                  }
                  title.value="";
                  return;
              }
          }else{
              window.alert("请输入正确的页码");
              title.value="";
              return;
          }
}

function jumpto2(total,eachpagenum,url){
	 var reg =/^\d+$/;
     var title = document.getElementById("goto");
          if (reg.test(title.value)){
              var totals = parseInt(total,10);
              if(title.value>0&&title.value<=totals){
                  window.location=url+"pageid="+title.value+"&eachpagenum="+eachpagenum;
              }else{
                  if(total===0){
                  	window.alert("当前无可用页码");
                  }else{
                  window.alert("页码范围为1至"+total);
                  }
                  title.value="";
                  return;
              }
          }else{
              window.alert("请输入正确的页码");
              title.value="";
              return;
          }
}