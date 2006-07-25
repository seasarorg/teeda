Autocompleter.Teeda = Class.create();
Object.extend(Object.extend(Autocompleter.Teeda.prototype, Autocompleter.Base.prototype), {
  initialize: function(element, update, compoptions, options) {
	  this.baseInitialize(element, update, options);
    this.options.asynchronous  = true;
    this.options.onComplete    = this.onComplete.bind(this);    
		this.options.parameters = $H(compoptions).toQueryString();
    this.options.defaultParams = this.options.parameters || null;
    this.url                   = 'teeda.ajax';
  },

  getUpdatedChoices: function() {
    entry = encodeURIComponent(this.options.paramName) + '=' + 
      encodeURIComponent(this.getToken());

    this.options.parameters = this.options.callback ?
      this.options.callback(this.element, entry) : entry;

    if(this.options.defaultParams) 
      this.options.parameters += '&' + this.options.defaultParams;

    new Ajax.Request(this.url, this.options);
  },

  onComplete: function(request) {
    var data = '<ul>';
    //JSON
    json = eval('('+request.responseText+')');
    json = json['data'];
    for (var i = 0; i < json.length; i++) {
	    var detail = json[i];
			if(this.options.decorator){
			  var decorator = this.options.decorator;
				detail = decorator(i, detail);
			}
			data += '<li>'+detail+'</li>';
  	}
    data += '</ul>';
    this.updateChoices(data);
  }

});


function teedaAutocompleter(element, param, options) {
	new Autocompleter.Teeda(element, element+'_auto_complete', param, options );
}

