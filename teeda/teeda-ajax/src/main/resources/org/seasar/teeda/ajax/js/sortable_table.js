/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
if (typeof(Kumu) == 'undefined') {
  Kumu = {};
}else{
  Kumu.dynamicLoad('event');
}

if (typeof(Kumu.Html) == 'undefined') {
  Kumu.Html = {};
}

if (typeof(Kumu.Html.SortableTable) == 'undefined') {
  Kumu.Html.SortableTable = Kumu.create();
}

Kumu.Html.SortableTable.prototype = Kumu.extend(Kumu.Html.SortableTable.prototype, {
  
  _ordertype : [],
  
  _header : [],
  
  _tbody : [],
  
  _sortbody : [],
    
  start : function(target, ordertype){
    target = $i(target);
    this._ordertype = ordertype;
    this._header = Kumu.toArray(target.getElementsByTagName("thead")[0].getElementsByTagName("th"));
    this._tbody = target.getElementsByTagName("tbody")[0];
    this._sortbody = Kumu.toArray(this._tbody.getElementsByTagName("tr"));
    var index = 0;
    this._header.map(function(head){
        head.__index = index;
        this.click_sort.registEventToElement(head, this);
        index++;
    }.bindScope(this));
  },
  
  _order : function(th){
    if(typeof(th.__asc) == 'undefined'){
      th.__asc = true;
    }
    var asc = th.__asc;
    if(asc){
        th.__asc = false;
        return false;
    }else{
        th.__asc = true;
        return true;
    }
  },
  
  click_sort : function click_sort(evt, th){
    var orderby = this._order(th);
    var index = th.__index;
    this._sortbody.sort(this._createComparator(orderby, index).bindScope(this));
    this._clearBody(this._tbody);
    this._appendBody(this._tbody);
  },
  
  _getCellValue : function(node, index){
    return node.getElementsByTagName("td")[index].innerHTML;
  },
  
  _createComparator : function(orderby, index){
    if(orderby){
      return function(node1, node2){
        var value1 = this._getCellValue(node1, index);
        var value2 = this._getCellValue(node2, index);
        if(this._ordertype && this._ordertype[index] == "number"){
          if(!parseFloat(value1)){
            return 1;
          }else if(!parseFloat(value2)){
            return -1;
          }
          return (parseFloat(value1) - parseFloat(value2));
        }else{
          return (value1.toLowerCase() > value2.toLowerCase()) ? 1 : -1;
        }
      }
    }else{
      return function(node1, node2){
        var value1 = this._getCellValue(node1, index);
        var value2 = this._getCellValue(node2, index);
        if(this._ordertype && this._ordertype[index] == "number"){
          if(!parseFloat(value1)){
            return 1;
          }else if(!parseFloat(value2)){
            return -1;
          }
          return (parseFloat(value2) - parseFloat(value1));
        }else{
          return (value2.toLowerCase() > value1.toLowerCase()) ? 1 : -1;
        }
      }
    }
  },
  
  _clearBody : function(tbody){
    Kumu.toArray(tbody.childNodes).map(function(node){
      node.parentNode.removeChild(node);
    });
  },
  
  _appendBody : function(tbody){
    this._sortbody.map(function(node){
      tbody.appendChild(node);
    });
  },
  
  initialize : function(target, type, loaded){
    if(!loaded){
      Kumu.Event.addEvent(window, 'load', function(e){
  		this.start(target, type);
  	  }.bindScope(this));
    }else{
	  this.start(target, type);
    }  
  },
  
  destroy : function(){
    this._header.map(function(node){
      Kumu.Event.process[node]['click'].map(function(event){
      	event.remove();
      });
    });
    this._header = [];
    this._tbody = [];
    this._sortbody = [];
  }
  
});

