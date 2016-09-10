function page(){
	var obj;
	var pageno ;
	var page_no;
	var total_name;
	var back_flag;
	this.setbackflag = function(flag){
	
		back_flag = flag;
	}
	this.seturl = function(url){
		obj = url.split(",");
	}
	this.settotalpage=function(total_filenames){
	
		total_name = total_filenames;
	}
	
	this.setpageno = function(pagen){
		pageno = pagen;
	}
	function numpage(){
		var count = obj.length;
		var str = "";
		for(var i =0;i<count;i++){
			if(back_flag){
				if(i==pageno){
					str = str +'<a href="'+obj[i]+'?{back:1}" style="color:red;">['+(i+1)+']</a>   ';
				}else{
					str = str +'<a href="'+obj[i]+'?{back:1}">'+(i+1)+'</a>     ';
				}
			}else{
				if(i==pageno){
					str = str +'<a href="'+obj[i]+'" style="color:red;">['+(i+1)+']</a>   ';
				}else{
					str = str +'<a href="'+obj[i]+'">'+(i+1)+'</a>     ';
				}
			}
		}
		return str;
	}
	
	function totalpage(){
		var count = obj.length-1;
		var str = "";
		if(count !=0 && count != pageno){
			//page_no = pageno +1;
			if(back_flag){
				str = str + '<a href="'+total_name+'?{pos:['+(pageno+1)+','+(obj.length)+'],back:1}">余下全页</a>';
			}else{
			
				str = str + '<a href="'+total_name+'?{pos:['+(pageno+1)+','+(obj.length)+']}">余下全页</a>';
			}
		}
		else if(count==pageno && count !=0){
			str = '余下全页';
		}
		else if(count ==0){
			str = '';
		}
		return str;
	}
	
	
	function nextpage(){
		var count = obj.length-1;
		var str = "";
		if(count !=0 && count != pageno){
			page_no = pageno +1;
			if(back_flag){
				str = str+'<a href="'+obj[page_no]+'?{back:1}">下一页</a>';
			}else{
			
				str = str+'<a href="'+obj[page_no]+'">下一页</a>';
			}
			
		}
		else if(count==pageno && count !=0){
			str = '下一页';
		}
		else if(count ==0){
			str = '';
		}
		return str;
	}
	
	
	function prepage(){
		var count = obj.length;
		var str = "";
		if(count != 0 && pageno!=0){
			page_no = pageno -1;
			if(back_flag){
				str = str + '<a href="'+obj[page_no]+'?{back:1}">上一页</a>';
			}else{
			
				str = str + '<a href="'+obj[page_no]+'">上一页</a>';
			}
		}
		else if(count != 0 && pageno==0){
			str = '上一页';
		}
		else if(count == 0){
			str = '';
		}
		return str;
	}
	
	 this.veiwHTML = function(){
		var str ="";
		str = numpage() +"   "+prepage()+"   "+nextpage()+"  " + totalpage();
		return str;
	 }
}
function viewPage(container,url,curpageno,total_filenames,flag){

	var pagess = new page();
	pagess.setbackflag(flag);
	pagess.seturl(url);
	pagess.setpageno(curpageno);
	pagess.settotalpage(total_filenames);
	container.innerHTML =pagess.veiwHTML();

}