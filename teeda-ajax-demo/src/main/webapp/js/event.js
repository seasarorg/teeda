if (typeof(Kumu.Event) == 'undefined') {
    Kumu.Event = {};
}

Kumu.extend(Kumu.Event, {

  stop: function(event) {
    if (event.preventDefault) { 
      event.preventDefault(); 
      event.stopPropagation(); 
    } else {
      event.returnValue = false;
    }
  },

  caches: false,
  
  _cached: function(element, name, observer, useCapture) {
  	var self = Kumu.Event;
    if (!self.caches){
    	 self.caches = [];
    }
    if (element.addEventListener) {
      self.caches.push([element, name, observer, useCapture]);
      element.addEventListener(name, observer, useCapture);
    } else if (element.attachEvent) {
      self.caches.push([element, name, observer, useCapture]);
      element.attachEvent('on' + name, observer);
    }
  },
  
  unloadCache: function() {
  	var self = Kumu.Event;
    if (!self.caches){
    	 return;
    }
    
    for (var i = 0; i < self.caches.length; i++) {
      self.stopObserving.apply(this, self.caches[i]);
      self.caches[i][0] = null;
    }
    self.caches = false;
  },

  observe: function(element, name, observer, useCapture) {
  	var self = Kumu.Event;
    var ele = $i(element);
    useCapture = useCapture || false;
    
    if (name == 'keypress' && (navigator.appVersion.match(/Konqueror|Safari|KHTML/) 
     || ele.attachevent)){
      name = 'keydown';
    }
    self._cached(ele, name, observer, useCapture);
  },

  stopObserving: function(element, name, observer, useCapture) {
  	var self = Kumu.Event;
    var element = $i(element);
    useCapture = useCapture || false;
    
    if (name == 'keypress' && ((navigator.appVersion.indexOf('AppleWebKit') > 0) || element.detachevent)){
      name = 'keydown';
    }
    
    if (element.removeeventListener) {
      element.removeeventListener(name, observer, useCapture);
    } else if (element.detachevent) {
      element.detachevent('on' + name, observer);
    }
  }
  
});

Kumu.Event.observe(window, 'unload', Kumu.Event.unloadCache, false);
