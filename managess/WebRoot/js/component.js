Power.Component = function(config){
    config = config || {};
    if(config.initialConfig){
        if(config.isAction){
            this.baseAction = config;
        }
        config = config.initialConfig; 
    }else if(config.tagName || config.dom || typeof config == "string"){
        config = {applyTo: config, id: config.id || config};
    }
    this.initialConfig = config;
    Power.apply(this, config);
    this.addEvents({
        disable : true,
        enable : true,
        beforeshow : true,
        show : true,
        beforehide : true,
        hide : true,
        beforerender : true,
        render : true,
        beforedestroy : true,
        destroy : true
    });
    Power.Component.superclass.constructor.call(this);
    this.initComponent();
    if(this.renderTo){
        this.render(this.renderTo);
        delete this.renderTo;
    }
};

Power.extend(Power.Component, Power.util.Observable, {
    implementor :{},
    init:function(){
    },
    initComponent:function(){
    
    
    },
    render : function(container, position){
        
    },
    // private
    // default function is not really useful
    onRender : function(ct, position){
        
    },
	afterRender :function(){
	 
	}
});
