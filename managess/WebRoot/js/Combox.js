

Power.Combox = function(config){
	
	Power.apply(this,config);
	Power.Combox.superclass.constructor.call(this);
	this.addEvents({
		beforerender:true,
		render:true,
		hide:true,
		show:true,
		beforehide:true,
		beforeshow:true,
		reset:true,
		beforereset:true
	});
	this.initComponent();
	
   
};

Power.extend(Power.Combox, Power.util.Observable,{
	bind:function(store){
	
		this.store = store;
	},
	onRender:function(ct,position){
		var inp_obj = document.createElement("input");
		ct.appendChild(inp_obj);
		inp_obj.style.width=this.width?this.width:70;
		inp_obj.maxlength='10' 
		inp_obj.value = this.text?this.text:"";
		inp_obj.defaultValue=this.text;
		//setInterval(function(){inp_obj.value=inp_obj.value;},10);
		var img_obj = document.createElement("img");
		img_obj.src = "images/select.jpg";
		img_obj.style.margin="3px 0px -3px -15px;";
		
		ct.appendChild(img_obj);
		var x = inp_obj.getBoundingClientRect().left;
	    var y = inp_obj.getBoundingClientRect().top;
		var userlist = this.store;
		var len_user = userlist.length;
		var str = "";
		for(var i=0;i<len_user;i++){
    	
    		if(i==0){
   				//var span_width=document.getElementById("hideDiv")!=null?(parseInt(document.getElementById("hideDiv").getBoundingClientRect().right)-parseInt(document.getElementById("hideDiv").getBoundingClientRect().left)):"0";
				//if(parseInt(span_width)>60){
					//userlist[i] = userlist[i].substr(0,5);
				//}
		 	}
   			str += "<li  style='cursor:hand;padding:0px;width:100%;color:\"#000000\"' onmouseout='this.style.backgroundColor=\"\";' onmouseover='this.style.backgroundColor=\"#4779cb\";event.cancelBubble=true;'><span style='width:100%'  >"+userlist[i]+"</span></li>";
    	}
		var div_str="<div id='' class='search-panel' onmousedown='event.cancelBubble=true;'   style='border:solid 1px #000000;'><ul id='search_ul'  total_height='0' lighthigh=-1 style='list-style-type:none;text-align:left;margin:0px;padding:0px;'>"+str+"</ul></div>";
    	ct.insertAdjacentHTML("beforeEnd",div_str);
    	var searchpanel = ct.lastChild;
    	img_obj.attachEvent("onmousedown", this.show.createDelegate(this,[searchpanel,inp_obj]));
    	inp_obj.attachEvent("onclick",this.show.createDelegate(this,[searchpanel,inp_obj]));
    	var span_obj = searchpanel.getElementsByTagName("span");
    	var span_len = span_obj.length;
    	var delegate_text = function(){};
    	
    	this.onselectchange=this.onselectchange?(this.onselectchange.createDelegate(this,[inp_obj])):(function(){});
    	this.appendSC= this.appendSC?(this.appendSC.createDelegate(this,[inp_obj])):undefined;
    	this.appendIC= this.appendIC?(this.appendIC.createDelegate(this,[inp_obj])):undefined;
    	this.onselectchange=this.appendSC?((this.onselectchange).createSequence(this.appendSC)):this.onselectchange;
    	this.oninputchange=this.oninputchange?(this.oninputchange.createDelegate(this,[inp_obj])):(function(){});
    	this.oninputchange=this.appendIC?(this.oninputchange.createSequence(this,appendIC)):this.oninputchange;
    	var combine_fn = function(){};
    	for(var i=0;i<span_len;i++){
    		//span_obj[i].attachEvent("onclick",(this.getName.createDelegate(this,[inp_obj,span_obj[i].innerHTML,searchpanel])).createSequence(this.onselectchange.createDelegate(this,[inp_obj.tagName])));
    		delegate_text = this.getName.createDelegate(this,[inp_obj,span_obj[i].innerHTML,searchpanel]);
    		combine_fn = delegate_text.createSequence(this.onselectchange);
    		//span_obj[i].attachEvent("onclick",combine_fn);
    		
    		span_obj[i].parentNode.attachEvent("onclick",combine_fn);
    	}
    	//inp_obj.onafterpaste=function(){if(isNaN(this.value))execCommand('undo');};
		//inp_obj.onchange=function(){if(isNaN(this.value) || this.value.search(/[^u4e00-u9fa5]/)!=-1) {this.value=this.value.replace(/[^u4e00-u9fa5]|\D/g,'');}else{ getValue(this);}};
   		inp_obj.attachEvent("onpropertychange",this.oninputchange.createSequence(this.hide.createDelegate(this,[searchpanel])));
   		//this.hide.createDelegate(this,[searchpanel])
   		inp_obj.attachEvent("onblur",function(obj){obj.hide.defer(100,this,[searchpanel]);}.createDelegate(this,[this]));
   		this.on("reset",(function(obj,text){if(obj.value==""){obj.value = text};}.createDelegate(this,[inp_obj,this.text])))
   		searchpanel.style.left=x;
        searchpanel.style.top=parseInt(y)+20;
        searchpanel.style.width=parseInt(inp_obj.currentStyle.width)-13;
        searchpanel.style.height=127;
        searchpanel.style.display='none';
	},
	afterRender : function(){
        //Power.BoxComponent.superclass.afterRender.call(this);
        /*this.renderReady = true;
        this.setSize(this.width, this.height);
        if(this.x || this.y){
            this.setPosition(this.x, this.y);
        }else if(this.pageX || this.pageY){
            this.setPagePosition(this.pageX, this.pageY);
        }*/
    },
	render:function(container,position){
	
		if(typeof container !='object') {
			if((container=document.getElementById(container))==null){
				throw new Error("render is wrong!");
				return;
			}
		}
		if(position !== undefined){
            if(typeof position == 'number'){
                position = container.childNodes[position];
            }else if(typeof position !='object'){
                
                return;
                throw new Error("render is wrong!");
            }
			
		}
		this.rendered = true;
		this.onRender(container,position || null);
		this.fireEvent("render", this);
		this.afterRender(this.container);
	},
	initComponent:function(){
	
		this.render(this.renderTo);
		this.bind(this.store);
	},
	getName:function(){
	
		arguments[0].value = arguments[1];
		this.hide(arguments[2]);
	},
	show:function(){
	  if(arguments[0]!=undefined && arguments[0]!=null){
    		arguments[0].style.display="block";
    		arguments[1].focus();
    		arguments[1].select();
    		event.cancelBubble=true;
    		document.documentElement.attachEvent("onmousedown",function(a){a.style.display="none";}.createDelegate(this,[arguments[0]]));
      }
	},
	hide:function(o){
		if(o!=undefined){
			o.style.display="none";
		}
	},
	onreset:function(o,text){
	
		if(o!=undefined){
		
			if(o.type!=undefined && o.type=="text"){
			
				o.value = text;
			}
		}
	}
});

