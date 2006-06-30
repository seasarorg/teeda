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
 
 Position = {
    isCSS:false,
    isW3C:false,
    isIE4:false,
    isNN4:false,
    isIE6CSS:false,
    
    init : function(){
        var self = Position;
        if(document.images){
            self.isCSS = (document.body && document.body.style) ? true:false;
            self.isW3C = (self.isCSS && document.getElementById) ? true:false;
            self.isIE4 = (self.isCSS && document.all) ? true:false;
            self.isNN4 = (document.layers) ? true:false;
            self.isIE6CSS = (document.compatMode && document.compatMode.indexOf('CSS1') >= 0) ? true:false;
        }  
    },
  
    seekLayer : function(doc, name){
        var self = Position;
        var theObj;
        for(var i = 0; i < doc.layers.length; i++){
            if(doc.layers[i].name = name){
                theObj = layers[i];
                break;
            }
            if(doc.layers[i].document.layers.length > 0){
                theObj = self.seekLayer(document.layers[i].document, name);h
            }
            return theObj;
        }
    },

    getRawObject : function(obj){
        var self = Position;
        var theObj;
        if(typeof obj  == 'string'){
            if(self.isW3C){
                theObj = document.getElementById(obj);    
            }else if(self.isIE4){
                theObj = document.all(obj);
            }else if(self.isNN4){
                theObj = self.seekLayer(document, obj);
            }
        }else{
            //string???????????
            theObj = obj;
        }
        return theObj;
    } ,
    
    getObject : function(obj){
        var self = Position;
        var theObj = self.getRawObject(obj);
        if(theObj && self.isCSS){
            theObj = theObj.style; 
        }
        return theObj;
    },
    
    getObjectLeft : function(obj){
        var self = Position;
        var elem = self.getRawObject(obj);
        var result = 0;
        if(document.defaultView){
            var style = document.defaultView;
            var cssDecl = style.getComputedStyle(elem, "");
            result = cssDecl.getPropertyValue('left');
        }else if(elem.currentStyle){
            result = elem.currentStyle.left;
        }else if(elem.style){
            result = elem.style.left;
        }else if(self.isNN4){
            result = elem.left;
        }
        if(result == 'auto'){
            result = 0;
        }
        return parseInt(result);
    },
    
    getObjectTop : function(obj){
        var self = Position;
        var elem = self.getRawObject(obj);
        var result = 0;
        if(document.defaultView){
            var style = document.defaultView;
            var cssDecl = style.getComputedStyle(elem, "");
            result = cssDecl.getPropertyValue('top');
        }else if(elem.currentStyle){
            result = elem.currentStyle.top;
        }else if(elem.style){
            result = elem.style.top;
        }else if(self.isNN4){
            result = elem.top;
        }
        if(result == 'auto'){
            result = 0;
        }
        return parseInt(result);
    },
    
    getObjectWidth : function(obj){
        var self = Position;
        var elem = self.getRawObject(obj);
        var result = 0;
        if(elem.offsetWidth){
            result = elem.offsetWidth;
        }else if(elem.clip && elem.clip.width){
            result = elem.clip.width;
        }else if(elem.style && elem.style.width){
            result = elem.style.pixelWidth;
        }
        return parseInt(result);
    },

    getObjectHeight : function(obj){
        var self = Position;
        var elem = self.getRawObject(obj);
        var result = 0;
        if(elem.offsetHeight){
            result = elem.offsetHeight;
        }else if(elem.clip && elem.clip.height){
            result = elem.clip.height;
        }else if(elem.style && elem.style.height){
            result = elem.style.pixelHeight;
        }
        return parseInt(result);
    },
    
    getInsideWindowWidth : function(){
        var self = Position;
        if(window.innerWidth){
            return window.innerWidth;
        }else if(self.isIE6CSS){
            return document.body.parentElement.clientWidth;
        }else if(document.body && document.body.clientWidth){
            return document.body.clientWidth;
        }
        return 0;
    },

    getInsideWindowHeight : function(){
        var self = Position;
        if(window.innerHeight){
            return window.innerHeight;
        }else if(self.isIE6CSS){
            return document.body.parentElement.clientHeight;
        }else if(document.body && document.body.clientHeight){
            return document.body.clientHeight;
        }
        return 0;
    },
    
    withIn : function(obj, x, y){
        var self = Position;
        var top = self.getObjectTop(obj);
        var left = self.getObjectLeft(obj);
        var height = self.getObjectHeight(obj);
        var width = self.getObjectWidth(obj);
        if((left < x) && (x < (left+width)) && 
        (top < y) && (y < (top+height))){
            return true;
        }else{
            return false;
        }        
    },
     
    shiftTo : function(obj, x, y){
        var self = Position;
        var theObj = self.getObject(obj);
        if(theObj){
            if(self.isCSS){
                if(theObj.position != 'absolute'){
                    theObj.position = 'absolute'
                }
                var units = (typeof theObj.left == 'string') ? "px" : 0;
                theObj.left = x+units;
                theObj.top = y+units;
            }else if(self.isNN4){
                theObj.moveTo(x, y);
            }
        }   
    },
    
    shiftBy : function(obj, deltaX, deltaY){
        var self = Position;
        var theObj = self.getObject(obj);
        if(theObj){
            if(self.isCSS){
                if(theObj.position != 'absolute'){
                    theObj.position = 'absolute'
                }
                var units = (typeof theObj.left == 'string') ? "px" : 0;
                theObj.left = self.getObjectLeft(obj) + deltaX + units;
                theObj.top = self.getObjectTop(obj) + deltaY + units;
            }else if(self.isNN4){
                theObj.moveTo(deltaX, deltaY);
            }
        }
    },

    setZIndex : function(obj, zOrder){
        var self = Position;
        var theObj = self.getObject(obj);
        if(theObj){
            theObj.zIndex = zOrder; 
        }
    },  
    
    setBGColor : function(obj, color){
        var self = Position;
        var theObj = self.getObject(obj);
        if(theObj){
            if(self.isNN4){
                theObj.bgcolor = color;
            }else if(self.isCSS){
                theObj.backgroundColor = color;
            }            
        }        
    },
    
    show : function(obj){
        var self = Position;
        var theObj = self.getObject(obj);
        if(theObj){
            theObj.visibility = 'visible';
        }
    },

    hide : function(obj){
        var self = Position;
        var theObj = self.getObject(obj);
        if(theObj){
            theObj.visibility = 'hidden';
        }
    }        
}

