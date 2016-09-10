function page(){
	var obj;
	var pageno ;
	var page_no;
	this.seturl = function(url){
		obj = url.split(",");
	}
	
	
	this.setpageno = function(pagen){
		pageno = pagen;
	}
	
	
	function numpage(){
		var count = obj.length;
		var str = "";
		for(var i =0;i<count;i++){
			if(i==pageno){
				str = str +'<a href="'+obj[i]+'" style="color:red;">['+(i+1)+']</a>   ';
			}else{
				str = str +'<a href="'+obj[i]+'">'+(i+1)+'</a>     ';
			}
		}
		return str;
	}
	
	
	function nextpage(){
		var count = obj.length-1;
		var str = "";
		if(count !=0 && count != pageno){
			page_no = pageno +1;
			str = str + '<a href="'+obj[page_no]+'">下一页</a>   ';
		}
		else if(count==pageno && count !=0){
			str = '下一页   ';
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
			str = str + '<a href="'+obj[page_no]+'">上一页</a>   ';
		}
		else if(count != 0 && pageno==0){
			str = '上一页   ';
		}
		else if(count == 0){
			str = '';
		}
		return str;
	}
	
	 this.veiwHTML = function(){
		var str ="";
		str = numpage() +"   "+prepage()+"   "+nextpage();
		return str;
	 }
}


function viewPage(container,url,currpage){
	var pagess = new page();
	pagess.seturl(url);
	pagess.setpageno(currpage);
	container.innerHTML =pagess.veiwHTML();
}
		