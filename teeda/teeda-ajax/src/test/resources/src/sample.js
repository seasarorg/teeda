debug = function(evt, element){
   alert(evt);
   alert(element);
}

move = function(){
    Position.init();
    Position.shiftBy('test:id0', 100,100);
}

var o ={
   show :function(v){
      alert(v);
   }, 
   
   debug : function(evt, element){
      this.show(evt);
      this.show(element);
   }
}

function on(evt){
    var elem = (evt.target) ? evt.target : evt.srcElement;
    if(elem.nodeType == 3){
      elem = elem.parentNode;
    }
    elem.className = 'on';  
}

function off(evt){
    var elem = (evt.target) ? evt.target : evt.srcElement;
    if(elem.nodeType == 3){
      elem = elem.parentNode;
    }
    elem.className = 'off';  
}
