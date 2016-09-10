    /**
        var pagescrolling=new pagescrolling();
        pagescrolling.setCurrentPage("2");
        pagescrolling.setTotalPage("4");
        pagescrolling.setRecordCount("10");
        var div_obj=document.getElementById("view_div");
        div_obj.innerHTML=pagescrolling.viewHtml();
        function movePageTo(page_no){
            pagescrolling.setCurrentPage(page_no);
            div_obj.innerHTML=pagescrolling.viewHtml();
        }
    */

    function PageScrolling(){

	me=this;

	var current_page;

	var total_page;

	var record_count;
    var data;
	
	var maxnum;
	var startnum;
	
	this.setData=function(_data){
                data=parseInt(_data);
	}
	this.getData = function(){
	
		return data;
	}
	
	this.setCurrentPage=function(page_no){
                current_page=parseInt(page_no);
	}

	this.getCurrentPage=function(){
            if(current_page<1)
                    current_page=1;
            else if(current_page>total_page)
                    current_page=total_page;

            return current_page;
	}

	this.setTotalPage=function(total){
            total_page=data.split(",").length;
	}

	this.getTotalPage=function(){
            total_page=data.split(",").length;
	}

	this.setRecordCount=function(count){
            record_count=data.split(",").length;
	}

	this.getRecordCount=function(){
             record_count=data.split(",").length;

            return record_count;
	}
	this.setMaxNum=function(maxNum){
		maxnum = data.split(",").length;
	}
	this.setpageitem=function(len){				
            record_pageitem=parseInt(1);
	}

	this.getpageitem=function(){
            if(record_pageitem<1)
            record_pageitem=1;
            return record_pageitem;
	}
	
	function countPage2(){
            var str_code="页次："+me.getCurrentPage()+"/"+total_page+"页&nbsp;&nbsp;共"+me.getLength()+"条&nbsp;&nbsp;"+me.getpageitem()+"条/页";
            return str_code;
	}
	
	function countPage(){
            var str_code="共"+me.getCurrentPage()+"/"+total_page+"页";
            return str_code;
	}

	function firstPage(){
            var str_code="";
            if(total_page==1 || current_page==1){
            	str_code = "首页";
            }
            else{
            	if(!option){
                	str_code="<a href='javascript:movePageTo(1,0,5);' title='Alt+F'>首页</a>";
                }else{
                	var tmp_option = opt_value;
                	str_code="<a href='javascript:movePageTo(1,"+tmp_option+");' title='Alt+F'>首页</a>";
                }
            }
            return str_code;
	}

	function previousPage(){
            var str_code="";
            if(total_page==1 || current_page==1){
            	str_code = "上一页";
            }
            else{
            	if(!option){
            	
	                var temp_current_page = (current_page-1)*record_pageitem-record_pageitem;
	                var temp_page=current_page-1;
	                str_code="<a href='"url+"' title='Alt+P'>上一页</a>";
                }else{
              
                	var temp_current_page=current_page-1;
                	var tmp_option = opt_value;
                	str_code="<a href='javascript:movePageTo("+temp_current_page+",0);' title='Alt+P'>上一页</a>";
                	
                }
            }
            return str_code;
	}

	function nextPage(){
            var str_code="";
            if(total_page==1 || current_page==total_page){
            	str_code = "下一页";
            }
            else{
            	if(!option){
	               var temp_current_page = (current_page+1)*record_pageitem-record_pageitem;
	               var temp_page=current_page+1;
	                str_code="<a href='javascript:movePageTo("+temp_page+","+temp_current_page+","+record_pageitem+");' title='Alt+N'>下一页</a>";
                }else{
                	
                	var temp_current_page=current_page+1;
                	var tmp_option = opt_value;
                	str_code="<a href='javascript:movePageTo("+temp_current_page+",1);' title='Alt+N'>下一页</a>";
                }
            }
            return str_code;
	}

	function lastPage(){
            var str_code="";
            if(total_page==1 || current_page==total_page){
            	str_code = "尾页";
            }
            else{
            	if(!option){
            		var temp_current_page = (total_page)*record_pageitem-record_pageitem;
            		var temp_page=total_page;
                	str_code="<a href='javascript:movePageTo("+temp_page+","+temp_current_page+","+record_pageitem+");' title='Alt+L'>尾页</a>"
                }else{
                	var tmp_option = opt_value;
                	str_code="<a href='javascript:movePageTo("+total_page+","+tmp_option+");' title='Alt+L'>尾页</a>"
                }
            }
            return str_code;
	}
	
	function go(){
		
		var str_code="";
		var select_obj = document.createElement("SELECT");
		var tmp_select = "<select id='go' onchange='movePageTo(this.value);this.disabled=true;' style=''>"
		var tmp_opt = "";
		for(var i=1;i<=maxnum;i++){
			tmp_opt +="<option value="+i+">"+i+"</option>";
		}
		tmp_select=tmp_select+tmp_opt+"</select>";
		str_code= "跳转至&nbsp;&nbsp;"+tmp_select+" 页";
		return str_code;
	
	}

	this.viewHtml=function(){
        var view_html=countPage()+"&nbsp;&nbsp;"+firstPage()+"&nbsp;&nbsp;"+previousPage()+"&nbsp;&nbsp;"+nextPage()+"&nbsp;&nbsp;"+lastPage()+" " + go();
        return view_html;
	}
	
	this.viewHtml2=function(){
            var view_html=firstPage()+"&nbsp;&nbsp;"+previousPage()+"&nbsp;&nbsp;"+nextPage()+"&nbsp;&nbsp;"+lastPage()+"&nbsp;&nbsp;"+countPage2();
            return view_html;
	}
    }