Dragable = {
    dragableObj:[],
    selectedObj:{},
    offsetX:{},
    offsetY:{},
    
    regist : function(target){
        var self = Dragable;
        self.dragableObj.push(target);
    },
    
    setSelectedElem : function(evt){
        var self = Dragable;
        var target = (evt.target) ? evt.target : evt.srcElement;
        var divID = (target.name && target.src) ? target.name  : target.id;
        if(divID){
            if(document.layers){
                self.selectedObj = document.layers[divID];
            }else if(document.all){
                self.selectedObj = document.all(divID);
            }else if(document.getElementById){
                self.selectedObj = document.getElementById(divID);
            }
            Position.setZIndex(self.selectedObj, 100);
            return;
        }
        self.selectedObj = null;
        return;
    },

    setSelectedElement : function(id){
        var self = Dragable;
        if(id){
            if(document.layers){
                self.selectedObj = document.layers[id];
            }else if(document.all){
                self.selectedObj = document.all(id);
            }else if(document.getElementById){
                self.selectedObj = document.getElementById(id);
            }
            Position.setZIndex(self.selectedObj, 100);
            return;
        }
        self.selectedObj = null;
        return;
    },
    
    engage : function(evt){
        var self = Dragable;
        evt = (evt) ? evt : event;
        
        self.setSelectedElem(evt);
        
        if(self.selectedObj){
            var selected = self.selectedObj;
            if(document.body && document.body.setCapture){
                document.body.setCapture(false);
            }
            if(evt.pageX) {
                self.offsetX = evt.pageX - ( (selected.offsetLeft) ? selected.offsetLeft : selected.left);
                self.offsetY = evt.pageY - ( (selected.offsetTop) ? selected.offsetTop : selected.top);
            }else if(typeof  evt.offsetX != 'undefined'){
                self.offsetX = evt.offsetX - (( evt.offsetX < -2) ? 0 : document.body.scrollLeft);
                self.offsetX -= (document.body.parentElement && document.body.parentElement.scrollLeft) ? document.body.parentElement.scrollLeft : 0;
                self.offsetY = evt.offsetY - (( evt.offsetY < -2) ? 0 : document.body.scrollTop);
                self.offsetY -= (document.body.parentElement && document.body.parentElement.scrollTop) ? document.body.parentElement.scrollTop : 0;
            }else if(typeof  evt.clientX != 'undefined'){
                self.offsetX = evt.clientX - ( (selected.offsetLeft) ? selected.offsetLeft : 0);
                self.offsetY = evt.clientY - ( (selected.offsetTop) ? selected.offsetTop : 0);
            }
        }
        return false;
    },
    
    dragIt : function(evt){
        var self = Dragable;
        evt = (evt) ? evt : event;
        if(self.selectedObj){
            if(evt.pageX){
                Position.shiftTo(self.selectedObj, (evt.pageX - self.offsetX), (evt.pageY - self.offsetY));
            }else if(evt.clientX || evt.clientY){
                Position.shiftTo(self.selectedObj, (evt.clientX - self.offsetX), (evt.clientY - self.offsetY));
            }
            evt.cancelBuble = false;
            return false;
        }        
    },
    
    release : function(evt){
        var self = Dragable;
        if(self.selectedObj){
            Position.setZIndex(self.selectedObj, 100);
            if(document.body && document.body.releaseCapture){
                document.body.releaseCapture();
            }
            self.selectedObj = null;
        }
    },
    
    init : function(){
        var self = Dragable;
        if(document.layers){
            document.captureEvents(Event.MOUSEDOWN | Event.MOUSEUP | Event.MOUSEMOVE);
            return;
        }else if(document.body && document.body.addEventListener){
            document.addEventListener('mousedown', self.engage, true);            
            document.addEventListener('mousemove', self.dragIt, true);            
            document.addEventListener('mouseup', self.release, true);            
            return ;
        }
        document.onmousedown = self.engage;
        document.onmousemove = self.dragIt;
        document.onmouseup = self.release;
        return ;
    }   
}

