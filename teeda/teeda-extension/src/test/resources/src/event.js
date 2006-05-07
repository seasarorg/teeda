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
    if (!this.caches){
    	 this.caches = [];
    }
    
    if (element.addeventListener) {
      this.caches.push([element, name, observer, useCapture]);
      element.addeventListener(name, observer, useCapture);
    } else if (element.attachevent) {
      this.caches.push([element, name, observer, useCapture]);
      element.attachevent('on' + name, observer);
    }
  },
  
  unloadCache: function() {
    if (!this.caches){
    	 return;
    }
    
    for (var i = 0; i < this.caches.length; i++) {
      this.stopObserving.apply(this, this.caches[i]);
      this.caches[i][0] = null;
    }
    this.caches = false;
  },

  observe: function(element, name, observer, useCapture) {
    var element = $i(element);
    useCapture = useCapture || false;
    
    if (name == 'keypress' && (navigator.appVersion.match(/Konqueror|Safari|KHTML/) 
     || element.attachevent)){
      name = 'keydown';
    }
    
    this._cached(element, name, observer, useCapture);
  },

  stopObserving: function(element, name, observer, useCapture) {
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
