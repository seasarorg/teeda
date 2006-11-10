
if (typeof(Kumu) == 'undefined') {
  Kumu = {};
}

if (typeof(Kumu.Dis) == 'undefined') {
  Kumu.Dis = {  
    lock : function(element) {
      if(!element.__lock){
        element.__lock = true;
      }
    },
                     
    unlock : function(element) {
      setTimeout(function(){
        if(element.__lock){
          element.__lock = false;
        }
      }, 5000);
    },

    disable : function(evt){
      var self = Kumu.Dis;
      evt = evt || window.event
      element = evt.target || evt.srcElement;
      if(element.__lock){
        if (evt.preventDefault) { 
          evt.preventDefault(); 
          evt.stopPropagation(); 
        } else {
          evt.returnValue = false;
          evt.cancelBubble = true;
        }
        return false;
      }else{
        self.lock(element);
        self.unlock(element);
        return true;
      }
    },

    disabledForm : function(targetForm/* form */) {
      var self = Kumu.Dis;
      if(targetForm.onsubmit){
      }else{
        targetForm.onsubmit = function(e){
          return self.disable(e);
        };
      }
    }
  }
}