LoggingPane = {
    pane :{},
    create : function(){
        LoggingPane.pane = document.createElement("DIV");
    }
}

DragIt={
    selectedObj:{},
    offsetX:{},
    offsetY:{},
    
    setSelectedElem : function(evt){
        var self = Dragable;
        var target = (evt.target) ? evt.target : evt.srcElement;
        var divID = (target.name && target.src) ? target.name  : target.id;
        if(divID){
            if(document.layers){
                self.selectedObj = document.layers[divID];
            }else if(document.all){
                self.selectedObj = document.all(divID);
            }else if(document.getElementById){
                self.selectedObj = document.getElementById(divID);
            }
            Position.setZIndex(self.selectedObj, 100);
            return;
        }
        self.selectedObj = null;
        return;
    },
    
    engage : function(evt){
        var self = Dragable;
        evt = (evt) ? evt : event;
        self.setSelectedElem(evt);
        if(self.selectedObj){
            var selected = self.selectedObj;
            if(document.body && document.body.setCapture){
                document.body.setCapture(false);
            }
            if(evt.pageX) {
                self.offsetX = evt.pageX - ( (selected.offsetLeft) ? selected.offsetLeft : selected.left);
                self.offsetY = evt.pageY - ( (selected.offsetTop) ? selected.offsetTop : selected.top);
            }else if(typeof  evt.offsetX != 'undefined'){
                self.offsetX = evt.offsetX - (( evt.offsetX < -2) ? 0 : document.body.scrollLeft);
                self.offsetX -= (document.body.parentElement && document.body.parentElement.scrollLeft) ? document.body.parentElement.scrollLeft : 0;
                self.offsetY = evt.offsetY - (( evt.offsetY < -2) ? 0 : document.body.scrollTop);
                self.offsetY -= (document.body.parentElement && document.body.parentElement.scrollTop) ? document.body.parentElement.scrollTop : 0;
            }else if(typeof  evt.clientX != 'undefined'){
                self.offsetX = evt.clientX - ( (selected.offsetLeft) ? selected.offsetLeft : 0);
                self.offsetY = evt.clientY - ( (selected.offsetTop) ? selected.offsetTop : 0);
            }
        }
        return false;
    },
    
    dragIt : function(evt){
        var self = Dragable;
        evt = (evt) ? evt : event;
        if(self.selectedObj){
            if(evt.pageX){
                Position.shiftTo(self.selectedObj, (evt.pageX - self.offsetX), (evt.pageY - self.offsetY));
            }else if(evt.clientX || evt.clientY){
                Position.shiftTo(self.selectedObj, (evt.clientX - self.offsetX), (evt.clientY - self.offsetY));
            }
            evt.cancelBuble = false;
            return false;
        }        
    },
    
    release : function(evt){
        var self = Dragable;
        if(self.selectedObj){
            Position.setZIndex(self.selectedObj, 100);
            if(document.body && document.body.releaseCapture){
                document.body.releaseCapture();
            }
            self.selectedObj = null;
        }
    },
    
    init : function(){
        var self = Dragable;
        self.selectedObj = [];
        if(document.layers){
            document.captureEvents(Event.MOUSEDOWN | Event.MOUSEUP | Event.MOUSEMOVE);
            return;
        }else if(document.body && document.body.addEventListener){
            document.addEventListener('mousedown', self.engage, true);            
            document.addEventListener('mousemove', self.dragIt, true);            
            document.addEventListener('mouseup', self.release, true);            
            return ;
        }
        document.onmousedown = self.engage;
        document.onmousemove = self.dragIt;
        document.onmouseup = self.release;
        return ;
    }   
}
