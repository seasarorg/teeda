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
